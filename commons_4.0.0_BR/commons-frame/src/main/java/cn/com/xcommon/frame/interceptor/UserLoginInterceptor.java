package cn.com.xcommon.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.util.CookiesUtils;

/**
 * @ClassName: UserLoginInterceptor
 * @Description: 用户登录校验拦截器
 * @date 2015年2月26日 下午4:38:50
 */
public class UserLoginInterceptor implements HandlerInterceptor {

	private String[] allowUrls;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 非登录状态就可以访问的页央
		String uri = request.getRequestURI();
		for (String url : allowUrls) {
			if (uri.contains(url))
				return true;
		}

		UserLoginBean userLoginBean = CookiesUtils.getCookie(request, response);
		if (userLoginBean == null) {
			String goUrl = request.getRequestURI();
			goUrl = goUrl.replace(request.getContextPath(), "");
			if (!goUrl.startsWith("/")) {
				goUrl = "/" + goUrl;
			}
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + ApplicationCache.getInstance().getStr("loginUrl") + "?goUrl=" + goUrl;
			response.sendRedirect(basePath);
			return false;
		}
		request.setAttribute(CookiesUtils.USER_LOGIN_BEAN, userLoginBean);
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public String[] getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

}
