## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**

- - -

#### 04 - Transferindo controle para outros recursos web

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/TransferenciaControle)

- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: TransferenciaControle

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**TransferenciaControle**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Adicionar um formulário para *login* em **index.jsp**

   ```jsp
   <body>
       <form action="login.jsp" method="POST">
           <fieldset>
               <legend>Login</legend>
               Usuário: <input type="text" name="usuario" /><br/>
               Senha: <input type="password" name="senha" /><br/>
               <input type="submit" value="Login" />
           </fieldset>
       </form>
   </body>
   ```

<div style="page-break-after: always"></div>

3. Criar arquivo **login.jsp**, com a lógica de controle de *login*

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%
   	String usuario = request.getParameter("usuario");
   	String senha = request.getParameter("senha");
   	if(usuario.equalsIgnoreCase(senha)) {
   %>
   		<jsp:forward page="sucesso.jsp">
       		<jsp:param name="nomeCompleto" value="Fulano da Silva" />
       		<jsp:param name="ultimoAcesso" value="29/03/2019" />
   		</jsp:forward>
   <% } else { %>
   		<jsp:forward page="index.jsp" />
   <% } %>
   ```

4. Criar a página **sucesso.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
      "http://www.w3.org/TR/html4/loose.dtd">
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>JSP Page</title>
       </head>
       <body>
           Bem-vindo <%= request.getParameter("nomeCompleto") %>!
           <br/>
           Seu último acesso foi em <%= request.getParameter("ultimoAcesso") %>!
       </body>
   </html>
   ```

5. Deploy e Testar a aplicação

6. Mostrar o código gerado para **login.jsp**, identificar a linha do *forward*, e como os parâmetros são adicionados ao *request*

7. Criar a página **cabecalho.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   Bem-vindo <%=request.getParameter("nomeCompleto")%>!
   <br/>
   Seu último acesso foi em <%=request.getParameter("ultimoAcesso")%>!
   <br>
   Você está em: <%= voceEstaEm %>!
   <br>
   <hr>
   ```

<div style="page-break-after: always"></div>

8. Criar a página **rodape.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page import="java.util.Date" %>
   <hr>
   <p align="center"><%= new Date() %></p>
   ```
   
   
   
9. Modificar a página **sucesso.jsp** para incluir os arquivos **cabecalho.jsp** e **rodape.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
       "http://www.w3.org/TR/html4/loose.dtd">
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>JSP Page</title>
       </head>
       <body>
           <% String voceEstaEm = "AT9 - Sala 197"; %>
           <%@include file="cabecalho.jsp" %>
           
           Menu de opções:<br/><br/>
           Conteúdo da página<br/>
           Conteúdo da página<br/>
           Conteúdo da página<br/>
           
           <jsp:include page="rodape.jsp"/>
       </body>
   </html>
   ```
   
10. Testar a aplicação 

    


#### Leituras adicionais

- - -

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp

  
