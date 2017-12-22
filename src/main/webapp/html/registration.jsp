<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 18.12.2017
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
</head>
<body>
<h2>Registration</h2>
<c:forEach items="${errors}" var="error">
    <c:out value="${error.value}"/><br>
</c:forEach>
<div>
    <form method="post" action="/app/registration">
        name <input type="text" name="login" ><br><br>
        phone<input type="text" name="phone" ><br><br>
        email<input type="text" name="email" ><br><br>
        password <input type="password" name="password" ><br><br>
        password <input type="password" name="secondPassword" ><br><br>
        <input type="submit" name="sub">
    </form>
</div>
</body>
</html>
