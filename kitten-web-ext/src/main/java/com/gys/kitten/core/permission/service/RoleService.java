package com.gys.kitten.core.permission.service;

import com.gys.kitten.core.permission.dao.RoleMapper;
import com.gys.kitten.core.permission.entity.AdminRoleMember;
import com.gys.kitten.core.permission.entity.RoleEntity;
import com.gys.kitten.core.permission.entity.RoleMenuMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午4:17
 * Des: 权限管理，角色管理服务层
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleEntity> getRoleList(String name, String status) throws Exception {
        return roleMapper.getRoleList(name, status);
    }

    @Transactional(readOnly = true)
    public RoleEntity getRoleById(long roleId) throws Exception {
        return roleMapper.getRoleById(roleId);
    }

    @Transactional(readOnly = true)
    public List<RoleEntity> getRolesByAdminId(Long adminId) throws Exception {
        return roleMapper.getRolesByAdminId(adminId);
    }

    @Transactional(readOnly = false)
    public int addRole(RoleEntity roleEntity) throws Exception {
        return roleMapper.roleAdd(roleEntity);
    }

    @Transactional(readOnly = false)
    public int updateRole(RoleEntity roleEntity) throws Exception {
        return roleMapper.roleUpdate(roleEntity);
    }

    @Transactional(readOnly = false)
    public int enableRole(RoleEntity roleEntity) throws Exception {
        return roleMapper.roleEnable(roleEntity);
    }

    @Transactional(readOnly = false)
    public int deleteRole(RoleEntity roleEntity) throws Exception {
        //TODO 删除角色的同时应该也删除角色用户关系表/菜单角色关系表，否则会留下垃圾数据
        return roleMapper.roleDelete(roleEntity);
    }

    @Transactional(readOnly = false)
    public void saveRoleMenu(Long[] addList, Long[] delList, Long roleId) throws Exception {
        RoleMenuMember roleMenuMember = new RoleMenuMember();
        roleMenuMember.setRoleID(roleId);
        if (addList != null) {
            for (int i = 0, j = addList.length; i < j; i++) {
                roleMenuMember.setMenuID(addList[i]);
                roleMapper.roleMenuMemberAdd(roleMenuMember);
            }
        }
        if (delList != null) {
            for (int i = 0, j = delList.length; i < j; i++) {
                roleMenuMember.setMenuID(delList[i]);
                roleMapper.roleMenuMemberDelete(roleMenuMember);
            }
        }
    }


    @Transactional(readOnly = false)
    public void saveAdminRole(Long[] addList, Long[] delList, Long adminId) throws Exception {
        AdminRoleMember adminRoleMember = new AdminRoleMember();
        adminRoleMember.setAdminId(adminId);
        if (addList != null) {
            for (int i = 0, j = addList.length; i < j; i++) {
                adminRoleMember.setRoleId(addList[i]);
                roleMapper.adminRoleMemberAdd(adminRoleMember);
            }
        }
        if (delList != null) {
            for (int i = 0, j = delList.length; i < j; i++) {
                adminRoleMember.setRoleId(delList[i]);
                roleMapper.adminRoleMemberDelete(adminRoleMember);
            }
        }
    }
}
