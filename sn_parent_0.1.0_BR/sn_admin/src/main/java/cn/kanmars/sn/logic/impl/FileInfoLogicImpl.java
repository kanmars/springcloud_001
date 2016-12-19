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
import cn.kanmars.sn.entity.TblFileInfo;
import cn.kanmars.sn.dao.TblFileInfoMapper;
/**
 * 文件信息
 * tbl_file_info
 */
@Service
@Transactional
public class FileInfoLogicImpl implements cn.kanmars.sn.logic.FileInfoLogic{

    protected HLogger logger = LoggerManager.getLoger("FileInfoLogicImpl");

    @Autowired
    private TblFileInfoMapper tblFileInfoMapper;

    /*
     * 查询信息
     */
    public TblFileInfo queryFileInfo(TblFileInfo tblFileInfo) throws Exception {
        logger.debug("queryFileInfo:start");
        logger.debug("传入参数:tblFileInfo:" + (tblFileInfo != null ? JSONObject.fromObject(tblFileInfo).toString() : "null"));
        TblFileInfo result = tblFileInfoMapper.select(tblFileInfo);
        logger.debug("查询结果:tblFileInfo:" + (result != null ? JSONObject.fromObject(result).toString() : "null"));
        logger.debug("queryFileInfo:end");
        return result;
    }
    /*
     * 新增信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer insertFileInfo(TblFileInfo tblFileInfo) throws Exception {
        logger.debug("insertFileInfo:start");
        logger.debug("传入参数:tblFileInfo:" + (tblFileInfo != null ? JSONObject.fromObject(tblFileInfo).toString() : "null"));
        int insertNum = tblFileInfoMapper.insert(tblFileInfo);
        logger.debug("操作结果:insertNum["+insertNum+"]");
        logger.debug("insertFileInfo:end");
        return insertNum;
    }
    /*
     * 修改信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updateFileInfo(TblFileInfo tblFileInfo) throws Exception {
        logger.debug("updateFileInfo:start");
        logger.debug("传入参数:tblFileInfo:" + (tblFileInfo != null ? JSONObject.fromObject(tblFileInfo).toString() : "null"));
        int updateNum = tblFileInfoMapper.updateByPrimaryKey(tblFileInfo);
        logger.debug("操作结果:updateNum["+updateNum+"]");
        logger.debug("updateFileInfo:end");
        return updateNum;
    }
    /*
     * 删除信息
     */
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer deleteFileInfo(TblFileInfo tblFileInfo) throws Exception {
        logger.debug("deleteFileInfo:start");
        logger.debug("传入参数:tblFileInfo:" + (tblFileInfo != null ? JSONObject.fromObject(tblFileInfo).toString() : "null"));
        int deleteNum = tblFileInfoMapper.delete(tblFileInfo);
        logger.debug("操作结果:deleteNum["+deleteNum+"]");
        logger.debug("deleteFileInfo:end");
        return deleteNum;
    }
    /*
     * 查询信息queryPage
     */
    public HashMap queryPageFileInfo(HashMap paramMap) throws Exception {
        logger.debug("queryPageFileInfo:start");
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
        HashMap result = PageQueryUtil.pageQuery(tblFileInfoMapper, startIndex, pageSize, paramMap);
        logger.debug("查询结果:result:" + result);
        logger.debug("queryPageFileInfo:end");
        return result;
    }
}

