package cn.kanmars.sn.factory;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.dao.TaskInfoDAO;
import cn.kanmars.sn.service.TimerBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TimerTaskFactory
 * @Description: 执行任务定时器初始化工厂
 * @date 2015年1月19日 下午4:34:57
 */
@Service
public class ChildTaskRunFactory {
	
	private HLogger logger = LoggerManager.getLoger("ChildTaskRunFactory");

	@Resource
	private TimerBuildService childTimerBuildServiceImpl;
	
	@Autowired
	private TaskInfoDAO taskInfoDAO;

	@PostConstruct
	public void initTask() {
		logger.info("执行任务定时器初始化工厂开始执行......");
		try{
			//读取站点推送配置到缓存
			SystemConfigInfoCache.setSiteList(taskInfoDAO.getSiteChildTask());
			
			List<TaskBasicConfig> list = childTimerBuildServiceImpl.getCreateTaskInfo();
			SystemConfigInfoCache.setRuleList(list);
			for(TaskBasicConfig config : list){
				childTimerBuildServiceImpl.createTimer(config);
			}
			logger.info("执行任务定时器初始化工厂执行结束，共有"+list.size()+"个定时器被初始化。");
		}catch(Exception e){
			logger.error("执行任务定时器初始化工厂异常:",e);
		}
	}
}
