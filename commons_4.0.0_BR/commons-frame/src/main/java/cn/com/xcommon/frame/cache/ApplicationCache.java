package cn.com.xcommon.frame.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.StringUtils;

/**
 * @ClassName: SystemWholeCache
 * @Description: 应用全局缓存类
 */
public class ApplicationCache {

	protected HLogger logger = LoggerManager.getLoger(this.getClass().getName());

	private ConcurrentMap<String, Object> cacheMap = null;
	
	private ConcurrentMap<String, List<IdValue>> cacheIdVlaue = null;


	private static class Singleton {
		private static final ApplicationCache singleton = new ApplicationCache();
	}

	public static final ApplicationCache getInstance() {
		return Singleton.singleton;
	}

	private ApplicationCache() {
		cacheMap = new ConcurrentHashMap<String, Object>();
		cacheIdVlaue = new ConcurrentHashMap<String, List<IdValue>>();
	}

	/**
	 * @Title: put
	 * @Description: 添加数据到缓存中 ,永不过期
	 * @param key 数据主键
	 * @param value 需要缓存的数据
	 * @return void 返回类型
	 * @throws
	 */
	public void put(String key, Object value) {
		long startTime = System.currentTimeMillis();
		OverdueInfoNewBean bean = new OverdueInfoNewBean(key, startTime, 0,value);
		cacheMap.put(key, bean);
	}
	
	
	public void putIdValue(String key, List<IdValue> value) {
		cacheIdVlaue.put(key, value);
	}
	public List<IdValue> getIdValue(String key) {
		return cacheIdVlaue.get(key);
	}

	/**
	 * @Title: put
	 * @Description: 添加数据到缓存中 ,并设置过期时间
	 * @param key 数据主键
	 * @param value 需要缓存的数据
	 * @param overTime 过期时间，单位（秒）
	 * @return void 返回类型
	 * @throws
	 */
	public void put(String key, Object value, long overTime) {
		//直接把数据全部放入
		long startTime = System.currentTimeMillis();
		overTime = overTime * 1000 + startTime;
		OverdueInfoNewBean bean = new OverdueInfoNewBean(key, startTime, overTime,value);
		cacheMap.put(key, bean);
	}

	/**
	 * @Title: get
	 * @Description: 根据主键获取数据
	 * @param key
	 * @return Object 返回类型
	 * @throws
	 */
	public Object get(String key) {
		OverdueInfoNewBean bean=(OverdueInfoNewBean) cacheMap.get(key);
		if(bean!=null){
			//没有设置缓存
			if(bean.getOverdueTime()==0){
				return bean.getValue();
			}else{//设置了缓存，判断时间，如果已经过期就返回空
				if (System.currentTimeMillis() > bean.getOverdueTime()) {
					logger.info("缓存已过期，主键为：" + bean.getKey());
					cacheMap.remove(key);
					return null;
				}else{
					return bean.getValue();	
				}
			}
		}else{
			return null;
		}
	}
	
	/**
	 * @Title: getStr
	 * @Description: 根据主键获取数据
	 * @param key
	 * @return String 返回类型
	 * @throws
	 */
	public String getStr(String key) {
		Object obj = get(key);
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * @Title: getInt
	 * @Description: 根据主键获取数据
	 * @param key
	 * @return int 如果key不存在，则返回0；
	 * @throws
	 */
	public int getInt(String key) {
		Object obj = get(key);
		if (obj != null) {
			return Integer.valueOf(obj.toString());
		}
		return 0;
	}

	/**
	 * @Title: getBigDecimal
	 * @Description: 根据主键获取数据
	 * @param key
	 * @return BigDecimal
	 * @throws
	 */
	public BigDecimal getBigDecimal(String key) {
		Object obj = get(key);
		if (obj != null) {
			return new BigDecimal(obj.toString());
		}
		return null;
	}

	/**
	 * 放置缓存数据
	 * @author zoufangfang
	 *
	 */
	@SuppressWarnings("unused")
	private class OverdueInfoNewBean {

		private String key;

		private long startTime;

		private long overdueTime;

		private Object value;

		public OverdueInfoNewBean(String key, long startTime, long overdueTime,Object value) {
			this.value=value;
			this.key = key;
			this.startTime = startTime;
			this.overdueTime = overdueTime;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getOverdueTime() {
			return overdueTime;
		}

		public void setOverdueTime(long overdueTime) {
			this.overdueTime = overdueTime;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

	private class OverdueInfoBean {

		private String key;

		private long startTime;

		private long overdueTime;

		public OverdueInfoBean(String key, long startTime, long overdueTime) {
			super();
			this.key = key;
			this.startTime = startTime;
			this.overdueTime = overdueTime;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getOverdueTime() {
			return overdueTime;
		}

		public void setOverdueTime(long overdueTime) {
			this.overdueTime = overdueTime;
		}

	}
}
