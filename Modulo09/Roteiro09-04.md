## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 09 - REST API**

- - -

#### 04 - Exemplo de servidor REST

**Cidades REST Server & AJAX**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo09/CidadesRS-v2)

- - -



##### (1) Configuração
- - -

1. Fazer uma cópia do diretório da aplicação **CidadesRS** (Roteiro 09-03).

   ```sh
   % cp -r CidadesRS CidadesRS-v2
   ```

2. Abrir a aplicação cópia

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

##### (2) Controladores
- - -
4. Criar as classes  controladores (pacote **br.ufscar.dc.dsw.controller**)

   4.1. Classe **br.ufscar.dc.dsw.controller.HomeController**
   
   ```java
   package br.ufscar.dc.dsw.controller;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.ModelMap;
   import org.springframework.web.bind.annotation.GetMapping;
   
   import br.ufscar.dc.dsw.service.spec.ICidadeService;
   import br.ufscar.dc.dsw.service.spec.IEstadoService;
   
   @Controller
   public class HomeController {
   
   	@Autowired
   	private IEstadoService estadoService;
   	
   	@Autowired
   	private ICidadeService cidadeService;
   
   	@GetMapping("/")
   	public String index() {
   		return "index";
   	}
   
   	@GetMapping("/buscaEstado")
   	public String buscaEstado(ModelMap model) {
   		model.addAttribute("estados", estadoService.findAll());
   		return "buscaEstado";
   	}
   	
   	@GetMapping("/buscaNome")
   	public String buscaNome() {
   		return "buscaNome";
   	}
   	
   	@GetMapping("/tabelaDinamica")
   	public String tabelaDinamica(ModelMap model) {
   		model.addAttribute("cidades", cidadeService.findAll());
   		return "tabelaDinamica";
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
   <script th:inline="javascript">
   var contextRoot = [[@{/}]];
   </script>
   <script th:src="@{/js/ajaxEstado.js}"></script>
   </head>
   <body>
   	<div class="form-group col-md-6">
   		<label for="estado">Estado</label> <select id="estado"
   			class="form-control">
   			<option value="">Selecione</option>
   			<option th:each="estado : ${estados}" th:value="${estado.id}"
   				th:text="${estado.nome}"></option>
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
   				url : contextRoot +'cidades/estados/'+ estadoId,
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
   			source: "/cidades/filtros",
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
   <script th:inline="javascript">
   var contextRoot = [[@{/}]];
   </script>
   <script th:src="@{/js/ajaxNome.js}"></script>
   </head>
   <body>
   	<form name='form'>
   		<div align="center">
   			<p>Lista de Cidades</p>
   			<label for="cidade">Nome</label> <input id="nome" name="nome">
   			<div id="cidades">
   
   				<p>
   					Quantidade: <span th:text="${#lists.size(cidades)}">[Engine
   						Size]</span>
   				</p>
   
   
   				<table border="1" style="width: 400px; border: 1px solid black">
   					<tr>
   						<th style="width: 10%; text-align: center"></th>
   						<th style="width: 70%;">Nome</th>
   						<th style="width: 20%; text-align: center">Estado</th>
   					</tr>
   					<tr th:each="cidade : ${cidades}">
   						<td style="text-align: center"><input
   							type="radio" th:id="|${cidade.nome}/${cidade.estado.sigla}|"
   							name="selCidade" th:value="|${cidade.nome}/${cidade.estado.sigla}|"
   							th:attr="onclick=|alert('Selecionado: ${cidade.nome}/${cidade.estado.sigla}')|" >
   						</td>
   						<td th:text="${cidade.nome}"></td>
   						<td th:text="${cidade.estado.sigla}" style="text-align: center"></td>
   					</tr>
   				</table>
   			</div>
   		</div>
   	</form>
   	<a th:href="@{/}">Voltar</a>
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
    				url : contextRoot +'cidades/filtros?term='+ nome,
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
    					$('#cidades').html(s);
    				},
    				error: function (request, status, error) {
    				       alert(request.responseText);
    				}
    			});
    		})
    	});
    ```
    
    
    
    

##### (4) Execução e testes

- - -

11. Compilar e executar (**mvn spring-boot:run**)

    Verificar que as operações REST estão funcionais

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
