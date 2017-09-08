var user = new Object();

$(function () {
    //激活输入框
    // $("#user-search-input").focus();

});

//点击搜索按钮时执行的方法
$("#user-search-button").on('click',function () {
    var requestURL = $("#request-url").val();
    var parameter = $("#user-search-input").val();
    // var href =
    window.location.href = requestURL + "?parameter=" + parameter;
});

//检测回车事件
$(document).keydown(function(event){
    // console.log(event.keyCode);
    // console.log($("#user-search-input:focus").length);
    if(event.keyCode ==13){
        if($("#user-search-input:focus").length==1){
            $("#user-search-button").trigger("click");
        }
    }
});
