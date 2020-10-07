<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${param.usuario == param.senha}">
        <jsp:useBean id="usuarioLogado" class="br.ufscar.dc.dsw.beans.Usuario" scope="session" />
        <jsp:setProperty name="usuarioLogado" property="nome" value="Fulano da Silva" />
        <jsp:setProperty name="usuarioLogado" property="nomeLogin" param="usuario" />
        <jsp:setProperty name="usuarioLogado" property="senha" />
        <jsp:forward page="principal.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="index.jsp" />
    </c:otherwise>
</c:choose>
