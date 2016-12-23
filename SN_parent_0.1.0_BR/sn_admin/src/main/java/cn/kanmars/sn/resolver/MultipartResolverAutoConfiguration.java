package cn.kanmars.sn.resolver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by baolong on 2016/12/15.
 */
@Configuration
public class MultipartResolverAutoConfiguration {

    /**
     * 文件上传解析器
     * @return
     */
    @Bean
    @ConditionalOnClass({CommonsMultipartResolver.class})
    public CommonsMultipartResolver createCommonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxInMemorySize(10960);//bytes
        return commonsMultipartResolver;
    }
}
