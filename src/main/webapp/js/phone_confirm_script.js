$(document).ready(function() {
    $("#pin_code").keyup(function () {
        if (document.getElementById("pin_code").value.length === 4) {
            $.ajax
            ({
                type: "post",//Метод передачи
                url: 'http://localhost:8080/app/phone_confirm?pin_code=' + document.getElementById("pin_code").value,//Название сервлета
                success: function (serverData)//Если запрос удачен
                {
                    $("#auth-info").css({
                        "background-color": serverData.backgroundColor,
                        "height": "50px",
                        "color": "white"
                    });
                    $("#auth-info").html(serverData.serverInfo);
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
