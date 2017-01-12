/**
 * SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblOperationLog;
/**
 * 操作日志表
 * tbl_operation_log
 */
public interface OperationLogLogic {

    /*
     * 查询信息
     */
    public TblOperationLog queryOperationLog(TblOperationLog tblOperationLog) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertOperationLog(TblOperationLog tblOperationLog) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateOperationLog(TblOperationLog tblOperationLog) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteOperationLog(TblOperationLog tblOperationLog) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageOperationLog(HashMap paramMap) throws Exception;

}

