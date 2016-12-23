package cn.com.xcommon.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName: DateFormatUtils
 * @Description: 日期格式化类
 * @date 2015年2月28日 下午2:23:31
 */
public class DateFormatUtils {
	
	public static SimpleDateFormat getDateFormate(String format){
		if(StringUtils.isEmpty(format)){
			return null;
		}
//		formatMap.put("yyyyMMdd", yyyyMMdd);	1	 new SimpleDateFormat("yyyyMMdd");
		if(format.equals("yyyyMMdd")){
			return new SimpleDateFormat("yyyyMMdd");
		}
//		formatMap.put("yyyy", yyyy);	1	 new SimpleDateFormat("yyyy");		
		if(format.equals("yyyy")){
			return new SimpleDateFormat("yyyy");
		}
//		formatMap.put("yyyyMMdd_", yyyyMMdd_);	2	 new SimpleDateFormat("yyyy-MM-dd");
		if(format.equals("yyyyMMdd_")){
			return new SimpleDateFormat("yyyy-MM-dd");
		}
//		formatMap.put("yyyyMMdd_IU", yyyyMMdd_IU);	3	 new SimpleDateFormat("yyyy/MM/dd");
		if(format.equals("yyyyMMdd_IU")){
			return new SimpleDateFormat("yyyy/MM/dd");
		}
//		formatMap.put("yyyyMMddC", yyyyMMddC);	4	 new SimpleDateFormat("yyyy年MM月dd日");
		if(format.equals("yyyyMMddC")){
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
//		formatMap.put("yyyyMM", yyyyMM);		5	 new SimpleDateFormat("yyyyMM");
		if(format.equals("yyyyMM")){
			return new SimpleDateFormat("yyyyMM");
		}
//		formatMap.put("yyyyMM_", yyyyMM_);		6	 new SimpleDateFormat("yyyy-MM");
		if(format.equals("yyyyMM_")){
			return new SimpleDateFormat("yyyy-MM");
		}
//		formatMap.put("yyyy", yyyy);			7	 new SimpleDateFormat("yyyy");
		if(format.equals("yyyy")){
			return new SimpleDateFormat("yyyy");
		}
//		formatMap.put("HHmmss", HHmmss);		8	 new SimpleDateFormat("HHmmss");
		if(format.equals("HHmmss")){
			return new SimpleDateFormat("HHmmss");
		}
//		formatMap.put("HHmmss_", HHmmss_);		9	 new SimpleDateFormat("HH:mm:ss");
		if(format.equals("HHmmss_")){
			return new SimpleDateFormat("HH:mm:ss");
		}
//		formatMap.put("HHmmssC", HHmmssC);		10	 new SimpleDateFormat("HH点mm分ss秒");
		if(format.equals("HHmmssC")){
			return new SimpleDateFormat("HH点mm分ss秒");
		}
//		formatMap.put("yyyyMMddHHmmss", yyyyMMddHHmmss);	11	new SimpleDateFormat("yyyyMMddHHmmss");
		if(format.equals("yyyyMMddHHmmss")){
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
//		formatMap.put("yyyyMMddHHmmssC", yyyyMMddHHmmssC);	12	new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
		if(format.equals("yyyyMMddHHmmssC")){
			return new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
		}
//		formatMap.put("yyyyMMddHHmmssSSS", yyyyMMddHHmmssSSS);	13	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		if(format.equals("yyyyMMddHHmmssSSS")){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		}
//		formatMap.put("yyyyMMddHHmmss_", yyyyMMddHHmmss_);		14 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		if(format.equals("yyyyMMddHHmmss_")){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		}
//		formatMap.put("yyyyMMddHHmmss_UI", yyyyMMddHHmmss_UI);	15	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(format.equals("yyyyMMddHHmmss_UI")){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
//		formatMap.put("yyyyMMddHHmmss_IU", yyyyMMddHHmmss_IU);	16	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if(format.equals("yyyyMMddHHmmss_IU")){
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		return null;
	}

	/**
	 * @param value 要格式化的字段值
	 * @param oldformat 旧字段值的格式
	 * @param newformat 新字段的格式<br/>
	 *        格式类型：<br/>
	 *        yyyyMMdd = yyyyMMdd<br/>
	 *        yyyyMMdd_ = yyyy-MM-dd<br/>
	 *        yyyyMM = yyyyMM<br/>
	 *        yyyyMM_ = yyyy-MM<br/>
	 *        yyyy = yyyy<br/>
	 *        HHmmss = HHmmss<br/>
	 *        HHmmss_ = HH:mm:ss<br/>
	 *        HHmmssC = HH点mm分ss秒<br/>
	 *        yyyyMMddHHmmss = yyyyMMddHHmmss<br/>
	 *        yyyyMMddHHmmssC = yyyy年MM月dd日HH点mm分ss秒<br/>
	 *        yyyyMMddHHmmssSSS = yyyy-MM-dd HH:mm:ss SSS
	 *        yyyyMMddHHmmss_ = yyyy-MM-dd HH:mm:ss <br/>
	 *        yyyyMMddHHmmss_UI = dd/MM/yyyy HH:mm:ss <br/>
	 */
	@SuppressWarnings("deprecation")
	public static String format(String value, String oldformat, String newformat) {
		Date date = null;
		if(value==null||"".equals(value)){
			return "" ;
		}
		try {
			if ("date".equals(oldformat)) {
				date = new Date(value);
			} else {
				date = getDateFormate(oldformat).parse(value);
			}
			return getDateFormate(newformat).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public static Date format(String value, String format) {
		Date date = null;
		try {
			return date = getDateFormate(format).parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param value 要格式化的字段值 格式类型：<br/>
	 *        yyyyMMdd = yyyyMMdd<br/>
	 *        yyyyMMdd_ = yyyy-MM-dd<br/>
	 *        yyyyMM = yyyyMM<br/>
	 *        yyyyMM_ = yyyy-MM<br/>
	 *        yyyy = yyyy<br/>
	 *        HHmmss = HHmmss<br/>
	 *        HHmmss_ = HH:mm:ss<br/>
	 *        HHmmssC = HH点mm分ss秒<br/>
	 *        yyyyMMddHHmmss = yyyyMMddHHmmss<br/>
	 *        yyyyMMddHHmmssC = yyyy年MM月dd日HH点mm分ss秒<br/>
	 *        yyyyMMddHHmmssSSS = yyyy-MM-dd HH:mm:ss SSS
	 */
	public static String format(Date value, String format) {
		try {
			return getDateFormate(format).format(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 计算一个时间加上或者减去一个秒后所得的时间
	 * @param time	yyyyMMddHHmmss
	 * @param second
	 * @return
	 */
	public static String countTime(String time, int second) {

		String nextTime = null;

		try {
			SimpleDateFormat tstDate = getDateFormate("yyyyMMddHHmmss");
			Date tstdate1 = tstDate.parse(time);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(tstdate1);
			rightNow.add(Calendar.SECOND, second);
			nextTime = tstDate.format(rightNow.getTime());
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return nextTime;
	}
	/**
	 *
	 * 计算一个日期加上或者减去一个日期数后所得的日期
	 * @param currDate
	 * @param date
	 * @return
	 */

	public static String countDate(String currDate, int date) {
		String nextDate = null;
		try {
			SimpleDateFormat tstDate = getDateFormate("yyyyMMdd");
			Date tstdate1 = tstDate.parse(currDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(tstdate1);
			rightNow.add(Calendar.DATE, date);
			nextDate = tstDate.format(rightNow.getTime());
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return nextDate;

	}
	
	/**
	 *
	 * 计算一个月份加上或者减去一个月数后所得的月份
	 * @param currMonth
	 * @param month
	 * @return
	 */

	public static String countMonth(String currMonth, int month) {
		String nextMonth = null;
		try {
			SimpleDateFormat tstDate = getDateFormate("yyyyMM");
			Date tstdate1 = tstDate.parse(currMonth);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(tstdate1);
			rightNow.add(Calendar.MONTH, month);
			nextMonth = tstDate.format(rightNow.getTime());
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return nextMonth;
	}
}
