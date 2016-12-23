package cn.com.sn.frame.template;

import java.util.List;
import java.util.Map;

import cn.com.sn.frame.cache.ApplicationCache;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @ClassName: DataDictionaryTemplate
 * @Description: 页面数据字典取值
 * @date 2015年2月27日 下午3:21:49
 */
public class DataDictionaryTemplate implements TemplateMethodModel {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && arguments.size() > 0) {
			String tableName = (String) arguments.get(0);
			String key = (String) arguments.get(1);
			Map<String, String> map = (Map<String, String>) ApplicationCache.getInstance().get(tableName);
			return map.get(key);
		}
		return "";
	}

}
