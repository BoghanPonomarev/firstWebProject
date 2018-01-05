
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 18.12.2017
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="utf-8">
    <title>Registration</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <link rel="stylesheet" href="${contextPath}/css/formhack.css">
</head>
<body>
<c:forEach items="${errors}" var="error">
    <c:out value="${error.value}"/><br>
</c:forEach>
<div class="container">
    <form class="registration" id="registration" method="post" action="${contextPath}/registration">
        <h1>Registration Form</h1>

        <label for="login">
            <span>Username</span>

            <input type="text" id="login" name="login" minlength="5" required>

            <ul class="input-requirements">
                <li>At least 5 characters long (and less than 15 characters)</li>
                <li>Must only contain letters and numbers (no special characters)</li>
            </ul>
        </label>

        <label for="email">
            <span>Email</span>

            <input type="text" id="email" name="email" maxlength="40" required>

            <ul class="input-requirements">
                <li>Email must have correct format</li>
            </ul>
        </label>

        <label for="phone">
            <span>Phone number</span>

            <input type="text" id="phone" name="phone_number" maxlength="20" required>

            <ul class="input-requirements">
                <li>Phone must have correct format</li>
            </ul>
        </label>

        <label for="password">
            <span>Password</span>

            <input type="password" id="password" name="password" maxlength="15" minlength="5" required>

            <ul class="input-requirements">
                <li>At least 5 characters long (and less than 15 characters)</li>
                <li>Password must consist only letters,numbers and some special characters(-_)</li>
            </ul>
        </label>

        <label for="password_repeat">
            <span>Repeat Password</span>
            <input type="password" id="password_repeat" name="password_repeat" maxlength="15" minlength="5" required>
            <ul class="input-requirements">
                <li>Passwords must be equal</li>
            </ul>
        </label>

        <br>

        <input type="submit">

    </form>
</div>

<script src="${contextPath}/js/reg_validation_script.js"></script>
</body>
