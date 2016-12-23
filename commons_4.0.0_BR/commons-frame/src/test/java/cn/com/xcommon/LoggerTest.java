package cn.com.xcommon;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;

/**
 * Created by baolong on 2016/9/29.
 */
public class LoggerTest {
    public static final HLogger logger = LoggerManager.getLoger("LoggerTest");
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
//        for(int i=0;i<1000000;i++){
        while(true){
            logger.info("交易信息");
        }
//        long end = System.currentTimeMillis();
//        System.out.println("打印一百万条日志耗时["+(end-start)+"]毫秒");
    }

    public static void execA(){
        logger.info("交易信息A");
        execB();
    }
    public static void execB(){
        logger.info("交易信息B");
        execC();
    }
    public static void execC(){
        logger.info("交易信息C");
        execD();
    }
    public static void execD(){
        logger.info("交易信息D");
    }
}
