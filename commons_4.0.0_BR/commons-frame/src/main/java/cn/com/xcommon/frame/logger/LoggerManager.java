package cn.com.xcommon.frame.logger;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import cn.com.xcommon.frame.util.PropertiesUtils;

/**
 * log实例化程序
 * 
 * @author Captain
 * @since 2013-03-04
 */
public class LoggerManager {

	public static final String STD_OUT_LOGER = "STD_OUT_LOGER";
	public static final String STD_OUT_FILE = "STD_OUT_FILE";
	public static final String TXN_LOG_LOGER = "TXN_LOG_LOGER";
	public static final String TXN_LOG_FILE = "TXN_LOG_FILE";
	public static final String ERROR_LOG_LOGER = "ERROR_LOG_LOGER";
	public static final String ERROR_LOG_FILE = "ERROR_LOG_FILE";
	public static final String ALL_LOG_LOGER = "ALL_LOG_LOGER";
	public static final String ALL_LOG_FILE = "ALL_LOG_FILE";
	
	public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

	public static String LOG_DIR = System.getProperty("user.home") + File.separator + LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR); // 相对于HOME目录
	
	static {

		initLog();
		
	}

	/**
	 * 默认loger
	 * 
	 * @return
	 */
	public static HLogger getHLogger() {
		HLogger txnLogger = new HLogger(Logger.getLogger(""));
		return txnLogger;
	}

	/**
	 * 实例化指定loger
	 * 
	 * @param transId
	 * @return
	 */
	public static HLogger getLoger(String transId) {
		return getLoger(null, transId, true);
	}

	/**
	 * 实例化指定loger
	 * 
	 * @param transId
	 * @return
	 */
	public static HLogger getLogerByPackage(String transId) {
		return getLoger(null, transId, false);
	}

	/**
	 * 实例化指定loger
	 * 
	 * @param fqcn
	 * @param transId
	 * @param flag
	 * @return
	 */
	public static HLogger getLoger(String fqcn, String transId, boolean flag) {
		
		HLogger txnLogger;
		if (fqcn == null) {
			txnLogger = new HLogger(HLogger.class.getName(), Logger.getLogger(transId == null ? "" : transId));
		} else {
			txnLogger = new HLogger(fqcn, Logger.getLogger(transId == null ? "" : transId));
		}
		if (transId == null || transId.trim().equals("")) {
			return txnLogger;
		}
		if (!getLogFlag()) {
			return txnLogger;
		}

		// 获取log4j.xml配置
		Logger initLogger = LogManager.getLogger(TXN_LOG_LOGER);
		String appenderClass = initLogger.getAppender(TXN_LOG_FILE).getClass().getName();
		Layout appenderLayout = initLogger.getAppender(TXN_LOG_FILE).getLayout();
		String conversionPattern = getPattern();
		String level = LoggerConfiger.getConfig(LoggerConfiger.LOG_LEVEL);
		
		// 后续修改为读取配置文件
		String logDir = getLogDir();
		
		// 判断交易日志目录是否存在
		if (!new File(logDir).exists()) {
			new File(logDir).mkdirs();
		}

		// 交易日志路径
		String txnLogDirSub = logDir + File.separator + LoggerConfiger.getConfig(LoggerConfiger.TXN_LOG_DIR_SUB);

		// 判断交易日志目录是否存在
		if (!new File(txnLogDirSub).isDirectory()) {
			new File(txnLogDirSub).mkdirs();
			initLog();
		}
		
		// 判断是否日切
		String srcFileDir = map.get(transId);
		if (srcFileDir == null || (srcFileDir != null && !txnLogDirSub.equals(srcFileDir))) {
			Properties p = new Properties();
			p.setProperty("log4j.appender." + transId, appenderClass);
			p.setProperty("log4j.appender." + transId + ".File", txnLogDirSub + File.separator + transId + ".log");
			p.setProperty("log4j.appender." + transId + ".layout", appenderLayout.getClass().getName());
			p.setProperty("log4j.appender." + transId + ".layout.ConversionPattern", conversionPattern);
			p.setProperty("log4j.logger." + transId, level + "," + transId);
			p.setProperty("log4j.additivity." + transId, flag + "");
			PropertyConfigurator.configure(p);
			map.put(transId, txnLogDirSub);
		}
		
		return txnLogger;
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("rawtypes")
	public static void initLog() {

		String level = LoggerConfiger.getConfig(LoggerConfiger.LOG_LEVEL);

		LogManager.getRootLogger().setLevel(Level.toLevel(level));

		//初始化日志路径
		initLogDir();

		// 初始化标准输出
		initStdLog();

		// 初始化错误输出
		initErrorLog();

		// 初始化所有输出
		initAllLog();
	}

	private static void initLogDir(){
		String os =  System.getProperties().getProperty("os.name");
		if(LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR).startsWith("/")&&(!os.startsWith("win")&&!os.startsWith("Win"))){
			//在非Windows系统，Linux,Unix下支持绝对路径
			LOG_DIR = LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR);
		}
	}


	/**
	 * 初始化标准输出
	 */
	private static void initStdLog() {
		if (LoggerConfiger.getConfig(LoggerConfiger.STDOUT_LOG_FLAG) != null
				&& LoggerConfiger.getConfig(LoggerConfiger.STDOUT_LOG_FLAG).trim()
						.equals("true")) {
			// 获取log4j.xml配置
			Logger initLogger = LogManager.getLogger(STD_OUT_LOGER);
			ConsoleAppender appender = (ConsoleAppender) initLogger.getAppender(STD_OUT_FILE);
			PatternLayout layout = (PatternLayout) appender.getLayout();
			layout.setConversionPattern(getPattern());
			appender.activateOptions();
			// 加入log4j配置
			LogManager.getRootLogger().addAppender(appender);
		}
	}

	/**
	 * 初始化错误输出
	 */
	private static void initErrorLog() {
		if (getLogFlag()) {
			// 获取log4j.xml配置
			Logger initLogger = LogManager.getLogger(ERROR_LOG_LOGER);
			RollingFileAppender appender = (RollingFileAppender) initLogger.getAppender(ERROR_LOG_FILE);
			PatternLayout layout = (PatternLayout) appender.getLayout();
			layout.setConversionPattern(getPattern());
			String file = getLogDir() + File.separator + LoggerConfiger.getConfig(LoggerConfiger.ERROR_LOG_FILE_NAME);
			String size = getLogFileSize();
			appender.setMaxFileSize(size);
			appender.setMaxBackupIndex(getLogFileCount());
			try {
				appender.setFile(file, true, appender.getBufferedIO(), appender.getBufferSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
			appender.activateOptions();
			// 加入log4j配置
			//将原有的appender删除，删除方法为，靠equals删除，因此HRollingFileAppender中有equal方法
			LogManager.getRootLogger().removeAppender(appender);
			//新增一个新的appender
			LogManager.getRootLogger().addAppender(appender);

		}
	}

	/**
	 * 初始化所有日志
	 */
	private static void initAllLog() {
		if (getLogFlag()) {
			// 获取log4j.xml配置
			Logger initLogger = LogManager.getLogger(ALL_LOG_LOGER);
			RollingFileAppender appender = (RollingFileAppender) initLogger.getAppender(ALL_LOG_FILE);
			PatternLayout layout = (PatternLayout) appender.getLayout();
			layout.setConversionPattern(getPattern());
			String file = getLogDir() + File.separator + LoggerConfiger.getConfig(LoggerConfiger.ALL_LOG_FILE_NAME);
			String size = getLogFileSize();
			appender.setMaxFileSize(size);
			appender.setMaxBackupIndex(getLogFileCount());

			try {
				appender.setFile(file, true, appender.getBufferedIO(),appender.getBufferSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
			appender.activateOptions();

			// 加入log4j配置
			//将原有的appender删除，删除方法为，靠equals删除，因此HRollingFileAppender中有equal方法
			LogManager.getRootLogger().removeAppender(appender);
			//新增一个新的appender
			LogManager.getRootLogger().addAppender(appender);
		}
	}

	/**
	 * 获取日志根路径
	 * 
	 * @return
	 */
	public static String getLogDir() {
		String logDir = LOG_DIR;
		if (LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR_DATE_FLAG) != null
				&& LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR_DATE_FLAG).trim()
						.equalsIgnoreCase("true")) {
			logDir = logDir + File.separator + LoggerConfiger.getConfig(LoggerConfiger.LOG_ENV) + File.separator + Utils.getCurrDate();
		}
		return logDir;
	}

	/**
	 * 获取日志样式
	 * 
	 * @return
	 */
	public static String getPattern() {
		String conversionPattern = LoggerConfiger.getConfig(LoggerConfiger.LOG_LOGOUT_CONVERSIONPATTERN);
		return conversionPattern;
	}

	/**
	 * 获取单个日志文件大小
	 * 
	 * @return
	 */
	public static String getLogFileSize() {
		String size = LoggerConfiger.getConfig(LoggerConfiger.LOG_FILE_SIZE);
		return size;
	}

	/**
	 * 获取备份日志文件格式
	 * 
	 * @return
	 */
	public static int getLogFileCount() {
		String count = LoggerConfiger.getConfig(LoggerConfiger.LOG_FILE_COUNT);
		int logCount = Integer.parseInt(count.trim());
		return logCount;
	}

	/**
	 * 获取日志开关
	 * 
	 * @return
	 */
	public static boolean getLogFlag() {

		boolean flag = false;
		if (LoggerConfiger.getConfig(LoggerConfiger.LOG_FLAG) != null
				&& LoggerConfiger.getConfig(LoggerConfiger.LOG_FLAG).trim()
						.equals("true")) {
			flag = true;
		}
		return flag;
	}
	
	public static void main(String[] args){
		HLogger hLogger = LoggerManager.getLoger("ABC");
		for(int i = 0;i<10000000;i++){
			hLogger.info("aaaaaaaaaaaaaaaaaa");
			hLogger.info("aaaaaaaaaaaaaaaaaa");
			hLogger.info("aaaaaaaaaaaaaaaaaa");
			hLogger.info("aaaaaaaaaaaaaaaaaa");
			hLogger.info("aaaaaaaaaaaaaaaaaa");
		}
		
	}
}