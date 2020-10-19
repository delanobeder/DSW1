<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<body>
    <h2>Hello World!</h2>

    <%
        String numValue = request.getParameter("num");
    	int num = (numValue == null) ? 5 : Integer.parseInt(numValue);
    
        String nome = "Fulano";
        String param = request.getParameter("nome"); 
        if (param != null) {
        	nome = param; 
        }
        
        for(int i=0; i < num; i++) {
    %>
    Olá <%= nome %>!<br />
    <% } %>
</body>

</html>
