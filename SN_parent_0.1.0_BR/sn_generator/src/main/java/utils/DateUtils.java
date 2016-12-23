package utils;

/**
 * Created by baolong on 2016/1/8.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {



    public static SimpleDateFormat getDateFormate(String format){
        if(StringUtils.isEmpty(format)){
            return null;
        }
        if(format.equals("yyyyMMdd")){
            return new SimpleDateFormat("yyyyMMdd");
        }
        if(format.equals("yyyy")){
            return new SimpleDateFormat("yyyy");
        }
        if(format.equals("yyyyMMdd_")){
            return new SimpleDateFormat("yyyy-MM-dd");
        }
        if(format.equals("yyyyMMdd_IU")){
            return new SimpleDateFormat("yyyy/MM/dd");
        }
        if(format.equals("yyyyMMddC")){
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
        if(format.equals("yyyyMM")){
            return new SimpleDateFormat("yyyyMM");
        }
        if(format.equals("yyyyMM_")){
            return new SimpleDateFormat("yyyy-MM");
        }
        if(format.equals("yyyy")){
            return new SimpleDateFormat("yyyy");
        }
        if(format.equals("HHmmss")){
            return new SimpleDateFormat("HHmmss");
        }
        if(format.equals("HHmmss_")){
            return new SimpleDateFormat("HH:mm:ss");
        }
        if(format.equals("HHmmssC")){
            return new SimpleDateFormat("HH点mm分ss秒");
        }
        if(format.equals("yyyyMMddHHmmss")){
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
        if(format.equals("yyyyMMddHHmmssC")){
            return new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
        }
        if(format.equals("yyyyMMddHHmmssSSS")){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        }
        if(format.equals("yyyyMMddHHmmss_")){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if(format.equals("yyyyMMddHHmmss_UI")){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if(format.equals("yyyyMMddHHmmss_IU")){
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
        return null;
    }

    /**
     * 按yyyyMMdd得到当前日期
     * @return 当前日期
     */
    public static String getCurrDate() {
        return getDateFormate("yyyyMMdd").format(new Date());
    }

    /**
     * 按yyyyMM得到当前月份
     * @return 当前月份
     */
    public static String getCurrMonth() {
        return getDateFormate("yyyyMM").format(new Date());
    }

    /**
     * 按yyyy得到当前年份
     * @return 当前年份
     */
    public static String getCurrYear() {
        return getDateFormate("yyyy").format(new Date());
    }

    /**
     * 按HHmmss得到当前时间
     * @return 当前时间
     */
    public static String getCurrTime() {
        return getDateFormate("HHmmss").format(new Date());
    }

    /**
     * 按yyyyMMddHHmmss得到当前时间
     * @return 当前日期时间
     */
    public static String getCurrDateTime() {
        return getDateFormate("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 计算日期差
     * 注:直接用时间差来计算日期差在Aix下面有问题
     * @param date1 yyyyMMdd
     * @param date2 yyyyMMdd
     * @return 日期差
     * @throws Exception
     */
    public static long getDateDiff(String date1, String date2) throws Exception {

        int tempDifference = 0;
        long difference = 0;
        Date dateTmp1 = getDateFormate("yyyyMMdd").parse(date1);
        Date dateTmp2 = getDateFormate("yyyyMMdd").parse(date2);

        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        if (dateTmp1.compareTo(dateTmp2) < 0) {
            earlier.setTime(dateTmp1);
            later.setTime(dateTmp2);
        } else {
            earlier.setTime(dateTmp2);
            later.setTime(dateTmp1);
        }

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier
                    .get(Calendar.YEAR));
            difference += tempDifference;
            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later
                .get(Calendar.DAY_OF_YEAR)) {
            tempDifference = later.get(Calendar.DAY_OF_YEAR)
                    - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;
            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (dateTmp1.compareTo(dateTmp2) < 0) {
            return -difference;
        } else {
            return difference;
        }

    }

    /**
     * 计算时间差
     * @param time1
     * @param time2
     * @return time2和time的时间差(秒)
     * @throws Exception
     */
    public static long getTimeDiff(String time1, String time2) throws Exception{

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        Date d1 = getDateFormate("yyyyMMddHHmmss").parse(time1);
        Date d2 = getDateFormate("yyyyMMddHHmmss").parse(time2);

        long diff = d2.getTime() - d1.getTime();
        long time = diff / 1000;

        return time;

    }

    /**
     * 计算一个时间加上或者减去一个秒后所得的时间
     * @param time
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

        }

        catch (Exception ex) {

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

            SimpleDateFormat tstDate = new SimpleDateFormat("yyyyMMdd");

            Date tstdate1 = tstDate.parse(currDate);

            Calendar rightNow = Calendar.getInstance();

            rightNow.setTime(tstdate1);

            rightNow.add(Calendar.DATE, date);

            nextDate = tstDate.format(rightNow.getTime());

        }

        catch (Exception ex) {

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

        }

        catch (Exception ex) {

            ex.printStackTrace();

        }

        return nextMonth;

    }


    /**
     * 判断是否闰年
     * @param  year
     * @return boolean
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 取指定年、月的最大天数
     * @param  year, month
     * @return number
     */
    public static int getMonthDay(int year, int month)
    {
        int[] numDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int n = numDays[month - 1];
        if(month == 2)
        {
            n = isLeapYear(year) ? 29 : 28;
        }
        return n;
    }


    /**
     * 将日期yyyy-MM-dd转为yyyyMMdd格式
     * @param date
     * @return
     */
    public static String getString_Date(String date) {
        String str = "";
        if (date != null) {
            str = date.replaceAll("-", "");
        }

        return str;
    }

    /*
     * 将8位时间字符串转换成10位格式：yyyyMMdd:yyyy-MM-dd
     */
    public static String get10_Date(String date) {
        String str = "";
        if (date != null) {
            try {
                str = date.substring(0,4)+"-"+date.substring(4, 6)+"-"+date.subSequence(6, 8);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return str;
    }

    /**
     * 得到本月第一天的日期
     *
     * @param today
     * @return
     */
    public static String  getFirstDate(String today) {
        String fristDate = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            Date day = formater.parse(today);
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.set(Calendar.DATE, 1);
            fristDate = formater.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fristDate;
    }


    /**
     * 获取指定月份的最后一天
     *
     * @param today(yyyyMMdd格式)
     * @return
     */
    public static String lastDateOfMonth(String today) {
        String lastDate = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            Date day = formater.parse(today);
            Calendar c = new GregorianCalendar();
            c.setTime(day);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            lastDate = formater.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastDate;
    }


    /**
     * 计算某日期之后N天的日期
     *
     * @param theDate
     * @param days
     * @return String
     */
    public static String getDate(String theDate, int days) {
        String endDay = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            Date day = formater.parse(theDate);
            Calendar c = new GregorianCalendar();
            c.setTime(day);
            c.add(GregorianCalendar.DATE, days);
            endDay = formater.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDay;
    }


    /**
     * 计算两个日期相隔的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(String startDate, String endDate) {
        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = formater.parse(startDate);
            eDate = formater.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 设日历为相应日期
        calendarStartDate.setTime(sDate);
        calendarEndDate.setTime(eDate);
        if (sDate.after(eDate)) {
            Calendar swap = calendarStartDate;
            calendarStartDate = calendarEndDate;
            calendarEndDate = swap;
        }

        int days = calendarEndDate.get(Calendar.DAY_OF_YEAR)
                - calendarStartDate.get(Calendar.DAY_OF_YEAR);
        int y2 = calendarEndDate.get(Calendar.YEAR);
        while (calendarStartDate.get(Calendar.YEAR) < y2) {
            days += calendarStartDate.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendarStartDate.add(Calendar.YEAR, 1);
        }

        return days;
    }


    /**
     * 计算两个日期相隔的月数(不比较日)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonthsBetween(String startDate, String endDate) {
        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = formater.parse(startDate);
            eDate = formater.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 设日历为相应日期
        calendarStartDate.setTime(sDate);
        calendarEndDate.setTime(eDate);
        if (sDate.after(eDate)) {
            Calendar swap = calendarStartDate;
            calendarStartDate = calendarEndDate;
            calendarEndDate = swap;
        }
        int months = calendarEndDate.get(Calendar.MONTH)
                - calendarStartDate.get(Calendar.MONTH)
                + (calendarEndDate.get(Calendar.YEAR) - calendarStartDate
                .get(Calendar.YEAR)) * 12;
        return months;
    }


    /**
     * 根据起始日和相隔天数计算终止日
     *
     * @param startDate
     * @param days
     * @return
     */
    public static String getEndDateByDays(String startDate, int days) {
        String endDay = null;
        SimpleDateFormat formater = getDateFormate("yyyyMMdd");
        Date day = null;
        try {
            day = formater.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DAY_OF_YEAR, days);
        endDay = formater.format(c.getTime());
        return endDay;
    }



    /**
     * 根据起始日和相隔月数计算终止日
     *
     * @param startDate
     * @param months
     * @return
     */
    public static String getEndDateByMonths(String startDate, int months) {
        String endDay = null;
        SimpleDateFormat formater = getDateFormate("yyyyMMdd");
        Date date = null;
        try {
            date = formater.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months);
        endDay = formater.format(c.getTime());
        return endDay;
    }


    /**
     * 根据起始日和期限计算终止日
     * @param startDate
     * @param years
     * @param months
     * @param days
     * @return
     */
    public static String getEndDateByTerm(String startDate, int years, int months,
                                          int days) {
        return getEndDateByDays(getEndDateByMonths(startDate, years * 12
                + months), days);
    }


    /**
     * 根据终止日和相隔天数计算起始日
     *
     * @param endDate
     * @param days
     * @return
     */
    public static String getStartDateByDays(String endDate, int days) {
        String startDay = null;
        SimpleDateFormat formater = getDateFormate("yyyyMMdd");
        Date day = null;
        try {
            day = formater.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DAY_OF_YEAR, 0 - days);
        startDay = formater.format(c.getTime());
        return startDay;
    }


    /**
     * 计算下一日期
     * @param date
     * @return
     */
    public static String getNextDay(String date) {
        SimpleDateFormat formater = getDateFormate("yyyyMMdd");
        Date day = null;
        try {
            day = formater.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DAY_OF_YEAR, 1);
        String nextDay = formater.format(c.getTime());
        return nextDay;
    }

    /**
     * 计算上一日期
     * @param date
     * @return
     */
    public static String getLastDay(String date) {
        SimpleDateFormat formater = getDateFormate("yyyyMMdd");
        Date day = null;
        try {
            day = formater.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DAY_OF_YEAR, -1);
        String lastDay = formater.format(c.getTime());
        return lastDay;
    }


    /**
     * 按输入格式取得系统当前时间
     *
     * @param format yyyyMMddHHmmss或者yyyyMMdd格式
     * @return
     */
    public static String getCurrDate(String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(new Date());
    }


    public static String format(String value, String oldformat, String newformat) {
        Date date = null;
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

    public static String getCurrDateHm() {
        return getDateFormate("yyyyMMddHHmmssSSS").format(new Date());
    }


    public static String getStrYYYYMMDDHHmmss(String strTime){

        Date date =  null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sd.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getDateFormate("yyyyMMddHHmmss").format(date);
    }



}
