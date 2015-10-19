<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<%@ page import="com.gys.kitten.core.permission.entity.*" %>
<%
    AdminEntity admin = null;
    if (request != null && request.getSession() != null) {
        if (request.getSession().getAttribute("user") != null) {
            admin = (AdminEntity) request.getSession().getAttribute("user");
        }
    }
%>
<script type="text/javascript">
    function showPassWordWindow() {
        $("#passwordWindow").modal('toggle');
    }

    function initPassWordForm() {
        var validateSet = {
            password: {must: true, ftip: '<span style="color: green">必填字段</span>'},
            newPassWord: {must: true, ftip: '<span style="color: green">必填字段</span>'},
            newPassWordR: {must: true, ftip: '<span style="color: green">必填字段</span>'}
        };
        $('#btn_submit_password').click(function () {
            if (form.validate(validateSet)) {
                var newPassWord = $("#newPassWord").val();
                var newPassWordR = $("#newPassWordR").val();
                if (newPassWord != newPassWordR) {
                    message_box.show('新密码两次输入不一致,请再次确认新密码', 'error');
                    return;
                }
                $('#passwordForm').ajaxSubmit(
                        {
                            success: function (result) {
                                if (result.success) {
                                    message_box.show('操作成功', 'success');
                                    $('#passwordForm').resetForm();
                                    $('#passwordWindow').modal('toggle');
                                } else {
                                    message_box.show("服务器错误：" + result.message, 'error');
                                }
                            },
                            error: commonErrorHandler
                        });
            }
        });
        form.friend.init(validateSet);
    }
    $(document).ready(function () {
        initPassWordForm();
        //变更主题
        $('#themes a').click(function (e) {
            e.preventDefault();
            var current_theme = $(this).attr('data-value');
            $.cookie('current_theme', current_theme, {expires: 365});
            switch_theme(current_theme);
            $('#themes i').removeClass('icon-ok');
            $(this).find('i').addClass('icon-ok');
        });

        var current_theme = $.cookie('current_theme') == null ? 'classic' : $.cookie('current_theme');
        $('#themes a[data-value="' + current_theme + '"]').find('i').addClass('icon-ok');
        switch_theme(current_theme);
    });
</script>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
		    <span class="">
		    	<a class="brand" style="color: white;" href="${ctx }"
                   title="kitten-web!喵咪~~喵咪！！喵咪~~！！@！#￥@#%#@……￥&嗷嗷嗷~~~" data-rel="tooltip">
                    kitten-web!
                </a>
		    </span>
            <!--
	        <span class="pull-left">
		    	<a class="kitten-log-logo" href="javascript:;"></a>
		    </span>
            -->
            <!-- theme selector starts -->

            <!-- user dropdown starts -->
            <div class="btn-group pull-right">
                <%if (admin != null) {%>
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-user"></i><span class="hidden-phone"> 您好！<%=admin.getName()%> </span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="javascript:;" onclick="showPassWordWindow();">改密</a></li>
                    <li class="divider"></li>
                    <li><a href="${ctx }/platform/logout">登出</a></li>
                </ul>
                <%}%>
            </div>
            <div class="btn-group pull-right theme-container">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-tint"></i><span class="hidden-phone">主题</span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" id="themes" data-rel="tooltip" title="由于浏览器缓存的原因，更换主题可能需要清楚缓存并刷新">
                    <li><a data-value="classic" href="#"><i class="icon-blank"></i> 默认</a></li>
                    <li><a data-value="cerulean" href="#"><i class="icon-blank"></i> 蓝哇哇</a></li>
                    <li><a data-value="cyborg" href="#"><i class="icon-blank"></i> 黑乎乎</a></li>
                    <li><a data-value="slate" href="#"><i class="icon-blank"></i> 典雅黑</a></li>
                    <li><a data-value="redy" href="#"><i class="icon-blank"></i> 红艳艳</a></li>
                    <li><a data-value="united" href="#"><i class="icon-blank"></i> 红扑扑</a></li>
                    <li><a data-value="simplex" href="#"><i class="icon-blank"></i> 银白白</a></li>
                    <li><a data-value="spacelab" href="#"><i class="icon-blank"></i> 灰蒙蒙</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div id="passwordWindow" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>密码修改</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="passwordForm" method="post" action="password">
            <div class="control-group">
                <label class="control-label" for="password">现密码：</label>

                <div class="controls">
                    <input type="text" name="password" id="password" placeholder="请输入现密码"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="newPassWord">新密码：</label>

                <div class="controls">
                    <input type="text" name="newPassWord" id="newPassWord" placeholder="请输入新密码"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="newPassWordR">确认：</label>

                <div class="controls">
                    <input type="text" name="newPassWordR" id="newPassWordR" placeholder="请确认新密码"/>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <a href="javascript:;" class="btn btn-primary" id="btn_submit_password">提交</a>
        <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>
