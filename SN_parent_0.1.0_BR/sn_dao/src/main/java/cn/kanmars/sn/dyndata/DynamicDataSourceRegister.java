package cn.kanmars.sn.dyndata;

import cn.com.sn.frame.util.MainSecurity;
import cn.com.sn.frame.util.PropertiesUtils;
import cn.com.sn.frame.util.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by baolong on 2016/12/22.
 */

@Configuration
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware,ApplicationContextAware {

    //如配置文件中未指定数据源类型，使用该默认值
    private static final Object DATASOURCE_TYPE_DEFAULT ="org.apache.commons.dbcp.BasicDataSource";

    public static final String jdbcPropertiesPath = "props/sn/jdbc/jdbc.properties";
    public static final String dataSourceName = "sn-DataSource";
    public static final String dataSourceName_prefix = "sn-DataSource-";
    public static final String dataSourceName_default = dataSourceName_prefix+"main";
    public static final String sessionFactoryName = "sn-SessionFactory";
    public static final String mapperScannerConfigurerName = "sn-MapperScannerConfigurer";
    public static final String txManagerName = "sn-txManager";

    private ConversionService conversionService = new DefaultConversionService();
    private PropertyValues dataSourcePropertyValues;

    // 默认数据源
    private DataSource defaultDataSource;

    private Map<String,String> jdbcProperties = new HashMap<String, String>();

    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();

    private ApplicationContext ac;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("DynamicDataSourceRegister.setEnvironment()");
        parseJdbcProperties(environment);
        initDefaultDataSource(environment);
        initCustomDataSources(environment);
    }

    private void parseJdbcProperties(Environment env){
        Properties p = PropertiesUtils.getProperties(jdbcPropertiesPath);
        for(Map.Entry e:p.entrySet()){
            String key = e.getKey().toString();
            String value = e.getValue().toString();
            jdbcProperties.put(key,value);
        }
    }

    private String getPropertiesValue(String key,Environment env){
        String result = jdbcProperties.get(key);
        if(result == null){
            result = env.getProperty(key);
        }
        if(StringUtils.isNotEmpty(result)&&result.startsWith("{AES}")){
            result = MainSecurity.decode(result.substring(5));
        }
        return result;
    }

    /**
     * 加载主数据源配置.
     * @param env
     */
    private void initDefaultDataSource(Environment env){
        // 读取主数据源
        String driverClassName = getPropertiesValue("sn.jdbc.driverClass", env);
        String url = getPropertiesValue("sn.jdbc.url",env);
        String username = getPropertiesValue("sn.jdbc.username",env);
        String password = getPropertiesValue("sn.jdbc.password",env);
        if(StringUtils.isEmpty(driverClassName)||StringUtils.isEmpty(url)){
            //此处username和password因无密码数据库的存在，所以允许为空
            throw new RuntimeException("加载主数据源配置时driverClassName和url为空!");
        }

        Map<String, Object> dsMap = new HashMap<String, Object>();
        dsMap.put("type", DATASOURCE_TYPE_DEFAULT);
        dsMap.put("driverClassName", driverClassName);
        dsMap.put("url", url);
        dsMap.put("username", username);
        dsMap.put("password", password);
        //创建数据源;
        defaultDataSource = buildDataSource(dsMap);
        dataBinder(defaultDataSource, env);
    }

    /**
     * 初始化更多数据源
     *
     * @author SHANHY
     * @create 2016年1月24日
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源

        String names = getPropertiesValue("datasource.names",env);
        if(StringUtils.isEmpty(names)){
            return;
        }
        for(String name : names.split(",")){
            String driverClassName = getPropertiesValue("sn."+name+".jdbc.driverClass", env);
            String url = getPropertiesValue("sn."+name+".jdbc.url",env);
            String username = getPropertiesValue("sn."+name+".jdbc.username",env);
            String password = getPropertiesValue("sn."+name+".jdbc.password",env);
            if(StringUtils.isEmpty(driverClassName)||StringUtils.isEmpty(url)){
                //此处username和password因无密码数据库的存在，所以允许为空
                throw new RuntimeException("加载辅数据源配置时driverClassName和url为空!");
            }
            Map<String, Object> dsMap = new HashMap<String, Object>();
            dsMap.put("type", DATASOURCE_TYPE_DEFAULT);
            dsMap.put("driverClassName", driverClassName);
            dsMap.put("url", url);
            dsMap.put("username", username);
            dsMap.put("password", password);
            DataSource ds = buildDataSource(dsMap);
            customDataSources.put(dataSourceName_prefix+name, ds);
            dataBinder(ds, env);
        }
    }

    /**
     * 创建datasource.
     * @param dsMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        Object type = dsMap.get("type");
        if (type == null){
            type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
        }
        Class<? extends DataSource> dataSourceType;

        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dsMap.get("driverClassName").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();
            DataSourceBuilder factory =   DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为DataSource绑定更多数据
     * @param dataSource
     * @param env
     */
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true

        if(dataSourcePropertyValues == null){

            String initialSize = getPropertiesValue("sn.cpool.initialSize",env);
            String minIdle = getPropertiesValue("sn.cpool.minIdle",env);
            String maxActive = getPropertiesValue("sn.cpool.maxActive",env);
            String maxWait = getPropertiesValue("sn.cpool.maxWait",env);
            String timeBetweenEvictionRunsMillis = getPropertiesValue("sn.cpool.timeBetweenEvictionRunsMillis",env);
            String minEvictableIdleTimeMillis = getPropertiesValue("sn.cpool.minEvictableIdleTimeMillis",env);
            String validationQuery = getPropertiesValue("sn.cpool.validationQuery",env);
            String testWhileIdle = getPropertiesValue("sn.cpool.testWhileIdle",env);
            String testOnBorrow = getPropertiesValue("sn.cpool.testOnBorrow",env);
            String testOnReturn = getPropertiesValue("sn.cpool.testOnReturn",env);
            if(StringUtils.isEmpty(initialSize))initialSize="5";
            if(StringUtils.isEmpty(minIdle))minIdle="5";
            if(StringUtils.isEmpty(maxActive))maxActive="10";
            if(StringUtils.isEmpty(maxWait))maxWait="10000";//毫秒
            if(StringUtils.isEmpty(timeBetweenEvictionRunsMillis))timeBetweenEvictionRunsMillis="10000";
            if(StringUtils.isEmpty(minEvictableIdleTimeMillis))minEvictableIdleTimeMillis="10000";
            if(StringUtils.isEmpty(validationQuery))validationQuery="SELECT 'x' from dual";
            if(StringUtils.isEmpty(testWhileIdle))testWhileIdle="true";
            if(StringUtils.isEmpty(testOnBorrow))testOnBorrow="false";
            if(StringUtils.isEmpty(testOnReturn))testOnReturn="false";
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("initialSize",initialSize);
            values.put("minIdle", minIdle);
            values.put("maxActive", maxActive);
            values.put("maxWait", maxWait);
            values.put("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
            values.put("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
            values.put("validationQuery", validationQuery);
            values.put("testWhileIdle", testWhileIdle);
            values.put("testOnBorrow", testOnBorrow);
            values.put("testOnReturn", testOnReturn);
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("DynamicDataSourceRegister.registerBeanDefinitions()");
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put(dataSourceName_default, defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add(dataSourceName_default);
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        for (String key : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        {
            // 创建DynamicDataSource
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(DynamicDataSource.class);
            beanDefinition.setSynthetic(true);
            MutablePropertyValues mpv = beanDefinition.getPropertyValues();
            //添加属性：AbstractRoutingDataSource.defaultTargetDataSource
            mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
            mpv.addPropertyValue("targetDataSources", targetDataSources);
            registry.registerBeanDefinition(dataSourceName, beanDefinition);
        }
        {
            // 创建SessionFactory
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SqlSessionFactoryBean.class);
            beanDefinition.setSynthetic(true);
            MutablePropertyValues mpv = beanDefinition.getPropertyValues();
            mpv.addPropertyValue("dataSource", new RuntimeBeanReference(dataSourceName));
            mpv.addPropertyValue("configLocation", "classpath:spring/mybatis-config.xml");
            List mapperLocations = new ArrayList();
            mapperLocations.add("classpath*:/props/sn/mapper/*.xml");
            mpv.addPropertyValue("mapperLocations", mapperLocations);
            registry.registerBeanDefinition(sessionFactoryName, beanDefinition);
        }

        {
            // 创建MapperScannerConfigurer
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(MapperScannerConfigurer.class);
            beanDefinition.setSynthetic(true);
            MutablePropertyValues mpv = beanDefinition.getPropertyValues();
            mpv.addPropertyValue("basePackage", "cn.kanmars.sn.dao");
            mpv.addPropertyValue("sqlSessionFactoryBeanName", sessionFactoryName);
            mpv.addPropertyValue("annotationClass", "cn.kanmars.sn.MybatisRepository");
            registry.registerBeanDefinition(mapperScannerConfigurerName, beanDefinition);
        }
        {
            // 创建txManager
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(DataSourceTransactionManager.class);
            beanDefinition.setSynthetic(true);
            MutablePropertyValues mpv = beanDefinition.getPropertyValues();
            mpv.addPropertyValue("dataSource", new RuntimeBeanReference(dataSourceName));
            registry.registerBeanDefinition(txManagerName, beanDefinition);
        }
    }
}
