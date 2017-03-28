<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipalImpl"%>
<!DOCTYPE html>
<html>
<head>
	<title>java-jasig-cas-client-demo</title>
	<meta charset="UTF-8" />
	<link href="${pageContext.request.contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs">
			<li class="active"><a href="/index.jsp">You are on the /index.jsp page</a></li>
			<li><a href="/protected/index.jsp">Call the /protected/index.jsp page</a></li>
			<li><a href="${pageContext.request.contextPath}/servlet/ServletDemo1">Call the CAS logout</a></li>
			<%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http%3A%2F%2Fcas-client%3A8080%2F">Call the CAS logout</a></li>--%>
			<%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http%3A%2F%2Fcas-client%3A8080%2F">Call the CAS logout</a></li>--%>
			<%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login?service=http://cas-client:8080">Call the CAS logout</a></li>--%>
			<%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-server:8488/cas/login">Call the CAS logout</a></li>--%>
			<%--<li><a href="http://cas-server:8488/logout?service=http://cas-client:8080/protected/index.jsp">Call the CAS logout</a></li>--%>
			<%--<li><a href="http://cas-server:8488/cas/logout?service=http://cas-client:8080/index.jsp">Call the CAS logout</a></li>--%>
			<h3>
				<% AttributePrincipalImpl user = (AttributePrincipalImpl) request.getUserPrincipal();if (user != null) { %><br>
				<p>User: <%=user.getName()%></p>
				<p>Attributes: <%=user.getAttributes()%></p>
				<% } else { %>
				<p>Unauthenticated / anonymous user</p>
				<% } %>
			</h3>
		</ul>
	</div>
</body>
</html>
