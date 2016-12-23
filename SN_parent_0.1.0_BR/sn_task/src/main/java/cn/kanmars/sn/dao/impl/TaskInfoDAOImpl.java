package cn.kanmars.sn.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.bean.DieTaskOpenConfig;
import cn.kanmars.sn.bean.SiteChildTask;
import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.dao.TaskInfoDAO;
import cn.kanmars.sn.utils.BasisConstants;
import org.springframework.stereotype.Service;


/** 
 * @ClassName: TaskInfoDAOImpl 
 * @Description: 查询定时任务的配置信息
 * @date 2015年1月20日 下午1:57:25
 */
@Service
public class TaskInfoDAOImpl implements TaskInfoDAO {
	
	private HLogger logger = LoggerManager.getLoger("TaskInfoDAOImpl");
	
	/**
	* @Title: getConn 
	* @Description: 获取数据库连接
	* @return Connection    返回类型 
	* @throws
	 */
	private Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(BasisConstants.driverClassName).newInstance();
			conn = DriverManager.getConnection(BasisConstants.driverUrl,BasisConstants.account,BasisConstants.password); 
		} catch (Exception e) {
			logger.error("获取Connection异常",e);
		} 
		return conn;
	}

	/* (非 Javadoc) 
	 * <p>Title: getTaskBasicConfig</p> 
	 * <p>Description: </p> 
	 * @return 
	 */
	public List<TaskBasicConfig> getTaskBasicConfig() {
		List<TaskBasicConfig> list = new ArrayList<TaskBasicConfig>();
		ResultSet rs = null;
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			String sql = "select id,task_name,run_rule,status,business_obj_name,parent_id,run_status,task_count,up_time,business_info,internet_name,server_ip from tbl_task_basic_config";
			if(StringUtils.isNotEmpty(BasisConstants.parentId)){
				sql = sql + "where parent_id = '"+BasisConstants.parentId+"'";
			}
//			sql+=" and id=13" ;//订单过期任务
//			sql+=" and id=17" ;//支付失败或冻结失败
			rs = statement.executeQuery(sql);
			TaskBasicConfig config = null;
			while(rs.next()){
				config = new TaskBasicConfig();
				config.setId(rs.getString(1));
				config.setTaskName(rs.getString(2));
				config.setRunRule(rs.getString(3));
				config.setStatus(rs.getString(4));
				config.setBusinessObjName(rs.getString(5));
				config.setParentId(rs.getString(6));
				config.setRunStatus(rs.getString(7));
				config.setTaskCount(rs.getInt(8));
				config.setUpTime(rs.getString(9));
				config.setBusinessInfo(rs.getString(10));
				config.setInternetName(rs.getString(11));
				config.setServerIp(rs.getString(12));
				list.add(config);
			}
		} catch (SQLException e) {
			logger.error("SQL执行异常", e);
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(statement!=null)
					statement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				logger.error("资源关闭异常", e);
			}
		}
		return list;
	}

	public List<SiteChildTask> getSiteChildTask() {
		List<SiteChildTask> list = new ArrayList<SiteChildTask>();
		ResultSet rs = null;
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			String sql = "select id,site_type,send_address,task_id,status,run_count,late_time from tbl_site_child_task where status=1";
			rs = statement.executeQuery(sql);
			SiteChildTask config = null;
			while(rs.next()){
				config = new SiteChildTask();
				config.setId(rs.getString(1));
				config.setSiteType(rs.getString(2));
				config.setSendAddress(rs.getString(3));
				config.setTaskId(rs.getString(4));
				config.setStatus(rs.getString(5));
				config.setRunCount(rs.getInt(6));
				config.setLateTime(rs.getInt(7));
				list.add(config);
			}
		} catch (SQLException e) {
			logger.error("SQL执行异常", e);
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(statement!=null)
					statement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				logger.error("资源关闭异常", e);
			}
		}
		return list;
	}

	public List<DieTaskOpenConfig> getDieTaskOpenConfig() {
		List<DieTaskOpenConfig> list = new ArrayList<DieTaskOpenConfig>();
		ResultSet rs = null;
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			String sql = "select id,open_ask,sqls,status from tbl_die_task_open_config where status=1";
			rs = statement.executeQuery(sql);
			DieTaskOpenConfig config = null;
			while(rs.next()){
				config = new DieTaskOpenConfig();
				config.setId(rs.getString(1));
				config.setOpenAsk(rs.getString(2));
				config.setSqls(rs.getString(3));
				config.setStatus(rs.getString(4));
				list.add(config);
			}
		} catch (SQLException e) {
			logger.error("SQL执行异常", e);
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(statement!=null)
					statement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				logger.error("资源关闭异常", e);
			}
		}
		return list;
	}

	public boolean getDieTaskOpenConfigRun() {
		List<DieTaskOpenConfig> list = getDieTaskOpenConfig();
		
		if(list.size()>0){
			Statement statement = null;
			Connection connection = null;
			try {
				connection = getConn();
				statement = connection.createStatement();
				int count = 0;
				for(DieTaskOpenConfig config : list){
					count = statement.executeUpdate(config.getSqls());
					logger.info(config.getOpenAsk()+" 解锁成功，影响记录（"+count+"）条");
				}
			} catch (SQLException e) {
				logger.error("SQL执行异常", e);
			}finally{
				try {
					if(statement!=null)
						statement.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					logger.error("资源关闭异常", e);
				}
			}
		}
		return true;
	}
}
