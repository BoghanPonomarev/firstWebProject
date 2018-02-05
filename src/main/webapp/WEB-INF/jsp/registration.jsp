<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<head>
    <title>Registration</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="/WEB-INF/jspf/imports.jspf" %>
    <script src="${contextPath}/js/registrationValidation.js"></script>
    <script src="${contextPath}/js/pinCode.js"></script>
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
                                                                                    class="card-title"><fmt:message key="registration.new"/></strong></div>
                        <div data-v-49aa1870="" class="card-body">
                            <div role="alert" id="pin_code_error"
                                 class="alert alert-danger" style="display: none">
                                <fmt:message key="registration.block"/>
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
                                               class="form-control" placeholder="<fmt:message key="registration.phone"/>"></div>
                                    <ul class="input-requirements">
                                        <li id="phone_massage1"><fmt:message key="registration.phone.error1"/></li>
                                        <li id="phone_massage2"><fmt:message key="registration.phone.error2"/></li>
                                    </ul>
                                </div>
                                <div data-v-49aa1870="" class="form-group">
                                    <div data-v-49aa1870="" class="input-group">
                                        <div data-v-49aa1870="" class="input-group-addon"><i data-v-49aa1870=""
                                                                                             class="fa fa-asterisk"></i>
                                        </div>
                                        <input data-v-49aa1870="" type="password" id="password" name="password"
                                               placeholder="<fmt:message key="registration.password"/>" class="form-control"></div>
                                    <ul class="input-requirements">
                                        <li id="password_massage1"><fmt:message key="registration.password.error1"/>
                                        </li>
                                        <li id="password_massage2"><fmt:message key="registration.password.error2"/>
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
                                               placeholder="<fmt:message key="registration.password.repeat"/>" class="form-control"></div>
                                    <ul class="input-requirements">
                                        <li id="password_massage3" style="display: none"><fmt:message key="registration.password.repeat.error"/></li>
                                    </ul>
                                </div>
                                <div data-v-49aa1870=""
                                     class="d-flex flex-column flex-lg-row align-items-center justify-content-between down-container">
                                    <button data-v-49aa1870="" type="submit" class="btn btn-primary"
                                            onclick="validateBlock()" id="commit">
                                        <fmt:message key="registration.singUp"/>
                                    </button>
                                    <button data-v-49aa1870="" type="submit" class="btn btn-primary btn-md float-right">
                                        <a href="${contextPath}/authorization" class="link text-light float-right"
                                           data-v-49aa1870=""><fmt:message key="registration.joined"/></a></button>
                                </div>
                            </form>
                        </div> <!----></div>
                </div>
                <div data-v-49aa1870="" class="card" style="display: none" id="pin_code_block">
                    <div data-v-49aa1870="" class="card-header"><!----> <strong data-v-49aa1870="" class="card-title"><fmt:message key="registration.enterPin"/></strong> <!----></div>
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
                                    <fmt:message key="registration.submit"/>
                                </button>
                                <button type="button" onclick="waitNextCode()" data-v-49aa1870=""
                                        class="btn btn-primary btn-md float-right"><fmt:message key="registration.number.change"/>
                                </button>
                            </div>
                        </form>
                    </div> <!----></div>
            </div>
            <div class="auth-wallpaper col-md-6 hidden-md-down">
                <div class="oblique"></div>
                <a href="#/" class="basix-home open active"><img src="${contextPath}/images/logo.png"
                                                                 alt="Logo"></a></div>
        </div>
    </div>
</div>
</body>
</html>