/**  可靠的 http client 
* @Title: HttpClientTools.java 
* @Package cn.com.sn.commons.http 
* @Description: httpclient工具 
* @author zoufangfang   
* @date 2015-3-11 下午3:22:55 
* @company cn.com.sn
* @version V1.0   
*/ 


package cn.com.sn.commons.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.RequestEntity;

import cn.com.sn.commons.http.util.HttpClientService;
import cn.com.sn.commons.http.util.HttpRequest;
import cn.com.sn.commons.http.util.HttpRequestMethodType;
import cn.com.sn.commons.http.util.HttpResponse;
import cn.com.sn.commons.http.util.HttpResponseStatusException;
import cn.com.sn.commons.http.util.HttpResponseType;

/** 
 * @ClassName: HttpClientTools 
 * @Description: httpclient工具 
 * @author zoufangfang 
 * @date 2015-3-11 下午3:22:55  
 */
public class HttpClientTools {
	

	/**
	 * Get请求
	 * @param httpClient 为空,将创建默认的 httpClient
	 * @param url
	 * @param data
	 * @param headerMap
	 * @param responseDataType 响应数据获取方式 { 0=String ; 1=byte[] } 
	 * @return
	 * @throws Exception
	 */
	public static Object get(HttpClient httpClient ,String url ,Map<String ,String> data ,Map<String ,String> headerMap ,int responseDataType) 
			throws Exception {
		Object responseData = null;
		
		//HttpRequest
		HttpRequest request = new HttpRequest(url);
		request.setHttpclient(httpClient);
		request.setMethod(HttpRequestMethodType.GET);
		request.setParameters(data);
		request.getRequestParams(true).setHeaderParams(headerMap);
		request.setResponseType(responseDataType == 0 ? HttpResponseType.STRING : HttpResponseType.BYTES);
		
		//get
		HttpResponse httpResponse = HttpClientService.execute(request);
		
		responseData = httpResponse.getResponseBodyString();
		
		return responseData;
	}
	
	/**
	 * Post请求
	 * @param httpClient 为空,将创建默认的 httpClient
	 * @param url
	 * @param paramMap
	 * @param headerMap
	 * @param responseDataType 响应数据获取方式 { 0=String ; 1=byte[] }
	 * @return
	 * @throws HttpResponseStatusException 响应异常
	 * @throws Exception
	 */
	public static Object post(HttpClient httpClient, String url, Map<String, String> paramMap, Map<String, String> headerMap, int responseDataType)
			throws Exception {

		Object responseData = post(httpClient, url, paramMap, null, headerMap, responseDataType);

		return responseData;
	}

	/**
	 * Post请求
	 * 
	 * @param httpClient
	 *            为空,将创建默认的 httpClient
	 * @param url
	 * @param headerMap
	 * @param requestEntity
	 * @param paramMap
	 * @param responseDataType
	 *            响应数据获取方式 { 0=String ; 1=byte[] }
	 * @return
	 * @throws HttpResponseStatusException
	 *             响应异常
	 * @throws Exception
	 */
	public static Object post(HttpClient httpClient, String url, Map<String, String> paramMap, RequestEntity requestEntity, Map<String, String> headerMap,
			int responseDataType) throws  Exception {

		Object responseData = null;
		
		//HttpRequest
		HttpRequest request = new HttpRequest(url);
		request.setHttpclient(httpClient);
		request.setParameters(paramMap);
		request.getRequestParams(true).setHeaderParams(headerMap);
		request.setRequestEntity(requestEntity);
		request.setResponseType(responseDataType == 0 ? HttpResponseType.STRING : HttpResponseType.BYTES);
		
		//post
		HttpResponse httpResponse = HttpClientService.execute(request);
		
		//result
		responseData = httpResponse.getResponseBodyString();
		
		return responseData;
	}

}
