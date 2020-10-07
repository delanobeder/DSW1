## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -

#### 06 - Atributos no escopo de requisição
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/EscopoRequisicao)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: EscopoRequisicao 

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

      - Abrir um terminal dentro da pasta do projeto (**EscopoRequsicao**) e executar o seguinte comando: 

        ```sh
        mvn -N io.takari:maven:wrapper
        ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar três servlets no pacote **br.ufscar.dc.dsw**: **ServletA**, **ServletB** e **ServletC**
3. O **ServletA** adiciona um atributo (**valor**) na requisição e faz *forward* para o **ServletB**

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/ServletA"})
   public class ServletA extends HttpServlet {
   
   	private static final long serialVersionUID = 1L;
   	
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   	    request.setAttribute("valor", 100);
   	    request.getRequestDispatcher("ServletB").forward(request, response);
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

4. O **ServletB** recupera o atributo (**valor**), modifica-o e o imprime, além de incluir o **ServletC**

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
     
     	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Servlet ServletB</title>");
               out.println("</head>");
               out.println("<body>");
               Integer i = (Integer) request.getAttribute("valor");
               if (i == null) {
                   i = 0;
               }
               i = i + 30;
               request.setAttribute("valor", i);
               out.println("Conteúdo gerado no ServletB: Valor = " + i + "<br/>");
               request.getRequestDispatcher("ServletC").include(request, response);
               out.println("</body>");
               out.println("</html>");
           } catch(Exception e) {
           }
       }
     
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
     
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

5. O **Servlet C** recupera o atributo (**valor**), modifica-o e o imprime

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
     
     	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             PrintWriter out = response.getWriter();
             Integer i = (Integer) request.getAttribute("valor");
             i = i + 1000;
             request.setAttribute("valor", i);
             out.println("Conteúdo gerado no ServletC: Valor = " + i + "<br/>");
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

6. Testar a aplicação e verificar que o valor está sendo passado de um servlet para outro. Se chamar o **ServletB** direto irá dar uma exceção. Como corrigir esse erro?

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
