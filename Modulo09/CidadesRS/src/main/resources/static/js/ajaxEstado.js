function apresentaDS() {
    var cidade = document.getElementById("cidade");
    var estado = document.getElementById("estado");
    var selCidade = cidade.options[cidade.selectedIndex].text; 
    var selEstado = estado.options[estado.selectedIndex].text;
    console.log(selCidade + "/" + selEstado);
    alert("Selecionado: " + selCidade + "/" + selEstado); 
}

$(document).ready(function() {

	$('#estado').on('change', function() {
		var estadoId = $(this).val();
			$.ajax({
				type : 'GET',
				// url : '/cidades/estados/' + estadoId,
				url : 'http://localhost:8080/cidades/estados/'+ estadoId,
				success : function(result) {
					var s = '<option value="">Selecione</option>';
					for (var i = 0; i < result.length; i++) {
						s += '<option value="' + result[i].id + '">'
						+ result[i].nome
						+ '</option>';
					}
					$('#cidade').html(s);
				},
				error: function (request, status, error) {
				       alert(request.responseText);
				}
			});
	})
});

