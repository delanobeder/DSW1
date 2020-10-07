## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 06 - Spring MVC** 

- - -

#### 01 - Aplicação "Alô Mundo" usando Spring MVC 
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo06/AloMundoMVC-v1)
- - -

1. Criar um novo projeto Spring (https://start.spring.io/)
	
	- **Project:** Maven Project
	
	- **Language:** Java
	
	- **Spring Boot:** 2.3.3
	
	- **Group:** br.ufscar.dc.dsw
	
	- **Artifact:** AloMundoMVC
	
	- **Name:** Alo Mundo MVC
	
	- **Description:** Alo Mundo MVC
	
	- **Package name:** br.ufscar.dc.dsw
	
	- **Packaging:** Jar
	
	- **Java:** 8
	
	  **Dependências:** Spring Web, Thymeleaf e Spring Boot DevTools
	
2. Baixar o arquivo .zip e descompactar em um diretório (**AloMundoMVC**)

3. Abrir a classe **br.ufscar.dc.dsw.AloMundoMvcApplication** (Bootstrap class) para visualizar a classe principal da aplicação

4. Entrar no diretório **AloMundoMVC** e executar a aplicação através do comando `mvnw spring-boot:run`

    • Abrir http://localhost:8080 e notar que nenhuma pagina é apresentada

5. Criar o controlador **br.ufscar.dc.dsw.controller.AloMundoController**
    Arquivo: **src/main/java/br/ufscar/dc/dsw/controller/AloMundoController.java**
    
    ```java
    package br.ufscar.dc.dsw.controller;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    
    @Controller
    public class AloMundoController {
        @GetMapping("/")
        public String index() {
            return "index";
        }
    }
    ```
    
    
    
    
    
6. Criar a visão **index.html** (no diretório **/src/main/resources/templates/**)

    ```html
    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Alô Mundo</title>
    </head>
    <body>
        <h1>Alô Mundo</h1>
    </body>
    </html>
    ```

7. Executar novamente e ver o efeito (a página **index.html** é renderizada)

8. Alterar o controlador para enviar o horário/data corrente para a visão

    ```java
    package br.ufscar.dc.dsw.controller;
    
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    
    @Controller
    public class AloMundoController {
        @GetMapping("/")
        public String index(Model model) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd MMMM yyyy");
            Calendar cal = Calendar.getInstance();
        
            model.addAttribute("date", dateFormat.format(cal.getTime()));
            return "index";
        }
    }
    ```

9. Alterar a visão para apresentar  o horário/data corrente

    ```html
    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    
    <head>
        <title>Alô Mundo</title>
    </head>
    
    <body>
        <h1>Alô Mundo</h1>
    
        <h4><span th:text="${date}">16 Março 2020</span></h4>
    </body>
    
    </html>
    ```

10. Executar novamente e ver o efeito (a página **index.html** é renderizada com a horário/data corrente)

11. Fim



#### Leituras adicionais

- - -

- Spring Quickstart Guide

  https://spring.io/quickstart

  

- Tutorial: Thymeleaf + Spring
  
  https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html

  

- Spring MVC Tutorial

  https://www.javatpoint.com/spring-mvc-tutorial
  
  
  
- Serving Web Content with Spring MVC

  https://spring.io/guides/gs/serving-web-content/
