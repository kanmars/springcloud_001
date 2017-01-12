package cn.com.xcommon.frame.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: SqlAttackFilter
 * @Description: 防止SQL注入的过滤器
 * @date 2015年2月6日 下午8:02:47
 */
public class SqlAttackFilter implements Filter {

	protected HLogger logger = LoggerManager.getLoger("SqlAttackFilter");

	private String rules = "[^%'=]{1,}";

	private String sendRedirect;

	private String messages = "请求参数中包含特殊字符！";

	public void init(FilterConfig filterConfig) throws ServletException {
		sendRedirect = filterConfig.getInitParameter("sendRedirect");
		if(StringUtils.isNotEmpty(filterConfig.getInitParameter("rules"))){
			rules = filterConfig.getInitParameter("rules");
		}
		if(StringUtils.isNotEmpty(filterConfig.getInitParameter("messages"))){
			messages = filterConfig.getInitParameter("messages");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpRequest.setAttribute("basePath",
					httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort() + httpRequest.getContextPath() + "/");
			Map<String, String[]> reqMap = request.getParameterMap();
			Iterator<Entry<String, String[]>> inter = reqMap.entrySet().iterator();
			String[] value = null;
			Matcher m = null;
			Pattern p = Pattern.compile(rules);
			Entry<String, String[]> entry = null;
			while (inter.hasNext()) {
				entry = inter.next();
				value = entry.getValue();
				if (value != null && value.length > 0 && StringUtils.isNotEmpty(value[0])) {
					m = p.matcher(value[0]);
					if (!m.matches()) {
						String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + httpRequest.getContextPath() + "/";
						logger.info("############请求包含特殊字符，value=" + value[0]);
						httpResponse.sendRedirect(contextPath + sendRedirect + "?messages=" + URLEncoder.encode(messages, "UTF-8"));
						return;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取redis缓存服务异常：", e);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
