package cn.kanmars.sn.feign;

import cn.com.xcommon.frame.logger.ExPatternParser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * 请求拦截器，发起新请求时，将一些信息写入head域中
 * 使用场景:sn-admin,sn-service,sn-web,sn-task等应用发出请求时，用于权限验证
 * Created by baolong on 2017/1/10.
 */
public class SecurityRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //需要注意Feign使用的是请求模版技术，此参数设置设置于模版中，在最终调用RequestTemplate.request()方法时才会生效
        template.header("SECURITYKEY","SECURITYKEY");
    }
}
