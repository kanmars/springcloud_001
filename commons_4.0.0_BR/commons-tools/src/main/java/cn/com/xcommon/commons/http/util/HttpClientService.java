package cn.com.xcommon.commons.http.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;


/**
 * http client service
 * <li><font color='blue'>提供一个方法执行HTTP请求 execute(HttpRequest request) </font></li>
 * @author lili-ds
 *
 */
public class HttpClientService {
	
	/** http 响应成功 */
	public static final int httpConnectionOK = HttpStatus.SC_OK;
	public static final String defaultEncoding = "UTF-8";
	public static final int connection_timeout = 10 * 1000;// 请求超时时长(毫秒数)
	public static final int so_timeout = 10 * 1000;// 读取数据超时时长(毫秒数)

	public static boolean readResponseBodyByCustomWay = true;//用自定义方式读取数据流(避免出现警告)
	public static final int response_length_warnlimit = 10 * 1024 * 1024;// 响应数据量警告(字节数)

	/**
	 * 执行HTTP请求
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	public static HttpResponse execute(HttpRequest request) throws Exception {
		
		if(null == request || StringUtils.isEmpty(request.getUrl()) ){
			throw new IllegalArgumentException("HttpRequest invalid parameter: url cannot be empty.");
		}
		
		//HttpClient
		HttpClient httpclient = getHttpClient(request);
		
		//HttpMethod
		HttpMethodBase method = null;
		if(HttpRequestMethodType.POST.equals(request.getMethod())){
			method = new PostMethod(request.getUrl());
		}else {
			method = new GetMethod(request.getUrl());
		}
		
		// 设置 header
		if(null != request.getRequestParams()){
			setRequestHeader(method ,request.getRequestParams().getHeaderParams());
		}
		
		// 设置参数
		NameValuePair[] params = null;
		if (null != request.getParameters() && request.getParameters().size() > 0) {
			List<NameValuePair> paramList = toParamList(request.getParameters());
			NameValuePair[] nv = new NameValuePair[1];
			params = (NameValuePair [])paramList.toArray(nv);
		}
		
		if (method instanceof GetMethod){
			if (null != params) {
				method.setQueryString(params);
			}
		}
		else if (method instanceof PostMethod){
			PostMethod postMethod = (PostMethod)method;
			if (null != params) {
				postMethod.setRequestBody(params);
			}
			// requestEntity
			if (null != request.getRequestEntity()) {
				postMethod.setRequestEntity(request.getRequestEntity());
			}
		}
		
		//设置响应数据量警告(字节数)
		method.getParams().setIntParameter(HttpMethodParams.BUFFER_WARN_TRIGGER_LIMIT, response_length_warnlimit);
		
		HttpResponse response = null;
		try {
			
			int statusCode = httpclient.executeMethod(method);
			Object responseData = handleResponse(statusCode ,method ,request.getResponseType());
			
			response = new HttpResponse();
			response.setHttpclient(httpclient);
			response.setResponseStatusCode(String.valueOf(statusCode));
			if(HttpResponseType.STRING.equals(request.getResponseType())){
				response.setResponseBodyString((String)responseData);
				response.setResponseType(HttpResponseType.STRING);
			}else{
				response.setResponseBodyBytes((byte[])responseData);
				response.setResponseType(HttpResponseType.BYTES);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			releaseConnection(method);
		}
		
		return response;
	}
	
	protected static Object handleResponse(Integer statusCode ,HttpMethodBase httpResponse ,HttpResponseType responseType) 
			throws Exception{
		Object responseData = null;
		
		// 获取返回数据
		statusCode = (null == statusCode ? httpResponse.getStatusLine().getStatusCode() : statusCode);
		if (statusCode == httpConnectionOK) {
			if(HttpResponseType.STRING.equals(responseType)){
				responseData = getResponseBodyAsString(httpResponse);
			}else {
				responseData = httpResponse.getResponseBody();
			}
			
		} else {
			// 响应异常
			String responseBody = null;
			if(HttpResponseType.STRING.equals(responseType)){
				responseBody = getResponseBodyAsString(httpResponse);
				responseData = responseBody;
			}else {
				responseData = httpResponse.getResponseBody();
			}
			
			String errorMessage = "Send " + httpResponse.getName() + " request and returns the response status is not " + httpConnectionOK + ".";
			HttpResponseStatusException responseStatusException = new HttpResponseStatusException(errorMessage, null);
			responseStatusException.setUri(httpResponse.getURI().toString());
			responseStatusException.setResponseBody(responseBody);
			responseStatusException.setStatusCode("" + statusCode);
			throw responseStatusException;
		}
		
		return responseData;
	}
	
	protected static String getResponseBodyAsString(HttpMethodBase httpResponse) throws Exception{
		String txt = null;
		
		long contentLength = httpResponse.getResponseContentLength();
		if(readResponseBodyByCustomWay && contentLength <=0 ){
			
			//charset
			String charset = httpResponse.getResponseCharSet();
			//InputStream
			InputStream is = httpResponse.getResponseBodyAsStream();
			InputStreamReader isr = null;
			if(null != charset && charset.trim().length() >0){
				isr = new InputStreamReader(is ,charset);
			}else{
				isr = new InputStreamReader(is);
			}
			//BufferedReader
			BufferedReader buffReader = new BufferedReader(isr); 
			//readLine
			StringBuffer sb = new StringBuffer();
			String line = buffReader.readLine();
			while (null != line) {
				sb.append(line);
				line = buffReader.readLine();
			}
			txt = sb.toString();
			
			buffReader.close();
			
		}else {
			
			txt = httpResponse.getResponseBodyAsString();
		}
		
		return txt;
	}
	
	/**
	 * 释放 HttpClient 连接
	 * @param request
	 * @param httpclient
	 */
	protected static void releaseConnection(HttpMethod httpMethod) {
		try{
			httpMethod.releaseConnection();
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 设置 请求头
	 * @param httpMethod
	 * @param headerParams
	 * @return
	 */
	protected static HttpMethod setRequestHeader(HttpMethod httpMethod ,Map<String ,String> headerParams){
		if(null != headerParams && headerParams.size() > 0 ){
			for(Entry<String ,String> e : headerParams.entrySet()){
				httpMethod.setRequestHeader(e.getKey(), e.getValue());
			}
		}
		return httpMethod;
	}
	/**map 转 NameValuePair 
	 * 
	 * @param dataParams
	 * @return
	 */
	protected static List<NameValuePair> toParamList(Map<String ,String> dataParams){
		List<NameValuePair> params = null;
		if(null != dataParams && dataParams.size() > 0 ){
			params = new ArrayList<NameValuePair>();
			for(Entry<String ,String> e : dataParams.entrySet()){
				params.add(new NameValuePair(e.getKey(), e.getValue()));
			}
		}
		return params;
	}
	
	/**
	 * 获取 http client
	 * @param request 
	 * @return
	 */
	public static HttpClient getHttpClient(HttpRequest request){
		
		//HttpClient
		HttpClient httpClient = ( null == request || null == request.getHttpclient() ? new HttpClient() : request.getHttpclient() );
		
		//charset
		String charset = defaultEncoding;
		//connection_timeout
		int _connection_timeout = connection_timeout;
		//so_timeout
		int _so_timeout = so_timeout;
		//useragent = "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.1)";
		String useragent = null;
		
		HttpRequestParams requestParams = null == request ? null : request.getRequestParams();
		HttpProxyParams httpProxyParams = null == request ? null : request.getHttpProxyParams();
		
		if(null != requestParams){
			if(null != requestParams.getCharset()){
				charset = requestParams.getCharset();
			}
			
			if(null != requestParams.getUseragent()){
				useragent = requestParams.getUseragent();
			}
			
			if(null != requestParams.getConnectionTimeout()){
				_connection_timeout = requestParams.getConnectionTimeout();
			}
			
			if(null != requestParams.getSoTimeout()){
				_so_timeout = requestParams.getSoTimeout();
			}
		}
		
		HttpConnectionManagerParams connectionParams = httpClient.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		connectionParams.setConnectionTimeout(_connection_timeout);
		// 设置读数据超时时间(单位毫秒)
		connectionParams.setSoTimeout(_so_timeout);

		//其他
		HttpClientParams clientParams = httpClient.getParams();
		clientParams.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		clientParams.setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
		clientParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		if(null != useragent && useragent.length() > 0 ){
			clientParams.setParameter(HttpMethodParams.USER_AGENT, useragent);
		}
		
		//------------------------- useProxy ------------------------- 
				
		boolean useProxy = false;//是否使用代理
		boolean useAuth = false;//是否使用论证
		
		String host = null;
		Integer port = null;
		String userName = null;
		String password = "";
		String domain = null;
		
		if(null != httpProxyParams 
				&& null != httpProxyParams.getHost() ){
			useProxy = true;
			host = httpProxyParams.getHost();
			port = 80;
			if(null != httpProxyParams.getPort()){
				port = httpProxyParams.getPort();
			}
			if(null != httpProxyParams.getUserName()){
				userName = httpProxyParams.getUserName();
			}
			if(null != httpProxyParams.getPassword()){
				password = httpProxyParams.getPassword();
			}
			if(null != httpProxyParams.getDomain()){
				domain = httpProxyParams.getDomain().toString();
			}
			if(null != userName && userName.length()>0 ){
				useAuth = true;
			}
		}
		if(useProxy){
			// 代理的主机
			ProxyHost proxy = new ProxyHost(host, port);
			// 使用代理
			if(null == httpClient.getHostConfiguration()){
				HostConfiguration hostConfiguration = new HostConfiguration();
				httpClient.setHostConfiguration(hostConfiguration);
			}
			httpClient.getHostConfiguration().setProxyHost(proxy);
			//抢先认证
			//httpClient.getParams().setAuthenticationPreemptive(true);
			// 代理认证
			if(useAuth){
				UsernamePasswordCredentials credentials = null;
				if(null == domain){
					// BASIC模式
					credentials = new UsernamePasswordCredentials(userName, password);
				}else {
					// NTLM认证模式
					credentials = new NTCredentials(userName, password, host, domain);
				}
				httpClient.getState().setProxyCredentials(AuthScope.ANY, credentials);
			}
		}
		
		return httpClient;
	}
	
}
