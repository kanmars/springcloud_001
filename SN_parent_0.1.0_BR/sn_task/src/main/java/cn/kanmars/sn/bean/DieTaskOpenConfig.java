package cn.kanmars.sn.bean;

/** 
 * @ClassName: DieTaskOpenConfig 
 * @Description:
 * @date 2015年1月20日 下午3:49:09
 */
public class DieTaskOpenConfig {

	/**ID*/
	private String id;
	
	/**解锁说明*/
	private String openAsk;
	
	/**SQL语句*/
	private String sqls;
	
	/**状态:1,启用；2，禁用。*/
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenAsk() {
		return openAsk;
	}

	public void setOpenAsk(String openAsk) {
		this.openAsk = openAsk;
	}

	public String getSqls() {
		return sqls;
	}

	public void setSqls(String sqls) {
		this.sqls = sqls;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
