<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Home</title>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${contextPath}/js/account_deletiong.js"></script>
    <script type="text/javascript" src="${contextPath}/js/sort_requests.js"></script>
    <link href="${contextPath}/css/users_drop-menu.css" rel="stylesheet"/>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
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
                        <fmt:message key="profile.status"/>:
                        <c:if test="${requestScope.user.banned}">
                            <span class="badge badge-danger"><fmt:message key="profile.status.banned"/></span>
                        </c:if>
                        <c:if test="${!requestScope.user.banned}">
                            <span class="badge badge-success"><fmt:message key="profile.status.dynamic"/></span>
                        </c:if>
                    </strong></div>
                    <div class="card-body">
                        <div class="mx-auto d-block">
                            <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.userCard.phone"/> : ${requestScope.user.phoneNumber}</h5>
                            <c:if test="${requestScope.user.email!=null}">
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.userCard.email"/> : ${requestScope.user.email}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.firstName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.userCard.firstName"/> : ${requestScope.user.firstName}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.secondName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.userCard.secondName"/> : ${requestScope.user.secondName}</h5>
                            </c:if>
                            <c:if test="${requestScope.user.thirdName!=null}">
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.userCard.thirdName"/> : ${requestScope.user.thirdName}</h5>
                            </c:if>
                        </div>
                        <hr>
                        <div class="card-text text-sm-center">
                            <c:if test="${requestScope.user.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                            &&requestScope.user.id!=sessionScope.userId}">
                                <a href="${contextPath}/admin/ban_user?user_id=${requestScope.user.id}&ban_status=${requestScope.user.banned}"
                                   style="color:green" class="btn btn-success">
                                    <span style="color: white"><fmt:message key="profile.unBan"/> </span>
                                </a>
                            </c:if>
                            <c:if test="${!requestScope.user.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                            &&requestScope.user.id!=sessionScope.userId}">
                                <a href="${contextPath}/admin/ban_user?user_id=${requestScope.user.id}&ban_status=${requestScope.user.banned}"
                                   style="color:red" class="btn btn-danger"><span style="color: white"><fmt:message key="profile.ban"/> </span>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <h2 style="text-align: center"><fmt:message key="profile.yourAccounts"/>:</h2><br>
        <form style="margin-left: 20px ;width: 200px">
            <fmt:message key="profile.sort.by"/>: <select data-v-12201132="" onchange="accountSortRequest(${requestScope.user.id})" id="sort"
                             class="form-control">
            <option value="ID"><fmt:message key="profile.sort.set"/></option>
            <option data-v-12201132="" value="ID"><fmt:message key="profile.sort.number"/></option>
            <option data-v-12201132="" value="NAME"><fmt:message key="profile.sort.name"/></option>
            <option data-v-12201132="" value="BALANCE"><fmt:message key="profile.sort.balance"/></option>
        </select><br>
            <c:if test="${requestScope.requestError!=null}">
            <span style="color: red">${requestScope.requestError}</span>
            </c:if>
        </form>
        <div class="row">
            <c:forEach items="${requestScope.accounts}" var="account">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header"><i class="fa fa-user"></i><strong
                                class="card-title pl-2">${account.id}
                            <fmt:message key="profile.status"/>:
                            <c:if test="${account.banned&&!account.requestedForUnban}">
                                <span class="badge badge-danger"><fmt:message key="profile.status.banned"/></span>
                            </c:if>
                            <c:if test="${!account.banned}">
                                <span class="badge badge-success"><fmt:message key="profile.status.dynamic"/></span>
                            </c:if>
                            <c:if test="${account.banned&&account.requestedForUnban}">
                                <span class="badge badge-warning"><fmt:message key="profile.status.requested"/></span>
                            </c:if>
                        </strong></div>
                        <div class="card-body">
                            <div class="mx-auto d-block">
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.name"/> : ${account.name}</h5>
                                <c:if test="${requestScope.user.id==sessionScope.userId}">
                                    <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.balance"/> : ${account.balance}</h5>
                                </c:if>
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.currency"/> : ${account.currency}</h5>
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.cardNumber"/> :
                                    <c:out value="${fn:substring(account.card.cardNumber,0, 5)}*****${fn:substring(account.card.cardNumber,10, fn:length(account.card.cardNumber)-1)}"/>
                                </h5>
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.cvv"/> : ${account.card.CVV}</h5>
                                <h5 class="text-sm-center mt-2 mb-1"><fmt:message key="profile.account.date"/> :${account.card.validThru}</h5>
                            </div>
                            <hr>
                            <div class="card-text text-sm-center">
                                <c:if test="${account.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')
                                &&requestScope.user.id!=sessionScope.userId}">
                                    <a href="${contextPath}/accounts/ban?accountId=${account.id}+&userId=${requestScope.user.id}"
                                       style="color:green" class="btn btn-success">
                                        <span style="color: white"><fmt:message key="profile.unBan"/> </span>
                                    </a>
                                </c:if>
                                <c:if test="${!account.banned&&(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')}">
                                    <a href="${contextPath}/accounts/ban?accountId=${account.id}+
                                    &userId=${requestScope.user.id}"
                                       style="color:red" class="btn btn-danger"><span style="color: white"><fmt:message key="profile.ban"/> </span>
                                    </a>
                                </c:if>
                                <c:if test="${account.banned&&!account.requestedForUnban&&!(sessionScope.userRole eq 'ADMIN'||sessionScope.userRole eq 'SUPER_ADMIN')&&requestScope.user.id==sessionScope.userId}">
                                    <a href="${contextPath}/accounts/request?accountId=${account.id}&userId=${requestScope.user.id}"
                                       style="color:green" class="btn btn-warning">
                                        <span style="color: black"><fmt:message key="profile.request"/> </span>
                                    </a>
                                </c:if>
                                <c:if test="${sessionScope.userId==requestScope.user.id}">
                                    <button type="button" class="btn btn-outline-danger" id="delete-button"
                                            onclick="showWindow(${account.id})"><fmt:message key="profile.delete"/>
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>
            <div data-v-12201132="" id="prompt-form-container" class="card-body card-block"
                 style="display: none;height: 100px;width: 300px">
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
                        <input data-v-12201132="" id="commit_req" value="Ok" onClick="checkPassword()" type="button"
                               class="btn btn-primary btn-sm">

                        <input data-v-12201132="" type="button" name="cancel" value="Отмена"
                               class="btn btn-danger btn-sm">
                    </div>
                </form>
            </div>
        </div>

    </div>

</div>
</div>
</body>
</html>
