package cn.com.xcommon.frame.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	/**
	 * 按yyyyMMdd得到当前日期
	 * 
	 * @return 当前日期
	 */
	public static String getCurrDate() {
		Date d = new Date();
		char[] datestr = new char[8];
		int year = d.getYear()+1900;
		int month = d.getMonth()+1;
		int date = d.getDate();
		datestr[0]=(char)(year/1000+'0');
		datestr[1]=(char)(year/100%10+'0');
		datestr[2]=(char)(year/10%10+'0');
		datestr[3]=(char)(year%10+'0');
		datestr[4]=(char)(month/10%10+'0');
		datestr[5]=(char)(month%10+'0');
		datestr[6]=(char)(date/10%10+'0');
		datestr[7]=(char)(date%10+'0');
		return new String(datestr);
	}

	/**
	 * 字符串填充函数
	 * 
	 * @param string
	 * @param filler
	 * @param totalLength
	 * @param atEnd
	 * @return
	 */
	public static String fillString(String string, char filler, int totalLength, boolean atEnd) {
		if (string.length() >= totalLength) {
			return string;
		}
		byte[] bytes = new byte[totalLength];
		int stringlength = string.length();
		if (!atEnd) {
			// 左补0
			int c_space = totalLength - stringlength;
			System.arraycopy(string.getBytes(), 0, bytes, c_space, stringlength);
			for (int i = 0; i < c_space; i++) {
				bytes[i] = (byte) filler;
			}
		} else {
			// 右补0
			System.arraycopy(string.getBytes(), 0, bytes, 0, stringlength);
			for (int i = stringlength; i < totalLength; i++) {
				bytes[i] = (byte) filler;
			}
		}
		return new String(bytes);
	}

	/**
	 * @Description 判断是否为数字
	 * @Created 2011-12-30下午02:53:59
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {

		if (str == null || str.trim().equals("")) {
			return 0;
		}

		int val = Integer.parseInt(str);

		return val;

	}

}