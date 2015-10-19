package com.gys.kitten.core.permission.controller;

import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.permission.entity.MenuEntity;
import com.gys.kitten.core.permission.service.AdminService;
import com.gys.kitten.core.permission.service.MenuService;
import com.gys.kitten.core.util.AuthUtil;
import com.gys.kitten.core.vo.ResponseObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Author: kitten
 * Date: 14-1-3
 * Time: 下午5:20
 * Des:首页action
 */
@Controller
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MenuService menuService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String STATUS_INACTIVE = "INACTIVE";

    @RequestMapping("/")
    public String frame() {
        return "platform/main/frame";
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseObject login(HttpServletRequest request,
                                @RequestParam(value = "user", required = true) String user,
                                @RequestParam(value = "pwd", required = true) String pwd) throws Exception {
        ResponseObject responseObject = new ResponseObject();
        AdminEntity admin = adminService.getAdminBylegacyId(user);
        if (admin == null) {
            responseObject.setMessage("该用户不存在，请联系系统管理员！");
            responseObject.setSuccess(false);
            return responseObject;
        }
        admin = adminService.getAdmin(user, DigestUtils.md5Hex(pwd));
        if (admin != null) {
            if (STATUS_INACTIVE.equals(admin.getStatus())) {
                responseObject.setMessage("该用户已经被停用，请联系管理员！");
                responseObject.setSuccess(false);
                return responseObject;
            }
            List<MenuEntity> menus = menuService.getMenusByAdminId(admin.getAdminId());
            AuthUtil.cacheCurrentUser(request, admin);
            request.getSession().setAttribute("user", admin);
            request.getSession().setAttribute("menus", menus);
            responseObject.setSuccess(true);
            return responseObject;
        } else {
            responseObject.setMessage("密码错误，请重试！");
            responseObject.setSuccess(false);
            return responseObject;
        }
    }

    @RequestMapping("/password")
    @ResponseBody
    public ResponseObject password(HttpServletRequest request,
                                   @RequestParam(value = "password", required = true) String password,
                                   @RequestParam(value = "newPassWord", required = true) String newPassWord) throws Exception {
        ResponseObject responseObject = new ResponseObject();
        AdminEntity admin = (AdminEntity) request.getSession().getAttribute("user");
        if (!admin.getPassword().toLowerCase().equals(DigestUtils.md5Hex(password))) {
            responseObject.setSuccess(false);
            responseObject.setMessage("密码错误，请确认！");
            return responseObject;
        }
        admin.setPassword(DigestUtils.md5Hex(newPassWord));
        admin.setUpdatedBy(admin.getName());
        adminService.updatePassWord(admin);
        responseObject.setSuccess(true);
        return responseObject;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        AuthUtil.logout(request);
        return "platform/main/frame";
    }

    @RequestMapping("/**")
    public ModelAndView forword(HttpServletRequest request) {
        String path = request.getRequestURI();
        String rPath;
        ModelAndView mav = new ModelAndView();
        path = path.replaceFirst(request.getContextPath(), "").replaceFirst(request.getServletPath(), "");
        if (StringUtils.endsWithIgnoreCase(path, "index")) {
            rPath = path + ".jsp";
        } else {
            rPath = "/views" + path + "/index.jsp";
            mav.setViewName("views" + path + "/index");
        }
        this.logger.debug("为请求路径[{}]导向页面[{}]。", path, rPath);
        return mav;
    }

    /**
     * 处理本Controller中没有被处理的异常
     *
     * @param ex      没有被捕获的异常
     * @param request 请求
     * @return 应该返回到500页面
     */
    @ExceptionHandler(Exception.class)
    public void handleUncaughtException(Exception ex, HttpServletRequest request, ServletResponse response) throws IOException {
        logger.error("Exception thrown when access " + request.getContextPath(), ex);
        PrintWriter out = response.getWriter();
        out.println("<script language=\"javaScript\">"
                + "parent.location.href='" + "/kitten-web/platform/error/500" + "'"
                + "</script>");
    }
}
