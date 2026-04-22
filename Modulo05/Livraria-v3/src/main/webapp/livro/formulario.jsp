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
						<a href="/${requestScope.contextPath}">
							<fmt:message key="main.link" />
						</a>
					</li>
					<li class="nav-item">
						<a href="/${requestScope.contextPath}/livros">
							<fmt:message key="books.list" />
						</a>
					</li>
				</ul>

				<c:choose>
					<c:when test="${livro != null}">
						<div class="row mt-3" align="center">
							<h2>
								<fmt:message key="books.update" />
							</h2>
						</div>
						<form class="row g-3" action="atualizacao" method="post">
							<%@include file="campos.jsp" %>
						</form>
					</c:when>
					<c:otherwise>
						<div class="row mt-3" align="center">
							<h2>
								<fmt:message key="books.register" />
							</h2>
						</div>
						<form class="row g-3" action="insercao" method="post">
							<%@include file="campos.jsp" %>
						</form>
					</c:otherwise>
				</c:choose>

				<c:if test="${!empty requestScope.mensagens}">
					<div class="alert alert-danger" role="alert">
						<c:forEach items="${requestScope.mensagens}" var="mensagem">
							${mensagem}
						</c:forEach>
					</div>
				</c:if>
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