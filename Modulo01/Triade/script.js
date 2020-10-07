
function hideEven() {
    var flag = confirm('Are you sure?');
    if (flag) {
        var divsToHide = document.getElementsByClassName("even");
        for (var i = 0; i < divsToHide.length; i++) {
            divsToHide[i].style.visibility = "hidden"; // or
            divsToHide[i].style.display = "none"; // depending on what you're doing
        }
    } else
        return false;

}

