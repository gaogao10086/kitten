package com.gys.kitten.core.permission.service;

import com.gys.kitten.core.permission.dao.MenuMapper;
import com.gys.kitten.core.permission.dao.RoleMapper;
import com.gys.kitten.core.permission.entity.MenuEntity;
import com.gys.kitten.core.permission.entity.RoleMenuMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午4:17
 * Des: 权限管理，菜单管理服务层
 */
@Service
@Transactional
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Transactional(readOnly = true)
    public List<MenuEntity> getMenusByAdminId(Long adminId) throws Exception {
        return menuMapper.getMenusByAdminId(adminId);
    }

    @Transactional(readOnly = true)
    public List<MenuEntity> getMenuList() throws Exception {
        return menuMapper.getMenuList();
    }

    @Transactional(readOnly = true)
    public List<MenuEntity> getActiveMenuList() throws Exception {
        return menuMapper.getActiveMenuList();
    }

    @Transactional(readOnly = true)
    public List<Long> getRoleMenuList(long roleId) throws Exception {
        return menuMapper.getRoleMenuList(roleId);
    }

    @Transactional(readOnly = false)
    public int menuAdd(MenuEntity menuEntity) throws Exception {
        return menuMapper.menuAdd(menuEntity);
    }

    @Transactional(readOnly = false)
    public int menuUpdate(MenuEntity menuEntity) throws Exception {
        return menuMapper.menuUpdate(menuEntity);
    }

    @Transactional(readOnly = false)
    public int menuEnable(MenuEntity menuEntity) throws Exception {
        List<MenuEntity> childrenMenuList = new ArrayList<MenuEntity>();
        childrenMenuList = getChildrenMenuList(menuEntity, childrenMenuList);
        childrenMenuList.add(menuEntity);
        for (MenuEntity me : childrenMenuList) {
            me.setUpdatedBy(menuEntity.getUpdatedBy());
            me.setStatus(menuEntity.getStatus());
            menuMapper.menuEnable(me);
        }
        return childrenMenuList.size();
    }

    @Transactional(readOnly = false)
    public int menuDelete(MenuEntity menuEntity) throws Exception {
        List<MenuEntity> childrenMenuList = new ArrayList<MenuEntity>();
        RoleMenuMember roleMenuMember = new RoleMenuMember();
        childrenMenuList = getChildrenMenuList(menuEntity, childrenMenuList);
        childrenMenuList.add(menuEntity);
        for (MenuEntity me : childrenMenuList) {
            roleMenuMember.setMenuID(me.getMenuID());
            roleMapper.roleMenuMemberDelete(roleMenuMember);
            menuMapper.menuDelete(me);
        }
        return childrenMenuList.size();
    }

    /**
     * 递归获得所有的子菜单
     *
     * @param menuEntity
     * @param resultList
     * @return
     * @throws Exception
     */
    private List<MenuEntity> getChildrenMenuList(MenuEntity menuEntity, List<MenuEntity> resultList) throws Exception {
        List<MenuEntity> childrenMenuList = menuMapper.getMenuByParentId(menuEntity.getMenuID());
        resultList.addAll(childrenMenuList);
        for (MenuEntity me : childrenMenuList) {
            getChildrenMenuList(me, resultList);
        }
        return resultList;
    }
}
