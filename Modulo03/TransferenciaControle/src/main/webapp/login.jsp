<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = request.getParameter("usuario");
    String senha = request.getParameter("senha");
    if (usuario.equalsIgnoreCase(senha)) {
%>
<jsp:forward page="sucesso.jsp">
    <jsp:param name="nomeCompleto" value="Steve Jobs" />
    <jsp:param name="ultimoAcesso" value="05/04/2010" />
</jsp:forward>
<% } else { %>
<jsp:forward page="index.jsp" />
<% }%>
