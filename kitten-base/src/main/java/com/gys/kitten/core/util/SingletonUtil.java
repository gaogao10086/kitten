package com.gys.kitten.core.util;

/**
 * Author: kitten
 * Date: 14-3-15
 * Time: 下午4:17
 * Des: 单例工具类
 */
public class SingletonUtil {

    private static SingletonUtil m_instance = null;

    private SingletonUtil() {
    }

    public synchronized static SingletonUtil getInstance() {
        if (m_instance == null) {
            m_instance = new SingletonUtil();
        }
        return m_instance;
    }

}
