package cn.kanmars.sn.util;

import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.entity.TblSysDic;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/1/18.
 */
public class DicSelect {

    private static Object instance;

    /**
     * 单例模式获取当前类的实例，不考虑多线程问题
     * @return
     */
    public synchronized static Object getInstance(){
        if(instance==null){
            instance = new DicSelect();
        }
        return instance;
    }

    public String get(String key){
        if(StringUtils.isEmpty(key)||key.indexOf(",")<0){
            return "<option>错误数据</option>";
        }
        String l1Code = key.substring(0,key.indexOf(","));
        String l2Code = key.substring(key.indexOf(",")+1);
        List<TblSysDic> list = SysDicUtils.getValueList(l1Code, l2Code);
        StringBuilder sb = new StringBuilder();
        for(TblSysDic dic:list){
            sb.append("<option value='"+dic.getCodeValue()+"'>");
            sb.append(dic.getCodeParam());
            sb.append("</option>");
        }
        return sb.toString();
    }
}
