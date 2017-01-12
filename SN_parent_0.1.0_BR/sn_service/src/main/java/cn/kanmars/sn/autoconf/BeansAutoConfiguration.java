package cn.kanmars.sn.autoconf;

import cn.kanmars.sn.aspect.OperationLogAspect;
import cn.kanmars.sn.aspect.RequestTrackAspect;
import cn.kanmars.sn.aspect.ServiceFacadeAspect;
import cn.kanmars.sn.feign.SecurityRequestInterceptor;
import cn.kanmars.sn.util.SysDicUtils;
import cn.kanmars.sn.util.SysSequenceUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by baolong on 2016/12/26.
 */
@Configuration
public class BeansAutoConfiguration implements ApplicationContextAware {

    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ac = ac;
    }

    @Bean("sysDicUtils")
    public SysDicUtils getSysDicUtils(){
        SysDicUtils sysDicUtils = new SysDicUtils();
        sysDicUtils.setApplicationContext(ac);
        return sysDicUtils;//返回后再根据@PostConstruct标签进行初始化
    }

    @Bean("sysSequenceUtils")
    public SysSequenceUtils getSysSequenceUtils(){
        SysSequenceUtils sysSequenceUtils = new SysSequenceUtils();
        sysSequenceUtils.setApplicationContext(ac);
        return sysSequenceUtils;//返回后再根据@PostConstruct标签进行初始化
    }

    @Bean("serviceFacadeAspect")
    public ServiceFacadeAspect getServiceFacadeAspect(){
        //所有服务在报错时，返回值为0002
        ServiceFacadeAspect serviceFacadeAspect = new ServiceFacadeAspect();
        return serviceFacadeAspect;
    }

    @Bean("requestTrackAspect")
    public RequestTrackAspect getRequestTrackAspect(){
        //请求跟踪切面，从请求中获取到head域中的REQUESTTRACK信息，然后存入本地线程号，处理完毕后移除
        RequestTrackAspect requestTrackAspect = new RequestTrackAspect();
        return requestTrackAspect;
    }

    @Bean("securityRequestInterceptor")
    public SecurityRequestInterceptor getRequestTrackInterceptor(){
        //请求签名信息，用于生成请求时，附加签名信息，防止误调用
        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        return securityRequestInterceptor;
    }

    @Bean("operationLogAspect")
    public OperationLogAspect getOperationLogAspect(){
        //日志切面
        OperationLogAspect operationLogAspect = new OperationLogAspect();
        return operationLogAspect;
    }
}
