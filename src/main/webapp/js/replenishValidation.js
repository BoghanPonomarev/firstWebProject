var validity = true;

function validateReplenish() {
    selectValidity("currency");
    selectValidity("account_name");
    amountValidation(document.getElementById("amount").value);
    if (validity) {
        var accountName =document.getElementById("account_name");
        var currency = document.getElementById("currency");
        window.location ="execute?account_name="+accountName.options[accountName.selectedIndex].value +"&currency="+
            currency.options[currency.selectedIndex].value +"&amount="+document.getElementById("amount").value;
    } else {
        validity = true;
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

function  selectValidity(id) {
        var e = document.getElementById(id);
       if(e.options[e.selectedIndex].value === "Please select"||e.options[e.selectedIndex].value === "Выберите"){
           validity = false;
           document.getElementById(id).style.borderColor = "red";
       }
       else{
           document.getElementById(id).style.borderColor = "green";
       }
}