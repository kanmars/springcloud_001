/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysroleInfo;
/**
 * 系统角色表
 * tbl_sysrole_info
 */
public interface SysroleInfoLogic {

    /*
     * 查询信息
     */
    public TblSysroleInfo querySysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysroleInfo(HashMap paramMap) throws Exception;

}

