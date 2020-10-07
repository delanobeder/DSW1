## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 05 - MVC utilizando as tecnologias Servlet, JSP, JSTL & JDBC**

- - -

#### 01 - CRUD MVC utilizando as tecnologias Servlet, JSP, JSTL & JDBC
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo05/Livraria-v2)
-- - -



##### (1) Configuração
- - -

1. Abrir projeto do Roteiro04-01 (**Livraria**) e adicionar biblioteca **JSTL** via Maven, adicionando as seguintes linhas ao arquivo pom.xml:

   ```xml
   <dependency>
       <groupId>javax.servlet</groupId>
       <artifactId>jstl</artifactId>
       <version>1.2</version>
       <scope>runtime</scope>
   </dependency>
   ```




##### (2) MVC: Modelo
- - -




2. Classes de entidade/domínio **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.domain**

   2.1. Criar a classe **br.ufscar.dc.dsw.domain.Editora**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   public class Editora {
   
       private Long id;
       private String CNPJ;
       private String nome;
   
       public Editora(Long id) {
           this.id = id;
       }
   
       public Editora(String CNPJ, String nome) {
           this.CNPJ = CNPJ;
           this.nome = nome;
       }
   
       public Editora(Long id, String CNPJ, String nome) {
           this(CNPJ, nome);
           this.id = id;
       }
   
       public Long getId() {
           return id;
       }
       public void setId(Long id) {
           this.id = id;
       }
   
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
   }
   ```

3. Classes DAO (*Data Access Object*) **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.dao**

   3.1. Criar a classe **br.ufscar.dc.dsw.dao.GenericDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.SQLException;
   
   abstract public class GenericDAO {
       
       public GenericDAO() {
           try {           
           	/* Setup Banco de dados Derby */
           	// Class.forName("org.apache.derby.jdbc.ClientDriver");
               
           	/* Setup Banco de dados MySQL */
           	Class.forName("com.mysql.cj.jdbc.Driver");
           } catch (ClassNotFoundException e) {
               throw new RuntimeException(e);
           }
       }
   
       protected Connection getConnection() throws SQLException {
       	
       	/* Conexão banco de dados Derby */
       	
           /* 
       	 * String url = "jdbc:derby://localhost:1527/Livraria";
       	 */
       	
       	/* Conexão banco de dados MySQL */
           
       	String url = "jdbc:mysql://localhost:3306/Livraria";
       	
           return DriverManager.getConnection(url, "root", "root");
       }
   }
   ```
   3.2 Criar a classe **br.ufscar.dc.dsw.dao.LivroDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.sql.Connection;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;
   import java.util.ArrayList;
   import java.util.List;
   
   import br.ufscar.dc.dsw.domain.Editora;
   import br.ufscar.dc.dsw.domain.Livro;
   
   public class LivroDAO extends GenericDAO {
   
       public void insert(Livro livro) {
   
           String sql = "INSERT INTO Livro (titulo, autor, ano, preco, editora_id) VALUES (?, ?, ?, ?, ?)";
   
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);;
   
               statement = conn.prepareStatement(sql);
               statement.setString(1, livro.getTitulo());
               statement.setString(2, livro.getAutor());
               statement.setInt(3, livro.getAno());
               statement.setFloat(4, livro.getPreco());
               statement.setLong(5, livro.getEditora().getId());
               statement.executeUpdate();
   
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
   
       public List<Livro> getAll() {
   
           List<Livro> listaLivros = new ArrayList<>();
   
           String sql = "SELECT * from Livro l, Editora e where l.EDITORA_ID = e.ID order by l.id";
   
           try {
               Connection conn = this.getConnection();
               Statement statement = conn.createStatement();
   
               ResultSet resultSet = statement.executeQuery(sql);
               while (resultSet.next()) {
                   Long id = resultSet.getLong("id");
                   String titulo = resultSet.getString("titulo");
                   String autor = resultSet.getString("autor");
                   int ano = resultSet.getInt("ano");
                   float preco = resultSet.getFloat("preco");
                   Long editora_id = resultSet.getLong(6);
                   String cnpj = resultSet.getString("cnpj");
                   String nome = resultSet.getString("nome");
                   Editora editora = new Editora(editora_id, cnpj, nome);
                   Livro livro = new Livro(id, titulo, autor, ano, preco, editora);
                   listaLivros.add(livro);
               }
   
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return listaLivros;
       }
   
       public void delete(Livro livro) {
           String sql = "DELETE FROM Livro where id = ?";
   
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
   
               statement.setLong(1, livro.getId());
               statement.executeUpdate();
   
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
   
       public void update(Livro livro) {
           String sql = "UPDATE Livro SET titulo = ?, autor = ?, ano = ?, preco = ?";
           sql += ", editora_id = ? WHERE id = ?";
   
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
   
               statement.setString(1, livro.getTitulo());
               statement.setString(2, livro.getAutor());
               statement.setInt(3, livro.getAno());
               statement.setFloat(4, livro.getPreco());
               statement.setFloat(5, livro.getEditora().getId());
               statement.setLong(6, livro.getId());
               statement.executeUpdate();
   
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
   
       public Livro get(Long id) {
           Livro livro = null;
   
           String sql = "SELECT * from Livro l, Editora e where l.id = ? and l.EDITORA_ID = e.ID";
   
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
   
               statement.setLong(1, id);
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
                   String titulo = resultSet.getString("titulo");
                   String autor = resultSet.getString("autor");
                   int ano = resultSet.getInt("ano");
                   float preco = resultSet.getFloat("preco");
   
                   Long editoraID = resultSet.getLong("editora_id");
                   Editora editora = new EditoraDAO().get(editoraID);
   
                   livro = new Livro(id, titulo, autor, ano, preco, editora);
               }
   
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return livro;
       }
   }
   ```

   <div style="page-break-after: always"></div>
   
   3.3. Criar a classe **br.ufscar.dc.dsw.dao.EditoraDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.sql.Connection;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;
   import java.util.ArrayList;
   import java.util.List;
   
   import br.ufscar.dc.dsw.domain.Editora;
   
   public class EditoraDAO extends GenericDAO {
   
       public List<Editora> getAll() {
   
           List<Editora> listaEditoras = new ArrayList<>();
           String sql = "SELECT * from Editora";
   
           try {
               Connection conn = this.getConnection();
               Statement statement = conn.createStatement();
   
               ResultSet resultSet = statement.executeQuery(sql);
               while (resultSet.next()) {
                   Long id = resultSet.getLong("id");
                   String cnpj = resultSet.getString("cnpj");
                   String nome = resultSet.getString("nome");
                   Editora editora = new Editora(id, cnpj, nome);
                   listaEditoras.add(editora);
               }
   
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return listaEditoras;
       }
   
       public Editora get(Long id) {
           Editora editora = null;   
           String sql = "SELECT * from Editora where id = ?";
   
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
               
               statement.setLong(1, id);
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
                   String cnpj = resultSet.getString("cnpj");
                   String nome = resultSet.getString("nome");
                   editora = new Editora(id, cnpj, nome);
               }
   
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
        return editora;
       }
   }
   ```

   

##### (3) MVC: Controlador
- - -




4. Criar os controladores **[C do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.controller**

   4.1. Criar a classe **br.ufscar.dc.dsw.controller.LivroController**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import br.ufscar.dc.dsw.dao.EditoraDAO;
   import br.ufscar.dc.dsw.dao.LivroDAO;
   import br.ufscar.dc.dsw.domain.Editora;
   import br.ufscar.dc.dsw.domain.Livro;
   import java.io.IOException;
   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;
   import javax.servlet.RequestDispatcher;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = "/livros/*")
   public class LivroController extends HttpServlet {
   
       private static final long serialVersionUID = 1L; 
       private LivroDAO dao;
   
       @Override
       public void init() {
           dao = new LivroDAO();
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
           doGet(request, response);
       }
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {           
           String action = request.getPathInfo();
           if (action == null) {
               action = "";
           }
   
           try {
               switch (action) {
                   case "/cadastro":
                       apresentaFormCadastro(request, response);
                       break;
                   case "/insercao":
                       insere(request, response);
                       break;
                   case "/remocao":
                       remove(request, response);
                       break;
                   case "/edicao":
                       apresentaFormEdicao(request, response);
                       break;
                   case "/atualizacao":
                       atualize(request, response);
                       break;
                   default:
                       lista(request, response);
                       break;
               }
           } catch (RuntimeException | IOException | ServletException e) {
               throw new ServletException(e);
           }
       }
   
       private void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           List<Livro> listaLivros = dao.getAll();
           request.setAttribute("listaLivros", listaLivros);
           RequestDispatcher dispatcher = request.getRequestDispatcher("/livro/lista.jsp");
           dispatcher.forward(request, response);
       }
   
       private Map<Long, String> getEditoras() {
           Map <Long,String> editoras = new HashMap<>();
           for (Editora editora: new EditoraDAO().getAll()) {
               editoras.put(editora.getId(), editora.getNome());
           }
           return editoras;
       }
       
       private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.setAttribute("editoras", getEditoras());
           RequestDispatcher dispatcher = request.getRequestDispatcher("/livro/formulario.jsp");
           dispatcher.forward(request, response);
       }
   
       private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           Long id = Long.parseLong(request.getParameter("id"));
           Livro livro = dao.get(id);
           request.setAttribute("livro", livro);
           request.setAttribute("editoras", getEditoras());
           RequestDispatcher dispatcher = request.getRequestDispatcher("/livro/formulario.jsp");
           dispatcher.forward(request, response);
       }
   
       private void insere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.setCharacterEncoding("UTF-8");
           
           String titulo = request.getParameter("titulo");
           String autor = request.getParameter("autor");
           Integer ano = Integer.parseInt(request.getParameter("ano"));
           Float preco = Float.parseFloat(request.getParameter("preco"));
           
           Long editoraID = Long.parseLong(request.getParameter("editora"));
           Editora editora = new EditoraDAO().get(editoraID);
           
           Livro livro = new Livro(titulo, autor, ano, preco, editora);
           dao.insert(livro);
           response.sendRedirect("lista");
       }
   
       private void atualize(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
           request.setCharacterEncoding("UTF-8");
           Long id = Long.parseLong(request.getParameter("id"));
           String titulo = request.getParameter("titulo");
           String autor = request.getParameter("autor");
           Integer ano = Integer.parseInt(request.getParameter("ano"));
           Float preco = Float.parseFloat(request.getParameter("preco"));
           
           Long editoraID = Long.parseLong(request.getParameter("editora"));
           Editora editora = new EditoraDAO().get(editoraID);
           
           Livro livro = new Livro(id, titulo, autor, ano, preco, editora);
           dao.update(livro);
           response.sendRedirect("lista");
       }
   
       private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
           Long id = Long.parseLong(request.getParameter("id"));
   
           Livro livro = new Livro(id);
           dao.delete(livro);
           response.sendRedirect("lista");
       }
   }
   ```




##### (4) MVC: Visão
- - -



5. Criar as visões (arquivos em **src/main/webapp**) **[V do MVC]**

   5.1. Criar o arquivo **livro/lista.jsp**

   ```jsp
   <%@ page language="java" contentType="text/html; charset=UTF-8"
   	pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <html>
   <head>
   <title>Livraria Virtual</title>
   </head>
   <body>
   	<%
   		String contextPath = request.getContextPath().replace("/", "");
   	%>
   	<div align="center">
   		<h1>Gerenciamento de Livros</h1>
   		<h2>
   			<a href="/<%=contextPath%>">Menu Principal</a> &nbsp;&nbsp;&nbsp; <a
   				href="/<%=contextPath%>/livros/cadastro">Adicione Novo Livro</a>
   		</h2>
   	</div>
   
   	<div align="center">
   		<table border="1">
   			<caption>Lista de Livros</caption>
   			<tr>
   				<th>ID</th>
   				<th>Título</th>
   				<th>Editora</th>
   				<th>Autor</th>
   				<th>Ano</th>
   				<th>Preço</th>
   				<th>Acões</th>
   			</tr>
   			<c:forEach var="livro" items="${requestScope.listaLivros}">
   				<tr>
   					<td>${livro.id}</td>
   					<td>${livro.titulo}</td>
   					<td>${livro.editora.nome}</td>
   					<td>${livro.autor}</td>
   					<td>${livro.ano}</td>
   					<td>${livro.preco}</td>
   					<td><a href="/<%= contextPath%>/livros/edicao?id=${livro.id}">Edição</a>
   						&nbsp;&nbsp;&nbsp;&nbsp; <a
   						href="/<%= contextPath%>/livros/remocao?id=${livro.id}"
   						onclick="return confirm('Tem certeza de que deseja excluir este item?');">
   							Remoção </a></td>
   				</tr>
   			</c:forEach>
   		</table>
   	</div>
   </body>
   </html>
   ```

   5.2. Criar o arquivo **livro/formulario.jsp**

   ```jsp
   <%@ page language="java" contentType="text/html; charset=UTF-8"
   	pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <html>
   <head>
   <title>Livraria Virtual</title>
   </head>
   
   <body>
   	<div align="center">
   		<h1>Gerenciamento de Livros</h1>
   		<h2>
   			<a href="lista">Lista de Livros</a>
   		</h2>
   	</div>
   	<div align="center">
   		<c:choose>
   			<c:when test="${livro != null}">
   				<form action="atualizacao" method="post">
   					<%@include file="campos.jsp"%>
   				</form>
   			</c:when>
   			<c:otherwise>
   				<form action="insercao" method="post">
   					<%@include file="campos.jsp"%>
   				</form>
   			</c:otherwise>
   		</c:choose>
   	</div>
   	<c:if test="${!empty requestScope.mensagens}">
   		<ul class="erro">
   			<c:forEach items="${requestScope.mensagens}" var="mensagem">
   				<li>${mensagem}</li>
   			</c:forEach>
   		</ul>
   	</c:if>
   </body>
   </html>
   ```
   <div style="page-break-after: always"></div>

   5.3. Criar o arquivo **livro/campos.jsp**
   
   
   ```jsp
   <%@ page language="java" contentType="text/html; charset=UTF-8"
   	pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <table border="1">
   	<caption>
      		<c:choose>
      			<c:when test="${livro != null}">
                                  Edição
                              </c:when>
      			<c:otherwise>
                                  Cadastro
                              </c:otherwise>
      		</c:choose>
   	</caption>
    	<c:if test="${livro != null}">
      		<input type="hidden" name="id" value="${livro.id}" />
      	</c:if>
      	<tr>
      		<td><label for="titulo">Título</label></td>
      		<td><input type="text" id="titulo" name="titulo" size="45"
      			required value="${livro.titulo}" /></td>
      	</tr>
      	<tr>
      		<td><label for="autor">Autor</label></td>
      		<td><input type="text" id="autor" name="autor" size="45" required
      			value="${livro.autor}" /></td>
      	</tr>
      	<tr>
      		<td><label for="editora">Editora</label></td>
      		<td><select id="editora" name="editora">
      				<c:forEach items="${editoras}" var="editora">
      					<option value="${editora.key}"
      						${livro.editora.nome==editora.value ? 'selected' : '' }>
      						${editora.value}</option>
      				</c:forEach>
      		</select></td>
      	</tr>
      	<tr>
      		<td><label for="ano">Ano</label></td>
      		<td><input type="number" id="ano" name="ano" size="5" required
      			min="1500" value="${livro.ano}" /></td>
      	</tr>
      	<tr>
      		<td><label for="preco">Preço</label></td>
      		<td><input type="number" id="preco" name="preco" required
      			min="0.01" step="any" size="5" value="${livro.preco}" /></td>
      	</tr>
      	<tr>
      		<td colspan="2" align="center"><input type="submit" value="Salva" /></td>
      	</tr>
   </table>
   ```
   5.4. Atualizar o arquivo **index.jsp**

   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Livraria Virtual</title>
       </head>
       <body>
           <a href="livros">CRUD Livros</a>
       </body>
   </html>
   ```
   
   5.5. Criar o arquivo **erro.jsp**

   ```jsp
   <%@ page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" isErrorPage="true" %>
   <%@ page isELIgnored="false"%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
       "http://www.w3.org/TR/html4/loose.dtd">
   <html>
       <head>
           <title>Erro</title>
       </head>
       <body>
           <center>
               <h1>Erro</h1>
               <h2><%= exception.getMessage()%><br/> </h2>
           </center>	
       </body>
   </html>
   ```



##### (5) Deployment
- - -


6. Criar ou alterar o arquivo **web.xml**

   ```xml
   <!DOCTYPE web-app PUBLIC
    "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd" >
   
   <web-app>
     <display-name>Archetype Created Web Application</display-name>
     <error-page>
       <exception-type>
           javax.servlet.ServletException
       </exception-type>
       <location>/erro.jsp</location>
     </error-page>
   </web-app>
   ```

   

   

   

7. Testar (**mvn clean package tomcat7:deploy**) realizando algumas operações CRUD

   - Criar um novo livro
   - Atualizar um livro criado
   - Visualizar a lista de livros
   - Remover um livro existente

8. Fim



#### Exercícios de fixação

- - -

- Implemente o **CRUD de Editoras**
  - **index.jsp** (para incluir um link para o **CRUD de Editoras**)
  - **EditoraDAO** (inclua os métodos para salvar, atualizar e remover editoras similar ao **LivroDAO**)
  - **EditoraController** (similar ao **LivroController**)
  - **editora/lista.jsp** e **editora/formulário.jsp** – similar a **livro/lista.jsp** e **livro/formulario.jsp**

  
  
- Internacionalize a aplicação (Inglês e Português)
