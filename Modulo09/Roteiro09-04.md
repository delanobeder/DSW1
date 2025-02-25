## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 09 - REST API** 

- - -

#### 04 - Livraria Virtual - Cliente do REST API (TransacoesRS) 
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo09/LivrariaMVC-v4)

- - -



1. Fazer uma cópia do diretório da aplicação **LivrariaMVC** (Roteiro 08-05).

   ```sh
   % cp -r LivrariaMVC-v3 LivrariaMVC-v4
   ```

   

2. Abrir a aplicação cópia

3. Atualizar a classe **br.ufscar.dc.dsw.controller.CompraController**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.List;
   import java.util.Locale;
   import java.util.ResourceBundle;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.security.core.context.SecurityContextHolder;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.ModelMap;
   import org.springframework.validation.BindingResult;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.client.RestClientException;
   import br.ufscar.dc.dsw.domain.Cartao;
   import br.ufscar.dc.dsw.domain.Compra;
   import br.ufscar.dc.dsw.domain.Livro;
   import br.ufscar.dc.dsw.domain.Transacao;
   import br.ufscar.dc.dsw.domain.Usuario;
   import br.ufscar.dc.dsw.security.UsuarioDetails;
   import br.ufscar.dc.dsw.service.spec.IClienteRestService;
   import br.ufscar.dc.dsw.service.spec.ICompraService;
   import br.ufscar.dc.dsw.service.spec.ILivroService;
   
   @Controller
   @RequestMapping("/compras")
   public class CompraController {
   
   	@Autowired
   	private ICompraService service;
   
   	@Autowired
   	private ILivroService livroService;
   
   	@Autowired
   	private IClienteRestService clienteRestService;
   
   	@GetMapping("/cadastrar")
   	public String cadastrar(ModelMap model, Compra compra) {
   		try {
   			model.addAttribute("livros", listaLivros());
   			model.addAttribute("cartoes", listaCartoes());
   		} catch (RestClientException e) {
   			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
   		}
   		return "compra/cadastro";
   	}
   
   	private Usuario getUsuario() {
   		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication()
   				.getPrincipal();
   		return usuarioDetails.getUser();
   	}
   
   	@GetMapping("/listar")
   	public String listar(ModelMap model, Locale locale) {
   
   		List<Compra> compras = service.buscarTodos(this.getUsuario());
   		try {
   			for (Compra c : compras) {
   				Transacao t = clienteRestService.buscaTransacao(c.getTransacaoID());
   				c.setDetalhes(toHTMLString(t, locale));
   				c.setData(t.getData());
   			}
   		} catch (RestClientException e) {
   			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
   		}
   
   		model.addAttribute("compras", compras);
   
   		return "compra/lista";
   	}
   
   	@PostMapping("/salvar")
   	public String salvar(Compra compra, BindingResult result, ModelMap model) {
   
   		try {
   			compra.setValor(compra.getLivro().getPreco());
   			compra.setUsuario(this.getUsuario());
   
   			Transacao transacao = new Transacao();
   			transacao.setDescricao("Compra Livraria Virtual");
   			transacao.setValor(compra.getLivro().getPreco().doubleValue());
   			String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
   			transacao.setData(data);
   			transacao.setCategoria("COMPRA");
   			transacao.setStatus("CONFIRMADA");
   			transacao.setCartao(compra.getCartao());
   			Long id = clienteRestService.salva(transacao);
   			compra.setTransacaoID(id);
   			service.salvar(compra);
   			model.addAttribute("sucess", "Compra inserida com sucesso.");
   			return "redirect:/compras/listar";
   		} catch (RestClientException e) {
   			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
   			return cadastrar(model, compra);
   		}
   	}
   
   	public List<Livro> listaLivros() {
   		return livroService.buscarTodos();
   	}
   
   	public List<Cartao> listaCartoes() {
   		return clienteRestService.buscaCartoes(getUsuario().getCPF());
   	}
   
   	private String toHTMLString(Transacao transacao, Locale locale) {
   		Cartao cartao = transacao.getCartao();
   		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
   		StringBuffer sb = new StringBuffer();
   
   		String s = bundle.getString("transacao.descricao.label");
   		sb.append("<b>" + s + "</b> " + transacao.getDescricao() + "<br/>");
   		s = bundle.getString("transacao.valor.label");
   		sb.append("<b>" + s + "</b> R$ " + String.format("%.2f", (double) transacao.getValor()) + "<br/>");
   		s = bundle.getString("transacao.data.label");
   		sb.append("<b>" + s + "</b> " + transacao.getData() + "<br/>");
   		s = bundle.getString("transacao.cartao.label");
   		sb.append("<b>" + s + " </b><br/>");
   		sb.append("<ul>");
   		s = bundle.getString("cartao.numero.label");
   		sb.append("<li><b>" + s + " </b>" + cartao.getNumero() + "</li>");
   		s = bundle.getString("cartao.titular.label");
   		sb.append("<li><b>" + s + " </b>" + cartao.getTitular() + "</li>");
   		s = bundle.getString("cartao.CPF.label");
   		sb.append("<li><b>" + s + " </b>" + cartao.getCPF() + "</li>");
   		s = bundle.getString("cartao.vencimento.label");
   		sb.append("<li><b>" + s + " </b>" + cartao.getVencimento() + "</li>");
   		sb.append("</ul>");
   		return sb.toString();
   	}
   }
   ```
   
   

4. Adicionar a classe **br.ufscar.dc.dsw.conversor.CartaoConversor**

   ```java
   package br.ufscar.dc.dsw.conversor;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.core.convert.converter.Converter;
   import org.springframework.stereotype.Component;
   import org.springframework.web.client.RestClientException;
   import br.ufscar.dc.dsw.domain.Cartao;
   import br.ufscar.dc.dsw.service.spec.IClienteRestService;
   
   @Component
   public class CartaoConversor implements Converter<String, Cartao>{
   
   	@Autowired
   	private IClienteRestService service;
   	
   	@Override
   	public Cartao convert(String text) {
   		
   		if (text.isEmpty()) {
   		 return null;	
   		}
   		
   		Long id = Long.valueOf(text);	
   		try {
   			return service.buscaCartao(id);
   		} catch(RestClientException e) {
   			return null;
   		}
   		
   	}
   }
   ```
   
   
   
5. Adicionar a classe **br.ufscar.dc.dsw.domain.Cartao**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   @SuppressWarnings("serial")
   public class Cartao extends AbstractEntity<Long> {
   
   	private String titular;
   	private String CPF;
   	private String numero;
   	private String vencimento;
   	private String CVV;
   	
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
   	public void setCVV(String cvv) {
   		this.CVV = cvv;
   	}
   
   	@Override
   	public String toString() {
   		return numero + "/" + titular;
   	}
   }
   ```

   

6. Atualizar a classe **br.ufscar.dc.dsw.domain.Compra**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.math.BigDecimal;
   import jakarta.persistence.Column;
   import jakarta.persistence.Entity;
   import jakarta.persistence.JoinColumn;
   import jakarta.persistence.ManyToOne;
   import jakarta.persistence.Table;
   import jakarta.persistence.Transient;
   import jakarta.validation.constraints.NotNull;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Compra")
   public class Compra extends AbstractEntity<Long> {
       
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
   	
   	@Column(nullable = false, name = "transacao_id")
   	private Long transacaoID;
   
   	@Transient
   	private Cartao cartao;
   	
   	@Transient
   	private String detalhes;
   	
   	@Transient
   	private String data;
   	
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
   	
   	public Long getTransacaoID() {
   		return transacaoID;
   	}
   
   	public void setTransacaoID(Long transacao) {
   		this.transacaoID = transacao;
   	}
   
   	public Cartao getCartao() {
   		return cartao;
   	}
   
   	public void setCartao(Cartao cartao) {
   		this.cartao = cartao;
   	}
   
   	public String getDetalhes() {
   		return detalhes;
   	}
   
   	public void setDetalhes(String descricao) {
   		this.detalhes = descricao;
   	}
   
   	public String getData() {
   		return data;
   	}
   
   	public void setData(String data) {
   		this.data = data;
   	}
   	
   	
   }
   ```

   

7. Adicionar a classe **br.ufscar.dc.dsw.domain.Transacao**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   @SuppressWarnings("serial")
   public class Transacao extends AbstractEntity<Long> {
   
   	private String descricao;
   	private Double valor;
   	private String data;
   	private String categoria;
   	private String status;
   	private Cartao cartao;
   
   	public String getDescricao() {
   		return descricao;
   	}
   	public void setDescricao(String descricao) {
   		this.descricao = descricao;
   	}
   
   	public Double getValor() {
   		return valor;
   	}
   	public void setValor(Double valor) {
   		this.valor = valor;
   	}
   
   	public String getData() {
   		return data;
   	}
   	public void setData(String data) {
   		this.data = data;
   	}
   
   	public String getCategoria() {
   		return categoria;
   	}
   	public void setCategoria(String tipo) {
   		this.categoria = tipo;
   	}
   
   	public String getStatus() {
   		return status;
   	}
   	public void setStatus(String status) {
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

   

8. Adicionar a classe **br.ufscar.dc.dsw.service.spec.IClienteRestService**

   ```java
   package br.ufscar.dc.dsw.service.spec;
   
   import java.util.List;
   import br.ufscar.dc.dsw.domain.Cartao;
   import br.ufscar.dc.dsw.domain.Transacao;
   
   public interface IClienteRestService {
   	Cartao buscaCartao(Long id);
   	List<Cartao> buscaCartoes(String cpf);
   	Transacao buscaTransacao(Long id);
   	Long salva(Transacao transacao);
   	boolean remove(Long id);
   }
   ```

   

9. Adicionar a classe **br.ufscar.dc.dsw.service.impl.ClienteRestService**

   ```java
   package br.ufscar.dc.dsw.service.impl;
   
   import java.util.Arrays;
   import java.util.List;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.web.client.RestTemplateBuilder;
   import org.springframework.context.annotation.Bean;
   import org.springframework.http.HttpEntity;
   import org.springframework.http.HttpHeaders;
   import org.springframework.http.MediaType;
   import org.springframework.http.ResponseEntity;
   import org.springframework.stereotype.Service;
   import org.springframework.web.client.RestClientException;
   import org.springframework.web.client.RestTemplate;
   
   import br.ufscar.dc.dsw.domain.Cartao;
   import br.ufscar.dc.dsw.domain.Transacao;
   import br.ufscar.dc.dsw.service.spec.IClienteRestService;
   
   @Service
   public class ClienteRestService implements IClienteRestService {
   
   	@Autowired
   	private RestTemplate restTemplate;
   
   	@Bean
   	public RestTemplate restTemplate(RestTemplateBuilder builder) {
   		return builder.build();
   	}
   
   	public Cartao buscaCartao(Long id) {
   		Cartao cartao = restTemplate.getForObject("http://localhost:8081/cartoes/" + id, Cartao.class);
   		return cartao;
   	}
   
   	public List<Cartao> buscaCartoes(String cpf) {
   		Cartao[] cartoes = restTemplate.getForObject("http://localhost:8081/cartoes/cpf/" + cpf, Cartao[].class);
   		return Arrays.asList(cartoes);
   	}
   
   	public Transacao buscaTransacao(Long id) {
   		return restTemplate.getForObject("http://localhost:8081/transacoes/" + id, Transacao.class);
   	}
   
   	public Long salva(Transacao transacao) {
   
   		HttpHeaders headers = new HttpHeaders();
   		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
   		HttpEntity<Transacao> entity = new HttpEntity<Transacao>(transacao, headers);
   
   		ResponseEntity<Transacao> res = restTemplate.postForEntity("http://localhost:8081/transacoes", entity,
   				Transacao.class);
   		Transacao t = res.getBody();
   
   		return t.getId();
   	}
   
   //	public boolean remove(Long id) {
   //		Transacao transacao = this.buscaTransacao(id);
   //		transacao.setStatus("CANCELADA");
   //		HttpHeaders headers = new HttpHeaders();
   //		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
   //		HttpEntity<Transacao> entity = new HttpEntity<Transacao>(transacao, headers);
   //
   //		ResponseEntity<Transacao> res = restTemplate.exchange("http://localhost:8081/transacoes/" + id, HttpMethod.PUT,
   //				entity, Transacao.class);
   //
   //		return res.getStatusCode() == HttpStatus.OK;
   //	}
   
   	public boolean remove(Long id) {
   
   		try {
   			restTemplate.delete("http://localhost:8081/transacoes/" + id);
   			return true;
   		} catch (RestClientException e) {
   			return false;
   		}
   	}
   }
   ```

   

10. Atualizar os arquivos relacionados à visão da entidade Compra

    10.1 Arquivo **src/main/resources/templates/compra/cadastro.html**

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
    						<label for="cartao">Cartão</label> 
    						<select id="cartao"
    							class="form-control" th:field="*{cartao}"
    							th:classappend="${#fields.hasErrors('cartao')} ? is-invalid" required>
    							<option value="">Selecione</option>
    							<option th:each="cartao : ${cartoes}" th:value="${cartao.id}"
    								th:text="|${cartao.numero} - ${cartao.titular}|"></option>
    						</select>
    						
    						<div class="invalid-feedback">
    							<span th:errors="*{cartao}"></span>
    						</div>
    					</div>
    					
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

    

    10.2 Arquivo **src/main/resources/templates/compra/lista.html**

    ```java
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
    							<th class="bg-warning" style="text-align: center" th:text="#{compra.detalhes.label}"></th>
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
    							<td style="text-align: center">
    							<button type="button" class="btn btn-secondary btn-sm"
    									data-container="body" data-toggle="popover"
    									data-html="true"
    									data-placement="right"
    									th:attr="data-content=${compra.detalhes}">
    									<span class="oi oi-eye" title="Visualizar" aria-hidden="true"></span>
    									</button>
    							<!--  
    							<a th:href="@{/}" data-toggle="modal" data-target="#exampleModalCenter"> <span
    									th:text="${compra.transacao}"> </span>
    							</a>
    							-->
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

    

11. Atualizar os arquivos relacionados a internacionalização

    11.1 Arquivo **src/main/resources/messages.properties**

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
    compra.detalhes.label = Detalhes da Transação
    
    # Titulos Transação
    
    transacao.descricao.label = Descrição:
    transacao.valor.label = Valor:
    transacao.data.label = Data:
    transacao.cartao.label = Cartão:
    
    # Titulos Cartão
    
    cartao.numero.label = Número:
    cartao.titular.label = Titular:
    cartao.CPF.label = CPF:
    cartao.vencimento.label = Vencimento:
    
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

    

    11.2 Arquivo **src/main/resources/messages_en.properties**

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
    compra.detalhes.label = Transaction Details
    
    # Titulos Transacao
    
    transacao.descricao.label = Description:
    transacao.valor.label = Value:
    transacao.data.label = Date:
    transacao.cartao.label = Card:
    
    # Card Titles
    
    cartao.numero.label = Number:
    cartao.titular.label = Holder:
    cartao.CPF.label = SSN:
    cartao.vencimento.label = Expiration:
    
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

    



12. Executar (**mvn spring-boot:run**) e testar
13. Fim







#### Leituras adicionais

- - -


- The Guide to RestTemplate

  https://www.baeldung.com/rest-template

  

- Spring Boot - Rest Template

  https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm

  

- Spring RestTemplate

  https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
