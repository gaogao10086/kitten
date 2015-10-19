<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>

<script type="text/javascript" src="${ctx}/resources/commonjs/init.js"></script>
<script type="text/javascript">
    var oTable;
    var validateSet = {
        nameAdd: {must: true, ftip: '<span style="color: green">必填字段</span>'},
        legacyId: {must: true, ftip: '<span style="color: green">必填字段</span>'}
    };
    $(document).ready(function () {
        form.friend.init(validateSet);
        oTable = createTable({
            "domId": "grid",
            "url": "${ctx}/platform/permission/admin/listJson",
            "columns": [
                {"mDataProp": 'name', "sTitle": "用户名", "bUseRendered": false},
                {"mDataProp": 'legacyId', "sTitle": "登陆账号", "bUseRendered": false},
                {"mDataProp": 'status', "sTitle": "可用状态", "bUseRendered": false, "fnRender": function (obj) {
                    var status = obj.aData.status == 'ACTIVE' ? "<span class='label label-success'>可用</span>" : "<span class='label label-important'>不可用</span>";
                    return status;
                }},
                {"mDataProp": "adminId", "sTitle": "操作", "sClass": "center", "sWidth": 350, "bUseRendered": false, "fnRender": function (obj) {
                    return "<a href=\"javascript:;\" class='btn btn-info' onclick=\"showUpdateWindow('" + obj.aData.adminId + "');\"><i class='icon-edit icon-white'></i>修改</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-danger' onclick=\"deleteUser('" + obj.aData.adminId + "');\"><i class='icon-trash icon-white'></i>删除</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-warning' onclick=\"enableUser('" + obj.aData.adminId + "','" + obj.aData.status + "');\"><i class='icon-check icon-white'></i>启/停用</a>&nbsp;&nbsp;" +
                            "<a href=\"javascript:;\" class='btn btn-inverse' onclick=\"roleManage('" + obj.aData.adminId + "');\"><i class='icon-wrench icon-white'></i>角色管理</a>";
                }}
            ],
            "paramsPackage": function (params) {
                params.push({name: "name", value: $.trim($("#name").val())});
                params.push({name: "status", value: $.trim($("#status").val())});
            },
            "rowCallback": function (nRow, aData) {
                $(nRow).attr("id", 'admin-' + aData.adminId);
                return nRow;
            },
            "tableCallback": function () {
            }
        });
    });
    //刷新表格
    function loadContent() {
        oTable.fnDraw();
    }
    //提交用户管理表单
    function submitForm() {
        if (form.validate(validateSet)) {
            $("#userWindow").mask("处理中，请稍等");
            $('#userForm').ajaxSubmit({
                        success: function (result) {
                            if (result.success) {
                                $("#userWindow").unmask();
                                $('#userWindow').modal('toggle');
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
    //展现用户角色管理页面
    function roleManage(adminId) {
        var url = "${ctx}/platform/permission/admin/roleManage";
        setTimeout(function () {
            $('#mainPanel').loading().load(url, {
                adminId: adminId
            });
        }, 10);
    }
    //展现用户更新窗体
    function showUpdateWindow(adminId) {
        var name = $("tr[id='admin-" + adminId + "']").eq(0).children().eq(0).text();
        var legacyId = $("tr[id='admin-" + adminId + "']").eq(0).children().eq(1).text();
        $("#adminId").attr("value", adminId);
        $("#handleFlag").attr("value", "update");
        $("#nameAdd").attr("value", name);
        $("#legacyId").attr("value", legacyId);
        $('#userWindow').modal('toggle');
    }
    //展现用户新增窗体
    function showAddWinodw() {
        $("#nameAdd").attr("value", "");
        $("#legacyId").attr("value", "");
        $("#handleFlag").attr("value", "add");
        $('#userWindow').modal('toggle');
    }
    //删除用户
    function deleteUser(adminId) {
        bootbox.confirm('确定删除该条记录？', function (confirmBtn) {
            if (confirmBtn) {
                $.ajax({url: '${ctx}/platform/permission/admin/delete?adminId=' + adminId,
                    success: function (result) {
                        if (result.success) {
                            $("tr[id='admin-" + adminId + "']").remove();
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
    //启停用用户
    function enableUser(adminId, status) {
        $.ajax({url: '${ctx}/platform/permission/admin/enable?adminId=' + adminId + '&status=' + status,
            success: function (result) {
                if (result.success) {
                    loadContent();
                    message_box.show('操作成功', 'success');
                } else {
                    message_box.show("服务器错误：" + result.message, 'error');
                }
            },
            error: commonErrorHandler
        });
    }
</script>

<!--主面板，ID固定为 mainPanel-->
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
        <!--查询表单-->
        <form class="form-inline row-fluid" style="padding:20px 0px 20px 20px;" id="queryForm"
              action="${ctx}/platform/permission/admin/excel" target="_blank">
            <div class="span5" style="width:350px">
                按用户名模询：
                <input type="text" id="name" name="name" placeholder="模糊查询">
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
            <div class="span4">
                <a type="button" class="btn btn btn-primary" href="javascript:;" onclick="loadContent();">
                    <li class="icon-search"></li>
                    &nbsp;查询&nbsp;
                </a>
                <a role="button" class="btn btn btn-info" href="javascript:;" style="margin-left:10px;"
                   onclick="showAddWinodw();">
                    <li class="icon-plus-sign"></li>
                    &nbsp;新增&nbsp;
                </a>
                <a role="button" class="btn btn " href="javascript:;" style="margin-left:10px;" data-rel="popover" data-content="Mac office is not OK" title="Sorry~"
                   onclick='$("#queryForm").submit();'>
                    <li class="icon-share"></li>
                    &nbsp;导出结果&nbsp;
                </a>
            </div>
        </form>
        <!--结果区域-->
        <div class="box">
            <div class="box-header well" data-original-title>
                <h2><i class="icon-user"></i>用户列表</h2>

            </div>
            <div id="content" class="box-content">
                <table id="grid" class="table table-striped table-bordered bootstrap-datatable datatable"/>
            </div>
        </div>
    </div>
</div>

<!--用户管理表单-->
<div id="userWindow" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="userWindowLabel">用户管理</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="userForm" method="post" action="${ctx}/platform/permission/admin/manage">
            <input type="hidden" name="adminId" id="adminId"/>
            <input type="hidden" name="handleFlag" id="handleFlag"/>

            <div class="control-group">
                <label class="control-label" for="nameAdd">用户名：</label>

                <div class="controls">
                    <input type="text" name="nameAdd" id="nameAdd" placeholder="请输入用户名"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="legacyId">登录名：</label>

                <div class="controls">
                    <input type="text" name="legacyId" id="legacyId" placeholder="请输入登录名，密码默认为‘abc123’"/>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_submit" onclick="submitForm()">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>