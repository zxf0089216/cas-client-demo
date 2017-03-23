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
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>

    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="<c:url value="/assets/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"/>
    <link href="<c:url value="/assets/css/login.css" />" rel="stylesheet"/>
    <!-- ================== END BASE CSS STYLE ================== -->

    <style type="text/css">
        .errors{
            /*border: 1px dotted #BB0000;*/
            color: #BB0000;
            padding-left: 110px;
            /*background: url(../images/error.gif) no-repeat 20px center;*/
        }
        .error{
            color: #BB0000;
            font-weight: normal;
        }
    </style>
</head>
<body>
<!-- begin #page-container -->
<div id="page-container">

    <!-- begin login -->
    <div class="login">
        <form:form method="post" id="fm1" cssClass="form-horizontal" commandName="${commandName}" htmlEscape="true">
            <fieldset>
                <legend>ARES Cloud</legend>
                <div class="form-group">
                    <div class="col-md-12">
                        <form:errors path="*" cssClass="errors" id="msg"/>
                        <%--<form:errors path="*" id="msg" cssClass="errors" element="div"/>--%>
                            <%--<span class="red" style="height:12px;" id="msg">--%>
                                <%--<form:errors path="*" />--%>
                            <%--</span>--%>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label"><spring:message code="screen.welcome.label.password"/></label>
                    <div class="col-md-7">
                        <c:if test="${not empty sessionScope.openIdLocalId}">
                            <strong>${sessionScope.openIdLocalId}</strong>
                            <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
                        </c:if>
                        <c:if test="${empty sessionScope.openIdLocalId}">
                            <form:input cssClass="form-control" cssErrorClass="error" id="username" size="25" tabindex="1"
                                        path="username" autocomplete="false" htmlEscape="true" placeholder="输入用户名"/>
                        </c:if>

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><spring:message code="screen.welcome.label.password"/></label>
                    <div class="col-md-7">
                        <form:password cssClass="required form-control" cssErrorClass="error" id="password" size="25" tabindex="2"
                                       path="password" htmlEscape="true" autocomplete="off" placeholder="输入密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-3">
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
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.validate-1.13.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/login.js" />"></script>
<!-- ================== END BASE JS ================== -->

</body>
</html>
