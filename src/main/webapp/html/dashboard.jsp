<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-lg">
        <div data-v-091b4d20="" class="navbar-header collapsed">
            <button data-v-091b4d20="" type="button" data-toggle="collapse" data-target="#main-menu"
                    aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation"
                    class="navbar-toggler"><i data-v-091b4d20="" class="fa fa-bars"></i></button>
            <a data-v-091b4d20="" href="#/" class="navbar-brand invisible-xs"><img data-v-091b4d20=""
                                                                                   src="/dist/logo.png?b1654d67dcbdec1e962eca90b45001fb"
                                                                                   alt="Logo" class="float-left"></a> <a
                data-v-091b4d20="" href="#/" class="navbar-brand d-md-none open active"><img data-v-091b4d20=""
                                                                                             src="/dist/logo2.png?7f116c75f9caae9aff422d475949e41b"
                                                                                             alt="Logo"></a></div>
        <div id="main-menu" role="navigation" class="main-menu collapse navbar-collapse">
            <ul class="navbar-nav">
                <li class="nav-item"><a href="#/dashboard" class=""><i class="menu-icon fa fa-dashboard"></i> <span
                        class="menu-title-text">Dashboard</span> <span class="badge badge-success">NEW</span></a></li>
                <h3 class="menu-title">
                    UI elements
                </h3>
                <li to="/components" class="menu-item-has-children"><a href="#" class="open"><i
                        class="menu-icon fa fa-puzzle-piece"></i><span class="menu-title-text">Components</span></a>
                    <ul class="sub-menu children">
                        <li class="nav-item"><a href="${contextPath}/user/settings"
                                                class="router-link-exact-active active"><i
                                class="menu-icon fa fa-puzzle-piece"></i> <span class="menu-title-text">Settings</span>
                        <li class="nav-item"><a href="${contextPath}/accounts/show_accounts" class=""><i
                                class="menu-icon fa fa-id-badge"></i> <span class="menu-title-text">Accounts</span>
                            <!----></a></li>
                    </ul>
                </li>
                <li class="nav-item"><a href="${contextPath}/user/show_profile" class=""><i
                        class="menu-icon fa fa-table"></i> <span
                        class="menu-title-text">Profile</span> <!----></a></li>
                <li to="/forms" class="menu-item-has-children open"><a href="#"><i
                        class="menu-icon fa fa-pencil-square-o"></i><span class="menu-title-text">Forms</span></a>
                    <ul class="sub-menu children">
                        <li class="nav-item"><a href="${contextPath}/accounts/get_add_account_form" class=""><i
                                class="menu-icon fa fa-pencil-square-o"></i> <span
                                class="menu-title-text">Add account</span> <span
                                class="badge badge-warning">NEW</span></a></li>
                        <li class="nav-item"><a href="#/components/advanced-form" class=""><i
                                class="menu-icon fa fa-pencil-square-o"></i> <span
                                class="menu-title-text">Advanced Form</span> <!----></a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div></div>
</aside>