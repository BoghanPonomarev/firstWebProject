<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Requested accounts</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
    <script type="text/javascript" src="${contextPath}/js/sortRequest.js"></script>
</head>
<body>
<div class="basix-container">
<%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
<div class="right-panel" id="right-panel">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <input style="display: none" id="page" value="${requestScope.page}">
    <form style="margin-left: 20px ;width: 200px">
        <fmt:message key="profile.sort.by"/>: <select data-v-12201132=""
                                                      onchange="requestedAccountSortRequest()"
                                                      id="sort"
                                                      class="form-control">
        <option value="ID"><fmt:message key="profile.sort.set"/></option>
        <option data-v-12201132="" value="ID"><fmt:message key="profile.sort.number"/></option>
        <option data-v-12201132="" value="NAME"><fmt:message key="profile.sort.name"/></option>
    </select><br>
        <c:if test="${requestScope.requestError!=null}">
            <span style="color: red">${requestScope.requestError}</span>
        </c:if>
    </form>
    <div class="row">
        <div style="margin-left: 30px" data-v-49aa1870="" class="card">
            <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title">
                <fmt:message key="account.requested.header"/>
            </strong> <!----></div>
            <div  data-v-49aa1870="" class="card-body">
                <div class="table-responsive" data-v-49aa1870="">
                    <div class="d-flex flex-md-row flex-column justify-content-md-between align-items-center">
                        <div data-v-43fdb2a9="" class="form-group with-icon-left">
                        </div>
                    </div>
                    <table data-v-6159491e="" class="vuetable table table-striped">
                        <thead data-v-6159491e="">
                        <tr data-v-6159491e=""><!---->
                            <th data-v-6159491e="" class="vuetable-th-component-id"><fmt:message
                                    key="payment.number"/></th>
                            <th data-v-6159491e="" id="id" class="vuetable-th-name sortable"><fmt:message
                                    key="profile.account.name"/></th>
                            <th data-v-6159491e="" id="void1" class="vuetable-th-email sortable">
                            </th>
                            <th data-v-6159491e="" id="void2" class="vuetable-th-email sortable">
                            </th>
                        </tr>
                        </thead>
                        <tbody data-v-6159491e="" class="vuetable-body">
                        <c:set var="iterator" value="${1}"/>
                        <c:forEach items="${requestScope.accounts}" var="account">
                            <tr data-v-6159491e="" data-num="${iterator}" render="true" name="num">
                                <td data-v-6159491e="" class="">${account.id}</td>
                                <td data-v-6159491e="" class="">${account.name}</td>
                                <td data-v-6159491e="" class=""><a  href="${contextPath}/admin/accounts/skip?accountId=${account.id}&link=/admin/accounts/requested?page=${requestScope.page}"><fmt:message key='account.requested.skip'/></a></td>
                                <td data-v-6159491e="" class=""><a href="${contextPath}/accounts/ban?accountId=${account.id}&link=/admin/accounts/requested?page=${requestScope.page}"><fmt:message key='account.requested.Un-ban'/></a></td>
                            </tr>
                            <c:set var="iterator" value="${iterator+1}"/>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%@ include file="/WEB-INF/jspf/pagination.jspf" %>
                </div>
            </div> <!----></div>
    </div>
</div>
</div>
</body>
</html>
