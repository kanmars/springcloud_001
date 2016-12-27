/**
 * SN Generator 
 */
package cn.kanmars.sn.logic.impl;

import cn.com.xcommon.frame.exception.SnCommonException;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.logic.ResultEnum;
import cn.com.xcommon.frame.util.MapObjTransUtils;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.logic.SysDicGetListLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 根据l1_code、l2_code获取List
 */
@Component
@Transactional
public class SysDicGetListLogicImpl implements SysDicGetListLogic{

    protected HLogger logger = LoggerManager.getLoger("SysDicGetListLogicImpl");

    @Autowired
    private TblSysDicMapper tblSysDicMapper;

    @Override
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ResultEnum exec(Object o) throws Exception {
        logger.info("SysDicGetListLogicImpl.exec.start");
        if(((HashMap) o).get("l1_code")==null||((HashMap) o).get("l2_code")==null){
            throw new SnCommonException("访问数据字典数据时l1_code["+((HashMap) o).get("l1_code")+"]" +
                    "或者l2_code["+((HashMap) o).get("l2_code")+"]无值");
        }

        String l1_code = ((HashMap)o).get("l1_code").toString();
        String l2_code = ((HashMap)o).get("l2_code").toString();
        HashMap param = new HashMap();
        param.put("l1Code",l1_code);
        param.put("l2Code",l2_code);
        List<HashMap> list = tblSysDicMapper.queryListMap(param);
        if(list == null){
            list = new ArrayList<HashMap>();
        }
        ((HashMap) o).put("result", list);
        logger.info("SysDicGetListLogicImpl.exec.end");
        return ResultEnum.PartOK;
    }
}


