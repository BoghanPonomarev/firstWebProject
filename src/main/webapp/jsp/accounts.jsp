<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Accounts</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<a href="${contextPath}/show_profile">Profile</a>
<c:if test="${requestScope.accounts==null}">
   <h3>У вас ни одного аккаунта</h3>
</c:if>
<c:if test="${requestScope.accounts!=null}">
    <h3>Your cards:</h3><br>

<c:forEach items="${requestScope.accounts}" var="account">
<table border="1px">
    <tr>
        <td>
           Account id
        </td>
        <td>
            <c:out value="${account.id}"/>
        </td>
    </tr>
    <tr>
        <td>
            Card number
        </td>
        <td>
            <c:out value="${account.card.cardNumber}"/>
        </td>
    </tr>
    <tr>
        <td>
            Date
        </td>
        <td>
            <c:out value="${account.card.validThru}"/>
        </td>
    </tr>
    <tr>
        <td>
            CVV
        </td>
        <td>
            <c:out value="${account.card.CVV}"/>
        </td>
    </tr>
    <tr>
        <td>
            Amount
        </td>
        <td>
            <c:out value="${account.card.amount}"/>
        </td>
    </tr>
    <tr>
        <td>
            Is banned
        </td>
        <td>
            <c:out value="${account.banned}"/>
        </td>
    </tr>
</table>
    <input type="button" value="delete" onclick="showWindow(${account.id})" id="delete-button"><br>
</c:forEach>
</c:if>
<c:forEach items="${requestScope.errors}" var="error">
    ${error}<br>
</c:forEach>
<form method="post" action="${contextPath}/accounts/add">
    card number: <input name="card_number"><br>
    month: <select name="month">
    <option value="01">01</option>
    <option value="02">02</option>
    <option value="03">03</option>
    <option value="04">04</option>
    <option value="05">05</option>
    <option value="06">06</option>
    <option value="07">07</option>
    <option value="08">08</option>
    <option value="09">09</option>
    <option value="09">10</option>
    <option value="09">11</option>
    <option value="09">12</option>
</select>
    year:<select name="year">
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
        <option value="24">24</option>
        <option value="25">25</option>
        <option value="26">26</option>
        <option value="27">27</option>
        <option value="28">28</option>
    </select><br>
    CVV: <input name="CVV"><br>
    money amount:<input name="amount"><br>
    <input type="submit" value="submit">
</form>
<div id="prompt-form-container">
    <form id="prompt-form">
        <div id="prompt-message"></div>
        <input name="text" id="password" type="password">
        <input type="button" id="commit_req" value="Ок">
        <input type="button" name="cancel" value="Отмена">
    </form>
</div>
<script src="${contextPath}/js/asking_for_password_script.js"></script>
<script src="${contextPath}/js/delete_account_ajax.js"></script>
</body>
</html>
