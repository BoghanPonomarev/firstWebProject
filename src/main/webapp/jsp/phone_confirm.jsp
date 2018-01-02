<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Pin confirm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="${contextPath}/js/phone_confirm_script.js"></script>
</head>
<body>
    <span>Pin code</span>
    <form class="auth-forms" method="post" action="${contextPath}/phone_confirm">
        Phone number: <input type="text" id="phone_number" name="phone_number" value="${requestScope.get('phone_number')}" readonly><br>
        Pin code: <input type="text" id="pin_code" name="pin_code" maxlength="5" required >
        <input type="submit" value="submit">
    </form>
</body>
