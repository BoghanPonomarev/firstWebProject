var validity = true;

function validatePayment() {
    identityValidation(document.getElementById("account_identity").value);
    amountValidation(document.getElementById("amount").value);
    passwordValidation(document.getElementById("password").value);
        if (validity) {
            window.location = document.getElementById("path").value + "/payments/form/check?amount=" + document.getElementById("amount").value +
                "&recipient_identity=" + document.getElementById("account_identity").value + "&account_id="
                + document.getElementById("user_account_id").value
                + "&password=" + document.getElementById("password").value
                + "&currency=" + document.getElementById("currency").value;
        } else {
            validity = true;
        }
}

function identityValidation(value) {
    if (!value.match(/^\d{14,16}$/im)&&!value.match(/^[A-Za-zА-Яа-я0-9]{2,13}$/im)) {
        document.getElementById("account_identity_error1").style.display = "block";
        document.getElementById("account_identity").style.borderColor = "red";
        validity = false;
    }
    else {
        document.getElementById("account_identity_error1").style.display = "none";
        document.getElementById("account_identity").style.borderColor = "green";
    }
}
function passwordValidation(input) {
    var color = true;
    if (input.length < 5 || input.length > 15) {
        document.getElementById("password_error1").style.display = "block";
        validity = false;
        color = false
    } else {
        document.getElementById("password_error1").style.display = "none";
    }
    if (input.match(/[^a-zA-Z0-9\-\_]/g)) {
        document.getElementById("password_error2").style.display = "block";
        validity = false;
        color = false;
    }
    else {
        document.getElementById("password_error2").style.display = "none";
    }
    if (color) {
        document.getElementById("password").style.borderColor = "green";
    } else {
        document.getElementById("password").style.borderColor = "red";
    }
}

function amountValidation(value) {
    var isRedBlock = false;
    if (!value.match(/^\d+(?:\.\d{0,2}){0,1}$/)) {
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

