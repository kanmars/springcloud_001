/**
 * SN Generator 
 */
package cn.kanmars.sn.service;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.logic.ResultEnum;
import cn.com.xcommon.frame.util.*;
import cn.kanmars.sn.logic.SysDicGetOneLogic;
import cn.kanmars.sn.logic.SysDicGetListLogic;
import cn.kanmars.sn.logic.SysDicGetAllLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Sn_系统数据字典服务
 */
@Controller
@RequestMapping("/sn_sysdic")
public class Sn_SysDicFacadeService {

    protected HLogger logger = LoggerManager.getLoger("Sn_SysDicFacadeService");

    @Autowired
    private SysDicGetOneLogic sysDicGetOneLogic;
    @Autowired
    private SysDicGetListLogic sysDicGetListLogic;
    @Autowired
    private SysDicGetAllLogic sysDicGetAllLogic;

    @ResponseBody
    @RequestMapping(value="/getAll",method = RequestMethod.GET)
    public HashMap getAll() throws Exception {
        HashMap result = new HashMap();
        logger.info("Sn_SysDicFacadeService.getAll.start");
        if(!ResultEnum.PartOK.equals(sysDicGetAllLogic.exec(result))){
            return result;
        }
        result.put("resCode","0000");
        result.put("resDesc","成功");
        logger.info("Sn_SysDicFacadeService.getAll[获取全量数据字典].end");
        return result;
    }
    @ResponseBody
    @RequestMapping(value="/getList/{l1_code}/{l2_code}",method = RequestMethod.GET)
    public HashMap getList(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code) throws Exception {
        HashMap result = new HashMap();
        logger.info("Sn_SysDicFacadeService.getList.start");
        result.put("l1_code", l1_code);
        result.put("l2_code", l2_code);
        if(!ResultEnum.PartOK.equals(sysDicGetListLogic.exec(result))){
            return result;
        }
        result.put("resCode","0000");
        result.put("resDesc","成功");
        logger.info("Sn_SysDicFacadeService.getList[根据l1_code、l2_code获取List].end");
        return result;
    }
    @ResponseBody
    @RequestMapping(value="/getOne/{l1_code}/{l2_code}/{code_param}",method = RequestMethod.GET)
    public HashMap getOne(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code,@PathVariable("code_param") String code_param) throws Exception {
        HashMap result = new HashMap();
        logger.info("Sn_SysDicFacadeService.getOne.start");
        result.put("l1_code", l1_code);
        result.put("l2_code", l2_code);
        result.put("code_param", code_param);
        if(!ResultEnum.PartOK.equals(sysDicGetOneLogic.exec(result))){
            return result;
        }
        result.put("resCode","0000");
        result.put("resDesc","成功");
        logger.info("Sn_SysDicFacadeService.getOne[根据l1_code、l2_code、paramCode获取Value].end");
        return result;
    }
}


