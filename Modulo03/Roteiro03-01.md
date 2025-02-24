## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**

- - -

#### 01 - Aplicação "Alô Mundo" usando JSP

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/AloMundoJSP)

- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw

  - **artifactId**: AloMundoJSP

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento

    - Abrir um terminal dentro da pasta do projeto (**AloMundoJSP**) e executar o seguinte comando:

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**jakarta.servlet**, **jakarta.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores


2. Alterar o conteúdo do arquivo **index.jsp** que já é criado por *default*

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
       </body>
   </html>
   ```

3. Criar um servlet **AloMundo** qualquer (com o mesmo texto do JSP)

4. Exibir o **servlet** gerado, dentro da pasta work do TomCat

<div style="page-break-after: always"></div>

5. Adicionar um código no servlet, para fazer um for de 1 a 10

   ```
   for(int i=0;i<10;i++) {
       String linha = "Linha "+i;
       out.println(i + ": " + linha + "<br/>");
   }
   ```

6. Adicionar um código equivalente no JSP, para comparar

   ```jsp
   <% for(int i=0;i<10;i++) {
          String linha = "Linha "+i;
   %>
          <%= i %> : <%= linha %> <br/>
   <% } %>
   ```

7. Rodar e ver o efeito

8. Verificar o **servlet** gerado

9. Fim



#### Leituras adicionais

- - -

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp
