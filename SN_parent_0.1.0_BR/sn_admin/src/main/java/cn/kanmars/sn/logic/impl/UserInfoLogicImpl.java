package cn.kanmars.sn.logic.impl;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TblSysuserInfoMapper;
import cn.kanmars.sn.entity.TblSysuserInfo;
import cn.kanmars.sn.util.PageQueryUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class UserInfoLogicImpl implements cn.kanmars.sn.logic.UserInfoLogic {
	
	protected HLogger logger = LoggerManager.getLoger("UserInfoLogicImpl");
	
	@Autowired
	private TblSysuserInfoMapper tblSysuserInfoMapper;
	
	public TblSysuserInfo queryUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
		logger.debug("queryUserInfo:start");
		logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
		TblSysuserInfo tuser = tblSysuserInfoMapper.select(tblSysuserInfo);
		logger.debug("查询结果:tblSysuserInfo:" + (tuser != null ? JSONObject.fromObject(tuser).toString() : "null"));
		logger.debug("queryUserInfo:end");
		return tuser;
		
	}


	@Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Integer insertUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
		logger.debug("insertUserInfo:start");
		logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
		int insertNum = tblSysuserInfoMapper.insert(tblSysuserInfo);
		logger.info("操作结果:insertNum["+insertNum+"]");
		logger.debug("insertUserInfo:end");
		return insertNum;
	}


	@Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Integer updateUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
		logger.debug("updateUserInfo:start");
		logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
		int updateNum=tblSysuserInfoMapper.updateByPrimaryKey(tblSysuserInfo);
		logger.info("操作结果:updateNum["+updateNum+"]");
		logger.debug("updateUserInfo:end");
		return updateNum;
	}


	@Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Integer deleteUserInfo(TblSysuserInfo tblSysuserInfo) throws Exception {
		logger.debug("deleteUserInfo:start");
		logger.debug("传入参数:tblSysuserInfo:" + (tblSysuserInfo != null ? JSONObject.fromObject(tblSysuserInfo).toString() : "null"));
		int deleteNum=tblSysuserInfoMapper.delete(tblSysuserInfo);
		logger.info("操作结果:insertNum[" + deleteNum + "]");
		logger.debug("deleteUserInfo:end");
		return deleteNum;
	}

	

	public HashMap queryPageUserInfo(HashMap paramMap) throws Exception {
		logger.debug("queryPageUserInfo:start");
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
		logger.debug("queryPageUserInfo:end");
		return result;
	}

}
