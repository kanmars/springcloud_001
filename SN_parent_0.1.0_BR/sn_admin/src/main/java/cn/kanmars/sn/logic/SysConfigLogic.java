/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysConfig;
/**
 * 系统配置表
 * tbl_sys_config
 */
public interface SysConfigLogic {

    /*
     * 查询信息
     */
    public TblSysConfig querySysConfig(TblSysConfig tblSysConfig) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysConfig(TblSysConfig tblSysConfig) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysConfig(TblSysConfig tblSysConfig) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysConfig(TblSysConfig tblSysConfig) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysConfig(HashMap paramMap) throws Exception;

}

