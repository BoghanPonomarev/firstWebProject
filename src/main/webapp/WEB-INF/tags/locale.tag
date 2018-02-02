<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag isELIgnored="false" %>

<select onchange="if (this.value) window.location.href='?language='+value;">
    <option value="${'en'}" >lang</option>
<c:forEach items="${requestScope.locales}" var="locale">
    <option value="${locale}" >${locale}</option>
</c:forEach>
</select>
