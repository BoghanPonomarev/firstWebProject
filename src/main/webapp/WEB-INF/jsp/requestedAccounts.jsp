<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Requested accounts</title>
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
    <script type="text/javascript" src="${contextPath}/js/sort_requests.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jspf/dashboard.jspf" %>
<div class="right-panel" id="right-panel">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="row">
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
    </div>
</div>
</body>
</html>
