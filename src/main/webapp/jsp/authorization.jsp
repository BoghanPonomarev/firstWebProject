<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <title>Authorization</title>
    <link type="text/css" rel="stylesheet"
          href="blob:http://fine-process.surge.sh/776d45d9-823d-4c51-8065-54f3213006f0">
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body class="">
<div id="app" class="app">
    <div class="auth-layout">
        <div class="nav hidden-lg-up"><a href="#/" class="basix-home open active"></a></div>
        <div class="main row">
            <div class="auth-content col-md-6">
                <div data-v-71645585="" class="login">
                    <div data-v-49aa1870="" data-v-71645585="" class="card">
                        <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870=""
                                                                                    class="card-title">Welcome
                            !</strong> <!----></div>
                        <div data-v-49aa1870="" class="card-body">
                            <div data-v-71645585="" data-v-49aa1870="" class="card-body card-block">
                                <span style="color:red">
                                <c:forEach items="${requestScope.errors}" var="error">
                                    <c:out value="${error}"/><br>
                                </c:forEach>
                                    </span>
                                <form data-v-71645585="" data-v-49aa1870="" method="post"
                                      action="${contextPath}/authorization/check"
                                      name="login">
                                    <div data-v-71645585="" data-v-49aa1870="" class="form-group">
                                        <div data-v-71645585="" data-v-49aa1870="" class="input-group">
                                            <div data-v-71645585="" data-v-49aa1870="" class="input-group-addon"><i
                                                    data-v-71645585="" data-v-49aa1870="" class="fa fa-envelope"></i>
                                            </div>
                                            <input value="" data-v-71645585="" data-v-49aa1870="" type="text"
                                                   id="phone_number"
                                                   name="phone_number" maxlength="15" class="form-control"
                                                   autocomplete="off"></div>
                                    </div>
                                    <div data-v-71645585="" data-v-49aa1870="" class="form-group">
                                        <div data-v-71645585="" data-v-49aa1870="" class="input-group">
                                            <div data-v-71645585="" data-v-49aa1870="" class="input-group-addon"><i
                                                    data-v-71645585="" data-v-49aa1870="" class="fa fa-asterisk"></i>
                                            </div>
                                            <input data-v-71645585="" data-v-49aa1870="" type="password" id="password"
                                                   autocomplete="off"
                                                   name="password" maxlength="15" placeholder="Password"
                                                   class="form-control"></div>
                                    </div>
                                    <div data-v-71645585="" data-v-49aa1870="" class="form-actions form-group">
                                        <button data-v-71645585="" data-v-49aa1870="" type="submit" value="submit"
                                                class="btn btn-success btn-md">Log In
                                        </button>
                                        <button data-v-71645585="" data-v-49aa1870="" type="submit"
                                                class="btn btn-primary btn-md float-right"><a data-v-71645585=""
                                                                                              href="${contextPath}/registration"
                                                                                              class="link text-light float-right"
                                                                                              data-v-49aa1870="">Create
                                            Account</a></button>
                                    </div>
                                </form>
                            </div>
                        </div> <!----></div>
                </div>
            </div>
            <div class="auth-wallpaper col-md-6 hidden-md-down">
                <div class="oblique"></div>
                <a href="#/" class="basix-home open active"><img src="/dist/logo.png?b1654d67dcbdec1e962eca90b45001fb"
                                                                 alt="Logo"></a></div>
        </div>
    </div>
</div>
<script src="${contextPath}/js/build.js"></script>


</body>
</html>