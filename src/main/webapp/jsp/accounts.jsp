<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Accounts</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${contextPath}/js/asking_for_password_script.js"></script>
    <script type="text/javascript" src="${contextPath}/js/delete_account_ajax.js"></script>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
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
    </div>
</div>
</div>
<div id="prompt-form-container" style="display: none">
    <form id="prompt-form">
        <div id="prompt-message"></div>
        <input name="text" id="password" type="password">
        <input type="button" id="commit_req" value="Ok" onClick="deleteAccount()">
        <input type="button" name="cancel" value="Отмена">
    </form>
</div>

</body>
</html>
