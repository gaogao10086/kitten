<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<head>
    <script type="text/javascript" src="${ctx}/resources/commonjs/init.js"></script>
    <script type="text/javascript">
        var zTree;
        var className = "dark";
        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false,
                nameIsHTML: true,
                showTitle: false
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: showRemoveBtn,
                showRenameBtn: showRenameBtn
            },
            data: {
                simpleData: {
                    enable: true
                },
                key: {
                    title: "title"
                }
            },
            callback: {
                beforeEditName: beforeEditName,
                beforeRemove: beforeRemove,
                beforeDrag: beforeDrag
            }
        };

        //跟节点不显示删除按钮
        function showRemoveBtn(treeId, treeNode) {
            return treeNode.id == 0 ? false : true;
        }
        //跟节点不显示修改按钮
        function showRenameBtn(treeId, treeNode) {
            return  treeNode.id == 0 ? false : true;
        }
        //禁用拖拽功能
        function beforeDrag(treeId, treeNodes) {
            return false;
        }
        //点击节点触发 删除自定义按键
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
            $("#enableBtn_" + treeNode.tId).unbind().remove();
        }
        //点击树节点增加 新增和启停用按钮并绑定功能
        function addHoverDom(treeId, treeNode) {
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.id != 0) {
                var enableStr = "<span class='button enable' id='enableBtn_" + treeNode.tId
                        + "' title='启/停用' onfocus='this.blur();'></span>";
                sObj.after(enableStr);
                var enabelBtn = $("#enableBtn_" + treeNode.tId);
                if (enabelBtn) enabelBtn.bind("click", function () {
                    var zTree = $.fn.zTree.getZTreeObj("menuTree");
                    zTree.selectNode(treeNode);
                    bootbox.confirm('确定操作？将会对下级菜单进行同样的操作！', function (rs) {
                        if (rs) {
                            $.ajax({url: '${ctx}/platform/permission/menu/enable?menuID=' + treeNode.id + '&status=' + treeNode.status,
                                success: function (result) {
                                    if (result.success) {
                                        ztreeInit();
                                        message_box.show('操作成功', 'success');
                                    } else {
                                        message_box.show("服务器错误：" + result.message, 'error');
                                    }
                                },
                                error: commonErrorHandler
                            });
                        }
                    });
                });
            }
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='添加下级菜单' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var addBtn = $("#addBtn_" + treeNode.tId);
            if (addBtn) addBtn.bind("click", function () {
                var zTree = $.fn.zTree.getZTreeObj("menuTree");
                zTree.selectNode(treeNode);
                $('#menuWindow').modal('toggle');
                $('#parentID').attr("value", treeNode.id);
                $('#handleFlag').attr("value", "add");
                $('#menuName').attr("value", '');
                $('#url').attr("value", '');
            });
            return false;
        }

        //点击修改按钮弹出修改窗体
        function beforeEditName(treeId, treeNode) {
            className = (className === "dark" ? "" : "dark");
            var zTree = $.fn.zTree.getZTreeObj("menuTree");
            zTree.selectNode(treeNode);
            $('#menuWindow').modal('toggle');
            $('#menuID').attr("value", treeNode.id);
            $('#handleFlag').attr("value", "update");
            $('#menuName').attr("value", treeNode.menuName);
            $('#url').attr("value", treeNode.url);
            return false;
        }

        //删除时触发
        function beforeRemove(treeId, treeNode) {
            className = (className === "dark" ? "" : "dark");
            var zTree = $.fn.zTree.getZTreeObj("menuTree");
            zTree.selectNode(treeNode);
            bootbox.confirm('确定删除？将会删除所有下级菜单！', function (rs) {
                if (rs) {
                    $.ajax({url: '${ctx}/platform/permission/menu/delete?menuID=' + treeNode.id,
                        success: function (result) {
                            if (result.success) {
                                ztreeInit();
                                message_box.show('删除成功', 'success');
                            } else {
                                message_box.show("服务器错误：" + result.message, 'error');
                            }
                        },
                        error: commonErrorHandler
                    });
                }
            });
            return false;
        }

        //初始化菜单树形
        function ztreeInit() {
            var treeNodes = [
                { id: 0, pId: -1, name: '<span style="font-weight: bold">' + 'kitten-web菜单树' + '</span>', open: true, title: 'kitten-web菜单树'}
            ];
            $.ajax({url: '${ctx}/platform/permission/menu/list',
                success: function (result) {
                    var dataList = result.data;
                    dataList.forEach(function (e) {
                        e.name = '<span title="' + e.menuName + '" data-rel="tooltip"><span style="font-weight: bold">' + e.menuName + '</span>' +
                                '&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 11px;font-weight: bold;" class="label ' +
                                (e.status == 'ACTIVE' ? 'label-success"' : 'label-important"') + '>' + (e.status == 'ACTIVE' ? '可用' : '不可用') + '</span><span>' +
                                '&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #0088CC">' + e.url + '</span>';
                        e.id = e.menuID;
                        e.pId = e.parentID;
                        e.open = true;
                    });
                    treeNodes = treeNodes.concat(dataList);
                    $.fn.zTree.init($("#menuTree"), setting, treeNodes);
                },
                error: function (xhr) {
                    message_box.show("操作失败!请重试!" + xhr.status + " " + xhr.statusText, 'error');
                }
            });
        }
        //表单初始化
        function formInit() {
            var validateSet = {
                menuName: {must: true, ftip: '<span style="color: green">必填字段</span>'},
                url: {must: false, ftip: '<span style="color: orange"></span>'}
            };
            form.friend.init(validateSet);
            //表单提交
            $('#btn_submit').click(function () {
                if (form.validate(validateSet)) {
                    $('#menuForm').ajaxSubmit({
                        success: function (result) {
                            if (result.success) {
                                $('#menuWindow').modal('toggle');
                                ztreeInit();
                                message_box.show('操作成功', 'success');
                            } else {
                                message_box.show("服务器错误：" + result.message, 'error');
                            }
                        },
                        error: commonErrorHandler
                    });
                }
            });
        }
        //页面初始化
        $(document).ready(function () {
            ztreeInit();
            formInit();
        });
    </script>

</head>

<!--主面板标题-->
<div id="mainPanel" class="box">
    <div class="box-header well" data-original-title>
        <h2><i class="icon-align-justify"></i>系统菜单管理</h2>

        <div class="box-icon">
            <a href="javascript:void(0);" class="btn btn-resize btn-round"><i class="icon-resize-full"></i></a>
            <a href="javascript:void(0);" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
        </div>
    </div>
    <div class="box-content">
        <div id="menuTree" class="ztree"></div>
    </div>
</div>
<div id="menuWindow" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">功能管理</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="menuForm" method="post" action="${ctx}/platform/permission/menu/manage">
            <input type="hidden" name="menuID" id="menuID"/>
            <input type="hidden" name="parentID" id="parentID"/>
            <input type="hidden" name="handleFlag" id="handleFlag"/>

            <div class="control-group">
                <label class="control-label" for="menuName">功能名称：</label>

                <div class="controls">
                    <input type="text" name="menuName" id="menuName" placeholder="请输入功能名称"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="url">URL：</label>

                <div class="controls">
                    <input type="text" name="url" id="url" placeholder="请输入请求URL"/>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_submit">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>