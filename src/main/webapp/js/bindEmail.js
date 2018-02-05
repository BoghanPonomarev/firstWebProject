function show(blockId, buttonId) {
    document.getElementById(blockId).css.display = "block";
    document.getElementById(buttonId).css.display = "none";
}

function cancel(blockId, buttonId) {
    document.getElementById(blockId).css.display = "none";
    document.getElementById(buttonId).css.display = "block";
}

var isVAlidEmail= true;
function sendEmail(emailId, errorId) {
    isExistEmail();
    if (document.getElementById("email").value.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
        document.getElementById("email_error").style.display = "none";
    }
    else {
        document.getElementById("email_error").style.display = "block";
        isVAlidEmail = false;
    }
    setTimeout(function () {
        document.getElementById('send_email').disabled = true;
        document.getElementById('Cancel').disabled = true;
        if (validity) {
            window.location = "mail/send?email=" + document.getElementById("email").value;
        } else {
            isVAlidEmail = true;

        }
        document.getElementById('send_email').disabled = false;
        document.getElementById('Cancel').disabled = false;
    }, 1300);
}
function isExistEmail() {
    $(document).ready(function () {
        $.ajax
        ({
            type: "get",
            url: '../user/edit/email?email=' + document.getElementById("email").value,
            success: function (data) {
                document.getElementById("email_error2").style.display = "none";
            },
            error: function (request, status, error) {
                isVAlidEmail = false;
                document.getElementById("email_error2").style.display = "block";
            }
        });
    });
}