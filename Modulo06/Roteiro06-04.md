## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 06 - Spring MVC** 

- - -

#### 04 - Implantando a aplicação "Alô Mundo MVC" no Tomcat
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo06/AloMundoMVC-v4)

- - -



1. Fazer uma cópia do diretório da aplicação **Alô Mundo MVC (v3)** (Roteiro 06-03).

   ```sh
   % cp -r AloMundoMVC-v3 AloMundoMVC-v4
   ```

2. Abrir a aplicação cópia (**AloMundoMVC-v4**)

3. Atualize o arquivo **pom.xml**

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <parent>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-parent</artifactId>
   	<version>3.4.3</version>
   	<relativePath /> <!-- lookup parent from repository -->
   </parent>
   <groupId>br.ufscar.dc.dsw</groupId>
   <artifactId>AloMundoMVC</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <name>AloMundoMVC (v3)</name>
   <packaging>war</packaging>
    ...
   
   <dependencies>
       ... 
   	<dependency>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-starter-tomcat</artifactId>
   		<scope>provided</scope>
   	</dependency>
   </dependencies>
   
   <build>
   	<finalName>${project.artifactId}</finalName>
   	<plugins>
   		<plugin>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-maven-plugin</artifactId>
   		</plugin>
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
   		...
   	</plugins>
   </build>
   </project>
   ```

4. Atualize a classe de configuração **br.ufscar.dc.dsw.AloMundoMvcApplication**

   ```java
   package br.ufscar.dc.dsw;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.boot.builder.SpringApplicationBuilder;
   import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
   
   @SpringBootApplication
   public class AloMundoMvcApplication extends SpringBootServletInitializer {
   
   	public static void main(String[] args) {
   		SpringApplication.run(AloMundoMvcApplication.class, args);
   	}
   
   	@Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
           return builder.sources(AloMundoMvcApplication.class);
       }
   }
   ```

5. Executar e verificar se a implantação funcionou perfeitamente

   ```bash
   $ mvn package tomcat7:redeploy
   ```

   


#### Leituras adicionais

- - -

- Deploy a Spring Boot WAR into a Tomcat Server

  https://www.baeldung.com/spring-boot-war-tomcat-deploy