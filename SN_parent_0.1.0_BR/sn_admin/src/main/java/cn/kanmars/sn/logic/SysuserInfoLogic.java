/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysuserInfo;
/**
 * 系统用户表
 * tbl_sysuser_info
 */
public interface SysuserInfoLogic {

    /*
     * 查询信息
     */
    public TblSysuserInfo querySysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysuserInfo(HashMap paramMap) throws Exception;

}

