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
						<a href="lista">
							<fmt:message key="purchases.list" />
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
						<fmt:message key="purchases.create" />
					</h2>
				</div>

				<form class="row mt-3" action="insercao" method="post">
					<%@include file="campos.jsp" %>
				</form>

				<c:if test="${!empty requestScope.mensagens}">
					<div class="alert alert-danger" role="alert">
						<c:forEach items="${requestScope.mensagens}" var="mensagem">
							${mensagem}
						</c:forEach>
					</div>
				</c:if>
			</div>
		</main>
		<footer class="fixed-bottom bg-light">
			<div class="container text-center">
				Departamento de Computação - Universidade Federal de São Carlos
			</div>
		</footer>
	</body>
</fmt:bundle>

</html>