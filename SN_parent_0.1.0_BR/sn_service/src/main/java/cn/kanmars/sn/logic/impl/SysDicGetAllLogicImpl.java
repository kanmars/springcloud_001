/**
 * SN Generator 
 */
package cn.kanmars.sn.logic.impl;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.logic.ResultEnum;
import cn.kanmars.sn.dao.TblSysDicMapper;
import cn.kanmars.sn.entity.TblSysDic;
import cn.kanmars.sn.logic.SysDicGetAllLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 查询全量数据字典表
 */
@Component
@Transactional
public class SysDicGetAllLogicImpl implements SysDicGetAllLogic{

    protected HLogger logger = LoggerManager.getLoger("SysDicGetAllLogicImpl");

    @Autowired
    private TblSysDicMapper tblSysDicMapper;

    @Override
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ResultEnum exec(Object o) throws Exception {
        logger.info("SysDicGetAllLogicImpl.exec.start");
        List<HashMap> list = tblSysDicMapper.queryListMap(new HashMap());
        if(list == null){
            list = new ArrayList<HashMap>();
        }
        ((HashMap)o).put("result",list);
        logger.info("SysDicGetAllLogicImpl.exec.end");
        return ResultEnum.PartOK;
    }
}


