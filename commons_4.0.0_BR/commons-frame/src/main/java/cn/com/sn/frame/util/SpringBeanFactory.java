package cn.com.sn.frame.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactory implements ApplicationContextAware {

	private static BeanFactory beanFactory;

	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		SpringBeanFactory.beanFactory = ac;
	}

	public static Object getBean(String name) throws BeansException {
		return beanFactory.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return beanFactory.getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return (T) beanFactory.getBean(requiredType);
	}

	public static Object getBean(String name, Object[] args) throws BeansException {
		return beanFactory.getBean(name, args);
	}

	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isPrototype(name);
	}

	public static <T> boolean isTypeMatch(String name, Class<T> targetType) throws NoSuchBeanDefinitionException {
		return beanFactory.isTypeMatch(name, targetType);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	public static String[] getAliases(String name) {
		return beanFactory.getAliases(name);
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}
}
