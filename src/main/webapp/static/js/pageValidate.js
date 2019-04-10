/**
 * Created by chengzhencai on 2017/4/25.
 * 添加和修改页面,返回确认
 * @type {{add: Function, update: Function, request: Function}}
 */
$.university.common.plugins.validate = {
    add: function (id,url) {
        this.request(id, "add",url)
    },
    update: function (id,url) {
        this.request(id, "update",url)
    },
    request: function (id, action,url) {
        $("#" + id + "-form").validate({
                submitHandler: function (form) {
                    $(':submit').attr("disabled", true);
                    $(form).ajaxSubmit({
                        type: "post",
                        dataType: "json",
                        url: url,
                        success: function (data) {
                            if (data.status == 1) {
                                parent.$('table').bootstrapTable('refresh');
                                layer.alert(data.message, function () {
                                    parent.$('.modal-backdrop').remove();
                                    parent.$('.modal').remove();
                                });
                            } else {
                                layer.alert(data.message);
                            }
                        }
                    });
                }
            }
        )
        ;
    }
}
;