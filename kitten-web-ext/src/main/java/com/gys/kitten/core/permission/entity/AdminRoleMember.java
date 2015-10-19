package com.gys.kitten.core.permission.entity;

/**
 * Author: kitten
 * Date: 13-12-29
 * Time: 上午10:59
 * Des:权限管理：角色菜单关联表
 */
public class AdminRoleMember {
    private Long adminId;
    private Long roleId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
