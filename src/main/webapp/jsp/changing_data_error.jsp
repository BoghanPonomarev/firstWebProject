<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Exception</title>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<a href="${contextPath}/accounts/show_accounts">Accounts</a>
<a href="${contextPath}/user/settings">Settings</a>
<a href="${contextPath}/user/show_profile">Profile</a>

</body>
</html>
