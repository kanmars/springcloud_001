package cn.kanmars.sn.logic.impl;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.entity.TblSysDic;
import cn.kanmars.sn.util.SysSequenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by baolong on 2016/1/12.
 */
@Transactional
@Service
public class TransactionLogicImpl implements cn.kanmars.sn.logic.TransactionLogic {

    protected static HLogger logger = LoggerManager.getLoger("TransactionLogicImpl");

    @Autowired
    private TblSysDicMapper tblSysDicMapper;

    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void exec() throws Exception {

        logger.info(SysSequenceUtils.generateStringId("aaa"));
        logger.info(SysSequenceUtils.generateStringId("bbb"));
        logger.info(SysSequenceUtils.generateStringId("ccc"));
        logger.info(SysSequenceUtils.generateStringId("ddd"));

    }

    public void exec1() throws Exception {
        TblSysDic tblSysDic = new TblSysDic();
        tblSysDic.setId(1);
        tblSysDic.setL1Code("0001");
        tblSysDic.setL1Desc("0001");
        tblSysDic.setL2Code("0001");
        tblSysDic.setL2Desc("0001");
        tblSysDic.setCodeParam("0001");
        tblSysDic.setCodeValue("0001");
        tblSysDic.setCodeIdx(1);
        tblSysDic.setLiveCount(60);
        tblSysDic.setCreateTime("20160112150900");
        tblSysDic.setUpTime("20160112150900");

        int i = tblSysDicMapper.insert(tblSysDic);

        logger.info("插入数据["+i+"]条");
    }

}
