## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 09 - REST API**

- - -

#### 02 - Exemplo de cliente de um servidor REST

**Cidades REST Server & AJAX**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo09/AJAX)

- - -



##### (1) Configuração
- - -

1. Criar um novo projeto Spring (https://start.spring.io/)

  - **Project:** Maven Project

  - **Language:** Java

  - **Spring Boot:** 2.4.0

  - **Group:** br.ufscar.dc.dsw

  - **Artifact:** AJAX

  - **Name:** AJAX

  - **Description:** AJAX

  - **Package name:** br.ufscar.dc.dsw

  - **Packaging:** Jar

  - **Java:** 8

    **Dependências:** Spring Web, Spring Boot DevTools e  Thymeleaf

2. Baixar o arquivo .zip e descompactar em um diretório (**AJAX**)

3. Configurar o projeto --- no arquivo **pom.xml** (incluir novas maven dependências)

   ```xml
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
   	<artifactId>jquery-ui</artifactId>
   	<version>1.12.1</version>
   </dependency>
   
   <dependency>
   	<groupId>nz.net.ultraq.thymeleaf</groupId>
   	<artifactId>thymeleaf-layout-dialect</artifactId>
   </dependency>
   ```

   <div style="page-break-after: always"></div>

##### (2) Controlador
- - -
4. Adicionar a classe **br.ufscar.dc.dsw.config.MvcConfig**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   
   @Configuration
   public class MvcConfig implements WebMvcConfigurer {
   	public void addViewControllers(ViewControllerRegistry registry) {
   		registry.addViewController("/").setViewName("index");
   		registry.addViewController("/buscaEstado").setViewName("buscaEstado");
   		registry.addViewController("/buscaNome").setViewName("buscaNome");
   		registry.addViewController("/tabelaDinamica").setViewName("tabelaDinamica");
   	}
   }
   ```

##### (3) Visões
- - -

5. Arquivo **src/main/resources/templates/index.html**

   ```html
   <html>
       <head>
           <title>AJAX</title>
           <meta charset="UTF-8">
           <meta name="viewport" content="width=device-width, initial-scale=1.0">
       </head>
       <body>
       	Busca por estado: <a th:href="@{/buscaEstado}">Clique aqui</a> (Dynamic select)  
           <br/><br/>
           Busca por nome: <a th:href="@{/buscaNome}">Clique aqui</a> (Autocomplete)
           <br/><br/>
           Tabela Dinâmica: <a th:href="@{/tabelaDinamica}">clique aqui</a> (filtro por nome)
           <br/><br/>
       </body>
   </html>
   ```

   

6. Arquivo **src/main/resources/templates/buscaEstado.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org">
   <head>
   <title>AJAX (dynamic select)</title>
   <script th:src="@{/webjars/jquery/jquery.min.js}"></script>
   <script th:src="@{/js/ajaxEstado.js}"></script>
   </head>
   <body>
   	<div class="form-group col-md-6">
   		<label for="estado">Estado</label> <select id="estado"
   			class="form-control">
   			<option value="">Selecione</option>
   		</select>
   	</div>
   
   	<div class="form-group col-md-6">
   		<label for="cidade">Cidade</label> <select id="cidade"
   			class='form-control' onchange='apresentaDS()'>
   		</select>
   	</div>
   
   <br/>
   
   	<a th:href="@{/}">Voltar</a>
   
   </body>
   </html>
   ```
   
   
   
7. Arquivo **src/main/resources/static/js/ajaxEstado.js**

   ```javascript
   function apresentaDS() {
       var cidade = document.getElementById("cidade");
       var estado = document.getElementById("estado");
       var selCidade = cidade.options[cidade.selectedIndex].text; 
       var selEstado = estado.options[estado.selectedIndex].text;
       console.log(selCidade + "/" + selEstado);
       alert("Selecionado: " + selCidade + "/" + selEstado); 
   }
   
   $(document).ready(function() {
   
   	$('#estado').on('change', function() {
   		var estadoId = $(this).val();
   			$.ajax({
   				type : 'GET',
   				url : 'http://localhost:8081/cidades/estados/'+ estadoId,
   				success : function(result) {
   					var s = '<option value="">Selecione</option>';
   					for (var i = 0; i < result.length; i++) {
   						s += '<option value="' + result[i].id + '">'
   						+ result[i].nome
   						+ '</option>';
   					}
   					$('#cidade').html(s);
   				},
   				error: function (request, status, error) {
   				       alert(request.responseText);
   				}
   			});
   	})
   	
   	$.ajax({
   		type : 'GET',  
   		url: "http://localhost:8081/estados",
           success: function(result) {
               var s = '<option value="">Selecione</option>';
               for (var i = 0; i < result.length; i++) {
   						s += '<option value="' + result[i].id + '">'
   						+ result[i].sigla
   						+ '</option>';
   			}
   			$('#estado').html(s);
   		},
   		error: function (request, status, error) {
   				       alert(request.responseText);
   		}
   	});
   });
   ```

   

8. Arquivo **src/main/resources/templates/buscaNome.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org">
   <head>
   <title>AJAX (Autocomplete)</title>
   <script th:src="@{/webjars/jquery/jquery.min.js}"></script>
   <script th:src="@{/webjars/jquery-ui/jquery-ui.js}"></script>
   <link rel="stylesheet" th:href="@{/webjars/jquery-ui/jquery-ui.css}">
   <script th:inline="javascript">
   	$(function() {	
   		$("#cidade").autocomplete({
   			minLength : 2,
   			source: "http://localhost:8081/cidades/filtros",
   			select : function(event, ui) {
   				alert("Selecionado: " + ui.item.value);
   			}
   		});
   	});
   </script>
   </head>
   <div class="ui-widget">
   	<label for="cidade">Nome</label> <input id="cidade" name="cidade"
   		placeholder="Pelo menos 2 caracteres">
   </div>
   <br />
   <a th:href="@{/}">Voltar</a>
   </body>
   </html>
   ```

   

9. Arquivo **src/main/resources/templates/tabelaDinamica.html**

   ```html
   <!DOCTYPE html>
   <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
   	xmlns:th="http://www.thymeleaf.org">
   <head>
   <title>AJAX (dynamic table)</title>
   <script th:src="@{/webjars/jquery/jquery.min.js}"></script>
   <script th:src="@{/js/ajaxNome.js}"></script>
   </head>
   <body>
   	<form name='form'>
   		<div align="center">
   			<p>Lista de Cidades</p>
   			<label for="cidade">Nome</label> <input id="nome" name="nome">
   			<div id="cidades" align="center"></div>
   		</div>
   	</form>
   </body>
   </html>
   ```
   
   
   
10. Arquivo **src/main/resources/static/js/ajaxNome.js**

    ```javascript
    $(document).ready(function() {
    
    		$('#nome').on('keyup', function() {
    			var nome = $(this).val();
    			$.ajax({
    				type : 'GET',
    				url : 'http://localhost:8081/cidades/filtros?term='+ nome,
    				success : function(result) {
    					var s = '';
    					
    					s += '<p>Quantidade: ' + result.length+'</p>';
    					s += '<table border="1" style="width: 400px; border: 1px solid black">';
    					s += '<tr>';
    					s += '   <th style="width: 10%; text-align: center"></th>';
    					s += '   <th style="width: 70%;">Nome</th>';
    					s += '   <th style="width: 20%; text-align: center">Estado</th>';
    					s += '</tr>';
    					for (var i = 0; i < result.length; i++) {
    						var indice = result[i].indexOf("/");
    						var cidade = result[i].slice(0, indice);
    						var estado = result[i].slice(indice + 1);
    						s += '<tr>';
    						s += '   <td style="text-align:center">';
    						s += '      <input type="radio" id="' + cidade + '/' + estado + '" '; 
    						s += '             name="selCidade" value="'+ cidade + '/' + estado + '"';
    						s += '             onclick="alert(' + '\'' + 'Selecionado: ' +  cidade + '/' + estado + '\')">'; 
    						s += '   </td>';
    						s += '   <td>' + cidade + '</td>';
    						s += '   <td style="text-align:center">' + estado + '</td>';
    						s += '</tr>'
    					}					
    					s += '</table>';
    					s += '<br/>';
    					s += '<a href="/">Voltar</a>';
    					$('#cidades').html(s);
    				},
    				error: function (request, status, error) {
    				       alert(request.responseText);
    				}
    			});
    		})
    		
    		$('#nome').keyup();
    	});
    ```
    
    
    
    

##### (4) Execução e testes

- - -

11. Compilar e executar (**mvn spring-boot:run**)

    Verificar que as funcionalidades AJAX estão operacionais

12. Fim

    

#### Leituras adicionais

- - -

- Building REST services with Spring

  https://spring.io/guides/tutorials/rest/

  

- Building a RESTful Web Service
  
  https://spring.io/guides/gs/rest-service/
  
  
  
- AJAX Tutorial

  https://www.w3schools.com/xml/ajax_intro.asp

  

- jQuery - AJAX Introduction
  
  https://www.w3schools.com/jquery/jquery_ajax_intro.asp
