package cn.com.xcommon.frame.interceptor;

import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.xcommon.frame.util.PropertiesUtils;

/**
 * @ClassName: BeanFieldInterceptor
 * @Description: 防止SQL注入的过虑器
 * @date 2015年2月4日 下午3:44:47
 */
public class BeanFieldInterceptor implements HandlerInterceptor {

	protected HLogger logger = LoggerManager.getLoger("BeanFieldInterceptor");

	private String rules = "[^%'=]{1,}";

	private String sendRedirect;

	private String messages = "请求参数中包含特殊字符！";

	private String configpathname;

	private boolean page_static_info = false;

	/*
	 * (非 Javadoc) <p>Title: preHandle</p> <p>Description: </p>
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("配置文件加载到session中");
		ServletContext sc = request.getServletContext();
		if (!page_static_info || sc.getAttribute("page_static_info") == null) {
			if(StringUtils.isNotEmpty(configpathname)){
				Properties p = PropertiesUtils.getProperties(configpathname);
				for (Entry<Object, Object> e : p.entrySet()) {
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					sc.setAttribute(key, value);
				}
			}
			sc.setAttribute("page_static_info", "true");
			page_static_info = true;
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: postHandle</p> <p>Description: </p>
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("############postHandle");
	}

	/*
	 * (非 Javadoc) <p>Title: afterCompletion</p> <p>Description: </p>
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex 21.75 X 19
	 * @throws Exception
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getSendRedirect() {
		return sendRedirect;
	}

	public void setSendRedirect(String sendRedirect) {
		this.sendRedirect = sendRedirect;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getConfigpathname() {
		return configpathname;
	}

	public void setConfigpathname(String configpathname) {
		this.configpathname = configpathname;
	}

}
