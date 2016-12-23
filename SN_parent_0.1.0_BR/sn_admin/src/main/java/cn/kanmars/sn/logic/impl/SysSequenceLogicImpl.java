/**
 * SN Generator
 */
package cn.kanmars.sn.logic.impl;


import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.util.PageQueryUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysSequence;
import cn.kanmars.sn.dao.TblSysSequenceMapper;
/**
 * 系统ID表
 * tbl_sys_sequence
 */
@Service
@Transactional
public class SysSequenceLogicImpl implements cn.kanmars.sn.logic.SysSequenceLogic{

    protected HLogger logger = LoggerManager.getLoger("SysSequenceLogicImpl");

    @Autowired
    private TblSysSequenceMapper tblSysSequenceMapper;

    /*
     * 查询信息
     */
    public TblSysSequence querySysSequence(TblSysSequence tblSysSequence) throws Exception {
        logger.debug("querySysSequence:start");
        logger.debug("传入参数:tblSysSequence:" + (tblSysSequence != null ? JSONObject.fromObject(tblSysSequence).toString() : "null"));
        TblSysSequence result = tblSysSequenceMapper.select(tblSysSequence);
        logger.debug("查询结果:tblSysSequence:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysSequence:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysSequence(TblSysSequence tblSysSequence) throws Exception {
        logger.debug("insertSysSequence:start");
        logger.debug("传入参数:tblSysSequence:" + (tblSysSequence != null ? JSONObject.fromObject(tblSysSequence).toString() : "null"));
        int insertNum = tblSysSequenceMapper.insert(tblSysSequence);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysSequence:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysSequence(TblSysSequence tblSysSequence) throws Exception {
        logger.debug("updateSysSequence:start");
        logger.debug("传入参数:tblSysSequence:" + (tblSysSequence != null ? JSONObject.fromObject(tblSysSequence).toString() : "null"));
        int updateNum = tblSysSequenceMapper.updateByPrimaryKey(tblSysSequence);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysSequence:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysSequence(TblSysSequence tblSysSequence) throws Exception {
        logger.debug("deleteSysSequence:start");
        logger.debug("传入参数:tblSysSequence:" + (tblSysSequence != null ? JSONObject.fromObject(tblSysSequence).toString() : "null"));
        int deleteNum = tblSysSequenceMapper.delete(tblSysSequence);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysSequence:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysSequence(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysSequence:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysSequenceMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysSequence:end");
        return result;
    }
}

