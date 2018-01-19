document.getElementById("commit_req").onclick = function(){
    if (document.getElementById("password").value.length > 4) {
        $.ajax
        ({
            type: "get",//Метод передачи
            url: '../accounts/delete?password=' + document.getElementById("password").value
            + "&account_id=" + element,
            success: function (data) {
                    window.location = "../accounts/show_accounts";
            },
            error: function (e)//Если запрос не удачен
            {
                $("#prompt-form").css({
                    "background-color": "red"
                });
                $("#prompt-message").html("Пароль не верный\nВведите правильный пароль!");
            }

        });
    }
};