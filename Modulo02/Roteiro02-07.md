## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -
#### 07 - Atributos no escopo de aplicação
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/EscopoAplicacao)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: EscopoAplicacao 

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

      - Abrir um terminal dentro da pasta do projeto (**EscopoAplicacao**) e executar o seguinte comando: 

        ```sh
        mvn -N io.takari:maven:wrapper
        ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar três servlets no pacote **br.ufscar.dc.dsw**: **ServletA**, **ServletB** e **ServletC**

3. O **ServletA** adiciona um atributo no **ServletContext**, e mostra na página, caso esteja definido

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/ServletA"})
   public class ServletA extends HttpServlet {
   
       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
       
           getServletContext().setAttribute("valor", 100);
       
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("Página gerada pelo ServletA: ");
               if (getServletContext().getAttribute("valor") == null) {
                   out.println("Valor não encontrado!");
               } else {
                   Integer i = (Integer) getServletContext().getAttribute("valor");
                   out.println("Valor = " + i);
               }
           }
       }
       
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
       
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

4. O **ServletB** faz apenas a exibição na página, de forma similar ao **ServletA**

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/ServletB"})
   public class ServletB extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
   
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("Página gerada pelo ServletB: ");
               if (getServletContext().getAttribute("valor") == null) {
                   out.println("Valor não encontrado!");
               } else {
                   Integer i = (Integer) getServletContext().getAttribute("valor");
                   out.println("Valor = " + i);
               }
           }
       }
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

5. O **ServletC** faz apenas a exibição na página, de forma similar ao **ServletA**

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/ServletC"})
   public class ServletC extends HttpServlet {
   
   	private static final long serialVersionUID = 1L;
   
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("Página gerada pelo ServletC: ");
               if (getServletContext().getAttribute("valor") == null) {
                   out.println("Valor não encontrado!");
               } else {
                   Integer i = (Integer) getServletContext().getAttribute("valor");
                   out.println("Valor = " + i);
               }
           }
       }
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

6. Testar a aplicação

   6.1. Abrir **ServletB** e/ou **ServletC**, e notar que não existe valor
   
   6.2. Agora abrir o **ServletA** antes
   
   6.3. Abrir **ServletC** em outro navegador (ou aba anônima), para simular outro cliente

7. Fim

   

#### Leituras adicionais

- - -

- Attribute in Servlet

  https://www.javatpoint.com/attribute

- Servlets (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/servlets

- Servlet Tutorial

  https://www.javatpoint.com/servlet-tutorial

- Introduction to Java Servlets

  https://www.baeldung.com/intro-to-servlets
