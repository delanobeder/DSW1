## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1
**Prof. Delano Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -

#### 02 - Aplicação “Alô mundo” em linha de comando
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/AloMundo)
- - -



1. Criar a pasta **AloMundo**
2. Criar a pasta **src/br/ufscar/dc/dsw**
3. Criar arquivo texto **AloMundoServlet.java** no diretório **src/br/ufscar/dc/dsw**

    ```java
    package br.ufscar.dc.dsw;

    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Date;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    public class AloMundoServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                  HttpServletResponse response)
                     throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Primeira aplicação</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Alô mundo!</h1>");
            out.println("<h1>"+ new Date() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
    ```

4. Compilar

  - No diretório raiz executar:

    ```sh
    javac -cp <caminho_para_tomcat>/lib/servlet-api.jar src/br/ufscar/dc/dsw/AloMundoServlet.java
    ```

5. Verificar que gerou o arquivo **AloMundoServlet.class**

6. Montar a estrutura da aplicação
   
   6.1. Criar a pasta **dist/WEB-INF/classes**
   
   6.2. Recompilar passando o diretório destino através da opção “-d” (ou mover o .class para dist/WEB-INF/classes/br/ufscar/dc/dsw)
   
   ```sh
   javac -cp <caminho_para_tomcat>/lib/servlet-api.jar src/dsw/AloMundoServlet.java -d dist/WEB-INF/classes
   ```
   
   6.3. Criar o arquivo **web.xml** na pasta **dist/WEB-INF**
   
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
   <web-app>
    <servlet>
        <servlet-name>AloMundo</servlet-name>
        <servlet-class>br.ufscar.dc.dsw.AloMundoServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AloMundo</servlet-name>
        <url-pattern>/TestarAloMundo</url-pattern>
    </servlet-mapping>
    <session-config>
        <session-timeout>
            30
        </session-timeout>
    </session-config>
   </web-app>
    ```
   
7. Criar o arquivo .war (executar a partir do diretório raiz da aplicação AloMundo)
    ```sh
    <caminho_para_java>/jar -cvf AloMundo.war -C dist .
    ```
    
8. Fazer o deploy
   
   8.1. Rodar o tomcat
   
   8.2. Entrar no localhost:8080  e fazer o deploy pela aplicação **manager**
   
9. Testar a aplicação, abrindo a URL http://localhost:8080/AloMundo/TestarAloMundo
   
   9.1. Fazer o undeploy da aplicação, pela aplicação **manager**
   
10. Apagar o arquivo **web.xml** (ou renomeie para web-old.xml)

11. Modificar o código fonte, adicionando as seguintes linhas
    ```java
    package br.ufscar.dc.dsw;
    
    import java.io.IOException;
    import java.io.PrintWriter;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.annotation.WebServlet;
    
    @WebServlet(urlPatterns = {"/TestarAloMundo"})
    public class AloMundoServlet extends HttpServlet {
    ...
    ```
    
12. Refazer o processo (compilar, deploy) e testar, abrindo agora a URL: http://localhost:8080/AloMundo/TestarAloMundo

13. Fazer undeploy da aplicação e parar o tomcat

14. Fim


#### Leituras adicionais
- - -

- Tomcat Application Developer's Guide (Deployment) 
  
  https://tomcat.apache.org/tomcat-11.0-doc/appdev/deployment.html
  
  
  
- Servlets (Caelum: Curso Java para Desenvolvimento Web)
  
  https://www.caelum.com.br/apostila-java-web/servlets
  
  
  
- Servlet Tutorial

  https://www.javatpoint.com/servlet-tutorial
