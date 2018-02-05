<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Replenish</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
    <script type="text/javascript" src="${contextPath}/js/replenishValidation.js"></script>

</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <div class="row">
            <c:if test="${fn:length(requestScope.accounts) == 0&&requestScope.errors == null}">
                <div role="alert" class="alert alert-danger">
                    <fmt:message key="replenish.withoutAccount"/><a href="${contextPath}/accounts/form" class="alert-link"><fmt:message key="replenish.create"/></a>.
                </div>
            </c:if>
            <c:if test="${fn:length(requestScope.accounts)!= 0}">
            <div data-v-12201132="" class="card" style="margin-left: 30px">
                <div data-v-12201132="" class="card-header"><strong data-v-12201132=""><fmt:message key="replenish.replenishing"/></strong>
                </div>
                <div data-v-12201132="" class="card-body card-block">
                    <c:forEach items="${requestScope.errors}" var="error">
                        <c:out value="${error}"/><br>
                    </c:forEach>
                    <form data-v-12201132="" action="" method="post">
                        <div data-v-12201132="" class="form-group"><label data-v-12201132="" for="amount"
                                                                          class=" form-control-label"><fmt:message key="payment.amount"/></label><input
                                data-v-12201132="" type="text" id="amount" name="amount"
                                placeholder="Enter amount.." class="form-control">
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                <ul data-v-12201132="" data-v-49aa1870="">
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error1"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    ><fmt:message key="account.addForm.startAmount.error1"/></i></li>
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error2"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    ><fmt:message key="account.addForm.startAmount.error2"/></i></li>
                                    <li data-v-12201132="" data-v-49aa1870="" id="amount_error3"
                                        style="display: none ;color:red"><i
                                            data-v-12201132="" data-v-49aa1870=""
                                    ><fmt:message key="account.addForm.startAmount.error3"/></i></li>
                                </ul>
                            </div>
                        </div>
                        <div style="margin: 10px" data-v-12201132="" class="col-12 col-md-9">
                            <select style="width: 60%" data-v-12201132="" name="account_name" id="account_name" class="form-control">
                                <option data-v-12201132="" value="Please select"><fmt:message key="replenish.select"/></option>
                                <c:forEach items="${requestScope.accounts}" var="account">
                                    <option data-v-12201132="" value="${account.name}">${account.name}</option>
                                </c:forEach>
                            </select></div>
                        <div data-v-12201132="" class="col-12 col-md-9">
                            <select style="width: 60%" data-v-12201132="" name="currency" id="currency" class="form-control">
                                <option data-v-12201132="" value="Please select"><fmt:message key="replenish.select"/></option>
                                <c:forEach items="${requestScope.currency}" var="oneCurrency">
                                    <option data-v-12201132="" value="${oneCurrency}">${oneCurrency}</option>
                                </c:forEach>
                            </select></div>
                    </form>
                </div>
                <div data-v-12201132="" class="card-footer">
                    <button data-v-12201132="" type="submit" class="btn btn-primary btn-sm" onclick="validateReplenish()"><i data-v-12201132=""
                                                                                               class="fa fa-dot-circle-o"></i>
                        <fmt:message key="account.addForm.submit"/>
                    </button>
                </div>
            </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
