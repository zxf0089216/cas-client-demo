<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2017/3/27 0027
  Time: 20:04
  参考:http://www.coin163.com/it/x308546869005816687/cas-cas-ajax-cas4.0
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="#" onclick="login()">点这测试</a>
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/jquery-1.9.1.js" />"></script>
<script>
    function login(){
        $.ajax({
            url: 'http://localhost/cas/login',
            type: 'GET',
            data:{Name:"keyun"},
            dataType: 'html',
            timeout: 1000,
            async:false,
            error: function(){alert('Error');},
            success: function(result){
                //如果用户本来就是登录着的,可以选择让用户直接跳转,还有个更妥当方法是注销用户,然后再掉login重新获取
//                if(result.indexOf("Log In Successful")!=-1){
                if(result.indexOf("登录成功")!=-1){
                    // window.location.href="http://localhost/test";
                    logout();
                    login();
                    return;
                }

                var ltsl=result.indexOf("\"LT"); //截取LT位置
                var lt=result.substring(ltsl+1);
                lt=lt.substring(0,lt.indexOf("\""));

                var evsl=result.indexOf("execution\" value=\""); //截取execution位置

                var ev=result.substring(evsl+18);
                ev=ev.substring(0,ev.indexOf("\""));
                post(lt,ev);
            }
        });
    }

    function logout(){
        $.ajax({
            url: 'http://localhost/cas/logout',
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

    //提交请求
    function post(lt,ev){
        $.ajax({
            url: 'http://localhost/cas/login',
            type: 'POST',
            async:false,
            data:{"execution":ev,"lt":lt,"password":"1","submit":"LOGIN","username":"admin","_eventId":"submit"},
            //dataType: 'json',
            timeout: 1000,
            error: function(r){
                debugger;
                alert('Error');
            },
            success: function(result){
                //如果登陆成功,则跳转,否则提示错误
                if(result.indexOf("登录成功")!=-1){
                    alert('ok   lt:'+lt);
//                    window.location.href="http://localhost/test";
                }
            }
        });
    }
</script>
</body>
</html>
