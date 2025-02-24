## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**

- - -

#### 05 - *Expression Language* 

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/ELJSP-v1)

- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: ELJSP

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**ELJSP**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**jakarta.servlet**, **jakarta.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar uma classe **br.ufscar.dc.dsw.beans.Usuario**, com o seguinte código

   ```java
   package br.ufscar.dc.dsw.beans;
   
   import java.text.SimpleDateFormat;
   import java.util.Date;
   
   public class Usuario {
       private String nomeLogin, nome, senha;
       private Date ultimoAcesso;
   
       public Usuario() {
           ultimoAcesso = new Date();
       }
       
       public void setNomeLogin(String nomeLogin) {
           this.nomeLogin = nomeLogin;
       }
       
       public String getNomeLogin() {
           return nomeLogin;
       }
       
       public String getNome() {
           return nome;
       }
       
       public void setNome(String nome) {
           this.nome = nome;
       }
       
       public String getSenha() {
           return senha;
       }
       
       public void setSenha(String senha) {
           this.senha = senha;
       }
       
       public String getUltimoAcesso() {
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss:SSS");
           return sdf.format(ultimoAcesso);
       }
   }
   ```

3. Criar a página **index.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Login page</title>
       </head>
       <body>
           <form action="login.jsp" method="POST">
               <fieldset >
                   <legend>Login</legend>
                   Usuário: <input type="text" name="usuario" /><br/>
                   Senha: <input type="password" name="senha" /><br/>
                   <input type="submit" value="Login" />
               </fieldset>
           </form>
       </body>
   </html>
   ```

4. Criar a página **login.jsp**

   ```jsp
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
   		<jsp:forward page="principal.jsp"/>
   <% }
      else { %>
   		<jsp:forward page="index.jsp" />
   <% } %>
   ```

5. Criar a página **principal.jsp** (lembrar de adicionar `<%@ page isELIgnored="false"%>`)

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Principal</title>
       </head>
       <body>
           Bem-vindo ${sessionScope.usuarioLogado.nome}
           (${sessionScope.usuarioLogado.nomeLogin})!
           <br/>
           Seu último acesso foi em ${sessionScope.usuarioLogado.ultimoAcesso}!<br/>
           Sua senha é ${sessionScope.usuarioLogado.senha}
       </body>
   </html>
   ```

6. Testar

7. Fim



#### Leituras adicionais

- - -

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp

  
