<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="#" onclick="login()">点这测试</a>
<input id="loginTicket" type="hidden" name="lt" value="${loginTicket}"/>
<input id="flowExecutionKey" type="hidden" name="execution" value="${flowExecutionKey}"/>
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script>
    var loginUrl = 'http://localhost:8488/cas/login';
    var logoutUrl = 'http://localhost:8488/cas/logout';
    var clientUrl="service=http%3A%2F%2Flocalhost%3A8080%2Fclient1%2F";
    clientUrl=getParameter(clientUrl,"service")
    console.log(clientUrl);

    function login(){
        $.ajax({
            url: loginUrl,
            type: 'GET',
            data:{},
            dataType: 'html',
            timeout: 3000,
            async:false,
            error: function(e){console.log("验证登录失败",e);alert('系统错误');},
            success: function(result){
                //如果用户本来就是登录着的,可以选择让用户直接跳转,还有个更妥当方法是注销用户,然后再掉login重新获取
                if(isSuccesss(result)){
                    // window.location.href="http://localhost/test";
                    logout();
                }

                flushLoginTicket(post);
//                flushLoginTicket();
            }
        });
    }

    function flushLoginTicket(callback){
//        var _services = 'service=' + encodeURIComponent(clientUrl);
//        $.getJSON(loginUrl + '?'+_services+'&get-lt=true&n='+ new Date().getTime()+ "&callback=?",function(response) {
        $.getJSON(loginUrl + '?get-lt=true&n='+ new Date().getTime()+ "&callback=?",function(response) {

                console.log(response);
            $('#loginTicket').val(response.lt);
            $('#flowExecutionKey').val(response.execution);

            if(callback){
                callback();
            }
        });
    }

    function logout(){
        $.ajax({
            url: logoutUrl,
            type: 'GET',
            dataType: 'html',
            timeout: 1000,
            async:false,
            error: function(){},
            success: function(result){
                console.log("logout success...");
            }
        });
    }

    function post(){
        var lt=$('#loginTicket').val();
        var ev=$('#flowExecutionKey').val();
//        console.log(lt,ev);
        $.ajax({
            url: loginUrl,
            type: 'POST',
            async:true,
            data:{"execution":ev,"lt":lt,"password":"1","submit":"LOGIN","username":"admin","_eventId":"submit"},
//            dataType: 'jsonp',
            timeout: 3000,
            error: function(r){
              console.error("登录失败",r);
            },
            success: function(result){
                console.log("post success..");
                //如果登陆成功,则跳转,否则提示错误
                if(isSuccesss(result)){
                    alert('登录成功');
                    window.location.href = clientUrl;
                }else{
                    alert("登录失败");
                }
            }
        });
    }

    function isSuccesss(res) {
        return "success"==res;
    }

    function getParameter(uri,name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
//        var r = window.location.search.substr(1).match(reg);
        var r = uri.match(reg);
        if(r!=null){
            return decodeURIComponent(r[2]); return null;
        }
    }
</script>
</body>
</html>
