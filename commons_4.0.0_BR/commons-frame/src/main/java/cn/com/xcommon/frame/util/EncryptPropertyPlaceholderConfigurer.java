package cn.com.xcommon.frame.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		logger.info("    " + propertyName + "          " + propertyValue);
		if (isEncryptProp(propertyValue)) {
			String decryptValue = getAesDeCode(propertyValue.replace("{AES}", ""));
			logger.info("配置文件字段解密：字段名=" + propertyName + "，字段值= " + propertyValue + ",解密后的值=" + decryptValue);
			return decryptValue;
		}
		return propertyValue;
	}

	/**
	 * 判断是否是加密的属性
	 * 
	 * @param propertyValue
	 * @return
	 */
	private boolean isEncryptProp(String propertyValue) {
		if (propertyValue.startsWith("{AES}")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取一个加密后的字符串
	 * 
	 * @param value
	 * @return
	 */
	private static String getAESEnCode(String value) {
		return MainSecurity.encode(value);
	}

	private static String getAesDeCode(String value) {
		return MainSecurity.decode(value);
	}

	public static void main(String[] args) {
		String s = getAESEnCode("LiTEMhmddcOQMOiL");
		System.out.println(s);
		String s_de = getAesDeCode(s);
		System.out.println(s_de);
	}
}
