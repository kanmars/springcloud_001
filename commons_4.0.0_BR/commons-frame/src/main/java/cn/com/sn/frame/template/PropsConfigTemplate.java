package cn.com.sn.frame.template;

import java.util.List;

import cn.com.sn.frame.cache.ApplicationCache;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @ClassName: PropsConfigTemplate
 * @Description: 缓存数据读取
 * @date 2015年3月4日 下午2:44:26
 */
public class PropsConfigTemplate implements TemplateMethodModel {

	public Object exec(List arguments) throws TemplateModelException {
		String value = "";
		try {
			String key = (String) arguments.get(0);
			value = ApplicationCache.getInstance().getStr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
