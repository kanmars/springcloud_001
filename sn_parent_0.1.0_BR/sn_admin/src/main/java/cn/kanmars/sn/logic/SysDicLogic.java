/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysDic;
/**
 * 系统字典表
 * tbl_sys_dic
 */
public interface SysDicLogic {

    /*
     * 查询信息
     */
    public TblSysDic querySysDic(TblSysDic tblSysDic) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysDic(TblSysDic tblSysDic) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysDic(TblSysDic tblSysDic) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysDic(TblSysDic tblSysDic) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysDic(HashMap paramMap) throws Exception;

}

