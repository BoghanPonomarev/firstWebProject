<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Execute</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf"%>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <c:if test="${requestScope.errors!=null}">
            <div role="alert" class="alert alert-danger">
                <fmt:message key="payment.execute.exception"/><br>
                <c:forEach items="${requestScope.errors}" var="error">
                    ${error}<br>
                </c:forEach>

                <a href="${contextPath}/payments" class="alert-link"> <fmt:message key="payment.execute.return"/></a>
            </div>
        </c:if>
        <c:if test="${requestScope.errors==null}">
            <div role="alert" class="alert alert-success">
                <fmt:message key="payment.execute.successFirst"/> <br>
                <fmt:message key="payment.execute.successSecond"/><br>
                <a href="${contextPath}/payments" class="alert-link"><fmt:message key="payment.execute.return"/></a>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
