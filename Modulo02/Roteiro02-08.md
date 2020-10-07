## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**
- - -

#### 08 - Atributos no escopo de sessão
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/EscopoSessao)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: EscopoSessao

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**EscopoSessao**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar um **servlet** chamado **br.ufscar.dc.dsw.ArmazenarNaSessao**, que pega um parâmetro chamado **nome** e armazena na sessão. Em seguida, imprime na página o nome armazenado

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/ArmazenarNaSessao"})
   public class ArmazenarNaSessao extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
       
       protected void processRequest(HttpServletRequest request,
               HttpServletResponse response)
               throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Servlet ArmazenarNaSessao</title>");
               out.println("</head>");
               out.println("<body>");
               String nome = request.getParameter("nome");
               request.getSession().setAttribute("nome", nome);
               out.println("Nome armazenado na sessão:" + nome);
               out.println("</body>");
               out.println("</html>");
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

3. Criar um **servlet** denominado **br.ufscar.dc.dsw.ExibirSessao** que exibe os dados da sessão

   ```java
   package br.ufscar.dc.dsw;
   import java.io.IOException;
   import java.io.PrintWriter;
   import java.text.SimpleDateFormat;
   import java.util.Date;
   import java.util.Enumeration;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import javax.servlet.http.HttpSession;
   
   @WebServlet(urlPatterns = {"/ExibirSessao"})
   public class ExibirSessao extends HttpServlet {
   
       protected void processRequest(
               HttpServletRequest request,
               HttpServletResponse response)
               throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Servlet ExibirSessao</title>");
               out.println("</head>");
               out.println("<body>");
       
               out.println("Dados da sessão:<br/>");
               HttpSession sessao = request.getSession();
               String idSessao = sessao.getId();
               Date dataCriacao = new Date(sessao.getCreationTime());
               Date dataUltimoAcesso = new Date(sessao.getLastAccessedTime());
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:SSS");
       
               out.println("Id:" + idSessao + "<br/>");
               out.println("Data criação:" + sdf.format(dataCriacao) + "<br/>");
               out.println("Data último acesso:" + sdf.format(dataUltimoAcesso) + "<br/>");
               out.println("Atributos:<br/>");
               Enumeration<String> nomesAtributos = sessao.getAttributeNames();
               while (nomesAtributos.hasMoreElements()) {
                   String nomeAtributo = nomesAtributos.nextElement();
                   Object valor = sessao.getAttribute(nomeAtributo);
                   out.println(nomeAtributo + "=" + valor + "<br/>");
               }
               out.println("</body>");
               out.println("</html>");
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

4. Testar a aplicação

   4.1. Criar duas sessões diferentes usando *browsers* diferentes
   
    http://localhost:8080/EscopoSessao/ArmazenarNaSessao?nome=Fulano

   4.2. Exibir as duas sessões e verificar que são diferentes
   
   http://localhost:8080/EscopoSessao/ExibirSessao

   

   

   

   

   

   

   

5. Atualizar o arquivo **web.xml**

   ```xml
   <web-app>
     <display-name>Archetype Created Web Application</display-name>
     <session-config>
       <session-timeout>1</session-timeout> <!-- 1 minuto para invalidar a sessão -->
     </session-config>
   </web-app>
   ```

6. Fim



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
