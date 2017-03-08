<%
	// handle CAS logout request from the browser by destroying the web session
	session.invalidate();
%>
<a href="/protected/index.jsp">Call the /protected/index.jsp page</a>