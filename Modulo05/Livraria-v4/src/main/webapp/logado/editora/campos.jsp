<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table border="1">
	<caption>
		<c:choose>
			<c:when test="${editora != null}">
				<fmt:message key="publishers.update" />
			</c:when>
			<c:otherwise>
				<fmt:message key="publishers.create" />
			</c:otherwise>
		</c:choose>
	</caption>
	<c:if test="${editora != null}">
		<input type="hidden" name="id" value="<c:out value='${editora.id}' />" />
	</c:if>
	<tr>
		<td><label for="CNPJ"> <fmt:message key="publisher.CNPJ" />
		</label></td>
		<td><input type="text" id="CNPJ" name="CNPJ" size="18" required
			value="<c:out value='${editora.CNPJ}' />" /></td>
	</tr>
	<tr>
		<td><label for="nome"><fmt:message key="publisher.name" />
		</label></td>
		<td><input type="text" name="nome" size="45" required
			value="<c:out value='${editora.nome}' />" /></td>
	</tr>

	<tr>
		<td colspan="2" align="center"><input type="submit"
			value="<fmt:message key="save.link" />" /></td>
	</tr>
</table>