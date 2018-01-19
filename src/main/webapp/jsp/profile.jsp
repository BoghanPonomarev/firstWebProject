<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<a href="${contextPath}/accounts/show_accounts">Accounts</a>
<c:forEach items="${requestScope.parameters}" var="parametr">
   <br> ${parametr.key} : ${parametr.value}
</c:forEach>
</body>
</html>
