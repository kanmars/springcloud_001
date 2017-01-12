package cn.kanmars.sn.controller;

import cn.com.xcommon.frame.logger.ExPatternParser;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.util.SysDicUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
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

    @HystrixCommand(
            groupKey="SecondaryController.secondary"
            ,commandKey="SecondaryController.secondary"
            ,threadPoolKey="SecondaryController.secondary"
            ,fallbackMethod = "fallbackSearchAll"
            ,commandProperties={
            @HystrixProperty(name= HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,value="5")//10秒5次请求
            ,@HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,value="5000")//熔断5秒
            ,@HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value="50")//出错率50%则熔断
            ,@HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,value="true")
            ,@HystrixProperty(name=HystrixPropertiesManager.REQUEST_CACHE_ENABLED,value="false")
            ,@HystrixProperty(name=HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_OPEN,value="false")}//强制开启
            ,threadPoolProperties={
            @HystrixProperty(name= HystrixPropertiesManager.CORE_SIZE,value="10")
    }
    )
    @RequestMapping("/secondary")
    public ModelAndView secondary(){
        String value = SysDicUtils.getValue("1","2","3");
        logger.info("value == " + value);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("title","SPRING BOOT WEB");
        mv.addObject("wellcome", "欢迎使用SPRING BOOT WEB,[tid="+ ExPatternParser.ThreadNumber.getThreadNumber()+"]");
        return mv;
    }

}
