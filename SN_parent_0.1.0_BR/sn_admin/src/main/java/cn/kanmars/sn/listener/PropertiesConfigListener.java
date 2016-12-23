package cn.kanmars.sn.listener;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;

/**
 * Created by baolong on 2016/12/15.
 */
@WebListener
public class PropertiesConfigListener implements ServletContextListener {

    private HLogger logger = LoggerManager.getLoger("PropertiesConfigListener");

    /** 多个配置文件以“,”号隔开 */
    private String propertiesConfigLocation="/bootstrap.properties,/application.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String[] paths = propertiesConfigLocation.split(",");
        InputStream is = null;
        BufferedReader br = null;
        String lineStr = null;
        String key, value;
        int index = 0;
        for (String path : paths) {
            try {
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * * Notification that the servlet context is about to be shut down. All
     * servlets and filters have been destroy()ed before any
     * ServletContextListeners are notified of context destruction.
     *
     * @param sce Information about the ServletContext that was destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
