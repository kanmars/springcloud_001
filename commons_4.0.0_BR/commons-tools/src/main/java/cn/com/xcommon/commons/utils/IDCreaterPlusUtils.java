package cn.com.xcommon.commons.utils;

/**
 * Created by zhaojiuyang on 2015/12/10.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by baolong on 2015/12/1.
 */
public class IDCreaterPlusUtils {
    /**
     * 掩码制造，从0开始，包含start，包含end，start>=0，end<=63
     * @param start 0<=start<=63
     * @param end   0<=end<=63
     * @return
     */
    private static long createMask(int start,int end) throws Exception{
        if(start <0 || start >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=start<=63 ");
        if(end   <0 || end   >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=end<=63 ");
        if(end < start) throw new Exception("IDCreaterPlusUtils createMask must: end >= start");
        long result = 0xffffffffffffffffl;
        result = result << (64-(end+1-start));
        result = result >>> start;
        return result;
    }

    /**
     * 给old代表的64位数字中的第start到第end位，设置一个数字为val，如果val超出start和end的长度区间，则舍弃掉超出的内容
     * @param old       原始数据
     * @param val       要设置进去的数据
     * @param start     开始位置，0<=start<=end<=63，闭区间
     * @param end       结束位置，0<=start<=end<=63, 闭区间
     * @return
     * @throws Exception
     */
    public static long setValue(long old,long val,int start,int end) throws Exception{
        if(start <0 || start >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=start<=63 ");
        if(end   <0 || end   >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=end<=63 ");
        if(end < start) throw new Exception("IDCreaterPlusUtils createMask must: end >= start");
        long result = old;
        long mask = createMask(start,end);
        result = result & (0xffffffffffffffffl ^ mask);//result与掩码的反码进行与操作，将指定位置为0
        long value = val;
        value = value << (64-(end+1-start));        //左移到指定位置
        value = value >>>  start;
        value = value & mask;                       //与掩码进行计算
        result = result | value;                    //将原值与新值进行组合
        return result;
    }

    /**
     * 获取到 l 中的第start到end位的数据并转换为long
     * @param l       原始数据
     * @param start     开始位置，0<=start<=end<=63，闭区间
     * @param end       结束位置，0<=start<=end<=63, 闭区间
     * @return
     * @throws Exception
     */
    public static long getValue(long l,int start,int end) throws Exception{
        if(start <0 || start >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=start<=63 ");
        if(end   <0 || end   >63 ) throw new Exception("IDCreaterPlusUtils createMask must: 0<=end<=63 ");
        if(end < start) throw new Exception("IDCreaterPlusUtils createMask must: end >= start");
        return ((l<<start)>>1&0x7fffffffffffffffl)>>(start-1)>>(63-end);
    }

    /**
     * 打印一个long的二进制码，不分割
     * @param l
     * @return
     */
    public static String pl64(long l){
        return pl64(l, false);
    }
    /**
     * 打印一个long的二进制码
     * @param l    要打印的数值
     * @param split    是否每4位分割
     * @return
     */
    public static String pl64(long l,boolean split){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<64;i++){
            sb.append(pl(l, i));
            if((i+1)%4 ==0 && split){
                sb.append(" ");
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 设置基础时间为2014年9月1日
     */
    private static final Date dateStart = new Date(2014-1900,9,1);

    /**
     * 打印l 的从0开始第index位的数据
     * @param l
     * @param index
     */
    private static String pl(long l,int index){
        return (0x0000000000000001L & (l>> (63-index)))+"";
    }

    /**
     * 创建一个简单的long
     * @return
     */
    public static long createSimpleLong(){
        try {
            return createSimpleLong(new Random().nextInt(33554430));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Random().nextLong();
    }

    /**
     * 创建一个简单的long
     * @param number 要储存的数据，可以是0-33554430闭区间内的数字
     * @return
     * @throws Exception
     */
    public static long createSimpleLong(int number) throws Exception {
        if(number<0||0>33554431){
            throw new RuntimeException("createSimpleLong储存的number可以是[0-33554431]闭区间内的数字");
        }
        long origin = 0L;
        long d = System.currentTimeMillis();//距离1970年的毫秒数
        d = d-dateStart.getTime();//设置起始时间为2014-9-1
        long pk = setValue(origin,d,1,38);//0为正负号，1到38位为毫秒时间，可以储存2^38毫秒，使用8.7年
        pk = setValue(pk,number,39,63);//设置39到63位为number，可储存2^25-1,即33554431以内的数字
        return pk;
    }

    /**
     * 从simpleLong中获取时间
     * @return
     */
    public static String getDateTimeFromLong(long l) throws Exception {
        long diffTime = getValue(l,1,38);
        long tm = dateStart.getTime()+diffTime;
        Date date = new Date(tm);
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
    }


    /**
     *  生成标准id
     * @param country    国家编号，范围0-3
     * @param city      城市编号，范围0-7
     * @param l1center  一级计算中心，范围0-3
     * @param l2center  二级计算中心，范围0-63
     * @param number    随机数，范围0-4095
     * @return
     * @throws Exception
     */
    public static long createStandardLong(int country,int city,int l1center,int l2center,int number) throws Exception {
        long origin = 0L;
        long d = System.currentTimeMillis();//距离1970年的毫秒数
        d = d-dateStart.getTime();//设置起始时间为2014-9-1
        long pk = setValue(origin,d,1,38);//0为正负号，1到38位为毫秒时间，可以储存2^38毫秒，使用8.7年
        pk = setValue(pk, country, 39, 40);//设置39到40位为国家，可储存2^2-1,即0-3以内的数字，四个国家
        pk = setValue(pk, city, 41, 43);//设置41到43位为城市，可储存2^3-1,即0-7以内的数字，八个城市
        pk = setValue(pk, l1center, 44, 45);//设置44到45位为一级计算中心，可储存2^2-1,即0-3以内的数字，四个一级计算中心
        pk = setValue(pk, l2center, 46, 51);//设置46到49位为二级计算中心，可储存2^6-1,即0-63以内的数字，六十四个二级计算中心
        pk = setValue(pk, number, 52, 63);//设置52到63位为随机数，可储存2^12-1,即0-4095以内的数字，支持1毫秒4096的并发
        return pk;
    }

    /**
     * 解析一个pk，返回值为long[6]数组，下标极其内容为：
     * result[0]:时间，距离1970年的毫秒数
     * result[1]:国家，范围0-3
     * result[2]:城市，范围0-7
     * result[3]:一级计算中心，范围0-3
     * result[4]:二级计算中心，范围0-63
     * result[5]:随机数，范围0-4095
     * @param pk
     * @return
     * @throws Exception
     */
    public static long[] parseStandardLong(long pk) throws Exception {
        long[] result = new long[6];
        result[0] = getValue(pk,1,38)+dateStart.getTime();
        result[1] = getValue(pk,39,40);
        result[2] = getValue(pk,41,43);
        result[3] = getValue(pk,44,45);
        result[4] = getValue(pk,46,51);
        result[5] = getValue(pk,52,63);
        return result;
    }



    public static void main(String[] args) throws Exception {

        long origin = 0L;
        long d = System.currentTimeMillis();
        d = d-new Date(2015-1900,11,1).getTime();
        long val = Long.valueOf(new Random().nextLong());
        long pk = setValue(origin,d,1,29);
        System.out.println(d);
        System.out.println(pl64(d));
        pk = setValue(pk,15,30,33);
        pk = setValue(pk,15,34,35);
        System.out.println(getValue(pk,1,29));
        System.out.println(pl64(getValue(pk, 1, 29)));
        System.out.println(getValue(pk, 30, 33));
        System.out.println(getDateTimeFromLong(createSimpleLong()));

    }
}