package cn.com.sn.commons.drools.commision;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 委托类，用于处理规则引擎请求
 * @author baolong
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class Commision implements Map {
	
	private Map map = new HashMap();
	
	public String toString() {
		return map.toString();
	}
	public int size() {
		return map.size();
	}
	public boolean isEmpty() {
		return map.isEmpty();
	}
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	public Object get(Object key) {
		return map.get(key);
	}
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	public Object remove(Object key) {
		return map.remove(key);
	}
	public void putAll(Map m) {
		map.putAll(m);
	}
	public void clear() {
		map.clear();
	}
	public Set keySet() {
		return map.keySet();
	}
	public Collection values() {
		return map.values();
	}
	public Set entrySet() {
		return map.entrySet();
	}
	
}
