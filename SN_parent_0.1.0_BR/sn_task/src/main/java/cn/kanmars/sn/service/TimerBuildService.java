package cn.kanmars.sn.service;

import cn.kanmars.sn.bean.TaskBasicConfig;

import java.util.List;


public interface TimerBuildService {
	
	/**
	* @Title: getCreateTaskInfo 
	* @Description: 查询创建子任务所需要 
	* @return List<TaskBasicConfig>    返回类型 
	* @throws
	 */
	public List<TaskBasicConfig> getCreateTaskInfo();

	/**
	* @Title: createTimer 
	* @Description: 向系统定时器容器中新增一个定时器
	* @return void  返回类型
	* @throws
	 */
	public void createTimer(TaskBasicConfig config);
	
	/**
	* @Title: updateTimer 
	* @Description: 修改已经存在的定时器
	* @return void  返回类型
	* @throws
	 */
	public void updateTimer(TaskBasicConfig config);
	
	/**
	* @Title: deleteTimer 
	* @Description: 删除已经存在的定时器
	* @return void  返回类型
	* @throws
	 */
	public void deleteTimer(TaskBasicConfig config);
}
