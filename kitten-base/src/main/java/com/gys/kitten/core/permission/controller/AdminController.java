package com.gys.kitten.core.permission.controller;

import com.gys.kitten.core.permission.csv.AdminCsview;
import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.permission.entity.RoleEntity;
import com.gys.kitten.core.permission.service.AdminService;
import com.gys.kitten.core.permission.service.RoleService;
import com.gys.kitten.core.util.AuthUtil;
import com.gys.kitten.core.util.MD5Util;
import com.gys.kitten.core.util.PageUtil;
import com.gys.kitten.core.vo.Page;
import com.gys.kitten.core.vo.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-25
 * Time: 下午4:15
 * Des:权限管理 用户页面 Action层
 */
@Controller
@RequestMapping("/permission/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String PASSWORD_DEFAULT = MD5Util.toMd5("abc123");

    private final static String STATUS_ACTIVE = "ACTIVE";

    private final static String STATUS_INACTIVE = "INACTIVE";

    @RequestMapping("/listJson")
    @ResponseBody
    public Page getAdminListJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {
        Page<AdminEntity> page = PageUtil.bulidPage(request);
        return adminService.getAdminList(page, name, status);
    }

    @RequestMapping("/excel")
    public ModelAndView getAdminListExcel() throws Exception {
        List<AdminEntity> allAdmin = adminService.getAdminList();
        ModelAndView modelAndView = new ModelAndView();
        AdminCsview csvView = new AdminCsview(allAdmin);
        modelAndView.setView(csvView);
        return modelAndView;
    }

    @RequestMapping("/roleManage")
    public ModelAndView roleManage(@RequestParam(value = "adminId", required = false) Long adminId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("platform/permission/admin/roleManage");
        AdminEntity adminEntity = adminService.getAdminById(adminId);
        List<RoleEntity> roleActiveList = roleService.getRoleList("", STATUS_ACTIVE);
        List<RoleEntity> roleCheckedList = roleService.getRolesByAdminId(adminId);
        List<Long> roleIdList = new ArrayList<Long>();
        for (RoleEntity roleEntity : roleCheckedList) {
            roleIdList.add(roleEntity.getRoleId());
        }
        for (RoleEntity roleEntity : roleActiveList) {
            if (roleIdList.contains(roleEntity.getRoleId())) {
                roleEntity.setChecked(true);
            }
        }
        modelAndView.addObject("roleActiveList", roleActiveList);
        modelAndView.addObject("roleIdList", roleIdList);
        modelAndView.addObject("adminEntity", adminEntity);
        return modelAndView;
    }


    @RequestMapping("/saveAdminRole")
    @ResponseBody
    public ResponseObject saveAdminRole(
            @RequestParam(value = "addList[]", required = false) Long[] addList,
            @RequestParam(value = "delList[]", required = false) Long[] delList,
            @RequestParam(value = "adminId", required = false) Long adminId) {
        ResponseObject responseObject = new ResponseObject();
        try {
            roleService.saveAdminRole(addList, delList, adminId);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }


    @RequestMapping("/manage")
    @ResponseBody
    public ResponseObject manageUser(
            @RequestParam(value = "adminId", required = false) String adminId,
            @RequestParam(value = "nameAdd", required = true) String nameAdd,
            @RequestParam(value = "legacyId", required = false) String legacyId,
            @RequestParam(value = "handleFlag", required = true) String handleFlag,
            HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName(nameAdd);
        adminEntity.setLegacyId(legacyId);
        adminEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        try {
            if ("add".equals(handleFlag)) {
                adminEntity.setPassword(PASSWORD_DEFAULT);
                adminEntity.setStatus(STATUS_ACTIVE);
                adminEntity.setCreatedBy(AuthUtil.getCurrentUser(request).getName());
                adminService.addUser(adminEntity);
            } else {
                adminEntity.setAdminId(Long.parseLong(adminId));
                adminService.updateUser(adminEntity);
            }
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

    @RequestMapping("/enable")
    @ResponseBody
    public ResponseObject enableUser(@RequestParam(value = "adminId", required = false) long adminId,
                                     @RequestParam(value = "status", required = true) String status,
                                     HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(adminId);
        adminEntity.setStatus(STATUS_ACTIVE.equals(status) ? STATUS_INACTIVE : STATUS_ACTIVE);
        adminEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        try {
            adminService.enableUser(adminEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseObject deleteUser(@RequestParam(value = "adminId", required = false) long adminId) {
        ResponseObject responseObject = new ResponseObject();
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminId(adminId);
        try {
            adminService.deleteUser(adminEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

}