package cn.kanmars.sn.step;

import cn.kanmars.sn.utils.QueryServerInfo;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/** 
 * @ClassName: AbstractStep，具有加强功能的:
 * 	获取
 * 	taskName			任务序号			JOBID_序号
 * 	businessObjName		业务对象名称		BUSINESS_OBJ_NAME
 * 	globalTaskName		全局任务名称		例如:syncProductDetailTask_HOSTNAMEBAOLONG_IP10.144.33.185_PID9260_TASKNAME530_2
 * 	globalTaskCount		全局任务数量		共有多少任务		(用于分片与建立监听，默认为1)
 * 	globalTaskIndex		当前全局任务索引	当前任务的全局序号	(用于分片与建立监听，默认为0)
 *
 * @Description:
 * @author baolong
 * @date 2016年4月8日 下午10:09:50
 */
@SuppressWarnings("unchecked")
public abstract class AbstractStep<T> implements IStep<T>{
	protected ConcurrentHashMap<String,Object> kernel_obj;

	protected String taskName;

	protected String businessObjName;

	public String getGlobalTaskName(){
		if(kernel_obj!=null){
			String GLOBAL_TASK_NAME = (String)kernel_obj.get("GLOBAL_TASK_NAME");
			return GLOBAL_TASK_NAME;
		}
		return businessObjName+"_HOSTNAME"+ QueryServerInfo.getHostName()+"_IP"+QueryServerInfo.getIp()+"_PID"+ QueryServerInfo.getPid()+"_TASKNAME"+taskName;
	}

	/**
	 * 获取全局task数量
	 * @return
	 */
	public int getGlobalTaskCount(){
		if(kernel_obj!=null){
			try{
				String GLOBAL_TASK_COUNT = (String)kernel_obj.get("GLOBAL_TASK_COUNT");
				Integer globalTaskCount = Integer.parseInt(GLOBAL_TASK_COUNT);
				return globalTaskCount;
			}catch (Exception e){}
		}
		return 1;
	}

	/**
	 * 获取当前task的序号
	 * @return
	 */
	public int getGlobalTaskIndex(){
		if(kernel_obj!=null){
			try{
				String GLOBAL_TASK_INDEX = (String)kernel_obj.get("GLOBAL_TASK_INDEX");
				Integer globalTaskIndex = Integer.parseInt(GLOBAL_TASK_INDEX);
				return globalTaskIndex;
			}catch (Exception e){}
		}
		return 0;
	}

	public String getBusinessObjName() {
		return businessObjName;
	}

	public void setBusinessObjName(String businessObjName) {
		this.businessObjName = businessObjName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ConcurrentHashMap<String, Object> getKernel_obj() {
		return kernel_obj;
	}

	public void setKernel_obj(ConcurrentHashMap<String, Object> kernel_obj) {
		this.kernel_obj = kernel_obj;
	}
}
