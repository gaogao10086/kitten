<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>


<div class="span2">
    <div id="adminMenuTree" class="ztree"></div>
</div>
<script>
    function showMenu2(subid) {
        $("#" + subid).toggle();
    }
    var menusList = [];
    <c:forEach var="m" items="${menus}">
    var menu = {};
    menu.name = '${m.menuName}';
    menu.id = '${m.menuID}';
    menu.pId = '${m.parentID}';
    menu.menuURL = '${m.url}';
    if (menu.name == '首页') {
        menu.iconSkin = 'home';
    } else if (menu.pId == 0) {
        menu.iconSkin = 'level0';
    } else if (menu.pId != 0 && menu.menuURL == '') {
        menu.iconSkin = 'level1';
    } else if (menu.menuURL != '') {
        menu.iconSkin = 'url';
    }
    menusList.push(menu);
    </c:forEach>
    if (menusList.length == 0) {
        message_box.show('很抱歉，您没有访问权限，请联系系统管理员', 'error');
    }
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            beforeExpand: beforeExpand,
            onExpand: onExpand
        }
    };

    var zNodes = menusList;
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("adminMenuTree");
        zTree.expandNode(treeNode, null, null, null, true);
        if (treeNode.menuURL != '') {
            showMenu('', treeNode.menuURL, this);
        }
    }
    function onExpand(event, treeId, treeNode) {
        curExpandNode = treeNode;
    }

    var curExpandNode = null;
    function beforeExpand(treeId, treeNode) {
        var pNode = curExpandNode ? curExpandNode.getParentNode() : null;
        var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
        var zTree = $.fn.zTree.getZTreeObj("adminMenuTree");
        for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
            if (treeNode !== treeNodeP.children[i]) {
                zTree.expandNode(treeNodeP.children[i], false);
            }
        }
        while (pNode) {
            if (pNode === treeNode) {
                break;
            }
            pNode = pNode.getParentNode();
        }
        if (!pNode) {
            singlePath(treeNode);
        }

    }
    function singlePath(newNode) {
        if (newNode === curExpandNode) return;
        if (curExpandNode && curExpandNode.open == true) {
            var zTree = $.fn.zTree.getZTreeObj("adminMenuTree");
            if (newNode.parentTId === curExpandNode.parentTId) {
                zTree.expandNode(curExpandNode, false);
            } else {
                var newParents = [];
                while (newNode) {
                    newNode = newNode.getParentNode();
                    if (newNode === curExpandNode) {
                        newParents = null;
                        break;
                    } else if (newNode) {
                        newParents.push(newNode);
                    }
                }
                if (newParents != null) {
                    var oldNode = curExpandNode;
                    var oldParents = [];
                    while (oldNode) {
                        oldNode = oldNode.getParentNode();
                        if (oldNode) {
                            oldParents.push(oldNode);
                        }
                    }
                    if (newParents.length > 0) {
                        zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
                    } else {
                        zTree.expandNode(oldParents[oldParents.length - 1], false);
                    }
                }
            }
        }
        curExpandNode = newNode;
    }


    $(document).ready(function () {
        $.fn.zTree.init($("#adminMenuTree"), setting, zNodes);
    });

</script>
<style>
    .ztree li span.button.home_ico_docu {
        display: inline-block;
        width: 14px;
        height: 14px;
        *margin-right: .3em;
        line-height: 14px;
        vertical-align: text-top;
        background-image: url("${ctx}/resources/charisma/img/glyphicons-halflings.png");
        background-repeat: no-repeat;
        background-position: 0 -24px;
    }

    .ztree li span.button.level0_ico_open, .ztree li span.button.level0_ico_close {
        display: inline-block;
        width: 14px;
        height: 14px;
        *margin-right: .3em;
        line-height: 14px;
        vertical-align: text-top;
        background-image: url("${ctx}/resources/charisma/img/glyphicons-halflings.png");
        background-repeat: no-repeat;
        background-position: -264px 0;
    }

    .ztree li span.button.level1_ico_open, .ztree li span.button.level1_ico_close {
        display: inline-block;
        width: 14px;
        height: 14px;
        *margin-right: .3em;
        line-height: 14px;
        vertical-align: text-top;
        background-image: url("${ctx}/resources/charisma/img/glyphicons-halflings.png");
        background-repeat: no-repeat;
        background-position: -216px 0;
    }

    .ztree li span.button.url_ico_docu {
        display: inline-block;
        width: 14px;
        height: 14px;
        *margin-right: .3em;
        line-height: 14px;
        vertical-align: text-top;
        background-image: url("${ctx}/resources/charisma/img/glyphicons-halflings.png");
        background-repeat: no-repeat;
        background-position: -240px 0;
    }
</style>