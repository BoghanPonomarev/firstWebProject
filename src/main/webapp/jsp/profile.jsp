<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <c:forEach items="${requestScope.parameters}" var="parametr">
            <br> ${parametr.key} : ${parametr.value}
        </c:forEach>
    </div>
</div>
</body>
</html>
