## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**

- - -

#### 03 - Aplicação “Alô mundo” com Apache Maven
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo02/AloMundoMVN)
- - -



1. Criar um projeto Maven Java Web -- abrir um terminal e executar o seguinte comando (não esquecer de configurar o Maven)

     ```sh
   mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.4
   ```

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: AloMundoMVN 
  - Não modificar as outras opções. Será criado um diretório chamado **AloMundoMVN**. 

  

2. Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

   - Abrir um terminal dentro da pasta do projeto (**AloMundoMVN**) e executar o seguinte comando: 

   ```sh
   mvn -N io.takari:maven:wrapper
   ```

3. Abra o diretório AloMundoMVN em um editor de texto (ex: Visual Studio Code). Adicionar a seguinte dependência e plugin ao arquivo **pom.xml** 

   ```xml
   <project ... >
     <dependencies>
     	...
         <dependency>
   			<groupId>jakarta.servlet</groupId>
   			<artifactId>jakarta.servlet-api</artifactId>
   			<version>6.0.0</version>
   			<scope>provided</scope>
   		</dependency>
   		<dependency>
   			<groupId>jakarta.servlet.jsp</groupId>
   			<artifactId>jakarta.servlet.jsp-api</artifactId>
   			<version>3.0.0</version>
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
           	<server>Tomcat11</server>
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

4. Agora é preciso inserir as credenciais do Tomcat para permitir que o plugin do Maven acesse o servidor

   - Editar o arquivo tomcat-users.xml, dentro da instalação do Tomcat, e adicionar o suporte para o papel de script para o usuário criado anteriormente: 

   ```xml
   <user username="admin" password="admin" roles="manager-gui,manager-script" />
   ```

5. Agora vamos configurar o Maven para gravar usuário e senha do Tomcat. Essa configuração deve ficar FORA do projeto, pois é algo específico de uma determinada máquina, e portanto caso esteja sendo utilizado repositório para compartilhamento de código, NÃO deve ser compartilhado!

   Abrir (ou criar) o arquivo .m2/settings.xml que fica localizado dentro da pasta de usuário. Ex: /home/delano/.m2/settings.xml
   O conteúdo importante é o que está destacado abaixo. Insira no local correto (dentro de servers). Caso não exista, crie o conteúdo todo e as tags pais, caso necessário:

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <settings>
    	<servers>
        	<server>
            	<id>Tomcat11</id>
            	<username>admin</username>
            	<password>admin</password>
        	</server>
    	</servers>
   </settings>
   ```

6. Criar uma nova pasta dentro de src/main: **java/br/ufscar/dc/dsw**

   

   

   

7. Criar um arquivo dentro da pasta acima, chamado **AloMundoServlet.java** 

     ```java
   package br.ufscar.dc.dsw;
     
   import java.io.IOException;
   import java.io.PrintWriter;
   import java.util.Date;
     
   import jakarta.servlet.ServletException;
   import jakarta.servlet.http.HttpServlet;
   import jakarta.servlet.annotation.WebServlet;
   import jakarta.servlet.http.HttpServletRequest;
   import jakarta.servlet.http.HttpServletResponse;
     
   @WebServlet(urlPatterns = { "/AloMundoServletNoMaven" })
   public class AloMundoServlet extends HttpServlet {
         
     private static final long serialVersionUID = 1L;
         
     protected void doGet(HttpServletRequest request,
      	      HttpServletResponse response) throws ServletException, IOException {
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

8. Abrir um terminal e executar o Tomcat.

     ```sh
   catalina.sh run (ou start)
   ```

9. Abrir um (outro) terminal dentro da pasta do projeto e executar os seguintes comandos (note que devido ao uso do Maven Wrapper, não é mais necessário acessar a instalação global do Maven):

     ```sh
   ./mvnw tomcat7:deploy
   ./mvnw tomcat7:redeploy <- automaticamente faz o deploy também, caso não exista
   ./mvnw tomcat7:undeploy
   ```

  

  



10. Testar, abrindo o browser no endereço: 
    http://localhost:8080/AloMundoMVN/AloMundoServletNoMaven

    Caso queira fazer o deploy manualmente, a pasta “target” dentro do projeto irá conter o arquivo .war gerado, após a execução de um dos comandos acima

11. Caso queira apenas gerar o .war (sem fazer deploy), rodar o comando:
    ```sh
    ./mvnw package
    ```

12. Fim



#### Leituras adicionais

- - -

- Servlets (Caelum: Curso Java para Desenvolvimento Web)
  
  https://www.caelum.com.br/apostila-java-web/servlets
  
- Servlet Tutorial
  
  https://www.javatpoint.com/servlet-tutorial
  
- Introduction to Java Servlets

  https://www.baeldung.com/intro-to-servlets
  
- Tomcat Application Developer's Guide (Deployment) 
  
  https://tomcat.apache.org/tomcat-11.0-doc/appdev/deployment.html
  
- Maven in 5 Minutes

  https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html 
  
- Apache Tomcat Maven Plugin

  http://tomcat.apache.org/maven-plugin-2.2/

  
