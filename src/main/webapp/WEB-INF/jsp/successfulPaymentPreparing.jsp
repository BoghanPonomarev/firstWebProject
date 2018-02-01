<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Payment</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<input value="${contextPath}" style="display: none" disabled id="path">
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
            <div role="alert" class="alert alert-success"><h4 class="alert-heading"><fmt:message key="payment.wellDone"/></h4>
                <p><fmt:message key="payment.successPreparing"/></p>
                <fmt:message key="payment.chack.data"/> : <br>
                <fmt:message key="payment.chack.data.payment"/> №${requestScope.id}<br>
                <fmt:message key="payment.chack.data.recipient"/>- ${requestScope.recipient}<br>
                <fmt:message key="payment.chack.data.amount"/> - ${requestScope.amount} ${requestScope.currency}
                <hr>
                <p class="mb-0">
                    <a href="${contextPath}/payments" role="button" class="btn btn-warning"><fmt:message key="payment.postpone"/></a>
                    <a href="${contextPath}/payments/execute?payment_id=${requestScope.id}" role="button" class="btn btn-primary"><fmt:message key="payment.execute"/></a>
                    <a href="${contextPath}/payments/delete?payment_id=${requestScope.id}" role="button" class="btn btn-danger"><fmt:message key="payment.delete"/></a>
                </p>
            </div>
        </div>

</div>
</body>
</html>
