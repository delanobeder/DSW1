<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login page</title>
    </head>
    <body>
        <fmt:bundle basename="messages">
            <form action="login.jsp" method="POST">
                <fieldset >
                    <legend><fmt:message key="login"/></legend>
                    <fmt:message key="user"/><input type="text" name="usuario" /><br/>
                    <fmt:message key="password"/><input type="password" name="senha" /><br/>
                    <input type="submit" value="<fmt:message key="login"/>" />
                </fieldset>
            </form>
        </fmt:bundle>
    </body>
</html>
