package cn.com.sn.frame.util;

import java.io.UnsupportedEncodingException;

import cn.com.sn.common.security.aes.AESUtil;
import cn.com.sn.common.security.base64.Base64Util;

public class MainSecurity {
	private static String key = "";
	static {
		key = "A5F3C6A11B03839D46AF9FB43C97C188";
	}

	public static String encode(String demsg) {
		try {
			return Base64Util.encodeMessage(AESUtil.encodeMessage(AESUtil.getSecretKey(key.getBytes()),
					demsg.getBytes("UTF-8")));
		} catch (Exception e) {
			return demsg;
		}
	}

	public static String decode(String enmsg) {
		try {
			return new String(AESUtil.decodeMessage(AESUtil.getSecretKey(key.getBytes()),
					Base64Util.decodeMessage(enmsg)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return enmsg;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(encode("123456"));
		System.out.println(decode("k8ZoRMSHPwbCQ4DfHeB70A=="));
//		System.out.println(encode("ikdsIzFcVSBUsY4+"));
	}
}
