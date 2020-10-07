<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table border="1">
	<caption>
		<c:choose>
			<c:when test="${livro != null}">
                            Edição
                        </c:when>
			<c:otherwise>
                            Cadastro
                        </c:otherwise>
		</c:choose>
	</caption>
	<c:if test="${livro != null}">
		<input type="hidden" name="id" value="${livro.id}" />
	</c:if>
	<tr>
		<td><label for="titulo">Título</label></td>
		<td><input type="text" id="titulo" name="titulo" size="45"
			required value="${livro.titulo}" /></td>
	</tr>
	<tr>
		<td><label for="autor">Autor</label></td>
		<td><input type="text" id="autor" name="autor" size="45" required
			value="${livro.autor}" /></td>
	</tr>
	<tr>
		<td><label for="editora">Editora</label></td>
		<td><select id="editora" name="editora">
				<c:forEach items="${editoras}" var="editora">
					<option value="${editora.key}"
						${livro.editora.nome==editora.value ? 'selected' : '' }>
						${editora.value}</option>
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><label for="ano">Ano</label></td>
		<td><input type="number" id="ano" name="ano" size="5" required
			min="1500" value="${livro.ano}" /></td>
	</tr>
	<tr>
		<td><label for="preco">Preço</label></td>
		<td><input type="number" id="preco" name="preco" required
			min="0.01" step="any" size="5" value="${livro.preco}" /></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Salva" /></td>
	</tr>
</table>