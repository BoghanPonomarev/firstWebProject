$(document).ready(function() {
    $("#pin_code").keyup(function () {
        if (document.getElementById("pin_code").value.length > 4) {
            $.ajax
            ({
                type: "post",//Метод передачи
                url: 'phone_confirm?pin_code=' + document.getElementById("pin_code").value
                + '&phone_number='+document.getElementById("phone_number").value,//Название сервлета
                success: function (data) {
                    if (data[0]&&data[0].localeCompare("yes")) {
                        window.location = "jsp/authorization.jsp";
                    }
                   else{
                    $("#auth-info").css({
                        "background-color": data.backgroundColor,
                        "height": "50px",
                        "color": "white"
                    });
                    $("#auth-info").html(serverData.serverInfo);
                }
                },
                error: function (e)//Если запрос не удачен
                {
                    $("#auth-info").css({"background-color": "#CC6666", "height": "50px", "color": "white"});
                    $("#auth-info").html("Запрос не удался!");
                }
            });

        }
    });

})

/*});*/
