/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysSequence;
/**
 * 系统ID表
 * tbl_sys_sequence
 */
public interface SysSequenceLogic {

    /*
     * 查询信息
     */
    public TblSysSequence querySysSequence(TblSysSequence tblSysSequence) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertSysSequence(TblSysSequence tblSysSequence) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateSysSequence(TblSysSequence tblSysSequence) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteSysSequence(TblSysSequence tblSysSequence) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysSequence(HashMap paramMap) throws Exception;

}

