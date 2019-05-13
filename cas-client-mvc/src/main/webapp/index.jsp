<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipalImpl" %>
<!DOCTYPE html>
<html>
<head>
    <title>java-jasig-cas-client-demo</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/jquery.js"></script>
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs">
        <li class="active"><a href="/index.jsp">You are on the /index.jsp page</a></li>
        <li><a href="javascript:" id="hello">call ajax</a></li>
        <li><a href="javascript:" id="logout">logout</a></li>
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

<script type="text/javascript">
    $(document).ready(function () {
        $("#hello").click(function () {
            $.ajax({
                type: "GET",
                url: " http://localhost:18080/hello",
                dataType: "json",
                success: function (data) {
                    alert("success");
                },
                error: function (jqXHR) {
                    console.log("Error: " + jqXHR.status);
                }
            });
        });

        $("#logout").click(function () {
            $.ajax({
                type: "GET",
                url: " http://localhost:18080/logout",
                dataType: "json",
                success: function (data) {
                    alert("success");
                },
                error: function (jqXHR) {
                    console.log("Error: " + jqXHR.status);
                }
            });
        });
    });
</script>
</body>
</html>
