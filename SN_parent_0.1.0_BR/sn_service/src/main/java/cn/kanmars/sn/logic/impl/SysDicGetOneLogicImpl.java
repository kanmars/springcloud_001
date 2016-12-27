/**
 * SN Generator 
 */
package cn.kanmars.sn.logic.impl;

import cn.com.xcommon.frame.exception.SnCommonException;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.logic.ResultEnum;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.logic.SysDicGetOneLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 根据l1_code、l2_code、paramCode获取Value
 */
@Component
@Transactional
public class SysDicGetOneLogicImpl implements SysDicGetOneLogic{

    protected HLogger logger = LoggerManager.getLoger("SysDicGetOneLogicImpl");

    @Autowired
    private TblSysDicMapper tblSysDicMapper;

    @Override
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ResultEnum exec(Object o) throws Exception {
        logger.info("SysDicGetOneLogicImpl.exec.start");
        if(((HashMap) o).get("l1_code")==null||((HashMap) o).get("l2_code")==null||((HashMap) o).get("code_param")==null){
            throw new SnCommonException("访问数据字典数据时l1_code["+((HashMap) o).get("l1_code")+"]" +
                    "或者l2_code["+((HashMap) o).get("l2_code")+"]" +
                    "或者code_param["+((HashMap) o).get("code_param")+"]无值");
        }

        String l1_code = ((HashMap)o).get("l1_code").toString();
        String l2_code = ((HashMap)o).get("l2_code").toString();
        String code_param = ((HashMap)o).get("code_param").toString();
        HashMap param = new HashMap();
        param.put("l1Code",l1_code);
        param.put("l2Code",l2_code);
        param.put("code_param",code_param);
        HashMap result = tblSysDicMapper.queryOneMap(param);
        ((HashMap) o).put("result", result);
        logger.info("SysDicGetOneLogicImpl.exec.end");
        return ResultEnum.PartOK;
    }
}


