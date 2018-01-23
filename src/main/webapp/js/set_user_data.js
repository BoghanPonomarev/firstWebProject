function show(blockId, buttonId) {
    document.getElementById(blockId).css.display = "block";
    document.getElementById(buttonId).css.display = "none";
}

function cancel(blockId, buttonId) {
    document.getElementById(blockId).css.display = "none";
    document.getElementById(buttonId).css.display = "block";
}

function sendEmail(emailId, errorId) {

    if (document.getElementById(emailId).value.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
        window.location = "set/check?email=" + document.getElementById(emailId).value;
    }
    else {
        document.getElementById(errorId).css({
            "background-color": "red",
            "display": "block"
        });
    }
}

function sendNames(firstNameId, secondNameId, thirdNameId, firstNameError, secondNameError, thirdNameError) {
    var validity = true;
    validity &= checkName(firstNameId, firstNameError);
    validity &= checkName(secondNameId, secondNameError);
    validity &= checkName(thirdNameId, thirdNameError);
    if (validity) {
        window.location = "set/check?first_name=" + document.getElementById(firstNameId).value
            + "&second_name=" + document.getElementById(secondNameId).value + "&third_name"
            + document.getElementById(thirdNameId).value;
    }
}

function checkName(nameId, errorId) {
    var input = document.getElementById(nameId).value;
    var validity = true;
    if (input.length < 2 || input.length > 15) {
        document.getElementById(errorId).css.display = "none"
        document.getElementById(errorId).innerHTML = "";
    }
    else {
        document.getElementById(errorId).css({
            "background-color": "red",
            "display": "block"
        });
        document.getElementById(errorId).innerHTML = document.getElementById(nameId).name +
            "Must be from 2 to 15 letters";
        validity = false;
    }
    if (input.match(/[a-zA-ZА-Яа-я -]/g)) {
        document.getElementById(errorId).css.display = "none"
        document.getElementById(errorId).innerHTML = "";
    }
    else {
        document.getElementById(errorId).css({
            "background-color": "red",
            "display": "block"
        });
        document.getElementById(errorId).innerHTML = document.getElementById(errorId).innerHTML + "\n"
            + document.getElementById(nameId).name +
            "is incorrect";
        validity = false;
    }
    return validity;
}