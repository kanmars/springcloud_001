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
import cn.kanmars.sn.entity.TblDemoInfo;
import cn.kanmars.sn.dao.TblDemoInfoMapper;
/**
 * DEMO表信息演示
 * tbl_demo_info
 */
@Service
@Transactional
public class DemoInfoLogicImpl implements cn.kanmars.sn.logic.DemoInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("DemoInfoLogicImpl");

    @Autowired
    private TblDemoInfoMapper tblDemoInfoMapper;

    /*
     * 查询信息
     */
    public TblDemoInfo queryDemoInfo(TblDemoInfo tblDemoInfo) throws Exception {
        logger.debug("queryDemoInfo:start");
        logger.debug("传入参数:tblDemoInfo:" + (tblDemoInfo != null ? JSONObject.fromObject(tblDemoInfo).toString() : "null"));
        TblDemoInfo result = tblDemoInfoMapper.select(tblDemoInfo);
        logger.debug("查询结果:tblDemoInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("queryDemoInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertDemoInfo(TblDemoInfo tblDemoInfo) throws Exception {
        logger.debug("insertDemoInfo:start");
        logger.debug("传入参数:tblDemoInfo:" + (tblDemoInfo != null ? JSONObject.fromObject(tblDemoInfo).toString() : "null"));
        int insertNum = tblDemoInfoMapper.insert(tblDemoInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertDemoInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateDemoInfo(TblDemoInfo tblDemoInfo) throws Exception {
        logger.debug("updateDemoInfo:start");
        logger.debug("传入参数:tblDemoInfo:" + (tblDemoInfo != null ? JSONObject.fromObject(tblDemoInfo).toString() : "null"));
        int updateNum = tblDemoInfoMapper.updateByPrimaryKey(tblDemoInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateDemoInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteDemoInfo(TblDemoInfo tblDemoInfo) throws Exception {
        logger.debug("deleteDemoInfo:start");
        logger.debug("传入参数:tblDemoInfo:" + (tblDemoInfo != null ? JSONObject.fromObject(tblDemoInfo).toString() : "null"));
        int deleteNum = tblDemoInfoMapper.delete(tblDemoInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteDemoInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageDemoInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageDemoInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblDemoInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageDemoInfo:end");
        return result;
    }
}

