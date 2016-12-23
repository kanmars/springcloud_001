package cn.kanmars.sn.dyndata;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.dyndata.annotation.DynamicDataSourceMethod;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/12/20.
 */
@Aspect
@Order(-10)//保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect implements ApplicationContextAware {

    private HLogger logger = LoggerManager.getLoger("DynamicDataSourceAspect");

    ApplicationContext ac;

    /*
     * @Before("@annotation(dynamicDataSource)")
     * 会拦截注解dynamicDataSource的方法，否则不拦截;
     */
    @Before("@annotation(dynamicDataSourceMethod)")
    public void changeDataSource(JoinPoint point, DynamicDataSourceMethod dynamicDataSourceMethod) throws Throwable {
        logger.debug("切换数据源");
        //获取当前的指定的数据源;
        int idx = dynamicDataSourceMethod.partionKeyIndex();
        String paramName = dynamicDataSourceMethod.partionKeyParamName();
        String value = dynamicDataSourceMethod.value();
        if(StringUtils.isEmpty(value)){
            throw new RuntimeException("使用DynamicDataSource时，必须设置value属性为实现了DynamicDataSourceRouter的bean的name!");
        }

        Object partionKey = null;
        if(idx!=-1){
            partionKey = point.getArgs()[idx];
        }else if(StringUtils.isNotEmpty(paramName)){
            //获取参数名称列表
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = getClazz(classType);
            String clazzName = clazz.getName();
            String clazzSimpleName = clazz.getSimpleName();
            String methodName = point.getSignature().getName();
            String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
            idx = -1;
            for(int i=0;i<paramNames.length;i++){
                if(paramNames[i].equals(paramName.trim())){
                    idx = i;
                    break;
                }
            }
            if(idx == -1){
                throw new RuntimeException("paramName["+paramName+"]对应的参数在["+methodName+"]中不存在!");
            }
            partionKey = point.getArgs()[idx];
        }
        logger.debug("使用DynamicDataSourceRouter["+value+"]");
        DynamicDataSourceRouter router = (DynamicDataSourceRouter)ac.getBean(value);
        if(router==null){
            throw new RuntimeException("value["+value+"]对应的DynamicDataSourceRouter中不存在!");
        }
        String dataSourcename = router.router(partionKey);
        logger.debug("使用数据源["+dataSourcename+"]");
        DynamicDataSourceContextHolder.setDataSourceType(dataSourcename);
    }

    @After("@annotation(dynamicDataSourceMethod)")
    public void restoreDataSource(JoinPoint point, DynamicDataSourceMethod dynamicDataSourceMethod) {
        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

    ConcurrentHashMap<String,Class<?>> classMap = new ConcurrentHashMap<String,Class<?>>();
    public Class<?> getClazz(String classType) throws ClassNotFoundException {
        Class<?> result = classMap.get(classType);
        if( result == null ){
            result = Class.forName(classType);
            Class<?> v = classMap.putIfAbsent(classType, result);
            result = classMap.get(classType);
        }
        return result;
    }


    ConcurrentHashMap<String,String[]> classmethod_paramterNameMap = new ConcurrentHashMap<String,String[]>();
    /**
     * 得到方法参数的名称
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    private String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException {
        String key = clazzName+"_"+methodName;
        String[] result = classmethod_paramterNameMap.get(key);
        if( result == null ){
            ClassPool pool = ClassPool.getDefault();
            //ClassClassPath classPath = new ClassClassPath(this.getClass());
            ClassClassPath classPath = new ClassClassPath(cls);
            pool.insertClassPath(classPath);

            CtClass cc = pool.get(clazzName);
            CtMethod cm = cc.getDeclaredMethod(methodName);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                // exception
            }
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++){
                paramNames[i] = attr.variableName(i + pos); //paramNames即参数名
            }
            result = paramNames;
            String[] v = classmethod_paramterNameMap.putIfAbsent(key, result);
            result = classmethod_paramterNameMap.get(key);
        }
        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }
}
