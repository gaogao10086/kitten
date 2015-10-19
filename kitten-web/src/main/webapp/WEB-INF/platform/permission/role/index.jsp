<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>

<script type="text/javascript" src="${ctx}/resources/commonjs/init.js"></script>
<script type="text/javascript">
    var oTable;
    var validateSet = {
        nameAdd: {must: true, ftip: '<span style="color: green">必填字段</span>'},
        description: {must: false, ftip: ''}
    };
    $(document).ready(function () {
        form.friend.init(validateSet);
        oTable = $('#grid').dataTable({
            "bFilter": false,
            "bServerSide": true,
            "bPaginate": false,
            "bInfo": false,
            "bSort": true,
            "oLanguage": oLanguage,
            "sAjaxDataProp": 'data',
            "sAjaxSource": "${ctx}/platform/permission/role/listJson",
            "aoColumns": [
                {"mDataProp": 'name'},
                {"mDataProp": 'description', "sWidth": 350},
                {"mDataProp": 'status', "bUseRendered": false, "fnRender": function (obj) {
                    var status = obj.aData.status == 'ACTIVE' ? "<span class='label label-success'>可用</span>" : "<span class='label label-important'>不可用</span>";
                    return status;
                }},
                {"mDataProp": "roleId", "sWidth": 350, "bUseRendered": false, "fnRender": function (obj) {
                    return "<a href=\"javascript:;\" class='btn btn-info' onclick=\"showUpdateWindow('" + obj.aData.roleId + "');\"><i class='icon-edit icon-white'></i>修改</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-danger' onclick=\"deleteRole('" + obj.aData.roleId + "');\"><i class='icon-trash icon-white'></i>删除</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-warning' onclick=\"enableRole('" + obj.aData.roleId + "','" + obj.aData.status + "');\"><i class='ui-icon-document-b icon-white'></i>启/停用</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-inverse' onclick=\"menuManage('" + obj.aData.roleId + "');\"><i class='ui-icon-help icon-white'></i>菜单管理</a>";
                }}
            ],
            "fnServerParams": function (aoData) {
                aoData.push({name: "name", value: $.trim($("#name").val())});
                aoData.push({name: "status", value: $.trim($("#status").val())});
            },
            "fnServerData": function (sSource, aoData, fnCallback) {
                $.ajax({
                    dataType: 'json',
                    type: 'post',
                    url: sSource,
                    data: aoData,
                    success: fnCallback
                });
            },
            "fnRowCallback": function (nRow, aData) {
                $(nRow).attr("id", 'role-' + aData.roleId);
                return nRow;
            },
            "fnDrawCallback": function () {
                var data = this.fnGetData();
                console.log(data);
            }
        });
    });

    //刷新表格
    function loadContent() {
        oTable.fnDraw();
    }

    //返回到角色列表页面
    function backRoleList() {
        var url = "${ctx}/platform/platform/permission/role/index";
        setTimeout(function () {
            $('#main').loading().load(url, {});
        }, 10);
    }

    //提交角色管理表单
    function submitForm() {
        if (form.validate(validateSet)) {
            $('#userForm').ajaxSubmit({
                success: function (result) {
                    if (result.success) {
                        $('#userWindow').modal('toggle');
                        loadContent();
                        message_box.show('操作成功', 'success');
                    } else {
                        message_box.show("服务器错误：" + result.message, 'error');
                    }
                },
                error: commonErrorHandler
            });
        }
    }

    function showUpdateWindow(roleId) {
        var name = $("tr[id='role-" + roleId + "']").eq(0).children().eq(0).text();
        var description = $("tr[id='role-" + roleId + "']").eq(0).children().eq(1).text();
        $("#roleId").attr("value", roleId);
        $("#handleFlag").attr("value", "update");
        $("#nameAdd").attr("value", name);
        $("#description").attr("value", description);
        $('#roleWindow').modal('toggle');
    }

    function showAddWinodw() {
        $("#nameAdd").attr("value", "");
        $("#description").attr("value", "");
        $("#handleFlag").attr("value", "add");
        $('#roleWindow').modal('toggle');
    }

    function menuManage(roleId) {
        var url = "${ctx}/platform/permission/role/menuManage";
        setTimeout(function () {
            $('#mainPanel').loading().load(url, {
                roleId: roleId
            });
        }, 10);
    }

    function deleteRole(roleId) {
        bootbox.confirm('确定删除该条记录？', function (confirmBtn) {
            if (confirmBtn) {
                $.ajax({url: '${ctx}/platform/permission/role/delete?roleId=' + roleId,
                    success: function (result) {
                        if (result.success) {
                            $("tr[id='role-" + roleId + "']").remove();
                            message_box.show('删除成功', 'success');
                        } else {
                            message_box.show("服务器错误：" + result.message, 'error');
                        }
                    },
                    error: commonErrorHandler
                });
            }
        });
    }

    function enableRole(roleId, status) {
        $.ajax({url: '${ctx}/platform/permission/role/enable?roleId=' + roleId + '&status=' + status,
            success: function (result) {
                if (result.success) {
                    message_box.show('操作成功', 'success');
                    loadContent();
                }
            },
            error: function (xhr) {
                message_box.show("操作失败!请重试!" + xhr.status + " " + xhr.statusText, 'error');
            }
        });
    }

    //提交角色管理表单
    function submitForm() {
        if (form.validate(validateSet)) {
            $('#roleForm').ajaxSubmit({
                        success: function (result) {
                            if (result.success) {
                                $('#roleWindow').modal('toggle');
                                loadContent();
                                message_box.show('操作成功', 'success');
                            } else {
                                message_box.show("服务器错误：" + result.message, 'error');
                            }
                        },
                        error: commonErrorHandler
                    }
            );
        }
    }
</script>

<div id="mainPanel" class="box">
    <!--主面板标题-->
    <div class="box-header well" data-original-title>
        <h2><i class="icon-align-justify"></i>用户管理</h2>

        <div class="box-icon">
            <a href="javascript:void(0);" class="btn btn-resize btn-round"><i class="icon-resize-full"></i></a>
            <a href="javascript:void(0);" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
        </div>
    </div>
    <!--主面板内容-->
    <div class="box-content">
        <form class="form-inline row-fluid" style="padding:20px 0px 20px 20px;">
            <div class="span5" style="width:350px">
                按角色名模糊查询：
                <input type="text" id="name" name="name">
            </div>

            <input name="status" id="status" value="ALL" type="hidden"/>

            <div id="drillTagDiv" class="span4 btn-group" data-toggle="buttons-radio" style="width:180px">
                <a class="btn btn-info active"
                   onclick="$('#status').val('ALL');">全部</a>
                <a class="btn btn-info"
                   onclick="$('#status').val('ACTIVE');">可用</a>
                <a class="btn btn-info"
                   onclick="$('#status').val('INACTIVE');">不可用</a>
            </div>
            <div class="span3">
                <a type="button" class="btn btn-primary" href="javascript:;" onclick="loadContent();">
                    &nbsp;查询&nbsp;
                    <li class="icon-search"></li>
                </a>
                <a role="button" class="btn btn-primary" href="javascript:;" style="margin-left:10px;"
                   onclick="showAddWinodw();">
                    &nbsp;新增&nbsp;
                    <li class="icon-flag"></li>
                </a>
            </div>
        </form>
        <!--结果区域-->
        <div class="box">
            <div class="box-header well" data-original-title>
                <h2><i class="icon-user"></i>角色列表</h2>
            </div>
            <div id="content" class="box-content">
                <table id="grid" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>角色名</th>
                        <th>描述</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="roleWindow" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">角色管理</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="roleForm" method="post"
              action="${ctx}/platform/permission/role/manage">
            <input type="hidden" name="roleId" id="roleId"/>
            <input type="hidden" name="handleFlag" id="handleFlag"/>

            <div class="control-group">
                <label class="control-label" for="nameAdd">角色名称：</label>

                <div class="controls">
                    <input type="text" name="nameAdd" id="nameAdd" placeholder="请输入角色名称"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="description">描述：</label>

                <div class="controls">
                    <input type="text" name="description" id="description"/>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_submit" onclick="submitForm()">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>