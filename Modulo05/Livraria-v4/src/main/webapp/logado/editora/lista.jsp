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
				<fmt:message key="publishers.welcome" />
			</h1>
			<h2>
				<a href="/${sessionScope.contextPath}/livros"> 
					<fmt:message key="books.entity" />
				</a> 
				&nbsp;&nbsp;&nbsp;
				<a href="/${sessionScope.contextPath}/usuarios"> 
					<fmt:message key="users.entity" />
				</a> 
				&nbsp;&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath}/logout.jsp"> 
					<fmt:message key="exit.link" />
				</a>
				<br/>
				<br/>
				<a href="/${sessionScope.contextPath}/editoras/cadastro">
					<fmt:message key="publishers.create" />
				</a> 
			</h2>
			<h3><fmt:message key="publishers.list" /></h3>
			<br/>
		</div>
		<div align="center">
			<table border="1">
				<tr>
					<th><fmt:message key="publisher.ID" /></th>
					<th><fmt:message key="publisher.CNPJ" /></th>
					<th><fmt:message key="publisher.name" /></th>
					<th><fmt:message key="actions.link" /></th>
				</tr>
				<c:forEach var="editora" items="${requestScope.listaEditoras}">
					<tr>
						<td><c:out value="${editora.id}" /></td>
						<td><c:out value="${editora.CNPJ}" /></td>
						<td><c:out value="${editora.nome}" /></td>
						<td><a
							href="/${sessionScope.contextPath}/editoras/edicao?id=<c:out value='${editora.id}' />">
								<fmt:message key="publishers.update" />
						</a> <c:if test="${editora.qtdeLivros == 0}">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a
									href="/${sessionScope.contextPath}/editoras/remocao?id=<c:out value='${editora.id}' />"
									onclick="return confirm('<fmt:message key="confirm.link" />');">
									<fmt:message key="publishers.delete" />
								</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</fmt:bundle>

</html>