<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>

<body>
    <h1>Alô Mundo</h1>
    
    <h1> <%= new Date() %></h1>
    
    <% for (int i = 0; i < 10; i++) {
            String linha = "Linha " + i;
    %>
    <%= i%>: <%= linha%> <br />
    <% }%>
    
</body>

</html>
