package cn.kanmars.sn.dyndata.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * Created by baolong on 2016/12/20.
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(-10)
public @interface DynamicDataSourceMethod {
    /**
     * 作为分区key的索引，从0开始，与partionKeyParamName不能共用，优先级为partionKeyIndex大于partionKeyParamName
     * @return
     */
    int[] partionKeyIndex() default {};

    /**
     * 作为分区key的参数名称，与partionKeyIndex不能共用，优先级为partionKeyIndex大于partionKeyParamName
     * @return
     */
    String[] partionKeyParamName() default {};

    /**
     * 需要指定DynamicDataSourceRouter的实现类，用于将分区key转化为一个数据源的名称
     * @return
     */
    String routerBeanName();
}
