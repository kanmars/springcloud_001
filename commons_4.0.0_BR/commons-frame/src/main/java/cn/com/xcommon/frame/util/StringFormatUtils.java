package cn.com.xcommon.frame.util;

public class StringFormatUtils {

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
}
