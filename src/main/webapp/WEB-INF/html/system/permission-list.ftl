<#include "../_/_header_extra.ftl">
<html>
<head>
    <title>权限列表</title>
</head>
<body>
<div class="container-fluid">
    <url id="blacklist"
         title="黑名单"
         update="updateBlacklistPage">
    </url>
    <form class="row col-sm-12 form-inline" id="_search">
        <div class="form-group">
            <label for="_search">权限名称:</label>
            <input type="text" name="permissionName" class="form-control" placeholder="配置项"/>
        </div>
        <button type="button" class="btn btn-info" onclick="$.university.table.search();">搜索</button>
        <a href="#" class="btn btn-info pull-right"
           onclick="$.university.system.modal.openDialog('新增权限','/manage/permission/permission_add.action',0.3,0.7)">新增权限</a>
    </form>
    <table class="table table-striped table-bordered table-condensed"
           data-toggle="table"
           data-url="/member/permission/findTableList.json"
           data-query-params="$.university.table.queryParams"
           data-height="600"
           data-pagination="true">
        <thead>
        <th data-field='permissionID'>编码</th>
        <th data-field='permissionName'>权限名称</th>
        <th data-field='permissionState' data-formatter="$.university.format.status" data-align="center">状态</th>
        <th data-field='permissionSign'>权限标识</th>
        <th data-field='menuItem' data-formatter="$.university.format.status" data-align="center">是否是菜单</th>
        <th data-field='menuUrl'>URL</th>
        <th data-field='menuName'>主菜单</th>
        <th data-field='menuSort'>排序</th>
        <th data-field='createdTime' data-formatter="$.university.format.dateTimeFormat" data-align="center">创建时间</th>
        <th data-field="action" data-formatter="$.university.table.action" data-bind="info,update,bind"
            data-events="blacklistActionEvents" data-align="center">操作
        </th>
        </thead>
    </table>
</div>
</body>
<#include "../_/_footer_extra.ftl">