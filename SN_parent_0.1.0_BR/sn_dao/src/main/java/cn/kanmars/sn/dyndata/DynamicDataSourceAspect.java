package cn.kanmars.sn.dyndata;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.StringUtils;

import cn.kanmars.sn.dyndata.annotation.DynamicDataSourceMethod;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

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
        int[] idx = dynamicDataSourceMethod.partionKeyIndex();
        String[] paramName = dynamicDataSourceMethod.partionKeyParamName();
        String router = dynamicDataSourceMethod.routerBeanName();
        if(StringUtils.isEmpty(router)){
            throw new RuntimeException("使用DynamicDataSource时，必须设置value属性为实现了DynamicDataSourceRouter的bean的name!");
        }

        Object[] partionKeys = null;
        if(idx.length!=0){
            partionKeys = new Object[idx.length];
            for(int i=0;i<idx.length;i++){
                if(idx[i]>idx.length-1){
                    throw new RuntimeException("使用DynamicDataSource时，partionKey值为["+idx[i]+"],但参数下标最大为["+(idx.length-1)+"]");
                }
                partionKeys[i]=point.getArgs()[idx[i]];
            }
        }else if(paramName.length!=0){
            //获取参数名称列表
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = getClazz(classType);
            String clazzName = clazz.getName();
            String clazzSimpleName = clazz.getSimpleName();
            String methodName = point.getSignature().getName();
            String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);

            partionKeys = new Object[paramName.length];
            for(int i=0;i<paramName.length;i++){//注解中的每个参数名
                boolean hasParam = false;
                for(int j=0;j<paramNames.length;j++){//目标方法中的参数名
                    if(paramNames[i].trim().equals(paramName[i].trim())){//目标方法中的参数名
                        hasParam = true;
                        partionKeys[i]=point.getArgs()[j];
                        break;
                    }
                }
                if(!hasParam){
                    throw new RuntimeException("使用DynamicDataSource时，partionKeyParamName值为["+paramName[i].trim()+"],但未找到此参数");
                }
            }
        }else{
            throw new RuntimeException("使用DynamicDataSource时，必须设置partionKeyIndex或者partionKeyParamName");
        }
        logger.debug("使用DynamicDataSourceRouter["+router+"]");
        DynamicDataSourceRouter dynamicDataSourceRouter = (DynamicDataSourceRouter)ac.getBean(router);
        if(dynamicDataSourceRouter==null){
            throw new RuntimeException("value["+router+"]对应的DynamicDataSourceRouter中不存在!");
        }
        String dataSourcename = dynamicDataSourceRouter.router(partionKeys);
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
