<
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Execute</title>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <c:if test="${requestScope.errors!=null}">
            <div role="alert" class="alert alert-danger">
                It seems there was some mistake, maybe it's..<br>
                <c:forEach items="${requestScope.errors}" var="error">
                    ${error}<br>
                </c:forEach>

                <a href="${contextPath}/payments/show_all" class="alert-link">Return to my payments</a>
            </div>
        </c:if>
        <c:if test="${requestScope.errors==null}">
            <div role="alert" class="alert alert-success">
                It seems that you just paid something very significant for you,<br>
                we are glad that you did it with our website.<br>
                <a href="${contextPath}/payments/show_all" class="alert-link">Return to my payments</a>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
