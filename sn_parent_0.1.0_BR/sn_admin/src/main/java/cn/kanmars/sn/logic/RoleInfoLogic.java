package cn.kanmars.sn.logic;

import cn.kanmars.sn.entity.TblSysroleInfo;

import java.util.HashMap;


public interface RoleInfoLogic {
	
	/*
	 * 查询角色信息
	 */
	public TblSysroleInfo queryRoleInfo(TblSysroleInfo tblSysroleInfo)throws Exception;
	
	/*
	 * 新增角色
	 * 
	 */
	public Integer insertRoleInfo(TblSysroleInfo tblSysroleInfo)throws Exception;
	/*
	 * 修改角色
	 * 
	 */
	public Integer updateRoleInfo(TblSysroleInfo tblSysroleInfo)throws Exception;
	/*
	 * 删除角色
	 * 
	 */
	public Integer deleteRoleInfo(TblSysroleInfo tblSysroleInfo)throws Exception;
	
	/*
	 * 查询list--page
	 * 
	 */
	public HashMap queryPageRoleInfo(HashMap paramMap)throws Exception;

}
