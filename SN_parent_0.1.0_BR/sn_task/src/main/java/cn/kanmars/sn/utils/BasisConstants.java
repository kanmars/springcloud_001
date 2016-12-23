package cn.kanmars.sn.utils;

import cn.com.xcommon.frame.util.MainSecurity;
import cn.com.xcommon.frame.util.StringUtils;

import java.util.ResourceBundle;

public class BasisConstants {


	/**子任务生成组*/
	public static final String CHILD_TASK_CREATE = "CHILD_TASK_CREATE";

	/**子任务执行组*/
	public static final String CHILD_TASK_RUN = "CHILD_TASK_RUN";
	
	/**系统保障任务组*/
	public static final String SYSTEM_TASK_GROUP = "SYSTEM_TASK_GROUP";


	public static final String PARENT_ID_NAME = "parentId";
	public static final String DRIVER_CLASS_NAME = "driverClassName";
	public static final String DRIVER_URL = "driverUrl";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String TASK_CONFIG_RULE = "taskConfigRule";
	public static final String TASK_DEADLOCK_RULE = "TaskDeadlockRule";
	public static final String REGISTER_FLG = "registerFlg";
	public static final String REGISTER_ADDR = "registerAddr";
	public static final String REGISTER_GROUP = "registerGroup";
	public static final String MQ_NOTIFY_FLG = "mqNotifyFlg";
	public static final String MQ_NOTIFY_ADDR = "mqNotifyAddr";
	public static final String MQ_NOTIFY_GROUP = "mqNotifyGroup";
	public static final String CONSUMER_GROUP = "consumerGroup";


	/**在数据库tbl_basic_task_config中需要执行的parentId，可以为空，代表全部执行*/
	public static final String parentId;

	/**数据库driverClassName*/
	public static final String driverClassName;
	
	/**数据库driverUrl*/
	public static final String driverUrl;
	
	/**数据库账号*/
	public static final String account;
	
	/**数据库密码*/
	public static final String password;
	
	/**任务参数变更监听器执行规则*/
	public static final String taskConfigRule;
	
	/**任务死锁监听器执行规则 */
	public static final String TaskDeadlockRule;

	/**是否注册标志*/
	public static final String registerFlg;
	/**注册地址*/
	public static final String registerAddr;
	/**注册组*/
	public static final String registerGroup;
	/**是否MQ通知标志*/
	public static final String mqNotifyFlg;
	/**MQ监听地址*/
	public static final String mqNotifyAddr;
	/**MQ组*/
	public static final String mqNotifyGroup;
	/**consumerGroup*/
	public static final String consumerGroup;


	static{
		ResourceBundle rb = ResourceBundle.getBundle("props/task");
		rb_static = rb;
		parentId = getBasisConstantsValue(PARENT_ID_NAME);
		driverClassName = getBasisConstantsValue(DRIVER_CLASS_NAME);
		driverUrl = getBasisConstantsValue(DRIVER_URL);
		account = getBasisConstantsValue(ACCOUNT);
		password = getBasisConstantsValue(PASSWORD);
		taskConfigRule = getBasisConstantsValue(TASK_CONFIG_RULE);
		TaskDeadlockRule = getBasisConstantsValue(TASK_DEADLOCK_RULE);
		String registerFlg_tmp = getBasisConstantsValue(REGISTER_FLG);
		if (StringUtils.isNotEmpty(registerFlg_tmp)) {
			registerFlg = registerFlg_tmp;
			registerAddr = getBasisConstantsValue(REGISTER_ADDR);
			registerGroup = getBasisConstantsValue(REGISTER_GROUP);
		} else {
			registerFlg = "false";
			registerAddr = "";
			registerGroup = "";
		}
		String mqNotifyFlg_tmp = getBasisConstantsValue(MQ_NOTIFY_FLG);
		if(StringUtils.isNotEmpty(mqNotifyFlg_tmp)){
			mqNotifyFlg = mqNotifyFlg_tmp;
			mqNotifyAddr = getBasisConstantsValue(MQ_NOTIFY_ADDR);
			mqNotifyGroup = getBasisConstantsValue(MQ_NOTIFY_GROUP);
			consumerGroup = getBasisConstantsValue(CONSUMER_GROUP);
		}else{
			mqNotifyFlg = "false";
			mqNotifyAddr = "";
			mqNotifyGroup = "";
			consumerGroup = "";
		}
	}
	
	
	/**
	 * 判断是否是加密的属性
	 * 
	 * @param propertyValue
	 * @return
	 */
	private static boolean isEncryptProp(String propertyValue) {
		if (propertyValue.startsWith("{AES}")) {
			return true;
		}
		return false;
	}
	private static String getAesDeCode(String value) {
		return MainSecurity.decode(value.replace("{AES}", ""));
	}

	private static ResourceBundle rb_static;

	/**
	 * 从配置文件中获取指定的配置数据
	 * @param key
	 * @return
	 */
	public static String getBasisConstantsValue(String key){
		String result = null;
		try{
			result = rb_static.getString(key);
		}catch (Exception e){

		}
		if(StringUtils.isEmpty(result)){
			return result;
		}
		if (isEncryptProp(result)){
			return getAesDeCode(result);
		}
		return result;
	}

	public static final String ZOOKEEPER_BASE_PATH="/JRFINANCE_TASKREGISTER";
	public static final String ZOOKEEPER_DEFAULT_GROUP = "DEFAULT_GROUP";
	public static final String MQ_NOTIFY_DEFAULT_GROUP = "DEFAULT_GROUP";
	public static final String MQ_CONSUMERGROUP_PREFIX = "finance-sn-task";
	public static final String MQ_TOPIC_PREFIX = "TASK_TRIGER_TOPIC_";


	public static final String KERNELOBJ_GLOBAL_TASK_NAME = "GLOBAL_TASK_NAME";
	public static final String KERNELOBJ_GLOBAL_TASK_COUNT = "GLOBAL_TASK_COUNT";
	public static final String KERNELOBJ_GLOBAL_TASK_INDEX = "GLOBAL_TASK_INDEX";
	public static final String KERNELOBJ_GLOBAL_REGISTER_PATH = "GLOBAL_REGISTER_PATH";
	public static final String KERNELOBJ_zkClient = "zkClient";
	public static final String KERNELOBJ_IZkChildListener = "IZkChildListener";
	public static final String KERNELOBJ_businessObjName = "businessObjName";
	public static final String KERNELOBJ_MQ_CONSUMER = "MQ_CONSUMER";
	public static final String KERNELOBJ_Semaphore = "Semaphore";

	public static final String SPLIT_HOSTNAME = "_HOSTNAME";
	public static final String SPLIT_IP = "_IP";
	public static final String SPLIT_PID = "_PID";
	public static final String SPLIT_TASKNAME = "_TASKNAME";

}
