package com.gys.kitten.core.permission.entity;

/**
 * Author: kitten
 * Date: 13-12-29
 * Time: 上午10:59
 * Des:权限管理：角色用户关系表
 */
public class RoleMenuMember {
    private Long roleID;
    private Long menuID;

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public Long getMenuID() {
        return menuID;
    }

    public void setMenuID(Long menuID) {
        this.menuID = menuID;
    }
}
