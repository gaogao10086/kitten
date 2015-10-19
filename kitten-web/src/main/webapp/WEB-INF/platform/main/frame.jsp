<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<%@ page import="com.gys.kitten.core.permission.entity.*" %>
<%
    AdminEntity admin = (AdminEntity) request.getSession().getAttribute("user");
    String user = admin != null ? admin.getLegacyId() : null;
%>
<!DOCTYPE HTML>
<head>
    <c:import url="/WEB-INF/platform/main/-include.jsp"/>
    <link href="${ctx }/resources/charisma/css/zTreeStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${ctx}/resources/charisma/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/commonjs/init.js"></script>
</head>
<body>
<%if (admin != null) {%>
<c:import url="/WEB-INF/platform/main/-header.jsp">
    <c:param name="user" value="<%=user%>"/>
</c:import>
<div class="container-fluid" id="platformBody">
    <div class="row-fluid">
        <c:import url="/WEB-INF/platform/main/-menu.jsp"/>
        <c:import url="/WEB-INF/platform/main/-main.jsp"/>
    </div>
</div>
<%} else {%>
<c:import url="/WEB-INF/platform/main/-header.jsp"/>
<script type="text/javascript">
    function login() {
        if ($('#user').val() == '') {
            message_box.show("请填写用户名!", 'error');
            return false;
        }
        if ($('#pwd').val() == '') {
            message_box.show("请填写密码!", 'error');
            return false;
        }
        $.ajax({
            url: "login",
            type: 'POST',
            data: {
                user: $('#user').val(),
                pwd: $('#pwd').val()
            },
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    location.href = "../platform/";
                } else {
                    message_box.show(data.message, 'error');
                }
            },
            error: commonErrorHandler
        });
        return false;
    }
</script>
<div class="row-fluid" style="margin-top: 50px;">
    <!--
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>欢迎访问Kitten-Web！呆萌！</h2>
        </div>
    </div>
    -->
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <div class="alert alert-info">
                请输入您的账户和密码,呆萌！
            </div>
            <form class="form-horizontal" action="login" method="post" onsubmit="return login();">
                <fieldset>
                    <div class="input-prepend" data-rel="tooltip" title="输入您的账号">
                        <span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10"
                                                                                    name="user" id="user"
                                                                                    placeholder="通常是您的名字全拼"
                                                                                    type="text" value=""/>
                    </div>
                    <div class="clearfix"></div>

                    <div class="clearfix"></div>

                    <div class="input-prepend" title="密码" data-rel="tooltip">
                        <span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10"
                                                                                    name="pwd" id="pwd"
                                                                                    type="password"
                                                                                    value=""/>

                    </div>
                    <p class="center span5">
                        <button type="submit" class="btn btn-primary">登陆</button>
                    </p>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</div>
<%}%>
<!--
<c:import url="/WEB-INF/platform/main/-footer.jsp"/>
-->
</body>
</html>