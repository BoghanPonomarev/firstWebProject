function accountSortRequest(userId){
    var e = document.getElementById("sort");
    var value = e.options[e.selectedIndex].value;
    window.location.replace("profile?sort=" + e.options[e.selectedIndex].value + "&user_id=" + userId);
}


function paymentSortRequest(page){
    var e = document.getElementById("sort");
    window.location.replace("payments?sort=" + e.options[e.selectedIndex].value + "&page=" + page);
}