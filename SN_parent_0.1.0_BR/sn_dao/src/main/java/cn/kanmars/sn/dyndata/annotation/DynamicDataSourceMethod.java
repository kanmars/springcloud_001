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
    int partionKeyIndex() default -1;
    String partionKeyParamName() default "";
    String value();
}
