<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:theme code="mobile.custom.css.file" var="mobileCss" text=""/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>统一登录 | Dashboard</title>
    <spring:theme code="standard.custom.css.file" var="customCssFile"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="<c:url value="/assets/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" />
    <link href="<c:url value="/assets/css/login.css" />" rel="stylesheet" />
    <!-- ================== END BASE CSS STYLE ================== -->
    <link type="text/css" rel="stylesheet" href="<c:url value="${customCssFile}" />"/>
    <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon"/>
</head>
<body id="cas" class="fl-theme-iphone">
<div class="flc-screenNavigator-view-container">
    <div class="fl-screenNavigator-view">
        <div id="content" class="fl-screenNavigator-scroll-container">

            <div class="box fl-panel" id="login">
                <form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
                    <form:errors path="*" id="msg" cssClass="errors" element="div"/>
                    <%--<h2><spring:message code="screen.welcome.instructions"/></h2>--%>
                    <h2>${sessionScope.openIdLocalId}</h2>
                    <div class="row fl-controls-left">
                        <label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid"/></label>

                        <c:if test="${not empty sessionScope.openIdLocalId}">
                            <%--<h2>not empty sessionScope.openIdLocalId</h2>--%>
                            <strong>${sessionScope.openIdLocalId}</strong>
                            <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}"/>
                        </c:if>

                        <c:if test="${empty sessionScope.openIdLocalId}">
                            <%--<h2>empty sessionScope.openIdLocalId</h2>--%>
                            <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey"/>
                            <form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1"
                                        accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true"/>
                        </c:if>
                    </div>
                    <div class="row fl-controls-left">
                        <label for="password" class="fl-label"><spring:message
                                code="screen.welcome.label.password"/></label>
                        <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey"/>
                        <form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2"
                                       path="password" accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off"/>
                    </div>
                    <div class="row btn-row">
                        <input type="hidden" name="lt" value="${loginTicket}"/>
                        <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                        <input type="hidden" name="_eventId" value="submit"/>

                        <input class="btn-submit" name="submit" accesskey="l"
                               value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit"/>
                        <input class="btn-reset" name="reset" accesskey="c"
                               value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset"/>
                    </div>
                </form:form>
            </div>

        </div>
    </div>
</div>
</body>
</html>
