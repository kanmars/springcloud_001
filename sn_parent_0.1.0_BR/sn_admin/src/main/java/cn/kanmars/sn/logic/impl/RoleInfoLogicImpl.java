package cn.kanmars.sn.logic.impl;

import java.util.HashMap;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TblSysroleInfoMapper;
import cn.kanmars.sn.entity.TblSysroleInfo;
import cn.kanmars.sn.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleInfoLogicImpl implements cn.kanmars.sn.logic.RoleInfoLogic {
	
	protected HLogger logger = LoggerManager.getLoger("RoleInfoLogicImpl");
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.gome.sn.admin.logic.UserLoginService#queryUser(cn.com.gome.sn.entity.TblSysuserInfo)
	 */
	
	 @Autowired
	private TblSysroleInfoMapper tblSysroleInfoMapper;
	
	public TblSysroleInfo queryRoleInfo(TblSysroleInfo tblSysroleInfo) {
			TblSysroleInfo trole= new TblSysroleInfo();
			logger.info("--------传入参数---tblSysroleInfo----------------"+tblSysroleInfo);
			trole = tblSysroleInfoMapper.select(tblSysroleInfo);
			logger.info("--------响应结果---trole----------------"+trole);
			return trole;
			
	} 
	 
	public TblSysroleInfo queryUserInfo(TblSysroleInfo tblSysroleInfo) {
		TblSysroleInfo trole= new TblSysroleInfo();
		logger.info("--------传入参数---tblSysuserInfo----------------"+tblSysroleInfo);
		trole = tblSysroleInfoMapper.select(tblSysroleInfo);
		logger.info("--------响应结果---tuser----------------"+trole);
		return trole;
		
	}
	
	public Integer insertRoleInfo(TblSysroleInfo tblSysroleInfo)
			throws Exception {
		logger.info("--------传入参数---tblSysroleInfo----------------"+tblSysroleInfo);
		int insertNum = tblSysroleInfoMapper.insert(tblSysroleInfo);
		logger.info("--------响应结果---insertNum----------------"+insertNum);
		return insertNum;
	}



	public Integer updateRoleInfo(TblSysroleInfo tblSysroleInfo)
			throws Exception {
		logger.info("--------传入参数---tblSysroleInfo----------------"+tblSysroleInfo);
		int updateNum=tblSysroleInfoMapper.updateByPrimaryKey(tblSysroleInfo);
		logger.info("--------响应结果---updateNum----------------"+updateNum);
		return updateNum;
	}



	public Integer deleteRoleInfo(TblSysroleInfo tblSysroleInfo)
			throws Exception {
		logger.info("--------传入参数---tblSysroleInfo----------------"+tblSysroleInfo);
		int deleteNum=tblSysroleInfoMapper.delete(tblSysroleInfo);
		logger.info("--------响应结果---deleteNum----------------"+deleteNum);
		return deleteNum;
	}

	

	public HashMap queryPageRoleInfo(HashMap paramMap) throws Exception {
		logger.info("--------传入参数---paramMap----------------"+paramMap);
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
		logger.info("--------响应结果---result----------------"+result);
		return result;
	}
}
