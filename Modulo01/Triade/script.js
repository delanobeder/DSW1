
function hideEven() {
    var flag = confirm('Você tem certeza?');
    if (flag) {
        var divsToHide = document.getElementsByClassName("even");
        for (var i = 0; i < divsToHide.length; i++) {
            divsToHide[i].style.visibility = "hidden"; 
            divsToHide[i].style.display = "none";
        }
    } else
        return false;

}

