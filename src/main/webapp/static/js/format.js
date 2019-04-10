/**
 * Created by chengzhencai on 2017/7/17.
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

$.university.format = {
    status: function (value) {
        if (1 == value) {
            return '启用'
        } else if (0 == value) {
            return '禁用'
        }
        return '未配置'
    },
    dateTimeFormat: function (value) {
        if (value == null) {
            return '-';
        }
        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
    }
};
