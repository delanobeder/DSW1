<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<fmt:bundle basename="message">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="page.title" /></title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" defer></script>
        <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="d-flex flex-column h-100">
        <header>
            <!-- Fixed navbar -->
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-light">
                <div class="container-fluid">
                    <h3><fmt:message key="page.title" /></h3>
                </div>
            </nav>
        </header>
        <main class="flex-shrink-0">
            
            <div class="container" style="padding: 90px 15px 0;">
                <c:if test="${mensagens.existeErros}">
                    <div class="alert alert-danger" role="alert">
                        <c:forEach var="erro" items="${mensagens.erros}">
                            ${erro}</br>
                        </c:forEach>
                    </div>
                </c:if>    
                <form class="row g-2" action="index.jsp" method="POST">
                    <div class="mb-3 row">
                        <label for="login" class="form-label">
                            <fmt:message key="user.login"/>
                        </label>
                        <input type="text" class="form-control" id="login" name="login" value="${param.login}">
                    </div>
                    <div class="mb-3 row">
                        <label for="senha" class="form-label">
                            <fmt:message key="user.password" />
                        </label>
                        <input type="password" class="form-control" id="senha" name="senha">
                    </div>
                    <div>
                        <button type="submit" name="bOK" class="btn btn-primary mb-3">
                            <fmt:message key="submit" />
                        </button>
                    </div>
                </form>
            </div>
        </main>
        <footer class="fixed-bottom py-3 bg-light">
            <div class="container text-center">
                Departamento de Computação - Universidade Federal de São Carlos
            </div>
        </footer>
    </body>
</fmt:bundle>
</html>