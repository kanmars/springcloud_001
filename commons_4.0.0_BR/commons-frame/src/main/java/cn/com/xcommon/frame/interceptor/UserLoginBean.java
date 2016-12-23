package cn.com.xcommon.frame.interceptor;

import java.io.Serializable;

/**
 * @ClassName: UserLoginBean
 * @Description: 用户登录信息bean
 * @date 2015年2月28日 上午9:45:59
 */
public class UserLoginBean implements Serializable {

	private static final long serialVersionUID = 1047625413715279856L;

	/** 用户Id */
	private String userId;

	/** 登录账户 */
	private String userAccount;

	/** 呢称 */
	private String userName;

	/** 真实姓名 */
	private String trueName;

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

}
