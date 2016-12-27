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

    @HystrixCommand(
            groupKey="ServiceAProductServiceInterFace.getAMap"
            ,commandKey="ServiceAProductServiceInterFace.getAMap"
            ,threadPoolKey="ServiceAProductServiceInterFace.getAMap"
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
    @RequestMapping(value = "/sn_sysdic/getAll", method = RequestMethod.GET)
    public Map<String,Object> getAll();

    @RequestMapping(value = "/sn_sysdic/getList/{l1_code}/{l2_code}", method = RequestMethod.GET)
    public Map<String,Object> getList(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code);

    @RequestMapping(value="/sn_sysdic/getOne/{l1_code}/{l2_code}/{code_param}",method = RequestMethod.GET)
    public Map<String,Object> getOne(@PathVariable("l1_code") String l1_code,@PathVariable("l2_code") String l2_code,@PathVariable("code_param") String code_param);

}
