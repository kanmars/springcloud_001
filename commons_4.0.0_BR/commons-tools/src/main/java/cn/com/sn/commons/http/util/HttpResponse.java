package cn.com.sn.commons.http.util;

import org.apache.commons.httpclient.HttpClient;


/**
 * Http Response 响应结果
 * <li><font color='blue'>responseBodyString (responseType == HttpResponseType.STRING)</font></li> 
 * <li><font color='blue'>responseBodyBytes (responseType == HttpResponseType.BYTES)</font></li> 
 * <li><font color='blue'>httpclient (返回执行请求后的httpclient)</font></li> 
 * @author lili-ds
 *
 */
public class HttpResponse {
	private HttpClient httpclient;
	/**
	 * 响应状态码
	 */
	private String responseStatusCode;
	
	private HttpResponseType responseType;
	
    /**
     * String类型的responseBody
     */
    private String   responseBodyString;

    /**
     * btye类型的responseBody
     */
    private byte[]   responseBodyBytes;

    /**
     * String类型的responseBody
     * @return
     */
	public String getResponseBodyString() {
		return responseBodyString;
	}

	public void setResponseBodyString(String responseBodyString) {
		this.responseBodyString = responseBodyString;
	}

	/**
	 * btye类型的responseBody
	 * @return
	 */
	public byte[] getResponseBodyBytes() {
		return responseBodyBytes;
	}

	public void setResponseBodyBytes(byte[] responseBodyBytes) {
		this.responseBodyBytes = responseBodyBytes;
	}

	/**
	 * 响应状态码
	 * @return
	 */
	public String getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(String responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	public HttpResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(HttpResponseType responseType) {
		this.responseType = responseType;
	}

	public HttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}
}
