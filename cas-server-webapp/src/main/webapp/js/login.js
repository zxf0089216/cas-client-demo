var validator;
$(document).ready(function () {
    $.validator.setDefaults({
        // debug: true
    });

    validator = $("#fm1").validate({
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
        submitHandler: function (form) {
            // console.log($(form).serialize());
            form.submit();
        },
        // onfocusout: function(element){
        //     $(element).valid();
        // },
        // focusCleanup: true
    });

    $('input').focus(function () {
        $("#msg").text("").hide();
    }).focusout(function () {
        $("#msg").text("").hide();
    });
});