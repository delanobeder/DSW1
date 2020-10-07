<%@ page contentType="text/html" pageEncoding="UTF-8"%>
Bem-vindo <%=request.getParameter("nomeCompleto")%>!
<br/>
Seu último acesso foi em <%=request.getParameter("ultimoAcesso")%>!
<br>
Você está em: <%= voceEstaEm %>!
<br>
<hr>
