<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AJAX (dynamic table)</title>
<script src="js/ajaxNome.js"></script>
</head>
<body>
	<br />
	<jsp:useBean id='bean' class='br.ufscar.dc.dsw.bean.BuscaPorNomeBean' />

	<form name='form'>
		<div align="center">
			<p>Lista de Cidades</p>
			<label for="cidade">Nome</label> <input id="cidade" name="cidade"
				onkeyUp="getCidades()">
			<div id="cidades">

				<p>Quantidade: ${fn:length(bean.cidades)}</p>
				<table border="1" style="width: 400px; border: 1px solid black">

					<tr>
						<th style="width: 10%; text-align: center"></th>
						<th style="width: 70%;">Nome</th>
						<th style="width: 20%; text-align: center">Estado</th>
					</tr>
					<c:forEach var="cidade" items="${bean.cidades}">
						<tr>
							<td style="text-align: center"><input
								type="radio" id="${cidade.nome}/${cidade.estado.sigla}"
								name="selCidade" value="${cidade.nome}/${cidade.estado.sigla}"
								onclick="alert('Selecionado: ${cidade.nome}/${cidade.estado.sigla}')">
							</td>
							<td>${cidade.nome}</td>
							<td style="text-align: center">${cidade.estado.sigla}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>
	<br />
	<a href="index.jsp">Voltar</a>
</body>
</html>
