<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>java-jasig-cas-client-demo</title>
	<meta charset="UTF-8" />
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs">
			<li class="active"><a href="/index.jsp">You are on the /index.jsp page</a></li>
			<li><a href="/protected/index.jsp">Call the /protected/index.jsp page</a></li>
			<li><a href="/servlet/ServletDemo1">Call the CAS logout</a></li>
			<li><a href="http://cas-client1:9080/servlet/ServletDemo1">test the proxy</a></li>
		</ul>
	</div>
</body>
</html>
