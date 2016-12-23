package cn.com.sn.common.security.sha;

import java.security.MessageDigest;

public class SHA1Util {
	
	public static String encodeMessage(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("SHA1");
        md5.update(data);
        return toHex(md5.digest());
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
		String message = "DWMNTH2CJFLCWL111111";
		String encode = encodeMessage(message.getBytes());
		System.out.println(encode);
	}
}
