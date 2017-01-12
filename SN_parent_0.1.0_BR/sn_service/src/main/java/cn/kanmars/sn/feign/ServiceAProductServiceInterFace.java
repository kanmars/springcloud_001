package cn.kanmars.sn.feign;

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
@FeignClient("sn-service")
public interface ServiceAProductServiceInterFace {

    @RequestMapping(value = "/getAMap", method = RequestMethod.GET)
    public Map<String,String> getAMap();

    @RequestMapping(value = "/addProduct/{id}/{name}", method = RequestMethod.GET)
    public String addProduct(@PathVariable("id") String id, @PathVariable("name") String name);
}
