## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**

- - -

#### 06 - JSP Standard Tag Library (JSTL) 

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/ELJSP-v2)

- - -



1. Abrir projeto do Roteiro03-05 (*Expression Language)* e adicionar biblioteca **JSTL** via Maven, adicionando as seguintes linhas ao arquivo pom.xml:

   ```xml
   <dependency>
       <groupId>jakarta.servlet.jsp.jstl</groupId>
   	<artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
   	<version>3.0.0</version>
   </dependency>
   <dependency>
   	<groupId>org.glassfish.web</groupId>
   	<artifactId>jakarta.servlet.jsp.jstl</artifactId>
   	<version>3.0.1</version>
   </dependency>
   ```

2. Adicionar a biblioteca Native2Ascii (https://native2ascii.net/) via Maven, adicionando as seguintes linhas ao arquivo pom.xml:

   ```xml
   <plugin>
   	<groupId>org.codehaus.mojo</groupId>
   	<artifactId>native2ascii-maven-plugin</artifactId>
   	<version>1.0-beta-1</version>
   	<executions>
   		<execution>
   			<id>native2ascii-utf8-resources</id>
   			<goals>
   				<goal>native2ascii</goal>
   			</goals>
   			<configuration>
   				<dest>${project.build.directory}/classes</dest>
   				<src>${project.resources[0].directory}</src>
   				<encoding>UTF-8</encoding>
   			</configuration>
   		</execution>
   	</executions>
   </plugin>
   ```

<div style="page-break-after: always"></div>

3. Modificar o arquivo **index.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
   <!DOCTYPE html>
   <html>
       <head>
           <title>Login page</title>
       </head>
       <body>
           <fmt:bundle basename="messages">
               <form action="login.jsp" method="POST">
                   <fieldset >
                       <legend><fmt:message key="login"/></legend>
                       <fmt:message key="user"/><input type="text" name="usuario" /><br/>
                       <fmt:message key="password"/><input type="password" name="senha" /><br/>
                       <input type="submit" value="<fmt:message key="login"/>" />
                   </fieldset>
               </form>
           </fmt:bundle>
       </body>
   </html>
   ```

4. Crie o arquivo de propriedades (no diretório **src/main/resources**) messages (e as variantes messages_en, message_fr e messages_ja)

   **Obs: Assegure-se que esses arquivos são salvos no formato UTF-8**

   4.1. messages.properties

   ```properties
   login = Login
   user = Usuário 
   password = Senha:
   ```
   
   4.2. messages_en.properties

   ```properties
   login = Login
   user = User: 
   password = Password:
   ```
   
   4.3. messages_fr.properties

   ```properties
   login = Login
   user = Identifiant：
   password = Mot de passe：
   ```
   
   4.4. messages_ja.properties

   ```properties
   login = ログイン
   user = ユーザー名:
   password = パスワード：
   ```
   
5. Modificar o arquivo **principal.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <c:set var="local" value="Principal" scope="page" />
   <c:set var="acessos" scope="page">
       <table border="1">
           <tr><td>Acessos: 34552</td></tr>
       </table>
   </c:set>
   <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
   <html>
   <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>Principal</title>
   </head>
   <body>
       Bem-vindo ${sessionScope.usuarioLogado.nome}
       (${sessionScope.usuarioLogado.nomeLogin})!
       <br />
       Seu último acesso foi em ${sessionScope.usuarioLogado.ultimoAcesso}!<br />
       Sua senha é ${sessionScope.usuarioLogado.senha}
       <hr>
       Você está em: ${pageScope.local}
       <hr>
       Menu de opções:<br /><br />
       Conteúdo da página<br />
       Conteúdo da página<br />
       <hr>
       ${pageScope.acessos}
   </body>
   </html>
   ```

6. Modificar arquivo **login.jsp** para remover os *scriptlets*

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <c:choose>
       <c:when test="${param.usuario == param.senha}">
           <jsp:useBean id="usuarioLogado" class="br.ufscar.dc.dsw.beans.Usuario" scope="session" />
           <jsp:setProperty name="usuarioLogado" property="nome" value="Fulano da Silva" />
           <jsp:setProperty name="usuarioLogado" property="nomeLogin" param="usuario" />
           <jsp:setProperty name="usuarioLogado" property="senha" />
           <jsp:forward page="principal.jsp"/>
       </c:when>
       <c:otherwise>
           <jsp:forward page="index.jsp" />
       </c:otherwise>
   </c:choose>
   ```
   
7. Criar uma classe **br.ufscar.dc.dsw.beans.ItemMenu**, com duas propriedades String: **nome** e **link**

   ```java
   package br.ufscar.dc.dsw.beans;
   
   public class ItemMenu {
       private String nome;
       private String link;
   
       public ItemMenu(String nome, String link) {
           this.nome = nome;
           this.link = link;
       }
       
       public void setNome(String nome) {
           this.nome = nome;
       }
       
       public String getNome() {
           return nome;
       }
       
       public void setLink(String link) {
           this.link = link;
       }
       
       public String getLink() {
           return link;
       }
   }
   ```

8. Criar uma classe **br.ufscar.dc.dsw.beans.Menu**:

   ```java
   package br.ufscar.dc.dsw.beans;
   import java.util.ArrayList;
   import java.util.List;
   
   public class Menu {
   
       List<ItemMenu> itensMenu;
       
       public Menu() {
           itensMenu = new ArrayList<>();
           itensMenu.add(new ItemMenu("Principal", "principal.jsp"));
           itensMenu.add(new ItemMenu("Notícias", "noticias.jsp"));
           itensMenu.add(new ItemMenu("Produtos", "produtos.jsp"));
           itensMenu.add(new ItemMenu("Fale conosco", "contato.jsp"));
       }
       
       public List<ItemMenu> getItensMenu() {
           return itensMenu;
       }
   }
   ```

   

   

9. Modificar o arquivo **principal.jsp** para implementar um menu dinâmico

   ```jsp
   Menu de opções:<br/><br/>
   <jsp:useBean id="menu" class="br.ufscar.dc.dsw.beans.Menu" scope="application" />
   <c:forEach var="im" items="${menu.itensMenu}">
     <a href="${im.link}">${im.nome}</a><br/>
   </c:forEach>
   ```

10. Testar a aplicação (limpar, construir e re-implantar)

11. Fim



#### Leituras adicionais

- - -

- Usando Taglibs (Caelum: Curso Java para Desenvolvimento Web)
  
  https://www.caelum.com.br/apostila-java-web/usando-taglibs

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp

- Java™ Internationalization Support
  
  https://docs.oracle.com/javase/8/docs/technotes/guides/intl/index.html
  
  
