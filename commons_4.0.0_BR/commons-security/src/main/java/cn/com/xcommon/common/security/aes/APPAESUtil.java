package cn.com.xcommon.common.security.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cn.com.xcommon.common.security.base64.Base64Util;
 
 
/**
 * 与无线端使用的aes加密
 * @author liuyang-ds26
 *
 */
public class APPAESUtil {
	public static String encrypt(String input, String key){
	byte[] crypted = null;
	try{
	SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	cipher.init(Cipher.ENCRYPT_MODE, skey);
	crypted = cipher.doFinal(input.getBytes());
	}catch(Exception e){
	System.out.println(e.toString());
	}
	return new String(Base64Util.encodeMessage(crypted));
}
 
public static String decrypt(String input, String key){
	byte[] output = null;
	try{
	SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	cipher.init(Cipher.DECRYPT_MODE, skey);
	output = cipher.doFinal(Base64Util.decodeMessage(input));
	}catch(Exception e){
	System.out.println(e.toString());
	}
	return new String(output);
}
 
	public static void main(String[] args) {
		String key = "0000000000000000";
		String data = "1234";
		
		System.out.println("加密后"+ APPAESUtil.encrypt(data, key));
		
		System.out.println(APPAESUtil.decrypt(APPAESUtil.encrypt(data, key), key));
		
			
	}	
}