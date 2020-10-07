<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
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

</body>

</html>
