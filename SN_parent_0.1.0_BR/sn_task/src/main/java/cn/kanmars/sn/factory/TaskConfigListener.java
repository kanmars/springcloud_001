package cn.kanmars.sn.factory;

import javax.annotation.PostConstruct;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TaskInfoDAO;
import cn.kanmars.sn.service.impl.TaskConfigRunImpl;
import cn.kanmars.sn.utils.BasisConstants;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TaskConfigListener 
 * @Description: 任务参数变更监听器
 * @date 2015年1月20日 下午4:48:09
 */
@Service
public class TaskConfigListener {
	
	private HLogger logger = LoggerManager.getLoger("TaskConfigListener");

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private TaskInfoDAO taskInfoDAO;
	
	@PostConstruct
	public void upTaskBasicConfig() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail =JobBuilder.newJob(TaskConfigRunImpl.class).withIdentity("TaskConfig", BasisConstants.SYSTEM_TASK_GROUP).build();
			jobDetail.getJobDataMap().put("taskInfoDAO", taskInfoDAO);
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder =CronScheduleBuilder.cronSchedule(BasisConstants.taskConfigRule);
			//按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger =TriggerBuilder.newTrigger().withIdentity("TaskConfig", BasisConstants.SYSTEM_TASK_GROUP).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("定时器的构造异常，实时器名称：TaskConfig,",e);
		}
	}
}
