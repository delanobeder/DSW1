var xmlHttp;

function apresenta() {
    var cidade = document.getElementById("cidade");
    var estado = document.getElementById("estado");
    var selCidade = cidade.options[cidade.selectedIndex].value; 
    var selEstado = estado.options[estado.selectedIndex].value;
    alert("Selecionado: " + selCidade + "/" + selEstado); 
}

function estadoSelecionado(str) {
    if (typeof XMLHttpRequest !== "undefined") {
        xmlHttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlHttp === null) {
        alert("Browser does not support XMLHTTP Request");
        return;
    }

    var url = "buscaPorEstado";
    url += "?estado=" + str;
    xmlHttp.onreadystatechange = atualizaCidades;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}

function atualizaCidades() {
    if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
        document.getElementById("cidades").innerHTML = xmlHttp.responseText;
    }
}