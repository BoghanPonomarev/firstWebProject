<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Home</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${contextPath}/js/account_deletiong.js"></script>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"/>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"/>
        <c:if test="${requestScope.error!=null}">
            <div role="alert" class="alert alert-danger">
                    ${requestScope.error}
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header"><i class="fa fa-user"></i><strong
                            class="card-title pl-2">${requestScope.user.id}
                        Status:
                        <c:if test="${requestScope.user.banned}">
                            <span class="badge badge-danger">Banned</span>
                        </c:if>
                        <c:if test="${!requestScope.user.banned}">
                            <span class="badge badge-success">Dynamic</span>
                        </c:if>
                    </strong></div>
                    <div class="card-body">
                        <div class="mx-auto d-block">
                            <h5 class="text-sm-center mt-2 mb-1">Phone : ${requestScope.user.phoneNumber}</h5>
                            <c:if test="${requestScope.user.email!=null}">
                                <h5 class="text-sm-center mt-2 mb-1">Email : ${requestScope.user.email}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.firstName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1">First name : ${requestScope.user.firstName}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.secondName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1">Second name : ${requestScope.user.secondName}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.thirdName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1">Third name : ${requestScope.user.thirdName}</h5>
                            </c:if>
                        </div>
                        <hr>
                        <div class="card-text text-sm-center">
                            <c:if test="${requestScope.user.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                            &&user.id!=sessionScope.userId}">
                                <a href="${contextPath}/admin/ban_user?user_id=${requestScope.user.id}&ban_status=${requestScope.user.banned}"
                                   style="color:green" class="btn btn-success">
                                    <span style="color: white">Un-ban</span>
                                </a>
                            </c:if>
                            <c:if test="${!requestScope.user.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                            &&user.id!=sessionScope.userId}">
                                <a href="${contextPath}/admin/ban_user?user_id=${requestScope.user.id}&ban_status=${requestScope.user.banned}"
                                   style="color:red" class="btn btn-danger"><span style="color: white">Ban</span>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <c:forEach items="${requestScope.accounts}" var="account">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header"><i class="fa fa-user"></i><strong
                                class="card-title pl-2">${account.id}
                            Status:
                            <c:if test="${account.banned&&!account.requestedForUnban}">
                                <span class="badge badge-danger">Banned</span>
                            </c:if>
                            <c:if test="${!account.banned}">
                                <span class="badge badge-success">Dynamic</span>
                            </c:if>
                            <c:if test="${account.banned&&account.requestedForUnban}">
                                <span class="badge badge-warning">Requested</span>
                            </c:if>
                        </strong></div>
                        <div class="card-body">
                            <div class="mx-auto d-block">
                                <h5 class="text-sm-center mt-2 mb-1">Name : ${account.name}</h5>
                                <c:if test="${requestScope.user.id==sessionScope.userId}">
                                    <h5 class="text-sm-center mt-2 mb-1">Balance : ${account.balance}</h5>
                                </c:if>
                                <h5 class="text-sm-center mt-2 mb-1">Currency : ${account.currency}</h5>
                                <h5 class="text-sm-center mt-2 mb-1">Card number :
                                    <c:out value="${fn:substring(account.card.cardNumber,0, 5)}*****${fn:substring(account.card.cardNumber,10, fn:length(account.card.cardNumber)-1)}"/>
                                </h5>
                                <h5 class="text-sm-center mt-2 mb-1">CVV : ${account.card.CVV}</h5>
                                <h5 class="text-sm-center mt-2 mb-1">End date :${account.card.validThru}</h5>
                            </div>
                            <hr>
                            <div class="card-text text-sm-center">
                                <c:if test="${account.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                                &&user.id!=sessionScope.userId}">
                                    <a href="${contextPath}/admin/ban_account?account_id=${account.id}&ban_status=${account.banned}&user_id=${requestScope.user.id}"
                                       style="color:green" class="btn btn-success">
                                        <span style="color: white">Un-ban</span>
                                    </a>
                                </c:if>
                                <c:if test="${!account.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                                          &&user.id!=sessionScope.userId}">
                                    <a href="${contextPath}/admin/ban_account?account_id=${account.id}&ban_status=${account.banned}&user_id=${requestScope.user.id}"
                                       style="color:red" class="btn btn-danger"><span style="color: white">Ban</span>
                                    </a>
                                </c:if>
                                <c:if test="${sessionScope.userId==requestScope.user.id}">
                                    <button type="button" class="btn btn-outline-danger" id="delete-button"
                                            onclick="showWindow(${account.id})">Delete
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>
                <div data-v-12201132=""  id="prompt-form-container" class="card-body card-block" style="display: none;height: 100px;width: 300px">
                    <form data-v-12201132="" id="prompt-form" style="width: 400px">
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="password"
                                                                          class=" form-control-label">
                            <div id="prompt-message"></div>
                        </label><input
                                data-v-12201132="" type="password" name="text" id="password"
                                placeholder="Enter Password.." class="form-control"><span data-v-12201132=""
                                                                                          class="help-block">Please enter your password</span>
                        </div>
                        <div data-v-12201132="" class="card-footer">
                            <input data-v-12201132=""  id="commit_req" value="Ok" onClick="deleteAccount()" type="button" class="btn btn-primary btn-sm">

                            <input data-v-12201132="" type="button" name="cancel" value="Отмена" class="btn btn-danger btn-sm">
                        </div>
                    </form>
                </div>
            </div>

        </div>

    </div>
</div>
</body>
</html>
