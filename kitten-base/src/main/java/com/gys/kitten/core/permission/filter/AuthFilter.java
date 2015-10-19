package com.gys.kitten.core.permission.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: kitten
 * Date: 13-12-27
 * Time: 下午5:12
 */
public class AuthFilter implements Filter {
    /**
     * 不要检查的url类型
     */
    private String[] escapeUrls = {"/platform", "/platform/", "/platform/login", "/platform/frame", "/platform/logout"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        if (!check(url, escapeUrls)) {
            HttpSession session = req.getSession();
            if (session.getAttribute("user") == null) {
                HttpServletResponse rsp = (HttpServletResponse) response;
                PrintWriter out = rsp.getWriter();
                out.println("<script language=\"javaScript\">"
                        + "parent.location.href='/kitten-web'"
                        + "</script>");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private boolean check(String element, String[] elements) {
        for (String e : elements) {
            if (element.endsWith(e)) {
                return true;
            }
        }
        return false;
    }
}
