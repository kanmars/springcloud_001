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
import cn.kanmars.sn.entity.TblTaskBasicConfig;
import cn.kanmars.sn.dao.TblTaskBasicConfigMapper;
/**
 * 任务基本信息配置表
 * tbl_task_basic_config
 */
@Service
@Transactional
public class TaskBasicConfigLogicImpl implements cn.kanmars.sn.logic.TaskBasicConfigLogic{

    protected HLogger logger = LoggerManager.getLoger("TaskBasicConfigLogicImpl");

    @Autowired
    private TblTaskBasicConfigMapper tblTaskBasicConfigMapper;

    /*
     * 查询信息
     */
    public TblTaskBasicConfig queryTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception {
        logger.debug("queryTaskBasicConfig:start");
        logger.debug("传入参数:tblTaskBasicConfig:" + (tblTaskBasicConfig != null ? JSONObject.fromObject(tblTaskBasicConfig).toString() : "null"));
        TblTaskBasicConfig result = tblTaskBasicConfigMapper.select(tblTaskBasicConfig);
        logger.debug("查询结果:tblTaskBasicConfig:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("queryTaskBasicConfig:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception {
        logger.debug("insertTaskBasicConfig:start");
        logger.debug("传入参数:tblTaskBasicConfig:" + (tblTaskBasicConfig != null ? JSONObject.fromObject(tblTaskBasicConfig).toString() : "null"));
        int insertNum = tblTaskBasicConfigMapper.insert(tblTaskBasicConfig);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertTaskBasicConfig:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception {
        logger.debug("updateTaskBasicConfig:start");
        logger.debug("传入参数:tblTaskBasicConfig:" + (tblTaskBasicConfig != null ? JSONObject.fromObject(tblTaskBasicConfig).toString() : "null"));
        int updateNum = tblTaskBasicConfigMapper.updateByPrimaryKey(tblTaskBasicConfig);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateTaskBasicConfig:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception {
        logger.debug("deleteTaskBasicConfig:start");
        logger.debug("传入参数:tblTaskBasicConfig:" + (tblTaskBasicConfig != null ? JSONObject.fromObject(tblTaskBasicConfig).toString() : "null"));
        int deleteNum = tblTaskBasicConfigMapper.delete(tblTaskBasicConfig);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteTaskBasicConfig:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageTaskBasicConfig(HashMap paramMap) throws Exception {
        logger.debug("queryPageTaskBasicConfig:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblTaskBasicConfigMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageTaskBasicConfig:end");
        return result;
    }
}

