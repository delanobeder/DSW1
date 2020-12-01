## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA** 

- - -

#### 03 -  Autenticação/Autorização

**Spring Security (Autenticação em memória)**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo08/LoginMVC-v1)

- - -



1. Criar um novo projeto Spring (https://start.spring.io/)

  - **Project:** Maven Project

  - **Language:** Java

  - **Spring Boot:** 2.4.0

  - **Group:** br.ufscar.dc.dsw

  - **Artifact:** LoginMVC

  - **Name:** LoginMVC

  - **Description:** LoginMVC

  - **Package name:** br.ufscar.dc.dsw

  - **Packaging:** Jar

  - **Java:** 8

    **Dependências:** Spring Web, Thymeleaf, Spring Security & Spring Boot DevTools

2. Baixar o arquivo .zip e descompactar em um diretório (**LoginMVC**)

3. Configurar o projeto --- no arquivo **pom.xml** (incluir novas maven dependências)

   ```xml
   <dependency>
   	<groupId>org.thymeleaf.extras</groupId>
   	<artifactId>thymeleaf-extras-springsecurity5</artifactId>
   </dependency>
   
   <dependency>
   	<groupId>org.webjars</groupId>
   	<artifactId>webjars-locator-core</artifactId>
   </dependency>
   
   <dependency>
   	<groupId>org.webjars</groupId>
   	<artifactId>jquery</artifactId>
   	<version>3.4.1</version>
   </dependency>
   	
   <dependency>
   	<groupId>org.webjars</groupId>
   	<artifactId>bootstrap</artifactId>
   	<version>4.4.1-1</version>
   </dependency>
   	
   <dependency>
   	<groupId>org.webjars.bower</groupId>
   	<artifactId>open-iconic</artifactId>
   	<version>1.1.1</version>
   </dependency>
   	
   <dependency>
   	<groupId>org.webjars.bower</groupId>
   	<artifactId>jquery-mask-plugin</artifactId>
   	<version>1.14.15</version>
   </dependency>
   	
   <dependency>
   	<groupId>nz.net.ultraq.thymeleaf</groupId>
   	<artifactId>thymeleaf-layout-dialect</artifactId>
   </dependency>
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

5. No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para usar o cache do thymeleaf e a internacionalização

   ```properties
   # THYMELEAF
   spring.thymeleaf.cache = false
     
   # I18n
   spring.messages.basename = messages
   ```
<div style="page-break-after: always"></div>
6. Adicionar a classe **br.ufscar.dc.dsw.config.MvcConfig.java** 

   ```java
   package br.ufscar.dc.dsw.config;
   
   import java.util.Locale;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.LocaleResolver;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
   import org.springframework.web.servlet.i18n.SessionLocaleResolver;
   
   @Configuration
   public class MvcConfig implements WebMvcConfigurer {
   
   	public void addViewControllers(ViewControllerRegistry registry) {
   		registry.addViewController("/home").setViewName("home");
   		registry.addViewController("/").setViewName("index");
   		registry.addViewController("/admin").setViewName("admin/index");
   		registry.addViewController("/user").setViewName("user/index");
   		registry.addViewController("/login").setViewName("login");
   	}
   
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


<div style="page-break-after: always"></div>
7. Adicionar a classe **br.ufscar.dc.dsw.config.WebSecurityConfig.java**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
   import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
   
   @Configuration
   @EnableWebSecurity
   public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   	@Override
   	protected void configure(HttpSecurity http) throws Exception {
   		http
   			.authorizeRequests()
   				.antMatchers("/", "/index", "/error").permitAll()
   				.antMatchers("/login/**", "/js/**").permitAll()
                   .antMatchers("/css/**", "/image/**", "/webjars/**").permitAll()
   				.antMatchers("/admin/**").hasRole("ADMIN")
                   .antMatchers("/user/**").hasRole("USER")
   				.anyRequest().authenticated()
   				.and()
   			.formLogin()
   				.loginPage("/login")
   				.permitAll()
   				.and()
   			.logout()
   				.logoutSuccessUrl("/")
   				.permitAll();
   	}
   	
   	@Autowired
       public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
           auth
                   .inMemoryAuthentication()
                   .withUser("admin").password("{noop}admin").roles("ADMIN")
                   .and().withUser("user").password("{noop}user").roles("USER");
       }
   }
   ```
<div style="page-break-after: always"></div>


8. Adicionar o controlador de erros - **br.ufscar.dc.dsw.controller.ErrorViewController.java**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.util.Map;
   import javax.servlet.http.HttpServletRequest;
   import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
   import org.springframework.http.HttpStatus;
   import org.springframework.stereotype.Component;
   import org.springframework.web.servlet.ModelAndView;
   
   @Component
   public class ErrorViewController implements ErrorViewResolver {
   
   	@Override
   	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {
   		        
   		ModelAndView model = new ModelAndView("error");
   		model.addObject("status", status.value());
   		switch (status.value()) {
   		case 403:
   			model.addObject("error", "403.error");
   			model.addObject("message", "403.message");
   			break;
   		case 404:
   			model.addObject("error", "404.error");
   			model.addObject("message", "404.message");
   			break;
   		default:
   			model.addObject("error", "default.error");
   			model.addObject("message", "default.message");
   			break;
   		}
   		return model;
   	}
   }
   ```
<div style="page-break-after: always"></div>
9. Adicionar os arquivos relacionados a visão da aplicação

   9.1 Arquivo **src/main/resources/css/main.css**

   ```css
   /*
    * Base structure
    */
   html, body {
   	height: 100%;
   }
   
   body {
   	padding-top: 56px;
   	padding-left: 0;
   } 
   
   /*
    * main layout
    */
   .layout-main {
       display: flex;
       min-height: calc(80vh - 52px);
       overflow: hidden;
   }
   
   /*
    * Content
    */
   .layout-content {
       width: 100%;
       padding-top: 0px;
   }
   
   .container {
   	width: 100%;
   	padding-top: 20px;
   }
   
   /*
    * Sidebar
    */
   .sidebar {
       width: 0px;
       height: 80vh;
       border-top: 1px solid #eee;
       border-right: 1px solid #eee;    
       background-color: #f8f9fa;
       flex-shrink: 0;
       overflow: hidden;
       transition: width .5s;
   }
   
   .sidebar .nav {
   	margin-bottom: 0px;
   }
   
   .sidebar .nav-item {
   	width: 100%;
   }
   
   .sidebar .nav-item {
   	margin-left: 0;
   }
   
   .sidebar .nav-link {
   	border-radius: 0;
   } 
   
   .sidebar ul li:hover a, .menu ul li.active a{
       color: #777777;
   }
   
   .sidebar ul{
       list-style: none;
       padding: 10px 0px;
   }
   
   .sidebar ul li a{
       display: block;
       color:#1a1a1a;
       padding: 10px 15px;
       white-space: nowrap;
   }
   
   .sidebar.sidebar-open {
       width: 300px;
   }
   
   /*
    * nav-pills home
    */
   .nav-pills .nav-link.active, .nav-pills .show>.nav-link {
   	color: #fff;
   	background-color: #1e94d2;
   }
   
   /*
    * toogle do navbar do menu
    */ 
   
   .navbar-toggle {
       display: inline-block;
       margin-left: 10px;
   }
   
   /*
    * Footer
    */
   .layout-footer {
   	position: relative;
   	left: 0;
   	right: 0;
   	bottom: 0;
   	margin: 0;
   	height: 50px;
   	border-top: 1px solid #eeeeee;
   }
   
   .footer-copy {
   	font-size: 92%;
   	display: block;
   	color: #777777;
   	text-align: center;
   }
   ```
   9.2 Baixar os arquivos de imagens e colocar no diretório **src/main/resources/image**

   * Brasil.gif, EUA.gif & logo.png

     

   9.3 Arquivo **src/main/resources/templates/fragments/footer.html**

   ```html
   <!DOCTYPE HTML>
   <html xmlns:th="http://www.thymeleaf.org"
   	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity">
   <body>
   	<th:block th:fragment="footer">
   		<footer>
   		<hr/>
   			<div class="container">
   				<div class="col-md-6 col-md-offset-3">
   					<div sec:authorize="isAuthenticated()">
   						<form th:action="@{/logout}" method="post">
   							<div class="form-group">
   								<div class="row">
   									<div class="col-sm-9 col-sm-offset-3">
   										<b><span th:text="#{login.username}"></span>:</b> <span sec:authentication="name"></span> | 
   										<b><span th:text="#{user.role}"></span>:</b> <span sec:authentication="principal.authorities"></span>
   									</div>
   									<div class="col-sm-3 col-sm-offset-3">
   										<input type="submit" name="login-submit" id="login-submit"
   											class="form-control btn btn-info" value="Logout">
   									</div>
   								</div>
   							</div>
   						</form>
   					</div>
   				</div>
   			</div>
   		</footer>
   
   		<script type="text/javascript"
   			th:src="@{/webjars/jquery/jquery.min.js/}"></script>
   		<script type="text/javascript"
   			th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>
   
   	</th:block>
   </body>
   </html>
   ```

   

   9.4 Arquivo **src/main/resources/templates/fragments/header.html**

   ```html
   <!DOCTYPE HTML>
   <html xmlns:th="http://www.thymeleaf.org">
   <body>
   	<th:block th:fragment="header">
   		<nav class="navbar navbar-inverse navbar-static-top">
   			<div class="container">
   				<div class="navbar-header">
   					<div style="right: 10px; position: absolute">
   						<a th:href="@{''(lang=pt)}"><img alt=""
   							th:src="@{/image/Brasil.gif}" style="width: 30px;" /></a> <a
   							th:href="@{''(lang=en)}"><img alt=""
   							th:src="@{/image/EUA.gif}" style="width: 30px;" /></a>
   					</div>
   					<a class="navbar-brand" th:href="@{/}"> <img
   						th:src="@{/image/logo.png}" alt="logo" style="width: 50px;" />
   					</a>
   				</div>
   				<div id="navbar" class="collapse navbar-collapse">
   					<ul class="nav navbar-nav">
   						<li class="active"><a th:href="@{/}">Home</a></li>
   					</ul>
   				</div>
   			</div>
   		</nav>
   	</th:block>
   </body>
   </html>
   ```

   9.5 Arquivo **src/main/resources/templates/fragments/layout.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
         xmlns:th="http://www.thymeleaf.org">
   <head>
       <meta charset="utf-8"/>
       <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
       <meta name="viewport" content="width=device-width, initial-scale=1"/>
   
       <link rel="stylesheet" type="text/css" th:href="@{/webjars/bootstrap/css/bootstrap.min.css}"/>
       <link rel="stylesheet" type="text/css" th:href="@{/css/main.css}"/>
   
       <title layout:title-pattern="$LAYOUT_TITLE - $CONTENT_TITLE">Spring Security Thymeleaf</title>
   </head>
   <body>
       <th:block th:replace="fragments/header :: header"/>
       <div class="container">
           <th:block layout:fragment="content"/>
       </div>
       <th:block th:replace="fragments/footer :: footer"/>
   </body>
   </html>
   ```

   

   9.6 Arquivo **src/main/resources/templates/error.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org"
   	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity"
   	layout:decorate="~{fragments/layout}">
   <head>
   <title th:text="#{error.page.title}"></title>
   </head>
   <body>
   	<div layout:fragment="content" th:remove="tag">
   
   
   
   		<h1 class="h3 mb-3 font-weight-normal">
   			<i class="oi oi-warning"></i> <span th:text="${status}"></span>
   		</h1>
   
   		<h2 th:text="#{${error}}"></h2>
   
   		<hr />
   
   		<div>
   			<span th:text="#{${message}}"></span><br />
   			<br /> <a class="btn btn-primary" type="button"
   				href="javascript:history.back()"><span th:text="#{previous.message}"></span></a>
   		</div>
   
   	</div>
   </body>
   </html>
   ```

   

   9.7 Arquivo **src/main/resources/templates/home.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org"
   	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity"
   	layout:decorate="~{fragments/layout}">
   <head>
   <title>Menu</title>
   </head>
   <body>
   	<div layout:fragment="content" th:remove="tag">
   		<h1>
   			<span th:text="#{home.hello}"></span>
   			<span th:inline="text">[[${#httpServletRequest.remoteUser}]]!</span>
   		</h1>
   		
   		<p><a th:href="@{/}"><span th:text="#{home.message.1}"></a></p>
   		
   		<div sec:authorize="hasRole('ROLE_ADMIN')">
   			<p><a th:href="@{/admin}"><span th:text="#{home.message.2}"></a></p>
   		</div>
   
   		<div sec:authorize="hasRole('ROLE_USER')">
   			<p><a th:href="@{/user}"><span th:text="#{home.message.3}"></a></p>
   		</div>
   	</div>
   </body>
   </html>
   ```
   
   

   9.8 Arquivo **src/main/resources/templates/index.html**

   ```html
<!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
         xmlns:th="http://www.thymeleaf.org"
         layout:decorate="~{fragments/layout}">
   <head>
       <title>Index</title>
   </head>
   <body>
   <div layout:fragment="content" th:remove="tag">
       <h1 th:text="#{index.message.1}"></h1>
       <p th:text="#{index.message.2}"></p>
       <ul>
           <li><a th:href="@{/home}"><span th:text="#{index.message.3}"></a></li>
       </ul>
   </div>
   </body>
   </html>
   ```
   
   

   9.9 Arquivo **src/main/resources/templates/login.html**

   ```html
<!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org"
   	layout:decorate="~{fragments/layout}">
   <head>
   <title th:text="#{login.message}"></title>
   </head>
   <body>
   	<div layout:fragment="content" th:remove="tag">
   
   		<div class="row">
   			<div class="col-md-6 col-md-offset-3">
   				<h1 th:text="#{login.message}"></h1>
   				<form th:action="@{/login}" method="post">
   					<div th:if="${param.error}">
   						<div class="alert alert-danger">
   							<span th:text="#{login.error}"></span>
   						</div>
   					</div>
   					<div class="form-group">
   						<label for="username" th:text="#{login.username}"></label>: <input
   							type="text" id="username" name="username" class="form-control"
   							autofocus="autofocus" th:placeholder="#{login.username}">
   					</div>
   					<div class="form-group">
   						<label for="password" th:text="#{login.password}"></label>: <input
   							type="password" id="password" name="password"
   							class="form-control" th:placeholder="#{login.password}">
   					</div>
   					<div class="form-group">
   						<div class="row">
   							<div class="col-sm-6 col-sm-offset-3">
   								<input type="submit" name="login-submit" id="login-submit"
   									class="form-control btn btn-info" th:value="#{login.button}">
   							</div>
   						</div>
   					</div>
   				</form>
   			</div>
   		</div>
   	</div>
   </body>
   </html>
   ```
   
   

10. Visão - área de usuários comuns (papel ROLE_USER)

    10.1 Arquivo **src/main/resources/templates/user/index.html**

    ```html
    <!DOCTYPE html>
    <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
    	xmlns:th="http://www.thymeleaf.org"
    	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity"
    	layout:decorate="~{fragments/layout}">
    <head>
    <title th:text="#{user.index.title}"></title>
    </head>
    <body>
    	<div layout:fragment="content" th:remove="tag">
    		<h1 th:text="#{user.index.title}"></h1>
    		<h1>
    			<span th:text="#{home.hello}"></span>
    			<span th:inline="text">[[${#httpServletRequest.remoteUser}]]!</span>
    		</h1>
    		<a class="btn btn-primary" type="button"
    				href="javascript:history.back()"><span th:text="#{previous.message}"></span></a>
    	</div>
    </body>
    </html>
    ```

    

11. Visão - área de usuários administradores (papel ROLE_ADMIN)

    11.1 Arquivo **src/main/resources/templates/admin/index.html**

    ```html
    <!DOCTYPE html>
    <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
    	xmlns:th="http://www.thymeleaf.org"
    	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity"
    	layout:decorate="~{fragments/layout}">
    <head>
    <title th:text="#{admin.index.title}"></title>
    </head>
    <body>
    	<div layout:fragment="content" th:remove="tag">
    		<h1 th:text="#{admin.index.title}"></h1>
    		<h1>
    			<span th:text="#{home.hello}"></span>
    			<span th:inline="text">[[${#httpServletRequest.remoteUser}]]!</span>
    		</h1>
    		<a class="btn btn-primary" type="button"
    				href="javascript:history.back()"><span th:text="#{previous.message}"></span></a>
    	</div>
    </body>
    </html>
    ```
    
    
    
12. Adiciona os arquivos de propriedades (I18n)

    **Obs: Assegure-se que esses arquivos são salvos no formato UTF-8**

    12.1 Arquivo **src/main/resources/messages.properties**
    
    ```properties
    index.message.1 = Olá Spring Security
    index.message.2 = Esta é uma página pública. Você apenas pode acessar as páginas seguras após se autenticar no sistema.
    index.message.3 = Acessar uma página segura.
    
    login.message  = Página de Login
    login.username = Nome de Usuário
    login.password = Senha
    login.button   = Entrar
    login.error    = Nome de usuário ou senha inválida.
    
    home.hello     = Olá
    home.message.1 = Acessar a página pública
    home.message.2 = Acessar a área de administradores (ROLE_ADMIN)
    home.message.3 = Acessar a área de usuários comuns (ROLE_USER)
    
    admin.index.title = Área do Administrador
    
    user.index.title = Área de usuários comuns
    
    user.role = Papel
    
    error.page.title = Página de Erros
    previous.message = Voltar a página anterior 
    403.error       = Página não autorizada.
    403.message     = Página não autorizada para o usuário logado.
    404.error       = Página não encontrada.
    404.message     = A url para a página não existe.
    default.error   = Ocorreu um erro interno no servidor.
    default.message = Ocorreu um erro inesperado, tente mais tarde.
    ```
    
      
    
    12.2 Arquivo **src/main/resources/messages_en.properties**
    
    ```properties
    index.message.1 = Hello Spring Security
    index.message.2 = This is a public page. You can only access secure pages after authenticating with the system.
    index.message.3 = Access a secure page.
    
    login.message  = Login Page
    login.username = User Name
    login.password = Password
    login.button   = LogIn
    login.error    = Invalid username or password.
    
    home.hello     = Hello
    home.message.1 = Access the public page
    home.message.2 = Access the administrators area (ROLE_ADMIN)
    home.message.3 = Access the ordinary users area (ROLE_USER)
    
    admin.index.title = Administrator area
    admin.index.hello = Hello
    
    user.role = Role
    
    error.page.title = Errors page
    previous.message = Back to previous page 
    403.error       = Unauthorized page.
    403.message     = Page not authorized for this user.
    404.error       = Page not found.
    404.message     = The Page url does not exist.
    default.error   =  An internal server error has occurred.
    default.message = An unexpected error occurred.
    ```
    
    <div style="page-break-after: always"></div>
    
13. Executar (**mvn spring-boot:run**) e testar
    * Testar, abrindo o browser no endereço: http://localhost:8080
    * Acessar a página principal (não precisa autenticação)
    * Acessar a página segura (precisa autenticação)
      * Logar admin/user e verificar que uma mensagem de erro é apresentada (senha inválida)
      * Logar admin/admin (Login com sucesso)
        * Tentar acessar a área de administrador (papel ROLE_ADMIN): Sucesso
        * Tentar acessar a área de usuário comum (papel ROLE_USER): mensagem de erro é apresentada (acesso não autorizado)
        * Logout
      * Logar user/admin e verificar que uma mensagem de erro é apresentada (senha inválida)
        * Logar user/user (Login com sucesso)
        * Tentar acessar a área de administrador (papel ROLE_ADMIN): mensagem de erro é apresentada (acesso não autorizado)
        * Tentar acessar a área de usuário comum (papel ROLE_USER): Sucesso 
        * Logout
14. Fim



#### Leituras adicionais

- - -

- Spring Security

  https://spring.io/projects/spring-security

  

- Securing a Web Application

  https://spring.io/guides/gs/securing-web/
  
  
  
- Thymeleaf + Spring Security integration basics

  https://www.thymeleaf.org/doc/articles/springsecurity.html
  
  
  
  
  


