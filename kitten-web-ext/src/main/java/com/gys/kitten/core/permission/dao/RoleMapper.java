package com.gys.kitten.core.permission.dao;

import com.gys.kitten.core.permission.entity.AdminRoleMember;
import com.gys.kitten.core.permission.entity.RoleEntity;
import com.gys.kitten.core.permission.entity.RoleMenuMember;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午3:47
 * Des:权限管理，角色管理
 */
public interface RoleMapper {
    @SelectProvider(type = RoleProvider.class, method = "getRoleList")
    public List<RoleEntity> getRoleList(
            @Param("name") String name,
            @Param("status") String status
    ) throws Exception;

    @SelectProvider(type = RoleProvider.class, method = "getRolesByAdminId")
    public List<RoleEntity> getRolesByAdminId(
            @Param("adminId") Long adminId
    ) throws Exception;

    @SelectProvider(type = RoleProvider.class, method = "getRoleById")
    public RoleEntity getRoleById(
            @Param("roleId") long roleId
    ) throws Exception;

    @Insert("INSERT INTO AdminRole (name,description,status,createTime,updateTime,createdBy,updatedBy) VALUES (#{name},#{description},#{status},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{createdBy},#{updatedBy})")
    public int roleAdd(RoleEntity roleEntity) throws Exception;

    @Insert("INSERT INTO AdminRoleMember (adminId,roleId) VALUES (#{adminId},#{roleId})")
    public int adminRoleMemberAdd(AdminRoleMember adminRoleMember) throws Exception;

    @Insert("INSERT INTO RoleMenuMember (menuID,roleID) VALUES (#{menuID},#{roleID})")
    public int roleMenuMemberAdd(RoleMenuMember roleMenuMember) throws Exception;

    @Update("UPDATE AdminRole SET name = #{name},description=#{description},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} WHERE roleId=#{roleId}")
    public int roleUpdate(RoleEntity roleEntity) throws Exception;

    @Update("UPDATE AdminRole SET  status = #{status},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} WHERE roleId=#{roleId}")
    public int roleEnable(RoleEntity roleEntity) throws Exception;

    @Delete("DELETE FROM  AdminRole  WHERE roleId=#{roleId}")
    public int roleDelete(RoleEntity roleEntity) throws Exception;

    @DeleteProvider(type = RoleProvider.class, method = "adminRoleMemberDelete")
    public int adminRoleMemberDelete(@Param("adminRoleMember") AdminRoleMember adminRoleMember) throws Exception;

    @DeleteProvider(type = RoleProvider.class, method = "roleMenuMemberDelete")
    public int roleMenuMemberDelete(@Param("roleMenuMember") RoleMenuMember roleMenuMember) throws Exception;

    class RoleProvider {

        public String getRoleList(Map<String, Object> params) {
            String name = (String) params.get("name");
            String status = (String) params.get("status");
            StringBuilder sql = new StringBuilder("select * from AdminRole where 1=1 ");
            if (StringUtils.isNotEmpty(name)) {
                sql.append("and name like '%").append(name).append("%' ");
            }
            if (StringUtils.isNotEmpty(status) && !status.equals("ALL")) {
                sql.append("and status ='").append(status).append("'");
            }
            sql.append(" ORDER BY roleId");
            return sql.toString();
        }

        public String getRolesByAdminId(Map<String, Object> params) {
            Long adminId = (Long) params.get("adminId");
            StringBuilder sql = new StringBuilder("SELECT ar.* FROM AdminRoleMember arm LEFT JOIN AdminRole ar ON arm.roleId = ar.roleID WHERE ar.status = 'ACTIVE' ");
            if (adminId != null && adminId != 0L) {
                sql.append("AND adminId =").append(adminId);
            }
            sql.append(" ORDER BY ar.roleId");
            return sql.toString();
        }

        public String getRoleById(Map<String, Object> params) {
            Long roleId = (Long) params.get("roleId");
            StringBuilder sql = new StringBuilder("select * from AdminRole where 1=1 ");
            sql.append("and roleId ='").append(roleId).append("'");
            return sql.toString();
        }


        public String roleMenuMemberDelete(Map<String, Object> params) {
            RoleMenuMember roleMenuMember = (RoleMenuMember) params.get("roleMenuMember");
            StringBuilder sql = new StringBuilder("DELETE  from rolemenumember where 1=1 ");
            if (roleMenuMember.getMenuID() != null) {
                sql.append("AND menuID =").append(roleMenuMember.getMenuID());
            }
            if (roleMenuMember.getRoleID() != null) {
                sql.append("AND roleID =").append(roleMenuMember.getRoleID());
            }
            return sql.toString();
        }

        public String adminRoleMemberDelete(Map<String, Object> params) {
            AdminRoleMember adminRoleMember = (AdminRoleMember) params.get("adminRoleMember");
            StringBuilder sql = new StringBuilder("DELETE  from adminrolemember where 1=1 ");
            if (adminRoleMember.getAdminId() != null) {
                sql.append("AND adminId =").append(adminRoleMember.getAdminId());
            }
            if (adminRoleMember.getRoleId() != null) {
                sql.append("AND roleID =").append(adminRoleMember.getRoleId());
            }
            return sql.toString();
        }
    }
}
