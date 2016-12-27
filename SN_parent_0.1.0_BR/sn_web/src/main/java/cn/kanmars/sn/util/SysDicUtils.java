package cn.kanmars.sn.util;

import cn.com.xcommon.frame.util.DateFormatUtils;
import cn.com.xcommon.frame.util.DateUtils;
import cn.kanmars.sn.feign.Sn_SysDicFacadeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/12/27.
 */
@Component
public class SysDicUtils {
    /** 精确到第三层级的字典 */
    protected static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, HashMap>>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, HashMap>>>();
    /** 精确到第二层级的列表字典 */
    protected static ConcurrentHashMap<String, ConcurrentHashMap<String, List<HashMap>>> map_list = new ConcurrentHashMap<String, ConcurrentHashMap<String, List<HashMap>>>();

    @Autowired
    private Sn_SysDicFacadeServiceInterface sn_SysDicFacadeServiceInterface_;

    private static Sn_SysDicFacadeServiceInterface sn_SysDicFacadeServiceInterface;

    @PostConstruct
    public void init(){
        sn_SysDicFacadeServiceInterface = sn_SysDicFacadeServiceInterface_;
    }

    /**
     * 传入参数，先从本地获取码值<br>
     * 如果码值不存在，则从数据库中查询码值并返回并更新本地缓存<br>
     *
     * @param l1Code
     * @param l2Code
     * @param codeParam
     * @return
     */
    public static String getValue(String l1Code, String l2Code, String codeParam) {

        String datetime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        // 如果三级路径存在
        if (map.get(l1Code) != null && map.get(l1Code).get(l2Code) != null
                && map.get(l1Code).get(l2Code).get(codeParam) != null) {
            HashMap tblSysDic = map.get(l1Code).get(l2Code).get(codeParam);
            String expireTime = DateUtils.countTime(tblSysDic.get("upTime").toString(), Integer.parseInt(tblSysDic.get("liveCount").toString()));
            if (expireTime.compareTo(datetime) >= 0) {// 例如，内存中是20151231000000而datetime是20150101000000，则不过期
                // 未超时
                return tblSysDic.get("codeValue").toString();
            } else {
                // 已超时
                // 重新加载
            }
        }
        // 数据库查询
        HashMap tblSysDic = getSysDicInDB(l1Code, l2Code, codeParam);
        // 数据库中不存在
        if (tblSysDic == null) {
            return null;
        }
        // 数据库中存在
        // 1、检验一级路径，如果一级路径不存在，则新建一级路径
        if (map.get(l1Code) == null) {
            map.put(l1Code, new ConcurrentHashMap<String, ConcurrentHashMap<String, HashMap>>());
        }
        // 2、检验二级路径，如果二级路径不存在，则新建二级路径
        if (map.get(l1Code).get(l2Code) == null) {
            map.get(l1Code).put(l2Code,	new ConcurrentHashMap<String, HashMap>());
        }
        // 3、放入codeParam码值并计算过期时间
        // 3.1 设置加载时间
        tblSysDic.put("upTime",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        // 3.2 放入码值
        map.get(l1Code).get(l2Code).put(codeParam, tblSysDic);

        return tblSysDic.get("codeValue").toString();
    }

    /**
     * 传入一级二级编码，获取值的列表，如果改二级编码对应的数据不存在，则返回一个空List
     *
     * @param l1Code
     *            一级码表
     * @param l2Code
     *            二级码表
     * @return
     */
    public static List<HashMap> getValueList(String l1Code, String l2Code) {
        String datetime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        // 如果二级路径存在
        if (map_list.get(l1Code) != null
                && map_list.get(l1Code).get(l2Code) != null) {
            List<HashMap> l2list = map_list.get(l1Code).get(l2Code);
            // 判断是否有超时
            boolean isExpired = false;
            for (HashMap tblSysDic : l2list) {
                String expireTime = DateUtils.countTime(tblSysDic.get("upTime").toString(),Integer.parseInt(tblSysDic.get("liveCount()").toString()));
                if (expireTime.compareTo(datetime) >= 0) {// 例如，内存中是20151231000000而datetime是20150101000000，则不过期
                    // 未超时
                } else {
                    // 已超时
                    isExpired = true;
                }
            }
            if (!isExpired) {
                // 未超时则返回
                return l2list;
            }
        }
        // 数据库查询
        List<HashMap> list = getSysDicListInDB(l1Code, l2Code);
        // 数据库中不存在
        if (list == null || list.size() == 0) {
            return new ArrayList<HashMap>();
        }
        // 数据库中存在
        // 1、检验一级路径，如果一级路径不存在，则新建一级路径
        if (map_list.get(l1Code) == null) {
            map_list.put(l1Code,new ConcurrentHashMap<String, List<HashMap>>());
        }
        // 2、创建二级路径
        // 2.1 设置加载时间
        for (HashMap tblSysDic : list) {
            tblSysDic.put("upTime",DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        }
        // 2.2放入二级路径
        map_list.get(l1Code).put(l2Code, list);
        return list;
    }


    /**
     * 实时数据库查询列表
     *
     * @param l1Code
     * @param l2Code
     * @param codeParam
     * @return
     */
    public static HashMap getSysDicInDB(String l1Code, String l2Code, String codeParam) {
        Map<String,Object> result = sn_SysDicFacadeServiceInterface.getOne(l1Code,l2Code,codeParam);
        if(((String)(result.get("resCode"))).equals("0000")){
            return (HashMap)result.get("result");
        }else{
            return null;
        }
    }

    /**
     * 实时数据库查询列表
     *
     * @param l1Code
     * @param l2Code
     * @return
     */
    public static List<HashMap> getSysDicListInDB(String l1Code, String l2Code) {
        Map<String,Object> result = sn_SysDicFacadeServiceInterface.getList(l1Code,l2Code);
        if(((String)(result.get("resCode"))).equals("0000")){
            return (List<HashMap>)result.get("result");
        }else{
            return null;
        }
    }
}
