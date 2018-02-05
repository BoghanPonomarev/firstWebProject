var validity = true;
var isCardNUmberFormRed = false;

function validateCard() {
    document.getElementById('commit').disabled = true;
    document.getElementById('commit').innerHTML = "Processing..";
    isExistCard();
    cvvValidation(document.getElementById("CVV").value);
    cardNumberValidation(document.getElementById("card_number").value);
    amountValidation(document.getElementById("amount").value);
    accountNameValidation(document.getElementById("account_name").value);
    setTimeout(function () {
        if (validity) {
            window.location = "../accounts/add?card_number=" + document.getElementById("card_number").value +
                "&CVV=" + document.getElementById("CVV").value + "&amount=" + document.getElementById("amount").value +
                "&year=" + document.getElementById("year").value + "&month=" + document.getElementById("month").value
                + "&currency=" + document.getElementById("currency").value + "&account_name=" + document.getElementById("account_name").value;
        } else {
            if (isCardNUmberFormRed) {
                document.getElementById("card_number").style.borderColor = "red";
            } else {
                document.getElementById("card_number").style.borderColor = "green";
            }
            validity = true;
            isCardNUmberFormRed = false;
        }
        document.getElementById('commit').disabled = false;
        document.getElementById('commit').innerHTML = "Submit";
    }, 1300);
}

function isExistCard() {
    $(document).ready(function () {
        $.ajax
        ({
            type: "get",
            url: '../accounts/check_card?card_number=' + document.getElementById("card_number").value,
            success: function (data) {
                document.getElementById("card_number_error2").style.display = "none";
            },
            error: function (request, status, error) {
                validity = false;
                document.getElementById("card_number_error2").style.display = "block";
                isCardNUmberFormRed = true;
            }
        });
    });
}

function cardNumberValidation(value) {
    if (!value.match(/^\d{14,16}$/im)) {
        document.getElementById("card_number_error1").style.display = "block";
        isCardNUmberFormRed = true;
        validity = false;
    }
    else {
        document.getElementById("card_number_error1").style.display = "none";
    }
}

function amountValidation(value) {
    var isRedBlock = false;
    if (!value.match(/^\d+(?:\.\d+){0,1}$/)) {
        document.getElementById("amount_error3").style.display = "block";
        isRedBlock = true;
        validity = false;
    }
    else {
        document.getElementById("amount_error3").style.display = "none";
    }
    if (value.length > 10) {
        document.getElementById("amount_error2").style.display = "block";
        isRedBlock = true;
        validity = false;
    }
    else {
        document.getElementById("amount_error2").style.display = "none";
    }
    if (value < 0) {
        document.getElementById("amount_error1").style.display = "block";
        isRedBlock = true;
        validity = false;
    }
    else {
        document.getElementById("amount_error1").style.display = "none";
    }
    if (isRedBlock) {
        document.getElementById("amount").style.borderColor = "red";
    }
    else {
        document.getElementById("amount").style.borderColor = "green";
    }
}

function cvvValidation(value) {
    if (!value.match(/^\d{3}$/)) {
        document.getElementById("cvv_error1").style.display = "block";
        document.getElementById("CVV").style.borderColor = "red";
        validity = false;
    }
    else {
        document.getElementById("cvv_error1").style.display = "none";
        document.getElementById("CVV").style.borderColor = "green";
    }
}

function accountNameValidation(value) {
    var isRedBlock = false;
    if (!value.match(/^[a-zA-Zа-яА-Я0-9\-]+$/)) {
        document.getElementById("account_name_error1").style.display = "block";
        isRedBlock = true;
        validity = false;
    }
    else {
        document.getElementById("account_name_error1").style.display = "none";
    }
    if (value.length < 5 || value.length > 13) {
        isRedBlock = true;
        validity = false;
        document.getElementById("account_name_error2").style.display = "block";
    }
    else {
        document.getElementById("account_name_error2").style.display = "none";
    }
    if (isRedBlock) {
        document.getElementById("account_name").style.borderColor = "red";
    }
    else {
        document.getElementById("account_name").style.borderColor = "green";
    }
}