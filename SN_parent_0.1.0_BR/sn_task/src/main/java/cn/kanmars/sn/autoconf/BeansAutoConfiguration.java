package cn.kanmars.sn.autoconf;

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
}
