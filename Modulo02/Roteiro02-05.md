## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -
#### 05 - Redirecionamento, Encaminhamento e Inclusão
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/Navegacao)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: Navegacao 

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

      - Abrir um terminal dentro da pasta do projeto (**Navegacao**) e executar o seguinte comando: 

        ```sh
        mvn -N io.takari:maven:wrapper
        ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Criar um arquivo pagina.html qualquer

   ```html
   <html>
   <body>
           <h2>Hello World!</h2>
   </body>
   </html>
   ```

3. Adicionar um novo servlet **br.ufscar.dc.dsw.RedirectServlet**, com o seguinte código:

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/RedirectServlet"})
   public class RedirectServlet extends HttpServlet {
   
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           response.sendRedirect("pagina.html");        
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

4. Executar (`mvn tomcat7:redeploy`) e acessar a URL http://localhost:8080/Navegacao/RedirectServlet e observar que mudou a URL no *browser* (navegador)

5. Adicionar um novo servlet **br.ufscar.dc.dsw.ForwardServlet**, com o seguinte código:

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = { "/ForwardServlet" })
   public class ForwardServlet extends HttpServlet {
   
       protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.getRequestDispatcher("pagina.html").forward(request, response);
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
   
6. Testar (http://localhost:8080/Navegacao/ForwardServlet) e observar que não mudou a URL no *browser* (navegador)

7. Adicionar um novo servlet **br.ufscar.dc.dsw.PrintParamsServlet**, que imprime todos os parâmetros

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   import java.util.Map;
   import java.util.Map.Entry;
   import java.util.Set;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/PrintParams"})
   public class PrintParamsServlet extends HttpServlet {
   
       protected void processRequest(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Servlet PrintParamsServlet</title>");
               out.println("</head>");
               out.println("<body>");
               Map<String, String[]> mapaDeParametros = request.getParameterMap();
               if (!mapaDeParametros.isEmpty()) {
                   out.println("<p>Parâmetros:</p>");
                   Set<Entry<String, String[]>> conjuntoDeParametros = mapaDeParametros.entrySet();
                   for (Entry<String, String[]> parametro : conjuntoDeParametros) {
                       out.println(parametro.getKey() + ":");
                       for (String v : parametro.getValue()) {
                           out.println("[" + v + "] ");
                       }
                       out.println("<br/>");
                   }
               } else {
                   out.println("<p>Nenhum parâmetro foi enviado</p>");
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

8. Modificar os servlets **RedirectServlet** e **ForwardServlet** para chamar o novo servlet **PrintParamsServlet**. Testar e verificar quando os parâmetros são passados.

     ```java
   // No RedirectServlet
   response.sendRedirect("PrintParams");
     
   // No ForwardServlet
   request.getRequestDispatcher("PrintParams").forward(request, response);
   ```

9. Criar dois arquivos html

   - cabecalho.html

     ```html
       <h1>Cabecalho</h1> 
     ```

   - rodape.html

     ```html
       <h1>Rodape</h1>
     ```

10. Criar um novo servlet, chamado **br.ufscar.dc.dsw.IncludeServlet**, que faz a inclusão dos dois HTMLs gerados no passo anterior

    ```java
    package br.ufscar.dc.dsw;
    
    import java.io.IOException;
    import java.io.PrintWriter;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    @WebServlet(urlPatterns = {"/IncludeServlet"})
    public class IncludeServlet extends HttpServlet {
    
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet IncludeServlet</title>");            
                out.println("</head>");
                out.println("<body>");
                request.getRequestDispatcher("cabecalho.html").include(request, response);
                out.println("<h2>Servlet IncludeServlet at " + request.getContextPath() + "</h2>");
                request.getRequestDispatcher("rodape.html").include(request, response);
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

11. Testar a aplicação (http://localhost:8080/Navegacao/IncludeServlet)

12. Fim



#### Leituras adicionais

- - -

- Servlet Redirect vs Forward

  https://www.baeldung.com/servlet-redirect-forward
  
- Servlets (Caelum: Curso Java para Desenvolvimento Web)
  
  https://www.caelum.com.br/apostila-java-web/servlets

- Servlet Tutorial

  https://www.javatpoint.com/servlet-tutorial

