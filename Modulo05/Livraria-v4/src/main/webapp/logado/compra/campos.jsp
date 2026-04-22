<div class="row mt-3" align="center">
	<table class="table table-striped">

		<tr>
			<th></th>
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
		</tr>
		<c:forEach var="livro" items="${livros}">
			<tr>
				<td style="width: 10%; text-align: center"><input type="radio" id="${livro.key}" name="livro"
						value="${livro.key}" required></td>
				<td>${livro.value.titulo}</td>
				<td>${livro.value.editora.nome}</td>
				<td>${livro.value.autor}</td>
				<td>${livro.value.ano}</td>
				<td>${livro.value.preco}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="mt-3" align="center">
	<button type="submit" class="btn btn-primary mb-3">
		<fmt:message key="save.link" />
	</button>
</div>