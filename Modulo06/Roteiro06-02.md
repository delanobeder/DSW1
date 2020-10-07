## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 06 - Spring MVC** 

- - -

#### 02 - Internacionalizando a aplicação "Alô Mundo MVC" 
**Idioma do Browser**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo06/AloMundoMVC-v2)

- - -



1. Fazer uma cópia do diretório da aplicação **Alô Mundo MVC** (Roteiro 06-01).

   ```sh
   % cp -r AloMundoMVC AloMundoMVC-v2
   ```

   

2. Abrir a aplicação cópia

3. Alterar o arquivo **src/main/resources/application.properties**

   ```properties
   spring.messages.basename = messages // arquivo basename do I18n
   ```

4. Adicionar a biblioteca Native2Ascii (https://native2ascii.net/) via Maven, adicionando as seguintes linhas ao arquivo **pom.xml**:

   ```xml
   <plugin>
   	<groupId>org.codehaus.mojo</groupId>
   	<artifactId>native2ascii-maven-plugin</artifactId>
   	<version>1.0-beta-1</version>
   	<executions>
   		<execution>
   			<id>native2ascii-utf8-resources</id>
   			<goals>
   				<goal>native2ascii</goal>
   			</goals>
   			<configuration>
   				<dest>${project.build.directory}/classes</dest>
   				<src>${project.resources[0].directory}</src>
   				<encoding>UTF-8</encoding>
   			</configuration>
   		</execution>
   	</executions>
   </plugin>
   ```

   

   

   

   

   

   

5. Atualiza o controlador **br.ufscar.dc.dsw.controller.AloMundoController**

      ```java
      package br.ufscar.dc.dsw.controller;
      
      import java.text.DateFormat;
      import java.util.Calendar;
      import java.util.Locale;
      
      import org.springframework.stereotype.Controller;
      import org.springframework.ui.Model;
      import org.springframework.web.bind.annotation.GetMapping;
      
      @Controller
      public class AloMundoController {
      
          @GetMapping("/")
          public String index(Model model, Locale locale) {
              DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL, locale);
          	
          	Calendar cal = Calendar.getInstance();
              model.addAttribute("dateString", dateFormat.format(cal.getTime()));
              model.addAttribute("date", cal.getTime());
          
              return "index";
          }
      }
      ```

6. Atualiza a visão **index.html**

      ```html
      <!DOCTYPE html>
      <html xmlns="http://www.w3.org/1999/xhtml"
      	xmlns:th="http://www.thymeleaf.org">
      <head>
      <title th:text="#{title.text}">Alô Mundo</title>
      </head>
      <body>
      	<h1 th:text="#{welcome.text}"></h1>
      	<h4>
      		<span th:text="${dateString}">16 Março 2020</span>
      	</h4>
      	<h4>
      		<span th:text="${#dates.format(date)}">16 Março 2020</span>
      	</h4>
      </body>
      </html>
      ```

      

      

      

      

      

      

7. Adiciona os arquivos de propriedades (I18n)

      **Obs: Assegure-se que esses arquivos são salvos no formato UTF-8**

      

      7.1 Arquivo **src/main/resources/messages.properties**

      ```properties
      welcome.text = Olá. Bem-vindo a todos.
      title.text = Olá Mundo
      ```

      7.2 Arquivo **src/main/resources/messages_en.properties**

      ```properties
      welcome.text = Hi. Welcome to Everyone.
      title.text = Hello World
      ```

      7.3 Arquivo **src/main/resources/messages_fr.properties**	

      ```properties
      welcome.text = Salut. Bienvenue à tous.
      title.text = Salut le Monde
      ```

      7.4 Arquivo **src/main/resources/messages_ja.properties**	

      ```properties
      welcome.text = こんにちは、皆さん、ようこそ。
      title.text = こんにちは世界
      ```



8. Executar e ver o efeito
   - A página **index.html** é renderizada com a horário/data corrente (traduzido de acordo com o idioma do browser)



#### Leituras adicionais

- - -

- Guide to Internationalization in Spring Boot
  
  https://www.baeldung.com/spring-boot-internationalization



- Your Step-by-Step Guide to Spring Boot Internationalization

  https://phrase.com/blog/posts/spring-boot-internationalization/



