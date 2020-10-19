## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**
- - -

#### 03 - Objetos implícitos

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/ObjetosImplicitos)

-  - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: ObjetosImplicitos

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**ObjetosImplicitos**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar o arquivo **index.jsp** com o seguinte conteúdo

   ```jsp
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
   ```

3. Testar e verificar que ocorre erro quando não é passado nenhum parâmetro

<div style="page-break-after: always"></div>

4. Corrigir o problema e testar novamente sem parâmetros e com diferentes valores de “num” e “nome”

   ```jsp
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
   ```

   

5. Fim









#### Leituras adicionais

- - -

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp

  
