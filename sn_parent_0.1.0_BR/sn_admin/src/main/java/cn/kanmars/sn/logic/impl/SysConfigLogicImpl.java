/**
 * Gome SN Generator 
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
import cn.kanmars.sn.entity.TblSysConfig;
import cn.kanmars.sn.dao.TblSysConfigMapper;
/**
 * 系统配置表
 * tbl_sys_config
 */
@Service
@Transactional
public class SysConfigLogicImpl implements cn.kanmars.sn.logic.SysConfigLogic{

    protected HLogger logger = LoggerManager.getLoger("SysConfigLogicImpl");

    @Autowired
    private TblSysConfigMapper tblSysConfigMapper;

    /*
     * 查询信息
     */
    public TblSysConfig querySysConfig(TblSysConfig tblSysConfig) throws Exception {
        logger.debug("querySysConfig:start");
        logger.debug("传入参数:tblSysConfig:" + (tblSysConfig != null ? JSONObject.fromObject(tblSysConfig).toString() : "null"));
        TblSysConfig result = tblSysConfigMapper.select(tblSysConfig);
        logger.debug("查询结果:tblSysConfig:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysConfig:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysConfig(TblSysConfig tblSysConfig) throws Exception {
        logger.debug("insertSysConfig:start");
        logger.debug("传入参数:tblSysConfig:" + (tblSysConfig != null ? JSONObject.fromObject(tblSysConfig).toString() : "null"));
        int insertNum = tblSysConfigMapper.insert(tblSysConfig);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysConfig:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysConfig(TblSysConfig tblSysConfig) throws Exception {
        logger.debug("updateSysConfig:start");
        logger.debug("传入参数:tblSysConfig:" + (tblSysConfig != null ? JSONObject.fromObject(tblSysConfig).toString() : "null"));
        int updateNum = tblSysConfigMapper.updateByPrimaryKey(tblSysConfig);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysConfig:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysConfig(TblSysConfig tblSysConfig) throws Exception {
        logger.debug("deleteSysConfig:start");
        logger.debug("传入参数:tblSysConfig:" + (tblSysConfig != null ? JSONObject.fromObject(tblSysConfig).toString() : "null"));
        int deleteNum = tblSysConfigMapper.delete(tblSysConfig);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysConfig:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysConfig(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysConfig:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysConfigMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysConfig:end");
        return result;
    }
}

