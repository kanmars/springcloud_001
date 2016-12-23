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
import cn.kanmars.sn.entity.TblSysroleInfo;
import cn.kanmars.sn.dao.TblSysroleInfoMapper;
/**
 * 系统角色表
 * tbl_sysrole_info
 */
@Service
@Transactional
public class SysroleInfoLogicImpl implements cn.kanmars.sn.logic.SysroleInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("SysroleInfoLogicImpl");

    @Autowired
    private TblSysroleInfoMapper tblSysroleInfoMapper;

    /*
     * 查询信息
     */
    public TblSysroleInfo querySysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception {
        logger.debug("querySysroleInfo:start");
        logger.debug("传入参数:tblSysroleInfo:" + (tblSysroleInfo != null ? JSONObject.fromObject(tblSysroleInfo).toString() : "null"));
        TblSysroleInfo result = tblSysroleInfoMapper.select(tblSysroleInfo);
        logger.debug("查询结果:tblSysroleInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysroleInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception {
        logger.debug("insertSysroleInfo:start");
        logger.debug("传入参数:tblSysroleInfo:" + (tblSysroleInfo != null ? JSONObject.fromObject(tblSysroleInfo).toString() : "null"));
        int insertNum = tblSysroleInfoMapper.insert(tblSysroleInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysroleInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception {
        logger.debug("updateSysroleInfo:start");
        logger.debug("传入参数:tblSysroleInfo:" + (tblSysroleInfo != null ? JSONObject.fromObject(tblSysroleInfo).toString() : "null"));
        int updateNum = tblSysroleInfoMapper.updateByPrimaryKey(tblSysroleInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysroleInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysroleInfo(TblSysroleInfo tblSysroleInfo) throws Exception {
        logger.debug("deleteSysroleInfo:start");
        logger.debug("传入参数:tblSysroleInfo:" + (tblSysroleInfo != null ? JSONObject.fromObject(tblSysroleInfo).toString() : "null"));
        int deleteNum = tblSysroleInfoMapper.delete(tblSysroleInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysroleInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysroleInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysroleInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysroleInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysroleInfo:end");
        return result;
    }
}

