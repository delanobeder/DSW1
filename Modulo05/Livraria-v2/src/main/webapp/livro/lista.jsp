<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Livraria Virtual</title>
	<link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" defer></script>
	<link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css" />
</head>

<body class="d-flex flex-column h-100">
	<header>
		<!-- Fixed navbar -->
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-light">
			<div class="container-fluid">
				<h3>Gerenciamento de Livros</h3>
			</div>
		</nav>
	</header>
	<main class="flex-shrink-0">

		<div class="container" style="padding: 90px 15px 0;">
			<ul class="nav nav-tabs nav-fill">
				<li class="nav-item">
					<a href="/${requestScope.contextPath}">
						Menu Principal
					</a>
				</li>
				<li class="nav-item">
					<a href="/${requestScope.contextPath}/livros/cadastro">
						Adicione Novo Livro
					</a>
				</li>
			</ul>

			<div class="row mt-3" align="center">
				<h3>
					Lista de Livros
				</h3>
			</div>

			<div class="row mt-3" align="center">
				<table class="table table-striped">
					<tr>
						<th>ID</th>
						<th>Título</th>
						<th>Editora</th>
						<th>Autor</th>
						<th>Ano</th>
						<th>Preço</th>
						<th>Ações</th>
						<th></th>
					</tr>
					<c:forEach var="livro" items="${requestScope.listaLivros}">
						<tr>
							<td>${livro.id}</td>
							<td>${livro.titulo}</td>
							<td>${livro.editora.nome}</td>
							<td>${livro.autor}</td>
							<td>${livro.ano}</td>
							<td>${livro.preco}</td>
							<td><a href="/${requestScope.contextPath}/livros/edicao?id=${livro.id}">Edição</a></td>
							<td><a href="/${requestScope.contextPath}/livros/remocao?id=${livro.id}"
									onclick="return confirm('Tem certeza de que deseja excluir este item?');">
									Remoção
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</main>
	<footer class="fixed-bottom py-3 bg-light">
		<div class="container text-center">
			Departamento de Computação - Universidade Federal de São Carlos
		</div>
	</footer>
</body>
</html>