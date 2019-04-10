<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>后台管理系统</title>
    <link href="${base.contextPath}/static/plugin/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${base.contextPath}/static/css/dashboard.css" rel="stylesheet">
    <link href="${base.contextPath}/static/css/leftnav.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" style="background-color: #28353c;font-size: 16px;padding: 10px">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#" style="color: #fff">后台管理系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" style="color: #fff">Welcome</a></li>
                <li><a href="#"><i
                        class="glyphicon glyphicon-user" style="color: #fff">${(realName)!''}</i></a></li>
                <li><a href="#"><i
                        class="glyphicon glyphicon-off text-danger">退出</i></a></li>
                <li><a href="#"></a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>
<i class="glyphicon glyphicon-menu-right"></i>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-md-2 sidebar">
            <ul id="accordion" class="accordion">
            <#if menuList??>
                <#list menuList as menu>
                    <li>
                        <a href="#${menu.menuName}" class="link collapsed" data-toggle="collapse"
                           style="text-decoration:none;">
                        ${menu.menuName}
                            <span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
                        </a>
                        <ul id="${menu.menuName}" class="submenu collapse">
                            <#list menu.permissionList as permission>
                                <li id="${permission.permissionSign}"><a>${permission.permissionName}</a>
                                </li>
                            </#list>
                        </ul>
                    </li>
                </#list>
            </#if>
            </ul>
        </div>
        <div class="col-sm-10 col-sm-offset-2 col-md-11 col-md-offset-2 main">
            
        </div>
    </div>
</div>
</body>
<script src="${base.contextPath}/static/js/jquery.min.js"></script>
<script src="${base.contextPath}/static/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${base.contextPath}/static/js/leftnav.js"></script>
</html>