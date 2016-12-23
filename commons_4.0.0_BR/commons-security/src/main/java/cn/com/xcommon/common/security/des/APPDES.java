package cn.com.xcommon.common.security.des;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.com.xcommon.common.security.base64.Base64Util;

/**
 * DES 加密--工具类
 */
public class APPDES {

    private static byte[] eniv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * des加密
     * 
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(eniv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64Util.encodeMessage(encryptedData);
    }

    private static byte[] deiv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * des 解密
     * 
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = Base64Util.decodeMessage(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(deiv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception {
		// DES 加密文件
		// des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
		// DES 解密文件
		// des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
		String str1 = "msg1234";
		// DES 加密字符串
		String str2 = APPDES.encryptDES(str1,"des-0001");
		System.out.println(str2);
		// DES 解密字符串
		String str3 = APPDES.decryptDES( str2,"des-0001");
		System.out.println(str2);
		System.out.println(str3);
    	
    	
    	/* String plaintext = "yangbo";
    	     try {
    	     System.out.println(APPDES.encryptDES(plaintext, "12345678"));
    	     } catch (Exception e) {
    	     e.printStackTrace();
    	     }*/
    	 
    	 
	}
}
