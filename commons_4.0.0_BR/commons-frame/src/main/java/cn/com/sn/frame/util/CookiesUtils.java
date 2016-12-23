package cn.com.sn.frame.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.com.sn.common.security.base64.Base64Util;
import cn.com.sn.frame.cache.ApplicationCache;
import cn.com.sn.frame.interceptor.UserLoginBean;

/**
 * @ClassName: CookiesUtils
 * @Description: cookie添加、删除、查询操作类
 * @date 2015年2月26日 下午3:06:47
 */
public class CookiesUtils {

	public static String key = "finance_user";

	public static String charset = "UTF-8";

	public static String USER_LOGIN_BEAN = "userLoginBean";

	public static int cookieOverdue = ApplicationCache.getInstance().getInt("cookieOverdue");

	public static boolean addCookie(UserLoginBean userLoginBean, HttpServletResponse response) {
		try {
			JSONObject json = JSONObject.fromObject(userLoginBean);
			String content = Base64Util.encodeMessage(json.toString().getBytes(charset));
			Cookie cookie = new Cookie(key, content);
			cookie.setPath("/");
			cookie.setMaxAge(cookieOverdue);
			response.addCookie(cookie);
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static UserLoginBean getCookie(HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				Cookie userCookie = null;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(key)) {
						userCookie = cookie;
						if (request.getServerName().equals(cookie.getDomain())) {
							break;
						}
					}
				}
				if (userCookie != null) {
					String value = userCookie.getValue();
					value = new String(Base64Util.decodeMessage(value), charset);
					userCookie.setPath("/");
					userCookie.setMaxAge(cookieOverdue);
					response.addCookie(userCookie);
					JSONObject jSONObject = JSONObject.fromObject(value);
					UserLoginBean userLoginBean = (UserLoginBean) JSONObject.toBean(jSONObject, UserLoginBean.class);
					return userLoginBean;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Cookie userCookie = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
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
		return false;
	}

}
