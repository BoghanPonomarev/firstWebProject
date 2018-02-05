<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Email</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <c:if test="${error==null}">
        <div role="alert" class="alert alert-success"><h4 class="alert-heading"><fmt:message key="payment.wellDone"/></h4>
            <p><fmt:message key="user.email.confirm"/></p>
        </div>
        </c:if>
        <c:if test="${error!=null}">
            <div role="alert" class="alert alert-danger"><h4 class="alert-heading"><fmt:message key="payment.wellDone"/></h4>
                <p><fmt:message key="user.email.confirm.error"/></p>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>