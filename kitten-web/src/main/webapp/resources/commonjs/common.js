G.root = G.ctx + '/platform/';

/* 文本框只能输入数字 */
function isNumber(e) {
    if ($.browser.msie) {
        if (((event.keyCode > 47) && (event.keyCode < 58)) ||
            (event.keyCode == 8)) {
            return true;
        } else {
            return false;
        }
    } else {
        if (((e.which > 47) && (e.which < 58)) ||
            (e.which == 8)) {
            return true;
        } else {
            return false;
        }
    }
}

function isMoney(v) {
    var a = /^[0-9]*(\.[0-9]{1,2})?$/;
    if (!a.test(v)) {
        return false;
    } else {
        return true;
    }
}

function checkInputIntFloat(oInput) {
    if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/, '')) {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' : oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}

function render_chart(char_id, type, categories, step, opts, series, width, height) {
    $("#" + char_id).css("width", width);
    $("#" + char_id).css("height", height);

    var options = $.extend(true, {
        chart: {
            defaultSeriesType: type,
            renderTo: char_id
        },
        title: {
            text: null
        },
        xAxis: {
            categories: categories,
            labels: {
                step: step,
                formatter: function () {
                    var reg = /^(\d{4})\-(\d{2})\-(\d{2})$/;
                    if (reg.test(this.value)) {
                        return this.value.substring(5);
                    }
                    return this.value;
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: null
            },
            labels: {
                formatter: function () {
                    if (parseInt(this.value) > 0 && parseInt(this.value) % 1000 == 0) {
                        return parseInt(this.value) / 1000 + 'k';
                    }
                    return this.value;
                }
            }
        },
        exporting: {
            enabled: false
        }
    }, opts || {});

    new Highcharts.Chart($.extend(true, options, series));
}

function genSeries(axisX, map) {
    var data = [];
    for (var i = 0; i < axisX.length; i++) {
        var value = map.get(axisX[i]);
        if (value == null)
            data[i] = null;
        else
            data[i] = value;
    }
    return data;
}

function stopBubble(e) {
    var e = e ? e : window.event;
    if (window.event) { // IE
        e.cancelBubble = true;
    } else { // FF
        //e.preventDefault();
        e.stopPropagation();
    }
}

function getMousePoint(ev) {
    // 定义鼠标在视窗中的位置
    var point = {
        x: 0,
        y: 0
    };

    // 如果浏览器支持 pageYOffset, 通过 pageXOffset 和 pageYOffset 获取页面和视窗之间的距离
    if (typeof window.pageYOffset != 'undefined') {
        point.x = window.pageXOffset;
        point.y = window.pageYOffset;
    }
    // 如果浏览器支持 compatMode, 并且指定了 DOCTYPE, 通过 documentElement 获取滚动距离作为页面和视窗间的距离
    // IE 中, 当页面指定 DOCTYPE, compatMode 的值是 CSS1Compat, 否则 compatMode 的值是 BackCompat
    else if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
        point.x = document.documentElement.scrollLeft;
        point.y = document.documentElement.scrollTop;
    }
    // 如果浏览器支持 document.body, 可以通过 document.body 来获取滚动高度
    else if (typeof document.body != 'undefined') {
        point.x = document.body.scrollLeft;
        point.y = document.body.scrollTop;
    }

    // 加上鼠标在视窗中的位置
    point.x += ev.clientX;
    point.y += ev.clientY;

    // 返回鼠标在视窗中的位置
    return point;
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {  //先查询cookie是否为空，为空就return ""
        var arrstr = document.cookie.split("; ");
        for (var i = 0; i < arrstr.length; i++) {
            var temp = arrstr[i].split("=");
            if (temp[0] == c_name)
                return temp[1];
        }

        /*var c_start=document.cookie.indexOf(c_name + "=");//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
         if (c_start!=-1){
         c_start=c_start + c_name.length+1;  //最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
         var c_end=document.cookie.indexOf(";",c_start);     //其实我刚看见indexOf()第二个参数的时候猛然有点晕，后来想起来表示指定的开始索引的位置...这句是为了得到值的结束位置。因为需要考虑是否是最后一项，所以通过";"号是否存在来判断
         if (c_end==-1)
         c_end = document.cookie.length;
         return unescape(document.cookie.substring(c_start,c_end));  //通过substring()得到了值
         }*/
    }
    return "";
}

function commonErrorHandler(xhr) {
    message_box.show("系统错误！状态编码：" + xhr.status + "，信息：" + xhr.statusText, 'error');
}
function moreOperation(op) {
    var wrapper = $('<div></div>').append(op);
    var i = 0;
    var group = $('<div class="btn-group" style="position: absolute;margin-left:5px;"><a class="btn btn-primary" href="javascript:void(0);"></i>更多</a>' +
        '<a class="btn btn-primary dropdown-toggle" style="height: 18px;*height:20px;" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span> </a>' +
        '<ul class="dropdown-menu" style="min-width:0px;"></ul></div>');
    var buttons = wrapper.children();
    var count = 2;
    buttons.each(function () {
        if (buttons.size() > count && i > 0) {
            $(this).find("i").removeClass("icon-white");
            var li = $("<li></li>").append($(this).removeClass());
            group.find(".dropdown-menu").append(li);
        }
        i++;
    });
    if (buttons.size() > count)
        wrapper.append(group);
    return wrapper.html();
}

var oLanguage = {
    "sLengthMenu": "每页显示 _MENU_ 条记录",
    "sZeroRecords": "对不起，查询不到任何相关数据",
    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
    "sInfoEmpty": "显示第 0 至 0 条结果，共 0 条记录",
    "sInfoFiltered": "",
    "sProcessing": "正在加载中...",
    "sSearch": "搜索 ：",
    "oPaginate": {
        "sFirst": "第一页",
        "sPrevious": "上一页 ",
        "sNext": "下一页 ",
        "sLast": "最后一页 "
    }
}

function createTable(config) {
    var tabConfig = {
        "bFilter": false,
        "bServerSide": true,
        "oLanguage": oLanguage,
        "sAjaxDataProp": 'data',
        "sAjaxSource": config.url,
        "aoColumns": config.columns,
        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
        "sPaginationType": "bootstrap_alt",
        "fnServerParams": config.paramsPackage,
        "fnRowCallback": config.rowCallback,
        "fnDrawCallback": config.tableCallback,
        "oTableTools": {
            "sSwfPath": "${ctx }/resources/charisma/swf/copy_csv_xls_pdf.swf",
            "aButtons": [
                {
                    "sExtends": "xls",
                    "sButtonText": "导出excel格式",
                    "mColumns": [1, 2, 3, 4, 5, 6, 7]
                },
                {
                    "sExtends": "csv",
                    "sButtonText": "导出csv格式",
                    "mColumns": [1, 2, 3, 4, 5, 6, 7]
                }
            ]
        },
        "fnServerData": function (sSource, aoData, fnCallback) {
            $.ajax({
                dataType: 'json',
                type: 'post',
                url: sSource,
                data: aoData,
                success: fnCallback
            });
        }
    }
    if (config.scroll) {
        tabConfig.sScrollY = "260px";
    }
    if (config.bigScroll) {
        tabConfig.sScrollY = "570px";
    }
    var oTable = $('#' + config.domId).dataTable(tabConfig);
    return oTable;
}

function showMenu(id, path, ob) {
    if ($("#" + id).css("display") == "block") {
        $("#" + id).css("display", "none");
    } else {
        $("#" + id).css("display", "block");
        frame$menu(path, ob);
    }
}

function frame$menu(model, el) {
    $('.nav-list li').removeClass('active');
    $(el).parent().addClass('active');
    if (model === 'javascript:;') {
        return;
    }
    $('#main').loading().load(G.root + model);
}

var menuTimeout;
$.fn.extend({
    limit: function () {
        var self = $("[limit]");
        self.each(function () {
            var objString = $(this).text();
            var objLength = $(this).text().length;
            var num = $(this).attr("limit");
            if (objLength > num) {
                $(this).attr("title", objString);
                $(this).text(objString.substring(0, num) + "...");
            }
        })
    }
});

$.fn.extend({
    loading: function () {
        this.html('<div class="progress progress-striped active"><div class="bar" style="width: 0%;">请稍候...</div></div>').find('.bar').css('width', '100%');
        return this;
    }
});
//页面初始化
function pageInit() {
    $('a[href="#"][data-top!=true]').click(function (e) {
        e.preventDefault();
    });
    $('.cleditor').cleditor();
    $('.datepicker').datepicker();
    $('.noty').click(function (e) {
        e.preventDefault();
        var options = $.parseJSON($(this).attr('data-noty-options'));
        noty(options);
    });
    $("input:checkbox, input:radio, input:file").not('[data-no-uniform="true"],#uniform-is-ajax').uniform();
    $('[data-rel="chosen"],[rel="chosen"]').chosen();
    $('#myTab a:first').tab('show');
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    $('.sortable').sortable({
        revert: true,
        cancel: '.btn,.box-content,.nav-header',
        update: function (event, ui) {
        }
    });
    $('.slider').slider({range: true, values: [10, 65]});

    $('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement": "bottom", delay: { show: 400, hide: 200 }});

    $('textarea.autogrow').autogrow();

    $('[rel="popover"],[data-rel="popover"]').popover();

    $('.iphone-toggle').iphoneStyle();

    $('.raty').raty({
        score: 4
    });
    $('#file_upload').uploadify({
        'swf': 'misc/uploadify.swf',
        'uploader': 'misc/uploadify.php'
    });
    $('.btn-close').click(function (e) {
        e.preventDefault();
        $(this).parent().parent().parent().fadeOut();
    });
    $('.btn-minimize').click(function (e) {
        e.preventDefault();
        var $target = $(this).parent().parent().next('.box-content');
        if ($target.is(':visible')) $('i', $(this)).removeClass('icon-chevron-up').addClass('icon-chevron-down');
        else                       $('i', $(this)).removeClass('icon-chevron-down').addClass('icon-chevron-up');
        $target.slideToggle();
    });
    $('.btn-setting').click(function (e) {
        e.preventDefault();
        $('#myModal').modal('show');
    });
    $(".btn-resize").click(resizeFull);
    $(".icon-plus").click(maxContent);
}

//还原 内容部分
function resizeSmall() {
    var navbar = $(".navbar").eq(0);
    var sidebar = $("#adminMenuTree").parent();
    var content = $("#main");
    navbar.animate({"margin-top": "0"}, 200, function () {
        navbar.removeAttr("style");
    });
    sidebar.animate({"margin-left": "0"}, 200, function () {
        sidebar.removeAttr("style");
    });
    content.removeAttr("style");
    $(".btn-resize").find("i").removeClass("icon-resize-small").addClass("icon-resize-full");
    $(".btn-resize").unbind();
    $(".btn-resize").bind("click", resizeFull);
}

//最大化 内容部分
function resizeFull() {
    var navbar = $(".navbar").eq(0);
    var sidebar = $("#adminMenuTree").parent();
    var content = $("#main");
    navbar.animate({marginTop: "-" + navbar.height() + "px", marginBottom: 0}, 200);
    sidebar.animate({marginLeft: "-" + (sidebar.width() + 200) + "px"}, 200);
    content.animate({marginLeft: -10, width: window.innerWidth - 50}, 200);
    $(".btn-resize").find("i").removeClass("icon-resize-full").addClass("icon-resize-small");
    $(".btn-resize").unbind();
    $(".btn-resize").bind("click", resizeSmall);
}

/**
 * 将div中的内容放入一个模态的全屏窗口中,仅提供展示。不支持任何操作
 */
function maxContent(e) {
    e.preventDefault();
    var contentHtml = $(this).parent().parent().parent().parent().html();
    var html = '<div class="modal hide fade"><div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
        '<h3> 大视图 </h3>' +
        '</div ><div class="modal-body" style="max-height:' + (window.innerHeight) + 'px;"><div class = "box">' +
        contentHtml + ' </div></div></div>';
    var div = $(html);
    div.css({height: '96%', width: '99%', top: '28%', left: '15%'});
    div.modal();
}

function switch_theme(theme_name) {
    var nowTheme = $('#bs-css').attr('href');
    if (nowTheme.indexOf(theme_name) <= 0) {
        $('#bs-css').attr('href', G.ctx + '/resources/charisma/css/bootstrap-' + theme_name + '.css');
    }
}

