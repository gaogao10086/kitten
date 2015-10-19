package com.gys.kitten.core.util;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: kitten
 * Date: 14-3-16
 * Time: 下午4:17
 * Des: 字符串验证工具类
 */
public final class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {
    }

    /**
     * 验证是否十六进制的颜色值
     *
     * @param colorHex
     * @return
     */
    public static boolean isColorHex(String colorHex) {
        String patternStr = "#[0-9a-fA-F]{6}";
        return Pattern.matches(patternStr, colorHex);
    }

    /**
     * 验证 url 的合法性
     *
     * @param url
     * @return
     */
    public static boolean validateUrl(String url) {
        String patternStr = "[a-zA-z]+://[^\\s]*";
        return Pattern.matches(patternStr, url);
    }

    /**
     * 验证电话号码的合法性
     *
     * @param tel
     */
    public static boolean validateTel(String tel) {
        String patternStr = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        return Pattern.matches(patternStr, tel);
    }


    public static JSONObject beanToJson(Object bean) {
        JSONObject json = new JSONObject();
        Class beanClass = bean.getClass();

        try {
            Method[] methods = beanClass.getDeclaredMethods();
            Method[] ms = beanClass.getMethods();
            for (Method m : ms) {
                if (StringUtils.equals("getId", m.getName())) {
                    json.put("id", m.invoke(bean, new Object[]{}));
                }
            }
            for (Method method : methods) {
                String methodName = method.getName();
                String key = "";
                if (methodName.startsWith("get") && !StringUtils.isNotEmpty(key = methodName.replaceFirst("get", ""))) {
                    Character c = key.charAt(0);
                    key = key.replaceFirst(String.valueOf(c), String.valueOf(Character.toLowerCase(c)));
                    json.put(key, method.invoke(bean, new Object[]{}));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 从url中解析出参数名的值
     *
     * @param url
     * @param name 参数名
     * @return
     */
    public static String getParameter(String url, String name) {
        Pattern p = Pattern.compile("(\\?|&+|)(.+?)=([^&]*)");
        Matcher m = p.matcher(url);
        while (m.find()) {
            String paraName = m.group(2);
            if (StringUtils.equals(name, paraName))
                return m.group(3);
        }
        return null;
    }

    /**
     * 获得唯一序列号
     *
     * @return
     */
    public static final String getUUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}