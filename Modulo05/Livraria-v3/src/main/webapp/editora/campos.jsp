
<c:if test="${editora != null}">
	<input type="hidden" name="id" value="<c:out value='${editora.id}' />" />
</c:if>

<div class="mb-3 row">
		<label class="form-label" for="CNPJ"><fmt:message key="publisher.CNPJ" /></label>
		<input class="form-control" type="text" id="CNPJ" name="CNPJ" size="18" required value="<c:out value='${editora.CNPJ}' />" />
</div>

<div class="mb-3 row">
	<label class="form-label" for="nome"><fmt:message key="publisher.name" /></label>
	<input class="form-control" type="text" name="nome" size="45" required value="<c:out value='${editora.nome}' />" />
</div>

<div>
	<button type="submit" class="btn btn-primary mb-3"><fmt:message key="save.link" /></button>
</div>