<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <title>后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${base.contextPath}/static/login/css/reset.css">
    <link rel="stylesheet" href="${base.contextPath}/static/login/css/supersized.css">
    <link rel="stylesheet" href="${base.contextPath}/static/login/css/style.css">
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
<style>
    input:-webkit-autofill,
    input:-webkit-autofill:hover,
    input:-webkit-autofill:focus {
        box-shadow: 0 0 0 60px #60647a inset;
        -webkit-text-fill-color: #878787;
    }
</style>
<body>
<div class="page-container">
    <h1>后台管理系统</h1>
    <form action="/inner/login.json" method="post" id="login">
        <div>
            <input type="text" name="account" class="account" placeholder="Account" autocomplete="off"/>
        <#if errorCode??>
            <div style="color: red;float: right">${msg}</div></#if>
        </div>
        <div>
            <input type="password" name="password" class="password" placeholder="Password"
                   oncontextmenu="return false"
                   onpaste="return false"/>
        <#if errorCode??>
            <div style="color: red;float: right">${msg}</div></#if>
        </div>
        <button id="submit" type="submit" onclick="document.getElementById('login').submit();">Sign in</button>
    </form>
    <div class="connect">
        <p>If we can only encounter each other rather than stay with each other,then I wish we had never
            encountered.</p>
        <p style="margin-top:20px;">如果只是遇见，不能停留，不如不遇见。</p>
    </div>
</div>
<!-- Javascript -->
<script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
<script src="${base.contextPath}/static/login/js/supersized.3.2.7.min.js"></script>
<script src="${base.contextPath}/static/login/js/supersized-init.js"></script>
<script>
    window.onload = function () {
        $(".connect p").eq(0).animate({"left": "0%"}, 600);
        $(".connect p").eq(1).animate({"left": "0%"}, 400);
    }
</script>
</body>

</html>

