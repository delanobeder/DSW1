<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${livro != null}">
	<input type="hidden" name="id" value="${livro.id}" />
</c:if>
<div class="mb-3 row">
	<label class="form-label" for="titulo">Título</label>
	<input class="form-control" type="text" id="titulo" name="titulo" size="45" required value="${livro.titulo}" />
</div>
<div class="mb-3 row">
	<label class="form-label" for="autor">Autor</label>
	<input class="form-control" type="text" id="autor" name="autor" size="45" required value="${livro.autor}" />
</div>
<div class="mb-3 row">
	<label class="form-label" for="editora">Editora</label>
	<select class="form-control" name="editora">
		<c:forEach items="${editoras}" var="editora">
			<option value="${editora.key}" ${livro.editora.nome==editora.value ? 'selected' : '' }>
				${editora.value}</option>
		</c:forEach>
	</select>
</div>
<div class="mb-3 row">
	<label class="form-label" for="ano">Ano</label>
	<input class="form-control" type="number" id="ano" name="ano" size="5" required min="1500" value="${livro.ano}" />
</div>
<div class="mb-3 row">
	<label class="form-label" for="preco">Preço</label>
	<input class="form-control" type="number" id="preco" name="preco" required min="0.01" step="any" size="5"
		value="${livro.preco}" />
</div>
<div>
	<button type="submit" class="btn btn-primary mb-3">Salvar</button>
</div>