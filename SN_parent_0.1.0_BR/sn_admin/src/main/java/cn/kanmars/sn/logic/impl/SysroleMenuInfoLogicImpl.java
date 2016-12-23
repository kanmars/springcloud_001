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
import cn.kanmars.sn.entity.TblSysroleMenuInfo;
import cn.kanmars.sn.dao.TblSysroleMenuInfoMapper;
/**
 * 系统角色菜单绑定表
 * tbl_sysrole_menu_info
 */
@Service
@Transactional
public class SysroleMenuInfoLogicImpl implements cn.kanmars.sn.logic.SysroleMenuInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("SysroleMenuInfoLogicImpl");

    @Autowired
    private TblSysroleMenuInfoMapper tblSysroleMenuInfoMapper;

    /*
     * 查询信息
     */
    public TblSysroleMenuInfo querySysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception {
        logger.debug("querySysroleMenuInfo:start");
        logger.debug("传入参数:tblSysroleMenuInfo:" + (tblSysroleMenuInfo != null ? JSONObject.fromObject(tblSysroleMenuInfo).toString() : "null"));
        TblSysroleMenuInfo result = tblSysroleMenuInfoMapper.select(tblSysroleMenuInfo);
        logger.debug("查询结果:tblSysroleMenuInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysroleMenuInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception {
        logger.debug("insertSysroleMenuInfo:start");
        logger.debug("传入参数:tblSysroleMenuInfo:" + (tblSysroleMenuInfo != null ? JSONObject.fromObject(tblSysroleMenuInfo).toString() : "null"));
        int insertNum = tblSysroleMenuInfoMapper.insert(tblSysroleMenuInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysroleMenuInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception {
        logger.debug("updateSysroleMenuInfo:start");
        logger.debug("传入参数:tblSysroleMenuInfo:" + (tblSysroleMenuInfo != null ? JSONObject.fromObject(tblSysroleMenuInfo).toString() : "null"));
        int updateNum = tblSysroleMenuInfoMapper.updateByPrimaryKey(tblSysroleMenuInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysroleMenuInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysroleMenuInfo(TblSysroleMenuInfo tblSysroleMenuInfo) throws Exception {
        logger.debug("deleteSysroleMenuInfo:start");
        logger.debug("传入参数:tblSysroleMenuInfo:" + (tblSysroleMenuInfo != null ? JSONObject.fromObject(tblSysroleMenuInfo).toString() : "null"));
        int deleteNum = tblSysroleMenuInfoMapper.delete(tblSysroleMenuInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysroleMenuInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysroleMenuInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysroleMenuInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysroleMenuInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysroleMenuInfo:end");
        return result;
    }
}

