## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -

#### 04 - Obtendo informações de uma requisição (**HTTP Request**)
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/HttpRequest)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 

  - **artifactId**: HttpRequest 

    

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento

      - Abrir um terminal dentro da pasta do projeto (**HttpRequest**) e executar o seguinte comando: 

        ```sh
        mvn -N io.takari:maven:wrapper
        ```



2. Abra o diretório **HttpRequest** em um editor de texto (ex: Visual Studio Code). Adicionar a seguinte dependência e plugin ao arquivo **pom.xml** 
	
   ```xml
   <project ... >
   	<dependencies>
   		...
      		<dependency>
      			<groupId>javax.servlet</groupId>
         		<artifactId>javax.servlet-api</artifactId>
         		<version>4.0.0</version>
         		<scope>provided</scope>
      		</dependency>
      		<dependency>
        		<groupId>javax.servlet.jsp</groupId>
          		<artifactId>jsp-api</artifactId>
          		<version>2.1</version>
          		<scope>provided</scope>
       	</dependency>
       </dependencies>
   	... 
   	<build>
      		...
      		<plugins>
       		<plugin>
           		<groupId>org.apache.tomcat.maven</groupId>
            		<artifactId>tomcat7-maven-plugin</artifactId>
            		<version>2.2</version>
            		<configuration>
               		<url>http://localhost:8080/manager/text</url>
                 		<server>Tomcat9</server>
                  		<path>/${project.artifactId}</path>
            		</configuration>
         		</plugin>
      		</plugins>
      		<pluginManagement> (atenção para o local correto, NÃO é aqui dentro!)
         		<plugins>
            		...
         		</plugins>
      		</pluginManagement>
      		...
   	</build>
   	...   
   </project>
   ```
   
3. Adicionar um novo Servlet: **br.ufscar.dc.dsw.InterpretarRequestServlet**. Acrescentar o seguinte código no corpo do método de tratamento de requisição:

   ```java
   package br.ufscar.dc.dsw;
   
   import java.io.IOException;
   import java.io.PrintWriter;
   
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = { "/InterpretarRequestServlet" })
   public class InterpretarRequestServlet extends HttpServlet {
       private static final long serialVersionUID = 1L;
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
           try (PrintWriter out = response.getWriter()) {
               out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>Servlet InterpretarRequestServlet</title>");
               out.println("</head>");
               out.println("<body>");
               out.println("<h1>Servlet InterpretarRequestServlet at " + request.getContextPath() + "</h1>");
               String requestURL = request.getRequestURL().toString();
               String protocol = request.getProtocol();
               int port = request.getLocalPort();
               String queryString = request.getQueryString();
               out.println("Requisição: " + requestURL + "<br/>");
               out.println("Protocolo: " + protocol + "<br/>");
               out.println("Porta: " + port + "<br/>");
               out.println("Query: " + queryString + "<br/>");
               out.println("</body>");
               out.println("</html>");
           }
       }
   }
   ```

4. Executar/Deploy (```mvn tomcat:deploy```), e testar com diferentes exemplos de *queries*

5. Apresentar que é possível implementar os dois métodos, **GET** e **POST** (basta trocar **doGet** por **doPost**)

   - Opcionalmente, pode-se criar um novo método, chamado **processRequest**, e redirecionar as chamadas de **doGet** e **doPost** para ele
   
   ```java
   protected void processRequest(HttpServletRequest request, HttpServletResponse response, String metodo) throws ServletException, IOException {
                   response.setContentType("text/html;charset=UTF-8");
                   try (PrintWriter out = response.getWriter()) {
                       out.println("<!DOCTYPE html>");
                       out.println("<html>");
                       out.println("<head>");
                       out.println("<title>Servlet InterpretarRequestServlet</title>");
                       out.println("</head>");
                       out.println("<body>");
                       out.println("<h1>Servlet InterpretarRequestServlet at " + request.getContextPath() + "</h1>");
                       String requestURL = request.getRequestURL().toString();
                       String protocol = request.getProtocol();
                       int port = request.getLocalPort();
                       String queryString = request.getQueryString();
                       out.println("Requisição: " + requestURL + "<br/>");
                       out.println("Protocolo: " + protocol + "<br/>");
                       out.println("Porta: " + port + "<br/>");
                       out.println("Método: " + metodo + "<br/>");
                       out.println("Query: " + queryString + "<br/>");
                       out.println("</body>");
                       out.println("</html>");
                   }
           }
              
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                   processRequest(request, response, "GET");
   }
              
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                   processRequest(request, response, "POST");
   }
   ```
   
   
   
   
   
6. Alterar a página HTML **index.html** para adicionar um *link* para o **servlet** e testar

   ```html
   <a href="InterpretarRequestServlet?a=123&b=123">Testa</a><br/>
   
   <p></p>
   ```

7. Adicionar um formulário (HTML form) no *index.html* para enviar dados via **GET** e testar

   ```html
    <form name="teste" action="InterpretarRequestServlet">
        <fieldset>
          <legend>Formulário de Envio</legend>
          Nome: <input type="text" name="nome" /> <br/>
          E-mail: <input type="text" name="email" /> <br/>
          Confirmação de e-mail: <input type="text" name="email" /> <br/>
          Senha: <input type="password" name="senha" /><br/>
          Gênero: <input type="radio" name="genero" value="Masculino" /> Masculino
          <input type="radio" name="genero" value="Feminino" /> Feminino <br/>
          Receber notícias: <input type="checkbox" name="receber" value="ok" /><br/>
          <input type="submit" name="enviar" value="Enviar" />
        </fieldset>
    </form>
   ```

8. Modificar o método para ***POST*** e testar.  Verificar que agora a *Query String* fica null

9. Modificar o código do **servlet** para mostrar os parâmetros

   ```java
   ...
    out.println("Query: " + queryString + "<br/>");
    Map<String,String[]> mapaDeParametros = request.getParameterMap();
    Set<Entry<String,String[]>> conjuntoDeParametros = mapaDeParametros.entrySet();
    for(Entry<String,String[]> parametro: conjuntoDeParametros) {
      out.println(parametro.getKey()+":");
      for(String v: parametro.getValue()) {
        out.println("[" + v + "] ");
      }
      out.println("<br/>");
    }
    out.println("</body>");
    out.println("</html>");
   ...
   ```

   

   

   

   

   

   

   

   

10. Modificar o código para pegar parâmetros pelo nome

       ```java
       out.println("O nome é: " + request.getParameter("nome") + "<br/>");
       boolean receberNoticias = false;
       String strReceberNoticias = request.getParameter("receber");
       if(strReceberNoticias != null && strReceberNoticias.equals("ok")) {
          receberNoticias = true;
       }
       out.println("Receber noticias: " + receberNoticias + "<br/>");
       out.println("</body>");
       out.println("</html>");
       ...
       ```

11. Adicionar um *input type hidden* e testar

    ```html
    <input type="hidden" name="teste" value="testando" />
    ```

12. Fim



#### Leituras adicionais

- - -

- Servlets (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/servlets

- Servlet Tutorial

  https://www.javatpoint.com/servlet-tutorial
