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
				url : 'http://localhost:8081/cidades/estados/'+ estadoId,
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
	
	$.ajax({
		type : 'GET',  
		url: "http://localhost:8081/estados",
        success: function(result) {
            var s = '<option value="">Selecione</option>';
            for (var i = 0; i < result.length; i++) {
						s += '<option value="' + result[i].id + '">'
						+ result[i].sigla
						+ '</option>';
			}
			$('#estado').html(s);
		},
		error: function (request, status, error) {
				       alert(request.responseText);
		}
	});
});

