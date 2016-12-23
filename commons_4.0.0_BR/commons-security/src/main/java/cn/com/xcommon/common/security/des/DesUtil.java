package cn.com.xcommon.common.security.des;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

import cn.com.xcommon.common.security.base64.Base64Util;

public class DesUtil {

	private static HashMap<String,Key> keymap = new HashMap<String,Key>();
	
	private static final String CHARSET = "UTF8";

	/**
	 * 根据参数生成 KEY
	 */
	public static Key getKey(String strKey) {
		Key key = keymap.get(strKey);
		if(key==null){
			try {
				KeyGenerator _generator = KeyGenerator.getInstance("DES");
				SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG","SUN");
				secureRandom.setSeed(strKey.getBytes());  
				_generator.init(secureRandom);
				key = _generator.generateKey();
				keymap.put(strKey, key);
				_generator = null;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Error initializing SqlMap class. Cause: " + e);
			}
		}
		return key;
	}

	/**
	 * 加密 String 明文输入 ,String 密文输出
	 */
	public static String encryptStr(String strKey,String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		try {
			Key key = getKey(strKey);
			byteMing = strMing.getBytes(CHARSET);
			byteMi = encryptByte(key,byteMing);
			strMi = Base64Util.encodeMessage(byteMi);
			//System.out.println(new String(getHexValue(byteMi)));
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String decryptStr(String strKey,String strMi) {
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			Key key = getKey(strKey);
			byteMi = Base64Util.decodeMessage(strMi);
			byteMing = decryptByte(key,byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}
	
	private static byte[] getHexValue(byte b) {
		int value = b;
		if (value < 0) {
			value = (int) (256 + b);
		}
		String s = Integer.toHexString(value);
		if (s.length() == 1) {
			return new byte[] { '0', (byte) s.charAt(0) };
		}
		return new byte[] { (byte) s.charAt(0), (byte) s.charAt(1) };
	}

	/* 得到十六进制表示字符串 */
	public static byte[] getHexValue(byte[] bytes) {
		byte[] result = new byte[bytes.length * 2];
		byte[] temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = getHexValue(bytes[i]);
			result[i * 2] = temp[0];
			result[i * 2 + 1] = temp[1];
		}
		return result;
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private static byte[] encryptByte(Key key,byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] decryptByte(Key key,byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 文件 file 进行加密并保存目标文件 destFile 中
	 * 
	 * @param file
	 *            要加密的文件 如 c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如 c:/ 加密后文件 .txt
	 */
	public static void encryptFile(String strKey,String file, String destFile) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		// cipher.init(Cipher.ENCRYPT_MODE, getKey());
		Key key = getKey(strKey);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(destFile);
		CipherInputStream cis = new CipherInputStream(is, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = cis.read(buffer)) > 0) {
			out.write(buffer, 0, r);
		}
		cis.close();
		is.close();
		out.close();
	}

	/**
	 * 文件采用 DES 算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如 c:/ 加密后文件 .txt *
	 * @param destFile
	 *            解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
	 */
	public void decryptFile(String strKey,String file, String dest) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		Key key = getKey(strKey);
		cipher.init(Cipher.DECRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(dest);
		CipherOutputStream cos = new CipherOutputStream(out, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = is.read(buffer)) >= 0) {
			cos.write(buffer, 0, r);
		}
		cos.close();
		out.close();
		is.close();
	}

	public static void main(String[] args) throws Exception {
		// DES 加密文件
		// des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
		// DES 解密文件
		// des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
		String str1 = "1234";
		// DES 加密字符串
		String str2 = DesUtil.encryptStr("des-0001", str1);
		// DES 解密字符串
		String str3 = DesUtil.decryptStr("123", str2);
		System.out.println(str2);
		System.out.println(str3);
	}
}