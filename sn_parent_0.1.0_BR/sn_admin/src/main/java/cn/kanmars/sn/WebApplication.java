package cn.kanmars.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by kanmars on 2016/12/4.
 */
@SpringBootApplication
@ImportResource(locations={
        "classpath*:/frame/ftl-frame-servlet.xml",
        "classpath*:/spring/sn-local-dataSource.xml"
})
@ComponentScan(basePackages = {"cn.kanmars.sn"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ServletComponentScan
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
