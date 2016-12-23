package cn.kanmars.sn.logic.impl;

import cn.com.sn.frame.exception.SnCommonException;
import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.logic.ResultEnum;
import cn.kanmars.sn.logic.ALogic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by baolong on 2016/12/15.
 */
@Component
@Transactional
public class ALogicImpl implements ALogic {

    private HLogger logger = LoggerManager.getLoger("ALogic");

    @Override
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ResultEnum exec(Object o) throws Exception {
        logger.info("ALogic.exec.start");
        logger.info("ALogic.exec.end");
        return ResultEnum.PartOK;
    }

}
