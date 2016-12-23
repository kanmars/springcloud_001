package cn.kanmars.sn;

import cn.kanmars.sn.dyndata.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by kanmars on 2016/12/4.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.kanmars.sn"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ServletComponentScan
@Import(DynamicDataSourceRegister.class)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
