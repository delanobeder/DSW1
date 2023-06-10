<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<fmt:bundle basename="message">

	<head>
<title><fmt:message key="page.title" /></title>
	</head>

	<body>
		<div align="center">
			<h1>
				<fmt:message key="books.welcome" />
			</h1>
			<h2>
				<a href="/${requestScope.contextPath}"> <fmt:message key="main.link" /></a> 
				&nbsp;&nbsp;&nbsp; 
				<a href="/${requestScope.contextPath}/livros/cadastro"> <fmt:message key="books.create" />
				</a>
			</h2>
		</div>
		<div align="center">
			<table border="1">
				<caption>
					<fmt:message key="books.list" />
				</caption>
				<tr>
					<th><fmt:message key="book.ID" /></th>
					<th><fmt:message key="book.title" /></th>
					<th><fmt:message key="book.publisher" /></th>
					<th><fmt:message key="book.author" /></th>
					<th><fmt:message key="book.year" /></th>
					<th><fmt:message key="book.price" /></th>
					<th><fmt:message key="actions.link" /></th>
				</tr>
				<c:forEach var="livro" items="${requestScope.listaLivros}">
					<tr>
						<td>${livro.id}</td>
						<td>${livro.titulo}</td>
						<td>${livro.editora.nome}</td>
						<td>${livro.autor}</td>
						<td>${livro.ano}</td>
						<td>${livro.preco}</td>
						<td><a href="/${requestScope.contextPath}/livros/edicao?id=${livro.id}">
								<fmt:message key="books.update" />
						</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
							href="/${requestScope.contextPath}/livros/remocao?id=${livro.id}"
							onclick="return confirm('<fmt:message key="confirm.link" />');">
								<fmt:message key="books.delete" />
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</body>
</fmt:bundle>

</html>