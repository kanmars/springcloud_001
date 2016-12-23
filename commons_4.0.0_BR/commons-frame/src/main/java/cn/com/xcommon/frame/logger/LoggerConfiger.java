package cn.com.xcommon.frame.logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import cn.com.xcommon.frame.util.StringUtils;

/**
 * Log4j 配置参数
 * 
 * @author Captain
 * 
 */
@SuppressWarnings("unchecked")
public class LoggerConfiger {

	public static final String LOG_ENV = "log.env";
	public static final String LOG_FLAG = "log.flag";
	public static final String LOG_LEVEL = "log.level";
	public static final String LOG_DIR = "log.dir";
	public static final String LOG_DIR_DATE_FLAG = "log.dir.date.flag";
	public static final String LOG_LOGOUT_CONVERSIONPATTERN = "log.logout.conversionpattern";
	public static final String LOG_ID_LENGTH = "log.id.length";
	public static final String LOG_FILE_SIZE = "log.file.size";
	public static final String LOG_FILE_COUNT = "log.file.count";

	public static final String TXN_LOG_DIR_SUB = "txn.log.dir.sub";
	public static final String ERROR_LOG_FILE_NAME = "error.log.file.name";
	public static final String ALL_LOG_FILE_NAME = "all.log.file.name";

	public static final String STDOUT_LOG_FLAG = "stdout.log.flag";
	
	private static final ConcurrentHashMap<String, String> map ;
	
	
	static{
		/**初始化Map*/
		map = new ConcurrentHashMap<String, String>();
		
		/** 给map设置初始值*/
		init();
		
		/**
		 * 加载配置属性
		 */
		try {
			SAXReader sax = new SAXReader();
			sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			Document doc = sax.read(LoggerConfiger.class.getResourceAsStream("/log4j.xml"));
			Node loggerConfigerNode =  doc.getRootElement().selectSingleNode("loggerConfiger");//获取loggerConfigerNode节点
			Node loggerConfigerNode_properties = loggerConfigerNode.selectSingleNode("properties");
			List<Node> properties =  loggerConfigerNode_properties.selectNodes("propertie");
			System.out.println("log4j配置为:");
			for(Node n : properties){
				String name = ((Element)n).attribute("name").getText().trim();
				String text = ((Element)n).getText().trim();
				System.out.println("  "+StringUtils.fillString(name, ' ', 30, true)+"   :   " + text);
				map.put(name, text);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 默认值
	 * @author baolong
	 *
	 */
	public static class DefaultLoggerConfiger{
		
		public static final String LOG_ENV = "dev";
		public static final String LOG_FLAG_VALUE = "true";
		public static final String LOG_LEVEL_VALUE = "debug";
		public static final String LOG_DIR_VALUE = "logs";
		public static final String LOG_DIR_DATE_FLAG_VALUE = "true";
		public static final String LOG_LOGOUT_CONVERSIONPATTERN_VALUE = "[%T][%P][%d{yyyyMMdd HHmmss,SSS}][%f{25}:%N] %m%n";
		public static final String LOG_ID_LENGTH_VALUE = "10";
		public static final String LOG_FILE_SIZE_VALUE = "100MB";
		public static final String LOG_FILE_COUNT_VALUE = "10";

		public static final String TXN_LOG_DIR_SUB_VALUE = "txn";
		public static final String ERROR_LOG_FILE_NAME_VALUE = "error.log";
		public static final String ALL_LOG_FILE_NAME_VALUE = "AllLogFileName.log";
		public static final String STDOUT_LOG_FLAG_VALUE = "true";
	}

	


	public LoggerConfiger() {

	}

	public static String getConfig(String key) {
		return map.get(key);
	}

	public static void init() {

		map.put(LOG_ENV,                      getConfig(LOG_ENV)!=null                      ?getConfig(LOG_ENV)                       :DefaultLoggerConfiger.LOG_ENV);
		map.put(LOG_FLAG,                     getConfig(LOG_FLAG)!=null                     ?getConfig(LOG_FLAG)                      :DefaultLoggerConfiger.LOG_FLAG_VALUE);
		map.put(LOG_LEVEL,                    getConfig(LOG_LEVEL)!=null                    ?getConfig(LOG_LEVEL)                     :DefaultLoggerConfiger.LOG_LEVEL_VALUE);
		map.put(LOG_DIR,                      getConfig(LOG_DIR)!=null                      ?getConfig(LOG_DIR)                       :DefaultLoggerConfiger.LOG_DIR_VALUE);
		map.put(LOG_DIR_DATE_FLAG,            getConfig(LOG_DIR_DATE_FLAG)!=null            ?getConfig(LOG_DIR_DATE_FLAG)             :DefaultLoggerConfiger.LOG_DIR_DATE_FLAG_VALUE);
		map.put(LOG_LOGOUT_CONVERSIONPATTERN, getConfig(LOG_LOGOUT_CONVERSIONPATTERN)!=null ?getConfig(LOG_LOGOUT_CONVERSIONPATTERN)  :DefaultLoggerConfiger.LOG_LOGOUT_CONVERSIONPATTERN_VALUE);
		map.put(LOG_ID_LENGTH,                getConfig(LOG_ID_LENGTH)!=null                ?getConfig(LOG_ID_LENGTH)                 :DefaultLoggerConfiger.LOG_ID_LENGTH_VALUE);
		map.put(LOG_FILE_SIZE,                getConfig(LOG_FILE_SIZE)!=null                ?getConfig(LOG_FILE_SIZE)                 :DefaultLoggerConfiger.LOG_FILE_SIZE_VALUE);
		map.put(LOG_FILE_COUNT,               getConfig(LOG_FILE_COUNT)!=null               ?getConfig(LOG_FILE_COUNT)                :DefaultLoggerConfiger.LOG_FILE_COUNT_VALUE);
		map.put(TXN_LOG_DIR_SUB,              getConfig(TXN_LOG_DIR_SUB)!=null              ?getConfig(TXN_LOG_DIR_SUB)               :DefaultLoggerConfiger.TXN_LOG_DIR_SUB_VALUE);
		map.put(ERROR_LOG_FILE_NAME,          getConfig(ERROR_LOG_FILE_NAME)!=null          ?getConfig(ERROR_LOG_FILE_NAME)           :DefaultLoggerConfiger.ERROR_LOG_FILE_NAME_VALUE);
		map.put(ALL_LOG_FILE_NAME,            getConfig(ALL_LOG_FILE_NAME)!=null            ?getConfig(ALL_LOG_FILE_NAME)             :DefaultLoggerConfiger.ALL_LOG_FILE_NAME_VALUE);
		map.put(STDOUT_LOG_FLAG,              getConfig(STDOUT_LOG_FLAG)!=null              ?getConfig(STDOUT_LOG_FLAG)               :DefaultLoggerConfiger.STDOUT_LOG_FLAG_VALUE);

	}
	
}

