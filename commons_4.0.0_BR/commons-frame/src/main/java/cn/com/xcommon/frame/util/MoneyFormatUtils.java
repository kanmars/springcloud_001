package cn.com.xcommon.frame.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyFormatUtils {

	private static DecimalFormat df = new DecimalFormat();
	private static final BigDecimal baseNum = new BigDecimal(100);

	public static String format(String value, String formatStyle, String formatType) {

		BigDecimal bg = new BigDecimal(value);
		if ("f2y".equals(formatType)) {
			bg = bg.divide(baseNum);
		} else if ("y2f".equals(formatType)) {
			bg = bg.multiply(baseNum);
		}

		df.applyPattern(formatStyle);

		return df.format(bg);
	}
	
	public static String format(BigDecimal value, String formatStyle, String formatType) {
		if ("f2y".equals(formatType)) {
			value = value.divide(baseNum);
		} else if ("y2f".equals(formatType)) {
			value = value.multiply(baseNum);
		}
		df.applyPattern(formatStyle);
		return df.format(value);
	}

}
