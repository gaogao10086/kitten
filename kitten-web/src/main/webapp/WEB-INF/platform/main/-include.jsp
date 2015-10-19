<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<script type="text/javascript">
    var G = G || {};
    G.ctx = '${ctx }';
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/resources/charisma/img/favicon.ico">

<!-- charisma styles -->
<!-- 蓝哇哇<link href="${ctx }/resources/charisma/css/bootstrap-cerulean.css" type="text/css" rel="stylesheet">-->
<link id="bs-css" href="${ctx }/resources/charisma/css/bootstrap-classic.css" type="text/css" rel="stylesheet">
<!-- 典雅黑 <link href="${ctx }/resources/charisma/css/bootstrap-cyborg.css" type="text/css" rel="stylesheet">-->
<!-- 红艳艳<link href="${ctx }/resources/charisma/css/bootstrap-redy.css" type="text/css" rel="stylesheet">-->
<!-- 红扑扑 <link href="${ctx }/resources/charisma/css/bootstrap-united.css" type="text/css" rel="stylesheet">-->
<!-- 灰蒙蒙 <link href="${ctx }/resources/charisma/css/bootstrap-spacelab.css" type="text/css" rel="stylesheet">-->
<!-- 黑乎乎 <link href="${ctx }/resources/charisma/css/bootstrap-slate.css" type="text/css" rel="stylesheet">-->
<!-- 惨白白<link href="${ctx }/resources/charisma/css/bootstrap-simplex.css" type="text/css" rel="stylesheet">-->

<!-- charisma css-->
<link href="${ctx }/resources/charisma/css/bootstrap-responsive.css" type="text/css" rel="stylesheet">
<link href="${ctx }/resources/charisma/css/charisma-app.css" type="text/css" rel="stylesheet">
<link href="${ctx }/resources/charisma/css/jquery-ui-1.8.21.custom.css" type="text/css" rel="stylesheet">
<link href='${ctx }/resources/charisma/css/fullcalendar.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/fullcalendar.print.css' type="text/css" rel='stylesheet' media='print'>
<link href='${ctx }/resources/charisma/css/chosen.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/uniform.default.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/colorbox.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/jquery.cleditor.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/jquery.noty.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/noty_theme_default.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/elfinder.min.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/elfinder.theme.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/jquery.iphone.toggle.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/opa-icons.css' type="text/css" rel='stylesheet'>
<link href='${ctx }/resources/charisma/css/uploadify.css' type="text/css" rel='stylesheet'>
<link href="${ctx }/resources/charisma/css/jquery.loadmask.css" rel="stylesheet" type="text/css"/>
<link href="${ctx }/resources/charisma/css/kitten.css" rel="stylesheet" type="text/css">
<!--[if IE 6]>
<link href="${ctx }/resources/charisma/css/bootstrap.ie6.css" rel="stylesheet" type="text/css"/>
<![endif]-->


<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<!-- jQuery and core-->
<script type="text/javascript" src="${ctx }/resources/charisma/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/charisma/js/jshow.utils.js"></script>
<script type="text/javascript" src="${ctx }/resources/charisma/js/jquery.loadmask.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/charisma/js/bootbox.js"></script>
<script type="text/javascript" src="${ctx }/resources/charisma/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/resources/charisma/js/form.validate.js"></script>
<!-- jQuery UI -->
<script src="${ctx }/resources/charisma/js/jquery-ui-1.8.21.custom.min.js"></script>
<!-- transition / effect library -->
<script src="${ctx }/resources/charisma/js/bootstrap-transition.js"></script>
<!-- alert enhancer library -->
<script src="${ctx }/resources/charisma/js/bootstrap-alert.js"></script>
<!-- modal / dialog library -->
<script src="${ctx }/resources/charisma/js/bootstrap-modal.js"></script>
<!-- custom dropdown library -->
<script src="${ctx }/resources/charisma/js/bootstrap-dropdown.js"></script>
<!-- scrolspy library -->
<script src="${ctx }/resources/charisma/js/bootstrap-scrollspy.js"></script>
<!-- library for creating tabs -->
<script src="${ctx }/resources/charisma/js/bootstrap-tab.js"></script>
<!-- library for advanced tooltip -->
<script src="${ctx }/resources/charisma/js/bootstrap-tooltip.js"></script>
<!-- popover effect library -->
<script src="${ctx }/resources/charisma/js/bootstrap-popover.js"></script>
<!-- button enhancer library -->
<script src="${ctx }/resources/charisma/js/bootstrap-button.js"></script>
<!-- accordion library (optional, not used in demo) -->
<script src="${ctx }/resources/charisma/js/bootstrap-collapse.js"></script>
<!-- carousel slideshow library (optional, not used in demo) -->
<script src="${ctx }/resources/charisma/js/bootstrap-carousel.js"></script>
<!-- autocomplete library -->
<script src="${ctx }/resources/charisma/js/bootstrap-typeahead.js"></script>
<!-- tour library -->
<script src="${ctx }/resources/charisma/js/bootstrap-tour.js"></script>
<!-- library for cookie management -->
<script src="${ctx }/resources/charisma/js/jquery.cookie.js"></script>
<!-- calander plugin -->
<script src='${ctx }/resources/charisma/js/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script src='${ctx }/resources/charisma/js/jquery.dataTables.js'></script>
<!-- chart libraries -->
<script src="${ctx }/resources/charisma/js/excanvas.js"></script>
<script src="${ctx }/resources/charisma/js/jquery.flot.min.js"></script>
<script src="${ctx }/resources/charisma/js/jquery.flot.pie.min.js"></script>
<script src="${ctx }/resources/charisma/js/jquery.flot.stack.js"></script>
<script src="${ctx }/resources/charisma/js/jquery.flot.resize.min.js"></script>
<!-- select or dropdown enhancer -->
<script src="${ctx }/resources/charisma/js/jquery.chosen.min.js"></script>
<!-- checkbox, radio, and file input styler -->
<script src="${ctx }/resources/charisma/js/jquery.uniform.min.js"></script>
<!-- plugin for gallery image view -->
<script src="${ctx }/resources/charisma/js/jquery.colorbox.min.js"></script>
<!-- rich text editor library -->
<script src="${ctx }/resources/charisma/js/jquery.cleditor.min.js"></script>
<!-- notification plugin -->
<script src="${ctx }/resources/charisma/js/jquery.noty.js"></script>
<!-- file manager library -->
<script src="${ctx }/resources/charisma/js/jquery.elfinder.min.js"></script>
<!-- star rating plugin -->
<script src="${ctx }/resources/charisma/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="${ctx }/resources/charisma/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="${ctx }/resources/charisma/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="${ctx }/resources/charisma/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="${ctx }/resources/charisma/js/jquery.history.js"></script>
<!-- platform common -->
<script src="${ctx }/resources/commonjs/common.js"></script>
<script src="${ctx }/resources/commonjs/StringBuffer.js"></script>
<script src="${ctx }/resources/commonjs/ArrayBuffer.js"></script>
<title>kitten</title>
