package cn.kanmars.sn.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.kanmars.sn.dao.TblSysmenuInfoMapper;
import cn.kanmars.sn.entity.TblSysmenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MenuInfoLogicImpl implements cn.kanmars.sn.logic.MenuInfoLogic {
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.gome.sn.admin.logic.UserLoginService#queryUser(cn.com.gome.sn.entity.TblSysuserInfo)
	 */
	
	 @Autowired
	private TblSysmenuInfoMapper tblSysmenuInfoMapper;
	

	public List<TblSysmenuInfo> queryUserMenu(String userNo) throws Exception {
		
		List<TblSysmenuInfo>  list = new ArrayList<TblSysmenuInfo>();

		list =  tblSysmenuInfoMapper.queryList(userNo);

		return list;
	}
	
}
