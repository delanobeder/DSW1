<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<fmt:bundle basename="message">

    <head>
        <title>
            <fmt:message key="page.title" />
        </title>
        <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" defer></script>
        <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="d-flex flex-column min-h-100">
        <header>
            <!-- Fixed navbar -->
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-light">
                <div class="container-fluid">
                    <h3>Desenvolvimento de Software para Web 1</h3>
                </div>
            </nav>
        </header>
        <main class="flex-shrink-0">
            <div class="container" style="padding: 90px 15px 0;">
                <div class="mb-3 row">
                    <h3>
                        <a href="livros">CRUD
                            <fmt:message key="books.entity" />
                        </a>
                    </h3>
                </div>
                <div class="mb-3 row">
                    <h3>
                        <a href="editoras">CRUD
                            <fmt:message key="publishers.entity" />
                        </a>
                    </h3>
                </div>
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

