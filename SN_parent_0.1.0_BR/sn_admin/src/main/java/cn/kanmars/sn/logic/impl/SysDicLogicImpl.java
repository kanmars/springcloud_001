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
import cn.kanmars.sn.entity.TblSysDic;
import cn.kanmars.sn.dao.TblSysDicMapper;
/**
 * 系统字典表
 * tbl_sys_dic
 */
@Service
@Transactional
public class SysDicLogicImpl implements cn.kanmars.sn.logic.SysDicLogic{

    protected HLogger logger = LoggerManager.getLoger("SysDicLogicImpl");

    @Autowired
    private TblSysDicMapper tblSysDicMapper;

    /*
     * 查询信息
     */
    public TblSysDic querySysDic(TblSysDic tblSysDic) throws Exception {
        logger.debug("querySysDic:start");
        logger.debug("传入参数:tblSysDic:" + (tblSysDic != null ? JSONObject.fromObject(tblSysDic).toString() : "null"));
        TblSysDic result = tblSysDicMapper.select(tblSysDic);
        logger.debug("查询结果:tblSysDic:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysDic:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysDic(TblSysDic tblSysDic) throws Exception {
        logger.debug("insertSysDic:start");
        logger.debug("传入参数:tblSysDic:" + (tblSysDic != null ? JSONObject.fromObject(tblSysDic).toString() : "null"));
        int insertNum = tblSysDicMapper.insert(tblSysDic);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysDic:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysDic(TblSysDic tblSysDic) throws Exception {
        logger.debug("updateSysDic:start");
        logger.debug("传入参数:tblSysDic:" + (tblSysDic != null ? JSONObject.fromObject(tblSysDic).toString() : "null"));
        int updateNum = tblSysDicMapper.updateByPrimaryKey(tblSysDic);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysDic:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysDic(TblSysDic tblSysDic) throws Exception {
        logger.debug("deleteSysDic:start");
        logger.debug("传入参数:tblSysDic:" + (tblSysDic != null ? JSONObject.fromObject(tblSysDic).toString() : "null"));
        int deleteNum = tblSysDicMapper.delete(tblSysDic);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysDic:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysDic(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysDic:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysDicMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysDic:end");
        return result;
    }
}

