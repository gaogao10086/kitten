package com.gys.kitten.core.permission.controller;

import com.gys.kitten.core.permission.entity.MenuEntity;
import com.gys.kitten.core.permission.entity.RoleEntity;
import com.gys.kitten.core.permission.service.MenuService;
import com.gys.kitten.core.permission.service.RoleService;
import com.gys.kitten.core.util.AuthUtil;
import com.gys.kitten.core.vo.Page;
import com.gys.kitten.core.vo.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午4:15
 * Des:权限管理 角色页面 Action层
 */
@Controller
@RequestMapping("permission/role")
public class RoleController {

    private final static String STATUS_ACTIVE = "ACTIVE";

    private final static String STATUS_INACTIVE = "INACTIVE";
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menuManage")
    public ModelAndView menuManage(@RequestParam(value = "roleId", required = false) Long roleId, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("platform/permission/role/menuManage");
        RoleEntity roleEntity = roleService.getRoleById(roleId);
        modelAndView.addObject("roleEntity", roleEntity);
        return modelAndView;
    }

    @RequestMapping("/listJson")
    @ResponseBody
    public Page getRoleListJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status) throws Exception {
        List<RoleEntity> roleList = roleService.getRoleList(name, status);
        return new Page<RoleEntity>(roleList);
    }

    @RequestMapping("/getMenuTree")
    @ResponseBody
    public Page getMenuList(@RequestParam(value = "roleId", required = false) Long roleId) throws Exception {
        List<Long> roleMenuList = menuService.getRoleMenuList(roleId);
        List<MenuEntity> menuList = menuService.getActiveMenuList();
        for (MenuEntity menuEntity : menuList) {
            if (roleMenuList.contains(menuEntity.getMenuID())) {
                menuEntity.setChecked(true);
            }
        }
        return new Page<MenuEntity>(menuList);
    }

    @RequestMapping("/saveRoleMenu")
    @ResponseBody
    public ResponseObject saveRoleMenu(
            @RequestParam(value = "addList[]", required = false) Long[] addList,
            @RequestParam(value = "delList[]", required = false) Long[] delList,
            @RequestParam(value = "roleId", required = false) Long roleId) throws Exception {
        ResponseObject responseObject = new ResponseObject();
        try {
            roleService.saveRoleMenu(addList, delList, roleId);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

    @RequestMapping("/manage")
    @ResponseBody
    public ResponseObject manageRole(
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "nameAdd", required = true) String nameAdd,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "handleFlag", required = true) String handleFlag,
            HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(nameAdd);
        roleEntity.setDescription(description);
        roleEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        try {
            if ("add".equals(handleFlag)) {
                roleEntity.setStatus(STATUS_ACTIVE);
                roleEntity.setCreatedBy(AuthUtil.getCurrentUser(request).getName());
                roleService.addRole(roleEntity);
            } else {
                roleEntity.setRoleId(Long.parseLong(roleId));
                roleService.updateRole(roleEntity);
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
    public ResponseObject enableRole(
            @RequestParam(value = "roleId", required = false) long roleId,
            @RequestParam(value = "status", required = true) String status,
            HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(roleId);
        roleEntity.setStatus(STATUS_ACTIVE.equals(status) ? STATUS_INACTIVE : STATUS_ACTIVE);
        roleEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        try {
            roleService.enableRole(roleEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseObject deleteRole(@RequestParam(value = "roleId", required = false) long roleId) {
        ResponseObject responseObject = new ResponseObject();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(roleId);
        try {
            roleService.deleteRole(roleEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }
}