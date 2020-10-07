<%@ page import="br.ufscar.dc.dsw.beans.Usuario" %>
<%
String nomeLogin = request.getParameter("usuario");
String senha = request.getParameter("senha");
if(senha.equals(nomeLogin)) {
    Usuario usuario = new Usuario();
    usuario.setNome("Steve Jobs");
    usuario.setNomeLogin(nomeLogin);
    usuario.setSenha(senha);
    session.setAttribute("usuarioLogado", usuario);
%>
<jsp:forward page="principal.jsp" />
<% }
else { %>
<jsp:forward page="index.jsp" />
<% } %>
