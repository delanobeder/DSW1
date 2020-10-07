## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA** 

- - -

#### 05 - Livraria Virtual com Autenticação/Autorização

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo08/LivrariaMVC-v3)

- - -



##### (1) Configuração

- - -

1. Fazer uma cópia do diretório da aplicação **LivrariaMVC** (Roteiro 08-02).

   ```sh
   % cp -r LivrariaMVC-v2 LivrariaMVC-v3
   ```

   

2. Abrir a aplicação cópia

3. Adicionar a dependência para o **Spring Security** (arquivo **pom.xml**)

   ```xml
   <dependency>
   	<groupId>org.thymeleaf.extras</groupId>
   	<artifactId>thymeleaf-extras-springsecurity5</artifactId>
   </dependency>
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
   	<groupId>org.springframework.security</groupId>
   	<artifactId>spring-security-test</artifactId>
   	<scope>test</scope>
   </dependency
   ```

   

4. Atualizar a classe **br.ufscar.dc.dsw.config.MVCConfig**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import java.util.Locale;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.ComponentScan;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.format.FormatterRegistry;
   import org.springframework.web.servlet.LocaleResolver;
   import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
   import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
   import org.springframework.web.servlet.i18n.SessionLocaleResolver;
   
   import br.ufscar.dc.dsw.conversor.BigDecimalConversor;
   
   @Configuration
   @ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
   public class MvcConfig implements WebMvcConfigurer {
   
   	public void addViewControllers(ViewControllerRegistry registry) {
   		registry.addViewController("/").setViewName("redirect:/home");
   		registry.addViewController("/home").setViewName("home");
   		registry.addViewController("/login").setViewName("login");
   	}
   
   	@Bean
   	public LocaleResolver localeResolver() {
   		SessionLocaleResolver slr = new SessionLocaleResolver();
   		slr.setDefaultLocale(new Locale("pt", "BR"));
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

   

5. Adicionar a classe **br.ufscar.dc.dsw.config.WebSecurityConfig**

   ```java
   package br.ufscar.dc.dsw.config;
   
   import org.springframework.context.annotation.*;
   import org.springframework.security.authentication.dao.*;
   import org.springframework.security.config.annotation.authentication.builders.*;
   import org.springframework.security.config.annotation.web.builders.*;
   import org.springframework.security.config.annotation.web.configuration.*;
   import org.springframework.security.core.userdetails.*;
   import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
   
   import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;
   
   @Configuration
   @EnableWebSecurity
   public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   	@Bean
   	public UserDetailsService userDetailsService() {
   		return new UsuarioDetailsServiceImpl();
   	}
   
   	@Bean
   	public BCryptPasswordEncoder passwordEncoder() {
   		return new BCryptPasswordEncoder();
   	}
   
   	@Bean
   	public DaoAuthenticationProvider authenticationProvider() {
   		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
   		authProvider.setUserDetailsService(userDetailsService());
   		authProvider.setPasswordEncoder(passwordEncoder());
   
   		return authProvider;
   	}
   
   	@Override
   	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   		auth.authenticationProvider(authenticationProvider());
   	}
   
   	@Override
   	protected void configure(HttpSecurity http) throws Exception {
   				http.authorizeRequests()
   				.antMatchers("/error", "/login/**", "/js/**").permitAll()
                   .antMatchers("/css/**", "/image/**", "/webjars/**").permitAll()
   				.antMatchers("/compras/**").hasRole("USER")
   				.antMatchers("/editoras/**", "/livros/**").hasRole("ADMIN")
                   .antMatchers("/usuarios/**").hasRole("ADMIN")
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
   }
   ```

   

6. Remover a classe **br.ufscar.dc.dsw.controller.HomeController**

##### (2) CRUD Usuário: Modelo, Controlador, Visão e I18n
- - -

7. Adicionar a classe **br.ufscar.dc.dsw.domain.Usuario** (entidade JPA)

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.Table;
   import javax.validation.constraints.NotBlank;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Usuario")
   public class Usuario extends AbstractEntity<Long> {
     
   	@NotBlank
       @Column(nullable = false, length = 20, unique = true)
       private String username;
       
   	@NotBlank
       @Column(nullable = false, length = 64)
       private String password;
          
       @NotBlank
       @Column(nullable = false, length = 60)
       private String name;
       
       @NotBlank
       @Column(nullable = false, length = 14)
       private String CPF;
       
       @NotBlank
       @Column(nullable = false, length = 10)
       private String role;
       
       @Column(nullable = false)
       private boolean enabled;
   		
   	public String getUsername() {
   		return username;
   	}
   	
   	public void setUsername(String username) {
   		this.username = username;
   	}
   	
   	public String getPassword() {
   		return password;
   	}
   	
   	public void setPassword(String password) {
   		this.password = password;
   	}
   	
   	public String getName() {
   		return name;
   	}
   	
   	public void setName(String name) {
   		this.name = name;
   	}
   	
   	
   	public String getCPF() {
   		return CPF;
   	}
   
   	public void setCPF(String cPF) {
   		CPF = cPF;
   	}
   
   	public String getRole() {
   		return role;
   	}
   	
   	public void setRole(String role) {
   		this.role = role;
   	}
   	
   	public boolean isEnabled() {
   		return enabled;
   	}
   	
   	public void setEnabled(boolean enabled) {
   		this.enabled = enabled;
   	}
   }
   ```

   

8. Adicionar a classe **br.ufscar.dc.dsw.dao.IUsuarioDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import org.springframework.data.jpa.repository.Query;
   import org.springframework.data.repository.CrudRepository;
   import org.springframework.data.repository.query.Param;
   
   import br.ufscar.dc.dsw.domain.Usuario;
   
   @SuppressWarnings("unchecked")
   public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
   	
   	Usuario findById(long id);
   
   	List<Usuario> findAll();
   	
   	Usuario save(Usuario usuario);
   
   	void deleteById(Long id);
   	
       @Query("SELECT u FROM Usuario u WHERE u.username = :username")
       public Usuario getUserByUsername(@Param("username") String username);
   }
   ```

   

9. Adicionar a interface **br.ufscar.dc.dsw.service.spec.IUsuarioService**

   ```java
   package br.ufscar.dc.dsw.service.spec;
   
   import java.util.List;
   
   import br.ufscar.dc.dsw.domain.Usuario;
   
   public interface IUsuarioService {
   
   	Usuario buscarPorId(Long id);
   
   	List<Usuario> buscarTodos();
   
   	void salvar(Usuario editora);
   
   	void excluir(Long id);	
   }
   ```

   

10. Adicionar a classe **br.ufscar.dc.dsw.service.impl.UsuarioService**

    ```java
    package br.ufscar.dc.dsw.service.impl;
    
    import java.util.List;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    import br.ufscar.dc.dsw.dao.IUsuarioDAO;
    import br.ufscar.dc.dsw.domain.Usuario;
    import br.ufscar.dc.dsw.service.spec.IUsuarioService;
    
    @Service
    @Transactional(readOnly = false)
    public class UsuarioService implements IUsuarioService {
    
    	@Autowired
    	IUsuarioDAO dao;
    
    	public void salvar(Usuario usuario) {
    		dao.save(usuario);
    	}
    
    	public void excluir(Long id) {
    		dao.deleteById(id);
    	}
    
    	@Transactional(readOnly = true)
    	public Usuario buscarPorId(Long id) {
    		return dao.findById(id.longValue());
    	}
    
    	@Transactional(readOnly = true)
    	public List<Usuario> buscarTodos() {
    		return dao.findAll();
    	}
    }
    ```

    

11. Adicionar a classe **br.ufscar.dc.dsw.controller.UsuarioController**

    ```java
    package br.ufscar.dc.dsw.controller;
    
    import javax.validation.Valid;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.ModelMap;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    
    import br.ufscar.dc.dsw.domain.Usuario;
    import br.ufscar.dc.dsw.service.spec.IUsuarioService;
    
    @Controller
    @RequestMapping("/usuarios")
    public class UsuarioController {
    	
    	@Autowired
    	private IUsuarioService service;
    	
    	@Autowired
    	private BCryptPasswordEncoder encoder;
    	
    	@GetMapping("/cadastrar")
    	public String cadastrar(Usuario usuario) {
    		return "usuario/cadastro";
    	}
    	
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    		model.addAttribute("usuarios",service.buscarTodos());
    		return "usuario/lista";
    	}
    	
    	@PostMapping("/salvar")
    	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
    		
    		if (result.hasErrors()) {
    			return "usuario/cadastro";
    		}
    
    		System.out.println("password = " + usuario.getPassword());
    		
    		usuario.setPassword(encoder.encode(usuario.getPassword()));
    		service.salvar(usuario);
    		attr.addFlashAttribute("sucess", "Usuário inserido com sucesso.");
    		return "redirect:/usuarios/listar";
    	}
    	
    	@GetMapping("/editar/{id}")
    	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    		model.addAttribute("usuario", service.buscarPorId(id));
    		return "usuario/cadastro";
    	}
    	
    	@PostMapping("/editar")
    	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
    		
    		if (result.hasErrors()) {
    			return "usuario/cadastro";
    		}
    
    		System.out.println(usuario.getPassword());
    		
    		service.salvar(usuario);
    		attr.addFlashAttribute("sucess", "Usuário editado com sucesso.");
    		return "redirect:/usuarios/listar";
    	}
    	
    	@GetMapping("/excluir/{id}")
    	public String excluir(@PathVariable("id") Long id, ModelMap model) {
    		service.excluir(id);
    		model.addAttribute("sucess", "Usuário excluído com sucesso.");
    		return listar(model);
    	}
    }
    ```
    
    <div style="page-break-after: always"></div>
    
12. Adicionar os arquivos relacionados à visão da entidade Usuário

    12.1 Arquivo **src/main/resources/templates/usuario/cadastro.html**

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
    						<span th:text="#{usuario.cadastrar.label}"></span></li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/usuarios/listar}"
    					role="button"> <span class="oi oi-spreadsheet" title="Listar"
    					aria-hidden="true"></span> <span th:text="#{usuario.listar.label}"></span>
    				</a>
    			</div>
    		</nav>
    
    		<div class="container" id="cadastro">
    
    			<div th:replace="fragments/alert"></div>
    
    			<form
    				th:action="${usuario.id == null} ? @{/usuarios/salvar} : @{/usuarios/editar}"
    				th:object="${usuario}" method="POST">
    
    				<div class="form-row">
    					<div class="form-group col-md-6">
    						<label th:text="#{usuario.username.label}" for="username"></label> 
    						<input type="text" class="form-control" id="username"
    							th:placeholder="#{usuario.username.label}"
    							autofocus="autofocus" th:field="*{username}"
    							th:classappend="${#fields.hasErrors('username')} ? is-invalid" />
    
    						<div class="invalid-feedback">
    							<span th:errors="*{username}"></span>
    						</div>
    					</div>
    
    					<div class="form-group col-md-6">
    						<label th:text="#{usuario.password.label}" for="password"></label> 
    						<input type="text" class="form-control" id="password"
    							th:placeholder="#{usuario.password.label}" autofocus="autofocus"
    							th:field="*{password}"
    							th:classappend="${#fields.hasErrors('password')} ? is-invalid" th:readonly="${usuario.id != null}"/>
    							
    						<div class="invalid-feedback">
    							<span th:errors="*{password}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label th:text="#{usuario.name.label}" for="name"></label> 
    						<input type="text" class="form-control" id="name" th:field="*{name}"
    							th:placeholder="#{usuario.name.label}" autofocus="autofocus"
    							th:classappend="${#fields.hasErrors('name')} ? is-invalid" />
    							
    						<div class="invalid-feedback">
    							<span th:errors="*{name}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label th:text="#{usuario.CPF.label}" for="CPF"></label> 
    						<input type="text" class="form-control" id="CPF" th:field="*{CPF}"
    							autofocus="autofocus" placeholder="___.___.___-__"
    							th:classappend="${#fields.hasErrors('CPF')} ? is-invalid" 
    							data-mask="000.000.000-00" data-mask-reverse="true" />
    							
    						<div class="invalid-feedback">
    							<span th:errors="*{CPF}"></span>
    						</div>
    					</div>
    					
    					<div class="form-group col-md-6">
    						<label th:text="#{usuario.role.label}" for="role"></label> 
    						<select id="role" class="form-control" th:field="*{role}"
    							th:classappend="${#fields.hasErrors('role')} ? is-invalid">
    							<option value="ROLE_ADMIN">ROLE_ADMIN</option>
    							<option value="ROLE_USER">ROLE_USER</option>
    						</select>
    
    						<div class="invalid-feedback">
    							<span th:errors="*{role}"></span>
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

    

    12.2 Arquivo **src/main/resources/templates/usuario/lista.html**

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
    						<span th:text="#{usuario.listar.label}"></span>
    					</li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/usuarios/cadastrar}" role="button"> 
    					<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    					<span th:text="#{usuario.cadastrar.label}"></span>
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
    							<th th:text="#{usuario.username.label}"></th>
    							<th th:text="#{usuario.name.label}"></th>
    							<th th:text="#{usuario.CPF.label}"></th>
    							<th th:text="#{usuario.role.label}"></th>
    							<th th:text="#{acao.label}"></th>
    						</tr>
    					</thead>
    					<tbody>
    						<tr th:each="usuario : ${usuarios}">
    							<td th:text="${usuario.id}"></td>
    							<td th:text="${usuario.username}"></td>
    							<td th:text="${usuario.name}"></td>
    							<td th:text="${usuario.CPF}"></td>
    							<td th:text="${usuario.role}"></td>
    							<td colspan="2">
    								<a class="btn btn-info btn-sm" th:href="@{/usuarios/editar/{id} (id=${usuario.id}) }"
    									role="button"> 
    									<span class="oi oi-brush" th:title="#{link.editar.label}" aria-hidden="true"> </span>
    								</a>
    								<button
    									th:id="${'btn_usuarios/excluir/' + usuario.id}"
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

    

13. Atualizar os arquivos relacionados à internacionalização da aplicação

    13.1 Arquivo **src/main/resources/ValidationMessages.properties**

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
    
    # Validação campos Compra
    
    NotNull.compra.livro = Selecione um livro.
    ```

    

    13.2 Arquivo **src/main/resources/ValidationMessages_en.properties**

    ```properties
    # Mensagens genéricas
    
    javax.validation.constraints.Size.message = Must be between {min} and {max} characters.
    javax.validation.constraints.NotBlank.message = It is mandatory.
    javax.validation.constraints.NotNull.message = It is mandatory.
    javax.validation.constraints.Digits.message = Must contain a maximum of {integer} digits.
    
    # Validação campos Livro
    
    NotBlank.livro.titulo = The title of the book is mandatory.
    NotBlank.livro.autor = The author of the book is mandatory.
    NotNull.livro.ano = The year of the book is mandatory.
    NotNull.livro.preco = The price of the book is mandatory.
    NotNull.livro.editora = Select a publisher.
    
    # Validação campos Editora
    
    Size.editora.CNPJ = The CNPJ of the publisher must have {max} characters.
    
    # Validação campos Compra
    
    NotNull.compra.livro = Select a book.
    ```

    

    13.3 Arquivo **src/main/resources/messages.properties**

    ```properties
    # Titulos do Header
    
    title.label = Livraria Virtual
    
    # Titulos do Sidebar
    
    sidebar.link.home = Home
    sidebar.link.cadastrar = Cadastrar
    sidebar.link.listar = Listar
    sidebar.titulo.editora = Editoras
    sidebar.titulo.livro = Livros
    sidebar.titulo.usuario = Usuários
    sidebar.titulo.compra = Compras
    
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
    
    # Titulos Usuario
    
    usuario.cadastrar.label = Cadastrar Usuário
    usuario.listar.label = Listar Usuários
    usuario.username.label = Login
    usuario.password.label = Senha
    usuario.name.label = Nome
    usuario.CPF.label = CPF
    usuario.role.label = Papel
    
    # Titulos Compra
    
    compra.cadastrar.label = Cadastrar Compra
    compra.listar.label = Listar Compras
    compra.data.label = Data
    compra.valor.label = Valor
    compra.livro.label = Livro
    
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
    
    # Mensagens Login
    
    login.message  = Página de Login
    login.username = Login
    login.password = Senha
    login.button   = Entrar
    login.error    = Nome de usuário ou senha inválida.
    
    user.role = Papel
    ```
    
    
    
    13.4 Arquivo **src/main/resources/messages_en.properties**
    
```properties
    # Titulos do Header

    title.label = Virtual Book Store

    # Titulos do Sidebar
    
    sidebar.link.home = Home
    sidebar.link.cadastrar = Register
    sidebar.link.listar = List
    sidebar.titulo.editora = Publishers
    sidebar.titulo.livro = Books
    sidebar.titulo.usuario = Users
    sidebar.titulo.compra = Purchases
    
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
    
    # Titulos Usuario
    
    usuario.cadastrar.label = Register User
    usuario.listar.label = Users List
    usuario.username.label = Login
    usuario.password.label = Password
    usuario.CPF.label = SSN
    usuario.name.label = Name
    usuario.role.label = Role
    
    # Titulos Compra
    
    compra.cadastrar.label = Register Purchase
    compra.listar.label = Purchases List
    compra.data.label = Date
    compra.valor.label = Value
    compra.livro.label = Book
    
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
    
    # Mensagens Login
    
    login.message  = Login Page
    login.username = Login
    login.password = Password
    login.button   = LogIn
    login.error    = Invalid username or password.
    
    user.role = Role
```

​    

<div style="page-break-after: always"></div>

##### (3) Autenticação/Autorização
- - -
14. Adicionar a classe **br.ufscar.dc.dsw.security.UsuarioDetails**

    ```java
    package br.ufscar.dc.dsw.security;
    
    import java.util.Arrays;
    import java.util.Collection;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import br.ufscar.dc.dsw.domain.Usuario;
     
    @SuppressWarnings("serial")
    public class UsuarioDetails implements UserDetails {
     
        private Usuario user;
         
        public UsuarioDetails(Usuario user) {
            this.user = user;
        }
     
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
            return Arrays.asList(authority);
        }
     
        @Override
        public String getPassword() {
            return user.getPassword();
        }
     
        @Override
        public String getUsername() {
            return user.getUsername();
        }
     
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
     
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
     
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
     
        @Override
        public boolean isEnabled() {
            return true;
        }
    
    	public Usuario getUser() {
    		return user;
    	}   
    }
    ```

    

15. Adicionar a classe **br.ufscar.dc.dsw.security.UserDetailsServiceImpl**

    ```java
    package br.ufscar.dc.dsw.security;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import br.ufscar.dc.dsw.dao.IUsuarioDAO;
    import br.ufscar.dc.dsw.domain.Usuario;
     
    public class UsuarioDetailsServiceImpl implements UserDetailsService {
     
        @Autowired
        private IUsuarioDAO dao;
         
        @Override
        public UserDetails loadUserByUsername(String username)
                throws UsernameNotFoundException {
            Usuario user = dao.getUserByUsername(username);
             
            if (user == null) {
                throw new UsernameNotFoundException("Could not find user");
            }
             
            return new UsuarioDetails(user);
        }
    }
    ```

    

16. Atualizar ou adicionar os arquivos relacionados ao *leiaute* da aplicação

    16.1 Arquivo **src/main/resources/templates/fragments/footer.html**

    ```html
    <!DOCTYPE HTML>
    <html xmlns:th="http://www.thymeleaf.org"
    	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity">
    <head>
    <meta charset="UTF-8">
    </head>
    <body>
    	<footer class="layout-footer" th:fragment="rodape">
    		<div class="container">
    
    			<div class="footer-copy">
    				<div sec:authorize="isAuthenticated()">
    					<span th:text="#{login.username}"></span>: <span
    						sec:authentication="name"></span> | <span th:text="#{user.role}"></span>:
    					<span sec:authentication="principal.authorities"></span> <br />
    				<span>&copy; Departamento de Computação &copy;</span>
    				</div>
    			</div>
    		</div>
    	</footer>
    </body>
    </html>
    ```

    

    16.2 Arquivo **src/main/resources/templates/fragments/header.html**

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
    					<div sec:authorize="isAuthenticated()" style="right: 10px; position: absolute">
    						<form th:action="@{/logout}" method="post">
    							<div class="row">
    								<div class="col-sm-12 col-sm-offset-3">
    									<input type="submit" name="login-submit" id="login-submit"
    										class="form-control btn btn-info" value="Logout">
    								</div>
    							</div>
    						</form>
    					</div>
    					<div style="right: 100px; position: absolute">
    						<a th:href="@{''(lang=pt)}"><img alt=""
    							th:src="@{/image/Brasil.gif}" style="width: 30px;" /></a> <a
    							th:href="@{''(lang=en)}"><img alt=""
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

    

    16.3 Arquivo **src/main/resources/templates/fragments/sidebar.html**

    ```html
    <!DOCTYPE html>
    <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
    	xmlns:th="http://www.thymeleaf.org"
    	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity">
    <head>
    <head>
    <meta charset="UTF-8">
    </head>
    <body>
    	<nav class="sidebar sidebar-open" th:fragment="nav-sidebar">
    		<ul class="nav nav-pills">
    			<li class="nav-item"><a class="nav-link " th:href="@{/}"> <i
    					class="oi oi-home"></i> <span th:text="#{sidebar.link.home}"></span>
    			</a></li>
    		</ul>
    		<div sec:authorize="hasRole('ROLE_USER')">
    			<ul class="nav nav-pills">
    				<li class="nav-item"><span class="nav-link active"
    					th:text="#{sidebar.titulo.compra}"></span></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/compras/cadastrar}"> <i class="oi oi-plus"></i> <span
    						th:text="#{sidebar.link.cadastrar}"></span>
    				</a></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/compras/listar}"> <i class="oi oi-spreadsheet"></i>
    						<span th:text="#{sidebar.link.listar}"></span>
    				</a></li>
    			</ul>
    		</div>
    
    		<div sec:authorize="hasRole('ROLE_ADMIN')">
    			<ul class="nav nav-pills">
    				<li class="nav-item"><span class="nav-link active"
    					th:text="#{sidebar.titulo.editora}"></span></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/editoras/cadastrar}"> <i class="oi oi-plus"></i> <span
    						th:text="#{sidebar.link.cadastrar}"></span>
    				</a></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/editoras/listar}"> <i class="oi oi-spreadsheet"></i>
    						<span th:text="#{sidebar.link.listar}"></span>
    				</a></li>
    			</ul>
    		</div>
    
    		<div sec:authorize="hasRole('ROLE_ADMIN')">
    			<ul class="nav nav-pills">
    				<li class="nav-item"><span class="nav-link active"
    					th:text="#{sidebar.titulo.livro}"></span></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/livros/cadastrar}"> <i class="oi oi-plus"></i> <span
    						th:text="#{sidebar.link.cadastrar}"></span>
    				</a></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/livros/listar}"> <i class="oi oi-spreadsheet"></i>
    						<span th:text="#{sidebar.link.listar}"></span>
    				</a></li>
    			</ul>
    		</div>
    
    		<div sec:authorize="hasRole('ROLE_ADMIN')">
    			<ul class="nav nav-pills">
    				<li class="nav-item"><span class="nav-link active"
    					th:text="#{sidebar.titulo.usuario}"></span></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/usuarios/cadastrar}"> <i class="oi oi-plus"></i> <span
    						th:text="#{sidebar.link.cadastrar}"></span>
    				</a></li>
    				<li class="nav-item"><a class="nav-link"
    					th:href="@{/usuarios/listar}"> <i class="oi oi-spreadsheet"></i>
    						<span th:text="#{sidebar.link.listar}"></span>
    				</a></li>
    			</ul>
    		</div>
    	</nav>
    </body>
    </html>
    ```

    

    16.4 Arquivo **src/main/resources/templates/login.html**

    ```html
    <!DOCTYPE html>
    <html xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
    	xmlns:th="http://www.thymeleaf.org" layout:decorate="~{layout2}">
    <head>
    <title th:text="#{login.message}"></title>
    </head>
    <body>
    	<div layout:fragment="content" th:remove="tag">
    
    		<br /> <br /> <br />
    		<div>
    			<div class="col-md-6">
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
    							<div class="col-sm-3 col-sm-offset-3">
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

    

    16.5 Arquivo **src/main/resources/templates/layout2.html**

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
        <link rel="icon" th:href="@{/image/favicon.png}" />
    
        <title th:text="#{title.label}" layout:title-pattern="$LAYOUT_TITLE - $CONTENT_TITLE"></title>
    </head>
    <body>
        <th:block th:replace="fragments/header :: header"/>
        <div class="container">
            <th:block layout:fragment="content"/>
        </div>
    </body>
    </html>
    ```

    

    

##### (4) CRUD Compra

- - -

17. Adicionar a classe **br.ufscar.dc.dsw.domain.Compra** (entidade JPA)

    ```java
    package br.ufscar.dc.dsw.domain;
    
    import java.math.BigDecimal;
    
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.JoinColumn;
    import javax.persistence.ManyToOne;
    import javax.persistence.Table;
    import javax.validation.constraints.NotNull;
    
    @SuppressWarnings("serial")
    @Entity
    @Table(name = "Compra")
    public class Compra extends AbstractEntity<Long> {
    
    	@Column(nullable = false, length = 19)
    	private String data;
        
    	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
    	private BigDecimal valor;
        
    	@NotNull(message = "{NotNull.compra.livro}")
    	@ManyToOne
    	@JoinColumn(name = "livro_id")
    	private Livro livro;
    
    	@NotNull
    	@ManyToOne
    	@JoinColumn(name = "usuario_id")
    	private Usuario usuario;
    
    	public String getData() {
    		return data;
    	}
    
    	public void setData(String data) {
    		this.data = data;
    	}
    
    	public BigDecimal getValor() {
    		return valor;
    	}
    
    	public void setValor(BigDecimal preco) {
    		this.valor = preco;
    	}
    
    	public Livro getLivro() {
    		return livro;
    	}
    
    	public void setLivro(Livro livro) {
    		this.livro = livro;
    	}
    
    	public Usuario getUsuario() {
    		return usuario;
    	}
    
    	public void setUsuario(Usuario usuario) {
    		this.usuario = usuario;
    	}
    }
    ```

    

18. Adicionar a classe **br.ufscar.dc.dsw.conversor.LivroConversor**

    ```java
    package br.ufscar.dc.dsw.conversor;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.convert.converter.Converter;
    import org.springframework.stereotype.Component;
    
    import br.ufscar.dc.dsw.domain.Livro;
    import br.ufscar.dc.dsw.service.spec.ILivroService;
    
    @Component
    public class LivroConversor implements Converter<String, Livro>{
    
    	@Autowired
    	private ILivroService service;
    	
    	@Override
    	public Livro convert(String text) {
    		
    		if (text.isEmpty()) {
    		 return null;	
    		}
    		
    		Long id = Long.valueOf(text);	
    		return service.buscarPorId(id);
    	}
    }
    ```

    

19. Adicionar a classe **br.ufscar.dc.dsw.dao.ICompraDAO**

    ```java
    package br.ufscar.dc.dsw.dao;
    
    import java.util.List;
    
    import org.springframework.data.repository.CrudRepository;
    
    import br.ufscar.dc.dsw.domain.Compra;
    import br.ufscar.dc.dsw.domain.Usuario;
    
    @SuppressWarnings("unchecked")
    public interface ICompraDAO extends CrudRepository<Compra, Long>{
    
    	Compra findById(long id);
    
    	List<Compra> findAllByUsuario(Usuario u);
    	
    	Compra save(Compra compra);
    }
    ```

    

20. Adicionar a interface **br.ufscar.dc.dsw.service.spec.ICompraService**

    ```java
    package br.ufscar.dc.dsw.service.spec;
    
    import java.util.List;
    
    import br.ufscar.dc.dsw.domain.Compra;
    import br.ufscar.dc.dsw.domain.Usuario;
    
    public interface ICompraService {
    
    	Compra buscarPorId(Long id);
    
    	List<Compra> buscarTodos(Usuario u);
    
    	void salvar(Compra editora);
    }
    ```

    

21. Adicionar a classe **br.ufscar.dc.dsw.service.impl.CompraService**

    ```java
    package br.ufscar.dc.dsw.service.impl;
    
    import java.util.List;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    import br.ufscar.dc.dsw.dao.ICompraDAO;
    import br.ufscar.dc.dsw.domain.Compra;
    import br.ufscar.dc.dsw.domain.Usuario;
    import br.ufscar.dc.dsw.service.spec.ICompraService;
    
    @Service
    @Transactional(readOnly = false)
    public class CompraService implements ICompraService {
    
    	@Autowired
    	ICompraDAO dao;
    	
    	public void salvar(Compra compra) {
    		dao.save(compra);
    	}
    
    	@Transactional(readOnly = true)
    	public Compra buscarPorId(Long id) {
    		return dao.findById(id.longValue());
    	}
    
    	@Transactional(readOnly = true)
    	public List<Compra> buscarTodos(Usuario u) {
    		return dao.findAllByUsuario(u);
    	}
    }
    ```

    

22. Adicionar a classe **br.ufscar.dc.dsw.controller.CompraController**

    ```java
    package br.ufscar.dc.dsw.controller;
    
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.List;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.ModelMap;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    
    import br.ufscar.dc.dsw.domain.Compra;
    import br.ufscar.dc.dsw.domain.Livro;
    import br.ufscar.dc.dsw.domain.Usuario;
    import br.ufscar.dc.dsw.security.UsuarioDetails;
    import br.ufscar.dc.dsw.service.spec.ICompraService;
    import br.ufscar.dc.dsw.service.spec.ILivroService;
    
    @Controller
    @RequestMapping("/compras")
    public class CompraController {
    	
    	@Autowired
    	private ICompraService service;
    	
    	@Autowired
    	private ILivroService livroService;
    	
    	@GetMapping("/cadastrar")
    	public String cadastrar(Compra compra) {
    		compra.setUsuario(this.getUsuario());
    		compra.setData("31/08/2020");
    		//compra.setValor(compra.getLivro().getPreco());
    		return "compra/cadastro";
    	}
    	
    	private Usuario getUsuario() {
    		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		return usuarioDetails.getUser();
    	}
    	
    	@GetMapping("/listar")
    	public String listar(ModelMap model) {
    					
    		model.addAttribute("compras",service.buscarTodos(this.getUsuario()));
    		
    		return "compra/lista";
    	}
    	
    	@PostMapping("/salvar")
    	public String salvar(Compra compra, BindingResult result, RedirectAttributes attr) {
    		
    		String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    		compra.setUsuario(this.getUsuario());
    		compra.setData(data);
    		compra.setValor(compra.getLivro().getPreco());
    		
    		service.salvar(compra);
    		attr.addFlashAttribute("sucess", "Compra inserida com sucesso.");
    		return "redirect:/compras/listar";
    	}
    	
    	@ModelAttribute("livros")
    	public List<Livro> listaLivros() {
    		return livroService.buscarTodos();
    	}
    }
    ```

    <div style="page-break-after: always"></div>

23. Adicionar os arquivos relacionados à visão da entidade Compra

    23.1 Arquivo **src/main/resources/templates/compra/cadastro.html**

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
    					<li class="nav-item active"><i class="oi oi-caret-right"></i>
    						<span th:text="#{compra.cadastrar.label}"></span></li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/compras/listar}"
    					role="button"> <span class="oi oi-spreadsheet" title="Listar"
    					aria-hidden="true"></span> <span th:text="#{compra.listar.label}"></span>
    				</a>
    			</div>
    		</nav>
    
    		<div class="container" id="cadastro">
    
    			<div th:replace="fragments/alert"></div>
    
    			<form th:action="@{/compras/salvar}" th:object="${compra}"
    				method="POST">
    
    				<div th:replace="fragments/validacao :: validacao"></div>
    
    
    				<div class="form-row">
    					<div class="form-group col-md-12">
    						
    						<table class="table table-striped table-hover table-sm">
    							<thead>
    								<tr>
    									<th>#</th>
    									<th th:text="#{livro.titulo.label}"></th>
    									<th th:text="#{livro.autor.label}"></th>
    									<th th:text="#{livro.editora.label}"></th>
    									<th th:text="#{livro.ano.label}"></th>
    									<th th:text="#{livro.preco.label}"></th>
    								</tr>
    							</thead>
    							<tbody>
    								<tr th:each="livro : ${livros}">
    									<td>
    										<input type="radio" th:id="${livro.id}" name="livro" th:value="${livro.id}" th:field="*{livro}" required>
    									</td>
    									<td th:text="${livro.titulo}"></td>
    									<td th:text="${livro.autor}"></td>
    									<td th:text="${livro.editora.nome}"></td>
    									<td th:text="${livro.ano}"></td>
    									<td th:text="|R$ ${#numbers.formatDecimal(livro.preco,2,2,'COMMA')}|"></td>
    								</tr>
    							</tbody>
    						</table>
    
    						<div class="invalid-feedback">
    							<span th:errors="*{livro}"></span>
    						</div>
    					</div>
    				</div>
    
    				<button type="submit" th:text="#{button.salvar.label}"
    					class="btn btn-primary btn-sm"></button>
    			</form>
    		</div>
    	</section>
    </body>
    </html>
    ```

    23.2 Arquivo **src/main/resources/templates/compra/lista.html**

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
    						<span th:text="#{compra.listar.label}"></span>
    					</li>
    				</ul>
    				<a class="btn btn-primary btn-md" th:href="@{/compras/cadastrar}" role="button"> 
    					<span class="oi oi-plus" title="Cadastro" aria-hidden="true"></span> 
    					<span th:text="#{compra.cadastrar.label}"></span>
    				</a>
    			</div>
    		</nav>
    		
    		<div class="container" id="listagem">
    
    			<div th:replace="fragments/alert"></div>
    
    			<div class="table-responsive">
    				<table class="table table-striped table-hover table-sm">
    					<thead>
    						<tr>
    							<th class="bg-warning">#</th>
    							<th class="bg-warning" th:text="#{compra.data.label}"></th>
    							<th class="bg-warning" th:text="#{livro.titulo.label}"></th>
    							<th class="bg-warning" th:text="#{livro.autor.label}"></th>
    							<th class="bg-warning" th:text="#{livro.editora.label}"></th>
    							<th class="bg-warning" th:text="#{livro.ano.label}"></th>
    							<th class="bg-warning" th:text="#{compra.valor.label}"></th>
    						</tr>
    					</thead>
    					<tbody>
    						<tr th:each="compra : ${compras}">
    							<td th:text="${compra.id}"></td>
    							<td th:text="${compra.data}"></td>
    							<td th:text="${compra.livro.titulo}"></td>
    							<td th:text="${compra.livro.autor}"></td>
    							<td th:text="${compra.livro.editora.nome}"></td>
    							<td th:text="${compra.livro.ano}"></td>
    							<td th:text="|R$ ${#numbers.formatDecimal(compra.valor,2,2,'COMMA')}|"></td>
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

24. Atualizar a classe **br.ufscar.dc.dsw.LivrariaMvcApplication**

    ```java
    package br.ufscar.dc.dsw;
    
    import java.math.BigDecimal;
    
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    
    import br.ufscar.dc.dsw.dao.IEditoraDAO;
    import br.ufscar.dc.dsw.dao.ILivroDAO;
    import br.ufscar.dc.dsw.dao.IUsuarioDAO;
    import br.ufscar.dc.dsw.domain.Editora;
    import br.ufscar.dc.dsw.domain.Livro;
    import br.ufscar.dc.dsw.domain.Usuario;
    
    @SpringBootApplication
    public class LivrariaMvcApplication {
    
    	public static void main(String[] args) {
    		SpringApplication.run(LivrariaMvcApplication.class, args);
    	}
    
    	@Bean
    	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder, IEditoraDAO editoraDAO, ILivroDAO livroDAO) {
    		return (args) -> {
    			
    			Usuario u1 = new Usuario();
    			u1.setUsername("admin");
    			u1.setPassword(encoder.encode("admin"));
    			u1.setCPF("012.345.678-90");
    			u1.setName("Administrador");
    			u1.setRole("ROLE_ADMIN");
    			u1.setEnabled(true);
    			usuarioDAO.save(u1);
    			
    			Usuario u2 = new Usuario();
    			u2.setUsername("beltrano");
    			u2.setPassword(encoder.encode("123"));
    			u2.setCPF("985.849.614-10");
    			u2.setName("Beltrano Andrade");
    			u2.setRole("ROLE_USER");
    			u2.setEnabled(true);
    			usuarioDAO.save(u2);
    			
    			Usuario u3 = new Usuario();
    			u3.setUsername("fulano");
    			u3.setPassword(encoder.encode("123"));
    			u3.setCPF("367.318.380-04");
    			u3.setName("Fulano Silva");
    			u3.setRole("ROLE_USER");
    			u3.setEnabled(true);
    			usuarioDAO.save(u3);
    			
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

     

25. Executar (**mvn spring-boot:run**) e testar

26. Fim
