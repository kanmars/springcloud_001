package cn.com.xcommon.frame.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented  
@Inherited
public @interface OperationLogDescription {
    //操作员
    String operationUser() default "";
	//操作名称
    String operationName() default "";
    //操作描述
    String operationDesc() default "";
    //操作应用
    String operationApp() default "";
    //类与方法
    String operationClassmethod() default "";
    //操作信息
    String operationInfo() default "";
}
