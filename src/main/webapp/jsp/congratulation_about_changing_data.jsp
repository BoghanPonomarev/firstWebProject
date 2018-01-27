<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Congratulation</title>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<a href="${contextPath}/accounts/show_accounts">Accounts</a>
<a href="${contextPath}/user/settings">Settings</a>
<a href="${contextPath}/user/show_profile">Profile</a>
<div>
    Congratulation , you successfully change your ${requestScope.variable}...
    <c:if test="${requestScope.variable eq 'email'}">
        Now we send the letter to your email, you can confirm your email right now
    </c:if>
</div>
</body>
</html>
