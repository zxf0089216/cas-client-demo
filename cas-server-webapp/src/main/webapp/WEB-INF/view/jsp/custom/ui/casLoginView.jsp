<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if IE 8]> <html xmlns="http://www.w3.org/1999/xhtml" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统一登录 | Dashboard</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>

    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="<c:url value="/assets/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"/>
    <link href="<c:url value="/assets/css/login.css" />" rel="stylesheet"/>
    <!-- ================== END BASE CSS STYLE ================== -->

    <style type="text/css">
        .errors{
            padding-left: 70px;
        }
        #password-error,#username-error,.errors{
            color: #BB0000;
            font-weight: normal;
        }
    </style>
</head>
<body style="overflow-y: hidden">
<!-- begin #page-container -->
<div id="page-container">
    <div class="login">
        <div class="news-feed">
            <div class="news-image">
                <img class="img-title" src="assets/img/title.png">
                <img class="img-square" src="assets/img/square.png">
                <img class="img-trangle" src="assets/img/trangle.png">
                <img class="img-title-s"  src="assets/img/title-s.png">
                <img class="img-square-s"  src="assets/img/square-s.png">
                <img class="img-trangle-s"  src="assets/img/trangle-s.png">
            </div>
        </div>
        <div class="right-content">
            <div class="login-header text-center">
                <img src="assets/img/ares-login-logo.png">
                <h3>ARES CLOUD 控制台</h3>
            </div>
            <div class="login-content">
                <form:form method="post" id="fm1" cssClass="margin-bottom-0" commandName="${commandName}" htmlEscape="true">
                    <div class="form-group">
                        <div class="col-md-12">
                            <form:errors path="*" cssClass="errors" id="msg"/>
                        </div>
                    </div>
                    <div class="form-group m-b-15">
                        <%--<label><spring:message code="screen.welcome.label.netid" /></label>--%>
                        <label>用户名</label>
                        <c:if test="${not empty sessionScope.openIdLocalId}">
                            <input class="form-control input-lg" type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" required=""/>
                        </c:if>
                        <c:if test="${empty sessionScope.openIdLocalId}">
                            <%--<form:input cssClass="form-control input-lg" cssErrorClass="error" id="username" size="25" tabindex="1"--%>
                                        <%--path="username" autocomplete="false" htmlEscape="true" placeholder="" required=""/>--%>
                            <input name="username" id="username" type="text" class="form-control input-lg" placeholder="" required>
                        </c:if>
                    </div>
                    <div class="form-group m-b-15">
                        <%--<label><spring:message code="screen.welcome.label.password"/></label>--%>
                        <label>密码</label>
                        <input type="password" name="password" id="password" class="form-control input-lg" placeholder="" required>
                        <%--<form:password cssClass="form-control input-lg" cssErrorClass="error" id="password" size="25" tabindex="2"--%>
                                       <%--path="password" htmlEscape="true" autocomplete="off" placeholder="" required=""/>--%>
                    </div>
                    <input id="loginTicket" type="hidden" name="lt" value="${loginTicket}"/>
                    <input id="flowExecutionKey" type="hidden" name="execution" value="${flowExecutionKey}"/>
                    <input type="hidden" name="_eventId" value="submit"/>
                    <button type="submit" class="btn btn-block btn-lg"  tabindex="4">登录</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<!-- end page container -->

<!-- ================== BEGIN BASE JS ================== -->
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.validate-1.13.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/login.js" />"></script>
<!-- ================== END BASE JS ================== -->

</body>
</html>
