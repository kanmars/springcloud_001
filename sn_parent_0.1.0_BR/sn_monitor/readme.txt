--------------------监控方案一-----------------------------------
1、TOMCAT部署
hystrix-dashboard-1.5.5.war
turbine-web-1.0.0.war
项目
2、在turbine-web项目的apache-tomcat-7.0.55\webapps\turbine-web\WEB-INF\classes下新增config.properties
内容如下
turbine.aggregator.clusterConfig=test
turbine.ConfigPropertyBasedDiscovery.test.instances=127.0.0.1:9101,127.0.0.1:9102
turbine.instanceUrlSuffix=/hystrix.stream

即为将：127.0.0.1:9101/hystrix.stream,127.0.0.1:9102/hystrix.stream的hystrix汇总起来

3、访问http://localhost:8080/hystrix-dashboard/项目，新增

http://localhost:8080/turbine-web/turbine.stream?cluster=test
增加监控
---------------------监控方案二----------------------------------------------------
使用DiscoveryClient
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
实时监控系统运行

