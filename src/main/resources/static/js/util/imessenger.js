/*
 * 用于封装提示框的js文件
 */

var imessenger = new Object();
$(function() {
    //设置Messenger相应的参数
    Messenger.options = {
        //设置显示位置和显示样式
        extraClasses: 'messenger-fixed messenger-on-top messenger-on-right',
        //设置显示的主题
        theme: 'flat'
    }

});

imessenger.error = function(text){
    Messenger().hideAll();
    Messenger().post({
        message: text,
        type: 'error',
        hideAfter: 2,
        showCloseButton: true
    });
}
imessenger.success = function(text){
    Messenger().hideAll();
    Messenger().post({
        message: text,
        type: 'success',
        hideAfter: 2,
        showCloseButton: true
    });
}
imessenger.info = function(text){
    Messenger().hideAll();
    Messenger().post({
        message: text,
        type: 'info',
        hideAfter: 2,
        showCloseButton: true
    });
}

imessenger.show = function(state,text) {
    Messenger().hideAll();
    Messenger().post({
        message: text,
        type: state,
        hideAfter: 2,
        showCloseButton: true
    });
}