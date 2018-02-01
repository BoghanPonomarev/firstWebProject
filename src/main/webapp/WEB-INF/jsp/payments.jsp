<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Payments</title>
    <link href="${contextPath}/css/users_drop-menu.css" rel="stylesheet"/>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
    <script type="text/javascript" src="${contextPath}/js/sort_requests.js"></script>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <div class="row">
            <span style="display: none" id="quantity"><c:out value="${requestScope.users.leght}" default="10"/></span>
            <div class="col-md-12">
                <div data-v-49aa1870="" class="card">
                    <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title">
                        <fmt:message key="payment.payments"/>
                    </strong> <!----></div>
                    <div data-v-49aa1870="" class="card-body">
                        <div class="table-responsive" data-v-49aa1870="">
                            <div class="d-flex flex-md-row flex-column justify-content-md-between align-items-center">
                                <div data-v-43fdb2a9="" class="form-group with-icon-left">
                                    <div data-v-43fdb2a9="" class="input-group">
                                        <form data-v-43fdb2a9="" class="form-inline">
                                            <button data-v-43fdb2a9="" type="submit"
                                                    class="btn btn-outline-success my-2 my-sm-0"><i data-v-43fdb2a9=""
                                                                                                    class="fa fa fa-search"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                                <form style="margin-left: 20px ;width: 200px">
                                    <fmt:message key="profile.sort.by"/>:<select data-v-12201132="" onchange="paymentSortRequest(${requestScope.page})" id="sort"
                                                     class="form-control">
                                    <option value="ID"><fmt:message key="profile.sort.set"/></option>
                                    <option data-v-12201132="" value="ID"><fmt:message key="profile.sort.number"/></option>
                                    <option data-v-12201132="" value="FIRST_NEW"><fmt:message key="payment.sort.old"/></option>
                                    <option data-v-12201132="" value="FIRST_OLD"><fmt:message key="payment.sort.new"/></option>
                                </select>
                                </form>
                            </div>
                            <table data-v-6159491e="" class="vuetable table table-striped">
                                <thead data-v-6159491e="">
                                <tr data-v-6159491e=""><!---->
                                    <th data-v-6159491e="" class="vuetable-th-component-id"></th>
                                    <th data-v-6159491e="" id="number" class="vuetable-th-name">№</th>
                                    <th data-v-6159491e="" id="date" class="vuetable-th-name"><fmt:message key="payment.date"/></th>
                                    <th data-v-6159491e="" id="amount" class="vuetable-th-name"><fmt:message key="payment.amount"/></th>
                                    <th data-v-6159491e="" id="sender" class="vuetable-th-name"><fmt:message key="payment.sender"/></th>
                                    <th data-v-6159491e="" id="recipient" class="vuetable-th-email sortable"><fmt:message key="payment.recipient"/>
                                    </th>
                                    <th data-v-6159491e="" id="status" class="vuetable-th-address.line2"><fmt:message key="payment.status"/></th>
                                </tr>
                                </thead>
                                <tbody data-v-6159491e="" class="vuetable-body">
                                <c:set var="iterator" value="${1}"/>
                                <c:forEach items="${requestScope.payments}" var="payment">
                                    <tr data-v-6159491e="" data-num="${iterator}">
                                        <c:set var="iterator" value="${iterator+1}"/>
                                        <td>
                                            <c:if test="${payment.type eq 'INCOMING'}">
                                                <i class="fa fa-arrow-down" aria-hidden="true" style="color: green"></i>
                                            </c:if>
                                            <c:if test="${payment.type eq 'OUTGOING'}">
                                                <i class="fa fa-arrow-up" aria-hidden="true" style="color: green"></i>
                                            </c:if>
                                        </td> <!---->
                                        <td data-v-6159491e="" class="">${payment.id}</td>
                                        <td data-v-6159491e="" class="">${payment.date}</td>
                                        <td data-v-6159491e="" class="">${payment.amount} ${payment.currency}</td>
                                        <td data-v-6159491e="" class="">${payment.sender}</td>
                                        <td data-v-6159491e="" class="">${payment.recipient}</td>
                                        <td data-v-6159491e="" class="">
                                            <c:if test="${payment.status eq 'SENT'}">
                                                <span class="badge badge-pill badge-success"><fmt:message key="payment.processed"/></span>
                                            </c:if>
                                            <c:if test="${payment.status eq 'PREPARED'}">
                                                <span class="badge badge-pill badge-warning"><fmt:message key="payment.ready"/></span>
                                            </c:if>
                                        </td>
                                        <td>
                                                <ul class="nav">
                                                    <li>
                                                        <a href="#"><i class="fa fa-sort-desc" style="color:black;" aria-hidden="true"></i></a>
                                                        <ul>
                                                            <li><a href="${contextPath}/payments/delete?payment_id=${payment.id}"><fmt:message key="payment.delete"/></a></li>
                                                            <c:if test="${payment.status eq 'PREPARED'}">
                                                            <li><a href="${contextPath}/payments/execute?payment_id=${payment.id}"><fmt:message key="payment.execute"/></a></li>
                                                            </c:if>
                                                        </ul>
                                                    </li>
                                                </ul>
                                        </td>
                                    </tr>
                                    <c:set var="iterator" value="${iterator+1}"/>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="d-flex justify-content-center pagination mb-4">
                                <div class="btn-group" style="">
                                    <c:if test="${requestScope.previousPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.previousPage}"><i
                                                class="fa fa-angle-left"></i></a>
                                    </c:if>
                                    <c:if test="${requestScope.doublePreviousPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.doublePreviousPage}">
                                                ${requestScope.page-2}
                                        </a>
                                    </c:if>
                                    <c:if test="${requestScope.previousPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.previousPage}">
                                                ${requestScope.page-1}
                                        </a>
                                    </c:if>
                                    <a class="btn btn-primary hide-not-focused-btn">${requestScope.page}</a>
                                    <c:if test="${requestScope.nextPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.nextPage}">${requestScope.page+1}</a>
                                    </c:if>
                                    <c:if test="${requestScope.doubleNextPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.doubleNextPage}">${requestScope.page+2}</a>
                                    </c:if>
                                    <c:if test="${requestScope.nextPage!=null}">
                                        <a class="btn-nav btn btn-primary pagination-link-btn"
                                           href="${requestScope.nextPage}"><i class="fa fa-angle-right"></i></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div> <!----></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
