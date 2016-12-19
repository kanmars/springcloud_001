package cn.kanmars.sn.resolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by baolong on 2016/12/15.
 */
@Configuration
public class FreeMarkerViewResolverAutoConfiguration {
    @Bean
    public FreeMarkerViewResolver createFreeMarkerViewResolver(){
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setAllowRequestOverride(false);
        return freeMarkerViewResolver;
    }
}
