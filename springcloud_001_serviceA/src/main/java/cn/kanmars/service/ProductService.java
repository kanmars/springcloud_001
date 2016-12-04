package cn.kanmars.service;

import cn.kanmars.properties.ServiceAProps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by kanmars on 2016/12/3.
 */
@Controller
@RestController
public class ProductService {

    @Autowired
    public ServiceAProps serviceAProps;

    @Autowired
    public RestTemplate restTemplate;

    @HystrixCommand(
            groupKey="g2"
            ,commandKey="c2"
            ,threadPoolKey="t1"
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
    @RequestMapping("/addProduct/{id}/{name}")
    public String addProduct(@PathVariable("id") String id,@PathVariable("name") String name ) {
        System.out.println("--------["+serviceAProps.getId1()+"]------");
        return "addSuccess[name:"+name+",id="+id+"]";
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    final String SERVICE_NAME = "springcloud-001-serviceB";

    @HystrixCommand(
            groupKey="g2"
            ,commandKey="c2"
            ,threadPoolKey="t1"
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
    @RequestMapping("/getServiceB/{id}/{name}")
    public String getServiceB(@PathVariable("id") String id,@PathVariable("name") String name ) {
        System.out.println("--------["+serviceAProps.getId1()+"]------");
        String s = restTemplate.getForObject("http://"+SERVICE_NAME+"/bbb",String.class);
        return "getServiceB[s:"+s+"]";
    }

    public String fallbackSearchAll(String id,String name){
        System.out.println("调用了fallback");
        return "fallbackSearchAll";
    }

    @RequestMapping("/getAMap")
    public Object getAMap(){
        return new HashMap<String,String>(){{
            this.put("a1","111");
            this.put("a2","222");
            this.put("a3","333");
            }};
    }
}
