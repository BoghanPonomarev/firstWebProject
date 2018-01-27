<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Admin page</title>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <div class="row">
            <span style="display: none" id="quantity"><c:out value="${requestScope.users.leght}" default="10"/></span>
            <div class="col-md-12">
                <div data-v-49aa1870="" class="card">
                    <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title">
                        Circus - links with meaning(user is banned,one of account is banned,user clear)
                    </strong> <!----></div>
                    <div data-v-49aa1870="" class="card-body">
                        <div class="table-responsive" data-v-49aa1870="">
                            <div class="d-flex flex-md-row flex-column justify-content-md-between align-items-center">
                                <div data-v-43fdb2a9="" class="form-group with-icon-left">
                                    <div data-v-43fdb2a9="" class="input-group">
                                        <form data-v-43fdb2a9="" class="form-inline"><input data-v-43fdb2a9=""
                                                                                            type="text"
                                                                                            placeholder="Search Name"
                                                                                            aria-label="Search"
                                                                                            id="input-icon-left"
                                                                                            name="input-icon-left"
                                                                                            required="required"
                                                                                            class="form-control mr-sm-2">
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
                                    <th data-v-6159491e="" id="id" class="vuetable-th-name sortable">Id</th>
                                    <th data-v-6159491e="" id="phoneNumber" class="vuetable-th-email sortable">Phone
                                        number
                                    </th>
                                    <th data-v-6159491e="" id="email" class="vuetable-th-address.line2">Email</th>
                                    <th data-v-6159491e="" id="role" class="vuetable-th-salary">Role</th>
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

                                        <td ><a href="${contextPath}/user/profile?user_id=${user.key.id}"><span style="color:${color}"
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
                                    <a class="btn btn-primary hide-not-focused-btn">${requestScope.page}</a>
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
