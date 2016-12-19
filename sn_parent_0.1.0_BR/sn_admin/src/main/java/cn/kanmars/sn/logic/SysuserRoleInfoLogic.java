/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysuserRoleInfo;
/**
 * 系统用户角色绑定表
 * tbl_sysuser_role_info
 */
public interface SysuserRoleInfoLogic {

    /*
     * 查询信息
     */
    public TblSysuserRoleInfo querySysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysuserRoleInfo(HashMap paramMap) throws Exception;

}

