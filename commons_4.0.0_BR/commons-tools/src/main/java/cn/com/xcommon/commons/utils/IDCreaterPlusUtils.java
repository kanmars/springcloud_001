package cn.com.xcommon.commons.utils;

/**
 * Created by zhaojiuyang on 2015/12/10.
 */

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
    public static long createMask(int start,int end) throws Exception{
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
     * 打印一个long的二进制码，不分割
     * @param l
     * @return
     */
    public static String pl64(long l){
        return pl64(l,false);
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
     * 打印l 的从0开始第index位的数据
     * @param l
     * @param index
     */
    public static String pl(long l,int index){
        return (0x0000000000000001L & (l>> (63-index)))+"";
    }

    public static void main(String[] args) throws Exception {
        long origin = 0L;
        long d = System.currentTimeMillis();
        d = d-new Date(2015,11,1).getTime();
//1449729872734
        long val = Long.valueOf(new Random().nextLong());
        long pk = setValue(origin,d,1,29);
        pk = setValue(pk,15,30,33);
        pk = setValue(pk,15,34,35);
    }

}