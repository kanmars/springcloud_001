package cn.kanmars.sn.util;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.cache.IdValue;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.DateFormatUtils;
import cn.com.xcommon.frame.util.DateUtils;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.entity.TblSysDic;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据字典工具类，使用示例，本版本为MYSQL版本<br>
 * System.out.println(SysDicUtils.getValue("TBL_PACKAGE_INFO", "PACKAGE_STAT", "020"));
  drop table if exists tbl_sys_dic;
  create table tbl_sys_dic
        (
        id                   int(64) not null comment 'ID',
        l1_code              VARCHAR(32) not null comment '一级字典码',
        l1_desc              VARCHAR(512) not null comment '一级字典描述',
        l2_code              VARCHAR(32) not null comment '二级字典码',
        l2_desc              VARCHAR(512) not null comment '二级字典描述',
        code_param           VARCHAR(64) not null comment '码表参数',
        code_value           VARCHAR(512) not null comment '实际值',
        code_idx             int(32) not null comment '排序',
        live_count           int(32) not null comment'存活时间（秒）',
        create_time          char(14) not null comment'添加时间',
        up_time              char(14) not null comment '更新时间',
        primary key (id)
        );

        alter table tbl_sys_dic comment '系统字典表';
 *
 * 使用场景：有数据库访问，需要数据字典的系统，例如sn-admin,sn-service,sn-task等，严禁sn-web和sn-zuul使用
 */
public class SysDicUtils implements ApplicationContextAware {

    private static HLogger logger= LoggerManager.getLoger("SysDicUtils");
    private static final String TYPE_DB="DB";
    private static final String TYPE_INNER="INNER";
    private static String type=TYPE_DB;//DB或者INNER，代表分别在数据库层储存数据字典，或者在代码层储存数据字典

    /** 精确到第三层级的字典 */
    protected static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, TblSysDic>>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, TblSysDic>>>();
    /** 精确到第二层级的列表字典 */
    protected static ConcurrentHashMap<String, ConcurrentHashMap<String, List<TblSysDic>>> map_list = new ConcurrentHashMap<String, ConcurrentHashMap<String, List<TblSysDic>>>();

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private TblSysDicMapper tblSysDicMapper_;

    private static TblSysDicMapper tblSysDicMapper;

    @PostConstruct
    public synchronized void init(){
        try{
            tblSysDicMapper_ = (TblSysDicMapper)applicationContext.getBean("tblSysDicMapper");
            tblSysDicMapper = tblSysDicMapper_;
            if(tblSysDicMapper_==null){
                logger.info("SysDicUtils在初始化时发生异常，选择使用内部数据字典TYPE_INNER");
                type = TYPE_INNER;
            }
            fillApplicationCache();
            final long startTime = System.currentTimeMillis();
            new Thread(new Runnable() {
                public void run() {
                    while(true){
                        fillApplicationCache();
                        long now = System.currentTimeMillis();
                        try {
                            if((now-startTime)<=(24*60*60*1000)){
                                //启动后一天之内
                                Thread.sleep(30000);//每30秒刷新一次缓存
                            }else{
                                //启动后一天之后
                                Thread.sleep(60*1000*5);//每五分钟刷新一次缓存
                            }
                        } catch (InterruptedException e) {
                            logger.error("刷新数据字典异常",e);
                        }
                    }
                }
            }).start();

        }catch (Exception e){
            logger.debug("SysDicUtils在初始化时发生异常，选择使用内部数据字典TYPE_INNER", e);
            type = TYPE_INNER;
            fillApplicationCache();
        }

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
            TblSysDic tblSysDic = map.get(l1Code).get(l2Code).get(codeParam);
            String expireTime = DateUtils.countTime(tblSysDic.getUpTime(), tblSysDic.getLiveCount());
            if (expireTime.compareTo(datetime) >= 0) {// 例如，内存中是20151231000000而datetime是20150101000000，则不过期
                // 未超时
                return tblSysDic.getCodeValue();
            } else {
                // 已超时
                // 重新加载
            }
        }
        // 数据库查询
        TblSysDic tblSysDic = getSysDicInDB(l1Code, l2Code, codeParam);
        // 数据库中不存在
        if (tblSysDic == null) {
            return null;
        }
        // 数据库中存在
        // 1、检验一级路径，如果一级路径不存在，则新建一级路径
        if (map.get(l1Code) == null) {
            map.put(l1Code,	new ConcurrentHashMap<String, ConcurrentHashMap<String, TblSysDic>>());
        }
        // 2、检验二级路径，如果二级路径不存在，则新建二级路径
        if (map.get(l1Code).get(l2Code) == null) {
            map.get(l1Code).put(l2Code,	new ConcurrentHashMap<String, TblSysDic>());
        }
        // 3、放入codeParam码值并计算过期时间
        // 3.1 设置加载时间
        tblSysDic.setUpTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        // 3.2 放入码值
        map.get(l1Code).get(l2Code).put(codeParam, tblSysDic);

        return tblSysDic.getCodeValue();
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
    public static List<TblSysDic> getValueList(String l1Code, String l2Code) {
        String datetime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        // 如果二级路径存在
        if (map_list.get(l1Code) != null
                && map_list.get(l1Code).get(l2Code) != null) {
            List<TblSysDic> l2list = map_list.get(l1Code).get(l2Code);
            // 判断是否有超时
            boolean isExpired = false;
            for (TblSysDic tblSysDic : l2list) {
                String expireTime = DateUtils.countTime(tblSysDic.getUpTime(),tblSysDic.getLiveCount());
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
        List<TblSysDic> list = getSysDicListInDB(l1Code, l2Code);
        // 数据库中不存在
        if (list == null || list.size() == 0) {
            return new ArrayList<TblSysDic>();
        }
        // 数据库中存在
        // 1、检验一级路径，如果一级路径不存在，则新建一级路径
        if (map_list.get(l1Code) == null) {
            map_list.put(l1Code,new ConcurrentHashMap<String, List<TblSysDic>>());
        }
        // 2、创建二级路径
        // 2.1 设置加载时间
        for (TblSysDic tblSysDic : list) {
            tblSysDic.setUpTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
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
    public static TblSysDic getSysDicInDB(String l1Code, String l2Code,
                                          String codeParam) {
        TblSysDic tblSysDic = new TblSysDic();
        tblSysDic.setL1Code(l1Code);
        tblSysDic.setL2Code(l2Code);
        tblSysDic.setCodeParam(codeParam);
        try {
            tblSysDic = tblSysDicMapper.select(tblSysDic);
        } catch (Exception e) {
            logger.error("获取数据字典异常",e);
            tblSysDic = null;
        }
        return tblSysDic;
    }

    /**
     * 实时数据库查询列表
     *
     * @param l1Code
     * @param l2Code
     * @return
     */
    public static List<TblSysDic> getSysDicListInDB(String l1Code, String l2Code) {
        List<TblSysDic> list = new ArrayList<TblSysDic>();

        TblSysDic tblSysDic = new TblSysDic();
        tblSysDic.setL1Code(l1Code);
        tblSysDic.setL2Code(l2Code);
        try {
            list = tblSysDicMapper.selectList(tblSysDic);
            Collections.sort(list, new Comparator<TblSysDic>() {
                public int compare(TblSysDic o1, TblSysDic o2) {
                    return o1.getCodeIdx().compareTo(o2.getCodeIdx());
                }
            });
        } catch (Exception e) {
            logger.error("获取数据字典异常", e);
            tblSysDic = null;
        }
        return list;
    }

    /**
     * 将数据字典中的值填充入ApplicationCache中
     */
    private static void fillApplicationCache(){
        List<TblSysDic> list = new ArrayList<TblSysDic>();

        TblSysDic tblSysDic = new TblSysDic();
        try {
            if(type.equals(TYPE_DB)){
                list = tblSysDicMapper.selectList(tblSysDic);
            }else if(type.equals(TYPE_INNER)){
                list = getInnerSysDic();
            }

            /**
             * 给Application设置直接的映射，从二级编码映射到具体的值
             * 例如
             * TBL_INVESMENT_TARGET   INVEST_STAT   010  待审核
             * TBL_INVESMENT_TARGET   INVEST_STAT   020  审核通过
             * 则在ApplicationCache中添加
             * key   是INVEST_STAT
             * value是ArrayList\<IdValue\>    的010  待审核，020审核通过
             * 的数据
             * 1、排序
             */
            Collections.sort(list, new Comparator<TblSysDic>() {
                public int compare(TblSysDic o1, TblSysDic o2) {
                    return o1.getCodeIdx().compareTo(o2.getCodeIdx());
                }
            });
            /**
             * 2、遍历并放入
             */
			/*临时对象*/
            HashMap<String,List<IdValue>> tmpHashMap = new HashMap<String, List<IdValue>>();
            HashMap<String,HashMap<String,IdValue>> tmpHashMap2 = new HashMap<String, HashMap<String,IdValue>>();
			/*拼装临时对象*/
            IdValue idval = null;
            for(TblSysDic sysDic : list){
                idval = new IdValue(sysDic.getCodeParam(),sysDic.getCodeValue(),false);
                if(tmpHashMap.get(sysDic.getL2Code())==null){
                    tmpHashMap.put(sysDic.getL2Code(), new ArrayList<IdValue>());
                }
                tmpHashMap.get(sysDic.getL2Code()).add(idval);

                if(tmpHashMap2.get(sysDic.getL2Code())==null){
                    tmpHashMap2.put(sysDic.getL2Code(), new HashMap<String,IdValue>());
                }
                tmpHashMap2.get(sysDic.getL2Code()).put(sysDic.getCodeParam(), idval);
            }
			/*将临时对象放入ApplicationCache*/
            for(Entry<String,List<IdValue>> entry: tmpHashMap.entrySet()){
                ApplicationCache.getInstance().putIdValue(entry.getKey(), entry.getValue());
            }
			/*将数据字典放入如INVEST_STAT.010放入ApplicationCache*/
            for(Entry<String,HashMap<String,IdValue>> entry: tmpHashMap2.entrySet()){
                String l2_code = entry.getKey();
                for(Entry<String,IdValue> e : entry.getValue().entrySet()){
                    ApplicationCache.getInstance().put(l2_code+"."+e.getKey(), e.getValue().getValue());
                }
            }
        } catch (Exception e) {
            logger.error("填充ApplicationCache异常",e);
            tblSysDic = null;
        }
    }
    private static List<TblSysDic> getInnerSysDic(){
        List<String> strList = new ArrayList<String>();
        //id  l1_code   l1_desc   l2_code   l2_desc  code_param   code_value code_idx liveCount createTime  uptime
        strList.add("1;    application_code;   application_code   ;    application_code    ;    application_code    ;    010  ;  系统A    ;    1    ;    60    ;    20160127164600    ;    20160127164600  ");
        strList.add("2;    application_code;   application_code   ;    application_code    ;    application_code    ;    020  ;  系统B    ;    2    ;    60    ;    20160127164600    ;    20160127164600  ");
        strList.add("3;    application_code;   application_code   ;    application_code    ;    application_code    ;    030  ;  系统C    ;    3    ;    60    ;    20160127164600    ;    20160127164600  ");
        strList.add("4;    tbl_sys_config  ;   tbl_sys_config     ;    tbl_sys_config      ;    tbl_sys_config      ;    010  ;  有效     ;    1    ;    60    ;    20160127164600    ;    20160127164600  ");
        strList.add("5;    tbl_sys_config  ;   tbl_sys_config     ;    tbl_sys_config      ;    tbl_sys_config      ;    020  ;  无效     ;    2    ;    60    ;    20160127164600    ;    20160127164600  ");
        List<TblSysDic> result = new ArrayList<TblSysDic>();
        for(String s : strList){
            String[] strArray = s.split(";");
            TblSysDic sysDic = new TblSysDic();
            sysDic.setId(Integer.parseInt(strArray[0].trim()));
            sysDic.setL1Code(strArray[1].trim());
            sysDic.setL1Desc(strArray[2].trim());
            sysDic.setL2Code(strArray[3].trim());
            sysDic.setL2Desc(strArray[4].trim());
            sysDic.setCodeParam(strArray[5].trim());
            sysDic.setCodeValue(strArray[6].trim());
            sysDic.setCodeIdx(Integer.parseInt(strArray[7].trim()));
            sysDic.setLiveCount(Integer.parseInt(strArray[8].trim()));
            sysDic.setCreateTime(strArray[9].trim());
            sysDic.setUpTime(strArray[10].trim());
            result.add(sysDic);
        }
        return result;
    }
}
