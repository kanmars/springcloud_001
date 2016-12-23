package cn.com.xcommon.frame.template;

import java.util.ArrayList;
import java.util.List;

import cn.com.xcommon.frame.util.MoneyFormatUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @ClassName: MoneyFormatTemplate
 * @Description: 金额格式化模板
 * @date 2015年2月27日 下午3:11:46
 */
public class MoneyFormatTemplate implements TemplateMethodModel {

	/**
	 * @param arguments.get(0) 要格式化的字段值
	 * @param arguments.get(1) 转换格式
	 * @param arguments.get(2) 转换类型
	 * @param arguments.get(3) 格式化失败的默认值 （可选） 转换格式： 无小数位：# 两位小数：#.00 带千分位的两位小数：###,###.00 转换类型： 元转分:y2f 分转元：f2y
	 */

	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {

		String defaultStr = "";
		String value = "";
		String get3 = "";
		if(arguments.size()==0){
			get3 = arguments.get(0).toString();
			return get3;
		}else{
			value = arguments.get(0).toString().replace(",", "");
		}
		try {

			if (arguments.size() >= 4 && null != arguments.get(3)) {
				defaultStr = (String) arguments.get(3);
			}

			return MoneyFormatUtils.format(value,(String) arguments.get(1), (String) arguments.get(2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return defaultStr;
	}

	public static void main(String[] args) throws TemplateModelException {

		MoneyFormatTemplate sft = new MoneyFormatTemplate();
		List list = new ArrayList();
		list.add("0.01");
		list.add("#.00");
		list.add("f2y");
		list.add("0.01");
		System.out.println(sft.exec(list));
		
	}

}
