package cn.com.xcommon.frame.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类用于非Spring场景查看配置，需要注意配置文件重复的情况，会造成配置异常
 * 
 * @author baolong
 *
 */
public class PropertiesUtils {

	private static final ConcurrentHashMap<String, Properties> map = new ConcurrentHashMap<String, Properties>();

	/**
	 * 从一个路径加载配置
	 * 
	 * @param path
	 * @return
	 */
	public static Properties getProperties(String path) {
		System.out.println("path===" + path);
		Properties result = map.get(path);
		// 如果能在map中找到
		if (result != null) {
			return result;
		}
		// 如果没有找到，则新建一个并加载配置
		result = new Properties();
		try {
			// System.out.println("PropertiesUtils.class.getClassLoader()==="+PropertiesUtils.class.getClassLoader());
			result.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			// 如果发生异常，则直接返回null
			return null;
		}
		map.put(path, result);
		return result;
	}

	/**
	 * 获取一个字符串
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public static String getString(String path, String key) {
		Properties p = getProperties(path);
		if (p == null) {
			return null;
		} else {
			return p.getProperty(key);
		}
	}

	/**
	 * 获取一个List，用英文逗号分隔
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public static List<String> getList(String path, String key) {
		Properties p = getProperties(path);
		if (p == null) {
			return null;
		}
		String value = p.getProperty(key);
		List<String> result = new ArrayList();
		if (value.length() == 0) {
			return result;
		}
		String[] value_s = value.split(",");
		for (String s : value_s) {
			result.add(s);
		}
		return result;
	}

	/**
	 * 获取一个List，用英文逗号分隔entry,用英文冒号分隔key与value
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public static Map<String, String> getMap(String path, String key) {
		Properties p = getProperties(path);
		if (p == null) {
			return null;
		}
		String value = p.getProperty(key);
		Map<String, String> result = new HashMap<String, String>();
		if (value.length() == 0) {
			return result;
		}
		String[] value_s = value.split(",");
		for (String s : value_s) {
			String[] key_value = s.split(":");
			result.put(key_value[0], key_value[1]);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtils.getString("PROPERTIES/pu.conf", "str"));
		List<String> ls = PropertiesUtils.getList("PROPERTIES/pu.conf", "list");
		for (String s : ls) {
			System.out.println(s);
		}
		Map<String, String> m = PropertiesUtils.getMap("PROPERTIES/pu.conf", "map");
		for (Entry<String, String> e : m.entrySet()) {
			System.out.println(e.getKey() + "   " + e.getValue());
		}
	}

}
