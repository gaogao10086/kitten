<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/platform/main/tag-lib.tag" %>
<script>
$(document).ready(function(){
    //themes, change CSS with JS
    //default theme(CSS) is cerulean, change it if needed
//    var current_theme = $.cookie('current_theme')==null ? 'cerulean' :$.cookie('current_theme');
//    switch_theme(current_theme);
//
//    $('#themes a[data-value="'+current_theme+'"]').find('i').addClass('icon-ok');
//
//    $('#themes a').click(function(e){
//        e.preventDefault();
//        current_theme=$(this).attr('data-value');
//        $.cookie('current_theme',current_theme,{expires:365});
//        switch_theme(current_theme);
//        $('#themes i').removeClass('icon-ok');
//        $(this).find('i').addClass('icon-ok');
//    });


    function switch_theme(theme_name)
    {
        $('#bs-css').attr('href','css/bootstrap-'+theme_name+'.css');
    }

    //ajax menu checkbox
    $('#is-ajax').click(function(e){
        $.cookie('is-ajax',$(this).prop('checked'),{expires:365});
    });
    $('#is-ajax').prop('checked',$.cookie('is-ajax')==='true' ? true : false);

    //disbaling some functions for Internet Explorer
    if($.browser.msie)
    {
        $('#is-ajax').prop('checked',false);
        $('#for-is-ajax').hide();
        $('#toggle-fullscreen').hide();
        $('.login-box').find('.input-large').removeClass('span10');

    }


    //highlight current / active link
    $('ul.main-menu li a').each(function(){
        if($($(this))[0].href==String(window.location))
            $(this).parent().addClass('active');
    });

    //establish history variables
    var
            History = window.History, // Note: We are using a capital H instead of a lower h
            State = History.getState(),
            $log = $('#log');

    //bind to State Change
    History.Adapter.bind(window,'statechange',function(){ // Note: We are using statechange instead of popstate
        var State = History.getState(); // Note: We are using History.getState() instead of event.state
        $.ajax({
            url:State.url,
            success:function(msg){
                $('#content').html($(msg).find('#content').html());
                $('#loading').remove();
                $('#content').fadeIn();
                var newTitle = $(msg).filter('title').text();
                $('title').text(newTitle);
                docReady();
            }
        });
    });

    //ajaxify menus
    $('a.ajax-link').click(function(e){
        if($.browser.msie) e.which=1;
        if(e.which!=1 || !$('#is-ajax').prop('checked') || $(this).parent().hasClass('active')) return;
        e.preventDefault();
        if($('.btn-navbar').is(':visible'))
        {
            $('.btn-navbar').click();
        }
        $('#loading').remove();
        $('#content').fadeOut().parent().append('<div id="loading" class="center">Loading...<div class="center"></div></div>');
        var $clink=$(this);
        History.pushState(null, null, $clink.attr('href'));
        $('ul.main-menu li.active').removeClass('active');
        $clink.parent('li').addClass('active');
    });

    //animating menus on hover
    $('ul.main-menu li:not(.nav-header)').hover(function(){
                $(this).animate({'margin-left':'+=5'},300);
            },
            function(){
                $(this).animate({'margin-left':'-=5'},300);
            });

    //other things to do on document ready, seperated for ajax calls
    docReady();
});


function docReady(){
    //prevent # links from moving to top
    $('a[href="#"][data-top!=true]').click(function(e){
        e.preventDefault();
    });

    //rich text editor
    $('.cleditor').cleditor();

    //datepicker
    $('.datepicker').datepicker();

    //notifications
    $('.noty').click(function(e){
        e.preventDefault();
        var options = $.parseJSON($(this).attr('data-noty-options'));
        noty(options);
    });


    //uniform - styler for checkbox, radio and file input
    $("input:checkbox, input:radio, input:file").not('[data-no-uniform="true"],#uniform-is-ajax').uniform();

    //chosen - improves select
    $('[data-rel="chosen"],[rel="chosen"]').chosen();

    //tabs
    $('#myTab a:first').tab('show');
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    //makes elements soratble, elements that sort need to have id attribute to save the result
    $('.sortable').sortable({
        revert:true,
        cancel:'.btn,.box-content,.nav-header',
        update:function(event,ui){
            //line below gives the ids of elements, you can make ajax call here to save it to the database
            //console.log($(this).sortable('toArray'));
        }
    });

    //slider
    $('.slider').slider({range:true,values:[10,65]});

    //tooltip
    $('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});

    //auto grow textarea
    $('textarea.autogrow').autogrow();

    //popover
    $('[rel="popover"],[data-rel="popover"]').popover();

    //file manager
    var elf = $('.file-manager').elfinder({
        url : 'misc/elfinder-connector/connector.php'  // connector URL (REQUIRED)
    }).elfinder('instance');

    //iOS / iPhone style toggle switch
    $('.iphone-toggle').iphoneStyle();

    //star rating
    $('.raty').raty({
        score : 4 //default stars
    });

    //uploadify - multiple uploads
    $('#file_upload').uploadify({
        'swf'      : 'misc/uploadify.swf',
        'uploader' : 'misc/uploadify.php'
        // Put your options here
    });

    //gallery controlls container animation
    $('ul.gallery li').hover(function(){
        $('img',this).fadeToggle(1000);
        $(this).find('.gallery-controls').remove();
        $(this).append('<div class="well gallery-controls">'+
                '<p><a href="#" class="gallery-edit btn"><i class="icon-edit"></i></a> <a href="#" class="gallery-delete btn"><i class="icon-remove"></i></a></p>'+
                '</div>');
        $(this).find('.gallery-controls').stop().animate({'margin-top':'-1'},400,'easeInQuint');
    },function(){
        $('img',this).fadeToggle(1000);
        $(this).find('.gallery-controls').stop().animate({'margin-top':'-30'},200,'easeInQuint',function(){
            $(this).remove();
        });
    });


    //gallery image controls example
    //gallery delete
    $('.thumbnails').on('click','.gallery-delete',function(e){
        e.preventDefault();
        //get image id
        //alert($(this).parents('.thumbnail').attr('id'));
        $(this).parents('.thumbnail').fadeOut();
    });
    //gallery edit
    $('.thumbnails').on('click','.gallery-edit',function(e){
        e.preventDefault();
        //get image id
        //alert($(this).parents('.thumbnail').attr('id'));
    });

    //gallery colorbox
    $('.thumbnail a').colorbox({rel:'thumbnail a', transition:"elastic", maxWidth:"95%", maxHeight:"95%"});

    //gallery fullscreen
    $('#toggle-fullscreen').button().click(function () {
        var button = $(this), root = document.documentElement;
        if (!button.hasClass('active')) {
            $('#thumbnails').addClass('modal-fullscreen');
            if (root.webkitRequestFullScreen) {
                root.webkitRequestFullScreen(
                        window.Element.ALLOW_KEYBOARD_INPUT
                );
            } else if (root.mozRequestFullScreen) {
                root.mozRequestFullScreen();
            }
        } else {
            $('#thumbnails').removeClass('modal-fullscreen');
            (document.webkitCancelFullScreen ||
                    document.mozCancelFullScreen ||
                    $.noop).apply(document);
        }
    });

    //tour
    if($('.tour').length && typeof(tour)=='undefined')
    {
        var tour = new Tour();
        tour.addStep({
            element: ".span10:first", /* html element next to which the step popover should be shown */
            placement: "top",
            title: "Custom Tour", /* title of the popover */
            content: "You can create tour like this. Click Next." /* content of the popover */
        });
        tour.addStep({
            element: ".theme-container",
            placement: "left",
            title: "Themes",
            content: "You change your theme from here."
        });
        tour.addStep({
            element: "ul.main-menu a:first",
            title: "Dashboard",
            content: "This is your dashboard from here you will find highlights."
        });
        tour.addStep({
            element: "#for-is-ajax",
            title: "Ajax",
            content: "You can change if pages load with Ajax or not."
        });
        tour.addStep({
            element: ".top-nav a:first",
            placement: "bottom",
            title: "Visit Site",
            content: "Visit your front end from here."
        });

        tour.restart();
    }

    //datatable
    $('.datatable').dataTable({
        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
        "sPaginationType": "bootstrap",
        "oLanguage": {
            "sLengthMenu": "_MENU_ records per page"
        }
    } );
    $('.btn-close').click(function(e){
        e.preventDefault();
        $(this).parent().parent().parent().fadeOut();
    });
    $('.btn-minimize').click(function(e){
        e.preventDefault();
        var $target = $(this).parent().parent().next('.box-content');
        if($target.is(':visible')) $('i',$(this)).removeClass('icon-chevron-up').addClass('icon-chevron-down');
        else 					   $('i',$(this)).removeClass('icon-chevron-down').addClass('icon-chevron-up');
        $target.slideToggle();
    });
    $('.btn-setting').click(function(e){
        e.preventDefault();
        $('#myModal').modal('show');
    });




    //initialize the external events for calender

    $('#external-events div.external-event').each(function() {

        // it doesn't need to have a start or end
        var eventObject = {
            title: $.trim($(this).text()) // use the element's text as the event title
        };

        // store the Event Object in the DOM element so we can get to it later
        $(this).data('eventObject', eventObject);

        // make the event draggable using jQuery UI
        $(this).draggable({
            zIndex: 999,
            revert: true,      // will cause the event to go back to its
            revertDuration: 0  //  original position after the drag
        });

    });


    //initialize the calendar
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        editable: true,
        droppable: true, // this allows things to be dropped onto the calendar !!!
        drop: function(date, allDay) { // this function is called when something is dropped

            // retrieve the dropped element's stored Event Object
            var originalEventObject = $(this).data('eventObject');

            // we need to copy it, so that multiple events don't have a reference to the same object
            var copiedEventObject = $.extend({}, originalEventObject);

            // assign it the date that was reported
            copiedEventObject.start = date;
            copiedEventObject.allDay = allDay;

            // render the event on the calendar
            // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
            $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

            // is the "remove after drop" checkbox checked?
            if ($('#drop-remove').is(':checked')) {
                // if so, remove the element from the "Draggable Events" list
                $(this).remove();
            }

        }
    });


    //chart with points
    if($("#sincos").length)
    {
        var sin = [], cos = [];

        for (var i = 0; i < 14; i += 0.5) {
            sin.push([i, Math.sin(i)/i]);
            cos.push([i, Math.cos(i)]);
        }

        var plot = $.plot($("#sincos"),
                [ { data: sin, label: "sin(x)/x"}, { data: cos, label: "cos(x)" } ], {
                    series: {
                        lines: { show: true  },
                        points: { show: true }
                    },
                    grid: { hoverable: true, clickable: true, backgroundColor: { colors: ["#fff", "#eee"] } },
                    yaxis: { min: -1.2, max: 1.2 },
                    colors: ["#539F2E", "#3C67A5"]
                });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css( {
                position: 'absolute',
                display: 'none',
                top: y + 5,
                left: x + 5,
                border: '1px solid #fdd',
                padding: '2px',
                'background-color': '#dfeffc',
                opacity: 0.80
            }).appendTo("body").fadeIn(200);
        }

        var previousPoint = null;
        $("#sincos").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));

            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);

                    showTooltip(item.pageX, item.pageY,
                            item.series.label + " of " + x + " = " + y);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });



        $("#sincos").bind("plotclick", function (event, pos, item) {
            if (item) {
                $("#clickdata").text("You clicked point " + item.dataIndex + " in " + item.series.label + ".");
                plot.highlight(item.series, item.datapoint);
            }
        });
    }

    //flot chart
    if($("#flotchart").length)
    {
        var d1 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.25)
            d1.push([i, Math.sin(i)]);

        var d2 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.25)
            d2.push([i, Math.cos(i)]);

        var d3 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.1)
            d3.push([i, Math.tan(i)]);

        $.plot($("#flotchart"), [
            { label: "sin(x)",  data: d1},
            { label: "cos(x)",  data: d2},
            { label: "tan(x)",  data: d3}
        ], {
            series: {
                lines: { show: true },
                points: { show: true }
            },
            xaxis: {
                ticks: [0, [Math.PI/2, "\u03c0/2"], [Math.PI, "\u03c0"], [Math.PI * 3/2, "3\u03c0/2"], [Math.PI * 2, "2\u03c0"]]
            },
            yaxis: {
                ticks: 10,
                min: -2,
                max: 2
            },
            grid: {
                backgroundColor: { colors: ["#fff", "#eee"] }
            }
        });
    }

    //stack chart
    if($("#stackchart").length)
    {
        var d1 = [];
        for (var i = 0; i <= 10; i += 1)
            d1.push([i, parseInt(Math.random() * 30)]);

        var d2 = [];
        for (var i = 0; i <= 10; i += 1)
            d2.push([i, parseInt(Math.random() * 30)]);

        var d3 = [];
        for (var i = 0; i <= 10; i += 1)
            d3.push([i, parseInt(Math.random() * 30)]);

        var stack = 0, bars = true, lines = false, steps = false;

        function plotWithOptions() {
            $.plot($("#stackchart"), [ d1, d2, d3 ], {
                series: {
                    stack: stack,
                    lines: { show: lines, fill: true, steps: steps },
                    bars: { show: bars, barWidth: 0.6 }
                }
            });
        }

        plotWithOptions();

        $(".stackControls input").click(function (e) {
            e.preventDefault();
            stack = $(this).val() == "With stacking" ? true : null;
            plotWithOptions();
        });
        $(".graphControls input").click(function (e) {
            e.preventDefault();
            bars = $(this).val().indexOf("Bars") != -1;
            lines = $(this).val().indexOf("Lines") != -1;
            steps = $(this).val().indexOf("steps") != -1;
            plotWithOptions();
        });
    }

    //pie chart
    var data = [
        { label: "Internet Explorer",  data: 12},
        { label: "Mobile",  data: 27},
        { label: "Safari",  data: 85},
        { label: "Opera",  data: 64},
        { label: "Firefox",  data: 90},
        { label: "Chrome",  data: 112}
    ];

    if($("#piechart").length)
    {
        $.plot($("#piechart"), data,
                {
                    series: {
                        pie: {
                            show: true
                        }
                    },
                    grid: {
                        hoverable: true,
                        clickable: true
                    },
                    legend: {
                        show: false
                    }
                });

        function pieHover(event, pos, obj)
        {
            if (!obj)
                return;
            percent = parseFloat(obj.series.percent).toFixed(2);
            $("#hover").html('<span style="font-weight: bold; color: '+obj.series.color+'">'+obj.series.label+' ('+percent+'%)</span>');
        }
        $("#piechart").bind("plothover", pieHover);
    }

    //donut chart
    if($("#donutchart").length)
    {
        $.plot($("#donutchart"), data,
                {
                    series: {
                        pie: {
                            innerRadius: 0.5,
                            show: true
                        }
                    },
                    legend: {
                        show: false
                    }
                });
    }




    // we use an inline data source in the example, usually data would
    // be fetched from a server
    var data = [], totalPoints = 300;
    function getRandomData() {
        if (data.length > 0)
            data = data.slice(1);

        // do a random walk
        while (data.length < totalPoints) {
            var prev = data.length > 0 ? data[data.length - 1] : 50;
            var y = prev + Math.random() * 10 - 5;
            if (y < 0)
                y = 0;
            if (y > 100)
                y = 100;
            data.push(y);
        }

        // zip the generated y values with the x values
        var res = [];
        for (var i = 0; i < data.length; ++i)
            res.push([i, data[i]])
        return res;
    }

    // setup control widget
    var updateInterval = 30;
    $("#updateInterval").val(updateInterval).change(function () {
        var v = $(this).val();
        if (v && !isNaN(+v)) {
            updateInterval = +v;
            if (updateInterval < 1)
                updateInterval = 1;
            if (updateInterval > 2000)
                updateInterval = 2000;
            $(this).val("" + updateInterval);
        }
    });

    //realtime chart
    if($("#realtimechart").length)
    {
        var options = {
            series: { shadowSize: 1 }, // drawing is faster without shadows
            yaxis: { min: 0, max: 100 },
            xaxis: { show: false }
        };
        var plot = $.plot($("#realtimechart"), [ getRandomData() ], options);
        function update() {
            plot.setData([ getRandomData() ]);
            // since the axes don't change, we don't need to call plot.setupGrid()
            plot.draw();

            setTimeout(update, updateInterval);
        }

        update();
    }
}


//additional functions for data table
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
    return {
        "iStart":         oSettings._iDisplayStart,
        "iEnd":           oSettings.fnDisplayEnd(),
        "iLength":        oSettings._iDisplayLength,
        "iTotal":         oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
        "iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
    };
}
$.extend( $.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function( oSettings, nPaging, fnDraw ) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function ( e ) {
                e.preventDefault();
                if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                    fnDraw( oSettings );
                }
            };

            $(nPaging).addClass('pagination').append(
                    '<ul>'+
                            '<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
                            '<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
                            '</ul>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
            $(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
        },

        "fnUpdate": function ( oSettings, fnDraw ) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

            if ( oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if ( oPaging.iPage <= iHalf ) {
                iStart = 1;
                iEnd = iListLength;
            } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }

            for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
                // remove the middle elements
                $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                // add the new list items and their event handlers
                for ( j=iStart ; j<=iEnd ; j++ ) {
                    sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                    $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                            .insertBefore( $('li:last', an[i])[0] )
                            .bind('click', function (e) {
                                e.preventDefault();
                                oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                                fnDraw( oSettings );
                            } );
                }

                // add / remove disabled classes from the static elements
                if ( oPaging.iPage === 0 ) {
                    $('li:first', an[i]).addClass('disabled');
                } else {
                    $('li:first', an[i]).removeClass('disabled');
                }

                if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                    $('li:last', an[i]).addClass('disabled');
                } else {
                    $('li:last', an[i]).removeClass('disabled');
                }
            }
        }
    }
});
</script>
<body>
<div class="container-fluid">
<div class="row-fluid">
<div id="content" class="span12">
<div>
    <ul class="breadcrumb">
        <li>
            <a href="#">首页</a> <span class="divider">/</span>
        </li>
        <li>
            <a href="#">系统入口</a>
        </li>
    </ul>
</div>
<div class="sortable row-fluid">
    <a data-rel="tooltip" title="6 new members." class="well span3 top-block" href="#">
        <span class="icon32 icon-red icon-user"></span>

        <div>Total Members</div>
        <div>507</div>
        <span class="notification">6</span>
    </a>

    <a data-rel="tooltip" title="4 new pro members." class="well span3 top-block" href="#">
        <span class="icon32 icon-color icon-star-on"></span>

        <div>Pro Members</div>
        <div>228</div>
        <span class="notification green">4</span>
    </a>

    <a data-rel="tooltip" title="$34 new sales." class="well span3 top-block" href="#">
        <span class="icon32 icon-color icon-cart"></span>

        <div>Sales</div>
        <div>$13320</div>
        <span class="notification yellow">$34</span>
    </a>

    <a data-rel="tooltip" title="12 new messages." class="well span3 top-block" href="#">
        <span class="icon32 icon-color icon-envelope-closed"></span>

        <div>Messages</div>
        <div>25</div>
        <span class="notification red">12</span>
    </a>
</div>

<div class="row-fluid">
    <div class="box span12">
        <div class="box-header well">
            <h2><i class="icon-info-sign"></i> 系统介绍</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content">
            <h1>Kitten
                <small>喵咪咪~~~~~.</small>
            </h1>
            <p>喵咪<。)#)))≦喵呜~~~</p>
        </div>
    </div>
</div>

<div class="row-fluid sortable">
    <div class="box span4">
        <div class="box-header well">
            <h2><i class="icon-th"></i> 百叶窗</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content">
            <ul class="nav nav-tabs" id="myTab">
                <li class="active"><a href="#info">窗户一</a></li>
                <li><a href="#custom">窗户二</a></li>
                <li><a href="#messages">窗户三</a></li>
            </ul>

            <div id="myTabContent" class="tab-content">
                <div class="tab-pane active" id="info">
                    <h1>Kitten
                        <small>喵咪咪~~~~~.</small>
                    </h1>
                    <p>喵咪<。)#)))≦喵呜~~~</p>
                </div>
                <div class="tab-pane" id="custom">
                    呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌
                </div>
                <div class="tab-pane" id="messages">
                    呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌 呆萌萌
                </div>
            </div>
        </div>
    </div>
    <!--/span-->

    <div class="box span4">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-user"></i>哼哼</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content">
            <div class="box-content">
                <ul class="dashboard-list">
                    傲娇的啥都不说~~~~~
                </ul>
            </div>
        </div>
    </div>
    <!--/span-->

    <div class="box span4">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-list-alt"></i> 图表走起来</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content">
            <div id="realtimechart" style="height:190px;"></div>
        </div>
    </div>
    <!--/span-->
</div>
<!--/row-->

<div class="row-fluid sortable">
    <div class="box span4">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-list"></i> 按钮走起来</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content buttons">
            <p class="btn-group">
                <button class="btn">Left</button>
                <button class="btn">Middle</button>
                <button class="btn">Right</button>
            </p>
            <p>
                <button class="btn btn-small"><i class="icon-star"></i> Icon button</button>
                <button class="btn btn-small btn-primary">Small button</button>
                <button class="btn btn-small btn-danger">Small button</button>
            </p>
            <p>
                <button class="btn btn-small btn-warning">Small button</button>
                <button class="btn btn-small btn-success">Small button</button>
                <button class="btn btn-small btn-info">Small button</button>
            </p>
            <p>
                <button class="btn btn-small btn-inverse">Small button</button>
                <button class="btn btn-large btn-primary btn-round">Round button</button>
                <button class="btn btn-large btn-round"><i class="icon-ok"></i></button>
                <button class="btn btn-primary"><i class="icon-edit icon-white"></i></button>
            </p>
            <p>
                <button class="btn btn-mini">Mini button</button>
                <button class="btn btn-mini btn-primary">Mini button</button>
                <button class="btn btn-mini btn-danger">Mini button</button>
                <button class="btn btn-mini btn-warning">Mini button</button>
            </p>
            <p>
                <button class="btn btn-mini btn-info">Mini button</button>
                <button class="btn btn-mini btn-success">Mini button</button>
                <button class="btn btn-mini btn-inverse">Mini button</button>
            </p>
        </div>
    </div>
    <!--/span-->

    <div class="box span4">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-list"></i> 按钮继续走</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content  buttons">
            <p>
                <button class="btn btn-large">Large button</button>
                <button class="btn btn-large btn-primary">Large button</button>
            </p>
            <p>
                <button class="btn btn-large btn-danger">Large button</button>
                <button class="btn btn-large btn-warning">Large button</button>
            </p>
            <p>
                <button class="btn btn-large btn-success">Large button</button>
                <button class="btn btn-large btn-info">Large button</button>
            </p>
            <p>
                <button class="btn btn-large btn-inverse">Large button</button>
            </p>
            <div class="btn-group">
                <button class="btn btn-large">Large Dropdown</button>
                <button class="btn btn-large dropdown-toggle" data-toggle="dropdown"><span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#"><i class="icon-star"></i> Action</a></li>
                    <li><a href="#"><i class="icon-tag"></i> Another action</a></li>
                    <li><a href="#"><i class="icon-download-alt"></i> Something else here</a></li>
                    <li class="divider"></li>
                    <li><a href="#"><i class="icon-tint"></i> Separated link</a></li>
                </ul>
            </div>

        </div>
    </div>
    <!--/span-->

    <div class="box span4">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-list"></i>条目走起来</h2>

            <div class="box-icon">
                <a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
                <a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
                <a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
            </div>
        </div>
        <div class="box-content">
            <ul class="dashboard-list">
                <li>
                    <a href="#">
                        <i class="icon-arrow-up"></i>
                        <span class="green">92</span>
                        New Comments
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-arrow-down"></i>
                        <span class="red">15</span>
                        New Registrations
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-minus"></i>
                        <span class="blue">36</span>
                        New Articles
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-comment"></i>
                        <span class="yellow">45</span>
                        User reviews
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-arrow-up"></i>
                        <span class="green">112</span>
                        New Comments
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-arrow-down"></i>
                        <span class="red">31</span>
                        New Registrations
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-minus"></i>
                        <span class="blue">93</span>
                        New Articles
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-comment"></i>
                        <span class="yellow">254</span>
                        User reviews
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <!--/span-->
</div>
<!--/row-->


<!-- content ends -->
</div>
<!--/#content.span10-->
</div>
<!--/fluid-row-->

<hr>

</div>

<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>确认</h3>
    </div>
    <div class="modal-body">
        <p></p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn btn-primary">Save changes</a>
        <a href="#" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>

<div class="modal hide fade" id="confirmModal" style="z-index: 99999;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>请确认</h3>
    </div>
    <div class="modal-body">
        <p></p>
    </div>
    <div class="modal-footer">
        <a href="javascript:void(0);" class="btn btn-primary">确定</a>
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>

<div class="modal hide fade" id="alertModal" style="z-index: 99999;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        <p></p>
    </div>
    <div class="modal-footer">
        <a href="javascript:void(0);" class="btn btn-primary">确定</a>
    </div>
</div>

<div class="modal hide fade" id="promptModal" style="width:510px;z-index: 99999;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提示框</h3>
    </div>
    <div class="modal-body" style="width:480px;">
        <p></p>
        <input class="span5" type="text" value="" placeholder="">
    </div>
    <div class="modal-footer">
        <a href="javascript:void(0);" class="btn btn-primary">确定</a>
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">关闭</a>
    </div>
</div>
</body>
</html>
