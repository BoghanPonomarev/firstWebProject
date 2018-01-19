<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<c:if test="${requestScope.users!=null}">
    <c:forEach items="${requestScope.users}" var="user">
        user id: ${user.key.id}<br>
        user email: ${user.key.email}<br>
        user phone number: ${user.key.phoneNumber}<br>
        user role: ${user.key.role}<br>
        user banned: ${user.key.banned}<br>
        <a href="${contextPath}/admin/ban_user?user_id=${user.key.id}&ban_status=${user.key.banned}">set banned value</a><br>
        <c:if test="${user.value!=null}">
            <c:forEach items="${user.value}" var="account">
                <table border="1">
                    <tr><td>account id :</td><td>${account.id}</td></tr><br>
                <tr><td>account status :</td><td>${account.banned}</td></tr><br>
                </table>
                <a href="${contextPath}/admin/ban_account?account_id=${account.id}&ban_status=${account.banned}">set banned value of account</a><br>
            </c:forEach>
        </c:if>
        <hr>
    </c:forEach>
</c:if>
</body>
</html>
