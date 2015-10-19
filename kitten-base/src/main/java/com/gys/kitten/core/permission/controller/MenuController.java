package com.gys.kitten.core.permission.controller;

import com.gys.kitten.core.permission.entity.MenuEntity;
import com.gys.kitten.core.permission.service.MenuService;
import com.gys.kitten.core.util.AuthUtil;
import com.gys.kitten.core.vo.Page;
import com.gys.kitten.core.vo.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-28
 * Time: 上午10:09
 * Des:权限管理，菜单管理action
 */
@Controller
@RequestMapping("/permission/menu")
public class MenuController {

    private final static String STATUS_ACTIVE = "ACTIVE";

    private final static String STATUS_INACTIVE = "INACTIVE";
    @Autowired
    private MenuService menuService;

    @RequestMapping("/list")
    @ResponseBody
    public Page getMenuList() throws Exception {
        List<MenuEntity> menuList = menuService.getMenuList();
        return new Page<MenuEntity>(menuList);
    }

    @RequestMapping("/manage")
    @ResponseBody
    public ResponseObject manageMenu(
            @RequestParam(value = "menuID", required = false) String menuID,
            @RequestParam(value = "parentID", required = false) String parentID,
            @RequestParam(value = "menuName", required = true) String menuName,
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "handleFlag", required = true) String handleFlag,
            HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuName(menuName);
        menuEntity.setUrl(url);
        menuEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        try {
            if ("add".equals(handleFlag)) {
                menuEntity.setStatus(STATUS_ACTIVE);
                menuEntity.setParentID(Long.parseLong(parentID));
                menuEntity.setCreatedBy(AuthUtil.getCurrentUser(request).getName());
                menuService.menuAdd(menuEntity);
            } else {
                menuEntity.setMenuID(Long.parseLong(menuID));
                menuService.menuUpdate(menuEntity);
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
    public ResponseObject enableMenu(@RequestParam(value = "menuID", required = true) long menuID,
                                     @RequestParam(value = "status", required = true) String status, HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuID(menuID);
        menuEntity.setUpdatedBy(AuthUtil.getCurrentUser(request).getName());
        menuEntity.setStatus(STATUS_ACTIVE.equals(status) ? STATUS_INACTIVE : STATUS_ACTIVE);
        try {
            menuService.menuEnable(menuEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseObject deleteMenu(@RequestParam(value = "menuID", required = true) long menuID) {
        ResponseObject responseObject = new ResponseObject();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuID(menuID);
        try {
            menuService.menuDelete(menuEntity);
            responseObject.setSuccess(true);
        } catch (Exception e) {
            responseObject.setSuccess(false);
            responseObject.setMessage(e.getMessage());
        }
        return responseObject;
    }
}
