package cn.kanmars.service;

import cn.kanmars.feign.ServiceAProductServiceInterFace;
import cn.kanmars.properties.ServiceAProps;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by kanmars on 2016/12/3.
 */
@Controller
@RestController
public class ProductService {

    final String SERVICE_NAME="springcloud-001-serviceA";

    @Autowired
    public DiscoveryClient discoveryClient;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public ServiceAProductServiceInterFace serviceAProductServiceInterFace;

    @HystrixCommand(
            groupKey="g1"
            ,commandKey="c1"
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
    @RequestMapping("/addOrder/{id}/{name}")
    public String addOrder(@PathVariable("id") String id,@PathVariable("name") String name ) {
        System.out.println("调用了home["+i+"]");
        if(i<=20)i++;
        if(i>=10){
            throw new RuntimeException("e");
        }
        String s = restTemplate.getForObject("http://"+SERVICE_NAME+"/addProduct/fromB_id/fromB_name",String.class);
        return "result is ["+s+"]";
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
        if (factory instanceof SimpleClientHttpRequestFactory) {
            SimpleClientHttpRequestFactory simple = (SimpleClientHttpRequestFactory) factory;
                simple.setReadTimeout(6000);
                simple.setConnectTimeout(6000);
        } else if (factory instanceof HttpComponentsClientHttpRequestFactory) {
            HttpComponentsClientHttpRequestFactory client = (HttpComponentsClientHttpRequestFactory) factory;
                client.setReadTimeout(6000);
                client.setConnectTimeout(6000);
        }
        return restTemplate;
    }

    public String fallbackSearchAll(String id,String name){
        System.out.println("调用了fallback");
        return "fallbackSearchAll";
    }



    private int i=0;
    private Thread t = new Thread(){

        public void run() {
            while (true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i>0){
                    i--;
                    System.out.println("i下降["+i+"]");
                }
            }
        }
    };
    {
        t.start();
    }

    @ResponseBody
    @RequestMapping(value="/bbb")
    public String bbb(){
        StringBuilder buf = new StringBuilder();
        List<String> serviceIds = discoveryClient.getServices();
        if(!CollectionUtils.isEmpty(serviceIds)){
            for(String s : serviceIds){
                System.out.println("serviceId:" + s);
                List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
                if(!CollectionUtils.isEmpty(serviceInstances)){
                    for(ServiceInstance si:serviceInstances){
                        buf.append("["+si.getServiceId() +" host=" +si.getHost()+" port="+si.getPort()+" uri="+si.getUri()+"]");
                    }
                }else{
                    buf.append("no service.");
                }
            }
        }
        return buf.toString();
    }

    @ResponseBody
    @RequestMapping(value="/ccc")
    public String ccc(){
        Map<String ,String> m = serviceAProductServiceInterFace.getAMap();
        System.out.println(m);

        return serviceAProductServiceInterFace.addProduct("123","567");
    }
}
