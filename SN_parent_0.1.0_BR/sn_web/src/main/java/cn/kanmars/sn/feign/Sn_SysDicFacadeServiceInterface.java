package cn.kanmars.sn.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by baolong on 2016/12/27.
 */
@FeignClient("sn-service")
public interface Sn_SysDicFacadeServiceInterface {

    @RequestMapping(value = "/sn_sysdic/getAll", method = RequestMethod.GET,headers = {"REQUESTTRACK=#{T(cn.com.xcommon.frame.logger.ExPatternParser.ThreadNumber).getThreadNumber()}"})
    public Map<String,Object> getAll();

    @RequestMapping(value = "/sn_sysdic/getList/{l1_code}/{l2_code}", method = RequestMethod.GET,headers = {"REQUESTTRACK=#{T(cn.com.xcommon.frame.logger.ExPatternParser.ThreadNumber).getThreadNumber()}"})
    public Map<String,Object> getList(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code);

    @RequestMapping(value="/sn_sysdic/getOne/{l1_code}/{l2_code}/{code_param}",method = RequestMethod.GET,headers = {"REQUESTTRACK={}"})
    public Map<String,Object> getOne(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code,@PathVariable("code_param") String code_param);

}
