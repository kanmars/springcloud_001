package cn.com.xcommon.frame.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.xcommon.common.security.aes.AESUtil;
import cn.com.xcommon.common.security.base64.Base64Util;

/**
 * 主密钥类，具有密钥版本控制等功能
 */
public class MainSecurity {

	private static final String charset = "UTF-8";

	private static ConcurrentHashMap<String,String> keyStore = new ConcurrentHashMap<String,String>();

	static {
		String key_0 = "A5F3C6A11B03839D46AF9FB43C97C188";
		keyStore.put("VERSION(0)", key_0);
	}

	/**
	 * 向主密钥库中增加密钥
	 * @param version	密钥版本
	 * @param key		密钥值
	 * @return
	 * @throws Exception
	 */
	public static boolean addKeyStore(String version,String key) throws Exception{
		if(keyStore.contains(version)){
			throw new Exception("向MainSecurity中添加密钥时，该密钥已经存在");
		}
		String returnValue = keyStore.putIfAbsent(version,key);
		if(returnValue!=null){
			throw new Exception("向MainSecurity中添加密钥时，该密钥已经存在");
		}
		return true;
	}

	/**
	 * 默认用密钥0编码
	 * @param demsg
	 * @return
	 */
	public static String encode(String demsg) {
		return encode(demsg, "VERSION(0)");
	}

	/**
	 * 默认用密钥0解码
	 * @param enmsg
	 * @return
	 */
	public static String decode(String enmsg) {
		return decode(enmsg, "VERSION(0)");
	}

	/**
	 * 传入内容与密钥版本，进行加密
	 * @param demsg
	 * @param version
	 * @return
	 */
	public static String encode(String demsg,String version) {
		try {
			String key = keyStore.get(version);
			if(StringUtils.isEmpty(key)){
				throw new RuntimeException("version["+version+"]对应的密钥在MainSecurity中不存在");
			}
			return Base64Util.encodeMessage(AESUtil.encodeMessage(AESUtil.getSecretKey(key.getBytes()),
					demsg.getBytes(charset)));
		} catch (Exception e) {
			return demsg;
		}
	}

	/**
	 * 传入内容与密钥版本，进行解密
	 * @param enmsg
	 * @param version
	 * @return
	 */
	public static String decode(String enmsg,String version) {
		try {
			String key = keyStore.get(version);
			if(StringUtils.isEmpty(key)){
				throw new RuntimeException("version["+version+"]对应的密钥在MainSecurity中不存在");
			}
			return new String(AESUtil.decodeMessage(AESUtil.getSecretKey(key.getBytes()),
					Base64Util.decodeMessage(enmsg)), charset);
		} catch (Exception e) {
			e.printStackTrace();
			return enmsg;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(encode("123456"));
		System.out.println(decode("k8ZoRMSHPwbCQ4DfHeB70A=="));
	}
}
