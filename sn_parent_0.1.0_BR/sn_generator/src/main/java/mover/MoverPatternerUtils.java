package mover;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**正则表达式替换类
 * Created by baolong on 2016/1/12.
 */
public class MoverPatternerUtils {
    public static void main(String[] args) {
        List<String> strList = new ArrayList<String>();
        strList.add("/SN/");
        strList.add("SN-admin");
        strList.add("/SN");
        strList.add("/SN/SN-c-SN-d-SN/");
        strList.add("SN/SN/SN-c-SN-d-SN/SN");
        strList.add("SN/SN/SN-c-SN-d-SN/SNADMIN/SN-SNCONTROLLER");

        for(String s : strList) {
            System.out.println(transStr(s,"SN","KKK"));
        }

    }

    public static String transStr(String s,String oldStr,String newStr){

        //左右都为非英文或者数字的替换掉
        //处理两次，避免特殊字符在前一个分组中使用后，第二个分组无法修改的问题
        for(int i=0;i<2;i++) {
            //将左右都是非英文字符串的替换掉
            Pattern p1 = Pattern.compile("([^a-zA-Z0-9])("+oldStr+")([^a-zA-Z0-9])");
            StringBuffer sb1 = new StringBuffer();
            Matcher m1 = p1.matcher(s);
            while (m1.find()) {
                if(m1.groupCount()==3){
                    //有头有尾
                    try{
                        m1.appendReplacement(sb1, transSpecialChar(m1.group(1))+newStr+transSpecialChar(m1.group(3)));
                    }catch (Exception e){
                        System.out.println("SSSPPP:    "+transSpecialChar(m1.group(1))+newStr+transSpecialChar(m1.group(3)));
                        e.printStackTrace();
                    }
                }
            }
            m1.appendTail(sb1);

            //将头部有特殊字符串的替换掉
            Pattern p2 = Pattern.compile("^("+oldStr+")([^a-zA-Z0-9])");
            StringBuffer sb2 = new StringBuffer();
            Matcher m2 = p2.matcher(sb1.toString());
            while (m2.find()) {
                if(m2.groupCount()==2){
                    //有头有尾
                    try{
                        m2.appendReplacement(sb2, newStr+transSpecialChar(m2.group(2)));
                    }catch(Exception e){
                        System.out.println("SSSPPP:    "+newStr+transSpecialChar(m2.group(2)));
                        e.printStackTrace();
                    }
                }
            }
            m2.appendTail(sb2);

            //将尾部有特殊字符串的替换掉
            Pattern p3 = Pattern.compile("([^a-zA-Z0-9])("+oldStr+")$");
            StringBuffer sb3 = new StringBuffer();
            Matcher m3 = p3.matcher(sb2.toString());
            while (m3.find()) {
                if(m3.groupCount()==2){
                    //有头有尾
                    try{
                        m3.appendReplacement(sb3, transSpecialChar(m3.group(1))+newStr);
                    }catch(Exception e){
                        System.out.println("SSSPPP:    "+transSpecialChar(m3.group(1))+newStr);
                        e.printStackTrace();
                    }
                }
            }
            m3.appendTail(sb3);

            //将左右都没有特殊字符串的替换掉
            Pattern p4 = Pattern.compile("^("+oldStr+")$");
            StringBuffer sb4 = new StringBuffer();
            Matcher m4 = p4.matcher(sb3.toString());
            while (m4.find()) {
                if(m4.groupCount()==1){
                    m4.appendReplacement(sb4, newStr);
                }
            }
            m4.appendTail(sb4);
            s = sb4.toString();
        }
        return s;
    }

    /**
     * 特殊字符转码，主要是\的问题
     * @param group
     * @return
     */
    public static String transSpecialChar(String group){
        if(group.equals("\\"))return "\\\\";
        if(group.equals("$"))return "\\$";
        return group;
    }
}
