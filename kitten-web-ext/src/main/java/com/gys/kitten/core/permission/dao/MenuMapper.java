package com.gys.kitten.core.permission.dao;

import com.gys.kitten.core.permission.entity.MenuEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Author: kitten
 * Date: 13-12-26
 * Time: 下午3:47
 * Des:权限管理，菜单管理
 */
public interface MenuMapper {
    @SelectProvider(type = MenuProvider.class, method = "getMenuList")
    public List<MenuEntity> getMenuList() throws Exception;

    @SelectProvider(type = MenuProvider.class, method = "getActiveMenuList")
    public List<MenuEntity> getActiveMenuList() throws Exception;

    @SelectProvider(type = MenuProvider.class, method = "getRoleMenuList")
    public List<Long> getRoleMenuList(@Param("roleId") long roleId) throws Exception;

    @SelectProvider(type = MenuProvider.class, method = "getMenuById")
    public MenuEntity getMenuById(@Param("menuID") long menuID) throws Exception;

    @SelectProvider(type = MenuProvider.class, method = "getMenuByParentId")
    public List<MenuEntity> getMenuByParentId(@Param("parentID") long parentID) throws Exception;

    @SelectProvider(type = MenuProvider.class, method = "getMenusByAdminId")
    public List<MenuEntity> getMenusByAdminId(@Param("adminId") Long adminId) throws Exception;

    @Insert("INSERT INTO Menu (menuName,parentID,url,createTime,updateTime,createdBy,updatedBy,status) " +
            "VALUES(#{menuName},#{parentID},#{url},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{createdBy},#{updatedBy},#{status})")
    public int menuAdd(MenuEntity menuEntity) throws Exception;

    @Update("UPDATE Menu SET menuName=#{menuName},url=#{url},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} " +
            "WHERE menuID=#{menuID}")
    public int menuUpdate(MenuEntity menuEntity) throws Exception;

    @Delete("DELETE FROM Menu Where menuID=#{menuID} ")
    public int menuDelete(MenuEntity menuEntity) throws Exception;

    @Delete("UPDATE Menu SET status=#{status},updateTime=CURRENT_TIMESTAMP,updatedBy=#{updatedBy} " +
            "WHERE menuID=#{menuID}")
    public int menuEnable(MenuEntity menuEntity) throws Exception;

    class MenuProvider {
        public String getMenusByAdminId(Map<String, Object> params) {
            Long adminId = (Long) params.get("adminId");
            StringBuilder sql = new StringBuilder("SELECT DISTINCT(m.menuID),m.menuName,m.url,m.parentID from AdminRoleMember arm left join RoleMenuMember rmm ON arm.roleId = rmm.roleID" +
                    " left join  Menu m  ON rmm.menuID = m.menuID where 1=1   AND m.status='ACTIVE' ");
            if (adminId != null && adminId != 0L) {
                sql.append("and adminId =").append(adminId);
            }
            sql.append(" ORDER BY m.parentID,m.menuID");
            return sql.toString();
        }

        public String getMenuList(Map<String, Object> params) {
            StringBuilder sql = new StringBuilder("SELECT * FROM  Menu ");
            return sql.toString();
        }

        public String getActiveMenuList(Map<String, Object> params) {
            StringBuilder sql = new StringBuilder("SELECT * FROM  Menu WHERE status = 'ACTIVE'");
            return sql.toString();
        }

        public String getRoleMenuList(Map<String, Object> params) {
            Long roleId = (Long) params.get("roleId");
            StringBuilder sql = new StringBuilder("SELECT M.menuID FROM  Menu M , RoleMenuMember RM WHERE  M.menuID=RM.menuID  AND M.status = 'ACTIVE' AND RM.roleId=");
            sql.append(roleId);
            return sql.toString();
        }

        public String getMenuById(Map<String, Object> params) {
            long menuID = (Long) params.get("menuID");
            StringBuilder sql = new StringBuilder("SELECT * FROM  Menu WHERE menuID=");
            sql.append(menuID);
            return sql.toString();
        }

        public String getMenuByParentId(Map<String, Object> params) {
            long parentID = (Long) params.get("parentID");
            StringBuilder sql = new StringBuilder("SELECT * FROM  Menu WHERE parentID=");
            sql.append(parentID);
            return sql.toString();
        }
    }
}
