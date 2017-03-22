<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="<c:url value="jquery/jquery-3.1.0.js"/>"></script>
    <script>
        $(function () {
            $("#test_form").submit(function () {
                var str = $("#txt").val();                  //取出textarea的值
                str = str.replace(/\r\n/g,"&#13;&#10;");    //替换
                str = str.replace(/\n/g,"&#13;&#10;");
                $("#txt").text(str);                        //再把替换后的值设置回去
                return true;                                //提交
            });
        });
    </script>
</head>
<body>
    <h1>Hello World</h1>
<a href="http://localhost:8080/CampusHelper/account/sendVerifyCode/1083276704@qq.com">点我</a>

</body>
</html>
