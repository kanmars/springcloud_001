package utils;

/**
 * Created by baolong on 2016/1/8.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;


public class StringUtils {

    public static final String defaultCharset = "UTF-8";

    /**
     * 判断string是否为空
     *
     * @param string
     * @return 如果为空则返回true，否则返回false
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0)
            return true;
        else
            return false;
    }
    /**
     * 判断string是否非空
     * @param string
     * @return 如果非空则返回true，如果是空返回false
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }



    /**
     * 返回指定长度的，由指定字符组成的字符串(相当于c语言的memset)
     *
     * @param ch
     * @param length
     * @return
     */
    public static String initString(char ch, int length) {
        if (length < 0)
            return "";
        char chars[] = new char[length];
        for (int i = 0; i < length; i++)
            chars[i] = ch;
        return new String(chars);
    }


    /**
     * 根据输入的字符串，返回指定长度的String
     * 如果length小于str的长度，则截取指定长度； 如果length大于str的长度，则不足部分用空格补齐
     *
     * @param str
     * @param length
     *            长度
     * @return
     */
    public static String strLeftAlign(String str, int length) {
        if (str == null)
            return initString(' ', length);

        int len = str.length();
        if (length < len)
            return str.substring(0, length);
        else if (length == len)
            return str;
        else
            return str + initString(' ', length - len);
    }

    /**
     * 根据输入的字符串，返回指定长度的String
     * 如果length小于str的长度，则截取指定长度； 如果length大于str的长度，则不足部分用空格补齐
     *
     * @param str
     * @param length
     *            长度
     * @param c 要填充的字符
     * @return
     */
    public static String strLeftAlign(String str, int length,char c) {
        if (str == null)
            return initString(c, length);

        int len = str.length();
        if (length < len)
            return str.substring(0, length);
        else if (length == len)
            return str;
        else
            return str + initString(c, length - len);
    }



    /**
     * 将List转换为以逗号分割的String
     *
     * @param strList
     * @return
     */
    public static String listToString(List strList) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < strList.size(); i++) {
            if (i != 0)
                buf = buf.append(",");
            buf = buf.append((String) strList.get(i));
        }
        return buf.toString();
    }



    /**
     * 将以逗号分割的String转换为List
     *
     * @param str
     * @return
     */
    public static List stringToList(String str) {
        List list = new ArrayList();
        if (StringUtils.isEmpty(str))
            return list;
        int startPos = 0;
        int endPos = str.indexOf(',', startPos);
        while (endPos >= 0) {
            list.add(str.substring(startPos, endPos));
            startPos = endPos + 1;
            endPos = str.indexOf(',', startPos);
        }
        if (startPos < str.length())
            list.add(str.substring(startPos));
        return list;
    }


    /**
     * 比较两个字段数组里面的内容是否相同
     *
     * @param str1,str2
     * @return
     */
    public static boolean compareToStrings(String[] str1, String[] str2) {
        boolean ret = false;
        if (str1==null&&str2==null){
            ret = true;
            return  ret;
        }else if(str1==null||str2==null){
            return  ret;
        }
        int len = str1.length;
        if (len == str2.length) {
            for (int i = 0; i < len; i++) {
                if (str1[i].equalsIgnoreCase(str2[i])) {
                    ret = true;
                }else{
                    ret = false;
                    break;
                }

            }
        }
        return ret;
    }


    /**
     * 合并两个数组
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static Object[] concatArray(Object[] arr1, Object[] arr2) {
        if (arr1 == null)
            return arr2;
        if (arr2 == null)
            return arr1;

        List list = new ArrayList(arr1.length + arr2.length);
        for (int i = 0; i < arr1.length; i++) {
            list.add(arr1[i]);
        }

        for (int i = 0; i < arr2.length; i++) {
            list.add(arr2[i]);
        }
        return list.toArray();
    }

    /**
     * 将byte型数组转为int型数据
     * @param bytes
     * @return
     */
    public static int byteToInt(byte[] bytes) {
        int reValue = 0;
        try {
            reValue = new Integer(new String(bytes));
        } catch (NumberFormatException e) {
            throw e;
        }

        return reValue;
    }

    /**
     * 将 int数据转为指定长度的byte型数组
     * @param value
     * @param length
     * @return
     */
    public static byte[] intToByte (int value, int length) {
        byte[] bytes = new byte[length];
        if (value > 0) {
            String valueStr = String.valueOf(value);
            byte[] tmp = valueStr.getBytes();
            for (int i=0; i<length - tmp.length; i++) {
                valueStr = '0' + valueStr;
            }
            bytes = valueStr.getBytes();
        } else {
            bytes = "0000".getBytes();
        }
        return bytes;
    }

    /**
     * 字符串去两边空格
     * @param value
     * @return
     */
    public static String trim(String value) {
        if (null == value) {
            return "";
        }
        return value.trim();
    }

    /**
     * 针对以数字或字母组成的字符串，去掉左补的 0
     * @param value
     * @return
     */
    public static String trimZero(String value) {
        if (null == value) {
            return "0";
        }
        long valueLong = Long.parseLong(value);
        String reStr = String.valueOf(valueLong);
        return reStr;
    }

    /**
     * 字符串填充函数
     * @param string
     * @param filler
     * @param totalLength
     * @param atEnd
     * @return
     */
    public static String fillString(String string, char filler, int totalLength, boolean atEnd) {
        if(string.length() >= totalLength){
            return string;
        }
        byte[] bytes = new byte[totalLength];
        int stringlength = string.length();
        if(!atEnd){
            //左补0
            int c_space = totalLength - stringlength;
            System.arraycopy(string.getBytes(), 0, bytes, c_space, stringlength);
            for(int i = 0;i<c_space;i++){
                bytes[i]= (byte)filler;
            }
        }else{
            //右补0
            System.arraycopy(string.getBytes(), 0, bytes,0 , stringlength);
            for(int i=stringlength;i<totalLength;i++){
                bytes[i]= (byte)filler;
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
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取一个set
     * @param configName
     * @return
     */
    public static Set<String> getSet(String configName){
        HashSet<String> result = new HashSet<String>();
        String value= configName;
        if(value!=null&&!value.trim().equals("")){
            String sarray[] = value.split(",");
            for(String str:sarray){
                result.add(str);
            }
        }
        return result;
    }
    /**
     * 重新设置日期格式形式
     * yyyy-MM-dd HH:mm:ss
     * 转成
     * yyyyMMddHHmmss
     * @param p_str_format
     * @return
     */
    public static String dateTransFormat( String p_str_format){
        String format = trim(p_str_format);
        format = format.replaceAll("-", "")
                .replaceAll(" ", "")
                .replaceAll(":", "");
        return format ;
    }

    /**
     * 重新组建参数
     * @param p_map
     * @return
     */
    public static String buildParam(Map<String,String> p_map){
        StringBuilder params = new StringBuilder("");
        Iterator it = p_map.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            String value = (String)p_map.get(key);
            if(!"".equals(key)&&!"".equals(value)){
                params.append("&"+key+"="+value);
            }
        }
        String paramSeq = params.substring(1);
        System.out.println(paramSeq);
        return paramSeq ;
    }

    /**
     * 将为NULL的String设置为空串。
     *
     * @param str
     *            字符串
     * @return String
     */
    public static String trimNull(String str) {
        return str == null ? "" : str;
    }



    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    public static String getRandomFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

    /**
     * 校验手机号规则
     * @param pMobile
     * @return
     */
    public static boolean isMobile(String pMobile) {
        boolean result = false;
        String mRegMobile = "^((1[3|8][0-9])|(14[5|7])|(15[^4,\\D])|(17[0|5|6|7|8]))\\d{8}$";

        if (!isEmpty(pMobile)) {
            Pattern p = Pattern.compile(mRegMobile);
            result = p.matcher(pMobile).matches();
        }
        return result;
    }

    /**
     * 混淆手机号，目前与EC的混淆规则相同
     * @param pMobile
     * @return
     */
    public static String formatMobile(String pMobile){
        if(!isMobile(pMobile))
            return pMobile;
        return pMobile.substring(0,3) + "****" + pMobile.substring(7);
    }
}