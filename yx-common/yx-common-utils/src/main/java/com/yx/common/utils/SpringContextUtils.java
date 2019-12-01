
package com.yx.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 *
 * @author TangHuaLiang
 * @version 2018-07-01
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.CONTEXT = applicationContext;
    }

    public static Object getBean(String name) {
        return CONTEXT.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return CONTEXT.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return CONTEXT.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return CONTEXT.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return CONTEXT.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return CONTEXT.getType(name);
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }

}
