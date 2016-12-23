package cn.kanmars.sn.bean;

/** 
 * @ClassName: SiteChildTask 
 * @Description: 站点子任务配置表实体
 * @date 2015年1月20日 下午3:26:49
 */
public class SiteChildTask {

	/**ID*/
	private String id;

	/**站点类型*/
	private String siteType;

	/**推送地址*/
	private String sendAddress;

	/**任务ID*/
	private String taskId;

	/**状态:1,启用；2，禁用。*/
	private String status;

	/**总执行次数*/
	private int runCount;

	/**延迟时间（秒做单位）*/
	private int lateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}

	public int getLateTime() {
		return lateTime;
	}

	public void setLateTime(int lateTime) {
		this.lateTime = lateTime;
	}
	
}
