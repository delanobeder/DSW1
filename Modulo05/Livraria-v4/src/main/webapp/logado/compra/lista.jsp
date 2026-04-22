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
						<fmt:message key="purchases.welcome" />
					</h3>
				</div>
			</nav>
		</header>

		<main class="flex-shrink-0">

			<div class="container" style="padding: 90px 15px 0;">
				<ul class="nav nav-tabs nav-fill">
					<li class="nav-item">
						<a href="/${sessionScope.contextPath}/compras/cadastro">
							<fmt:message key="purchases.create" />
						</a>
					</li>

					<li class="nav-item">
						<a href="${pageContext.request.contextPath}/logout.jsp">
							<fmt:message key="exit.link" />
						</a>
					</li>
				</ul>

				<div class="row mt-3" align="center">
					<h3>
						<fmt:message key="purchases.list" />
					</h3>
				</div>

				<div class="row mt-3" align="center">
					<table class="table table-striped">

						<tr>
							<th>
								<fmt:message key="purchase.ID" />
							</th>
							<th>
								<fmt:message key="purchase.date" />
							</th>
							<th>
								<fmt:message key="purchase.value" />
							</th>
							<th>
								<fmt:message key="purchase.book.title" />
							</th>
							<th>
								<fmt:message key="purchase.book.publisher" />
							</th>
							<th>
								<fmt:message key="purchase.book.author" />
							</th>
							<th>
								<fmt:message key="purchase.book.year" />
							</th>
						</tr>
						<c:forEach var="compra" items="${requestScope.listaCompras}">
							<tr>
								<td>${compra.id}</td>
								<td>${compra.data}</td>
								<td>${compra.valor}</td>
								<td>${compra.livro.titulo}</td>
								<td>${compra.livro.editora.nome}</td>
								<td>${compra.livro.autor}</td>
								<td>${compra.livro.ano}</td>
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