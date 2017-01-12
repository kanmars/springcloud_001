/**
 * SN Generator 
 */
package cn.kanmars.sn.logic.impl;


import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.util.PageQueryUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import cn.kanmars.sn.entity.TblOperationLog;
import cn.kanmars.sn.dao.TblOperationLogMapper;
/**
 * 操作日志表
 * tbl_operation_log
 */
@Service
@Transactional
public class OperationLogLogicImpl implements cn.kanmars.sn.logic.OperationLogLogic{

    protected HLogger logger = LoggerManager.getLoger("OperationLogLogicImpl");

    @Autowired
    private TblOperationLogMapper tblOperationLogMapper;

    /*
     * 查询信息
     */
    public TblOperationLog queryOperationLog(TblOperationLog tblOperationLog) throws Exception {
        logger.debug("queryOperationLog:start");
        logger.debug("传入参数:tblOperationLog:" + (tblOperationLog != null ? JSONObject.fromObject(tblOperationLog).toString() : "null"));
        TblOperationLog result = tblOperationLogMapper.select(tblOperationLog);
        logger.debug("查询结果:tblOperationLog:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("queryOperationLog:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertOperationLog(TblOperationLog tblOperationLog) throws Exception {
        logger.debug("insertOperationLog:start");
        logger.debug("传入参数:tblOperationLog:" + (tblOperationLog != null ? JSONObject.fromObject(tblOperationLog).toString() : "null"));
        int insertNum = tblOperationLogMapper.insert(tblOperationLog);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertOperationLog:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateOperationLog(TblOperationLog tblOperationLog) throws Exception {
        logger.debug("updateOperationLog:start");
        logger.debug("传入参数:tblOperationLog:" + (tblOperationLog != null ? JSONObject.fromObject(tblOperationLog).toString() : "null"));
        int updateNum = tblOperationLogMapper.updateByPrimaryKey(tblOperationLog);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateOperationLog:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteOperationLog(TblOperationLog tblOperationLog) throws Exception {
        logger.debug("deleteOperationLog:start");
        logger.debug("传入参数:tblOperationLog:" + (tblOperationLog != null ? JSONObject.fromObject(tblOperationLog).toString() : "null"));
        int deleteNum = tblOperationLogMapper.delete(tblOperationLog);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteOperationLog:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageOperationLog(HashMap paramMap) throws Exception {
        logger.debug("queryPageOperationLog:start");
        logger.debug("传入参数:paramMap:" + paramMap);
        String startIndex_s = (String)paramMap.get("startIndex");
        String pageSize_s =  (String)paramMap.get("pageSize");
        int startIndex = 1;
        int pageSize = 10;
        try{
            startIndex = Integer.parseInt(startIndex_s);
            pageSize = Integer.parseInt(pageSize_s);
        }catch(Exception e){
            logger.error("startIndex和pageSize解析异常",e);
        }
        HashMap result = PageQueryUtil.pageQuery(tblOperationLogMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageOperationLog:end");
        return result;
    }
}

