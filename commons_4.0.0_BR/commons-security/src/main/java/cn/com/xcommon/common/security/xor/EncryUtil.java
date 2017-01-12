package cn.com.xcommon.common.security.xor;

import java.math.BigInteger;
import java.security.MessageDigest;
/**
 * 
* @ClassName: EncryUtil
* @Description: 异或进行加密解密算法 以及md5签名
* @author lijiaqi 
* @date 2015年7月3日 下午3:38:20
 */
public class EncryUtil {
	
	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829232312312";

	
	/**
	* @Title: encrypt 
	* @Description: 对称加密 
	* @param @param password
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static final String encrypt(String password) {
		
		if(isEmpty(password)){
			return "";
		}

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	/**
	* @Title: decrypt 
	* @Description: 对称解密
	* @param @param encrypted
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static final String decrypt(String encrypted) {
		
		if(isEmpty(encrypted)){
			return "";
		}
		
		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);
			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
		
	}

	/**
	* @Title: encodeMessage 
	* @Description: md5签名
	* @param @param data
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String md5Message(String data) throws Exception {
		
		if(isEmpty(data)){
			return "";
		}
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data.getBytes());
		return toHex(md5.digest());
		
	}

	private static String toHex(byte[] buffer) {
		byte[] result = new byte[buffer.length * 2];

		for (int i = 0; i < buffer.length; i++) {
			byte[] temp = getHexValue(buffer[i]);
			result[(i * 2)] = temp[0];
			result[(i * 2 + 1)] = temp[1];
		}
		return new String(result).toUpperCase();
	}

	private static byte[] getHexValue(byte b) {
		int value = b;
		if (value < 0) {
			value = 256 + b;
		}
		String s = Integer.toHexString(value);
		if (s.length() == 1) {
			return new byte[] { 48, (byte) s.charAt(0) };
		}
		return new byte[] { (byte) s.charAt(0), (byte) s.charAt(1) };
	}

	/**
	* @Title: isEmpty 
	* @Description: 判断是否为空
	* @param @param str
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private static boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}
	
	public static void main(String[] args) throws Exception{

		String content = "siteAccount=9528010030&orderNo=03015111915524679905611031431692&orderMoney=25000&payMoney=25000&userNo=1012073692&pageBackUrl=http://z.atguat.com.cn:20703/pay/pay_success.dhtml&notifyUrl=http://10.126.53.61:8292/cashierPayBack/service.do&orderType=29&isSupportStages=0&isByStages=1&orderExpireMsg=30M&orderExpireTime=20151119162245&key=Rq9Fm/ed76aG3aT/hALiwo+0lw==";
		/*1.对称加解密*/
		System.out.println("加密前：" + content);

	    String encrypt = "736974654163636f756e743d39353238303130303330266f726465724e6f3d3033303135313131393135343933333735363035363131303032303233363932266f726465724d6f6e65793d3235303030267061794d6f6e65793d323530303026757365724e6f3d3130313230373336393226706167654261636b55726c3d687474703a2f2f7a2e6174677561742e636f6d2e636e3a32303730332f7061792f7061795f737563636573732e6468746d6c266e6f7469667955726c3d687474703a2f2f31302e3132362e35332e36313a383239322f636173686965725061794261636b2f736572766963652e646f26697342795374616765733d31266973537570706f72745374616765733d30266f72646572547970653d32392673656375726974795369676e3d3032384443363237373145373544413133343831324633323133413237323137266f72646572496e666f3d7b226f7264657241636365737355726c223a22687474703a2f2f63616966752e6a722e6174677561742e636f6d2e636e3a33303132332f6d61696e2f686f6d652e6468746d6c222c227374726f6c6c41726f756e6455726c223a22687474703a2f2f7a2e6174677561742e636f6d2e636e3a32303730332f6d61696e2f696e6465782e6468746d6c222c2261646472657373223a22e4b889e58583e6a1a5e9b98fe6b6a6e5a4a7e58ea642e5baa73231e5b182222c22757365724e616d65223a223133313230313936353733222c226f726465724578706972654d7367223a223330e58886e9929fe58685222c226f7264657245787069726554696d65223a223230313531313139313631393332222c226f7264657245787069726554696d6544657363223a223131e69c883139e697a52031363a31393a3332222c227375624fb7a70de0e3bc0da10285";
	    System.out.println("加密后：" + encrypt);

	    String decrypt = EncryUtil.decrypt(encrypt);
	    System.out.println("解密后：" + decrypt);
	    /*2.Md5签名*/
	    String md5Str = EncryUtil.md5Message(content);
		System.out.println("签名：" + md5Str);
	}
	
}