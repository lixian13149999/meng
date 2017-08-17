var login = new Object();

login.login = function () {
    var username = $("#username").val();
    var password = $("#password").val();
    var check = $("#keep_login").get(0);
    var keepOnline = $("#keep_online").get(0).checked;


    $.ajax({
        url: '/login',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
            "username": username,
            "password": password,
            "keepOnline": keepOnline
        },
        success: function (data) {
            console.log(data)
            if (data.code == 200) {
                window.location.href = '/login';
            }

        },
        error: function () {
            console.log('error');
        }
    });
}