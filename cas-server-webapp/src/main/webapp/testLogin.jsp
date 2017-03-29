<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统一登录 | Dashboard</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <link href="<c:url value="/assets/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"/>
    <link href="<c:url value="/assets/css/login.css" />" rel="stylesheet"/>
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>
</head>
<!-- begin #page-container -->
<div id="page-container">

    <!-- begin login -->
    <div class="login">
        <form method="post" id="fm1" cssClass="form-horizontal" onsubmit="return login();">
            <fieldset>
                <legend>ARES Cloud</legend>
                <div class="form-group">
                    <div class="col-md-12">
                        <span class="red" style="height:12px;" id="msg"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">用户名</label>
                    <div class="col-md-7">
                        <%--<input type="text" class="form-control" placeholder="输入用户名">--%>
                        <input type="text" cssClass="form-control" id="username" size="25" tabindex="1"
                               placeholder="输入用户名" value="admin"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">密码</label>
                    <div class="col-md-7">
                        <%--<input type="password" class="form-control" placeholder="输入密码">--%>
                        <input type="password" cssClass="required form-control" cssErrorClass="error" id="password"
                               size="25" tabindex="2" placeholder="输入密码" value="1"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-3">
                        <input type="hidden" name="isajax" value="true"/>
                        <input type="hidden" name="isframe" value="true"/>
                        <input id="loginTicket" type="hidden" name="lt" value="${loginTicket}"/>
                        <input id="flowExecutionKey" type="hidden" name="execution" value="${flowExecutionKey}"/>
                        <input type="hidden" name="_eventId" value="submit"/>

                        <input type="submit" class="btn btn-sm btn-primary m-r-5" tabindex="4" value="登录"/>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <!-- end login -->

</div>
<!-- end page container -->

<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script>
    var loginUrl = window.location.protocol + "//" + window.location.host + '/cas/login';
    var clientUrl = getParameter("service");
    var $msg = $("#msg");

    function login(callback) {
        var ticketUrl = loginUrl + '?get-lt=true&n=' + new Date().getTime() + "&callback=?";

        $.getJSON(ticketUrl).done(function (res) {
            $.ajax({
                url: loginUrl,
                type: 'POST',
                async: true,
                data: {
                    "execution": res.execution,
                    "lt": res.lt,
                    "password": "2",
                    "submit": "LOGIN",
                    "username": "admin",
                    "_eventId": "submit"
                },
                timeout: 3000,
                error: function (r) {
                    console.error("登录失败", r);
                },
                success: function (result) {
                    if (isSuccesss(result)) {
                        if (clientUrl) {
                            window.location.href = clientUrl;
                        } else {
                            alert("登录成功");
                        }
                    } else {
                        $msg.text("用户名密码错误")
                    }
                }
            });
        }).fail(function (e) {
            console.log(e);
            alert("登录失败");
        });
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

    login();
</script>
<!-- ================== END BASE JS ================== -->

</body>
</html>
