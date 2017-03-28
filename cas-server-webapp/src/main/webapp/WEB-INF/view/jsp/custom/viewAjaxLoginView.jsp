<%--<%--%>
    <%--Boolean isFrame = (Boolean)request.getAttribute("isFrame");--%>
    <%--Boolean isLogin = (Boolean)request.getAttribute("isLogin");--%>
<%--//    String callbackName = request.getParameter("callback");--%>
    <%--String callbackName = "feedBackUrlCallBack";--%>
    <%--String msg = "用户名或密码错误！";--%>
    <%--String jsonData = "{'login':"+isLogin+", 'msg': "+msg+"}";--%>
    <%--String jsonp = callbackName + "(" + jsonData + ")";--%>

    <%--response.setContentType("application/javascript");--%>
    <%--response.getWriter().write(jsonp);--%>
<%--%>--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>正在登录....</title>
</head>
<body>
<script type="text/javascript">
<%
    Boolean isFrame = (Boolean)request.getAttribute("isFrame");
    Boolean isLogin = (Boolean)request.getAttribute("isLogin");
    if(isLogin){
        if(isFrame){%>
            parent.location.replace('${service}?ticket=${ticket}')
        <%} else{%>
            location.replace('${service}?ticket=${ticket}')
        <%}
    }
%>
${callback}({'login':${isLogin ? '"success"': '"fails"'}, 'msg': ${isLogin ? '""': '"用户名或密码错误！"'}})
</script>
</body>
</html>