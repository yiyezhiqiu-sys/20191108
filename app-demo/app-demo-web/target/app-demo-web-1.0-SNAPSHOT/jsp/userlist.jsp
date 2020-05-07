<%--
  Created by IntelliJ IDEA.
  User: houbin
  Date: 2020/1/11
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="C" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
<table>
    <tr>
        <td>id</td>
        <td>名称</td>
    </tr>
    <c:forEach items="${list }" var="user">
        <tr>
            <td>${user.userid }</td>
            <td>${user.username }</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
