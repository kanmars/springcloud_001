package cn.com.xcommon.commons.http.util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParams {
	private Integer connectionTimeout;
	private Integer soTimeout;
	private String charset;
	
	private Map<String ,String> headerParams;
	private String useragent;
	
	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public Integer getSoTimeout() {
		return soTimeout;
	}
	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public String getUseragent() {
		return useragent;
	}
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}
	public Map<String, String> getHeaderParams() {
		return headerParams;
	}
	public Map<String, String> getHeaderParams(boolean create) {
		if(create && null == headerParams){
			headerParams = new HashMap<String, String>();
		}
		return headerParams;
	}
	public void setHeaderParams(Map<String, String> headerParams) {
		this.headerParams = headerParams;
	}
	
}
