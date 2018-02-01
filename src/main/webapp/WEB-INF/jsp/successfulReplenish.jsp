<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Success</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <div role="alert" class="alert alert-success">
            <fmt:message key="replenish.success"/>
        </div>
    </div>
</div>
</body>
</html>
