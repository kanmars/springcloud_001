package cn.com.xcommon.frame.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.com.xcommon.common.security.base64.Base64Util;
import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.interceptor.UserLoginBean;

/**
 * @ClassName: CookiesUtils
 * @Description: cookie添加、删除、查询操作类
 * @date 2015年2月26日 下午3:06:47
 */
public class CookiesUtils {

	public static String charset = "UTF-8";

	public static String USER_LOGIN_BEAN = "userLoginBean";

	public static int cookieOverdue = -1;

	static{
		String cookieOverdue_s = ApplicationCache.getInstance().getStr("cookieOverdue");
		if(StringUtils.isNotEmpty(cookieOverdue_s)){
			try {
				cookieOverdue = Integer.parseInt(cookieOverdue_s);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void addCookie(HttpServletRequest request,HttpServletResponse response,String name, String value) {
		Cookie cookies = new Cookie(name, value);
		cookies.setPath("/");
//      cookies.setMaxAge(-1);//设置cookie经过多长秒后被删除。如果0，就说明立即删除。如果是负数就表明当浏览器关闭时自动删除。
		cookies.setMaxAge(cookieOverdue);
		response.addCookie(cookies);
	}
	public String getCookieValue(HttpServletRequest request,HttpServletResponse response,String name) {
		if (StringUtils.isNotEmpty(name)) {
			Cookie cookie = getCookie(request,response,name);
			if(cookie!=null){
				return cookie.getValue();
			}
		}
		return "";
	}

	public static void delCookie(HttpServletRequest request, HttpServletResponse response,String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Cookie userCookie = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					userCookie = cookie;
					if (request.getServerName().equals(cookie.getDomain())) {
						break;
					}
				}
			}
			if (userCookie != null) {
				userCookie.setPath("/");
				userCookie.setMaxAge(0);
				response.addCookie(userCookie);
			}
		}
	}

	public Cookie getCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		try {
			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals(cookieName)) {
						return cookie;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookie;
	}

}
