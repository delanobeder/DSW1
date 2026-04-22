<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Livraria Virtual</title>
    <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" defer></script>
    <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css" />
</head>

<body class="d-flex flex-column h-100">
    <header>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-light">
            <div class="container-fluid">
                <h3>Gerenciamento de Livros</h3>
            </div>
        </nav>
    </header>
    <main class="flex-shrink-0">

        <div class="container" style="padding: 90px 15px 0;">
            <div class="row mt-3" align="center">
                <h3>Erro</h3>
                <h4>
                    <%= exception.getMessage()%>
                </h4>
            </div>
        </div>
    </main>
    <footer class="fixed-bottom py-3 bg-light">
        <div class="container text-center">
            Departamento de Computação - Universidade Federal de São Carlos
        </div>
    </footer>
</body>

</html>