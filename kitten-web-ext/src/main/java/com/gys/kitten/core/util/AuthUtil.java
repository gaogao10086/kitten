package com.gys.kitten.core.util;

import com.gys.kitten.core.permission.entity.AdminEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: kitten
 * Date: 14-3-15
 * Time: 下午4:17
 * Des: 权限验证辅助工具类
 */

public final class AuthUtil {

    private AuthUtil() {
    }

    private static final String SESSION_USER_INFO_KEY = "_USER_INFO_";
    private static List<HttpSession> sessions = new ArrayList<HttpSession>();

    public static String getSessionKey(String sessionId) {
        return SESSION_USER_INFO_KEY + sessionId;
    }

    /**
     * 从seerion中获取用户信息.
     *
     * @param httpRequest
     * @return
     */
    public static AdminEntity getCurrentUser(HttpServletRequest httpRequest) {
        AdminEntity userInfo = null;
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            userInfo = (AdminEntity) session.getAttribute(getSessionKey(session.getId()));
        }
        return userInfo;
    }

    /**
     * 在session中存储权限用户信息.
     *
     * @param httpRequest
     * @param userInfo
     * @return
     */
    public static boolean cacheCurrentUser(HttpServletRequest httpRequest, AdminEntity userInfo) {
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            session.setAttribute(getSessionKey(session.getId()), userInfo);
            return true;
        }
        return false;
    }

    /**
     * 用户注销.
     *
     * @param httpRequest
     */
    public static void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 向session列表添加新 session
     *
     * @param session
     */
    public static void addSession(HttpSession session) {
        sessions.add(session);
    }

    /**
     * 从session列表中删除相应的session
     *
     * @param session
     */
    public static void removeSession(HttpSession session) {
        for (HttpSession hs : sessions) {
            if (hs == session) {
                sessions.remove(hs);
                return;
            }
        }
    }

}
