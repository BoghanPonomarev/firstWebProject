<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Admin page</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
</head>
<body>
<div class="basix-container">
    <%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
    <div class="right-panel" id="right-panel">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <div class="row">
            <%--<span style="display: none" id="quantity"><c:out value="${requestScope.users.size}" default="10"/></span>--%>
            <div class="col-md-12">
                <div data-v-49aa1870="" class="card">
                    <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title">
                        <fmt:message key="users.header"/>
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
                            </div>
                            <table data-v-6159491e="" class="vuetable table table-striped">
                                <thead data-v-6159491e="">
                                <tr data-v-6159491e=""><!---->
                                    <th data-v-6159491e="" class="vuetable-th-component-id"></th>
                                    <th data-v-6159491e="" id="id" class="vuetable-th-name sortable"><fmt:message key="users.id"/></th>
                                    <th data-v-6159491e="" id="phoneNumber" class="vuetable-th-email sortable"><fmt:message key="users.phone"/>
                                    </th>
                                    <th data-v-6159491e="" id="email" class="vuetable-th-address.line2"><fmt:message key="users.emial"/></th>
                                    <th data-v-6159491e="" id="role" class="vuetable-th-salary"><fmt:message key="users.role"/></th>
                                </tr>
                                </thead>
                                <tbody data-v-6159491e="" class="vuetable-body">
                                <c:set var="iterator" value="${1}"/>
                                <c:forEach items="${requestScope.users}" var="user">
                                    <c:set value="green" var="color"/>
                                    <c:forEach items="${user.value}" var="it">
                                        <c:if test="${it.banned}">
                                            <c:set value="#FFA500" var="color"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${user.key.banned}">
                                        <c:set value="red" var="color"/>
                                    </c:if>
                                    <tr data-v-6159491e="" data-num="${iterator}" render="true" name="num">
                                        <!---->

                                        <td><a href="${contextPath}/user/profile?user_id=${user.key.id}"><span
                                                style="color:${color}"
                                                class="fa fa-circle"></span>
                                        </a>
                                        </td> <!---->
                                        <td data-v-6159491e="" class="">${user.key.id}</td>
                                        <td data-v-6159491e="" class="">${user.key.phoneNumber}</td>
                                        <td data-v-6159491e="" class=""><c:out value="${user.key.email}"
                                                                               default="-"/></td>
                                        <td data-v-6159491e="" class="">${user.key.role}</td>
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
