<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<c:forEach items="${errors}" var="error">
    <c:out value="${error.value}"/><br>
</c:forEach>
<form action="${contextPath}/authorization" method="post">
   phone number:
<input name="phone_number" maxlength="15"><br>
    password:
    <input name="password" maxlength="15"><br>
    <input type="submit" value="submit">
</form>
</body>
</html>
