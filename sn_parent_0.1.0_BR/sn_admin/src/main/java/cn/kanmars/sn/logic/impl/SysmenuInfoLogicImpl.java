/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic.impl;


import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TblSysmenuInfoMapper;
import cn.kanmars.sn.entity.TblSysmenuInfo;
import cn.kanmars.sn.util.PageQueryUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 系统菜单表
 * tbl_sysmenu_info
 */
@Service
@Transactional
public class SysmenuInfoLogicImpl implements cn.kanmars.sn.logic.SysmenuInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("SysmenuInfoLogicImpl");

    @Autowired
    private TblSysmenuInfoMapper tblSysmenuInfoMapper;

    /*
     * 查询信息
     */
    public TblSysmenuInfo querySysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception {
        logger.debug("querySysmenuInfo:start");
        logger.debug("传入参数:tblSysmenuInfo:" + (tblSysmenuInfo != null ? JSONObject.fromObject(tblSysmenuInfo).toString() : "null"));
        TblSysmenuInfo result = tblSysmenuInfoMapper.select(tblSysmenuInfo);
        logger.debug("查询结果:tblSysmenuInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("querySysmenuInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception {
        logger.debug("insertSysmenuInfo:start");
        logger.debug("传入参数:tblSysmenuInfo:" + (tblSysmenuInfo != null ? JSONObject.fromObject(tblSysmenuInfo).toString() : "null"));
        int insertNum = tblSysmenuInfoMapper.insert(tblSysmenuInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertSysmenuInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception {
        logger.debug("updateSysmenuInfo:start");
        logger.debug("传入参数:tblSysmenuInfo:" + (tblSysmenuInfo != null ? JSONObject.fromObject(tblSysmenuInfo).toString() : "null"));
        int updateNum = tblSysmenuInfoMapper.updateByPrimaryKey(tblSysmenuInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateSysmenuInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception {
        logger.debug("deleteSysmenuInfo:start");
        logger.debug("传入参数:tblSysmenuInfo:" + (tblSysmenuInfo != null ? JSONObject.fromObject(tblSysmenuInfo).toString() : "null"));
        int deleteNum = tblSysmenuInfoMapper.delete(tblSysmenuInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteSysmenuInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageSysmenuInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageSysmenuInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblSysmenuInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageSysmenuInfo:end");
        return result;
    }
	public List<TblSysmenuInfo> queryListSysmenuInfo(TblSysmenuInfo tblSysmenuInfo) throws Exception {
		logger.debug("queryListSysmenuInfo:start");
        logger.debug("传入参数:tblSysmenuInfo:" + tblSysmenuInfo);
		List<TblSysmenuInfo> list = new ArrayList<TblSysmenuInfo>();
		list = tblSysmenuInfoMapper.selectList(tblSysmenuInfo);
		logger.debug("查询结果:list:" + list);
	    logger.debug("queryListSysmenuInfo:end");
		return list;
	}
}

