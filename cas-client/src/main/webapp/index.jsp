<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipalImpl" %>
<!DOCTYPE html>
<html>
<head>
    <title>java-jasig-cas-client-demo</title>
    <meta charset="UTF-8"/>
    <link href="${pageContext.request.contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/jquery-1.4.2.min.js"></script>
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs">
        <li class="active"><a href="/index.jsp">You are on the /index.jsp page</a></li>
        <li><a href="/protected/index.jsp">Call the /protected/index.jsp page</a></li>
        <li><a href="javascript:test1();">test ajax</a></li>
        <li><a href="${pageContext.request.contextPath}/servlet/ServletDemo1">Call the CAS logout</a></li>
        <%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http%3A%2F%2Fcas-client%3A8080%2F">Call the CAS logout</a></li>--%>
        <%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http%3A%2F%2Fcas-client%3A8080%2F">Call the CAS logout</a></li>--%>
        <%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http://cas-client:8080">Call the CAS logout</a></li>--%>
        <%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login">Call the CAS logout</a></li>--%>
        <%--<li><a href="http://cas-server:8488/logout?service=http://cas-client:8080/protected/index.jsp">Call the CAS logout</a></li>--%>
        <%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-client:8080/index.jsp">Call the CAS logout</a></li>--%>
        <h3>
            <% AttributePrincipalImpl user = (AttributePrincipalImpl) request.getUserPrincipal();
                if (user != null) { %><br>
            <p>User: <%=user.getName()%>
            </p>
            <p>Attributes: <%=user.getAttributes()%>
            </p>
            <% } else { %>
            <p>Unauthenticated / anonymous user</p>
            <% } %>
        </h3>
    </ul>
</div>

<script>
    function test1() {
        $.ajax({
            url: '/servlet/OAuthServlet',
            type: 'POST', //GET
            async: true,    //或false,是否异步
            data: {
                name: 'yang', age: 25
            },
//                timeout:5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {
                console.log('发送前', xhr)
            },
            success: function (data, textStatus, jqXHR) {
                console.log('成功')
                console.log(data)
                console.log(textStatus)
                console.log(jqXHR)
                if (data && data.error_no==400) {
                    window.location.href=data.location;
                }
            },
            error: function (xhr, textStatus) {
                console.log('错误')
                console.log(xhr)
                console.log(textStatus)
            },
            complete: function () {
//                    console.log('结束');
            }
        })
    }
</script>
</body>
</html>
