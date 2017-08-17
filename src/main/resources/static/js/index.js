var index = new Object();

index.inputPage = function () {
    //    var username = $("#username").val();
    //    var password = $("#password").val();
    //    var check = $("#keep_login").get(0);
    //    var keepOnline = $("#keep_online").get(0).checked;


    $.ajax({
        url: '/input',
        type: 'get',
        dataType: 'html',
        async: false,
        data: {
            "username": "123",
        },
        success: function (data) {
            console.log(data)
            cb(data);
        },
        error: function () {
            console.log('error');
        }
    });

    function cb(data) {
        $("#index-main").html(data);
    }
}