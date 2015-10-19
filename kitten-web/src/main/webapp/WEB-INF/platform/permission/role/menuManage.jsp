<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<head>
    <script type="text/javascript">
        var checkedNode = [];
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
            }
        };
        //初始化菜单树形
        function ztreeInit() {
            var treeNodes = [
                { id: 0, pId: -1, name: 'kitten-web系统功能树', open: true}
            ];
            $.ajax({url: '${ctx}/platform/permission/role/getMenuTree?roleId=${roleEntity.roleId}',
                success: function (result) {
                    var dataList = result.data;
                    dataList.forEach(function (e) {
                        e.name = e.menuName;
                        e.id = e.menuID;
                        e.pId = e.parentID;
                        e.url = '';
                        e.open = true;
                        if (e.checked == true) {
                            checkedNode.push(e.id);
                        }
                    });
                    treeNodes = treeNodes.concat(dataList);
                    $.fn.zTree.init($("#menuTree"), setting, treeNodes);
                },
                error: commonErrorHandler
            });
        }

        //保存角色权限
        function saveRoleMenu() {
            var addList = [];
            var delList = [];
            var nodesId = [];
            var treeObj = $.fn.zTree.getZTreeObj("menuTree");
            var nodes = treeObj.getCheckedNodes(true);
            nodes.forEach(function (e) {
                if (!checkedNode.contain(e.id) && e.id != 0) {
                    addList.push(e.id);
                }
                nodesId.push(e.id);
            });
            checkedNode.forEach(function (e) {
                if (!nodesId.contain(e)) {
                    delList.push(e);
                }
            });
            $.ajax({url: '${ctx}/platform/permission/role/saveRoleMenu',
                data: {addList: addList, delList: delList, roleId: $("#roleId").val()},
                success: function (result) {
                    message_box.show('新分配了' + addList.length + '个菜单，删除了' + delList.length + '个菜单', 'success');
                    ztreeInit();
                    addList.length = 0;
                    delList.length = 0;
                    nodesId.length = 0;
                },
                error: function (xhr) {
                    message_box.show("操作失败!请重试!" + xhr.status + " " + xhr.statusText, 'error');
                }
            });
        }

        //页面初始化
        $(document).ready(function () {
            ztreeInit();
        });
    </script>

</head>
<div class="box-content">
    <form class="well form-inline">
        <div class="row-fluid">
            <div class="span4">
                <label class="control-label" style="font-weight: bold;margin-top: 3px;">为角色：<span
                        style="TEXT-DECORATION: underline">${roleEntity.name}</span>&nbsp;分配菜单</label>
            </div>

            <input name="roleId" id="roleId" value="${roleEntity.roleId}" type="hidden"/>

            <div class="span4">
                <a role="button" class="btn btn-primary" href="javascript:;" style="margin-left:10px;"
                   onclick="saveRoleMenu();">
                    &nbsp;保存&nbsp;
                    <li class="icon-flag"></li>
                </a>
                <a role="button" class="btn btn-primary" href="javascript:;" style="margin-left:10px;"
                   onclick="backRoleList();">
                    &nbsp;返回角色列表&nbsp;
                    <li class="icon-home"></li>
                </a>
            </div>
        </div>
    </form>

    <div id="content">
        <div id="menuTree" class="ztree"></div>
    </div>
</div>