<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Accounts</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <link href="${contextPath}/css/asking_for_delete.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${contextPath}/js/add_account.js"></script>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div class="basix-container">
    <jsp:include page="../html/dashboard.jsp"></jsp:include>
    <div class="right-panel" id="right-panel">
        <jsp:include page="../html/header.html"></jsp:include>
        <div data-v-49aa1870="" data-v-12201132="" class="card" style="width:70%">
            <div data-v-49aa1870="" class="card-body">
                <div data-v-12201132="" data-v-49aa1870="" id="pay-invoice">
                    <div data-v-12201132="" data-v-49aa1870="" class="card-body">
                        <div data-v-12201132="" data-v-49aa1870="" class="card-title"><h3 data-v-12201132=""
                                                                                          data-v-49aa1870=""
                                                                                          class="text-center">

                            New Card</h3></div>
                        <hr data-v-12201132="" data-v-49aa1870="">
                        <form data-v-12201132="" data-v-49aa1870="" action="" method="post" novalidate="novalidate">
                            <div style="color: red">
                                <c:forEach items="${requestScope.errors}" var="error">
                                    ${error}
                                </c:forEach>
                            </div>
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group"><label data-v-12201132=""
                                                                                                 data-v-49aa1870=""
                                                                                                 class="control-label mb-1">Start
                                amount
                            </label> <input data-v-12201132="" data-v-49aa1870="" id="amount"
                                            name="amount" type="text" aria-required="true"
                                            aria-invalid="false" class="form-control">
                                <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                    <ul data-v-12201132="" data-v-49aa1870="">
                                        <li data-v-12201132="" data-v-49aa1870="" id="amount_error1"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Amount must be positive</i></li>
                                        <li data-v-12201132="" data-v-49aa1870="" id="amount_error2"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Amount to large</i></li>
                                        <li data-v-12201132="" data-v-49aa1870="" id="amount_error3"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Incorrect format of amount</i></li>
                                    </ul>
                                </div>
                            </div>
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group"><label data-v-12201132=""
                                                                                                 data-v-49aa1870=""
                                                                                                 class="control-label mb-1">Card
                                number</label> <input data-v-12201132="" data-v-49aa1870="" id="card_number"
                                                      name="card_number" type="text" value="" data-val="true"
                                                      autocomplete="cc-number"
                                                      class="form-control cc-number identified visa">
                                <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                    <ul data-v-12201132="" data-v-49aa1870="">
                                        <li data-v-12201132="" data-v-49aa1870="" id="card_number_error1"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Incorrect card number</i></li>
                                        <li data-v-12201132="" data-v-49aa1870="" id="card_number_error2"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Card number already exist</i></li>
                                    </ul>
                                </div>
                            </div>
                            <div data-v-12201132="" data-v-49aa1870="" class="form-group"><label data-v-12201132=""
                                                                                                 data-v-49aa1870=""
                                                                                                 class="control-label mb-1">Account
                                name
                            </label> <input data-v-12201132="" data-v-49aa1870="" id="account_name"
                                            name="card_number" type="text" value="" data-val="true"
                                            autocomplete="cc-number"
                                            class="form-control cc-number identified visa">
                                <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                    <ul data-v-12201132="" data-v-49aa1870="">
                                        <li data-v-12201132="" data-v-49aa1870="" id="account_name_error1"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Incorrect account name</i></li>
                                        <li data-v-12201132="" data-v-49aa1870="" id="account_name_error2"
                                            style="display: none ;color:red"><i
                                                data-v-12201132="" data-v-49aa1870=""
                                        >Account must be within from 5 to 15 letters</i></li>
                                    </ul>
                                </div>
                            </div>
                            <div data-v-12201132="" data-v-49aa1870="" class="row">
                                <div data-v-12201132="" data-v-49aa1870="" class="col-6">
                                    <div style="width: 100%" data-v-12201132="" data-v-49aa1870="" class="form-group">
                                        <label
                                                data-v-12201132="" data-v-49aa1870=""
                                                class="control-label mb-1">CVV</label> <input data-v-12201132=""
                                                                                              data-v-49aa1870=""
                                                                                              id="CVV"
                                                                                              name="CVV"
                                                                                              type="tel" value=""
                                                                                              data-val="true"
                                                                                              placeholder="XXX"
                                                                                              autocomplete="cc-exp"
                                                                                              class="form-control cc-exp">
                                        <div data-v-12201132="" data-v-49aa1870="" class="form-group text-center">
                                            <ul data-v-12201132="" data-v-49aa1870="" class="list-inline">
                                                <li data-v-12201132="" data-v-49aa1870="" id="cvv_error1"
                                                    style="display: none ;color:red"><i
                                                        data-v-12201132="" data-v-49aa1870=""
                                                >Incorrect CVV</i></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    Currency: <select style="width: 100%" name="currency" id="currency"
                                                      data-v-12201132="" class="form-control">
                                    <option value="USD">USD</option>
                                    <option value="UAH">UAH</option>
                                </select>
                                </div>
                            </div>
                            <div class="row">
                                Month:<select style="width: 10%" data-v-12201132="" id="month" class="form-control"
                                              name="month">
                                <option value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="09">10</option>
                                <option value="09">11</option>
                                <option value="09">12</option>
                            </select>
                                Year: <select style="width: 10%" data-v-12201132="" id="year" class="form-control"
                                              name="year">
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                            </select>
                            </div>
                        </form>
                    </div>
                </div>
                <div data-v-12201132="" data-v-49aa1870="">
                    <button onclick="validateCard()" data-v-12201132="" data-v-49aa1870="" id="commit" type="submit"
                            class="btn btn-lg btn-info btn-block"><i data-v-12201132="" data-v-49aa1870=""
                                                                     class="fa fa-lock fa-lg"></i>&nbsp;
                        Submit
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
