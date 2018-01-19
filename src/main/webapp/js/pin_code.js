function checkCode() {
    $(document).ready(function () {
                $.ajax
                ({
                    type: "post",//Метод передачи
                    url: 'registration/check_code?pin_code=' + document.getElementById("pin_code").value
                    + '&phone_number=' + document.getElementById("phone_number").value,//Название сервлета
                    success: function (data) {
                            window.location = "registration/check?phone_number="+document.getElementById("phone_number").value+
                            "&password=" + document.getElementById("password").value + "&password_repeat="+document.getElementById("password_repeat").value;
                    },
                    error: function(request, status, error) {
                        $("#pin_code_error").css({
                            "background-color": "red",
                            "height": "50px",
                            "color": "black",
                            "display" : "block"
                        });
                    }
                });

    });
}
function sendCode() {
    $(document).ready(function () {
        $.ajax
        ({
            type: "post",//Метод передачи
            url: 'registration/send_code?phone_number=' + document.getElementById("phone_number").value,
        });

    });
}


function changeForm() {
    changeHidenOfElement("reg_block");
    changeHidenOfElement("pin_code_block");
    document.getElementById("repeat_phone").innerHTML = "Your phone number is: "
        +document.getElementById("phone_number").value;
    $("#pin_code_error").css({"display" : "none"});
}

