## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA**

- - -

#### 01 - CRUD MVC utilizando as tecnologias Spring MVC, Thymeleaf & JPA

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo08/LivrariaMVC-v1)

- - -



##### (1) Configuração
- - -


1. Criar um novo projeto Spring (https://start.spring.io/)

  - **Project:** Maven Project

  - **Language:** Java

  - **Spring Boot:** 2.4.0

  - **Group:** br.ufscar.dc.dsw

  - **Artifact:** LivrariaMVC

  - **Name:** LivrariaMVC

  - **Description:** LivrariaMVC

  - **Package name:** br.ufscar.dc.dsw

  - **Packaging:** Jar

  - **Java:** 8

    **Dependências:** Spring Web, Spring Data JPA, Spring Boot DevTools, Thymeleaf & Validation

2. Baixar o arquivo .zip e descompactar em um diretório (**LivrariaMVC**)

3. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 4)

   3.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Livraria** criado anteriormente (Roteiro 04-01)

   ```properties
   # DERBY
   spring.datasource.url=jdbc:derby://localhost:1527/Livraria
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver
   # JPA
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   # THYMELEAF
   spring.thymeleaf.cache = false
   ```
   
   3.2. No arquivo **pom.xml**, adicionaremos a biblioteca do ***Derby JDBC Driver*** como dependência do projeto
   
   ```xml
   <dependency>
      <groupId>org.apache.derby</groupId>
      <artifactId>derbyclient</artifactId>
      <scope>runtime</scope>
   </dependency>
   ```
   
4. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

   4.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Livraria** criado anteriormente (Roteiro 04-01)

   ```properties
   # MySQL
   spring.datasource.url = jdbc:mysql://localhost:3306/Livraria
   spring.datasource.username = root
   spring.datasource.password = root
   # JPA
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   # THYMELEAF
   spring.thymeleaf.cache = false
   ```
   4.2. No arquivo **pom.xml**, adicionaremos a biblioteca do ***MySQL JDBC Driver*** como dependência do projeto
   
   ```xml
   <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <scope>runtime</scope>
   </dependency>
   ```
   
5. Configurar o projeto --- no arquivo **pom.xml** (incluir novas maven dependências)
Lembrar de adicionar o **plugin** native2ascii (ver roteiros anteriores)
   
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
5.1 Adicionar a biblioteca Native2Ascii (https://native2ascii.net/) via Maven, adicionando as seguintes linhas ao arquivo **pom.xml**:

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

##### (2) Home Page
- - -

6. Criar a página principal (visão e controlador)

   Fazer download do arquivo **resources.zip** e descompactar o conteúdo no diretório **src/main/resources**

   6.1. Explore os arquivos presentes no diretório **src/main/resources/static** e **src/main/resources/templates**

   ```
src/main/resources/static/css/style.css
   src/main/resources/static/image/error.ico
   src/main/resources/static/image/favicon.png
   src/main/resources/static/image/image.png
   src/main/resources/templates/fragments/alert.html
   src/main/resources/templates/fragments/footer.html
   src/main/resources/templates/fragments/header.html
   src/main/resources/templates/fragments/modal.html
   src/main/resources/templates/fragments/sidebar.html
   src/main/resources/templates/fragments/validacao.html
   src/main/resources/templates/home.html
   src/main/resources/templates/layout.html
   ```
   
   
   6.2. Criar a classe **br.ufscar.dc.dsw.controller.HomeController**
   
   ```java
   package br.ufscar.dc.dsw.controller;
   
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.GetMapping;
   
   @Controller
   public class HomeController {
   	@GetMapping("/")
   	public String home() {
   		return "home";
   	}
   }
   ```
   6.3. Executar (**mvn spring-boot:run**) e ver o efeito (a página **home.html** é renderizada)

<div style="page-break-after: always"></div>


##### (3) CRUD Editora: Modelo
- - -

7. Criar as classes de domínio (pacote **br.ufscar.dc.dsw.domain**)

   7.1. Classe abstrata **br.ufscar.dc.dsw.domain.AbstractEntity**
   
   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.io.Serializable;
   import javax.persistence.GeneratedValue;
   import javax.persistence.GenerationType;
   import javax.persistence.Id;
   import javax.persistence.MappedSuperclass;
   
   @SuppressWarnings("serial")
   @MappedSuperclass
   public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
   	
   	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   	private ID id;
   
   	public ID getId() {
   		return id;
   	}
   	public void setId(ID id) {
   		this.id = id;
   	}
   
   	@Override
   	public int hashCode() {
   		final int prime = 31;
   		int result = 1;
   		result = prime * result + ((id == null) ? 0 : id.hashCode());
   		return result;
   	}
   
   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj) return true;
   		if (obj == null) return false;
   		if (getClass() != obj.getClass()) return false;
   		AbstractEntity<?> other = (AbstractEntity<?>) obj;
   		if (id == null) {
   			if (other.id != null)
   				return false;
   		} else if (!id.equals(other.id))
   			return false;
   		return true;
   	}
   
   	@Override
   	public String toString() {
   		return "id=" + id;
   	}	
   }
   ```
   
   
   
   
   
   7.2. Classe **br.ufscar.dc.dsw.domain.Livro**
   
   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.math.BigDecimal;
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.JoinColumn;
   import javax.persistence.ManyToOne;
   import javax.persistence.Table;
   import javax.validation.constraints.NotBlank;
   import javax.validation.constraints.NotNull;
   import javax.validation.constraints.Size;
   import org.springframework.format.annotation.NumberFormat;
   import org.springframework.format.annotation.NumberFormat.Style;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Livro")
   public class Livro extends AbstractEntity<Long> {
   
   	@NotBlank(message = "{NotBlank.livro.titulo}")
   	@Size(max = 60)
   	@Column(nullable = false, length = 60)
   	private String titulo;
   
   	@NotBlank(message = "{NotBlank.livro.autor}")
   	@Size(max = 60)
   	@Column(nullable = false, length = 60)
   	private String autor;
       
   	@NotNull(message = "{NotNull.livro.ano}")
   	@Column(nullable = false, length = 5)
   	private Integer ano;
   	
   	@NotNull(message = "{NotNull.livro.preco}")
   	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
   	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
   	private BigDecimal preco;
       
   	@NotNull(message = "{NotNull.livro.editora}")
   	@ManyToOne
   	@JoinColumn(name = "editora_id")
   	private Editora editora;
   
   	public String getTitulo() {
   		return titulo;
   	}
   
   	public void setTitulo(String titulo) {
   		this.titulo = titulo;
   	}
   
   	public String getAutor() {
   		return autor;
   	}
   
   	public void setAutor(String autor) {
   		this.autor = autor;
   	}
   
   	public Integer getAno() {
   		return ano;
   	}
   
   	public void setAno(Integer ano) {
   		this.ano = ano;
   	}
   
   	public BigDecimal getPreco() {
   		return preco;
   	}
   
   	public void setPreco(BigDecimal preco) {
   		this.preco = preco;
   	}
   
   	public Editora getEditora() {
   		return editora;
   	}
   
   	public void setEditora(Editora editora) {
   		this.editora = editora;
   	}
   }
   ```
   
   7.3. Classe **br.ufscar.dc.dsw.domain.Editora**
   
   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.List;
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.OneToMany;
   import javax.persistence.Table;
   import javax.validation.constraints.NotBlank;
   import javax.validation.constraints.Size;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Editora")
   public class Editora extends AbstractEntity<Long> {
   
   	@NotBlank
   	@Size(min = 18, max = 18, message = "{Size.editora.CNPJ}")
   	@Column(nullable = false, unique = true, length = 60)
   	private String CNPJ;
   	
   	@NotBlank
   	@Size(min = 3, max = 60)
   	@Column(nullable = false, unique = true, length = 60)
   	private String nome;
   
   	@OneToMany(mappedBy = "editora")
   	private List<Livro> livros;
   	
   	public String getCNPJ() {
   		return CNPJ;
   	}
   
   	public void setCNPJ(String CNPJ) {
   		this.CNPJ = CNPJ;
   	}
   	
   	public String getNome() {
   		return nome;
   	}
   
   	public void setNome(String nome) {
   		this.nome = nome;
   	}
   
   	public List<Livro> getLivros() {
   		return livros;
   	}
   
   	public void setLivros(List<Livro> livros) {
   		this.livros = livros;
   	}
   }
   ```
   7.4 Criar arquivo de mensagens de validação (**src/main/resources/ValidationMessages.properties**)
   
   ```properties
   # Mensagens genéricas
   
   javax.validation.constraints.Size.message = Deve estar entre {min} e {max} caracteres.
   javax.validation.constraints.NotBlank.message = É obrigatório.
   javax.validation.constraints.NotNull.message = É obrigatório.
   javax.validation.constraints.Digits.message = Deve conter no máximo {integer} digitos.
   
   # Validação campos Livro
   
   NotBlank.livro.titulo = O título do livro é obrigatório.
   NotBlank.livro.autor = O autor do livro é obrigatório.
   NotNull.livro.ano = O ano do livro é obrigatório.
   NotNull.livro.preco = O preço do livro é obrigatório.
   NotNull.livro.editora = Selecione uma editora.
   
   # Validação campos Editora
   
   Size.editora.CNPJ = O CNPJ da editora deve ter {max} caracteres.
   ```
   <div style="page-break-after: always"></div>
   
8. Criar as interfaces DAO (pacote **br.ufscar.dc.dsw.dao**)

   8.1. Interface **br.ufscar.dc.dsw.dao.IEditoraDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Editora;
   
   @SuppressWarnings("unchecked")
   public interface IEditoraDAO extends CrudRepository<Editora, Long>{
   	Editora findById(long id);
   	List<Editora> findAll();
   	Editora save(Editora editora);
   	void deleteById(Long id);
   }
   ```
   
9. Criar as classes e interfaces de serviço (pacote **br.ufscar.dc.dsw.service**)

   9.1. Interface **br.ufscar.dc.dsw.service.spec.IEditoraService**
   
   ```java
   package br.ufscar.dc.dsw.service.spec;
   import java.util.List;
   import br.ufscar.dc.dsw.domain.Editora;
   public interface IEditoraService {
   	Editora buscarPorId(Long id);
   	List<Editora> buscarTodos();
   	void salvar(Editora editora);
   	void excluir(Long id);
   	boolean editoraTemLivros(Long id);
   }
   ```
   9.2. Classe **br.ufscar.dc.dsw.service.impl.EditoraService**
   
   ```java
   package br.ufscar.dc.dsw.service.impl;
   
   import java.util.List;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;
   import br.ufscar.dc.dsw.dao.IEditoraDAO;
   import br.ufscar.dc.dsw.domain.Editora;
   import br.ufscar.dc.dsw.service.spec.IEditoraService;
   
   @Service
   @Transactional(readOnly = false)
   public class EditoraService implements IEditoraService {
   
   	@Autowired
   	IEditoraDAO dao;
   	
   	public void salvar(Editora editora) {
   		dao.save(editora);
   	}
   
   	public void excluir(Long id) {
   		dao.deleteById(id);
   	}
   
   	@Transactional(readOnly = true)
   	public Editora buscarPorId(Long id) {
   		return dao.findById(id.longValue());
   	}
   
   	@Transactional(readOnly = true)
   	public List<Editora> buscarTodos() {
   		return dao.findAll();
   	}
   	
   	@Transactional(readOnly = true)
   	public boolean editoraTemLivros(Long id) {
   		return !dao.findById(id.longValue()).getLivros().isEmpty(); 
   	}
   }
   ```

##### (4) CRUD Editora: Controlador
- - -

10. Criar a classe controlador (pacote **br.ufscar.dc.dsw.controller**)

    10.1. Classe **br.ufscar.dc.dsw.controller.EditoraController**
    
    ```java
    package br.ufscar.dc.dsw.controller;
    
    import javax.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.ModelMap;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import br.ufscar.dc.dsw.domain.Editora;
    import br.ufscar.dc.dsw.service.spec.IEditoraService;
    
    @Controller
    @RequestMapping("/editoras")
    public class EditoraController {
    	
    	@Autowired
    	private IEditoraService service;
    	
    	@GetMapping("/cadastrar")
    	public String cadastrar(Editora editora) {
    		return "editora/cadastro";
    	}
    	
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    		model.addAttribute("editoras",service.buscarTodos());
    		return "editora/lista";
    	}
    	
    	@PostMapping("/salvar")
    	public String salvar(@Valid Editora editora, BindingResult result, RedirectAttributes attr) {
    		
    		if (result.hasErrors()) {
    			return "editora/cadastro";
    		}
    		
    		service.salvar(editora);
    		attr.addFlashAttribute("sucess", "Editora inserida com sucesso.");
    		return "redirect:/editoras/listar";
    	}
    	
    	@GetMapping("/editar/{id}")
    	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    		model.addAttribute("editora", service.buscarPorId(id));
    		return "editora/cadastro";
    	}
    	
    	@PostMapping("/editar")
    	public String editar(@Valid Editora editora, BindingResult result, RedirectAttributes attr) {
    		
    		if (result.hasErrors()) {
    			return "editora/cadastro";
    		}
    
    		service.salvar(editora);
    		attr.addFlashAttribute("sucess", "Editora editada com sucesso.");
    		return "redirect:/editoras/listar";
    	}
    	
    	@GetMapping("/excluir/{id}")
    	public String excluir(@PathVariable("id") Long id, ModelMap model) {
    		if (service.editoraTemLivros(id)) {
    			model.addAttribute("fail", "Editora não excluída. Possui livro(s) vinculado(s).");
    		} else {
    			service.excluir(id);
    			model.addAttribute("sucess", "Editora excluída com sucesso.");
    		}
    		return listar(model);
    	}
    }
    ```

<div style="page-break-after: always"></div>

##### (5) CRUD Editora: Visão
- - -

11. Criar as visões (no diretório **src/main/resources/templates/editora**)

    11.1. Criar o arquivo **cadastro.html** (no diretório **src/main/resources/templates/editora**)
    
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
    						<span>Cadastrar Editoras</span></li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/editoras/listar}"
    					role="button"> <span class="oi oi-spreadsheet" title="Listar"
    					aria-hidden="true"></span> <span>Listar Editoras</span>
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
    						<label for="nome">Nome</label> 
    						<input type="text" class="form-control" id="nome" placeholder="Nome"
    							autofocus="autofocus" th:field="*{nome}"
    							th:classappend="${#fields.hasErrors('nome')} ? is-invalid" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{nome}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="CNPJ">CNPJ</label> 
    						<input type="text" class="form-control" id="CNPJ" placeholder="__.___.___/____-__"
    							autofocus="autofocus" th:field="*{CNPJ}"
    							th:classappend="${#fields.hasErrors('CNPJ')} ? is-invalid" 
    							data-mask="00.000.000/0000-00" data-mask-reverse="true" />
    						                               
    						<div class="invalid-feedback">
    							<span th:errors="*{CNPJ}"></span>
    						</div>
    					</div>
    				</div>
    
    				<input type="hidden" id="id" th:field="*{id}" />
    				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
    			</form>
    		</div>
    	</section>
    </body>
    </html>
    ```
    
    
    
    11.2. Criar o arquivo **lista.html** (no diretório **src/main/resources/templates/editora**)
    
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
    						<span>Lista de Editoras</span>
    					</li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/editoras/cadastrar}" role="button"> 
    					<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    					<span>Nova Editora</span>
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
    							<th>CNPJ</th>
    							<th>Nome</th>
    							<th>Ação</th>
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
    									<span class="oi oi-brush" title="Editar" aria-hidden="true"> </span>
    								</a>
    								<button
    									th:id="${'btn_editoras/excluir/' + editora.id}"
    									type="button" class="btn btn-danger btn-sm"
    									data-toggle="modal" data-target="#myModal">
    									<span class="oi oi-circle-x" title="Excluir" aria-hidden="true"></span>
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
    <div style="page-break-after: always"></div>
    
    11.3 Alterar o arquivo **src/main/resources/fragments/sidebar.html** (para incluir links para o CRUD Editora)
    
    ```html
    <ul class="nav nav-pills">
    	<li class="nav-item"><span class="nav-link active">Editoras</span></li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/editoras/cadastrar}"> 
                <i class="oi oi-plus"></i>		
                <span>Cadastrar</span></a>
        </li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/editoras/listar}"> 
                <i class="oi oi-spreadsheet"></i>
    			<span>Listar</span></a>
        </li>
    </ul>
    ```
    11.4. Atualizar a classe **br.ufscar.dc.dsw.LivrariaMvcApplication**
    
    ```java
    package br.ufscar.dc.dsw;
    
    import java.math.BigDecimal;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import br.ufscar.dc.dsw.dao.IEditoraDAO;
    import br.ufscar.dc.dsw.domain.Editora;
    
    @SpringBootApplication
    public class LivrariaMvcApplication {
    	public static void main(String[] args) {
    		SpringApplication.run(LivrariaMvcApplication.class, args);
    	}
    	@Bean
    	public CommandLineRunner demo(IEditoraDAO editoraDAO) {
    		return (args) -> {	
    			Editora e1 = new Editora();
    			e1.setCNPJ("55.789.390/0008-99");
    			e1.setNome("Companhia das Letras");
    			editoraDAO.save(e1);
    			
    			Editora e2 = new Editora();
    			e2.setCNPJ("71.150.470/0001-40");
    			e2.setNome("Record");
    			editoraDAO.save(e2);
    			
    			Editora e3 = new Editora();
    			e3.setCNPJ("32.106.536/0001-82");
    			e3.setNome("Objetiva");
    			editoraDAO.save(e3);			
    		};
    	}
    }
    ```
    11.5. Executar (**mvn spring-boot:run**) e ver o efeito (o CRUD Editora está operacional)




##### (6) CRUD Livro: Modelo
- - -

12. Criar as classes de Conversão (pacote  **br.ufscar.dc.dsw.conversor**)

    12.1. Classe **br.ufscar.dc.dsw.conversor.EditoraConversor** 
    
    ```java
    package br.ufscar.dc.dsw.conversor;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.convert.converter.Converter;
    import org.springframework.stereotype.Component;
    
    import br.ufscar.dc.dsw.domain.Editora;
    import br.ufscar.dc.dsw.service.spec.IEditoraService;
    
    @Component
    public class EditoraConversor implements Converter<String, Editora>{
    
    	@Autowired
    	private IEditoraService service;
    	
    	@Override
    	public Editora convert(String text) {
    		
    		if (text.isEmpty()) {
    		 return null;	
    		}
    		
    		Long id = Long.valueOf(text);	
    		return service.buscarPorId(id);
    	}
    }
    ```
    12.2 Classe **br.ufscar.dc.dsw.conversor.BigDecimalConversor** 
    
    ```java
    package br.ufscar.dc.dsw.conversor;
    
    import java.math.BigDecimal;
    import org.springframework.core.convert.converter.Converter;
    
    public class BigDecimalConversor implements Converter<String, BigDecimal> {
    
    	@Override
    	public BigDecimal convert(String text) {
    
    		if (text.isEmpty()) {
    			return null;
    		} else {
    			text = text.replace(',','.');	
    		}	
    		return new BigDecimal(Double.parseDouble(text));
    	}
    }
    ```
    12.3 Criar a classe de configuração **br.ufscar.dc.dsw.config.MvcConfig**
    
    ```java
    package br.ufscar.dc.dsw.config;
    
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.format.FormatterRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
    
    import br.ufscar.dc.dsw.conversor.BigDecimalConversor;
    
    
    @Configuration
    @ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
    public class MvcConfig implements WebMvcConfigurer {
        
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new BigDecimalConversor());
        }
    }
    ```


13. Criar as classes e interfaces DAO (pacote **br.ufscar.dc.dsw.dao**)

    13.1. Interface **br.ufscar.dc.dsw.dao.ILivroDAO**
    
    ```java
    package br.ufscar.dc.dsw.dao;
    
    import java.util.List;
    import org.springframework.data.repository.CrudRepository;
    import br.ufscar.dc.dsw.domain.Livro;
    
    @SuppressWarnings("unchecked")
    public interface ILivroDAO extends CrudRepository<Livro, Long>{
    	Livro findById(long id);
    	List<Livro> findAll();
    	Livro save(Livro editora);
    	void deleteById(Long id);
    }
    ```
    <div style="page-break-after: always"></div>


14. Criar as classes e interfaces de serviço (pacote **br.ufscar.dc.dsw.service**)

    14.1. Interface **br.ufscar.dc.dsw.service.spec.ILivroService**
    
    ```java
    package br.ufscar.dc.dsw.service.spec;
    
    import java.util.List;
    import br.ufscar.dc.dsw.domain.Livro;
    
    public interface ILivroService {
    	Livro buscarPorId(Long id);
    	List<Livro> buscarTodos();
    	void salvar(Livro livro);
    	void excluir(Long id);
    }
    ```
    
    14.2. Classe **br.ufscar.dc.dsw.service.impl.LivroService**
    
    ```java
    package br.ufscar.dc.dsw.service.impl;
    
    import java.util.List;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    import br.ufscar.dc.dsw.dao.ILivroDAO;
    import br.ufscar.dc.dsw.domain.Livro;
    import br.ufscar.dc.dsw.service.spec.ILivroService;
    
    @Service
    @Transactional(readOnly = false)
    public class LivroService implements ILivroService {
    
    	@Autowired
    	ILivroDAO dao;
    	
    	public void salvar(Livro livro) {
    		dao.save(livro);
    	}
    	
    	public void excluir(Long id) {
    		dao.deleteById(id);
    	}
    	
    	@Transactional(readOnly = true)
    	public Livro buscarPorId(Long id) {
    		return dao.findById(id.longValue());
    	}
    	
    	@Transactional(readOnly = true)
    	public List<Livro> buscarTodos() {
    		return dao.findAll();
    	}
    }
    ```



##### (7) CRUD Livro: Controlador
- - -

15. Criar a classe controlador (pacote **br.ufscar.dc.dsw.controller**)
    15.1. Classe **br.ufscar.dc.dsw.controller.LivroController**
    
    ```java
    package br.ufscar.dc.dsw.controller;
    
    import java.util.List;
    
    import javax.validation.Valid;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.ModelMap;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    
    import br.ufscar.dc.dsw.domain.Editora;
    import br.ufscar.dc.dsw.domain.Livro;
    import br.ufscar.dc.dsw.service.spec.IEditoraService;
    import br.ufscar.dc.dsw.service.spec.ILivroService;
    
    @Controller
    @RequestMapping("/livros")
    public class LivroController {
    
    	@Autowired
    	private ILivroService livroService;
    
    	@Autowired
    	private IEditoraService editoraService;
    
    	@GetMapping("/cadastrar")
    	public String cadastrar(Livro livro) {
    		return "livro/cadastro";
    	}
    
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    		model.addAttribute("livros", livroService.buscarTodos());
    		return "livro/lista";
    	}
    
    	@PostMapping("/salvar")
    	public String salvar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "livro/cadastro";
    		}
    
    		livroService.salvar(livro);
    		attr.addFlashAttribute("sucess", "Livro inserido com sucesso");
    		return "redirect:/livros/listar";
    	}
    
    	@GetMapping("/editar/{id}")
    	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    		model.addAttribute("livro", livroService.buscarPorId(id));
    		return "livro/cadastro";
    	}
    
    	@PostMapping("/editar")
    	public String editar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "livro/cadastro";
    		}
    
    		livroService.salvar(livro);
    		attr.addFlashAttribute("sucess", "Livro editado com sucesso.");
    		return "redirect:/livros/listar";
    	}
    
    	@GetMapping("/excluir/{id}")
    	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
    		livroService.excluir(id);
    		attr.addFlashAttribute("sucess", "Livro excluído com sucesso.");
    		return "redirect:/livros/listar";
    	}
    
    	@ModelAttribute("editoras")
    	public List<Editora> listaEditoras() {
    		return editoraService.buscarTodos();
    	}
    }
    ```


<div style="page-break-after: always"></div>

##### (8) CRUD Livro: Visão
- - -


16. Criar as visões (no diretório **src/main/resources/templates/livro**)

    16.1. Criar o arquivo **cadastro.html** (no diretório **src/main/resources/templates/livro**)

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
    						<span>Cadastrar Livros</span></li>
    				</ul>
    			</div>
    			<a class="btn btn-primary btn-md" th:href="@{/livros/listar}"
    				role="button"> <span class="oi oi-spreadsheet" title="Cadastro"
    				aria-hidden="true"></span> <span>Listar Livros</span>
    			</a>
    		</nav>
    
    		<div class="container" id="cadastro">
    
    			<div th:replace="fragments/alert"></div>
    
    			<form
    				th:action="${livro.id == null} ? @{/livros/salvar} : @{/livros/editar}"
    				th:object="${livro}" method="POST">
    
    				<div class="form-row">
    					<div class="form-group col-md-6">
    						<label for="nome">Título</label> 
    						<input type="text" class="form-control" id="nome" placeholder="Título"
    							autofocus="autofocus" th:field="*{titulo}"
    							th:classappend="${#fields.hasErrors('titulo')} ? is-invalid" />
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{titulo}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="nome">Autor</label> 
    						<input type="text" class="form-control" id="nome" placeholder="Autor"
    							autofocus="autofocus" th:field="*{autor}"
    							th:classappend="${#fields.hasErrors('autor')} ? is-invalid" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{autor}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="editora">Editora</label> 
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
    						<label for="nome">Ano</label> 
    						<input type="number" class="form-control" id="nome" placeholder="Ano"
    							autofocus="autofocus" th:field="*{ano}"
    							th:classappend="${#fields.hasErrors('ano')} ? is-invalid" />
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{ano}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="preco">Preço</label> 
    						<input type="text" class="form-control" id="preco" placeholder="Preço"
    							data-mask="000000,00" data-mask-reverse="true" th:field="*{preco}"
    							th:classappend="${#fields.hasErrors('preco')} ? is-invalid" />
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{preco}"></span>
    						</div>
    					</div>
    				</div>
    				<input type="hidden" id="id" th:field="*{id}" />
    				<button type="submit" class="btn btn-primary btn-sm">Salvar</button>
    			</form>
    		</div>
    	</section>
    </body>
    </html>
    ```
    
    16.2. Criar a visão **lista.html** (no diretório **src/main/resources/templates/livro**)
    
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
    						<span>Lista de Livros</span>
    					</li>
    				</ul>
    			</div>
    			<a class="btn btn-primary btn-md" th:href="@{/livros/cadastrar}" role="button"> 
    				<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    				<span>Novo Livro</span>
    			</a>
    		</nav>
    
    		<div class="container" id="listagem">
    
    			<div th:replace="fragments/alert"></div>
    
    			<div class="table-responsive">
    				<table class="table table-striped table-hover table-sm">
    					<thead>
    						<tr>
    							<th>#</th>
    							<th>Título</th>
    							<th>Autor</th>
    							<th>Editora</th>
    							<th>Ano</th>
    							<th>Preço</th>
    							<th>Ação</th>
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
    									<span class="oi oi-brush" title="Editar" aria-hidden="true"></span>
    								</a>
    								<button th:id="${#strings.concat('btn_livros/excluir/',livro.id)}" 
    									type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal">
    									<span class="oi oi-circle-x" title="Excluir" aria-hidden="true"></span>
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
    
    16.3. Alterar o arquivo **src/main/resources/fragments/sidebar.html** (para incluir links para o CRUD Livro)
    
    ```html
    <ul class="nav nav-pills">
    	<li class="nav-item"><span class="nav-link active">Livros</span></li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/livros/cadastrar}"> 
                <i class="oi oi-plus"></i> 
                <span>Cadastrar</span>
    			</a>
        </li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/livros/listar}"> 
                <i class="oi oi-spreadsheet"></i>
    			<span>Listar</span>
    		</a>
        </li>
    </ul>
    ```
    <div style="page-break-after: always"></div>
    
    16.4. Atualizar a classe **br.ufscar.dc.dsw.LivrariaMvcApplication**
    
    ```java
    package br.ufscar.dc.dsw;
    
    import java.math.BigDecimal;
    
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    
    import br.ufscar.dc.dsw.dao.IEditoraDAO;
    import br.ufscar.dc.dsw.dao.ILivroDAO;
    import br.ufscar.dc.dsw.domain.Editora;
    import br.ufscar.dc.dsw.domain.Livro;
    
    @SpringBootApplication
    public class LivrariaMvcApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(LivrariaMvcApplication.class, args);
    	}
    
    	@Bean
    	public CommandLineRunner demo(IEditoraDAO editoraDAO, ILivroDAO livroDAO) {
    		return (args) -> {
    						
    			Editora e1 = new Editora();
    			e1.setCNPJ("55.789.390/0008-99");
    			e1.setNome("Companhia das Letras");
    			editoraDAO.save(e1);
    			
    			Editora e2 = new Editora();
    			e2.setCNPJ("71.150.470/0001-40");
    			e2.setNome("Record");
    			editoraDAO.save(e2);
    			
    			Editora e3 = new Editora();
    			e3.setCNPJ("32.106.536/0001-82");
    			e3.setNome("Objetiva");
    			editoraDAO.save(e3);
    			
    			Livro l1 = new Livro();
    			l1.setTitulo("Ensaio sobre a Cegueira");
    			l1.setAutor("José Saramago");
    			l1.setAno(1995);
    			l1.setPreco(BigDecimal.valueOf(54.9));
    			l1.setEditora(e1);
    			livroDAO.save(l1);
    			
    			Livro l2 = new Livro();
    			l2.setTitulo("Cem anos de Solidão");
    			l2.setAutor("Gabriel Garcia Márquez");
    			l2.setAno(1977);
    			l2.setPreco(BigDecimal.valueOf(59.9));
    			l2.setEditora(e2);
    			livroDAO.save(l2);
    			
    			Livro l3 = new Livro();
    			l3.setTitulo("Diálogos Impossíveis");
    			l3.setAutor("Luis Fernando Verissimo");
    			l3.setAno(2012);
    			l3.setPreco(BigDecimal.valueOf(22.9));
    			l3.setEditora(e3);
    			livroDAO.save(l3);
    		};
    	}
    }
    ```
    
    16.5. Executar (**mvn spring-boot:run**) e ver o efeito (o CRUD Livro está operacional)



##### (9) Página de erros

- - -

17. Adicionar o controlador para página de erros

    17.1 Classe **br.ufscar.dc.dsw.controller.ErrorViewController**
    
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
    				case 404:
    					model.addObject("error", "Página não encontrada.");
    					model.addObject("message", "A url para a página '" + map.get("path") + "' não existe.");
    					break;
    				case 500:
    					model.addObject("error", "Ocorreu um erro interno no servidor.");
    					model.addObject("message", "Ocorreu um erro inexperado, tente mais tarde.");
    					break;
    				default:
    					model.addObject("error", map.get("error"));
    					model.addObject("message", map.get("message"));
    					break;
    		}		
    		return model;
    	}
    }
    ```



18. Adicionar a página de erros (arquivo **src/main/resources/template/error.html**)

```html
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" th:href="@{/image/favicon.png}" />

<title th:text="${status}"></title>

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
		
		<h2 th:text="${error}"></h2>
		
		<div>
			<span th:text="${message}"></span><br/><br/>
			<a class="btn btn-primary" type="button" href="javascript:history.back()">Voltar a página anterior</a>
		</div>
	</div>
</body>
</html>	
```

19. Executar (**mvn spring-boot:run**) e ver o efeito quando tenta acessar uma página não existente (por exemplo: http://localhost:8080/book)
20. Fim



#### Leituras adicionais

- - -

* Introduction to WebJars 

  https://www.baeldung.com/maven-webjars

  

* Spring WebJars tutorial 

  http://zetcode.com/spring/webjars/
