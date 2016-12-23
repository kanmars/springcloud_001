/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import java.util.List;

import cn.kanmars.sn.entity.TblSysmenuInfo;
/**
 * 系统菜单表
 * tbl_sysmenu_info
 */
public interface SysmenuInfoLogic {

    /*
     * 查询信息
     */
    public TblSysmenuInfo querySysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysmenuInfo(HashMap paramMap) throws Exception;

    /*
     * 查询信息queryList
     */
    public List<TblSysmenuInfo> queryListSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception;

}

