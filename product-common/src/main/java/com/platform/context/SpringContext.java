package com.platform.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-21 16:27
 */
@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringContext() {
    }

    public void setApplicationContext(ApplicationContext appContext) {
        setContext(appContext);
    }

    private static void setContext(ApplicationContext appContext) {
        applicationContext = appContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
