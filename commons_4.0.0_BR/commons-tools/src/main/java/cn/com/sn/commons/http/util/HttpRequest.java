package cn.com.sn.commons.http.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.RequestEntity;


/**
 * http request 请求参数 
 * <li><font color='blue'>method = HttpRequestMethodType.POST (默认值)</font></li> 
 * <li><font color='blue'>responseType = HttpResponseType.STRING (默认值)</font></li> 
 * <li><font color='blue'>httpclient (为空时创建默认对象)</font></li> 
 * @author lili-ds
 * 
 */
public class HttpRequest {
	private HttpRequestMethodType method = HttpRequestMethodType.POST;
	private HttpResponseType responseType = HttpResponseType.STRING;
	
	private HttpRequestParams requestParams;
	private HttpProxyParams httpProxyParams;
	
	private HttpClient httpclient;
	
	private String url;
	private String queryString;
	private Map<String, String> parameters;
	private RequestEntity requestEntity;
	
	public HttpRequest(){
	}
	
	public HttpRequest(String url){
		this.url = url;
	}
	
	public HttpRequestMethodType getMethod() {
		return method;
	}
	public void setMethod(HttpRequestMethodType method) {
		this.method = method;
	}
	public HttpResponseType getResponseType() {
		return responseType;
	}
	public void setResponseType(HttpResponseType responseType) {
		this.responseType = responseType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public Map<String, String> getParameters(boolean create) {
		if(create && null == parameters){
			parameters = new HashMap<String, String>();
		}
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public RequestEntity getRequestEntity() {
		return requestEntity;
	}
	public void setRequestEntity(RequestEntity requestEntity) {
		this.requestEntity = requestEntity;
	}
	public HttpRequestParams getRequestParams() {
		return requestParams;
	}
	public HttpRequestParams getRequestParams(boolean create) {
		if(create && null == requestParams){
			requestParams = new HttpRequestParams();
		}
		return requestParams;
	}
	public void setRequestParams(HttpRequestParams requestParams) {
		this.requestParams = requestParams;
	}
	public HttpProxyParams getHttpProxyParams() {
		return httpProxyParams;
	}
	public HttpProxyParams getHttpProxyParams(boolean create) {
		if(create && null == httpProxyParams){
			httpProxyParams = new HttpProxyParams();
		}
		return httpProxyParams;
	}
	public void setHttpProxyParams(HttpProxyParams httpProxyParams) {
		this.httpProxyParams = httpProxyParams;
	}
	public HttpClient getHttpclient() {
		return httpclient;
	}
	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}
	
}
