package cn.kanmars.sn.service;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.logic.ResultEnum;
import cn.com.sn.frame.util.*;
import cn.kanmars.sn.logic.ALogic;
import cn.kanmars.sn.logic.BLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by baolong on 2016/12/15.
 */
@Controller
@RestController
public class ServiceAFacadeService {

    private HLogger logger = LoggerManager.getLoger("ServiceAFacadeService");

    @Autowired
    private ALogic aLogic;

    @Autowired
    private BLogic bLogic;

    @ResponseBody
    @RequestMapping(value = "/serviceAmethodA/{id}/{name}",method = RequestMethod.GET)
    public HashMap serviceAmethodA(@PathVariable("id") String id,@PathVariable("name") String name) throws Exception {
        HashMap result = new HashMap();
        logger.info("ServiceAFacadeService.serviceAmethodA.start");
        result.put("id", id);
        result.put("name",name);
        if(!ResultEnum.PartOK.equals(aLogic.exec(result))){
            return result;
        }
        if(!ResultEnum.PartOK.equals(bLogic.exec(result))){
            return result;
        }
        result.put("resCode","0000");
        result.put("resDesc","成功");
        logger.info("ServiceAFacadeService.serviceAmethodA.end");
        return result;
    }
}
