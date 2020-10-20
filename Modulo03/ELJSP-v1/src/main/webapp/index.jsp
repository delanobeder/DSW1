<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
    </head>
    <body>
        <form action="login.jsp" method="POST">
            <fieldset >
                <legend>Login</legend>
                Usuário: <input type="text" name="usuario" /><br/>
                Senha: <input type="password" name="senha" /><br/>
                <input type="submit" value="Login" />
            </fieldset>
        </form>
    </body>
</html>
