<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Registration</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700' rel='stylesheet' type='text/css'>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/formhack.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<c:forEach items="${requestScope.errors}" var="error">
    <c:out value="${error}"/><br>
</c:forEach>
<div class="container" id="reg_block">
    <form class="registration" id="registration" method="post" action="${contextPath}/registration/check">
        <h1>Registration Form</h1>
        <label for="phone_number">
            <span>Phone number</span>

            <input type="text" id="phone_number" name="phone_number" maxlength="15" required>

            <ul class="input-requirements">
                <li  id="phone_massage1">Phone is icorectt</li>
                <li id="phone_massage2">Phone has already registered</li>
            </ul>
        </label>

        <label for="password">
            <span>Password</span>

            <input type="password" id="password" name="password" maxlength="15" minlength="5" required>

            <ul class="input-requirements">
                <li id="password_massage1">At least 5 characters long (and less than 15 characters)</li>
                <li id="password_massage2">Password must consist only letters,numbers and some special characters(-_)</li>
            </ul>
        </label>

        <label for="password_repeat">
            <span>Repeat Password</span>
            <input type="password" id="password_repeat" name="password_repeat" maxlength="15" minlength="5" required>
            <ul class="input-requirements">
                <li id="password_massage3" style="display: none">Passwords must be equal</li>
            </ul>
        </label>

        <br>

        <input type="button" onclick="validate()"value="submit" id="commit">

    </form>
</div>
<div style="display: none" id="pin_code_block">
    <form class="auth-forms" method="post" action="${contextPath}/registration/send_code">
        <span id="pin_code_error" style="display: none">Pin code is wrong</span>
        <span  id="repeat_phone"></span><br>
        Pin code: <input type="text" id="pin_code" name="pin_code" maxlength="5" required >
        <input type="button" value="Submit" onclick="checkCode()">
        <input type="button" value="Change number" onclick="changeForm()">
    </form>
</div>

<script src="${contextPath}/js/registration_validation.js"></script>
<script src="${contextPath}/js/pin_code.js"></script>
</body>
