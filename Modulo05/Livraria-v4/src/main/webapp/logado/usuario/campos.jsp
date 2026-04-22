
<c:if test="${usuario != null}">
	<input type="hidden" name="id" value="<c:out value='${usuario.id}' />" />
</c:if>
<div class="mb-3 row">
	<label for="nome" class="form-label">
		<fmt:message key="user.name" />
	</label>
	<input type="text" class="form-control" id="nome" name="nome" size="45" required
		value="<c:out value='${usuario.nome}' />" />
</div>

<div class="mb-3 row">
	<label for="login" class="form-label">
		<fmt:message key="user.login" />
	</label>
	<input type="text" class="form-control" id="login" name="login" size="20" required
		value="<c:out value='${usuario.login}' />" />
</div>

<div class="mb-3 row">
	<label for="senha" class="form-label">
		<fmt:message key="user.password" />
	</label>
	<input type="text" class="form-control" name="senha" size="20" required
		value="<c:out value='${usuario.senha}' />" />
</div>

<div class="mb-3 row">
	<label for="papel" class="form-label">
		<fmt:message key="user.role" />
	</label>

	<select name="papel" class="form-control">
		<option value="ADMIN" ${usuario.papel=="ADMIN" ? 'selected="selected"' : '' }>ADMIN</option>
		<option value="USER" ${usuario.papel=="USER" ? 'selected="selected"' : '' }>USER</option>
	</select>
</div>
<div>
	<button type="submit" class="btn btn-primary mb-3">
		<fmt:message key="save.link" />
	</button>
</div>