<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AJAX (dynamic table)</title>
<script src="js/ajaxNome.js" defer></script>
</head>
<body onload="getCidades()">
	<br />

	<form name='form'>
		<div align="center">
			<p>Lista de Cidades</p>
			<label for="cidade">Nome</label> 
			<input id="cidade" name="cidade" onkeyUp="getCidades()">
			<div id="cidades">
			</div>
		</div>
	</form>
	<br />
	<a href="index.jsp">Voltar</a>
</body>
</html>
