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
    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="<c:url value="/assets/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"/>
    <link href="<c:url value="/assets/css/login.css" />" rel="stylesheet"/>
    <!-- ================== END BASE CSS STYLE ================== -->
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>
</head>
<!-- begin #page-container -->
<div id="page-container">

    <!-- begin login -->
    <div class="login">
        <form:form method="post" id="fm1" cssClass="form-horizontal" commandName="${commandName}" htmlEscape="true" onsubmit="return loginValidate();" target="ssoLoginFrame">
        <%--<form:form method="post" id="fm1" cssClass="form-horizontal" commandName="${commandName}" htmlEscape="true" onsubmit="return loginValidate();">--%>
            <form:errors path="*" id="msg" cssClass="errors" element="div"/>
            <fieldset>
                <legend>ARES Cloud</legend>
                <div class="form-group">
                    <div class="col-md-12">
                        <%--<form:errors path="*" id="msg" cssClass="errors" element="div"/>--%>
                            <span class="red" style="height:12px;" id="msg"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label"><spring:message code="screen.welcome.label.password"/></label>
                    <div class="col-md-7">
                        <%--<input type="text" class="form-control" placeholder="输入用户名">--%>
                        <form:input cssClass="form-control" cssErrorClass="error" id="username" size="25" tabindex="1"
                                    path="username" autocomplete="false" htmlEscape="true" placeholder="输入用户名"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><spring:message code="screen.welcome.label.password"/></label>
                    <div class="col-md-7">
                        <%--<input type="password" class="form-control" placeholder="输入密码">--%>
                        <form:password cssClass="required form-control" cssErrorClass="error" id="password" size="25" tabindex="2"
                                       path="password" htmlEscape="true" autocomplete="off" placeholder="输入密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-3">
                        <input type="hidden" name="isajax" value="true" />
                        <input type="hidden" name="isframe" value="true" />
                        <input id="loginTicket" type="hidden" name="lt" value="${loginTicket}"/>
                        <input id="flowExecutionKey" type="hidden" name="execution" value="${flowExecutionKey}"/>
                        <input type="hidden" name="_eventId" value="submit"/>

                        <input type="submit" class="btn btn-sm btn-primary m-r-5"  tabindex="4"
                               value="<spring:message code="screen.welcome.button.login" />" />

                        <%--<button type="submit" class="">登录</button>--%>
                    </div>
                </div>
            </fieldset>
        </form:form>
    </div>
    <!-- end login -->

</div>
<!-- end page container -->

<!-- ================== BEGIN BASE JS ================== -->
<%--<script type="text/javascript" src="<c:url value="/js/jquery-1.4.2.min.js" />"></script>--%>
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script>
    $(document).ready(function(){
//        flushLoginTicket();
    });

    // 登录验证函数, 由 onsubmit 事件触发
    var loginValidate = function(){
//        console.log("loginValidate....");
        var msg;
        if ($.trim($('#username').val()).length == 0 ){
            msg = "用户名不能为空。";
        } else if ($.trim($('#password').val()).length == 0 ){
            msg = "密码不能为空。";
        }

//        console.log(msg);
        if (msg && msg.length > 0) {
            $('#msg').fadeOut().text(msg).fadeIn();
            return false;
            // Can't request the login ticket.
        } else if ($('#loginTicket').val().length == 0){
            $('#msg').text('服务器正忙，请稍后再试..');
            return false;
        } else {
            flushLoginTicket();
//            console.log("验证成功开始创建iframe...");
            $('body').append($('<iframe/>').attr({
                style: "display:none;width:0;height:0",
                id: "ssoLoginFrame",
                name: "ssoLoginFrame",
                src: "javascript:false;"
            }));
//            console.log($('#fm1').serialize());
//            $.ajax({
//                type: "POST",
//                url: "http://cas-server:8488/cas/login",
//                data:$('#fm1').serialize(),
//                success: function(msg){
//                }
//            });

//            $.post('http://cas-server:8488/cas/login', $("#fm1").serialize(), function(data) {
//                console.log(data);
//            });

//            return false;
            return true;
        }
    }

    // 登录处理回调函数，将由 iframe 中的页同自动回调
    var feedBackUrlCallBack = function (result) {
        console.log("feedBackUrlCallBack...");
        customLoginCallBack(result);
        deleteIFrame('#ssoLoginFrame');// 删除用完的iframe,但是一定不要在回调前删除，Firefox可能有问题的
    };

    // 自定义登录回调逻辑
    var customLoginCallBack = function(result){
        console.log("customLoginCallBack...");
        console.log(result);
        // 登录失败，显示错误信息
        if (result.login == 'fails'){
            $('#msg').fadeOut().text(result.msg).fadeIn();
            // 重新刷新 login ticket
            flushLoginTicket();
        }
    }

    var deleteIFrame = function (iframeName) {
        var iframe = $(iframeName);
        if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求
            iframe.remove()
        }
    };

    // 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
    var flushLoginTicket = function(){
        var _services = 'service=' + encodeURIComponent('http://cas-client:8080');
//        $.getJSON('http://cas-server:8488/cas/login?'+_services+'&get-lt=true&n='+ new Date().getTime(),
//            function(data){
//            console.log(data);
//                // 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。
////                $('#loginTicket').val(_loginTicket);
//            });

        $.getJSON('http://cas-server:8488/cas/login?'+_services+'&get-lt=true&n='+ new Date().getTime()+ "&callback=?",
            function(response) {
                console.log(response);
                $('#loginTicket').val(response.lt);
                $('#flowExecutionKey').val(response.execution);
            });
    }
</script>
<!-- ================== END BASE JS ================== -->

</body>
</html>
