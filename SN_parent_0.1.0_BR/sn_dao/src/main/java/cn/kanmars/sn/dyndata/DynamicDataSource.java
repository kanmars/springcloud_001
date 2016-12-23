package cn.kanmars.sn.dyndata;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by baolong on 2016/12/20.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public void init(){

    }

    public void close(){

    }
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceType();
        if(StringUtils.isNotEmpty(dataSourceName)){
            return dataSourceName;
        }else{
            return DynamicDataSourceRegister.dataSourceName_default;
        }
    }
}
