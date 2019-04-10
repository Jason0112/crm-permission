package com.university.crm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * date: 2019/3/15
 * description :
 *
 * @author : zhencai.cheng
 */
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public ApplicationContextUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }
}