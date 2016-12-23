package cn.kanmars.sn.step;

import cn.kanmars.sn.bean.TaskBasicConfig;

import java.util.List;

/**
 * @ClassName: IStep 
 * @Description: 业务接口 
 */
public interface IStep<T> {

	/**
	* @Title: queryTaskInfo 
	* @Description: 查询所有要执行的任务集合
	* @return List<T>    返回类型
	* @throws
	 */
	public List<T> queryTaskInfo(TaskBasicConfig config,String trigerMsg);
	
	/**
	* @Title: runTask 
	* @Description: 执行每一条任务
	* @param  t  任务信息实体
	* @return void    返回类型 
	* @throws
	 */
	public boolean runTask(T t);
	
	/**
	* @Title: editTaskInfo 
	* @Description: 修改所有要执行的任务集合
	* @param list
	* @return boolean    返回类型
	* @throws
	 */
	public boolean editTaskInfo(List<T> list);
}
