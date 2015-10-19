package com.gys.kitten.core.permission.service;

import com.gys.kitten.core.permission.dao.AdminMapper;
import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午4:17
 * Des: 权限管理，用户管理服务层
 */

@Service("adminService")
@Transactional
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Transactional(readOnly = true)
    public Page<AdminEntity> getAdminList(Page<AdminEntity> page, String name, String status) throws Exception {
        List<AdminEntity> adminList = adminMapper.getAdminList(page, name, status);
        Integer iTotalDisplayRecords = adminMapper.getAdminCount(name, status);
        page.setiTotalDisplayRecords(iTotalDisplayRecords);
        page.setData(adminList);
        return page;
    }

    @Transactional(readOnly = true)
    public List<AdminEntity> getAdminList() throws Exception {
        return adminMapper.getAllAdmin();
    }

    @Transactional(readOnly = true)
    public AdminEntity getAdmin(String legacyId, String pwd) throws Exception {
        return adminMapper.getAdmin(legacyId, pwd);
    }

    @Transactional(readOnly = true)
    public AdminEntity getAdminBylegacyId(String legacyId) throws Exception {
        return adminMapper.getAdminBylegacyId(legacyId);
    }

    @Transactional(readOnly = true)
    public AdminEntity getAdminById(long adminId) throws Exception {
        return adminMapper.getAdminById(adminId);
    }

    @Transactional(readOnly = false)
    public int addUser(AdminEntity adminEntity) throws Exception {
        return adminMapper.addUser(adminEntity);
    }

    @Transactional(readOnly = false)
    public int updateUser(AdminEntity adminEntity) throws Exception {
        return adminMapper.updateUser(adminEntity);
    }

    @Transactional(readOnly = false)
    public int enableUser(AdminEntity adminEntity) throws Exception {
        return adminMapper.enableUser(adminEntity);
    }

    //TODO 应该删除相关的角色用户关系表，否则会产生垃圾数据
    @Transactional(readOnly = false)
    public int deleteUser(AdminEntity adminEntity) throws Exception {
        return adminMapper.deleteUser(adminEntity);
    }

    @Transactional(readOnly = false)
    public int updatePassWord(AdminEntity adminEntity) throws Exception {
        return adminMapper.updatePassWord(adminEntity);
    }
}
