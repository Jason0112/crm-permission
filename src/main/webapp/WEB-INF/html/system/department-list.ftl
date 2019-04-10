<#include "../_/_header_extra.ftl">
<html>
<head>
    <title>部门列表</title>
</head>
<body>
<div class="container-fluid">
    <url id="blacklist"
         title="黑名单"
         update="updateBlacklistPage">
    </url>
    <form class="row col-sm-12 form-inline" id="_search">
        <div class="form-group">
            <label for="_search">部门名称:</label>
            <input type="text" name="departmentName" class="form-control" placeholder="部门名称"/>
        </div>
        <button type="button" class="btn btn-primary" onclick="$.university.table.search();">搜索</button>
        <a href="#" class="btn btn-info pull-right"
           onclick="$.university.system.modal.openDialog('新增部门','/manage/department/department_add.action',0.3,0.7)">新增部门</a>
    </form>
    <table class="table table-striped table-bordered table-condensed"
           data-toggle="table"
           data-url="/member/department/findTableList.json"
           data-query-params="$.university.table.queryParams"
           data-height="600"
           data-pagination="true">
        <thead>
        <th data-field='departmentID'>编码</th>
        <th data-field='departmentName'>部门名称</th>
        <th data-field='departmentSign' data-align="center">部门标识</th>
        <th data-field='departmentState' data-formatter="$.university.format.status" data-align="center">状态</th>
        <th data-field='remark'>备注</th>
        <th data-field='createdTime'>创建时间</th>
        <th data-field="action" data-formatter="$.university.table.settingAction" data-bind="info,update"
            data-events="blacklistActionEvents" data-align="center">操作
        </th>
        </thead>
    </table>
</div>
</body>
<#include "../_/_footer_extra.ftl">