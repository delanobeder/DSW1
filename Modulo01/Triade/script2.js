
function hideEven() {
    var flag = confirm('Você tem certeza?');
    if (flag) {
        var b = document.getElementById("b2");
        var hide = (b.textContent == "Hide Even");
        console.log(hide);
        var divsToHide = document.getElementsByClassName("even");
        for (var i = 0; i < divsToHide.length; i++) {
            if (hide) {
                divsToHide[i].style.visibility = "hidden";
                divsToHide[i].style.display = "none";
            } else {
                divsToHide[i].style.visibility = "visible";
                divsToHide[i].style.display = "initial";
            }
        }
        if (hide) {
            b.textContent = "Show Even";
        } else {
            b.textContent = "Hide Even";
        }

    } else
        return false;

}

function init() {
    var b = document.getElementById("b2");
    b.addEventListener("click", hideEven);
}

init();

