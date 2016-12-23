package cn.kanmars.sn.util;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.util.MapObjTransUtils;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.entity.TblSysDic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baolong on 2016/1/27.
 */
public class DicList {

    protected HLogger logger = LoggerManager.getLoger("DicList");

    private static Object instance;

    /**
     * 单例模式获取当前类的实例，不考虑多线程问题
     * @return
     */
    public synchronized static Object getInstance(){
        if(instance==null){
            instance = new DicList();
        }
        return instance;
    }

    public List get(String key){
        List result = new ArrayList();
        if(StringUtils.isEmpty(key)||key.indexOf(",")<0){
            return result;
        }
        String l1Code = key.substring(0,key.indexOf(","));
        String l2Code = key.substring(key.indexOf(",") + 1);
        List<TblSysDic> list = SysDicUtils.getValueList(l1Code, l2Code);

        for(TblSysDic dic : list){
            try {
                result.add(MapObjTransUtils.objectToMap(dic));
            } catch (Exception e) {
                logger.error("对象转换异常",e);
            }
        }
        return result;
    }
}
