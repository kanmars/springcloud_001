package cn.com.xcommon.frame.controller;

import java.util.List;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.cache.IdValue;

/**
 * @ClassName: DataDictionaryController
 * @Description: 数据字典获取控制器
 * @date 2015年3月4日 下午4:41:58
 */
public class DictionaryController extends ApiBaseController {

	public List<IdValue> select(String key) {
		List<IdValue> obj = null;
		try {
			obj = ApplicationCache.getInstance().getIdValue(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
