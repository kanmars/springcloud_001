package cn.com.sn.frame.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;

import cn.com.sn.frame.cache.ApplicationCache;
import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;

/**
 * @ClassName: PropertiesConfigListener
 * @Description: 配置文件加载监听器
 * @date 2015年2月27日 上午11:56:29
 */
public class PropertiesConfigListener implements ServletContextListener {

	private HLogger logger = LoggerManager.getLoger("PropertiesConfigListener");

	/** 多个配置文件以“,”号隔开 */
	private String propertiesConfigLocation;

	public void contextInitialized(ServletContextEvent sce) {
		try {
			propertiesConfigLocation = sce.getServletContext().getInitParameter("propertiesConfigLocation").trim();
			if (StringUtils.isNotEmpty(propertiesConfigLocation)) {
				String[] paths = propertiesConfigLocation.split(",");
				InputStream is = null;
				BufferedReader br = null;
				String lineStr = null;
				String key, value;
				int index = 0;
				for (String path : paths) {
					is = this.getClass().getResourceAsStream(path);
					br = new BufferedReader(new InputStreamReader(is));
					while ((lineStr = br.readLine()) != null) {
						if (lineStr.startsWith("#")) {
							continue;
						}
						index = lineStr.indexOf("=");
						if (index <= 0)
							continue;
						key = lineStr.substring(0, index).trim();
						value = lineStr.substring(index + 1, lineStr.length()).trim();
						logger.debug("##########key=" + key + ",value=" + value);
						ApplicationCache.getInstance().put(key, value);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
