package cn.com.xcommon.frame.interceptor;

import cn.com.xcommon.common.security.base64.Base64Util;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import net.sf.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.*;

/**
 * @ClassName: UserLoginBean
 * @Description: 用户登录信息bean
 * @date 2015年2月28日 上午9:45:59
 */
public class UserLoginBean implements Serializable {

	private static final long serialVersionUID = 1047625413715279856L;

	protected static HLogger logger = LoggerManager.getLoger("UserLoginBean");
	private static final String default_charSet = "UTF-8";

	/** 用户Id */
	private String userId;

	/** 登录账户 */
	private String userAccount;

	/** 呢称 */
	private String userName;

	/** 真实姓名 */
	private String trueName;

	/** 菜单列表 */
	private String menulist;

	/** 其他信息 */
	private HashMap<String,String> othInfo = new LinkedHashMap<String, String>();

	public UserLoginBean() {
		super();
	}

	/**
	 * <p>
	 * Title: UserLoginBean
	 * </p>
	 * <p>
	 * Description: 用户登录信息实例化构造函数
	 * </p>
	 * 
	 * @param userId 用户Id
	 * @param userAccount 登录账户
	 * @param userName 呢称
	 * @param trueName 真实姓名
	 */
	public UserLoginBean(String userId, String userAccount, String userName, String trueName) {
		super();
		this.userId = userId;
		this.userAccount = userAccount;
		this.userName = userName;
		this.trueName = trueName;
	}


	public UserLoginBean(String userId, String userAccount, String userName, String trueName, String menulist, HashMap<String, String> othInfo) {
		this.userId = userId;
		this.userAccount = userAccount;
		this.userName = userName;
		this.trueName = trueName;
		this.menulist = menulist;
		this.othInfo = othInfo;
	}

	/** 登录账户 */
	public String getUserAccount() {
		return userAccount;
	}

	/** 登录账户 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/** 用户Id */
	public String getUserId() {
		return userId;
	}

	/** 用户Id */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 呢称 */
	public String getUserName() {
		return userName;
	}

	/** 呢称 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 真实姓名 */
	public String getTrueName() {
		return trueName;
	}

	/** 真实姓名 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/** 获取菜单列表 */
	public String getMenulist() {
		return menulist;
	}

	/** 设置菜单列表 */
	public void setMenulist(String menulist) {
		this.menulist = menulist;
	}

	public HashMap<String, String> getOthInfo() {
		return othInfo;
	}

	public void setOthInfo(HashMap<String, String> othInfo) {
		this.othInfo = othInfo;
	}

	/** 绑定信息 */
	public void attachInfo(String key,String value){
		othInfo.put(key,value);
	}

	/** 解绑信息 */
	public void unattachInfo(String key){
		othInfo.remove(key);
	}

	public static class CookiesUtils {
		public static final String key = "ADVANCE_USER_LOGIN_BEAN";
		public static final String charset = "UTF-8";
		public static final Integer cookieOverdue = 3600;

		public CookiesUtils() {
		}

		public static boolean addCookie(UserLoginBean userLoginBean, HttpServletResponse response) {
			JSONObject jsonObject = JSONObject.fromObject(userLoginBean);
			jsonObject.remove("menulist");
			//压缩
			String str = ZipUtils.gzip(jsonObject.toString());
			Cookie cookie = new Cookie(key, str);
			cookie.setPath("/");
			cookie.setMaxAge(cookieOverdue);
			response.addCookie(cookie);
			return true;
		}

		public static UserLoginBean getCookie(HttpServletRequest request, HttpServletResponse response) {
			try {
				Cookie[] e = request.getCookies();
				if(e != null) {
					Cookie userCookie = null;
					Cookie[] value = e;
					int jSONObject = e.length;

					for(int userLoginBean = 0; userLoginBean < jSONObject; ++userLoginBean) {
						Cookie cookie = value[userLoginBean];
						if(cookie.getName().equals(key)) {
							userCookie = cookie;
							if(request.getServerName().equals(cookie.getDomain())) {
								break;
							}
						}
					}

					if(userCookie != null) {
						//解压
						String var9 = ZipUtils.gunzip(userCookie.getValue());
						userCookie.setPath("/");
						userCookie.setMaxAge(cookieOverdue);
						response.addCookie(userCookie);
						JSONObject var10 = JSONObject.fromObject(var9);
						UserLoginBean var11 = (UserLoginBean)JSONObject.toBean(var10, UserLoginBean.class);
						return var11;
					}
				}
			} catch (Exception var8) {
				logger.error("获取用户信息异常",var8);
			}

			return null;
		}

		public static boolean delCookie(HttpServletRequest request, HttpServletResponse response) {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				Cookie userCookie = null;
				Cookie[] arr$ = cookies;
				int len$ = cookies.length;

				for(int i$ = 0; i$ < len$; ++i$) {
					Cookie cookie = arr$[i$];
					if(cookie.getName().equals(key)) {
						userCookie = cookie;
						if(request.getServerName().equals(cookie.getDomain())) {
							break;
						}
					}
				}

				if(userCookie != null) {
					userCookie.setPath("/");
					userCookie.setMaxAge(0);
					response.addCookie(userCookie);
				}
			}
			return false;
		}
	}

	public static class ZipUtils {

		/**

		 * 使用gzip进行压缩
		 */
		public static String gzip(String primStr) {
			if (primStr == null || primStr.length() == 0) {
				return primStr;
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			GZIPOutputStream gzip=null;
			try {
				gzip = new GZIPOutputStream(out);
				gzip.write(primStr.getBytes(default_charSet));
			} catch (IOException e) {
				logger.error("压缩异常", e);
			}finally{
				if(gzip!=null){
					try {
						gzip.close();
					} catch (IOException e) {
						logger.error("压缩异常", e);
					}
				}
			}


			return Base64Util.encodeMessage(out.toByteArray());
		}

		/**
		 *
		 * <p>Description:使用gzip进行解压缩</p>
		 * @param compressedStr
		 * @return
		 */
		public static String gunzip(String compressedStr){
			if(compressedStr==null){
				return null;
			}

			ByteArrayOutputStream out= new ByteArrayOutputStream();
			ByteArrayInputStream in=null;
			GZIPInputStream ginzip=null;
			byte[] compressed=null;
			String decompressed = null;
			try {
				compressed = Base64Util.decodeMessage(compressedStr);
				in=new ByteArrayInputStream(compressed);
				ginzip=new GZIPInputStream(in);

				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = ginzip.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed=new String(out.toByteArray(),default_charSet);
			} catch (IOException e) {
				logger.error("解压异常", e);
			} finally {
				if (ginzip != null) {
					try {
						ginzip.close();
					} catch (IOException e) {
						logger.error("关闭ginzip异常", e);
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("关闭in异常", e);
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error("关闭out异常", e);
					}
				}
			}

			return decompressed;
		}

		/**
		 * 使用zip进行压缩
		 * @param str 压缩前的文本
		 * @return 返回压缩后的文本
		 */
		public static final String zip(String str) {
			if (str == null)
				return null;
			byte[] compressed;
			ByteArrayOutputStream out = null;
			ZipOutputStream zout = null;
			String compressedStr = null;
			try {
				out = new ByteArrayOutputStream();
				zout = new ZipOutputStream(out);
				zout.setLevel(Deflater.BEST_COMPRESSION);
				zout.putNextEntry(new ZipEntry("0"));
				zout.write(str.getBytes(default_charSet));
				zout.closeEntry();
				compressed = out.toByteArray();
				compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
			} catch (IOException e) {
				compressed = null;
				logger.error("IOException", e);
			} finally {
				if (zout != null) {
					try {
						zout.close();
					} catch (IOException e) {
						logger.error("关闭zout异常", e);
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error("关闭out异常", e);
					}
				}
			}
			return compressedStr;
		}

		/**
		 * 使用zip进行解压缩
		 * @param compressedStr 压缩后的文本
		 * @return 解压后的字符串
		 */
		public static final String unzip(String compressedStr) {
			if (compressedStr == null) {
				return null;
			}

			ByteArrayOutputStream out = null;
			ByteArrayInputStream in = null;
			ZipInputStream zin = null;
			String decompressed = null;
			try {
				byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
				out = new ByteArrayOutputStream();
				in = new ByteArrayInputStream(compressed);
				zin = new ZipInputStream(in);
				zin.getNextEntry();
				byte[] buffer = new byte[1024];
				int offset = -1;
				while ((offset = zin.read(buffer)) != -1) {
					out.write(buffer, 0, offset);
				}
				decompressed = new String(out.toByteArray(),default_charSet);
			} catch (IOException e) {
				decompressed = null;
				logger.error("IOException", e);
			} finally {
				if (zin != null) {
					try {
						zin.close();
					} catch (IOException e) {
						logger.error("关闭zin异常", e);
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("关闭in异常", e);
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error("关闭out异常", e);
					}
				}
			}
			return decompressed;
		}
	}
}
