package utils;

import java.io.*;

public class LoggerUtil {
	public static final String defaultCharset = "UTF-8";
	public static void info(String s){
		try {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("E://m.log", true),defaultCharset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(s);
			pw.println(s);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		info("1233333333");
	}
}
