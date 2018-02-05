<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Setting</title>
    <script src="${contextPath}/js/bindEmail.js"></script>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <c:set var="emailButton" value="${'Change'}"/>
        <c:set var="fullNameButton" value="${'Bind it!'}"/>
        <c:if test="${requestScope.email!=null}">
            Your email: ${requestScope.email}<br>
            <c:set var="emailButton" value="Change"/>
        </c:if>
        <c:if test="${requestScope.email==null}">
            You have no email
            <c:set var="emailButton" value="Bind it!"/>
        </c:if>
        <button id="edit_email" onclick="show('email_change','edit_email')">${pageScope.emailButton}</button>
        <br>
        <form id="email_change" >
            <div id="email_error" style="display: none;color: red">Email is incorrect</div>
            <div id="email_error2" style="display: none;color: red">Email is almost exist</div>
            Email : <input type="text" name="email" id="email"><br>
        </form>
        <button style="width: 50px;height: 30px" value="Send" id="send_email" onclick="sendEmail('email','email_error')">Send</button>
        <button value="Cancel" id="Cancel" onclick="cancel('email_change','edit_email')">Cancel</button>
    </div>
</div>
</body>
</html>
