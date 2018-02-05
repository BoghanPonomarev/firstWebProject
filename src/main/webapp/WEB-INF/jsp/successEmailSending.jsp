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
        <div role="alert" class="alert alert-success"><h4 class="alert-heading"><fmt:message key="payment.wellDone"/></h4>
            <p><fmt:message key="user.email.send"/></p>
        </div>

    </div>
</div>
</body>
</html>
