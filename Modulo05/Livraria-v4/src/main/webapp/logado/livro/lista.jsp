<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<fmt:bundle basename="message">

	<head>
		<title>
			<fmt:message key="page.title" />
		</title>
		<link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" defer></script>
		<link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css" />
	</head>

	<body class="d-flex flex-column h-100">
		<header>
			<!-- Fixed navbar -->
			<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-light">
				<div class="container-fluid">
					<h3>
						<fmt:message key="books.welcome" />
					</h3>
				</div>
			</nav>
		</header>
		<main class="flex-shrink-0">

			<div class="container" style="padding: 90px 15px 0;">
				<ul class="nav nav-tabs nav-fill">
					<li class="nav-item">
						<a href="/${sessionScope.contextPath}/editoras">
							<fmt:message key="publishers.entity" />
						</a>
					</li>
					<li class="nav-item">
						<a href="/${sessionScope.contextPath}/usuarios">
							<fmt:message key="users.entity" />
						</a>
					</li>
					<li class="nav-item">
						<a href="${pageContext.request.contextPath}/logout.jsp">
							<fmt:message key="exit.link" />
						</a>
					</li>
				</ul>
				<div class="row mt-3" align="center">
					<h2>
						<a href="/${sessionScope.contextPath}/livros/cadastro">
							<fmt:message key="books.create" />
						</a>
					</h2>
				</div>
				<div class="row mt-3" align="center">
					<h3>
						<fmt:message key="books.list" />
					</h3>
				</div>

				<div class="row mt-3" align="center">
					<table class="table table-striped">
						<tr>
							<th>
								<fmt:message key="book.ID" />
							</th>
							<th>
								<fmt:message key="book.title" />
							</th>
							<th>
								<fmt:message key="book.publisher" />
							</th>
							<th>
								<fmt:message key="book.author" />
							</th>
							<th>
								<fmt:message key="book.year" />
							</th>
							<th>
								<fmt:message key="book.price" />
							</th>
							<th>
								<fmt:message key="actions.link" />
							</th>
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
								<td><a href="/${sessionScope.contextPath}/livros/edicao?id=${livro.id}">
										<fmt:message key="books.update" />
										</a>
								</td>
								<td><a
										href="/${sessionScope.contextPath}/livros/remocao?id=${livro.id}"
										onclick="return confirm('<fmt:message key="confirm.link" />');">
									<fmt:message key="books.delete" />
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
</fmt:bundle>

</html>