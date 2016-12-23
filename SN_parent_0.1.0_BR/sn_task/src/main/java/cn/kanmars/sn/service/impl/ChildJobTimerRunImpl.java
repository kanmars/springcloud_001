package cn.kanmars.sn.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.DateUtils;
import cn.com.xcommon.frame.util.SpringBeanFactory;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.step.AbstractStep;
import cn.kanmars.sn.step.IStep;
import cn.kanmars.sn.utils.BasisConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.I0Itec.zkclient.ZkClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
* @ClassName: ChildJobTimerRunImpl 
* @Description: 执行子任务的执行器实现
 */
public class ChildJobTimerRunImpl implements Job{

	private HLogger logger = LoggerManager.getLoger("BaseJobTimerRunImpl");

	/**
	 * 执行子任务的执行器实现
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Object obj = context.getJobDetail().getJobDataMap().get("TaskBasicConfig");
		//在ChildTimerBuildServiceImpl.timerGenerator时创建信号量
		Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
		if(obj != null){
			TaskBasicConfig config = (TaskBasicConfig) obj;
			IStep istep = (IStep) SpringBeanFactory.getBean(config.getBusinessObjName());
			if(istep==null)return;
			String className = istep.getClass().getSuperclass().getSimpleName();
			//如果是AbstractStep，则设置kernelObj对象
			if(istep instanceof AbstractStep){
				String taskName = context.getJobDetail().getKey().getName();
				ConcurrentHashMap<String,Object> kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
				((AbstractStep)istep).setKernel_obj(kernel_obj);
				((AbstractStep)istep).setTaskName(taskName);
				((AbstractStep)istep).setBusinessObjName(config.getBusinessObjName());
				//尝试获取消息队列数据
				String topic = (String)context.getJobDetail().getJobDataMap().get("topic");
				String tags  = (String)context.getJobDetail().getJobDataMap().get("tags");
				String keys  = (String)context.getJobDetail().getJobDataMap().get("keys");
				byte[] body =  (byte[])context.getJobDetail().getJobDataMap().get("body");
				String message = null;
				if(topic!=null&&tags!=null&&body!=null){
					try {
						message = new String(body,"utf-8");
						logger.info("定时任务被消息触发topic["+topic+"]tags["+tags+"]keys["+keys+"]body["+body+"]message["+message+"]");
						if(message.equals("NULLMESSAGE")){
							//如果是被TaskTrigerUtils放入了一个字符串是NULLMESSAGE，则直接设置message为null
							message = null;
						}
					} catch (UnsupportedEncodingException e) {
						logger.error("消息转化异常",e);
					}
				}

				//获取信号量，在ChildTimerBuildServiceImpl.timerGenerator时创建信号量
				Semaphore semaphone = (Semaphore)kernel_obj.get(BasisConstants.KERNELOBJ_Semaphore);

				int planCount = 0;
				int execCount=0;
				int execUseTime=0;
				int perAvgExecUseTime=0;
				long allStart = System.currentTimeMillis();
				try{
					//如果runStatus=0，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发则可以任意执行，不受限制，即：可能出现任务堆积线程数用尽的情况
					//如果runStatus=1，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发后会等待上一个任务执行完成，如果等待超时后(默认6000毫秒)，上一个任务未执行完，则需要抛弃此任务
					//如果runStatus=2，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发后则抛弃此任务
					if("1".equals(config.getRunStatus())){
						logger.debug("开始申请信号量" + semaphone);
						boolean requireResult = semaphone.tryAcquire(6000, TimeUnit.MILLISECONDS);
						if(!requireResult){
							logger.debug("申请信号量失败");
							//如果6秒内没有获取到信号量，则停止当前任务执行
							return;
						}
						logger.debug("申请信号量成功");
					}else if("2".equals(config.getRunStatus())){
						int i = semaphone.availablePermits();//获取当前信号量可用的数量
						if(i<=0){
							//如果已经信号量被别的线程占用，则停止任务执行
							return;
						}
					}
					//按照统一流程执行
					List<Object> list = istep.queryTaskInfo(config,message);
					//统计查询出多少要执行的数据
					if(list != null){
						planCount = list.size();
					}
					if(list != null&&list.size()>0){
						for(Object temp : list){
							//记录单条数据开始时间
							long perStart = System.currentTimeMillis();
							//此处不try，是为了保留在业务层任务执行到一半发现错误，进而暂停的能力
							//执行单条
							boolean execResult = istep.runTask(temp);
							//如果单条数据为true，则执行结果加一
							if(execResult){execCount++;}
							//记录单条数据结束时间
							long perEnd = System.currentTimeMillis();
							//计算单条数据平均时间
							perAvgExecUseTime = (perAvgExecUseTime+((int)(perEnd-perStart))/1000)/2;
						}
						istep.editTaskInfo(list);
					}else{
						logger.info("没有查询到需要执行的记录："+config.getTaskName());
					}
				}catch(Exception e){
					logger.error("处理异常", e);
				}finally {
					if("1".equals(config.getRunStatus())||"2".equals(config.getRunStatus())){
						semaphone.release();
						logger.debug("释放信号量");
					}
				}
				long allEnd = System.currentTimeMillis();
				execUseTime = (int)(allEnd - allStart)/1000;
				//储存执行信息
				storeExecuteInfo(kernel_obj, planCount,execCount,execUseTime,perAvgExecUseTime);
			}else{
				try{
					//按照统一流程执行
					List<Object> list = istep.queryTaskInfo(config,null);
					if(list != null){
						for(Object temp : list){
							istep.runTask(temp);
						}
						istep.editTaskInfo(list);
					}else{
						logger.info("没有查询到需要执行的记录："+config.getTaskName());
					}
				}catch(Exception e){
					logger.error("处理异常",e);
				}
			}
		}
	}

	/**
	 * 储存执行信息
	 * @param kernel_obj	核心对象
	 * @param planCount	计划执行数量
	 * @param execCount	实际执行数量
	 * @param execUseTime	当前任务执行时间
	 * @param perAvgExecUseTime	平均每个数据执行时间
	 */
	public void storeExecuteInfo(ConcurrentHashMap<String,Object> kernel_obj,int planCount,int execCount,int execUseTime,int perAvgExecUseTime){
		synchronized (kernel_obj){//以每个任务的kernel_obj为监视器对象
			ZkClient zkCLient = null;
			if(kernel_obj!=null){
				zkCLient = (ZkClient)kernel_obj.get("zkClient");
			}
			String GLOBAL_REGISTER_PATH = null;
			if(zkCLient!=null&&kernel_obj!=null){
				String GLOBAL_REGISTER_PATH_tmp = (String)kernel_obj.get("GLOBAL_REGISTER_PATH");
				if(zkCLient.exists(GLOBAL_REGISTER_PATH_tmp)){
					GLOBAL_REGISTER_PATH = GLOBAL_REGISTER_PATH_tmp;
				}
			}
			if(zkCLient!=null&&kernel_obj!=null&&GLOBAL_REGISTER_PATH!=null){
				//保证历史对象存在
				JSONArray execHistory = new JSONArray();
				if(zkCLient.exists(GLOBAL_REGISTER_PATH)){
					try{
						String preData = zkCLient.readData(GLOBAL_REGISTER_PATH);
						if(StringUtils.isNotEmpty(preData)&&preData.startsWith("{")){
							JSONObject preData_json = JSONObject.fromObject(preData);
							execHistory = preData_json.getJSONArray("execHistory");
							if(execHistory==null){
								execHistory = new JSONArray();
							}
						}
					}catch (Exception e){
						logger.debug("处理异常可忽略",e);
						execHistory = new JSONArray();
					}
				}
				//将本次的信息存入历史对象
				HashMap storeMap = new HashMap();
				storeMap.put("execTime", DateUtils.getCurrDateTime());//最近执行时间
				storeMap.put("planCount", planCount + "");//查询出的应执行的任务数量
				storeMap.put("execCount", execCount + "");//实际执行成功的任务数量
				storeMap.put("execUseTime",execUseTime+"");
				storeMap.put("perAvgExecUseTime",perAvgExecUseTime+"");
				execHistory.add(0, JSONObject.fromObject(storeMap));//再未把history序列化之前，放入execHistory
				if(execHistory.size()>10){
					for(int i=10;i<execHistory.size();i++){
						execHistory.remove(i);
					}
				}//删除超出10条的数据
				storeMap.put("execHistory", execHistory);
				zkCLient.writeData(GLOBAL_REGISTER_PATH, JSONObject.fromObject(storeMap).toString());
			}
		}
	}

}
