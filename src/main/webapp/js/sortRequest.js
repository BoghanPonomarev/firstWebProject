function accountSortRequest(userId){
    var e = document.getElementById("sort");
    var value = e.options[e.selectedIndex].value;
    window.location.replace("profile?sort=" + e.options[e.selectedIndex].value + "&userId=" + userId);
}
function requestedAccountSortRequest(){
    var e = document.getElementById("sort");
    var value = e.options[e.selectedIndex].value;
    window.location.replace("requested?sort=" + e.options[e.selectedIndex].value+"&page="+document.getElementById("page").value);
}

function paymentSortRequest(page){
    var e = document.getElementById("sort");
    window.location.replace("payments?sort=" + e.options[e.selectedIndex].value + "&page=" + page);
}