<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Payment</title>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<input value="${contextPath}" style="display: none" disabled id="path">
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"/>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
            <div role="alert" class="alert alert-success"><h4 class="alert-heading">Well done!</h4>
                <p>We recorded your operation and it is ready for processing, what will you do next?</p>
                Check the data: <br>
                Payment №${requestScope.id}<br>
                recipient identification - ${requestScope.recipient}<br>
                amount - ${requestScope.amount} ${requestScope.currency}
                <hr>
                <p class="mb-0">
                    <a href="${contextPath}/payments/show_all" role="button" class="btn btn-warning">Postpone</a>
                    <a href="${contextPath}/payments/execute?payment_id=${requestScope.id}" role="button" class="btn btn-primary">Execute</a>
                    <a href="${contextPath}/payments/delete?payment_id=${requestScope.id}" role="button" class="btn btn-danger">Delete</a>
                </p>
            </div>
        </div>

</div>
</body>
</html>
