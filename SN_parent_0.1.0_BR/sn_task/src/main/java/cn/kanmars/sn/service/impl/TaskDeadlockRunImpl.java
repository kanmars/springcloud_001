package cn.kanmars.sn.service.impl;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TaskInfoDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * @ClassName: TaskDeadlockImpl 
 * @Description: 死锁任务解锁执行器实现 
 */
public class TaskDeadlockRunImpl  implements Job{
	
	private HLogger logger = LoggerManager.getLoger("TaskDeadlockRunImpl");

	/**
	 * 死锁任务解锁执行器实现 
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Object obj = context.getJobDetail().getJobDataMap().get("taskInfoDAO");
		if(obj != null){
			TaskInfoDAO taskInfoDAO = (TaskInfoDAO) obj;
			taskInfoDAO.getDieTaskOpenConfigRun();
			logger.info("死锁任务解锁执行成功。。。");
		}
	}

}
