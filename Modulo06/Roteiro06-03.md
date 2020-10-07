## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 06 - Spring MVC** 

- - -

#### 03 - Internacionalizando a aplicação "Alô Mundo MVC"
**Através de um menu de opções**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo06/AloMundoMVC-v3)

- - -



1. Fazer uma cópia do diretório da aplicação **Alô Mundo MVC (v2)** (Roteiro 06-02).

   ```sh
   % cp -r AloMundoMVC-v2 AloMundoMVC-v3
   ```

2. Abrir a aplicação cópia (**AloMundoMVC-v3**)

3. Baixar as imagens do arquivo e descompactar no diretório (**src/main/resources/static/image**)

4. Atualiza a visão **index.html**

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<title th:text="#{title.text}">Alô Mundo</title>
</head>
<body>
	<div style="right: 40px; position: absolute">
		<a th:href="@{''(lang=pt)}"><img alt="" th:src="@{/image/Brasil.gif}" style="width: 50px;" /></a> 
		<a th:href="@{''(lang=en)}"><img alt="" th:src="@{/image/EUA.gif}" style="width: 50px;" /></a>
		<a th:href="@{''(lang=fr)}"><img alt="" th:src="@{/image/Franca.gif}" style="width: 50px;" /></a> 
		<a th:href="@{''(lang=ja)}"><img alt="" th:src="@{/image/Japao.gif}" style="width: 50px;" /></a>
	</div>
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
<div style="page-break-after: always"></div>

5. Adiciona a classe de configuração **br.ufscar.dc.dsw.config.MvcConfig**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import java.util.Locale;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.ComponentScan;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.LocaleResolver;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
   import org.springframework.web.servlet.i18n.SessionLocaleResolver;
   
   
   @Configuration
   @ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
   public class MvcConfig implements WebMvcConfigurer {
   
       @Bean
       public LocaleResolver localeResolver() {
           SessionLocaleResolver slr = new SessionLocaleResolver();
           slr.setDefaultLocale(new Locale("pt","BR"));
           return slr;
       }
   
       @Bean
       public LocaleChangeInterceptor localeChangeInterceptor() {
           LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
           lci.setParamName("lang");
           return lci;
       }
   
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
           registry.addInterceptor(localeChangeInterceptor());
       }
   }
   ```

6. Executar e ver o efeito

   - A página **index.html** é renderizada com a horário/data corrente (traduzido de acordo com a escolha - idioma selecionado)

<div style="page-break-after: always"></div>

#### Leituras adicionais

- - -

- Guide to Internationalization in Spring Boot

  https://www.baeldung.com/spring-boot-internationalization

  
  
- Your Step-by-Step Guide to Spring Boot Internationalization

  https://phrase.com/blog/posts/spring-boot-internationalization/


