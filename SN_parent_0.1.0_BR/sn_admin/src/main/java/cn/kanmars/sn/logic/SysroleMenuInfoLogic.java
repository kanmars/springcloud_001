/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysroleMenuInfo;
/**
 * 系统角色菜单绑定表
 * tbl_sysrole_menu_info
 */
public interface SysroleMenuInfoLogic {

    /*
     * 查询信息
     */
    public TblSysroleMenuInfo querySysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysroleMenuInfo(HashMap paramMap) throws Exception;

}

