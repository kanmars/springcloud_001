package cn.com.xcommon.frame.template;

import java.util.ArrayList;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @ClassName: StringFormatTemplate
 * @Description: 字符串格式化模板
 * @date 2015年2月27日 下午3:12:13
 */
public class StringFormatTemplate implements TemplateMethodModel {

	/**
	 * @param arguments.get(0) 要格式化的字段值
	 * @param arguments.get(1) 转换格式
	 * @param arguments.get(2) 转换类型
	 * @param arguments.get(3) 格式化失败的默认值 （可选） 转换格式： 是否为空：isNull 转换类型： 返回字符串类型:String 返回布尔类型：Boolean
	 */
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {

		String defaultStr = "";
		try {

			if (arguments.size() >= 4 && null != arguments.get(3)) {
				defaultStr = (String) arguments.get(3);
			}
			String value = (String) arguments.get(0);
			String formatType = (String) arguments.get(2);
			String retType = (String) arguments.get(1);
			if ("isNull".equals(formatType)) {
				return isNull(value, retType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultStr;
	}

	public static Object isNull(String value, String retType) {
		if (value.isEmpty() && "String".equals(retType)) {
			return "";
		} else if (value.isEmpty() && "Boolean".equals(retType)) {
			return true;
		} else if (!value.isEmpty() && "String".equals(retType)) {
			return value;
		} else if (!value.isEmpty() && "Boolean".equals(retType)) {
			return false;
		} else {
			return value;
		}
	}


	public static void main(String[] args) throws TemplateModelException {

		StringFormatTemplate sft = new StringFormatTemplate();
		List list = new ArrayList();
		list.add("2");
		list.add("Boolean");
		list.add("isNull");
		list.add("hehe");
		System.out.println(sft.exec(list));
	}

}
