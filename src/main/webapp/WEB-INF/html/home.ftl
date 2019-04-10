<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>首页</title>
    <link rel="stylesheet" href="${base.contextPath}/static/plugin/layui-v2.4.5/layui/css/layui.css">
    <link rel="stylesheet" href="${base.contextPath}/static/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">后台管理</div>
        <ul class="layui-nav layui-layout-left"></ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item"><i class="fa fa-user fa-lg"></i>${realName}</li>
            <li class="layui-nav-item"><a href="">退出</a></li>
        </ul>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <div title="菜单缩放" class="kit-side-fold" style="text-align: center;font-size: 20px">
                <i class="fa fa-navicon" aria-hidden="true"></i>
            </div>
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                <#if menuList??>
                    <#list menuList as menu>
                        <a class=""><i class="fa fa-gear fa-lg"></i> <span>${menu.menuName}</span></a>
                        <dl class="layui-nav-child">
                            <#list menu.permissionList as permission>
                                <dd class="leftdaohang" data-url="${permission.permissionSign}"
                                    mytitle="${permission.permissionName}"><a><i class="fa fa-align-justify"></i>
                                    <span>${permission.permissionName}</span></a></dd>
                            </#list>
                        </dl>
                    </#list>
                </#if>
                </li>
            </ul>
        </div>
    </div>
    <div class="layui-body">
        <div class="layui-tab layui-tab-card" lay-filter="demo" lay-allowclose="true">
            <ul class="layui-tab-title"> </ul>
            <div class="layui-tab-content"></div>
        </div>
    </div>
    <div class="layui-footer">© university.com - 报名考试系统</div>
</div>
<script src="${base.contextPath}/static/js/jquery.min.js"></script>
<script src="${base.contextPath}/static/plugin/layui-v2.4.5/layui/layui.all.js"></script>
<script src="${base.contextPath}/static/js/home.js"></script>
</body>
<script>
    $(document).ready(function () {
        $('.layui-tab-content').css({height: window.screen.availHeight-240})
    });

</script>
</html>