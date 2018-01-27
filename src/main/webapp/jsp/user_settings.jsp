<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Setting</title>
    <script src="${contextPath}/js/set_user_data.js"></script>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <c:set var="emailButton" value="${'Change'}"/>
        <c:set var="fullNameButton" value="${'Bind it!'}"/>
        <c:if test="${requestScope.email!=null}">
            Your email: ${requestScope.email}<br>
        </c:if>
        <c:if test="${requestScope.email==null}">
            You have no email
            <c:set var="emailButton" value="Bind it!"/>
        </c:if>
        <button id="edit_email" onclick="show('email_change','edit_email')">${pageScope.emailButton}</button>
        <br>
        <div id="email_change" style="display: none">
            <span id="email_error">Email is incorrect</span>
            Email : <input type="text" id="email"><br>
            <button value="Send" id="send_email" onclick="sendEmail('email','email_error')"></button>
            <button value="Cancel" onclick="cancel('email_change','edit_email')"></button>
        </div>
        <c:if test="${requestScope.fullName!=null}">
            Your full name: ${requestScope.email}<br>
        </c:if>
        <c:if test="${requestScope.fullName==null}">
            You have no your full name on our site
            <c:set var="fullNameButton" value="Bind it!"/>
        </c:if>
        <button id="edit_full_name"
                onclick="show('full_name_change','edit_full_name')">${pageScope.fullNameButton}</button>
        <br>
        <div id="full_name_change" style="display: none">
            First name : <input type="text" id="first_name"><br>
            <span name="First name" id="first_name_error"></span>
            First name : <input type="text" id="second_name"><br>
            <span name="Second name" id="second_name_error"></span>
            First name : <input type="text" id="third_name"><br>
            <span name="Third name" id="third_name_error"></span>
            <button value="Send" id="send_full_name" onclick="sendNames('first_name',
    'second_name','third_name','first_name_error','second_name_error','third_name_error')"/>
            <button value="Cancel" onclick="cancel('email_change','edit_email')"></button>
        </div>
    </div>
</div>
</body>
</html>
