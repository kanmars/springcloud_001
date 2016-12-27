package cn.kanmars.sn.controller;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.feign.Sn_SysDicFacadeServiceInterface;
import cn.kanmars.sn.util.SysDicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by baolong on 2016/12/27.
 */
@Controller
@RequestMapping("/secondary")
public class SecondaryController {
    protected HLogger logger = LoggerManager.getLoger("SecondaryController");

    @RequestMapping("/secondary")
    public ModelAndView secondary(){
        String value = SysDicUtils.getValue("1","2","3");
        logger.info("value == " + value);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("title","SPRING BOOT WEB");
        mv.addObject("wellcome", "欢迎使用SPRING BOOT WEB");
        return mv;
    }

}
