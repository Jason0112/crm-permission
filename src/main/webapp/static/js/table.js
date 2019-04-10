/**
 * Created by chengzhencai on 16/5/4.
 */
$.university.table = {
    info: '<a class="info btn btn-info" href="#" title="查看"> 查看<i class="glyphicon glyphicon-edit"> </i></a>',
    update: '<a class="edit btn btn-info" href="#" title="修改"> 修改<i class="glyphicon glyphicon-edit"> </i></a>',
    remove: '<a class="remove btn btn-warning" href="#" title="删除"> 删除<i class="glyphicon glyphicon-remove"> </i></a>',
    bind: '<a class="bind btn btn-warning" href="#" title="绑定"> 绑定<i class="glyphicon glyphicon-remove"> </i></a>',
    queryParams: function (params) {
        var queryParameter = {
            pageSize: params.limit,
            pageNumber: params.pageNumber,
            per_page: 2,
            page: 1,
            sort: params.sort,
            order: params.order
        };
        $('#_search').find('input').each(function () {
            if (this.value.trim() != '') {
                queryParameter[this.name] = $(this).val();
            }
        });
        $('#_search').find('select').each(function () {
            if (this.value.trim() != '') {
                queryParameter[this.name] = $(this).val();
            }
        });
        return queryParameter;
    },
    search: function () {
        $('[data-toggle="table"]').bootstrapTable('refresh');
    },
    action: function () {
        var text = this.bind;
        if (!text) {
            return '';
        }
        var ary = text.split(','), operator = [];
        ary.forEach(function (item, index) {
            operator[index] = $.university.table[item];
        });
        return operator.join('');
    }
};

window.ActionEvents = {
    'click .info': function (e, value, row) {
        var obj = $('url').get(0), title = obj.title + '信息', id = obj.getAttribute('id');
        var url = obj.getAttribute('info') + ".action?" + id + "=" + row[id];
        $.university.system.modal.openDialog(title, url, 0.4);
    },
    'click .edit': function (e, value, row) {
        var obj = $('url').get(0), title = '更新' + obj.title + '信息', id = obj.getAttribute('id');
        var url = obj.getAttribute('info') + ".action?" + id + "=" + row[id] + "&update=true";
        $.university.system.modal.openDialog(title, url, 0.4);
    },
    'click .bind': function (e, value, row) {
        var obj = $('url').get(0), title = '绑定' + obj.title + '信息', id = obj.getAttribute('id');
        var url = obj.getAttribute('info') + ".action?" + id + "=" + row[id] + "&update=true";
        $.university.system.modal.openDialog(title, url, 0.4);
    },
    'click .remove': function (e, value, row) {
        var obj = $('url').get(0), title = obj.title, id = obj.getAttribute('id');
        var url = obj.getAttribute('delete') + ".json";
        $.university.system.modal.confirm(title, url, {id: row[id]});
    }
};