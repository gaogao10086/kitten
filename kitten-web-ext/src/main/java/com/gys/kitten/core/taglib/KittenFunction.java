package com.gys.kitten.core.taglib;

import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.util.AuthUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Author: kitten
 * Date: 11-3-15
 * Time: 下午4:17
 * Des: 自定义标签库
 */
public final class KittenFunction {

    public static boolean isContain(List list, Object o) {
        for (Object element : list) {
            if (element.equals(o))
                return true;
        }
        return false;
    }

    public static boolean isContain(Object[] list, Object o) {
        for (Object element : list) {
            String o1 = String.valueOf(o);
            String o2 = String.valueOf(element);
            if (StringUtils.equals(o1, o2))
                return true;
        }
        return false;
    }

    public static boolean hasAuth(HttpServletRequest request, String requestUrl) {
        AdminEntity userInfo = AuthUtil.getCurrentUser(request);
        return true;
    }
}
