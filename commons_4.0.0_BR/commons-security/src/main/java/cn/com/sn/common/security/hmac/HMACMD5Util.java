package cn.com.sn.common.security.hmac;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.com.sn.common.security.base64.Base64Util;

public class HMACMD5Util {
	
	public static String getMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encodeMessage(secretKey.getEncoded());
    }
	
	public static byte[] encodeMessage(byte[] data, String key) throws Exception {
        byte[] bkey = Base64Util.decodeMessage(key);
        SecretKey secretKey = new SecretKeySpec(bkey, "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
	
	
	private static String toHex(byte[] buffer) {
		byte[] result = new byte[buffer.length * 2];
		byte[] temp;
		for(int i = 0; i < buffer.length; i++) {
			temp = getHexValue(buffer[i]);
			result[i *2] = temp[0];
			result[i * 2 + 1] = temp[1];
		}
		return new String(result).toUpperCase();
    }
	private static byte[] getHexValue(byte b) {
		int value = b;
		if(value < 0) {
			value = (int) (256 + b);
		}
		String s = Integer.toHexString(value);
		if(s.length() ==1) {
			return new byte[]{'0',(byte)s.charAt(0)};
		}
		return new byte[]{(byte)s.charAt(0),(byte)s.charAt(1)};
	}

	
	public static void main(String[] args) throws Exception {
		String msg = "111111";
        byte[] data = msg.getBytes();
        try {
            System.out.println("msg:" + msg);
            String key = getMacKey();
            System.out.println("mac key:" + key);
            System.out.println("mac:" + toHex(encodeMessage(data, key)));
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}