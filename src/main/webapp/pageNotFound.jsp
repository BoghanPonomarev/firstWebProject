<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>404</title>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${contextPath}/js/accountValidation.js"></script>
    <%@ include file="/WEB-INF/jspf/imports.jspf"%>
</head>
<body>
<div class="main row">
    <div class="auth-content col-md-6">
        <div class="page-404">
            <div class="justify-content-center">
                <div class="clearfix">
                    <div class="col-sm-4"><h1 class="float-left display-3 mr-4">404</h1></div>
                    <div class="col-sm-8"><h5 class="pt-3">Oops! You're lost.</h5>
                        <p class="text-muted text-small">The page you are looking for was not found.</p></div>
                </div>
                <div class="input-prepend input-group"><span class="input-group-addon"><i
                        class="fa fa-search"></i></span> <input id="prependedInput" size="16" type="text"
                                                                placeholder="What are you looking for?"
                                                                class="form-control"> <span class="input-group-btn"><button
                        type="button" class="btn btn-info">Search</button></span></div>
            </div>
        </div>
    </div>
    <div class="auth-wallpaper col-md-6 hidden-md-down">
        <div class="oblique"></div>
        <a href="#/" class="basix-home open active"><img src="${contextPath}/images/logo.png"
                                                         alt="Logo"></a></div>
</div>
</body>
</html>
