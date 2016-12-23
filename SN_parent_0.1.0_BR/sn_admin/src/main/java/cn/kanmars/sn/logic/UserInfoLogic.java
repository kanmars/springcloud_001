package cn.kanmars.sn.logic;

import cn.kanmars.sn.entity.TblSysuserInfo;

import java.util.HashMap;
import java.util.List;

public interface UserInfoLogic {
	
	/*
	 * 查询用户信息 登录
	 */
	public TblSysuserInfo queryUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;
	
	/*
	 * 新增用户
	 * 
	 */
	public Integer insertUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;
	/*
	 * 修改用户
	 * 
	 */
	public Integer updateUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;
	/*
	 * 删除用户
	 * 
	 */
	public Integer deleteUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;
	
	/*
	 * 查询list--page
	 * 
	 */
	public HashMap queryPageUserInfo(HashMap paramMap) throws Exception;

}
