<#include "../_/_header_extra.ftl">
<html>
<head>
    <title>菜单列表</title>
</head>
<body>
<div class="container-fluid">
    <url id="menuID"
         title="菜单"
         info="/member/menu/menu-info">
    </url>
    <form class="row col-sm-12 form-inline" id="_search">
        <div class="form-group">
            <label for="_search">菜单名称:</label>
            <input type="text" name="menuName" class="form-control" placeholder="配置项"/>
        </div>
        <button type="button" class="btn btn-primary" onclick="$.university.table.search();">搜索</button>
        <a href="#" class="btn btn-info pull-right"
           onclick="$.university.system.modal.openDialog('新增菜单','/manage/menu/menu_add.action',0.3,0.7)">新增菜单</a>
    </form>
    <table class="table table-striped table-bordered table-condensed"
           data-toggle="table"
           data-url="/member/menu/findTableList.json"
           data-query-params="$.university.table.queryParams"
           data-height="600"
           data-pagination="true">
        <thead>
        <th data-field='menuID'>编码</th>
        <th data-field='menuName'>菜单名称</th>
        <th data-field='systemSign' data-align="center">系统标识</th>
        <th data-field='menuOrder' data-align="center">排序</th>
        <th data-field='remark'>备注</th>
        <th data-field='createdTime' data-formatter="$.university.format.dateTimeFormat" data-align="center">创建时间</th>
        <th data-field="action" data-formatter="$.university.table.action" data-bind="info,update,bind"
            data-events="ActionEvents" data-align="center">操作
        </th>
        </thead>
    </table>
</div>
</body>
<#include "../_/_footer_extra.ftl">