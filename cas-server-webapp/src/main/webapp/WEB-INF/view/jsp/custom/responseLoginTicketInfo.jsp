<%@ page contentType="text/html; charset=UTF-8"%>
<%--<%out.print("flightHandler({'lt':'");%>${loginTicket}<%out.print("','execution':'");%>${flowExecutionKey}<%out.print("'});");%>--%>
<%--<%out.print("{'lt':'");%>${loginTicket}<%out.print("','execution':'");%>${flowExecutionKey}<%out.print("'}");%>--%>
<%
String callbackName = request.getParameter("callback");
String jsonData = "{\"lt\":\"" + request.getAttribute("loginTicket") + "\", \"execution\":\"" + request.getAttribute("flowExecutionKey") + "\"}";
String jsonp = callbackName + "(" + jsonData + ")";

response.setContentType("application/javascript");
response.getWriter().write(jsonp);
%>