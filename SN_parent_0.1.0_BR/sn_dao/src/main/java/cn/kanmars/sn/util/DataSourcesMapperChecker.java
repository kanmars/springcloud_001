package cn.kanmars.sn.util;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.entity.TblSysDic;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 测试类，验证mapper使用是否正常
 * Created by baolong on 2016/1/8.
 */
public class DataSourcesMapperChecker {

    public static final HLogger logger = LoggerManager.getLoger("DataSourcesMapperChecker");

    public static void select() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //select
        TblSysDic tblSysDic = new TblSysDic();
        tblSysDic.setId(1);
        tblSysDic = tblSysDicMapper.select(tblSysDic);
        logger.info(JSONObject.fromObject(tblSysDic).toString());
    }
    public static void selectList() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        TblSysDic tblSysDic = new TblSysDic();
        //selectList
        tblSysDic = new TblSysDic();
        tblSysDic.setL1Code("0001");
        List<TblSysDic> list = tblSysDicMapper.selectList(tblSysDic);
        logger.info(JSONArray.fromObject(list).toString());
    }
    public static void insert() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        TblSysDic tblSysDic = new TblSysDic();
        //insert
        tblSysDic.setId(new Random().nextInt());
        tblSysDic.setL1Code("0002");
        tblSysDic.setL1Desc("0001");
        tblSysDic.setL2Code("01");
        tblSysDic.setL2Desc("01");
        tblSysDic.setCodeParam(new Random().nextInt() + "");
        tblSysDic.setCodeValue(new Random().nextInt() + "");
        tblSysDic.setCodeIdx(new Random().nextInt());
        tblSysDic.setLiveCount(60);
        tblSysDic.setCreateTime("20160108202400");
        tblSysDic.setUpTime("20160108202400");
        int i = tblSysDicMapper.insert(tblSysDic);
    }

    public static void updateByPrimaryKey() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        TblSysDic tblSysDic = new TblSysDic();
        //updateByPrimaryKey
        tblSysDic.setId(1);
        tblSysDic.setL1Code("0001-l1_code");
        tblSysDic.setL1Desc("0001-l1_desc");
        tblSysDic.setL2Code("0001-l2_code");
        tblSysDic.setL2Desc("0001-l2_desc");
        tblSysDic.setCodeParam(new Random().nextInt() + "");
        tblSysDic.setCodeValue(new Random().nextInt() + "");
        tblSysDic.setCodeIdx(new Random().nextInt());
        tblSysDic.setLiveCount(60);
        tblSysDic.setCreateTime("20160108202400");
        tblSysDic.setUpTime("20160108202400");
        int ii = tblSysDicMapper.updateByPrimaryKey(tblSysDic);
    }

    public static void delete() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        TblSysDic tblSysDic = new TblSysDic();
        //delete
        tblSysDic.setId(-767524798);
        tblSysDic.setL1Desc("0001");
        int iii = tblSysDicMapper.delete(tblSysDic);
    }

    public static void queryOneMap() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //queryOneMap
        HashMap paramMap = new HashMap();
        paramMap.put("id", 1);
        paramMap.put("l1Code", "0001-l1_code");
        HashMap resultMap = tblSysDicMapper.queryOneMap(paramMap);
        logger.info(resultMap.toString());
    }

    public static void queryListMap() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //queryListMap
        HashMap paramMap = new HashMap();
        paramMap.put("l1Code", "0001");
        List<HashMap> resultList = tblSysDicMapper.queryListMap(paramMap);
        logger.info(resultList.toString());
    }

    public static void updateCAS() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //updateCAS
        HashMap paramMap = new HashMap();
        paramMap.put("codeValue_new", "9999");
        paramMap.put("codeValue", "kkk");
        int iiii = tblSysDicMapper.updateCAS(paramMap);
    }

    public static void queryForPage() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //queryForPage
        HashMap paramMap = new HashMap();
        paramMap.put("countFlag", "Y");
        List<HashMap> resultCountList = tblSysDicMapper.queryForPage(paramMap);
        logger.info(resultCountList.get(0).get("totalCount").toString());
    }

    public static void updateBatch() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //updateBatch
        List<HashMap> updataBatch = new ArrayList<HashMap>();
        HashMap tmp1 = new HashMap();
        tmp1.put("id", 1);
        tmp1.put("l1Code_new", "kkkkkk1");
        updataBatch.add(tmp1);

        HashMap tmp2 = new HashMap();
        tmp2.put("id", 2);
        tmp2.put("l1Code_new", "kkkkkk2");
        updataBatch.add(tmp2);

        HashMap tmp3 = new HashMap();
        tmp3.put("id", 3);
        tmp3.put("l1Code_new", "kkkkkk3");
        updataBatch.add(tmp3);

        int iiiiii = tblSysDicMapper.updateBatch(updataBatch);
        logger.info(iiiiii + "");
    }

    public static void selectByPrimaryKey() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        //selectByPrimaryKey
        TblSysDic tblSysDic = tblSysDicMapper.selectByPrimaryKey(1);
        logger.info(JSONObject.fromObject(tblSysDic).toString());
    }

    public static void pageQuery() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/sn-local-dataSource.xml");
        TblSysDicMapper tblSysDicMapper =(TblSysDicMapper)ac.getBean("tblSysDicMapper");
        Object result =  PageQueryUtil.pageQuery(tblSysDicMapper, 1, 10, null);
        logger.info(result.toString());
        logger.info("OK");
    }

    public static void main(String[] args) throws Exception {

    }
}
