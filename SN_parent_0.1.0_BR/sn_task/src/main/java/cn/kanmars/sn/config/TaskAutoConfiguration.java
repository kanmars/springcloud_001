package cn.kanmars.sn.config;

import cn.com.sn.frame.util.SpringBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by baolong on 2016/12/22.
 */
@Configuration
public class TaskAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext ac;

    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ac = ac;
    }

    @Bean("springBeanFactory")
    public SpringBeanFactory getSpringBeanFactory(){
        SpringBeanFactory sbf = new SpringBeanFactory();
        sbf.setApplicationContext(ac);
        return sbf;
    }

    @Bean("schedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean(){
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        sfb.setApplicationContextSchedulerContextKey("applicationContext");
        return sfb;
    }
}
