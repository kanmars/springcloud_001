package cn.com.xcommon.commons.http.util;
/**
 * http 响应状态码 异常
 * @author fd_lili
 *
 */
public class HttpResponseStatusException extends Exception{
	private static final long serialVersionUID = 5133413318715718424L;
	
	private String uri;
	private String statusCode;
	private String responseBody;
	
	/**
	 * http 响应状态码 异常
	 * @param message
	 * @param cause
	 */
	public HttpResponseStatusException(String message, Throwable cause){
		super(message,cause);
	}
	/**
	 * http 响应状态码 异常
	 * @param uri
	 * @param statusCode
	 * @param responseBody
	 * @param message
	 * @param cause
	 */
	public HttpResponseStatusException(String uri ,String statusCode ,String responseBody , String message, Throwable cause){
		super(message,cause);
		this.uri = uri;
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}
	
	public String getMessage() {
		return this.getErrorMessage();
	}
	public String toString() {
		return this.getErrorMessage();
	}
	/**
	 * 响应状态码 信息
	 * @return
	 */
	public String getStatusMessage(){
		StringBuffer sb = new StringBuffer();
		sb.append("Http response status : {\"uri\":\"" + uri + "\"");
		sb.append(" ,\"statusCode\":" + statusCode + "\"}");
		return sb.toString();
	}
	
	private String getErrorMessage(){
		int respLen = (null == responseBody ? 0 : responseBody.length());
		int subLen = (respLen > 300 ? 300 : respLen);
		String suspensionPoints = (respLen > subLen ? " ... " : "");
		String nonNormalResponse = (null == responseBody ? null : responseBody.substring(0, subLen) + suspensionPoints);
		
		StringBuffer sb = new StringBuffer();
		sb.append("HttpResponseStatusException: {\"uri\":\"" + uri + "\"");
		sb.append(" ,\"statusCode\":" + statusCode );
		sb.append(" ,\"responseBody\":\"" + nonNormalResponse + "\"}");
		sb.append("\"}\n");
		sb.append(super.getMessage());
		return sb.toString();
	}

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
