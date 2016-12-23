package cn.kanmars.sn.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.util.MapObjTransUtils;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.dao.TaskInfoDAO;
import cn.kanmars.sn.service.TaskRegisterService;
import cn.kanmars.sn.service.TimerBuildService;
import cn.kanmars.sn.utils.BasisConstants;
import cn.kanmars.sn.utils.QueryServerInfo;
import net.sf.json.JSONObject;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ChildTimerBuildServiceImpl 
 * @Description: 执行任务的定时器的构造实现 
 * @date 2015年1月20日 上午10:42:44
 */
@Service
public class ChildTimerBuildServiceImpl implements TimerBuildService {
	
	private HLogger logger = LoggerManager.getLoger("ChildTimerBuildServiceImpl");

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private TaskRegisterService taskRegisterService;

	@Autowired
	private TaskInfoDAO taskInfoDAO;
	
	public List<TaskBasicConfig> getCreateTaskInfo() {
		return taskInfoDAO.getTaskBasicConfig();
	}

	public synchronized void createTimer(TaskBasicConfig job) {
		try {
			if("1".equals(job.getStatus()) && QueryServerInfo.checkIp(job)){
				logger.info("执行任务的定时器的创建开始......");
				String taskName = "";
				for(int i=0;i<job.getTaskCount();i++){
					taskName = job.getId()+"_"+i;
					timerGenerator(job,taskName);
				}
				SystemConfigInfoCache.getTaskCountMap().put(job.getId()+"_"+ BasisConstants.CHILD_TASK_RUN, job.getTaskCount());
				SystemConfigInfoCache.getUpdateTaskTimeMap().put(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN,job.getUpTime());
				SystemConfigInfoCache.getRuleMap().put(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN,job.getRunRule());
				logger.info("执行任务的定时器的创建成功，定时器名称："+job.getTaskName()+"，定时器总数："+job.getTaskCount());
			}
		} catch (SchedulerException e) {
			logger.error("定时器的构造异常，实时器名称："+job.getTaskName(),e);
		}
	}
	
	private synchronized void timerGenerator(TaskBasicConfig job,String taskName) throws SchedulerException{
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobDetail jobDetail =JobBuilder.newJob(ChildJobTimerRunImpl.class).withIdentity(taskName, BasisConstants.CHILD_TASK_RUN).build();
		jobDetail.getJobDataMap().put("TaskBasicConfig", job);
		Trigger trigger = null;
		if(StringUtils.isEmpty(job.getRunRule())){
			throw new RuntimeException("job.getRunRule()配置不正确为空");
		}
		if(StringUtils.isNotEmpty(job.getRunRule())&&(job.getRunRule().indexOf(":")<0||job.getRunRule().substring(0,job.getRunRule().indexOf(":")).equalsIgnoreCase("cron"))){
			//如果job.getRule没有冒号，或者	是以cron开头的，则为cronScheduleBuilder
			trigger =TriggerBuilder.newTrigger().withIdentity(taskName, BasisConstants.CHILD_TASK_RUN).withSchedule(CronScheduleBuilder.cronSchedule(job.getRunRule())).build();
		}else if(StringUtils.isNotEmpty(job.getRunRule())&&(job.getRunRule().indexOf(":")>0&&job.getRunRule().substring(0,job.getRunRule().indexOf(":")).equalsIgnoreCase("simple"))){
			//如果job.getRule有冒号并且以simple开头，则默认使用两种形式
			try {
				String rule = job.getRunRule().substring(job.getRunRule().indexOf(":")+1);
				if(rule.split(",").length!=3&&rule.split(",").length!=1){
					throw new Exception();
				}
				String startAt = "20010101000000";
				String endAt = "20990101000000";
				long intervalMilliseconds = 0;
				if(rule.split(",").length==3){
					startAt = rule.split(",")[0];
					endAt = rule.split(",")[1];
					intervalMilliseconds = Long.parseLong(rule.split(",")[2]);
				}else if(rule.split(",").length==1){
					intervalMilliseconds = Long.parseLong(rule);
				}
				trigger = TriggerBuilder.newTrigger().withIdentity(taskName, BasisConstants.CHILD_TASK_RUN).withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(intervalMilliseconds).repeatForever()
					)
					.startAt(new SimpleDateFormat("yyyyMMddHHmmss").parse(startAt))
					.endAt(new SimpleDateFormat("yyyyMMddHHmmss").parse(endAt))
					.build();
			} catch (Exception e) {
				throw new RuntimeException("job.getRunRule()配置不正确，请使用:  simple:20161019184200,20181019184200,200或者  simple:200 的形式，分别代表开始时间、结束时间、毫秒间隔");
			}
		}else {
			throw new RuntimeException("job.getRunRule()配置不正确为空");
		}
		//创建内核对象kernel_obj并触发任务增加动作
		ConcurrentHashMap<String,Object>  kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
		if(kernel_obj == null){
			kernel_obj = new ConcurrentHashMap<String, Object>();
			kernel_obj.putAll(JSONObject.fromObject(job));
			kernel_obj.put("TaskBasicConfig", job);
			kernel_obj.put(BasisConstants.KERNELOBJ_Semaphore,new Semaphore(1));
			SystemConfigInfoCache.getTaskInnerInfos().put(taskName, kernel_obj);
		}
		kernel_obj.put("TASK_SCHEDULER", scheduler);
		kernel_obj.put("TASK_JOBDETAILKEY", jobDetail.getKey());
		taskRegisterService.addTask(taskName, kernel_obj);
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public synchronized void updateTimer(TaskBasicConfig job) {
		try {
			logger.info("执行任务的定时器的更新开始......");
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			int count = SystemConfigInfoCache.getTaskCountMap().get(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			String rule = SystemConfigInfoCache.getRuleMap().get(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			String upTime=SystemConfigInfoCache.getUpdateTaskTimeMap().get(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			String taskName = "";
			
			//判断任务是否改为指定执行
			if(!QueryServerInfo.checkIp(job)){
				//删除任务
				for(int i=0;i<count;i++){
					taskName = job.getId()+"_"+i;
					JobKey jobKey = JobKey.jobKey(taskName, BasisConstants.CHILD_TASK_RUN); 
					scheduler.deleteJob(jobKey);
					//获取已有的内存对象并且删除掉任务
					ConcurrentHashMap<String,Object>  kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
					taskRegisterService.deleteTask(taskName, kernel_obj);
					SystemConfigInfoCache.getTaskInnerInfos().remove(taskName);
				}
				logger.info("定时器更改为指定机器执行，指定服务器IP=" + job.getServerIp() + ",删除任务：" + job.getTaskName());
				SystemConfigInfoCache.getTaskCountMap().remove(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
				SystemConfigInfoCache.getUpdateTaskTimeMap().remove(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
				SystemConfigInfoCache.getRuleMap().remove(job.getId() + "_" + BasisConstants.CHILD_TASK_RUN);
				return ;
			}
			
			if(!upTime.equals(job.getUpTime())){
				for(int i=0;i<count;i++){
					taskName = job.getId()+"_"+i;
					timerChange(job,taskName);
				}
				SystemConfigInfoCache.getRuleMap().put(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN,job.getRunRule());
			}
			
			if(count>job.getTaskCount()){
				//删除任务
				for(int i=job.getTaskCount();i<count;i++){
					taskName = job.getId()+"_"+i;
					JobKey jobKey = JobKey.jobKey(taskName, BasisConstants.CHILD_TASK_RUN);
					//获取已有的内存对象并且删除掉任务
					ConcurrentHashMap<String,Object>  kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
					taskRegisterService.deleteTask(taskName, kernel_obj);
					SystemConfigInfoCache.getTaskInnerInfos().remove(taskName);
					scheduler.deleteJob(jobKey);
				}
			}else{
				//增加任务
				for(int i=count;i<job.getTaskCount();i++){
					taskName = job.getId()+"_"+i;
					timerGenerator(job,taskName);
				}
			}

			logger.info("执行任务的定时器的更新成功，定时器名称："+job.getTaskName()+"，原有定时器总数："+count+"，更新后的定时器总数："+job.getTaskCount());
			SystemConfigInfoCache.getTaskCountMap().put(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN,job.getTaskCount());
			SystemConfigInfoCache.getUpdateTaskTimeMap().put(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN,job.getUpTime());
		} catch (SchedulerException e) {
			logger.error("定时器的修改异常，实时器名称："+job.getTaskName(),e);
		}
	}
	
	
	private synchronized void timerChange(TaskBasicConfig job,String taskName) throws SchedulerException{
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(taskName, BasisConstants.CHILD_TASK_RUN);
		Trigger trigger = scheduler.getTrigger(triggerKey);
		if(trigger !=null ){
			//修改配置信息
			JobKey jobKey = JobKey.jobKey(taskName, BasisConstants.CHILD_TASK_RUN); 
			Object obj=scheduler.getJobDetail(jobKey).getJobDataMap().get("TaskBasicConfig");
			try {
				MapObjTransUtils.objToObj((TaskBasicConfig) obj, job);//进行直接复制
			} catch (Exception e) {
				logger.error("对象转化异常",e);
			}


			Trigger newTrigger = null;
			if(StringUtils.isEmpty(job.getRunRule())){
				throw new RuntimeException("job.getRunRule()配置不正确为空");
			}
			if(StringUtils.isNotEmpty(job.getRunRule())&&(job.getRunRule().indexOf(":")<0||job.getRunRule().substring(0,job.getRunRule().indexOf(":")).equalsIgnoreCase("cron"))){
				//如果job.getRule没有冒号，或者	是以cron开头的，则为cronScheduleBuilder
				newTrigger = ((TriggerBuilder)trigger.getTriggerBuilder()).withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(job.getRunRule())).build();
			}else if(StringUtils.isNotEmpty(job.getRunRule())&&(job.getRunRule().indexOf(":")>0&&job.getRunRule().substring(0,job.getRunRule().indexOf(":")).equalsIgnoreCase("simple"))){
				//如果job.getRule有冒号并且以simple开头，则默认使用两种形式
				try {
					String rule = job.getRunRule().substring(job.getRunRule().indexOf(":")+1);
					if(rule.split(",").length!=3){
						throw new Exception();
					}
					String startAt = rule.split(",")[0];
					String endAt = rule.split(",")[1];
					long intervalMilliseconds = Long.parseLong(rule.split(",")[2]);
					newTrigger = ((TriggerBuilder)trigger.getTriggerBuilder()).withIdentity(triggerKey).withSchedule(
							SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(intervalMilliseconds).repeatForever()
					)
							.startAt(new SimpleDateFormat("yyyyMMddHHmmss").parse(startAt))
							.endAt(new SimpleDateFormat("yyyyMMddHHmmss").parse(endAt))
							.build();
				} catch (Exception e) {
					throw new RuntimeException("job.getRunRule()配置不正确，请使用:  simple:20161019184200,20181019184200,200的形式，分别代表开始时间、结束时间、毫秒间隔");
				}
			}else {
				throw new RuntimeException("job.getRunRule()配置不正确为空");
			}
			ConcurrentHashMap<String,Object>  kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
			kernel_obj.putAll(JSONObject.fromObject(job));//更新所有的配置信息
			kernel_obj.put("TASK_SCHEDULER", scheduler);
			kernel_obj.put("TASK_JOBDETAILKEY", jobKey);
			taskRegisterService.updateTask(taskName, kernel_obj);
			scheduler.rescheduleJob(triggerKey, newTrigger);
		}
	}

	public synchronized void deleteTimer(TaskBasicConfig job) {
		try {
			logger.info("执行任务的定时器的删除开始......");
			Scheduler scheduler = schedulerFactoryBean.getScheduler(); 
			int count = SystemConfigInfoCache.getTaskCountMap().get(job.getId() + "_" + BasisConstants.CHILD_TASK_RUN);
			String taskName = "";
			JobKey jobKey = null;
			for(int i=0;i<count;i++){
				jobKey = JobKey.jobKey(job.getId()+"_"+i, BasisConstants.CHILD_TASK_RUN);
				taskName = job.getId()+"_"+i;
				//获取已有的内存对象并且删除掉任务
				ConcurrentHashMap<String,Object>  kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
				taskRegisterService.deleteTask(taskName, kernel_obj);
				SystemConfigInfoCache.getTaskInnerInfos().remove(taskName);
				scheduler.deleteJob(jobKey);
			}
			SystemConfigInfoCache.getTaskCountMap().remove(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			SystemConfigInfoCache.getUpdateTaskTimeMap().remove(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			SystemConfigInfoCache.getRuleMap().remove(job.getId()+"_"+BasisConstants.CHILD_TASK_RUN);
			logger.info("执行任务的定时器的删除成功，定时器名称："+job.getTaskName()+"，删除定时器总数："+count);
		} catch (SchedulerException e) {
			logger.error("定时器的删除异常，实时器名称："+job.getTaskName(),e);
		} 
	}
}
