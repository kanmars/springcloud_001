package cn.kanmars.sn.cache;

import cn.kanmars.sn.bean.SiteChildTask;
import cn.kanmars.sn.bean.TaskBasicConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/** 
 * @ClassName: SystemConfigInfoCache 
 * @Description: 基础配置信息缓存类 
 * @date 2015年1月20日 下午6:25:50
 */
public class SystemConfigInfoCache {

	/**任务配置是否修改过的标志符，用修改时间做为唯一值*/
	protected static Map<String , String> updateTaskTimeMap = new HashMap<String , String>();
	
	/**同一类型任务进程数记录*/
	protected static Map<String , Integer> taskCountMap = new HashMap<String , Integer>();
	
	/**调度规则*/
	protected static Map<String , String> ruleMap = new HashMap<String , String>();
	
	/**任务对象缓存*/
	protected static List<TaskBasicConfig> ruleList = null;

	/**站点推送配置缓存*/
	protected static ConcurrentMap<String , SiteChildTask> siteMap = null;
	
	public synchronized static void setSiteList(List<SiteChildTask> siteList){
		if(siteList!=null && siteList.size()>0){
			ConcurrentMap<String , SiteChildTask> temp = new ConcurrentHashMap<String, SiteChildTask>();
			for(SiteChildTask siteChildTask : siteList){
				temp.put(siteChildTask.getSiteType()+"_"+siteChildTask.getTaskId(), siteChildTask);
			}
			siteMap = temp;
		}
	}
	
	/**
	* @Title: getSiteChildTask 
	* @Description: 获取站点推送配置信息
	* @param @param siteType
	* @param @param taskId
	* @param @return    设定文件 
	* @return SiteChildTask    返回类型 
	* @throws
	 */
	public static SiteChildTask getSiteChildTask(String siteType,String taskId){
		if(siteMap!=null){
			return siteMap.get(siteType+"_"+taskId);
		}
		return null;
	}

	/**KEY 为taskName ->即taskName = job.getId()+"_"+i;*/
	private static Map<String,ConcurrentHashMap<String,Object>> taskInnerInfos = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();

	/**
	 * 针对任务设置信息
	 * @param taskName	taskName = job.getId()+"_"+i;i为任务的序号
	 * @param infoname
	 * @param info
	 */
	public synchronized static void putTaskInnerInfo(String taskName,String infoname,String info){
		ConcurrentHashMap<String,Object> taskInnerInfo = taskInnerInfos.get(taskName);
		if(taskInnerInfo==null){
			taskInnerInfo = new ConcurrentHashMap<String, Object>();
			taskInnerInfos.put(taskName,taskInnerInfo);
		}
		taskInnerInfo.put(infoname,info);
	}
	/**
	 * 针对任务获取信息
	 * @param taskName	taskName = job.getId()+"_"+i;i为任务的序号
	 * @param infoname
	 */
	public synchronized static Object getTaskInnerInfo(String taskName,String infoname){
		ConcurrentHashMap<String,Object> taskInnerInfo = taskInnerInfos.get(taskName);
		if(taskInnerInfo==null){
			taskInnerInfo = new ConcurrentHashMap<String, Object>();
			taskInnerInfos.put(taskName,taskInnerInfo);
		}
		return taskInnerInfo.get(infoname);
	}

	public static Map<String, Integer> getTaskCountMap() {
		return taskCountMap;
	}

	public static void setTaskCountMap(Map<String, Integer> taskCountMap) {
		SystemConfigInfoCache.taskCountMap = taskCountMap;
	}

	public static Map<String, String> getRuleMap() {
		return ruleMap;
	}

	public static void setRuleMap(Map<String, String> ruleMap) {
		SystemConfigInfoCache.ruleMap = ruleMap;
	}

	public static List<TaskBasicConfig> getRuleList() {
		return ruleList;
	}

	public static void setRuleList(List<TaskBasicConfig> ruleList) {
		SystemConfigInfoCache.ruleList = ruleList;
	}

	public static ConcurrentMap<String, SiteChildTask> getSiteMap() {
		return siteMap;
	}

	public static void setSiteMap(ConcurrentMap<String, SiteChildTask> siteMap) {
		SystemConfigInfoCache.siteMap = siteMap;
	}

	public static void setTaskInnerInfos(Map<String, ConcurrentHashMap<String, Object>> taskInnerInfos) {
		SystemConfigInfoCache.taskInnerInfos = taskInnerInfos;
	}

	public static Map<String, String> getUpdateTaskTimeMap() {
		return updateTaskTimeMap;
	}

	public static void setUpdateTaskTimeMap(Map<String, String> updateTaskTimeMap) {
		SystemConfigInfoCache.updateTaskTimeMap = updateTaskTimeMap;
	}

	/**
	 * 删除一个任务缓存信息
	 * @param taskName	taskName = job.getId()+"_"+i;i为任务的序号
	 */
	public synchronized static void delTaskInnerInfo(String taskName){
		taskInnerInfos.remove(taskName);
	}

	/**
	 * 获取任务缓存对象，根据此对象，可以获取到任务的数量等信息
	 * @return
	 */
	public static Map<String,ConcurrentHashMap<String,Object>> getTaskInnerInfos(){
		return taskInnerInfos;
	}

}
