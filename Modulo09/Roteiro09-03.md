## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 09 - REST API**

- - -

#### 03 - Transações (CRUD REST API)
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo09/TransacoesRS)

- - -



##### (1) Configuração

- - -


1. Criar um novo projeto Spring (https://start.spring.io/)

  - **Project:** Maven Project

  - **Language:** Java

  - **Spring Boot:** 2.4.0

  - **Group:** br.ufscar.dc.dsw

  - **Artifact:** TransacoesRS

  - **Name:** TransacoesRS

  - **Description:** TransacoesRS

  - **Package name:** br.ufscar.dc.dsw

  - **Packaging:** Jar

  - **Java:** 8

    **Dependências:** Spring Web, Spring Data JPA, Spring Boot DevTools, Thymeleaf & Validation

2. Baixar o arquivo .zip e descompactar em um diretório (**TransacoesRS**)

3. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   3.1. Criar novo banco de dados **Transacoes** (usuário: **root**, senha: **root**)

   - Crie o arquivo **db/Derby/create.sql**

     ```sql
     connect 'jdbc:derby:Transacoes;create=true;user=root;password=root';
     disconnect;
     quit;
     ```
     
     <div style="page-break-after: always"></div>

   3.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:Transacoes;create=true;user=root;password=root';
   ij> disconnect;
   ij> quit;
   ```
   
   3.3. Iniciar o servidor **Apache Derby**. Em um terminal executar: 

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar $DERBY_HOME/lib/derbyrun.jar server start
   ```
   
   3.4. Adicionar biblioteca do ***Derby JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
<dependency>
        <groupId>org.apache.derby</groupId>
        <artifactId>derbyclient</artifactId>
        <version>10.14.2.0</version>
        <scope>runtime</scope>
    </dependency>
   ```
   
   3.5 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Transacoes**

   ```properties
   # DERBY
   spring.datasource.url=jdbc:derby://localhost:1527/Transacoes
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

   # SERVER PORT
   server.port = 8081
   ```
   
   <div style="page-break-after: always"></div>

4. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

      4.1. Criar novo banco de dados **Transacoes** (usuário: **root**, senha: **root**)

      - Crie o arquivo **db/MySQL/create.sql**
        
      ```sql
      create database Transacoes;
      use Transacoes;
   ```
   4.2. Em um terminal no diretório do projeto, executar 
   
   ```
   % mysql -uroot -p
   Enter password: 
   Welcome to the MySQL monitor.  Commands end with ; or \g.
   Your MySQL connection id is 13
   Server version: 8.0.21 MySQL Community Server - GPL
   Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.
   Oracle is a registered trademark of Oracle Corporation and/or its
   affiliates. Other names may be trademarks of their respective
   owners.
   Type 'help;' or '\h' for help. Type '\c' to clear the current input
   statement.
      
   mysql> source db/MySQL/create.sql
   Query OK, 1 row affected (0.02 sec)
   Database changed
   mysql> quit
   Bye
   ```
   
   4.3. Adicionar biblioteca do  ***MySQL JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
   <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>8.0.21</version>
     <scope>runtime</scope>
   </dependency>
   ```
      <div style="page-break-after: always"></div>
4.4 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Transacoes**

      ```properties
   # MySQL & JPA
   spring.datasource.url = jdbc:mysql://localhost:3306/Transacoes
   spring.datasource.username = root
   spring.datasource.password = root
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   # THYMELEAF
   spring.thymeleaf.cache = false
   # SERVER PORT
   server.port = 8081
      ```
   
5. Configurar o projeto --- no arquivo **pom.xml** (incluir novas maven dependências)

      ```xml
      <dependency>
         	<groupId>com.googlecode.json-simple</groupId>
         	<artifactId>json-simple</artifactId>
         	<version>1.1.1</version>
      </dependency>		
      <dependency>
         	<groupId>com.fasterxml.jackson.dataformat</groupId>
         	<artifactId>jackson-dataformat-xml</artifactId>
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


##### (3) CRUD Editora: Cartão
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
   		int result = prime + ((id == null) ? 0 : id.hashCode());
   		return result;
   	}
   
   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj) return true;
   		if (obj == null) return false;
   		if (getClass() != obj.getClass())
   			return false;
   		AbstractEntity<?> other = (AbstractEntity<?>) obj;
   		if (id == null) {
   			if (other.id != null) return false;
   		} else if (!id.equals(other.id)) return false;
   		return true;
   	}
   
   	@Override
   	public String toString() {
   		return "id=" + id;
   	}	
   }
   ```
   
   
   
   
   
   7.2. Classe **br.ufscar.dc.dsw.domain.Cartao**
   
   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.List;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.OneToMany;
   import javax.persistence.Table;
   import javax.validation.constraints.NotBlank;
   
   import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
   
   @SuppressWarnings("serial")
   @JsonIgnoreProperties(value = { "transacoes" })
   @Entity
   @Table(name = "Cartao")
   public class Cartao extends AbstractEntity<Long> {
   
   	@NotBlank
   	@Column(nullable = false, length = 80)
   	private String titular;
   
   	@NotBlank
       @Column(nullable = false, length = 14)
       private String CPF;
   	
   	@NotBlank
   	@Column(nullable = false, unique = true, length = 19)
   	private String numero;
   	
   	@NotBlank
   	@Column(nullable = false, length = 5)
   	private String vencimento;
   	
   	@NotBlank
   	@Column(nullable = false, length = 3)
   	private String CVV;
   	
   	@OneToMany(mappedBy = "cartao")
   	private List<Transacao> transacoes;
   	
   	public String getTitular() {
   		return titular;
   	}
   
   	public void setTitular(String nome) {
   		this.titular = nome;
   	}
   
   	public String getCPF() {
   		return CPF;
   	}
   
   	public void setCPF(String cPF) {
   		CPF = cPF;
   	}
   
   	public String getNumero() {
   		return numero;
   	}
   
   	public void setNumero(String numero) {
   		this.numero = numero;
   	}
   
   	public String getVencimento() {
   		return vencimento;
   	}
   
   	public void setVencimento(String vencimento) {
   		this.vencimento = vencimento;
   	}
   
   	public String getCVV() {
   		return CVV;
   	}
   
   	public void setCVV(String seguranca) {
   		this.CVV = seguranca;
   	}
   
   	
   	public List<Transacao> getTransacoes() {
   		return transacoes;
   	}
   
   	public void setTransacoes(List<Transacao> transacoes) {
   		this.transacoes = transacoes;
   	}
   
   	@Override
   	public String toString() {
   		return numero + "/" + titular;
   	}
   }
   ```
   
   7.3. Classe **br.ufscar.dc.dsw.domain.Transacao**
   
   ```java
   
   package br.ufscar.dc.dsw.domain;
   
   import java.math.BigDecimal;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.EnumType;
   import javax.persistence.Enumerated;
   import javax.persistence.JoinColumn;
   import javax.persistence.ManyToOne;
   import javax.persistence.Table;
   import javax.validation.constraints.NotBlank;
   import javax.validation.constraints.NotNull;
   
   import org.springframework.format.annotation.NumberFormat;
   import org.springframework.format.annotation.NumberFormat.Style;
   
   import br.ufscar.dc.dsw.domain.enumeration.Categoria;
   import br.ufscar.dc.dsw.domain.enumeration.Status;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Transacao")
   public class Transacao extends AbstractEntity<Long> {
   
   	@NotBlank
   	@Column(nullable = false, length = 60)
   	private String descricao;
   	
   	@NotNull
   	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
   	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
   	private BigDecimal valor;
   	
   	@NotBlank
   	@Column(nullable = false, length = 10)
   	private String data;
   
   	@NotNull
   	@Column(nullable = false, length = 9)
   	@Enumerated(EnumType.STRING)
   	private Categoria categoria;
   
   	@NotNull
   	@Column(nullable = false, length = 10)
   	@Enumerated(EnumType.STRING)
   	private Status status;
   	
   	@NotNull
   	@ManyToOne
   	@JoinColumn(name = "cartao_id")
   	private Cartao cartao;
   
   	public String getDescricao() {
   		return descricao;
   	}
   
   	public void setDescricao(String descricao) {
   		this.descricao = descricao;
   	}
   
   	public BigDecimal getValor() {
   		return valor;
   	}
   
   	public void setValor(BigDecimal valor) {
   		this.valor = valor;
   	}
   
   	public String getData() {
   		return data;
   	}
   
   	public void setData(String data) {
   		this.data = data;
   	}
   
   	public Categoria getCategoria() {
   		return categoria;
   	}
   
   	public void setCategoria(Categoria tipo) {
   		this.categoria = tipo;
   	}
   
   	public Status getStatus() {
   		return status;
   	}
   
   	public void setStatus(Status status) {
   		this.status = status;
   	}
   
   	public Cartao getCartao() {
   		return cartao;
   	}
   
   	public void setCartao(Cartao cartao) {
   		this.cartao = cartao;
   	}
   	
   	@Override
   	public String toString() {
   		return "[" + categoria + "/" + status + "] " + descricao + " - " + valor + " - " + data + " (" + cartao + ")";
   	}
   }
   ```
   7.4.  Enumeração **br.ufscar.dc.dsw.domain.enumeration.Categoria**
   
   ```java
   package br.ufscar.dc.dsw.domain.enumeration;
   
   public enum Categoria {
   	COMPRA("COMPRA"), PAGAMENTO("PAGAMENTO");
   	
   	private String value;
   	
   	private Categoria(String value) {
   		this.value = value;
   	}
   
   	public String getValue() {
   		return value;
   	}
   	
   	public static Categoria parse(String value) {
   		if (value.equals(COMPRA.getValue())) {
   			return COMPRA;
   		} else {
   			return PAGAMENTO;
   		}
   	}
   }
   ```
   
   
   
   7.5.  Enumeração **br.ufscar.dc.dsw.domain.enumeration.Status**
   
   ```java
   package br.ufscar.dc.dsw.domain.enumeration;
   
   public enum Status {
   	
   	CONFIRMADA("CONFIRMADA"), CANCELADA("CANCELADA");
   	
   	private String value;
   	
   	private Status(String value) {
   		this.value = value;
   	}
   
   	public String getValue() {
   		return value;
   	}
   	
   	public static Status parse(String value) {
   		if (value.equals(CONFIRMADA.getValue())) {
   			return CONFIRMADA;
   		} else {
   			return CANCELADA;
   		}
   	}
   }
   ```
   
   
   
8. Criar as interfaces DAO (pacote **br.ufscar.dc.dsw.dao**)

   8.1. Interface **br.ufscar.dc.dsw.dao.ICartaoDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import org.springframework.data.jpa.repository.Query;
   import org.springframework.data.repository.CrudRepository;
   import org.springframework.data.repository.query.Param;
   
   import br.ufscar.dc.dsw.domain.Cartao;
   
   @SuppressWarnings("unchecked")
   public interface ICartaoDAO extends CrudRepository<Cartao, Long> {
   	List<Cartao> findAll();
   
   	Cartao findById(long id);
   
   	Cartao save(Cartao editora);
   
   	void deleteById(Long id);
   	
   	@Query("select c from Cartao c where cpf = :cpf")
   	public List<Cartao> findByCPF(@Param("cpf") String cpf);
   }
   ```
   
9. Criar as classes e interfaces de serviço (pacote **br.ufscar.dc.dsw.service**)

   9.1. Interface **br.ufscar.dc.dsw.service.spec.ICartaoService**
   
   ```java
   package br.ufscar.dc.dsw.service.spec;
   
   import java.util.List;
   
   import br.ufscar.dc.dsw.domain.Cartao;
   
   public interface ICartaoService {
   	
   	Cartao buscarPorId(Long id);
   	List<Cartao> buscarTodos();
   	List<Cartao> buscarPorCPF(String cpf);
   	void salvar(Cartao cartao);
   	void excluir(Long id);	
   	boolean cartaoTemTransacoes(Long id);
   }
   ```
   9.2. Classe **br.ufscar.dc.dsw.service.impl.CartaoService**
   
   ```java
   package br.ufscar.dc.dsw.service.impl;
   
   import java.util.List;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;
   
   import br.ufscar.dc.dsw.dao.ICartaoDAO;
   import br.ufscar.dc.dsw.domain.Cartao;
   import br.ufscar.dc.dsw.service.spec.ICartaoService;
   
   @Service
   @Transactional(readOnly = false)
   public class CartaoService implements ICartaoService {
   
   	@Autowired
   	ICartaoDAO dao;
   
   	@Override
   	@Transactional(readOnly = true)
   	public Cartao buscarPorId(Long id) {
   		return dao.findById(id.longValue());
   	}
   
   	@Override
   	@Transactional(readOnly = true)
   	public List<Cartao> buscarTodos() {
   		return dao.findAll();
   	}
   	
   	public List<Cartao> buscarPorCPF(String cpf) {
   		return dao.findByCPF(cpf);
   	}
   	@Override
   	public void salvar(Cartao cartao) {
   		dao.save(cartao);
   	}
   	
   	@Override
   	public void excluir(Long id) {
   		dao.deleteById(id.longValue());
   	}
   	
   	@Transactional(readOnly = true)
   	public boolean cartaoTemTransacoes(Long id) {
   		return !dao.findById(id.longValue()).getTransacoes().isEmpty();
   	}
   }
   ```

##### (4) CRUD Cartão: Controlador
- - -

10. Criar a classe controlador (pacote **br.ufscar.dc.dsw.controller**)

    10.1. Classe **br.ufscar.dc.dsw.controller.CartaoController**
    
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
    
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.service.spec.ICartaoService;
    
    @Controller
    @RequestMapping("/cartoes")
    public class CartaoController {
    
    	@Autowired
    	private ICartaoService service;
    
    	@GetMapping("/cadastrar")
    	public String cadastrar(Cartao cartao) {
    		return "cartao/cadastro";
    	}
    
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    		model.addAttribute("cartoes", service.buscarTodos());
    		return "cartao/lista";
    	}
    
    	@PostMapping("/salvar")
    	public String salvar(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "cartao/cadastro";
    		}
    
    		service.salvar(cartao);
    		attr.addFlashAttribute("sucess", "Cartão inserido com sucesso");
    		return "redirect:/cartoes/listar";
    	}
    
    	@GetMapping("/editar/{id}")
    	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    		model.addAttribute("cartao", service.buscarPorId(id));
    		return "cartao/cadastro";
    	}
    
    	@PostMapping("/editar")
    	public String editar(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "cartao/cadastro";
    		}
    
    		service.salvar(cartao);
    		attr.addFlashAttribute("sucess", "Cartão editado com sucesso.");
    		return "redirect:/cartoes/listar";
    	}
    
    	@GetMapping("/excluir/{id}")
    	public String excluir(@PathVariable("id") Long id, ModelMap model) {
    		if (service.cartaoTemTransacoes(id)) {
    			model.addAttribute("fail", "Cartão não excluído. Possui transação vinculada.");
    		} else {
    			service.excluir(id);
    			model.addAttribute("sucess", "Cartão excluído com sucesso.");	
    		}
    		return listar(model);
    	}
    }
    ```

<div style="page-break-after: always"></div>

##### (5) CRUD Cartão: Visão
- - -

11. Criar as visões (no diretório **src/main/resources/templates/cartao**)

    11.1. Criar o arquivo **cadastro.html** (no diretório **src/main/resources/templates/cartao**)
    
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
    						<span>Cadastrar Cartões</span></li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/cartoes/listar}"
    					role="button"> <span class="oi oi-spreadsheet" title="Listar"
    					aria-hidden="true"></span> <span>Listar Cartões</span>
    				</a>
    			</div>
    		</nav>
    
    		<div class="container" id="cadastro">
    
    			<div th:replace="fragments/alert"></div>
    
    			<form
    				th:action="${cartao.id == null} ? @{/cartoes/salvar} : @{/cartoes/editar}"
    				th:object="${cartao}" method="POST">
    
    				<div class="form-row">
    					<div class="form-group col-md-6">
    						<label for="titular">Titular</label> 
    						<input type="text" class="form-control" id="titular" placeholder="Titular"
    							autofocus="autofocus" th:field="*{titular}"
    							th:classappend="${#fields.hasErrors('titular')} ? is-invalid" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{titular}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="CPF">CPF</label> 
    						<input type="text" class="form-control" id="CPF" placeholder="___.___.___-__"
    							autofocus="autofocus" th:field="*{CPF}"
    							th:classappend="${#fields.hasErrors('CPF')} ? is-invalid"
    							data-mask="000.000.000-00" data-mask-reverse="true" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{CPF}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="numero">Número</label> 
    						<input type="text" class="form-control" id="numero" placeholder="____ ____ ____ ____"
    							autofocus="autofocus" th:field="*{numero}"
    							th:classappend="${#fields.hasErrors('numero')} ? is-invalid"
    							data-mask="0000 0000 0000 0000" data-mask-reverse="true" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{numero}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="vencimento">Vencimento</label> 
    						<input type="text" class="form-control" id="vencimento" placeholder="__/__"
    							autofocus="autofocus" th:field="*{vencimento}"
    							th:classappend="${#fields.hasErrors('vencimento')} ? is-invalid"
    							data-mask="00/00" data-mask-reverse="true" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{vencimento}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="CVV">CVV</label> 
    						<input type="text" class="form-control" id="CVV" placeholder="CVV"
    							autofocus="autofocus" th:field="*{CVV}"
    							th:classappend="${#fields.hasErrors('CVV')} ? is-invalid"
    							data-mask="000" data-mask-reverse="true" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{CVV}"></span>
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
    
    
    
    11.2. Criar o arquivo **lista.html** (no diretório **src/main/resources/templates/cartao**)
    
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
    						<span>Lista de Cartões</span>
    					</li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/cartoes/cadastrar}" role="button"> 
    					<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    					<span>Novo Cartão</span>
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
    							<th>Titular</th>
    							<th>CPF</th>
    							<th>Número</th>
    							<th>Vencimento</th>
    							<th>CVV</th>
    							<th>Ação</th>
    						</tr>
    					</thead>
    					<tbody>
    						<tr th:each="cartao : ${cartoes}">
    							<td th:text="${cartao.id}"></td>
    							<td th:text="${cartao.titular}"></td>
    							<td th:text="${cartao.CPF}"></td>
    							<td th:text="${cartao.numero}"></td>
    							<td th:text="${cartao.vencimento}"></td>
    							<td th:text="${cartao.CVV}"></td>
    							<td colspan="2">
    								<a class="btn btn-info btn-sm" th:href="@{/cartoes/editar/{id} (id=${cartao.id}) }"
    									role="button"> 
    									<span class="oi oi-brush" title="Editar" aria-hidden="true"> </span>
    								</a>
    								<button
    									th:id="${'btn_cartoes/excluir/' + cartao.id}"
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
    11.3 Alterar o arquivo **src/main/resources/fragments/sidebar.html** (para incluir links para o CRUD Cartao)
    
    ```html
    <ul class="nav nav-pills">
    	<li class="nav-item"><span class="nav-link active">Cartões</span></li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/cartoes/cadastrar}"> 
                <i class="oi oi-plus"></i>		
                <span>Cadastrar</span></a>
        </li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/cartoes/listar}"> 
                <i class="oi oi-spreadsheet"></i>
    			<span>Listar</span></a>
        </li>
    </ul>
    ```
    <div style="page-break-after: always"></div>
    11.4. Atualizar a classe **br.ufscar.dc.dsw.TransacoesRSApplication**
    
    ```java
    package br.ufscar.dc.dsw;
    
    import java.math.BigDecimal;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import br.ufscar.dc.dsw.dao.ICartaoDAO;
    import br.ufscar.dc.dsw.dao.ITransacaoDAO;
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.domain.Transacao;
    import br.ufscar.dc.dsw.domain.enumeration.Categoria;
    import br.ufscar.dc.dsw.domain.enumeration.Status;
    
    @SpringBootApplication
    public class TransacoesRSApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(TransacoesRSApplication.class, args);
    	}
    	
    	@Bean
    	public CommandLineRunner demo(ICartaoDAO cartaoDAO, ITransacaoDAO transacaoDAO) {
    		return (args) -> {
    
    			Cartao c1 = new Cartao();
    			c1.setTitular("Beltrano Andrade");
    			c1.setCPF("985.849.614-10");
    			c1.setNumero("5259 5697 2426 9163");
    			c1.setVencimento("02/22");
    			c1.setCVV("147");
    			cartaoDAO.save(c1);
    
    			Cartao c2 = new Cartao();
    			c2.setTitular("Fulano Silva");
    			c2.setCPF("367.318.380-04");
    			c2.setNumero("4929 5828 5594 8623");
    			c2.setVencimento("12/21");
    			c2.setCVV("663");
    			cartaoDAO.save(c2);
    		};
    	}
    }
    ```
    11.5. Executar (**mvn spring-boot:run**) e ver o efeito (o CRUD Cartão está operacional)



<div style="page-break-after: always"></div>
##### (6) CRUD Transação: Modelo


12. Criar as classes e interfaces DAO (pacote **br.ufscar.dc.dsw.dao**)

    12.1. Interface **br.ufscar.dc.dsw.dao.ITransacaoDAO**
    
    ```java
    package br.ufscar.dc.dsw.dao;
    
    import java.util.List;
    import org.springframework.data.repository.CrudRepository;
    import br.ufscar.dc.dsw.domain.Transacao;
    
    @SuppressWarnings("unchecked")
    public interface ITransacaoDAO extends CrudRepository<Transacao, Long> {
    	List<Transacao> findAll();
    	Transacao findById(long id);
    	Transacao save(Transacao transacao);
    	void deleteById(Long id);
    }
    ```
    


13. Criar as classes e interfaces de serviço (pacote **br.ufscar.dc.dsw.service**)

    13.1. Interface **br.ufscar.dc.dsw.service.spec.ITransacaoService**
    
    ```java
    package br.ufscar.dc.dsw.service.spec;
    
    import java.util.List;
    import br.ufscar.dc.dsw.domain.Transacao;
    
    public interface ITransacaoService {
    	Transacao buscarPorId(Long id);
    	List<Transacao> buscarTodos();
    	void salvar(Transacao transacao);
    	void excluir(Long id);
    }
    ```
    
    13.2. Classe **br.ufscar.dc.dsw.service.impl.TransacaoService**
    
    ```java
    package br.ufscar.dc.dsw.service.impl;
    
    import java.util.List;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    import br.ufscar.dc.dsw.dao.ITransacaoDAO;
    import br.ufscar.dc.dsw.domain.Transacao;
    import br.ufscar.dc.dsw.service.spec.ITransacaoService;
    
    @Service
    @Transactional(readOnly = false)
    public class TransacaoService implements ITransacaoService {
    
    	@Autowired
    	ITransacaoDAO dao;
    
    	@Override
    	@Transactional(readOnly = true)
    	public Transacao buscarPorId(Long id) {
    		return dao.findById(id.longValue());
    	}
    
    	@Override
    	@Transactional(readOnly = true)
    	public List<Transacao> buscarTodos() {
    		return dao.findAll();
    	}
    
    	@Override
    	public void salvar(Transacao transacao) {
    		dao.save(transacao);
    	}
    	
    	@Override
    	public void excluir(Long id) {
    		dao.deleteById(id.longValue());
    	}
    }
    ```



##### (7) CRUD Transação: Controlador
- - -

14. Criar a classe controlador (pacote **br.ufscar.dc.dsw.controller**)
    14.1. Classe **br.ufscar.dc.dsw.controller.TransacaoController**
    
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
    
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.domain.Transacao;
    import br.ufscar.dc.dsw.domain.enumeration.Categoria;
    import br.ufscar.dc.dsw.domain.enumeration.Status;
    import br.ufscar.dc.dsw.service.spec.ICartaoService;
    import br.ufscar.dc.dsw.service.spec.ITransacaoService;
    
    @Controller
    @RequestMapping("/transacoes")
    public class TransacaoController {
    
    	@Autowired
    	private ITransacaoService service;
    
    	@Autowired
    	private ICartaoService cartaoService;
    	
    	@GetMapping("/cadastrar")
    	public String cadastrar(Transacao transacao) {
    		return "transacao/cadastro";
    	}
    
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    		model.addAttribute("transacoes", service.buscarTodos());
    		return "transacao/lista";
    	}
    
    	@PostMapping("/salvar")
    	public String salvar(@Valid Transacao transacao, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "transacao/cadastro";
    		}
    
    		service.salvar(transacao);
    		attr.addFlashAttribute("sucess", "Transação inserida com sucesso.");
    		return "redirect:/transacoes/listar";
    	}
    
    	@GetMapping("/editar/{id}")
    	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    		model.addAttribute("transacao", service.buscarPorId(id));
    		return "transacao/cadastro";
    	}
    
    	@PostMapping("/editar")
    	public String editar(@Valid Transacao transacao, BindingResult result, RedirectAttributes attr) {
    
    		if (result.hasErrors()) {
    			return "transacao/cadastro";
    		}
    
    		service.salvar(transacao);
    		attr.addFlashAttribute("sucess", "Transação editada com sucesso.");
    		return "redirect:/transacoes/listar";
    	}
    
    	@GetMapping("/excluir/{id}")
    	public String excluir(@PathVariable("id") Long id, ModelMap model) {
    		service.excluir(id);
    		model.addAttribute("sucess", "Transação excluída com sucesso.");
    		return listar(model);
    	}
    	
    	@ModelAttribute("cartoes")
    	public List<Cartao> listaCartoes() {
    		return cartaoService.buscarTodos();
    	}
    	
    	@ModelAttribute("categorias")
    	public Categoria[] listaCategorias() {
    		return Categoria.values();
    	}
    	
    	@ModelAttribute("statuses")
    	public Status[] listaStatuses() {
    		return Status.values();
    	}	
    }
    ```




##### (8) CRUD Transação: Visão
- - -


15. Criar as visões (no diretório **src/main/resources/templates/transacao**)

    15.1. Criar o arquivo **cadastro.html** (no diretório **src/main/resources/templates/transacao**)

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
    						<span>Cadastrar Transações</span></li>
    				</ul>
    			</div>
    			<a class="btn btn-primary btn-md" th:href="@{/transacoes/listar}"
    				role="button"> <span class="oi oi-spreadsheet" title="Cadastro"
    				aria-hidden="true"></span> <span>Listar Transações</span>
    			</a>
    		</nav>
    
    		<div class="container" id="cadastro">
    
    			<div th:replace="fragments/alert"></div>
    
    			<form
    				th:action="${transacao.id == null} ? @{/transacoes/salvar} : @{/transacoes/editar}"
    				th:object="${transacao}" method="POST">
    
    				<div class="form-row">
    					<div class="form-group col-md-6">
    						<label for="nome">Descrição</label> 
    						<input type="text" class="form-control" id="descricao" placeholder="Descrição"
    							autofocus="autofocus" th:field="*{descricao}"
    							th:classappend="${#fields.hasErrors('descricao')} ? is-invalid" />
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{descricao}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label for="valor">Valor</label> 
    						<input type="text" class="form-control" id="valor" placeholder="Valor"
    							data-mask="000000,00" data-mask-reverse="true" th:field="*{valor}"
    							th:classappend="${#fields.hasErrors('valor')} ? is-invalid" />
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{valor}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label for="data">Data</label> 
    						<input type="text" class="form-control" id="data" placeholder="__/__/____"
    							autofocus="autofocus" th:field="*{data}"
    							th:classappend="${#fields.hasErrors('data')} ? is-invalid"
    							data-mask="00/00/0000" data-reverse="true" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{data}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label for="categoria">Categoria</label> 
    						<select id="categoria" class="form-control" th:field="*{categoria}"
    							th:classappend="${#fields.hasErrors('categoria')} ? is-invalid">
    							<option value="">Selecione</option>
    							<option th:each="categoria : ${categorias}" th:value="${categoria}"
    								th:text="${categoria}"></option>
    						</select>
    
    						<div class="invalid-feedback">
    							<span th:errors="*{categoria}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label for="status">Status</label> 
    						<select id="status" class="form-control" th:field="*{status}"
    							th:classappend="${#fields.hasErrors('status')} ? is-invalid">
    							<option value="">Selecione</option>
    							<option th:each="status : ${statuses}" th:value="${status}"
    								th:text="${status}"></option>
    						</select>
    
    						<div class="invalid-feedback">
    							<span th:errors="*{status}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label for="cartao">Cartão</label> 
    						<select id="cartao" class="form-control" th:field="*{cartao}"
    							th:classappend="${#fields.hasErrors('cartao')} ? is-invalid">
    							<option value="">Selecione</option>
    							<option th:each="cartao : ${cartoes}" th:value="${cartao.id}"
    								th:text="${cartao.numero}"></option>
    						</select>
    
    						<div class="invalid-feedback">
    							<span th:errors="*{cartao}"></span>
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
    
    15.2. Criar a visão **lista.html** (no diretório **src/main/resources/templates/transacao**)
    
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
    						<span>Lista de Transações</span>
    					</li>
    				</ul>
    			</div>
    			<a class="btn btn-primary btn-md" th:href="@{/transacoes/cadastrar}" role="button"> 
    				<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    				<span>Nova Transação</span>
    			</a>
    		</nav>
    
    		<div class="container" id="listagem">
    
    			<div th:replace="fragments/alert"></div>
    
    			<div class="table-responsive">
    				<table class="table table-striped table-hover table-sm">
    					<thead>
    						<tr>
    							<th>#</th>
    							<th>Descrição</th>
    							<th>Valor</th>
    							<th>Data</th>
    							<th>Categoria</th>
    							<th>Status</th>
    							<th>Cartão</th>
    							<th>Ação</th>
    						</tr>
    					</thead>
    					<tbody>						
    						<tr th:each="transacao : ${transacoes}">
    							<td th:text="${transacao.id}"></td>
    							<td th:text="${transacao.descricao}"></td>
    							<td th:text="|R$ ${#numbers.formatDecimal(transacao.valor,2,2,'COMMA')}|"></td>
    							<td th:text="${transacao.data}"></td>
    							<td th:text="${transacao.categoria}"></td>
    							<td th:text="${transacao.status}"></td>
    							<td th:text="${transacao.cartao.numero}"></td>
    							<td colspan="2">
    								<a class="btn btn-info btn-sm"
    									th:href="@{/transacoes/editar/{id} (id=${transacao.id}) }" role="button"> 
    									<span class="oi oi-brush" title="Editar" aria-hidden="true"></span>
    								</a>
    								<button th:id="${#strings.concat('btn_transacoes/excluir/',transacao.id)}" 
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
    
    15.3. Alterar o arquivo **src/main/resources/fragments/sidebar.html** (para incluir links para o CRUD Livro)
    
    ```html
    <ul class="nav nav-pills">
    	<li class="nav-item"><span class="nav-link active">Transações</span></li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/transacoes/cadastrar}"> 
                <i class="oi oi-plus"></i> 
                <span>Cadastrar</span>
    			</a>
        </li>
    	<li class="nav-item">
            <a class="nav-link" th:href="@{/transacoes/listar}"> 
                <i class="oi oi-spreadsheet"></i>
    			<span>Listar</span>
    		</a>
        </li>
    </ul>
    ```
    
    
    15.4. Atualizar a classe **br.ufscar.dc.dsw.TransacoesRSApplication**
    
    ```java
    package br.ufscar.dc.dsw;
    
    import java.math.BigDecimal;
    
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    
    import br.ufscar.dc.dsw.dao.ICartaoDAO;
    import br.ufscar.dc.dsw.dao.ITransacaoDAO;
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.domain.Transacao;
    import br.ufscar.dc.dsw.domain.enumeration.Categoria;
    import br.ufscar.dc.dsw.domain.enumeration.Status;
    
    @SpringBootApplication
    public class TransacoesRSApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(TransacoesRSApplication.class, args);
    	}
    	
    	@Bean
    	public CommandLineRunner demo(ICartaoDAO cartaoDAO, ITransacaoDAO transacaoDAO) {
    		return (args) -> {
    
    			Cartao c1 = new Cartao();
    			c1.setTitular("Beltrano Andrade");
    			c1.setCPF("985.849.614-10");
    			c1.setNumero("5259 5697 2426 9163");
    			c1.setVencimento("02/22");
    			c1.setCVV("147");
    			cartaoDAO.save(c1);
    
    			Cartao c2 = new Cartao();
    			c2.setTitular("Fulano Silva");
    			c2.setCPF("367.318.380-04");
    			c2.setNumero("4929 5828 5594 8623");
    			c2.setVencimento("12/21");
    			c2.setCVV("663");
    			cartaoDAO.save(c2);
    			
    			Transacao t1 = new Transacao();
    			t1.setDescricao("Compra Mercado Preso");
    			t1.setValor(BigDecimal.valueOf(100.87));
    			t1.setData("10/08/2020");
    			t1.setCategoria(Categoria.COMPRA);
    			t1.setStatus(Status.CONFIRMADA);
    			t1.setCartao(c1);
    			transacaoDAO.save(t1);
    			
    			Transacao t2 = new Transacao();
    			t2.setDescricao("Pagamento via Boleto");
    			t2.setValor(BigDecimal.valueOf(100.87));
    			t2.setData("01/09/2020");
    			t2.setCategoria(Categoria.PAGAMENTO);
    			t2.setStatus(Status.CONFIRMADA);
    			t2.setCartao(c1);
    			transacaoDAO.save(t2);
    		};
    	}
    }
    ```
    
    15.5. Executar (**mvn spring-boot:run**) e ver o efeito (o CRUD Transação está operacional)


<div style="page-break-after: always"></div>
##### (9) Página de erros

- - -

16. Adicionar o controlador para página de erros

    16.1 Classe **br.ufscar.dc.dsw.controller.ErrorViewController**
    
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



17. Adicionar a página de erros (arquivo **src/main/resources/template/error.html**)

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

18. Executar (**mvn spring-boot:run**) e ver o efeito quando tenta acessar uma página não existente (por exemplo: http://localhost:8080/book)




##### (10) Controladores REST API
- - -

19. Criar as classes  controladores (pacote **br.ufscar.dc.dsw.controller**)

    19.1. Classe **br.ufscar.dc.dsw.controller.CartaoRestController**

    ```java
    package br.ufscar.dc.dsw.controller;
    
    import java.io.IOException;
    import java.util.List;
    
    import org.json.simple.JSONObject;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;
    
    import com.fasterxml.jackson.databind.ObjectMapper;
    
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.service.spec.ICartaoService;
    
    @RestController
    public class CartaoRestController {
    
    	@Autowired
    	private ICartaoService service;
    
    	private boolean isJSONValid(String jsonInString) {
    		try {
    			return new ObjectMapper().readTree(jsonInString) != null;
    		} catch (IOException e) {
    			return false;
    		}
    	}
    	
    	private void parse(Cartao cartao, JSONObject json) {
    
    		Object id = json.get("id");
    		if (id != null) {
    			if (id instanceof Integer) {
    				cartao.setId(((Integer) id).longValue());
    			} else {
    				cartao.setId(((Long) id));
    			}
    		}
    		
    		cartao.setTitular((String) json.get("titular"));
    		cartao.setCPF((String) json.get("cpf"));
    		cartao.setNumero((String) json.get("numero"));
    		cartao.setVencimento((String) json.get("vencimento"));
    		cartao.setCVV((String) json.get("cvv"));		
    	}
    
    	@GetMapping(path = "/cartoes")
    	public ResponseEntity<List<Cartao>> lista() {
    		List<Cartao> lista = service.buscarTodos();
    		if (lista.isEmpty()) {
    			return ResponseEntity.notFound().build();
    		}
    		return ResponseEntity.ok(lista);
    	}
    
    	@GetMapping(path = "/cartoes/cpf/{cpf}")
    	public ResponseEntity<List<Cartao>> listaPorCPF(@PathVariable("cpf") String cpf) {
    		List<Cartao> lista = service.buscarPorCPF(cpf);
    		if (lista.isEmpty()) {
    			return ResponseEntity.notFound().build();
    		}
    		return ResponseEntity.ok(lista);
    	}
    	
    	@GetMapping(path = "/cartoes/{id}")
    	public ResponseEntity<Cartao> lista(@PathVariable("id") long id) {
    		Cartao cartao = service.buscarPorId(id);
    		if (cartao == null) {
    			return ResponseEntity.notFound().build();
    		}
    		return ResponseEntity.ok(cartao);
    	}
    
    	@PostMapping(path = "/cartoes")
    	@ResponseBody
    	public ResponseEntity<Cartao> cria(@RequestBody JSONObject json) {
    		try {
    			if (isJSONValid(json.toString())) {
    				Cartao cartao = new Cartao(); 
    				parse(cartao, json);
    				service.salvar(cartao);
    				return ResponseEntity.ok(cartao);
    			} else {
    				return ResponseEntity.badRequest().body(null);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    		}
    	}
    
    	@PutMapping(path = "/cartoes/{id}")
    	public ResponseEntity<Cartao> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
    		try {
    			if (isJSONValid(json.toString())) {
    				Cartao cartao = service.buscarPorId(id);
    				if (cartao == null) {
    					return ResponseEntity.notFound().build();
    				} else {
    					parse(cartao, json);
    					service.salvar(cartao);
    					return ResponseEntity.ok(cartao);
    				}
    			} else {
    				return ResponseEntity.badRequest().body(null);
    			}
    		} catch (Exception e) {
    			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    		}
    	}
    
    	@DeleteMapping(path = "/cartoes/{id}")
    	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
    
    		Cartao cartao = service.buscarPorId(id);
    		if (cartao == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			service.excluir(id);
    			return ResponseEntity.noContent().build();
		}
    	}
}
    ```

    
    
    19.2. Classe **br.ufscar.dc.dsw.controller.TransacaoRestController**
    
    ```java
    package br.ufscar.dc.dsw.controller;
    
    import java.io.IOException;
    import java.math.BigDecimal;
    import java.util.List;
    import java.util.Map;
    
    import org.json.simple.JSONObject;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;
    
    import com.fasterxml.jackson.databind.ObjectMapper;
    
    import br.ufscar.dc.dsw.domain.Cartao;
    import br.ufscar.dc.dsw.domain.Transacao;
    import br.ufscar.dc.dsw.domain.enumeration.Categoria;
    import br.ufscar.dc.dsw.domain.enumeration.Status;
    import br.ufscar.dc.dsw.service.spec.ITransacaoService;
    
    @RestController
    public class TransacaoRestController {
    
    	@Autowired
    	private ITransacaoService service;
    
    	private boolean isJSONValid(String jsonInString) {
    		try {
    			return new ObjectMapper().readTree(jsonInString) != null;
    		} catch (IOException e) {
    			return false;
    		}
    	}
    
    	@SuppressWarnings("unchecked")
    	private void parse(Cartao cartao, JSONObject json) {
    
    		Map<String, Object> map = (Map<String, Object>) json.get("cartao");
    
    		Object id = map.get("id");
    		if (id instanceof Integer) {
    			cartao.setId(((Integer) id).longValue());
    		} else {
    			cartao.setId(((Long) id));
    		}
    
    		cartao.setTitular((String) map.get("titular"));
    		cartao.setCPF((String) map.get("cpf"));
    		cartao.setNumero((String) map.get("numero"));
    		cartao.setVencimento((String) map.get("vencimento"));
    		cartao.setCVV((String) map.get("cvv"));
    	}
    
    	private void parse(Transacao transacao, JSONObject json) {
    
    		Object id = json.get("id");
    		if (id != null) {
    			if (id instanceof Integer) {
    				transacao.setId(((Integer) id).longValue());
    			} else {
    				transacao.setId((Long) id);
    			}
    		}
    
    		transacao.setDescricao((String) json.get("descricao"));
    		Double valor = (Double) json.get("valor");
    		transacao.setValor(BigDecimal.valueOf(valor));
    		transacao.setData((String) json.get("data"));
    		transacao.setCategoria(Categoria.parse((String) json.get("categoria")));
    		transacao.setStatus(Status.parse((String) json.get("status")));
    
    		Cartao cartao = new Cartao();
    		parse(cartao, json);
    		transacao.setCartao(cartao);
    	}
    
    	@GetMapping(path = "/transacoes")
    	public ResponseEntity<List<Transacao>> lista() {
    		List<Transacao> lista = service.buscarTodos();
    		if (lista.isEmpty()) {
    			return ResponseEntity.notFound().build();
    		}
    		return ResponseEntity.ok(lista);
    	}
    
    	@GetMapping(path = "/transacoes/{id}")
    	public ResponseEntity<Transacao> lista(@PathVariable("id") long id) {
    		Transacao transacao = service.buscarPorId(id);
    		if (transacao == null) {
    			return ResponseEntity.notFound().build();
    		}
    		return ResponseEntity.ok(transacao);
    	}
    
    	@PostMapping(path = "/transacoes")
    	@ResponseBody
    	public ResponseEntity<Transacao> cria(@RequestBody JSONObject json) {
    		try {
    			if (isJSONValid(json.toString())) {
    				Transacao transacao = new Transacao();
    				parse(transacao, json);
    				service.salvar(transacao);
    				return ResponseEntity.ok(transacao);
    			} else {
    				return ResponseEntity.badRequest().body(null);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    		}
    	}
    
    	@PutMapping(path = "/transacoes/{id}")
    	public ResponseEntity<Transacao> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
    		try {
    			if (isJSONValid(json.toString())) {
    				Transacao transacao = service.buscarPorId(id);
    				if (transacao == null) {
    					return ResponseEntity.notFound().build();
    				} else {
    					parse(transacao, json);
    					service.salvar(transacao);
    					return ResponseEntity.ok(transacao);
    				}
    			} else {
    				return ResponseEntity.badRequest().body(null);
    			}
    		} catch (Exception e) {
    			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    		}
    	}
    
    	@DeleteMapping(path = "/transacoes/{id}")
    	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
    
    		Transacao transacao = service.buscarPorId(id);
    		if (transacao == null) {
    			return ResponseEntity.notFound().build();
    		} else {
    			service.excluir(id);
    			return ResponseEntity.noContent().build();
    		}
    	}
    }
    ```

##### (11) Execução e testes

20. Compilar e executar (**mvn spring-boot:run**)

    Verificar que as operações REST estão funcionais

21. Fim

<div style="page-break-after: always"></div>

#### Leituras adicionais

- - -

- Persisting Enums in JPA

  https://www.baeldung.com/jpa-persisting-enums-in-jpa

  

- Building REST services with Spring

  https://spring.io/guides/tutorials/rest/

  

- Building a RESTful Web Service
  
  https://spring.io/guides/gs/rest-service/






<div style="page-break-after: always"></div>

#### Roteiro de Testes - REST API
- - -

##### CRUD: Transações

- - -



- Cria uma nova transação [**C**reate - **CRUD**]

  POST http://localhost:8081/transacoes

  Body: raw/JSON (application/json)
  ```json
  { "descricao": "Compra Online", "valor": 98.76, "data": "08/12/2019", 
    "categoria": "COMPRA", "status": "CONFIRMADA",
    "cartao": { "id": 1, "titular": "Beltrano Andrade", 
                "numero": "5259 5697 2426 9163", "vencimento": "02/22", 
                "cpf": "985.849.614-10", "cvv": "147" } }
  ```



* Retorna a lista de transações [**R**ead - **CRUD**]

  GET http://localhost:8081/transacoes



* Retorna a transação de id = 1 [**R**ead - **CRUD**]

  GET http://localhost:8081/transacoes/1
  
  

* Atualiza a transação (apenas descrição) de id = 3 [**U**pdate - **CRUD**]

  PUT http://localhost:8081/transacoes/3
  
  Body: raw/JSON (application/json) 
  
  ```json
  { "descricao": "Compra Online Atualizada", "valor": 98.76, 
    "data": "08/12/2019", "categoria": "COMPRA", "status": "CONFIRMADA",
    "cartao": { "id": 1, "titular": "Beltrano Andrade", 
                "numero": "5259 5697 2426 9163", "vencimento": "02/22", 
                "cpf": "985.849.614-10", "cvv": "147" } }
  ```

<div style="page-break-after: always"></div>


* Atualiza a transação (descrição e cartão) de id = 3 [**U**pdate - **CRUD**]

  PUT http://localhost:8081/transacoes/3
  
  Body: raw/JSON (application/json)
  
  ```json
  { "descricao": "Compra Online Atualizada Novamente", "valor": 98.76, 
    "data": "08/12/2019", "categoria": "COMPRA", "status": "CONFIRMADA",
    "cartao": { "id": 2, "titular": "Fulano Silva", 
                "numero": "4929 5828 5594 8623", "vencimento": "12/21", 
                "cpf": "367.318.380-04", "cvv": "663" } }
  ```



* Remove a transação de id = 3 [**D**elete - **CRUD**]

  DELETE http://localhost:8081/transacoes/3



- - -

##### CRUD: Cartões

- - -


- Cria um novo cartão [**C**reate - **CRUD**]

  POST http://localhost:8081/cartoes

  ```json
  { "titular": "Sincrano Alves", "numero": "5183 3373 8522 6547", 
    "vencimento": "11/21", "cpf": "214.720.460-99", "cvv": "853" }
  ```



* Retorna a lista de cartões [**R**ead - **CRUD**]

  GET http://localhost:8081/cartoes

  

* Retorna o cartão de id = 1 [**R**ead - **CRUD**]

  GET http://localhost:8081/cartoes/1

  

* Atualiza o cartão (número, vencimento, cvv) de id = 3 [**U**pdate - **CRUD**]

  PUT http://localhost:8081/cartoes/3

  ```json
  { "titular": "Sincrano Alves", "numero": "4916 1338 6252 1766", 
    "vencimento": "04/21", "cpf": "214.720.460-99", "cvv": "209" }
  ```

  

* Remove o cartão de id = 3 [**D**elete - **CRUD**]

  DELETE http://localhost:8081/cartoes/3
