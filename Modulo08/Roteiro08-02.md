## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA** 

- - -

#### 02 - Internacionalizando a aplicação "LivrariaMVC" (Demonstração 01)
**Através de um menu de opções**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo08/LivrariaMVC-v2)

- - -



1. Fazer uma cópia do diretório da aplicação **LivrariaMVC** (Roteiro 08-01).

   ```sh
   % cp -r LivrariaMVC LivrariaMVC-v2
   ```

   

2. Abrir a aplicação cópia

3. Baixar as imagens do arquivo e descompactar no diretório (**src/main/resources/static/image**)

4. Alterar o arquivo **src/main/resources/application.properties**

   ```properties
   spring.messages.basename = messages // arquivo basename do I18n
   ```

5. Adicionar a biblioteca Native2Ascii (https://native2ascii.net/) via Maven, adicionando as seguintes linhas ao arquivo **pom.xml**:

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
<div style="page-break-after: always"></div>

6. Atualizar a classe de configuração **br.ufscar.dc.dsw.config.MvcConfig.java**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import java.util.Locale;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.ComponentScan;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.format.FormatterRegistry;
   import org.springframework.web.servlet.LocaleResolver;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
   import org.springframework.web.servlet.i18n.SessionLocaleResolver;
   import br.ufscar.dc.dsw.conversor.BigDecimalConversor;
   
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
       
       @Override
       public void addFormatters(FormatterRegistry registry) {
           registry.addConverter(new BigDecimalConversor());
       }
   }
   ```
<div style="page-break-after: always"></div>
7. Alterar o controlador de erros - **br.ufscar.dc.dsw.controller.ErrorViewController.java**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.util.Map;
   
   import jakarta.servlet.http.HttpServletRequest;
   
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
   
   
   
8. Alterar as visões da entidade **Editora**

   8.1. Arquivo **src/main/resources/templates/editora/cadastro.html**

   ```html
   <!DOCTYPE html>
   <html layout:decorate="~{layout}">
   <head>
   <meta charset="UTF-8" />
   </head>
   <body>
   	<section class="layout-content" layout:fragment="corpo">
   
   		<nav class="navbar navbar-expand-md bg-light">
   			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
   				<ul class="navbar-nav mr-auto">
   					<li class="nav-item active"><i class="oi oi-caret-right"></i>
   						<span th:text="#{editora.cadastrar.label}"></span></li>
   				</ul>
   				<a class="btn btn-primary btn-md" th:href="@{/editoras/listar}"
   					role="button"> <span class="oi oi-spreadsheet" title="Listar"
   					aria-hidden="true"></span> <span th:text="#{editora.listar.label}"></span>
   				</a>
   			</div>
   		</nav>
   
   		<div class="container" id="cadastro">
   
   			<div th:replace="fragments/alert"></div>
   
   			<form
   				th:action="${editora.id == null} ? @{/editoras/salvar} : @{/editoras/editar}"
   				th:object="${editora}" method="POST">
   
   				<div class="form-row">
   					<div class="form-group col-md-6">
   						<label th:text="#{editora.nome.label}" for="nome"></label> 
   						<input type="text" class="form-control" id="nome" autofocus="autofocus"
   							th:field="*{nome}" th:placeholder="#{editora.nome.label}"
   							th:classappend="${#fields.hasErrors('nome')} ? is-invalid" />
   
   						<div class="invalid-feedback">
   							<span th:errors="*{nome}"></span>
   						</div>
   					</div>
   
   					<div class="form-group col-md-6">
   						<label th:text="#{editora.cnpj.label}" for="CNPJ"></label> 
   						<input type="text" class="form-control" id="CNPJ" autofocus="autofocus"
   							th:field="*{CNPJ}" placeholder="__.___.___/____-__"
   							data-mask="00.000.000/0000-00" data-mask-reverse="true"
   							th:classappend="${#fields.hasErrors('CNPJ')} ? is-invalid" />
   							
   						<div class="invalid-feedback">
   							<span th:errors="*{CNPJ}"></span>
   						</div>
   					</div>
   				</div>
   
   				<input type="hidden" id="id" th:field="*{id}" />
   				<button type="submit" th:text="#{button.salvar.label}"
   					class="btn btn-primary btn-sm"></button>
   			</form>
   		</div>
   	</section>
   </body>
   </html>
   ```
   8.2. Arquivo **src/main/resources/templates/editora/lista.html**

     ```html
   <!DOCTYPE html>
   <html layout:decorate="~{layout}">
   <head>
   <meta charset="UTF-8" />
   </head>
   <body>
   	<section class="layout-content" layout:fragment="corpo">
     
     		<nav class="navbar navbar-expand-md bg-light">
     			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
     				<ul class="navbar-nav mr-auto">
     					<li class="nav-item active">
     						<i class="oi oi-caret-right"></i>
     						<span th:text="#{editora.listar.label}"></span>
     					</li>
     				</ul>
     				<a class="btn btn-primary btn-md" th:href="@{/editoras/cadastrar}" role="button"> 
     					<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
     					<span th:text="#{editora.cadastrar.label}"></span>
     				</a>
     			</div>
     		</nav>
     
     		<div class="container" id="listagem">
     
     			<div th:replace="fragments/alert"></div>
     
     			<div class="table-responsive">
     				<table class="table table-striped table-hover table-sm">
     					<thead>
     						<tr>
     							<th>#</th>
     							<th th:text="#{editora.cnpj.label}"></th>
     							<th th:text="#{editora.nome.label}"></th>
     							<th th:text="#{acao.label}"></th>
     						</tr>
     					</thead>
     					<tbody>
     						<tr th:each="editora : ${editoras}">
     							<td th:text="${editora.id}"></td>
     							<td th:text="${editora.CNPJ}"></td>
     							<td th:text="${editora.nome}"></td>
     							<td colspan="2">
     								<a class="btn btn-info btn-sm" th:href="@{/editoras/editar/{id} (id=${editora.id}) }"
     									role="button"> 
     									<span class="oi oi-brush" th:title="#{link.editar.label}" aria-hidden="true"> </span>
     								</a>
     								<button
     									th:id="${'btn_editoras/excluir/' + editora.id}"
     									type="button" class="btn btn-danger btn-sm"
     									data-toggle="modal" data-target="#myModal">
     									<span class="oi oi-circle-x" th:title="#{link.excluir.label}" aria-hidden="true"></span>
     								</button>
     							</td>
     						</tr>
     					</tbody>
     				</table>
     			</div>
     		</div>
     
     		<div th:replace="fragments/modal :: modal"></div>
     	</section>
   </body>
   </html>
     ```

9. Alterar as visões da entidade **Livro**

   9.1. Arquivo **src/main/resources/templates/livro/cadastro.html**

   ```html
   <!DOCTYPE html>
   <html layout:decorate="~{layout}">
   <head>
   <meta charset="UTF-8" />
   </head>
   <body>
   
   	<section class="layout-content" layout:fragment="corpo">
   
   		<nav class="navbar navbar-expand-md bg-light">
   			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
   				<ul class="navbar-nav mr-auto">
   					<li class="nav-item active"><i class="oi oi-caret-right"></i>
   						<span th:text="#{livro.cadastrar.label}"></span></li>
   				</ul>
   			</div>
   			<a class="btn btn-primary btn-md" th:href="@{/livros/listar}"
   				role="button"> <span class="oi oi-spreadsheet" title="Cadastro"
   				aria-hidden="true"></span> <span th:text="#{livro.listar.label}"></span>
   			</a>
   		</nav>
   
   		<div class="container" id="cadastro">
   
   			<div th:replace="fragments/alert"></div>
   
   			<form
   				th:action="${livro.id == null} ? @{/livros/salvar} : @{/livros/editar}"
   				th:object="${livro}" method="POST">
   
   				<div class="form-row">
   					<div class="form-group col-md-6">
   						<label th:text="#{livro.titulo.label}" for="nome"></label> 
   						<input type="text" class="form-control" id="nome" placeholder="Título"
   							autofocus="autofocus" th:field="*{titulo}"
   							th:classappend="${#fields.hasErrors('titulo')} ? is-invalid" />
   						
   						<div class="invalid-feedback">
   							<span th:errors="*{titulo}"></span>
   						</div>
   					</div>
   
   					<div class="form-group col-md-6">
   						<label th:text="#{livro.autor.label}" for="nome"></label> 
   						<input type="text" class="form-control" id="nome" placeholder="Autor"
   							autofocus="autofocus" th:field="*{autor}"
   							th:classappend="${#fields.hasErrors('autor')} ? is-invalid" />
   						
   						<div class="invalid-feedback">
   							<span th:errors="*{autor}"></span>
   						</div>
   					</div>
   
   					<div class="form-group col-md-6">
   						<label th:text="#{livro.editora.label}" for="editora"></label> 
   						<select id="editora" class="form-control" th:field="*{editora}"
   							th:classappend="${#fields.hasErrors('editora')} ? is-invalid">
   							<option value="">Selecione</option>
   							<option th:each="editora : ${editoras}" th:value="${editora.id}"
   								th:text="${editora.nome}"></option>
   						</select>
   
   						<div class="invalid-feedback">
   							<span th:errors="*{editora}"></span>
   						</div>
   					</div>
   
   					<div class="form-group col-md-6">
   						<label th:text="#{livro.ano.label}" for="ano"></label> 
   						<input type="number" class="form-control" id="nome" placeholder="Ano"
   							autofocus="autofocus" th:field="*{ano}"
   							th:classappend="${#fields.hasErrors('ano')} ? is-invalid" />
   						
   						<div class="invalid-feedback">
   							<span th:errors="*{ano}"></span>
   						</div>
   					</div>
   
   					<div class="form-group col-md-6">
   						<label th:text="#{livro.preco.label}" for="preco"></label> 
   						<input type="text" class="form-control" id="preco" placeholder="Preço"
   							data-mask="000000,00" data-mask-reverse="true" th:field="*{preco}" 
   							th:classappend="${#fields.hasErrors('preco')} ? is-invalid" />
   						
   						<div class="invalid-feedback">
   							<span th:errors="*{preco}"></span>
   						</div>
   					</div>
   				</div>
   
   				<input type="hidden" id="id" th:field="*{id}" />
   				<button type="submit" th:text="#{button.salvar.label}"
   					class="btn btn-primary btn-sm"></button>
   			</form>
   		</div>
   	</section>
   </body>
   </html>
   ```
   9.2. Arquivo **src/main/resources/templates/livro/lista.html**

     ```html
   <!DOCTYPE html>
   <html layout:decorate="~{layout}">
   <head>
   </head>
   <body>
   	<section class="layout-content" layout:fragment="corpo">
     
     		<nav class="navbar navbar-expand-md bg-light">
     			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
     				<ul class="navbar-nav mr-auto">
     					<li class="nav-item active">
     						<i class="oi oi-caret-right"></i>
     						<span th:text="#{livro.listar.label}"></span>
     					</li>
     				</ul>
     			</div>
     			<a class="btn btn-primary btn-md" th:href="@{/livros/cadastrar}" role="button"> 
     				<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
     				<span th:text="#{livro.cadastrar.label}"></span>
     			</a>
     		</nav>
     
     		<div class="container" id="listagem">
     
     			<div th:replace="fragments/alert"></div>
     
     			<div class="table-responsive">
     				<table class="table table-striped table-hover table-sm">
     					<thead>
     						<tr>
     							<th>#</th>
     							<th th:text="#{livro.titulo.label}"></th>
     							<th th:text="#{livro.autor.label}"></th>
     							<th th:text="#{livro.editora.label}"></th>
     							<th th:text="#{livro.ano.label}"></th>
     							<th th:text="#{livro.preco.label}"></th>
     							<th th:text="#{acao.label}"></th>
     						</tr>
     					</thead>
     					<tbody>						
     						<tr th:each="livro : ${livros}">
     							<td th:text="${livro.id}"></td>
     							<td th:text="${livro.titulo}"></td>
     							<td th:text="${livro.autor}"></td>
     							<td th:text="${livro.editora.nome}"></td>
     							<td th:text="${livro.ano}"></td>
     							<td th:text="|R$ ${#numbers.formatDecimal(livro.preco,2,2,'COMMA')}|"></td>
     							<td colspan="2">
     								<a class="btn btn-info btn-sm"
     									th:href="@{/livros/editar/{id} (id=${livro.id}) }" role="button"> 
     									<span class="oi oi-brush" th:title="#{link.editar.label}" aria-hidden="true"></span>
     								</a>
     								<button th:id="${#strings.concat('btn_livros/excluir/',livro.id)}" 
     									type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal">
     									<span class="oi oi-circle-x" th:title="#{link.excluir.label}" aria-hidden="true"></span>
     								</button>
     							</td>
     						</tr>
     					</tbody>
     				</table>
     			</div>
     		</div>
     
     		<div th:replace="fragments/modal :: modal"></div>
     	</section>
   </body>
   </html>
     ```

   

10. Atualizar arquivos relacionados ao leiaute da aplicação (visão)

   10.1.  Arquivo **src/main/resources/templates/fragments/header.html**

   ```html
   <!DOCTYPE html>
   <html>
   <head>
   <meta charset="UTF-8">
   </head>
   <body>
   	<header th:fragment="cabecalho">
    		<nav
    			class="navbar navbar-inverse navbar navbar-dark bg-dark fixed-top">
     			<div class="container-fluid">
     				<div class="navbar-header">
     					<div style="right: 10px; position: absolute">
     						<a th:href="@{''(lang=pt)}"><img alt=""
     							th:src="@{/image/Brasil.gif}" style="width: 30px;" /></a> 
     						<a th:href="@{''(lang=en)}"><img alt=""
     							th:src="@{/image/EUA.gif}" style="width: 30px;" /></a>
     					</div>
     					<button type="button" class="btn btn-dark navbar-toggle pull-left">
     						<i class="oi oi-menu"></i>
     					</button>
     					<a class="navbar-brand " th:href="@{/}" th:text="#{title.label}">
     					</a>
     				</div>
     			</div>
     		</nav>
     	</header>
   </body>
   </html>
   ```

10.2. Arquivo **src/main/resources/templates/fragments/modal.html**

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<!-- CONFIRM MODAL -->
	<div class="modal fade" tabindex="-1" role="dialog" id="myModal" th:fragment="modal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						arial-label="Close"></button>
					<h4 class="modal-title" th:text="#{remove.label.warning}"></h4>
				</div>
				<div class="modal-body">
					<p th:text="#{remove.label.message}"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" th:text="#{button.cancelar.label}"></button>
					<button id="ok_confirm" type="button" class="btn btn-primary">OK</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>
```



10.3. Arquivo **src/main/resources/templates/fragments/sidebar.html**

```html
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"></head>
<body>
	<nav class="sidebar sidebar-open" th:fragment="nav-sidebar">
		<ul class="nav nav-pills">
			<li class="nav-item"><a class="nav-link " th:href="@{/}"> <i
					class="oi oi-home"></i> <span th:text="#{sidebar.link.home}"></span>
			</a></li>
		</ul>

		<ul class="nav nav-pills">
			<li class="nav-item"><span class="nav-link active" th:text="#{sidebar.titulo.editora}" ></span></li>
			<li class="nav-item"><a class="nav-link"
				th:href="@{/editoras/cadastrar}"> <i class="oi oi-plus"></i> <span th:text="#{sidebar.link.cadastrar}"></span>
			</a></li>
			<li class="nav-item"><a class="nav-link"
				th:href="@{/editoras/listar}"> <i class="oi oi-spreadsheet"></i>
					<span th:text="#{sidebar.link.listar}"></span>
			</a></li>
		</ul>

		<ul class="nav nav-pills">
			<li class="nav-item"><span class="nav-link active" th:text="#{sidebar.titulo.livro}"></span></li>
			<li class="nav-item"><a class="nav-link"
				th:href="@{/livros/cadastrar}"> <i class="oi oi-plus"></i> <span th:text="#{sidebar.link.cadastrar}"></span>
			</a></li>
			<li class="nav-item"><a class="nav-link" th:href="@{/livros/listar}">
					<i class="oi oi-spreadsheet"></i> <span th:text="#{sidebar.link.listar}"></span>
			</a></li>
		</ul>
	</nav>
</body>
</html>
```



10.4. Arquivo **src/main/resources/templates/home.html**

```html
<!DOCTYPE html>
<html layout:decorate="~{layout}">
<head>
</head>
<body>
	<section class="layout-content" layout:fragment="corpo">

		<div class="container" style="text-align: center;">
			<h1 th:text="#{title.label}">Livraria Virtual</h1>
			<img alt="" th:src="@{/image/image.png}" style="width:500px;">
		</div>

	</section>
</body>
</html>
```



10.5. Arquivo **src/main/resources/templates/layout.html**

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
<title th:text="#{title.label}"></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" th:href="@{/image/favicon.png}" />
<!-- Bootstrap core CSS -->
<link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet" />
<link th:href="@{/webjars/open-iconic/font/css/open-iconic-bootstrap.min.css}"
	rel="stylesheet" />

<!-- Custom styles for this template -->
<link th:href="@{/css/style.css}" rel="stylesheet" />
</head>
<body>
	<header th:replace="fragments/header :: cabecalho">
		<div>header</div>
	</header>
	<div class="layout-main">
		<aside>
			<nav th:replace="fragments/sidebar :: nav-sidebar">
				<span>menu</span>
			</nav>
		</aside>
		<section layout:fragment="corpo">
			<div>conteúdo principal das páginas</div>
		</section>
	</div>
	<footer th:replace="fragments/footer :: rodape">
		<div>footer</div>
	</footer>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script th:src="@{/webjars/jquery/jquery.min.js}"></script>
	<script th:src="@{/webjars/jquery-mask-plugin/dist/jquery.mask.min.js}"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>
	<script type="text/javascript">
		var url = '';
		
		$('button[id*="btn_"]').click(function() {
			url = "http://localhost:8080/" + $(this).attr('id').split("_")[1]
		});
		
		$('#ok_confirm').click(function() {
			document.location.href = url;
		});
		
		$(function() {
			$('[data-toggle="popover"]').popover();
		});

		$(document).ready(function() {
			$(".navbar-toggle").click(function() {
				$(".sidebar").toggleClass("sidebar-open");
			})
		});
	</script>
</body>
</html>
```



10.6 Arquivo **src/main/resources/templates/error.html**

```html
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" th:href="@{/image/favicon.png}" />

<title th:text="#{error.page.title}"></title>

<!-- Bootstrap core CSS -->
<link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet" />
<link th:href="@{/webjars/open-iconic/font/css/open-iconic-bootstrap.min.css}" rel="stylesheet" />
<!-- Custom styles for this template -->
<link th:href="@{/css/style.css}" rel="stylesheet" />
</head>		
<body class="text-center">
	<div>
		<h1 class="h3 mb-3 font-weight-normal">
			<i class="oi oi-warning"></i> 
			<span th:text="${status}"></span>
		</h1>
		<h2 th:text="#{${error}}"></h2>
		<hr/>
		<div>
			<span th:text="#{${message}}"></span><br />
			<br /> <a class="btn btn-primary" type="button"
				href="javascript:history.back()"><span th:text="#{previous.message}"></span></a>
		</div>
	</div>
</body>
</html>	
```



11. Adiciona os arquivos de propriedades (I18n)

    **Obs: Assegure-se que esses arquivos são salvos no formato UTF-8**

    11.1 **src/main/resources/ValidationMessages_en.properties**

      ```properties
    # Mensagens genéricas
      
    jakarta.validation.constraints.Size.message = Must be between {min} and {max} characters.
    jakarta.validation.constraints.NotBlank.message = It is mandatory.
    jakarta.validation.constraints.NotNull.message = It is mandatory.
    jakarta.validation.constraints.Digits.message = Must contain a maximum of {integer} digits.
      
    # Validação campos Livro
      
    NotBlank.livro.titulo = The title of the book is mandatory.
    NotBlank.livro.autor = The author of the book is mandatory.
    NotNull.livro.ano = The year of the book is mandatory.
    NotNull.livro.preco = The price of the book is mandatory.
    NotNull.livro.editora = Select a publisher.
      
    # Validação campos Editora
      
    Size.editora.CNPJ = The CNPJ of the publisher must have {max} characters.
    Unique.editora.CNPJ = The CNPJ should be unique.
      ```
      11.2 **src/main/resources/messages.properties**
    
      ```properties
    # Titulos do Header
    
    title.label = Livraria Virtual
    
    # Titulos do Sidebar
    
    sidebar.link.home = Home
    sidebar.link.cadastrar = Cadastrar
    sidebar.link.listar = Listar
    sidebar.titulo.editora = Editoras
    sidebar.titulo.livro = Livros
    
    # Titulos Editora
    
    editora.cadastrar.label = Cadastrar Editora
    editora.listar.label = Listar Editoras
    
    editora.nome.label = Nome
    editora.cnpj.label = CNPJ
    
    # Titulos Livro
    
    livro.cadastrar.label = Cadastrar Livro
    livro.listar.label = Listar Livros
    
    livro.titulo.label = Título
    livro.autor.label = Autor
    livro.editora.label = Editora
    livro.ano.label = Ano
    livro.preco.label = Preço
    
    # Mensagens Remoção
    
    remove.label.warning = Atenção
    remove.label.message = Clique em OK para proceder com a exclusão...
    
    # Mensagens Botões & Caixas de Seleção
    
    link.excluir.label = Excluir
    link.editar.label = Editar
    button.cancelar.label = Cancelar
    button.salvar.label = Salvar
    select.label = Selecione...
    acao.label = Ação
    
    # Mensagens de erro
    
    error.page.title = Página de Erros
    
    previous.message = Voltar a página anterior 
    
    403.error       = Página não autorizada.
    403.message     = Página não autorizada para o usuário logado.
    404.error       = Página não encontrada.
    404.message     = A url para a página não existe.
    default.error   = Ocorreu um erro interno no servidor.
    default.message = Ocorreu um erro inesperado, tente mais tarde.
      ```
    11.3. **src/main/resources/messages_en.properties**
    
      ```properties
    # Titulos do Header
    
    title.label = Virtual Book Store
    
    # Titulos do Sidebar
    
    sidebar.link.home = Home
    sidebar.link.cadastrar = Register
    sidebar.link.listar = List
    sidebar.titulo.editora = Publishers
    sidebar.titulo.livro = Books
    
    # Titulos Editora
    
    editora.cadastrar.label = Register Publisher
    editora.listar.label = Publishers List
    
    editora.nome.label = Name
    editora.cnpj.label = CNPJ
    
    # Titulos Livro
    
    livro.cadastrar.label = Register Book
    livro.listar.label = Books List
    
    livro.titulo.label = Title
    livro.autor.label = Author
    livro.editora.label = Publisher
    livro.ano.label = Year
    livro.preco.label = Price
    
    # Mensagens Remoção
    
    remove.label.warning = Attention
    remove.label.message = Click OK to proceed with the deletion...
    
    # Mensagens Botões & Caixas de Seleção
    
    link.excluir.label = Delete
    link.editar.label = Update
    button.cancelar.label = Cancel
    button.salvar.label = Save
    select.label = Select...
    acao.label = Action
    
    # Mensagens de erro
    
    error.page.title = Errors page
    
    previous.message = Back to previous page 
    
    403.error       = Unauthorized page.
    403.message     = Page not authorized for this user.
    404.error       = Page not found.
    404.message     = The Page url does not exist.
    default.error   =  An internal server error has occurred.
    default.message = An unexpected error occurred.
      ```

  

12. Executar (**mvn spring-boot:run**) e ver o efeito

- As páginas são renderizadas (traduzido de acordo com a escolha - idioma selecionado)



#### Leituras adicionais

- - -

- Guide to Internationalization in Spring Boot
  
  https://www.baeldung.com/spring-boot-internationalization
  
  
  
- Your Step-by-Step Guide to Spring Boot Internationalization
  
  https://phrase.com/blog/posts/spring-boot-internationalization/

