function setHiddenStatus(className) {
    var elements = document.getElementsByClassName(className);
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].style.display === "none") {
            elements[i].style.display = "block";
        }
        else {
            elements[i].style.display = "none";
        }
    }
}