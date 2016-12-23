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
import cn.kanmars.sn.entity.TblSysuserInfo;
import cn.kanmars.sn.dao.TblSysuserInfoMapper;
/**
 * 系统用户表
 * tbl_sysuser_info
 */
@Service
@Transactional
public class SysuserInfoLogicImpl implements cn.kanmars.sn.logic.SysuserInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("SysuserInfoLogicImpl");

    @Autowired
    private TblSysuserInfoMapper tblSysuserInfoMapper;

    /*
     * 查询信息
     */
    public TblSysuserInfo querySysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
        logger.debug("querySysuserInfo:start");
        logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
        TblSysuserInfo result = tblSysuserInfoMapper.select(tblSysuserInfo);
        logger.debug("查询结果:tblSysuserInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysuserInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
        logger.debug("insertSysuserInfo:start");
        logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
        int insertNum = tblSysuserInfoMapper.insert(tblSysuserInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysuserInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
        logger.debug("updateSysuserInfo:start");
        logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
        int updateNum = tblSysuserInfoMapper.updateByPrimaryKey(tblSysuserInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysuserInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysuserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
        logger.debug("deleteSysuserInfo:start");
        logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
        int deleteNum = tblSysuserInfoMapper.delete(tblSysuserInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysuserInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysuserInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysuserInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysuserInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysuserInfo:end");
        return result;
    }
}

