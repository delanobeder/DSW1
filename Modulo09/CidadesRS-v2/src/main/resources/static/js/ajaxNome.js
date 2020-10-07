


$(document).ready(function() {

		$('#nome').on('keyup', function() {
			var nome = $(this).val();
			$.ajax({
				type : 'GET',
				url : contextRoot +'cidades/filtros?term='+ nome,
				success : function(result) {
					var s = '';
					s += '<p>Quantidade: ' + result.length+'</p>';
					s += '<table border="1" style="width: 400px; border: 1px solid black">';
					s += '<tr>';
					s += '   <th style="width: 10%; text-align: center"></th>';
					s += '   <th style="width: 70%;">Nome</th>';
					s += '   <th style="width: 20%; text-align: center">Estado</th>';
					s += '</tr>';
					for (var i = 0; i < result.length; i++) {
						var indice = result[i].indexOf("/");
						var cidade = result[i].slice(0, indice);
						var estado = result[i].slice(indice + 1);
						s += '<tr>';
						s += '   <td style="text-align:center">';
						s += '      <input type="radio" id="' + cidade + '/' + estado + '" '; 
						s += '             name="selCidade" value="'+ cidade + '/' + estado + '"';
						s += '             onclick="alert(' + '\'' + 'Selecionado: ' +  cidade + '/' + estado + '\')">'; 
						s += '   </td>';
						s += '   <td>' + cidade + '</td>';
						s += '   <td style="text-align:center">' + estado + '</td>';
						s += '</tr>'
					}					
					s += '</table>';
					$('#cidades').html(s);
				},
				error: function (request, status, error) {
				       alert(request.responseText);
				}
			});
		})
	});