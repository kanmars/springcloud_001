package cn.kanmars.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by kanmars on 2016/12/3.
 */
@Component
@RefreshScope
@EnableAutoConfiguration
public class ServiceAProps {
    @Value("${id1:id1_default}")
    private String id1;
    @Value("${id2:id2_default}")
    private String id2;
    @Value("${id3:id3_default}")
    private String id3;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }
}
