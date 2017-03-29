$(function () {

    var validator = $("#fm1").validate({
        rules: {
            username: {
                required: true,
                minlength: 2,
                maxlength: 32
            },
            password: {
                required: true,
                minlength: 1,
                maxlength: 32
            },
            "confirm-password": {
                equalTo: "#password"
            }
        },
        messages: {
            username: {
                required: "必须填写用户名",
                minlength: "用户名最小为2位",
                maxlength: "用户名最大为32位",
                rangelength: "用户名应该在2-32位",
                remote: "用户名不存在"
            },
            password: {
                required: "必须填写密码",
                minlength: "密码最小为2位",
                maxlength: "密码最大为16位",
            }
        },
        submitHandler: login
    });

    $('input').focus(function () {
        $msg.text("").hide();
    }).focusout(function () {
        $msg.text("").hide();
    });
});

var loginUrl = window.location.protocol + "//" + window.location.host + '/cas/login';
var clientUrl = getParameter("service");
var $msg = $("#msg");
var $submitBtn=$("#submitBtn");

function login() {
    var ticketUrl = loginUrl + '?get-lt=true&n=' + new Date().getTime() + "&callback=?";
    var username = $("#username").val();
    var password = $("#password").val();

    $submitBtn.attr({"disabled":"disabled"});

    $.getJSON(ticketUrl).done(function (res) {
        $.ajax({
            url: loginUrl,
            type: 'POST',
            async: true,
            data: {
                "username": username,
                "password": password,
                "execution": res.execution,
                "lt": res.lt,
                "submit": "LOGIN",
                "_eventId": "submit"
            },
            timeout: 3000,
            error: function (r) {
                console.error("登录失败", r);
                $submitBtn.removeAttr("disabled");
            },
            success: function (result) {
                if (isSuccesss(result)) {
                    if (clientUrl) {
                        window.location.href = clientUrl;
                    } else {
                        alert("登录成功");
                    }
                } else {
                    $msg.text("用户名密码错误").fadeIn();
                    $submitBtn.removeAttr("disabled");
                }
            }
        });
    }).fail(function (e) {
        console.error(e);
        alert("登录失败");
    })
}

function isSuccesss(res) {
    return "success" == res;
}

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
}