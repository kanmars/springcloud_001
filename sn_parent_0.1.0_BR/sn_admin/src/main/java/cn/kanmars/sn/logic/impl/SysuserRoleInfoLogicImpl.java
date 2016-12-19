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
import cn.kanmars.sn.entity.TblSysuserRoleInfo;
import cn.kanmars.sn.dao.TblSysuserRoleInfoMapper;
/**
 * 系统用户角色绑定表
 * tbl_sysuser_role_info
 */
@Service
@Transactional
public class SysuserRoleInfoLogicImpl implements cn.kanmars.sn.logic.SysuserRoleInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("SysuserRoleInfoLogicImpl");

    @Autowired
    private TblSysuserRoleInfoMapper tblSysuserRoleInfoMapper;

    /*
     * 查询信息
     */
    public TblSysuserRoleInfo querySysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception {
        logger.debug("querySysuserRoleInfo:start");
        logger.debug("传入参数:tblSysuserRoleInfo:" + (tblSysuserRoleInfo != null ? JSONObject.fromObject(tblSysuserRoleInfo).toString() : "null"));
        TblSysuserRoleInfo result = tblSysuserRoleInfoMapper.select(tblSysuserRoleInfo);
        logger.debug("查询结果:tblSysuserRoleInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysuserRoleInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception {
        logger.debug("insertSysuserRoleInfo:start");
        logger.debug("传入参数:tblSysuserRoleInfo:" + (tblSysuserRoleInfo != null ? JSONObject.fromObject(tblSysuserRoleInfo).toString() : "null"));
        int insertNum = tblSysuserRoleInfoMapper.insert(tblSysuserRoleInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysuserRoleInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception {
        logger.debug("updateSysuserRoleInfo:start");
        logger.debug("传入参数:tblSysuserRoleInfo:" + (tblSysuserRoleInfo != null ? JSONObject.fromObject(tblSysuserRoleInfo).toString() : "null"));
        int updateNum = tblSysuserRoleInfoMapper.updateByPrimaryKey(tblSysuserRoleInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysuserRoleInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysuserRoleInfo(TblSysuserRoleInfo tblSysuserRoleInfo) throws Exception {
        logger.debug("deleteSysuserRoleInfo:start");
        logger.debug("传入参数:tblSysuserRoleInfo:" + (tblSysuserRoleInfo != null ? JSONObject.fromObject(tblSysuserRoleInfo).toString() : "null"));
        int deleteNum = tblSysuserRoleInfoMapper.delete(tblSysuserRoleInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysuserRoleInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysuserRoleInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysuserRoleInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysuserRoleInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysuserRoleInfo:end");
        return result;
    }
}

