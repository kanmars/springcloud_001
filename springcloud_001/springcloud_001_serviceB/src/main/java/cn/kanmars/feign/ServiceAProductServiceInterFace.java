package cn.kanmars.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by kanmars on 2016/12/4.
 */
@FeignClient("springcloud-001-serviceA")
public interface ServiceAProductServiceInterFace {

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
    @RequestMapping(value = "/getAMap", method = RequestMethod.GET)
    public Map<String,String> getAMap();

    @HystrixCommand(
            groupKey="ServiceAProductServiceInterFace.addProduct"
            ,commandKey="ServiceAProductServiceInterFace.addProduct"
            ,threadPoolKey="ServiceAProductServiceInterFace.addProduct"
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
    @RequestMapping(value = "/addProduct/{id}/{name}", method = RequestMethod.GET)
    public String addProduct(@PathVariable("id") String id, @PathVariable("name") String name);
}
