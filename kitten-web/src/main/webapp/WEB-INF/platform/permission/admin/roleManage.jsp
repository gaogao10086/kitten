<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>

<script type="text/javascript">
    var oldSeleted = ${roleIdList};
    //返回到用户列表
    function backAdminList() {
        var url = "${ctx}/platform/platform/permission/admin/index";
        setTimeout(function () {
            $('#main').loading().load(url, {});
        }, 10);
    }
    //保存用户角色关系
    function saveAdminRole() {
        var addList = [];
        var delList = [];
        var newSeleted = [];
        $("input[type='checkbox']:checked").each(function (e, o) {
            newSeleted.push(parseInt(o.id.substring(10, o.id.length)));
        });
        newSeleted.forEach(function (e) {
            if (!oldSeleted.contain(e)) {
                addList.push(e);
            }
        });
        oldSeleted.forEach(function (e) {
            if (!newSeleted.contain(e)) {
                delList.push(e);
            }
        });
        $.ajax({
            url: '${ctx}/platform/permission/admin/saveAdminRole',
            data: {addList: addList, delList: delList, adminId: $("#adminId").val()},
            success: function (result) {
                message_box.show('新分配了' + addList.length + '个角色，删除了' + delList.length + '个角色', 'success');
                roleManage($("#adminId").val());
            },
            error: function (xhr) {
                message_box.show("操作失败!请重试!" + xhr.status + " " + xhr.statusText, 'error');
            }
        });
    }
</script>

<div class="box-content">
    <form class="well form-inline">
        <div class="row-fluid">
            <input name="adminId" id="adminId" value="${adminEntity.adminId}" type="hidden"/>

            <div class="span4">
                <label class="control-label" style="font-weight: bold;margin-top: 3px;">为用户：<span
                        style="TEXT-DECORATION: underline">${adminEntity.name}</span>&nbsp;分配角色</label>
            </div>
            <div class="span4">
                <a role="button" class="btn btn-primary" href="javascript:;" style="margin-left:10px;"
                   onclick="saveAdminRole();">
                    &nbsp;保存&nbsp;
                    <li class="icon-flag"></li>
                </a>
                <a role="button" class="btn btn-primary" href="javascript:;" style="margin-left:10px;"
                   onclick="backAdminList();">
                    &nbsp;返回用户列表&nbsp;
                    <li class="icon-home"></li>
                </a>
            </div>
        </div>
    </form>

    <div class="box">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-user"></i>角色列表</h2>
        </div>
        <div class="box-content">
            <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                <tr>
                    <th style="width: 40px;">选择</th>
                    <th>角色名</th>
                    <th>描述</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${roleActiveList}" varStatus="s">
                    <tr id="role-${d.roleId}">
                        <td class="center"><input type="checkbox" id="roleCheck-${d.roleId}"
                                                  <c:if test="${d.checked}">checked="true"</c:if>/>
                        </td>
                        <td class="center">${d.name}</td>
                        <td class="center">${d.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>