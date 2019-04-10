layui.use('element', function () {
    var $ = layui.jquery, element = layui.element;
    //触发事件
    var active = {
        tabAdd: function () {
            var htmlurl = $(this).attr('data-url');
            var mytitle = $(this).attr('mytitle');
            var arrayObj = new Array();
            $(".layui-tab-title").find('li').each(function () {
                var y = $(this).attr("lay-id");
                arrayObj.push(y);
            });
            var have = $.inArray(mytitle, arrayObj);
            if (have >= 0) {

                element.tabChange('demo', mytitle);
            } else {
                element.tabAdd('demo', {
                    title: mytitle,
                    content: '<iframe style="width:100%;height: 100%;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" src=' + htmlurl + ' ></iframe>',
                    id: mytitle
                })
                element.tabChange('demo', mytitle);
            }
        }

    };
    $(".leftdaohang").click(function () {
        var type = "tabAdd";
        var othis = $(this);
        active[type] ? active[type].call(this, othis) : '';
    });
});

var isShow = true;  //定义一个标志位
$('.kit-side-fold').click(function () {
    //选择出所有的span，并判断是不是hidden
    $('.layui-nav-item span').each(function () {
        if ($(this).is(':hidden')) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
    //判断isshow的状态
    if (isShow) {
        $('.layui-side.layui-bg-black').width(60); //设置宽度
        $('.kit-side-fold i').css('margin-right', '70%');  //修改图标的位置
        //将footer和body的宽度修改
        $('.layui-body').css('left', 60 + 'px');
        $('.layui-footer').css('left', 60 + 'px');
        //将二级导航栏隐藏
        $('dd span').each(function () {
            $(this).hide();
        });
        //修改标志位
        isShow = false;
    } else {
        $('.layui-side.layui-bg-black').width(200);
        $('.kit-side-fold i').css('margin-right', '10%');
        $('.layui-body').css('left', 200 + 'px');
        $('.layui-footer').css('left', 200 + 'px');
        $('dd span').each(function () {
            $(this).show();
        });
        isShow = true;
    }
});