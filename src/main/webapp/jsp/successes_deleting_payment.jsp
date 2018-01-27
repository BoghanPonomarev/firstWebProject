<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Congratulations</title>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"/>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <div role="alert" class="alert alert-success">
            Congratulations, you successful deleted your payment <br>
            Now , we suggest you
            <a href="${contextPath}/payments/show_all" class="alert-link">look at your other payments.</a>
        </div>
    </div>
</div>
</body>
</html>
