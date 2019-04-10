<#include "../_/_header_extra.ftl">
<html>
<head>
    <title>账号列表</title>
</head>
<body>
<div class="container-fluid">
    <url id="blacklist"
         title="黑名单"
         update="updateBlacklistPage">
    </url>
    <form class="row col-sm-12 form-inline" id="_search">
        <div class="form-group">
            <label for="_search">姓名:</label>
            <input type="text" name="realName" class="form-control" placeholder="姓名"/>
        </div>
        <div class="form-group">
            <label for="_search">账号:</label>
            <input type="text" name="account" class="form-control" placeholder="账号"/>
        </div>

        <div class="form-group">
            <label for="_search">状态:</label>
            <select name="state" class="form-control">
                <option value="">全部</option>
                <option value="1">正常</option>
                <option value="-1">禁用</option>
            </select>
        </div>
        <button type="button" class="btn btn-primary" onclick="$.university.table.search();">搜索</button>
        <a href="#" class="btn btn-info pull-right"
           onclick="$.university.system.modal.openDialog('新增账号','/manage/account/account_add.action',0.3,0.7)">新增账号</a>
    </form>
    <table class="table table-striped table-bordered table-condensed"
           data-toggle="table"
           data-url="/member/account/findTableList.json"
           data-query-params="$.university.table.queryParams"
           data-height="600"
           data-pagination="true">
        <thead>
        <th data-field='accountID'>编码</th>
        <th data-field='realName'>姓名</th>
        <th data-field='account' data-align="center">账号</th>
        <th data-field='state' data-formatter="$.university.format.status" data-align="center">状态</th>
        <th data-field='lastLoginIP'>最后登录ip</th>
        <th data-field='lastLoginTime' data-formatter="$.university.format.dateTimeFormat" data-align="center">最后登录时间
        </th>
        <th data-field='createdTime' data-formatter="$.university.format.dateTimeFormat" data-align="center">创建时间</th>
        <th data-field="action" data-formatter="$.university.table.action"
            data-events="blacklistActionEvents" data-align="center">操作
        </th>
        </thead>
    </table>
</div>
</body>
<#include "../_/_footer_extra.ftl">