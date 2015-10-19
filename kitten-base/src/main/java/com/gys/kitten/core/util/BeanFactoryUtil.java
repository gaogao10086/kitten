package com.gys.kitten.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Author: kitten
 * Date: 14-3-16
 * Time: 下午4:17
 * Des: 获得bean的辅助工具类，一般用于junit单元测试
 */

public class BeanFactoryUtil implements ApplicationContextAware {

    public static ApplicationContext appContext;

    public static Object getBean(String s) {
        return appContext.getBean(s);
    }

    public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
        appContext = applicationcontext;
    }

}
