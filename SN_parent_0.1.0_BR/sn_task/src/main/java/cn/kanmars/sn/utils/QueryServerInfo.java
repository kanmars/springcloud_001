package cn.kanmars.sn.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.bean.TaskBasicConfig;
import org.apache.commons.lang.StringUtils;

public class QueryServerInfo {

	private static HLogger logger = LoggerManager.getLoger("QueryServerInfo");

    /** IP */
    private static String LOCAL_IP_HOST = "";
    
    /**
    * @Title: checkIp 
    * @Description: 判断此任务需要单台机器执行
    * 当serverIp为空时，创建此任务，返回true;
    * 当serverIp与当前服务器IP相符时，创建此任务，返回true;
    * 当serverIp与当前服务器IP不相符时，不创建此任务，返回false;
    * @param config
    * @return boolean    返回类型 
    * @throws
     */
    public static boolean checkIp(TaskBasicConfig config){
		if(StringUtils.isNotEmpty(config.getServerIp())){
			if(StringUtils.isEmpty(LOCAL_IP_HOST)){
				initLocalIp(config.getInternetName());
			}
			//指定IP运行时，获取不到当前机器的IP，则不运行
			if(StringUtils.isEmpty(LOCAL_IP_HOST)){
				return false;
			}
			//指定IP运行，且获取到了当前机器的IP
			List<String> targetIps = Arrays.asList(config.getServerIp().split(","));
			if(targetIps.contains(LOCAL_IP_HOST)){
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 初始化本地服务器的ip.
	 */
	public synchronized static void initLocalIp(String internetName) {
		try {
			Properties prop = System.getProperties();
			String os = prop.getProperty("os.name");
			logger.info("服务器系统版本：" + os);
			if (os.startsWith("win") || os.startsWith("Win")) {
				InetAddress addr = InetAddress.getLocalHost();
				LOCAL_IP_HOST = addr.getHostAddress();// 获得本机IP
				logger.info("IP地址：" + LOCAL_IP_HOST);
			}else{
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
				String address = "";
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					if(ni.getName().equals(internetName)){
						while (ips.hasMoreElements()) {
							address = ips.nextElement().getHostAddress();
							logger.info("IP地址：" + address);
							if (address.split("\\.").length == 4) {
								LOCAL_IP_HOST = address;
								return;
							}else{
								address = "";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取服务器本地IP异常", e);
		}
	}

	private static String getHostNameForLiunx() {
		try {
			return (InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException uhe) {
			logger.debug("UnknownHostException", uhe);
			String host = uhe.getMessage(); // host = "hostname: hostname"
			if (host != null) {
				int colon = host.indexOf(':');
				if (colon > 0) {
					return host.substring(0, colon);
				}
			}
			return "UnknownHost";
		}
	}


	public static String getHostName() {
		if (System.getenv("COMPUTERNAME") != null) {
			return System.getenv("COMPUTERNAME");
		} else {
			return getHostNameForLiunx();
		}
	}

	public static String getIp(){
		String hostname = getHostName();
		try {
			return InetAddress.getByName(hostname).getHostAddress();
		} catch (UnknownHostException e) {
			logger.debug("UnknownHostException", e);
		}
		return "UnknownHost";
	}
	public static String getPid(){
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = name.split("@")[0];
		return pid;
	}
}
