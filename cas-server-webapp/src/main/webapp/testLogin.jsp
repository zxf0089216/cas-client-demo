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
                        <input type="text" cssClass="form-control" id="username" size="25" tabindex="1"  placeholder="输入用户名" value="admin"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">密码</label>
                    <div class="col-md-7">
                        <%--<input type="password" class="form-control" placeholder="输入密码">--%>
                        <input type="password" cssClass="required form-control" cssErrorClass="error" id="password" size="25" tabindex="2"  placeholder="输入密码" value="1"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-3">
                        <input type="hidden" name="isajax" value="true" />
                        <input type="hidden" name="isframe" value="true" />
                        <input id="loginTicket" type="hidden" name="lt" value="${loginTicket}"/>
                        <input id="flowExecutionKey" type="hidden" name="execution" value="${flowExecutionKey}"/>
                        <input type="hidden" name="_eventId" value="submit"/>

                        <input type="submit" class="btn btn-sm btn-primary m-r-5"  tabindex="4" value="登录" />
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
    var loginUrl = 'http://localhost:8488/cas/login';
    var clientUrl = 'http://localhost:8080/client1';
    var logoutUrl = 'http://localhost:8488/cas/logout';
//    logout();
//    login();

    $(function(){
        flushLoginTicket();
    });

    function flushLoginTicket(callback){
        var _services = 'service=' + encodeURIComponent(clientUrl);
        $.getJSON(loginUrl + '?'+_services+'&get-lt=true&n='+ new Date().getTime()+ "&callback=?",function(response) {
            console.log(response);
            $('#loginTicket').val(response.lt);
            $('#flowExecutionKey').val(response.execution);

            if(callback){
                callback();
            }
        });

//        $.ajax({
//            url: loginUrl + '?'+_services+'&get-lt=true&n='+ new Date().getTime(),
//            type: 'GET',
//            data:{},
//            dataType: 'json',
//            timeout: 1000,
//            async:false,
//            error: function(){alert('Error');},
//            success: function(result){
//                console.log(response);
//                $('#loginTicket').val(response.lt);
//                $('#flowExecutionKey').val(response.execution);
//
//                if(callback){
//                    callback();
//                }
//            }
//        });
    }

    function login(){
        flushLoginTicket(function(){
            var lt=$('#loginTicket').val();
            var ev=$('#flowExecutionKey').val();

            $.ajax({
                url: loginUrl,
                type: 'POST',
                async:true,
                data:{"execution":ev,"lt":lt,"password":"1","submit":"LOGIN","username":"admin","_eventId":"submit"},
                timeout: 1000,
                error: function(r){
//                debugger;
                    console.log(r);
                    alert('Error');
                },
                success: function(result){
                    //如果登陆成功,则跳转,否则提示错误
                    if(result.indexOf("登录成功")!=-1){
                        alert('ok   lt:'+lt);
//                    window.location.href="http://localhost/test";
                    }else{
                        alert("登录失败");
                    }
                }
            });

        });

        return false;
    }

    function logout(){
        $.ajax({
            url: logoutUrl,
            type: 'GET',
            dataType: 'html',
            timeout: 1000,
            async:false,
            error: function(){},
            success: function(result){
                console.log("logout success.constructor.");
//                login();
            }
        });
    }
</script>
<!-- ================== END BASE JS ================== -->

</body>
</html>
