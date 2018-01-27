<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Registration</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700' rel='stylesheet' type='text/css'>
    <link type="text/css" rel="stylesheet"
          href="blob:http://fine-process.surge.sh/776d45d9-823d-4c51-8065-54f3213006f0">
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="${contextPath}/css/font.css" rel="stylesheet"/>
    <style>li {
        display: none;
        color: red
    }</style>
</head>
<body>
<div id="app" class="app">
    <div class="auth-layout">
        <div class="nav hidden-lg-up"><a href="#/" class="basix-home open active"></a></div>
        <div class="main row">
            <div class="auth-content col-md-6">
                <div class="register" id="reg_block">
                    <div data-v-49aa1870="" class="card">
                        <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870=""
                                                                                    class="card-title">Create New
                            Account</strong></div>
                        <div data-v-49aa1870="" class="card-body">
                            <div role="alert" id="pin_code_error"
                                 class="alert alert-danger" style="display: none">
                                We block you on 30 second because we suspect that you may be a hacker or
                                your pin code is wrong
                            </div>
                            <c:forEach items="${requestScope.errors}" var="error">
                                <c:out value="${error}"/><br>
                            </c:forEach>
                            <form data-v-49aa1870="" method="post" action="${contextPath}/registration/check"
                                  name="register" id="registration" class="registration">
                                <div data-v-49aa1870="" class="form-group">
                                    <div data-v-49aa1870="" class="input-group">
                                        <div data-v-49aa1870="" class="input-group-addon"><i data-v-49aa1870=""
                                                                                             class="fa fa-envelope"></i>
                                        </div>
                                        <input data-v-49aa1870="" type="text" id="phone_number" name="phone_number"
                                               maxlength="15"
                                               class="form-control" placeholder="Phone"></div>
                                    <ul class="input-requirements">
                                        <li id="phone_massage1">Phone is incorrect</li>
                                        <li id="phone_massage2">Phone has already registered</li>
                                    </ul>
                                </div>
                                <div data-v-49aa1870="" class="form-group">
                                    <div data-v-49aa1870="" class="input-group">
                                        <div data-v-49aa1870="" class="input-group-addon"><i data-v-49aa1870=""
                                                                                             class="fa fa-asterisk"></i>
                                        </div>
                                        <input data-v-49aa1870="" type="password" id="password" name="password"
                                               placeholder="Password" class="form-control"></div>
                                    <ul class="input-requirements">
                                        <li id="password_massage1">At least 5 characters long (and less than 15
                                            characters)
                                        </li>
                                        <li id="password_massage2">Password must consist only letters,numbers and some
                                            special characters(-_)
                                        </li>
                                    </ul>
                                </div>
                                <div data-v-49aa1870="" class="form-group">
                                    <div data-v-49aa1870="" class="input-group">
                                        <div data-v-49aa1870="" class="input-group-addon"><i data-v-49aa1870=""
                                                                                             class="fa fa-asterisk"></i>
                                        </div>
                                        <input data-v-49aa1870="" type="password" id="password_repeat"
                                               name="password_repeat"
                                               placeholder="Password" class="form-control"></div>
                                    <ul class="input-requirements">
                                        <li id="password_massage3" style="display: none">Passwords must be equal</li>
                                    </ul>
                                </div>
                                <div data-v-49aa1870=""
                                     class="d-flex flex-column flex-lg-row align-items-center justify-content-between down-container">
                                    <button data-v-49aa1870="" type="submit" class="btn btn-primary"
                                            onclick="validateBlock()" id="commit">
                                        Sign Up
                                    </button>
                                    <button data-v-49aa1870="" type="submit" class="btn btn-primary btn-md float-right">
                                        <a href="${contextPath}/authorization" class="link text-light float-right"
                                           data-v-49aa1870="">Already
                                            joined?</a></button>
                                </div>
                            </form>
                        </div> <!----></div>
                </div>
                <div data-v-49aa1870="" class="card" style="display: none" id="pin_code_block">
                    <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title">Enter
                        Pin Code</strong> <!----></div>
                    <div data-v-49aa1870="" class="card-body">
                        <form data-v-49aa1870="" method="post" action="${contextPath}/registration/send_code"
                              name="register">
                            <div data-v-49aa1870="" class="form-group">
                                <div data-v-49aa1870="" class="card-body">
                                    <div id="repeat_phone"></div>
                                    <div class="input-group">
                                        <div data-v-49aa1870="" class="input-group-addon"><i data-v-49aa1870=""
                                                                                             class="fa fa-envelope"></i>
                                        </div>
                                        <input data-v-49aa1870="" type="text" id="pin_code" name="pin_code"
                                               placeholder="Pin code"
                                               class="form-control" maxlength="5">
                                    </div>
                                </div>
                            </div>
                            <div data-v-49aa1870=""
                                 class="d-flex flex-column flex-lg-row align-items-center justify-content-between down-container">
                                <button type="button" onclick="checkCode()" data-v-49aa1870="" class="btn btn-primary">
                                    Submit
                                </button>
                                <button type="button" onclick="waitNextCode()" data-v-49aa1870=""
                                        class="btn btn-primary btn-md float-right">Change number
                                </button>
                            </div>
                        </form>
                    </div> <!----></div>
            </div>
            <div class="auth-wallpaper col-md-6 hidden-md-down">
                <div class="oblique"></div>
                <a href="#/" class="basix-home open active"><img src="/dist/logo.png?b1654d67dcbdec1e962eca90b45001fb"
                                                                 alt="Logo"></a></div>
        </div>
    </div>
</div>
<script src="${contextPath}/js/build.js"></script>
<script src="${contextPath}/js/registration_validation.js"></script>
<script src="${contextPath}/js/pin_code.js"></script>

</body>
</html>