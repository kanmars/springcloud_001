package cn.kanmars.sn.util;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by baolong on 2016/1/8.
 */
public class PageQueryUtil {

    /**数据统计标记-*/
    public static final String COUNTFLAG = "countFlag";
    /**数据统计标志-Y*/
    public static final String COUNTFLAG_Y = "Y";
    /**数据统计标志-N*/
    public static final String COUNTFLAG_N = "N";
    /**分页开始数量*/
    public static final String LIMITSIZE = "limitSize";
    /**分页每页大小*/
    public static final String LIMITSTART = "limitStart";
    /**记录总记录数*/
    public static final String TOTALCOUNT = "TOTALCOUNT";
    /**记录总页数*/
    public static final String TOTALPAGE = "totalPage";
    /**记录起始页*/
    public static final String STARTINDEX = "startIndex";
    /**记录每页数量*/
    public static final String PAGESIZE = "pageSize";
    /**记录每页数据*/
    public static final String PAGERECORDS = "pageRecords";
    /**
     * 分页查询工具类
     * @param mapper    mapper类
     * @param startIndex    开始页数，如果传入小于0的数字，则将其设置为默认值1
     * @param pageSize  分页的页数大小,如果传入小于等于0的数字，则将其设置为默认值10
     * @param paramMap  参数列表
     * @return
     * @throws Exception
     */
    public static HashMap pageQuery(Object mapper,int startIndex,int pageSize,HashMap paramMap) throws Exception {
        HashMap result = new HashMap();
        if(startIndex<=0)startIndex=1;
        if(pageSize<=0)pageSize=10;
        if(paramMap==null)paramMap = new HashMap();

        result.putAll(paramMap);

        Class cls = mapper.getClass();
        Method method = cls.getMethod("queryForPage",new Class[]{HashMap.class});

        //以下算法，涉及记录数的用long，涉及页数的用int
        //1、查询总数
        paramMap.put(PageQueryUtil.COUNTFLAG,PageQueryUtil.COUNTFLAG_Y);
        List<HashMap> resultCountList = (List<HashMap>)method.invoke(mapper,paramMap);
        Long totalCount = Long.parseLong(resultCountList.get(0).get(PageQueryUtil.TOTALCOUNT).toString());
        result.put(PageQueryUtil.TOTALCOUNT,totalCount);
        //2、计算分页信息
        //2.1计算总页数
        int totalPage = (int)(totalCount/pageSize + (totalCount%pageSize>0?1:0));
        result.put(PageQueryUtil.TOTALPAGE,totalPage);
        //2.2设置当前页和当前页记录数量
        //如果总页数超出，则设置为最后一页
        if(startIndex > totalPage){
            startIndex = totalPage;
            //如果totalPage为0，则设置startIndex为1，避免表中没有数据的情况
            if(totalPage==0)startIndex=1;
        }
        result.put(PageQueryUtil.STARTINDEX,startIndex);
        result.put(PageQueryUtil.PAGESIZE,pageSize);

        //3、查询分页数据
        paramMap.put(PageQueryUtil.COUNTFLAG,PageQueryUtil.COUNTFLAG_N);
        paramMap.put(PageQueryUtil.LIMITSTART,(startIndex-1)*pageSize);
        paramMap.put(PageQueryUtil.LIMITSIZE,pageSize);
        List<HashMap<String, Object>> pageRecordList = (List<HashMap<String, Object>>)method.invoke(mapper,paramMap);
        if(pageRecordList==null)pageRecordList = new ArrayList<HashMap<String, Object>>();

        //4、对查询到的数据进行驼峰格式化
        pageRecordList = formatHashMapKeyInList(pageRecordList);

        //5、将数据放到返回结果中
        result.put(PageQueryUtil.PAGERECORDS, pageRecordList);

        return result;
    }

    /**
     * 数据库列名转化为属性名,如DEAL_ID=dealId; <br>
     * 不能保证完全正确，如DBUTIL不会智能的转化为DBUtil,而会转化为dbutil, <br>
     * 规则为全部转化为单词，然后首字母小写
     *
     * @param DBName
     * @return
     */

    public static String formatDBNameToVarName(String DBName) {
        StringBuilder result = new StringBuilder("");
        // 以"_"分割
        String[] DBNameArr = DBName.split("_");
        for (int i = 0, j = DBNameArr.length; i < j; i++) {
            // 获取以"_"分割后的字符数组的每个元素的第一个字母，第一个小写，其他的大写
            if(i==0){
                result.append((DBNameArr[i].charAt(0)+"").toLowerCase());
            }else{
                result.append((DBNameArr[i].charAt(0)+"").toUpperCase());
            }
            // 将其他字符转换成小写
            result.append(DBNameArr[i].substring(1).toLowerCase());
        }
        char c0 = result.charAt(0);
        if (c0 >= 'A' && c0 <= 'Z')
            c0 = (char) (c0 + 'a' - 'A');
        result.setCharAt(0, c0);
        return result.toString();
    }

    /**
     * 将hashMap的key转化为驼峰格式，如{"BRH_ID":"1234","BRH_NAME":"机构"}改为{"brhId":"1234",
     * "brhName":"机构"}
     *
     * @param hashMap
     * @return
     */
    public static HashMap<String, Object> formatHashMapKey(
            HashMap<String, Object> hashMap) {
        HashMap result = new HashMap();
        String key = null;
        // 遍历map
        for (Map.Entry<String, Object> e : (Set<Map.Entry<String, Object>>) hashMap
                .entrySet()) {
            key = (String) e.getKey();
            // 将hashMap的key转化为驼峰格式
            key = formatDBNameToVarName(key);
            // 封装为新的map
            result.put(key, e.getValue());
        }
        // 返回格式化后的map
        return result;
    }

    public static List<HashMap<String, Object>> formatHashMapKeyInList(List<HashMap<String, Object>> list){
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for(HashMap<String, Object> cell : list){
            result.add(formatHashMapKey(cell));
        }
        return result;
    }
}
