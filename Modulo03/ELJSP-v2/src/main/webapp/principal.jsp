<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="local" value="Principal" scope="page" />
<c:set var="acessos" scope="page">
    <table border="1">
        <tr>
            <td>Acessos: 34552</td>
        </tr>
    </table>
</c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Principal</title>
</head>

<body>
    Bem-vindo ${sessionScope.usuarioLogado.nome}
    (${sessionScope.usuarioLogado.nomeLogin})!
    <br />
    Seu último acesso foi em ${sessionScope.usuarioLogado.ultimoAcesso}!<br />
    Sua senha é ${sessionScope.usuarioLogado.senha}
    <hr>
    Você está em: ${pageScope.local}
    <hr>
    Menu de opções:<br/><br/>
    <jsp:useBean id="menu" class="br.ufscar.dc.dsw.beans.Menu" scope="application" />
    <c:forEach var="im" items="${menu.itensMenu}">
        <a href="${im.link}">${im.nome}</a><br/>
    </c:forEach>

    <hr>
    ${pageScope.acessos}
</body>

</html>
