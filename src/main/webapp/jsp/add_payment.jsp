<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Services</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
    <script type="text/javascript" src="${contextPath}/js/payment_validation.js"></script>

</head>
<body>
<input value="${contextPath}" style="display: none" disabled id="path">
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"/>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"/>
            <div data-v-12201132="" class="card" style="width: 50%">
                <div data-v-12201132="" class="card-header"><strong data-v-12201132="">Normal</strong> Form
                </div>
                <div data-v-12201132="" class="card-body card-block">
                    <form data-v-12201132="" action="" method="post">
                        <div style="color: red">
                            <c:forEach items="${requestScope.errors}" var="error">
                                ${error}<br>
                            </c:forEach>
                        </div>
                        <c:set var="identityValue" value=""/>
                        <c:if test="${requestScope.identity!=null}">
                            <c:set var="identityValue" value="${requestScope.identity}"/>
                        </c:if>
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="account_identity"
                                                                          class=" form-control-label">Card number or name of recipient account</label><input
                               value="${identityValue}" data-v-12201132="" type="text" id="account_identity" name="account_identity"
                                class="form-control">
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                <ul data-v-12201132="" data-v-49aa1870="">
                                    <li data-v-12201132="" data-v-49aa1870="" id="account_identity_error1"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    >Name or card number is not correct</i></li>
                                </ul>
                            </div>
                        </div>
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="password"
                                                                          class=" form-control-label">Your password</label><input
                                data-v-12201132="" type="password" id="password" name="password"
                                placeholder="Enter Password.." class="form-control">
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                <ul class="input-requirements">
                                    <li id="password_error1" style="color: red;display: none">At least 5 characters long (and less than 15
                                        characters)
                                    </li>
                                    <li id="password_error2" style="color: red;display: none">Password must consist only letters,numbers and some
                                        special characters(-_)
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <c:set var="amountValue" value=""/>
                        <c:if test="${requestScope.amount!=null}">
                            <c:set var="amountValue" value="${requestScope.amount}"/>
                        </c:if>
                        <div style="width: 40%" data-v-12201132=""  class="form-group"><label data-v-12201132="" for="amount"
                                                                          class=" form-control-label">Amount</label><input
                                value="${amountValue}"
                                data-v-12201132="" type="text" id="amount" name="amount"
                                placeholder="Enter amount" class="form-control">
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                <ul data-v-12201132="" data-v-49aa1870="">
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error1"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    >Amount must be positive</i></li>
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error2"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    >Amount to large</i></li>
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error3"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    >Incorrect format of amount</i></li>
                                </ul>
                            </div>
                        </div>
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="password"
                                                                          class=" form-control-label">Choose account</label>
                            <select style="width: 50%" name="user_account_id" id="user_account_id"
                                    data-v-12201132="" class="form-control">
                                <c:forEach items="${requestScope.accounts}" var="account">
                                <option value="${account.id}">${account.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="password"
                                                                          class=" form-control-label">Choose currency</label>
                            <select style="width: 50%" name="currency" id="currency"
                                    data-v-12201132="" class="form-control">
                                <c:forEach items="${requestScope.currency}" var="cur">
                                    <option value="${cur}">${cur}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <div data-v-12201132="" class="card-footer">
                    <button data-v-12201132="" class="btn btn-primary btn-sm" onclick="validatePayment()"><i data-v-12201132=""
                                                                                                             class="fa fa-dot-circle-o"></i>
                        Prepare
                    </button>
                </div>
            </div>
    </div>
</div>
</body>
</html>
