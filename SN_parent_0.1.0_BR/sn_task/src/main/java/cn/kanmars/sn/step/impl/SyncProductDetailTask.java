package cn.kanmars.sn.step.impl;


import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.entity.TblSysDic;
import cn.kanmars.sn.step.AbstractStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: SyncProductDetailTask
 * @Description: 商品详情同步定时任务
 * @author fengcong
 * @date 2015年8月18日 下午5:56:00
 * @param
 */
@Service
public class SyncProductDetailTask extends AbstractStep<TblSysDic> {

	protected HLogger logger = LoggerManager.getLoger("SyncProductDetailTask");
     
	@Autowired
    private TblSysDicMapper tblSysDicMapper;
	
	public List<TblSysDic> queryTaskInfo(TaskBasicConfig config,String trigerMsg) {
		logger.info("SyncProductDetailTask.queryTaskInfo--start");
		logger.info("当前任务执行对象全局名称["+getGlobalTaskName()+"]");
		logger.info("当前任务对象全局数量["+getGlobalTaskCount()+"]");
		logger.info("当前任务对象全局序号["+getGlobalTaskIndex()+"]");
		// 扫描条数
		String rowNum = config.getBusinessInfo();
		logger.info("rowNum:" + rowNum);
		if (StringUtils.isEmpty(rowNum)) {
			rowNum = "10";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("rowNum", rowNum);

		TblSysDic tblsysdic=new TblSysDic();
		tblsysdic.setId(1);
		TblSysDic productInfoList = tblSysDicMapper.select(tblsysdic);
		logger.info(productInfoList.toString());
		logger.info("SyncProductDetailTask.queryTaskInfo--end");
		return null;
	}


	/**
	 * 执行成功则返回true，否则返回false。一般此处为技术性成功而非业务性成功
	 * @param info
	 * @return
	 */
	public boolean runTask(TblSysDic info) {
		logger.info("SyncProductDetailTask.runTask--start");
		boolean result = false;
		int id = info.getId();
		try{
			//锁定任务
			HashMap lockMap = new HashMap();
			lockMap.put("id",id);
			lockMap.put("lockFlg","010");
			lockMap.put("lockFlg_new","020");
			int i = tblSysDicMapper.updateCAS(lockMap);

			//此处返回的true成功，用于统计任务执行成功数量
			//在定时任务执行查看页面可以看到
			//result = sysDicLogic.doSth(info);

		}catch (Exception e){
			logger.error("SyncProductDetailTask.runTask异常",e);
		}finally {
			//解锁任务
			HashMap unLockMap = new HashMap();
			unLockMap.put("id",id);
			unLockMap.put("lockFlg","020");
			unLockMap.put("lockFlg_new","010");
			int i = tblSysDicMapper.updateCAS(unLockMap);
		}
		logger.info("SyncProductDetailTask.runTask--end");
		return result;
	}

	public boolean editTaskInfo(List<TblSysDic> list) {
		return false;
	}

}
