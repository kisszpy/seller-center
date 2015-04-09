package com.rrs.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Component
@Lazy(false)
public class ApplicationContextHolder implements ApplicationContextAware,DisposableBean {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> entityClass){
        return applicationContext.getBean(entityClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        ApplicationContextHolder.applicationContext = null;
    }
}
