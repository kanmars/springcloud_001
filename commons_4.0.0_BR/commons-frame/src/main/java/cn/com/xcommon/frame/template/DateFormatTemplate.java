package cn.com.xcommon.frame.template;

import java.util.List;

import cn.com.xcommon.frame.util.DateFormatUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @ClassName: DateFormatTemplate
 * @Description: 日期格式化模板
 * @date 2015年2月27日 下午3:09:23
 */
public class DateFormatTemplate implements TemplateMethodModel {

	/**
	 * @param arguments.get(0) 要格式化的字段值
	 * @param arguments.get(1) 旧字段值的格式
	 * @param arguments.get(2) 新字段的格式
	 * @param arguments.get(3) 格式化失败的默认值 （可选） 格式类型： yyyyMMdd = yyyyMMdd yyyyMMdd_ = yyyy-MM-dd yyyyMM = yyyyMM yyyyMM_ =
	 *        yyyy-MM yyyy = yyyy HHmmss = HHmmss HHmmss_ = HH:mm:ss HHmmssC = HH点mm分ss秒 yyyyMMddHHmmss = yyyyMMddHHmmss
	 *        yyyyMMddHHmmssC = yyyy年MM月dd日HH点mm分ss秒 yyyyMMddHHmmssSSS = yyyy-MM-dd HH:mm:ss SSS
	 */
	@SuppressWarnings({ "rawtypes" })
	public Object exec(List arguments) throws TemplateModelException {
		String defaultStr = "";
		try {
			if (null != arguments.get(3)) {
				defaultStr = (String) arguments.get(3);
			}
			if (arguments != null && arguments.size() > 0) {
				String value = (String) arguments.get(0);
				String formatOld = (String) arguments.get(1);
				String formatNew = (String) arguments.get(2);
				return DateFormatUtils.format(value, formatOld, formatNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultStr;
	}

}
