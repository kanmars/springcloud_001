package cn.kanmars.sn.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by baolong on 2016/12/14.
 */
@Component
@RefreshScope
@EnableAutoConfiguration
public class sn_adminProperties {

    @Value("${loginUrl:/login/login.dhtml}")
    private static String loginUrl;

    @Value("${resourceUrl:}")
    private static String resourceUrl;

    @Value("${file_root_path:'http://192.168.1.1:8080/sn-admin'}")
    private static String file_root_path;

    @Value("${file_path:/snfile}")
    private static String file_path;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getFile_root_path() {
        return file_root_path;
    }

    public void setFile_root_path(String file_root_path) {
        this.file_root_path = file_root_path;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
