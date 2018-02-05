function changeHiddenOfElement(id) {
    if (document.getElementById(id).style.display == "none") {
        document.getElementById(id).style.display = "block"
    }
    else {
        document.getElementById(id).style.display = "none"
    }
}

var validity = true;

function phoneValidation(input) {
    if (!input.match(/^\d{10}|(?:\d{3}-){2}\d{4}|\(\d{3}\)\d{3}-?\d{4}$/im)) {
        document.getElementById("phone_massage1").style.display = "block";
        document.getElementById("phone_number").style.borderColor = "red";
        validity = false;
    }
    else {
        document.getElementById("phone_number").style.borderColor = "green";
        document.getElementById("phone_massage1").style.display = "none";
    }
}

function passwordValidation(input) {
    var color = true;
    if (input.length < 5 || input.length > 15) {
        document.getElementById("password_massage1").style.display = "block";
        validity = false;
        color = false
    } else {
        document.getElementById("password_massage1").style.display = "none";
    }
    if (input.match(/[^a-zA-Z0-9\-\_]/g)) {
        document.getElementById("password_massage2").style.display = "block";
        validity = false;
        color = false;
    }
    else {
        document.getElementById("password_massage2").style.display = "none";
    }
    if (color) {
        document.getElementById("password").style.borderColor = "green";
    } else {
        document.getElementById("password").style.borderColor = "red";
    }
}

function repeatPasswordValidation(input) {
    if (input != document.getElementById("password").value) {
        document.getElementById("password_massage3").style.display = "block";
        document.getElementById("password_repeat").style.borderColor = "red";
        validity = false;
    }
    else {
        document.getElementById("password_massage3").style.display = "none";
        document.getElementById("password_repeat").style.borderColor = "green";
    }
}


function isExistPhone() {
    $(document).ready(function () {
        if (document.getElementById("phone_number").value.length > 8) {
            $.ajax
            ({
                type: "get",//Метод передачи
                url: 'registration/check_phone?phone_number=' + document.getElementById("phone_number").value,
                success: function (data) {
                    document.getElementById("phone_massage2").style.display = "none";
                    document.getElementById("phone_number").style.borderColor = "green";
                },
                error: function (request, status, error) {
                    validity = false;
                    document.getElementById("phone_massage2").style.display = "block";
                    document.getElementById("phone_number").style.borderColor = "red";
                }
            });
        } else {
            return true;
        }
    });
}

function validateBlock() {
    document.getElementById('commit').disabled = true;
    document.getElementById('commit').innerHTML = "Processing..";
    isExistPhone();
    setTimeout(phoneValidation(document.getElementById("phone_number").value), 750);
    passwordValidation(document.getElementById("password").value);
    repeatPasswordValidation(document.getElementById("password_repeat").value);
    setTimeout(function () {
        if (validity) {
            sendCode();
            changeForm();
        } else {
            validity = true;
        }
        document.getElementById('commit').disabled = false;
        document.getElementById('commit').innerHTML = "Sign Up";
    }, 1300);
}
