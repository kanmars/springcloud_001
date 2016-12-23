package cn.kanmars.sn.util;

import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.entity.TblSysDic;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/1/18.
 */
public class DicCheckbox {

    private static Object instance;

    /**
     * 单例模式获取当前类的实例，不考虑多线程问题
     * @return
     */
    public synchronized static Object getInstance(){
        if(instance==null){
            instance = new DicCheckbox();
        }
        return instance;
    }

    public List get(String key){
        List<String> result = new ArrayList<String>();
        if(StringUtils.isEmpty(key)||key.indexOf(",")<0){
            return result;
        }
        String l1Code = key.substring(0,key.indexOf(","));
        String l2Code = key.substring(key.indexOf(",") + 1);
        List<TblSysDic> list = SysDicUtils.getValueList(l1Code, l2Code);

        for(TblSysDic dic : list){
            result.add(dic.getCodeValue());
        }
        return result;
    }
}
