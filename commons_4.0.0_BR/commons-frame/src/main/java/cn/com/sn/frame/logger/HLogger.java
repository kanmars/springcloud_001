package cn.com.sn.frame.logger;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * loger定义类
 * 
 * @author Captain
 * @since 2013-03-04
 */
public class HLogger {
	private transient Logger logger;
	private String name;
	private String FQCN = (HLogger.class).getName();
	private static final boolean flag = LoggerConfiger.getConfig(LoggerConfiger.LOG_FLAG) != null
			&& LoggerConfiger.getConfig(LoggerConfiger.LOG_FLAG).trim().equals("true") ? true
			: false;
	private int idLen = Utils.isNumber(LoggerConfiger.getConfig(LoggerConfiger.LOG_ID_LENGTH)) ? Utils.toInt(LoggerConfiger.getConfig(LoggerConfiger.LOG_ID_LENGTH)) : 0;
			
	private volatile String datetime = Utils.getCurrDate();
	
	protected HLogger() {

	}

	protected HLogger(String name) {

	}
	
	public HLogger(Logger logger) {
		this.logger = null;
		name = null;
		name = logger.getName();
		this.logger = logger;
	}
	
	public HLogger(String fqcn, Logger logger) {
		FQCN = fqcn;
		this.logger = null;
		name = null;
		name = logger.getName();
		this.logger = logger;
	}

	public Logger getLoger() {
		
		//如果为空，直接创建
		if(logger == null){
			logger = Logger.getLogger(name);
		}
		
		//如果时间相同，则直接返回
		if(datetime.equals(Utils.getCurrDate())){
			return logger;
		}
		
		//如果时间不同，则重新加载配置文件
		// 获取log4j.xml配置
		Logger initLogger = LogManager.getLoggerRepository().getLogger(LoggerManager.TXN_LOG_LOGER);
		
		String appenderClass = initLogger.getAppender(LoggerManager.TXN_LOG_FILE).getClass().getName();
		Layout appenderLayout = initLogger.getAppender(LoggerManager.TXN_LOG_FILE).getLayout();
		String conversionPattern = LoggerManager.getPattern();

		String level = LoggerConfiger.getConfig(LoggerConfiger.LOG_LEVEL);
		if (level == null) {
			level = initLogger.getLevel().toString();
		}

		String logDir = LoggerManager.getLogDir();
		// 判断交易日志目录是否存在
		if (!new File(logDir).exists()) {
			new File(logDir).mkdirs();
		}

		// 交易日志路径
		String txnLogDirSub = logDir + File.separator + LoggerConfiger.getConfig(LoggerConfiger.TXN_LOG_DIR_SUB);

		// 判断交易日志目录是否存在
		if (!new File(txnLogDirSub).isDirectory()) {
			new File(txnLogDirSub).mkdirs();
			LoggerManager.initLog();
		}
		
		// 判断是否日切
		String srcFileDir = LoggerManager.map.get(name);
		if (srcFileDir == null || (srcFileDir != null && !txnLogDirSub.equals(srcFileDir))) {
			//由于在第57行，直接datetime.equals(Utils.getCurrDate())后，会return logger，此分支是必走的
			Properties p = new Properties();
			p.setProperty("log4j.appender." + name, appenderClass);
			p.setProperty("log4j.appender." + name + ".File", txnLogDirSub + File.separator + name + ".log");
			p.setProperty("log4j.appender." + name + ".layout", appenderLayout.getClass().getName());
			p.setProperty("log4j.appender." + name + ".layout.ConversionPattern", conversionPattern);
			p.setProperty("log4j.logger." + name, level + "," + name);
			p.setProperty("log4j.additivity." + name, flag + "");
			PropertyConfigurator.configure(p);
			datetime = Utils.getCurrDate();	//重新设置datetime
			logger = Logger.getLogger(name);//重新创建logger
			LoggerManager.map.put(name, txnLogDirSub);
		}
		
		return logger;
	}
	
	private String genMessage(String id, String message) {

		return genMessage(id, idLen, message);
	}
	private String genMessage(String id, int len, String message) {

		String str = message;
		if (id != null && !id.trim().equals("")) {
			str = "[" + Utils.fillString(id, '0', len, false) + "] " + message;
		}

		return str;
	}
	public void trace(String message) {
		trace(null, message, null, null);
	}

	public void trace(String id, String message) {
		trace(id, message, null, null);
	}

	public void trace(String message, Throwable t) {
		trace(null, message, null, t);
	}
	
	public void trace(String id,String message, Throwable t) {
		trace(id, message, null, t);
	}
	
	public void trace(String strNsg, byte[] byteMsg) {
		trace(null, strNsg, byteMsg, null);
	}

	public void trace(String id, String strNsg, byte[] byteMsg) {
		trace(id, strNsg, byteMsg, null);
	}

	public void trace(String strNsg,  byte[] byteMsg, Throwable t) {
		trace(null, strNsg, byteMsg, t);
	}
	

	public void trace(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.DEBUG)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.DEBUG, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.DEBUG, sb.toString(), t);
			}
		}
	}
	
	public void debug(String message) {
		debug(null, message, null, null);
	}

	public void debug(String id, String message) {
		debug(id, message, null, null);
	}

	public void debug(String message, Throwable t) {
		debug(null, message, null, t);
	}
	
	public void debug(String id,String message, Throwable t) {
		debug(id, message, null, t);
	}
	
	public void debug(String strMsg, byte[] byteMsg) {
		debug(null, strMsg, byteMsg, null);
	}

	public void debug(String id, String strMsg, byte[] byteMsg) {
		debug(id, strMsg, byteMsg, null);
	}

	public void debug(String strMsg, byte[] byteMsg, Throwable t) {
		debug(null, strMsg, byteMsg, t);
	}
	
	public void debug(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.DEBUG)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.DEBUG, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.DEBUG, sb.toString(), t);
			}
		}
	}
	
	public void info(String message) {
		info(null, message, null, null);
	}

	public void info(String id, String message) {
		info(id, message, null, null);
	}

	public void info(String message, Throwable t) {
		info(null, message, null, t);
	}
	
	public void info(String id,String message, Throwable t) {
		info(id, message, null, t);
	}
	
	public void info(String strMsg,byte[] byteMsg) {
		info(null, strMsg, byteMsg, null);
	}

	public void info(String id, String strMsg,byte[] byteMsg) {
		info(id, strMsg, byteMsg, null);
	}

	public void info(String strMsg,byte[] byteMsg, Throwable t) {
		info(null, strMsg, byteMsg, t);
	}

	public void info(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.INFO)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.INFO, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.INFO, sb.toString(), t);
			}
		}
	}
	
	public void warn(String message) {
		warn(null, message, null, null);
	}

	public void warn(String id, String message) {
		warn(id, message, null, null);
	}

	public void warn(String message, Throwable t) {
		warn(null, message, null, t);
	}

	public void warn(String id, String message, Throwable t) {
		warn(id, message, null, t);
	}
	
	public void warn(String strMsg, byte[] byteMsg) {
		warn(null, strMsg, byteMsg, null);
	}

	public void warn(String id, String strMsg, byte[] byteMsg) {
		warn(id, strMsg, byteMsg, null);
	}

	public void warn(String strMsg, byte[] byteMsg, Throwable t) {
		warn(null, strMsg, byteMsg, t);
	}

	public void warn(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.WARN)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.WARN, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.WARN, sb.toString(), t);
			}
		}
	}

	public void error(String message) {
		error(null, message, null, null);
	}

	public void error(String id, String message) {
		error(id, message, null, null);
	}

	public void error(String message, Throwable t) {
		error(null, message, null, t);
	}
	
	public void error(String id, String message, Throwable t) {
		error(id, message, null, t);
	}
	
	public void error(String strMsg, byte[] byteMsg) {
		error(null, strMsg, byteMsg, null);
	}

	public void error(String id, String strMsg, byte[] byteMsg) {
		error(id, strMsg, byteMsg, null);
	}

	public void error(String strMsg, byte[] byteMsg, Throwable t) {
		error(null, strMsg, byteMsg, t);
	}

	public void error(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.ERROR)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.ERROR, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.ERROR, sb.toString(), t);
			}
		}
	}

	public void fatal(String message) {
		fatal(null, message, null, null);
	}

	public void fatal(String id, String message) {
		fatal(id, message, null, null);
	}

	public void fatal(String message, Throwable t) {
		fatal(null, message, null, t);
	}
	
	public void fatal(String id, String message, Throwable t) {
		fatal(id, message, null, t);
	}
	
	public void fatal(String strMsg, byte[] byteMsg) {
		fatal(null, strMsg, byteMsg, null);
	}

	public void fatal(String id, String strMsg, byte[] byteMsg) {
		fatal(id, strMsg, byteMsg, null);
	}

	public void fatal(String strMsg, byte[] byteMsg, Throwable t) {
		fatal(null, strMsg, byteMsg, t);
	}
	
	public void fatal(String id, String strMsg, byte[] byteMsg , Throwable t) {
		if (flag && getLoger().isEnabledFor(Level.FATAL)) {
			if(byteMsg == null){
				getLoger().log(FQCN, Level.FATAL, genMessage(id, strMsg), t);
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(genMessage(id, strMsg));
				sb.append(ByteLogUtil.getByteLogMessage(byteMsg));
				getLoger().log(FQCN, Level.FATAL, sb.toString(), t);
			}
		}
	}

}
