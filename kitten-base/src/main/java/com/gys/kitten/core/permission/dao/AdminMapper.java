package com.gys.kitten.core.permission.dao;

import com.gys.kitten.core.permission.entity.AdminEntity;
import com.gys.kitten.core.util.PageUtil;
import com.gys.kitten.core.vo.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午3:47
 * Des:权限管理，用户管理
 */
public interface AdminMapper {

    @Insert("INSERT INTO Admin (name,legacyId,password,status,createTime,updateTime,createdBy,updatedBy) VALUES (#{name},#{legacyId},#{password},#{status},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{createdBy},#{updatedBy})")
    int addUser(AdminEntity adminEntity) throws Exception;

    @Update("UPDATE Admin SET name = #{name},legacyId=#{legacyId},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} WHERE adminId=#{adminId}")
    int updateUser(AdminEntity adminEntity) throws Exception;

    @Update("UPDATE Admin SET  status = #{status},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} WHERE adminId=#{adminId}")
    int enableUser(AdminEntity adminEntity) throws Exception;

    @Update("UPDATE Admin SET  password = #{password},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} WHERE adminId=#{adminId}")
    int updatePassWord(AdminEntity adminEntity) throws Exception;

    @Delete("DELETE FROM  Admin  WHERE adminId=#{adminId}")
    int deleteUser(AdminEntity adminEntity) throws Exception;

    @SelectProvider(type = AdminSelectProvider.class, method = "getAdminList")
    List<AdminEntity> getAdminList(
            @Param("page") Page page,
            @Param("name") String name,
            @Param("status") String status
    ) throws Exception;

    @Select("SELECT * FROM Admin")
    List<AdminEntity> getAllAdmin() throws Exception;

    @SelectProvider(type = AdminSelectProvider.class, method = "getAdminCount")
    Integer getAdminCount(
            @Param("name") String name,
            @Param("status") String status
    ) throws Exception;

    @SelectProvider(type = AdminSelectProvider.class, method = "getAdmin")
    AdminEntity getAdmin(
            @Param("legacyId") String legacyId,
            @Param("pwd") String pwd
    ) throws Exception;

    @SelectProvider(type = AdminSelectProvider.class, method = "getAdminById")
    AdminEntity getAdminById(
            @Param("adminId") long adminId
    ) throws Exception;

    @SelectProvider(type = AdminSelectProvider.class, method = "getAdminBylegacyId")
    AdminEntity getAdminBylegacyId(
            @Param("legacyId") String legacyId
    ) throws Exception;

    class AdminSelectProvider {

        public String getAdminList(Map<String, Object> params) {
            String name = (String) params.get("name");
            String status = (String) params.get("status");
            Page page = (Page) params.get("page");
            StringBuilder sql = new StringBuilder("SELECT * FROM Admin WHERE 1=1 ");
            if (StringUtils.isNotEmpty(name)) {
                sql.append("AND name like '%").append(name).append("%' ");
            }
            if (StringUtils.isNotEmpty(status) && !status.equals("ALL")) {
                sql.append("AND status ='").append(status).append("'");
            }
            sql = PageUtil.orderAppend(page, sql);
            sql = PageUtil.limitAppend(page, sql);
            return sql.toString();
        }

        public String getAdminCount(Map<String, Object> params) {
            String name = (String) params.get("name");
            String status = (String) params.get("status");
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Admin WHERE 1=1 ");
            if (StringUtils.isNotEmpty(name)) {
                sql.append("AND name like '%").append(name).append("%' ");
            }
            if (StringUtils.isNotEmpty(status) && !status.equals("ALL")) {
                sql.append("AND status ='").append(status).append("'");
            }
            return sql.toString();
        }

        public String getAdminById(Map<String, Object> params) {
            Long adminId = (Long) params.get("adminId");
            StringBuilder sql = new StringBuilder("SELECT * FROM Admin WHERE adminId=");
            sql.append(adminId);
            return sql.toString();
        }

        public String getAdminBylegacyId(Map<String, Object> params) {
            String legacyId = (String) params.get("legacyId");
            StringBuilder sql = new StringBuilder("SELECT * FROM Admin WHERE legacyId='");
            sql.append(legacyId);
            sql.append("'");
            return sql.toString();
        }

        public String getAdmin(Map<String, Object> params) {
            String legacyId = (String) params.get("legacyId");
            String pwd = (String) params.get("pwd");
            StringBuilder sql = new StringBuilder("SELECT * from admin where 1=1 ");
            if (!StringUtils.isEmpty(legacyId)) {
                sql.append("and legacyId ='").append(legacyId).append("'");
            }
            if (!StringUtils.isEmpty(pwd)) {
                sql.append("and password ='").append(pwd).append("'");
            }
            return sql.toString();
        }
    }
}
