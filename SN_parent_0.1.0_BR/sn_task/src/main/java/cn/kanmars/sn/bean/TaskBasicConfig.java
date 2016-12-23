package cn.kanmars.sn.bean;

/** 
 * @ClassName: TaskBasicConfig 
 * @Description: 任务基本信息配置表实体
 * @date 2015年1月20日 上午10:51:38
 */
public class TaskBasicConfig {
	
	/** ID */
	private String id;

	/** 任务名称 */
	private String taskName;

	/** 执行规则 */
	private String runRule;

	/** 状态:1,启用；2，禁用。 */
	private String status;

	/** 业务对象名称 */
	private String businessObjName;

	/**业务ID*/
	private String parentId;
	
	/**执行状态：
	 * 如果runStatus=0，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发则可以任意执行，不受限制，即：可能出现任务堆积线程数用尽的情况
	 * 如果runStatus=1，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发后会等待上一个任务执行完成，如果等待超时后(默认6000毫秒)，上一个任务未执行完，则需要抛弃此任务
	 * 如果runStatus=2，则同时运行taskCount个任务，当每个任务未执行完时，新任务触发后则抛弃此任务*/
	private String runStatus;
	
	/**任务数*/
	private int taskCount;
	
	/**修改时间*/
	private String upTime;
	
	/**任务参数*/
	private String businessInfo;
	
	/**网卡名称*/
	private String internetName;
	
	/**服务器IP*/
	private String serverIp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRunRule() {
		return runRule;
	}

	public void setRunRule(String runRule) {
		this.runRule = runRule;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessObjName() {
		return businessObjName;
	}

	public void setBusinessObjName(String businessObjName) {
		this.businessObjName = businessObjName;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getInternetName() {
		return internetName;
	}

	public void setInternetName(String internetName) {
		this.internetName = internetName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
}
