package cn.com.sn.frame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: FluxAttackFilter
 * @Description: 流量攻击过虑器
 */
public class FluxAttackFilter implements Filter {

	private static final Log logger = LogFactory.getLog("SqlAbnormalFilter");

	private String sendRedirect;

//	private CacheService cacheService;

	public void init(FilterConfig filterConfig) throws ServletException {
		sendRedirect = filterConfig.getInitParameter("sendRedirect");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
//		try {
//			if (cacheService == null)
//				cacheService = (CacheService) SpringBeanFactory.getBean("cacheService");
//			HttpServletRequest httpRequest = (HttpServletRequest) request;
//			String ipListStr = cacheService.get("PIAOJU", "bill_monitor");
//			String clientIp = getClientIp(httpRequest);
//			logger.info("异常IP：" + ipListStr + ",当前客户端IP：" + clientIp);
//		} catch (Exception e) {
//			logger.error("获取redis缓存服务异常：", e);
//		}
		chain.doFilter(request, response);
	}

	private String getClientIp(HttpServletRequest httpRequest) {
		if (httpRequest.getHeader("x-forwarded-for") == null) {
			return httpRequest.getRemoteAddr();
		}
		return httpRequest.getHeader("x-forwarded-for");
	}

	public void destroy() {

	}

}
