package cn.com.sn.common.security.base64;

import java.io.UnsupportedEncodingException;

/**
 * 标准Base64加密解密
 * @author baolong 20140327
 *
 */
public class Base64Util {
	/**第62和63个字符，不同的base64这两个字符不同*/
	private static char char62='+';
	/**第62和63个字符，不同的base64这两个字符不同*/
	private static char char63='/';
	/**
	 * 基础的BASE64
	 */
	private static char[] infp = {
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		'0','1','2','3','4','5','6','7','8','9',
		char62,char63
	};
	
	/** 是否需要每N个字符添加回车换行	 */
	public static final boolean NEED_ADD_ENTER_CODE = true;
	/** 每76个字符添加一个回车换行	 */
	public static final int ENTERCOUNT = 76;
	/**	分隔符的类型 */
	public static final String ENTER = "";
	/** byte掩码，保存完整byte */
	public static final int MASK_FLOW_8 = 0x00000000000000ff;//1个int为8个byte，一个byte为8位，即2^8,由于一个16进制数可以代表2^4，所以1byte为2个16进制数
	/** byte掩码，保存byte的后六位 */
	public static final int MASK_FLOW_6 = 0x000000000000003f;//1个int为8个byte，一个byte为8位，即2^8,由于一个16进制数可以代表2^4，所以1byte为2个16进制数
	/**
	 * 字符与byte的转换,基础的64个字符会被替换成其下标值，而其他的所有字符都会转化为0
	 * @param c
	 * @return
	 */
	private static byte transS2B(char c){
		byte result = 0;
		if(c>='A'&&c<='Z'){
			result = (byte)(c - 'A' );
			return result;
		}
		if(c>='a'&&c<='z'){
			result = (byte)(c- 'a' + 26);
			return result;
		}
		if(c>='0'&&c<='9'){
			result = (byte)(c- '0' + 52);
			return result;
		}
		if(c==char62)return 62;
		if(c==char63)return 63;
		return result;
	}
	/**
	 * 对byte[]数组进行base64加密，结果为字符串，每76个字符填充一个\n
	 * @param message
	 * @return
	 */
	public static String encodeMessage(byte[] message){
		//准备字符串StringBuilder
		StringBuilder sb = new StringBuilder();
		int message_byte_length = message.length;
		//计算后部补充的字节的数量
		int end = 3 - message_byte_length % 3;
		if(end == 3) end = 0;
		//实际被操作的数组
		byte[] operate_message_byte = null;
		if(end >0){
			//如果发现需要在末尾补字节，则生成实际的操作数组
			operate_message_byte = new byte[message_byte_length + end];
			System.arraycopy(message, 0, operate_message_byte, 0, message_byte_length);
		}else{
			//如果发现不需要在末尾补字节，则操作数组就是原byte数组
			operate_message_byte = message;
		}
		//循环处理
		//1 int = 8位
		//每读操作数据中的3个字节，生成四个char数组插入stringBuilder
		int line_count = 1;//字符总数的计数，每76个字符增加一个换行符
		for(int i=0,j=operate_message_byte.length/3;i<j;i++){
			int i_byte_real = i*3;
			//[1 123456	  |2         ]		把第一个字节的123456位转化为字符，放入sb
			sb.append(infp[operate_message_byte[i_byte_real]>>2 & MASK_FLOW_6 ]);
			//[1       78 |2 1234    ]		把第一个字节的78，第二个字节的1234位转化为字符，放入sb
			sb.append(infp[((operate_message_byte[i_byte_real]<<4 & 0x30 ) | (operate_message_byte[i_byte_real+1]>>>4 & 0xf))& MASK_FLOW_6 ]);
			//[2     5678 |3 12      ]		把第二个字节的5678，第三个字节的12位转化为字符，放入sb
			//如果操作数据中的倒数第二位不存在（在原数据中不存在），则直接跳出，不做任何处理（无数据则不写）
			if(i_byte_real+1>message_byte_length-1){break;}
			sb.append(infp[((operate_message_byte[i_byte_real+1]<<2 & 0x3C) | (operate_message_byte[i_byte_real+2]>>>6 & 0x3 ))& MASK_FLOW_6 ]);
			//[3   345678 |          ]		把第三个字节的345678位转化为字符，放入sb
			//如果操作数据中的倒数第一位不存在（在原数据中不存在），则直接跳出，不做任何处理（无数据则不写）
			if(i_byte_real+2>message_byte_length-1){break;}
			sb.append(infp[operate_message_byte[i_byte_real+2] &  MASK_FLOW_6 ]);
			//如果发现已经是76个字符的倍数，则插入一个换行符
			if(NEED_ADD_ENTER_CODE && ((line_count+3)%ENTERCOUNT == 0) ){sb.append(ENTER);}
			line_count +=4;
		}
		//添加末尾的等号，如果end为2则添加两个=，如果end为1则添加一个=
		if(end==2){
			sb.append("==");
		}else if(end == 1){
			sb.append('=');
		}else{}
		
		return sb.toString();
	}
	
	/**
	 * 将String的message转化为去除回车换行的字符串
	 * @param message
	 * @return
	 */
	private static char[] clearStringMessage(String message){
		char[] result = null;
		char[] message_chararray = message.toCharArray();
		int message_chararray_length = message_chararray.length;
		int controlCharCount = 0;//去除控制字符回车换行
		//计算控制字符的数量
		for(int i=0;i<message_chararray_length;i++){
			if(message_chararray[i]=='\r'||message_chararray[i]=='\n'){
				controlCharCount++;
			}
		}
		//用控制字符数量计算出最终char数组的长度
		result = new char[message_chararray_length - controlCharCount];
		//生成最终的char数组,i为原char数组的下标,j为新char数组的下标
		for(int i=0,j=0;i<message_chararray_length;i++){
			if(message_chararray[i]!='\r'&&message_chararray[i]!='\n'){
				result[j]=message_chararray[i];
				j++;
			}
		}
		return result;
	}
	/**
	 * 对base64加密后的字符串进行解密，解密结果为byte数组
	 * @param message
	 * @return
	 */
	public static byte[] decodeMessage(String message){
		//去除message中的控制字符并且转化为char数组
		char[] message_char = clearStringMessage(message);
		//计算虚拟的byte数组的长度(带等号的、补0byte的长度)
		int length = message_char.length / 4 * 3 ;
		//计算最终结果的长度，realLength为message字符串中的真实长度(不带等号的，去除0byte的长度)
		int realLength = 0;
		if(message_char[message_char.length-2] == '='){
			realLength = length - 2;
		}else if(message_char[message_char.length-1] == '='){
			realLength = length - 1;
		}else{
			realLength = length;
		}
		
		//生成最终结果数据，
		byte[] result = new byte[realLength];
		//对byse64数组进行拆分，并插入result
		for(int i=0 ; i<length/3;i++){
			//base64的char数组的真实下标
			int i_base64_real = i*4;
			//byte数组的真实下标
			int i_byte___real = i*3;
			//将四个字符转化为其下标，如果是=则转化为0
			byte b1 = transS2B(message_char[i_base64_real]);		//1	00123456		一个byte为8位
			byte b2 = transS2B(message_char[i_base64_real+1]);		//2	00123456		一个byte为8位
			byte b3 = transS2B(message_char[i_base64_real+2]);		//3	00123456		一个byte为8位
			byte b4 = transS2B(message_char[i_base64_real+3]);		//4	00123456		一个byte为8位
			//进行合并，把4个byte值转化为3个byte值
			byte bb1 = (byte)((b1<<2 & 0xfc) | (b2 >>4 & 0x03) &  MASK_FLOW_8 );//1 123456  |  2       12
			byte bb2 = (byte)((b2<<4 & 0xf0) | (b3 >>2 & 0x0f) &  MASK_FLOW_8 );//2 3456    |  3     1234
			byte bb3 = (byte)((b3<<6 & 0xc0) | (b4     & 0x3f) &  MASK_FLOW_8 );//3 56      |  4   123456
			//对byte数组赋值
			//第一个byte
			result[ i_byte___real ] = bb1;
			//第二个byte，如果在最终的result中没有该byte的位置（即该byte为补0的情况），则跳出
			if( (i_byte___real+1) >= realLength ){break;}
			result[i_byte___real+1] = bb2;
			//第三个byte，如果在最终的result中没有该byte的位置（即该byte为补0的情况），则跳出
			if( (i_byte___real+2) >= realLength ){break;}
			result[i_byte___real+2] = bb3;
		}
		return result;
	}
	
	/**
	 * 测试方法
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		long start = System.currentTimeMillis();
		System.out.println(start);
		int count = 1;
		String target = "这是一段Base64加密的文本文件，需要转化曾为UTF- 8 的byte数组，然后生成BASE64格式的文本";

		for(int i=0;i<count;i++){
			byte [] tmp = target.getBytes("UTF-8");
			String enStr = encodeMessage(tmp);
			System.out.println(enStr);
			String ouStr = new String(decodeMessage(enStr),"UTF-8");
			System.out.println(ouStr);
			System.out.println(ouStr.equals(target));
		}
		long end = System.currentTimeMillis();
		System.out.println(end);
		System.out.println("耗时"+(end-start));
		//用于jProfile性能测试
		Thread.sleep(10000000);
	}
}