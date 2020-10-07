<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<body>
    <h2>Hello World!</h2>

    <%
        int num = Integer.parseInt(request.getParameter("num"));
        String nome = request.getParameter("nome");
        for(int i=0; i < num; i++) {
      %>
    Olá <%=nome%>!<br />
    <% } %>
</body>

</html>
