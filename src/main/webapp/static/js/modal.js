/**
 * Created by chengzhencai on 2017/4/25.
 */
$.university.system.modal = {

  openDialog: function (title, url, height, width) {
    this.initModal();
    if (!height) {
      height = 0.6;
    }
    if (!width) {
      width = 0.7;
    }
    $('.modal-title').text(title);
    $('#myModalDialog').css('width', window.screen.width * width);
    $('#myModalBody').css('height', window.screen.height * height);
    $('#myFrame').attr('src', url);
    $('#myModal').modal({backdrop: 'static', keyboard: false});

  },
  /**
   * 初始化modal
   * @returns {boolean}
   */
  initModal: function () {
    if ($('.modal-title').length > 0) {
      return false;
    }
    $('body').append($('<div>').addClass('modal fade ').attr({id: 'myModal', tabindex: '-1', role: 'dialog'}).append(
        $('<div>').addClass('modal-dialog').attr({id: 'myModalDialog'}).append(
          $('<div>').addClass('modal-content').append(
            $('<div>').addClass('modal-header').append(
              $('<button>').addClass('close').attr({
                type: "button",
                'data-dismiss': 'modal',
                'aria-label': 'Close'
              }).append(
                $('<span>').attr({'aria-hidden': true}).append(
                  $('<em>').addClass('glyphicon glyphicon-remove')
                )
              )
            ).append(
              $('<h3>').append(
                $('<small>').addClass('modal-title').text('Modal title')
              )
            )
          ).append(
            $('<div>').addClass('modal-body').attr({id: 'myModalBody'}).append(
              $('<iframe>').attr({
                id: 'myFrame', name: 'myFrame', src: '', frameborder: 0,
                allowfullscreen: true, allowtransparency: true
              }).css({'width': '100%', height: '100%', border: 0})
            )
          )
        )
      )
    );
  },
  confirm: function (message, url, data) {
    layer.confirm('是否删除' + message, function () {
      $.ajax({
        url: url,
        dataType: "json",
        async: false,
        type: "POST",
        data: data,
        success: function (data) {
          if (data.status == 1) {
            layer.alert(message + '删除成功', function () {
              window.location.reload();
            });
          }else if(data.status == 2){
            layer.alert(data.message)
          } else {
            layer.alert(message + '删除失败.')
          }
        }
      });
    })
  }
};
