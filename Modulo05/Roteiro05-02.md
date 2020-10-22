## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 05 - MVC utilizando as tecnologias Servlet, JSP, JSTL & JDBC**

- - -

#### 02 - Autenticação/Autorização

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo05/Login)

---



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 

  - **artifactId**: Login

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**Login**) e executar o seguinte comando: 

      ```sh
      mvn -N io.takari:maven:wrapper
      ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

      

2. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   2.1. Criar novo banco de dados **Login** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/Derby/create.sql**

   ```sql
   connect 'jdbc:derby:Login;create=true;user=root;password=root';
   
   create table Usuario(id bigint not null generated always as identity, nome varchar(256) not null, login varchar(20) not null unique, senha varchar(64) not null, papel varchar(10), CONSTRAINT Usuario_PK PRIMARY KEY (id));
   
   insert into Usuario(nome, login, senha, papel) values ('Administrador', 'admin', 'admin', 'ADMIN');
   
   insert into Usuario(nome, login, senha, papel) values ('Usuario', 'user', 'user', 'USER');
   
   disconnect;
   
   quit;
   ```

   

   

   2.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:Login;create=true;user=root;password=root';
   ij> create table Usuario(id bigint not null generated always as identity, nome varchar(256) not null, login varchar(20) not null unique, senha varchar(64) not null, papel varchar(10), CONSTRAINT Usuario_PK PRIMARY KEY (id));
   0 linhas inseridas/atualizadas/excluídas
   ij> insert into Usuario(nome, login, senha, papel) values ('Administrador', 'admin', 'admin', 'ADMIN');
   1 linha inserida/atualizada/excluída
   ij> insert into Usuario(nome, login, senha, papel) values ('Usuario', 'user', 'user', 'USER');
   1 linha inserida/atualizada/excluída
   ij> disconnect;
   ij> quit;
   ```

   2.3. Iniciar o servidor **Apache Derby**. Em um terminal executar: 

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar $DERBY_HOME/lib/derbyrun.jar server start
   ```

   2.4. Adicionar biblioteca do ***Derby JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
   <dependency>
        <groupId>org.apache.derby</groupId>
        <artifactId>derbyclient</artifactId>
        <version>10.14.2.0</version>
        <scope>runtime</scope>
    </dependency>
   ```

   

   

   

   

   

   

   

   

   

   

   

   

   

3. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

   3.1. Criar novo banco de dados **Login** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/MySQL/create.sql**

   ```sql
   create database Login;
   
   use Login
   
   create table Usuario(id bigint not null auto_increment, nome varchar(256) not null, login varchar(20) not null unique, senha varchar(64) not null, papel varchar(10), primary key (id));
   
   insert into Usuario(nome, login, senha, papel) values ('Administrador', 'admin', 'admin', 'ADMIN');
   
   insert into Usuario(nome, login, senha, papel) values ('Usuario', 'user', 'user', 'USER');
   ```

   

   3.2. Em um terminal no diretório do projeto, executar 

   ```sh
   % mysql -uroot -p
   Enter password: 
   Welcome to the MySQL monitor.  Commands end with ; or \g.
   Your MySQL connection id is 13
   Server version: 8.0.21 MySQL Community Server - GPL
   Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.
   Oracle is a registered trademark of Oracle Corporation and/or its
   affiliates. Other names may be trademarks of their respective
   owners.
   Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
   
   mysql> source db/MySQL/create.sql;
   Query OK, 1 row affected (0.02 sec)
   Database changed
   Query OK, 0 rows affected (0.09 sec)
   Query OK, 1 row affected (0.02 sec)
   Query OK, 1 row affected (0.01 sec)
   mysql> quit
   Bye
   ```

   3.3. Adicionar biblioteca do  ***MySQL JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
   <dependency>
   	<groupId>mysql</groupId>
   	<artifactId>mysql-connector-java</artifactId>
        <version>8.0.21</version>
   	<scope>runtime</scope>
   </dependency>
   ```

   

4. Classes de entidade/domínio **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.domain**

   4.1. Criar a classe **br.ufscar.dc.dsw.domain.Usuario**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   public class Usuario {
   
   	private Long id;
   	private String nome;
   	private String login;
   	private String senha;
   	private String papel;
   	
   	public Usuario(Long id) {
   		this.id = id;
   	}
   	
   	public Usuario(String nome, String login, String senha, String papel) {
   		super();
   		this.nome = nome;
   		this.login = login;
   		this.senha = senha;
   		this.papel = papel;
   	}
   	
   	public Usuario(Long id, String nome, String login, String senha, String papel) {
   		super();
   		this.id = id;
   		this.nome = nome;
   		this.login = login;
   		this.senha = senha;
   		this.papel = papel;
   	}
   	
   	public Long getId() {
   		return id;
   	}	
   	public void setId(Long id) {
   		this.id = id;
   	}
   	
   	public String getNome() {
   		return nome;
   	}
   	public void setNome(String nome) {
   		this.nome = nome;
   	}
   	
   	public String getLogin() {
   		return login;
   	}
   	public void setLogin(String login) {
   		this.login = login;
   	}
   	
   	public String getSenha() {
   		return senha;
   	}
   	public void setSenha(String password) {
   		this.senha = password;
   	}
   	
   	public String getPapel() {
   		return papel;
   	}
   	public void setPapel(String papel) {
   		this.papel = papel;
   	}
   }
   ```



5. Classes DAO (*Data Access Object*) **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.dao**

   5.1. Criar a classe **br.ufscar.dc.dsw.dao.GenericDAO**

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
       	/* Conexão banco de dados Derby
       	 * String url = "jdbc:derby://localhost:1527/Login";
       	 * return DriverManager.getConnection(url, "root", "root");
       	 */
       	
       	/* Conexão banco de dados MySQL */
       	String url = "jdbc:mysql://localhost:3306/Login";
       	return DriverManager.getConnection(url, "root", "root");
       }
   }
   ```

   5.2. Criar a classe **br.ufscar.dc.dsw.dao.UsuarioDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.sql.Connection;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;
   import java.util.ArrayList;
   import java.util.List;
   import br.ufscar.dc.dsw.domain.Usuario;
   
   public class UsuarioDAO extends GenericDAO {
   
       public void insert(Usuario usuario) {    
           String sql = "INSERT INTO Usuario (nome, login, senha, papel) VALUES (?, ?, ?, ?)";
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);;    
               statement = conn.prepareStatement(sql);
               statement.setString(1, usuario.getNome());
               statement.setString(2, usuario.getLogin());
               statement.setString(3, usuario.getSenha());
               statement.setString(4, usuario.getPapel());
               statement.executeUpdate();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
       
       public List<Usuario> getAll() {   
           List<Usuario> listaUsuarios = new ArrayList<>();
           String sql = "SELECT * from Usuario u";
           try {
               Connection conn = this.getConnection();
               Statement statement = conn.createStatement();
               ResultSet resultSet = statement.executeQuery(sql);
               while (resultSet.next()) {
                   long id = resultSet.getLong("id");
                   String nome = resultSet.getString("nome");
                   String login = resultSet.getString("login");
                   String senha = resultSet.getString("senha");
                   String papel = resultSet.getString("papel");
                   Usuario usuario = new Usuario(id, nome, login, senha, papel);
                   listaUsuarios.add(usuario);
               }
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return listaUsuarios;
       }
       
       public void delete(Usuario usuario) {
           String sql = "DELETE FROM Usuario where id = ?";
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
               statement.setLong(1, usuario.getId());
               statement.executeUpdate();
               statement.close();
               conn.close();
           } catch (SQLException e) {
           }
       }
       
       public void update(Usuario usuario) {
           String sql = "UPDATE Usuario SET nome = ?, login = ?, senha = ?, papel = ? WHERE id = ?";
       
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
               statement.setString(1, usuario.getNome());
               statement.setString(2, usuario.getLogin());
               statement.setString(3, usuario.getSenha());
               statement.setString(4, usuario.getPapel());
               statement.executeUpdate();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
       
       public Usuario getbyID(Long id) {
           Usuario usuario = null;
           String sql = "SELECT * from Usuario WHERE id = ?";
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
               statement.setLong(1, id);
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
                   String nome = resultSet.getString("nome");
                   String login = resultSet.getString("login");
                   String senha = resultSet.getString("senha");
                   String papel = resultSet.getString("papel");
                   usuario = new Usuario(id, nome, login, senha, papel);
               }
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return usuario;
       }
       
       public Usuario getbyLogin(String login) {
           Usuario usuario = null;
           String sql = "SELECT * from Usuario WHERE login = ?";
           try {
               Connection conn = this.getConnection();
               PreparedStatement statement = conn.prepareStatement(sql);
               statement.setString(1, login);
               ResultSet resultSet = statement.executeQuery();
               if (resultSet.next()) {
               	Long id = resultSet.getLong("id");
                   String nome = resultSet.getString("nome");
                   String senha = resultSet.getString("senha");
                   String papel = resultSet.getString("papel");
                   usuario = new Usuario(id, nome, login, senha, papel);
               }
               resultSet.close();
               statement.close();
               conn.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return usuario;
       }
   }
   ```

6. Criar a classe utilitária **br.ufscar.dc.dsw.util.Erro**

   ```java
   package br.ufscar.dc.dsw.util;
   
   import java.io.Serializable;
   import java.util.ArrayList;
   import java.util.List;
   
   public final class Erro implements Serializable {
   
   	private static final long serialVersionUID = 1L;
   	private final List<String> erros;
   	
   	public Erro() {
   		erros = new ArrayList<>();
   	}
   	
   	public Erro(String mensagem) {
   		erros = new ArrayList<>();
   		erros.add(mensagem);
   	}
   	
   	public void add(String mensagem) {
   		erros.add(mensagem);
   	}
   	
   	public boolean isExisteErros() {
   		return !erros.isEmpty();
   	}
   	
   	public List<String> getErros() {
   		return erros;
   	}
   }
   ```

7. Criar os controladores **[C do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.controller**

   7.1. Criar a classe **br.ufscar.dc.dsw.controller.IndexController**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.io.IOException;
   import javax.servlet.RequestDispatcher;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import br.ufscar.dc.dsw.dao.UsuarioDAO;
   import br.ufscar.dc.dsw.domain.Usuario;
   import br.ufscar.dc.dsw.util.Erro;
   
   @WebServlet(name = "Index", urlPatterns = { "/index.jsp", "/logout.jsp" })
   public class IndexController extends HttpServlet {
   
   	private static final long serialVersionUID = 1L;
       
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request, response);
       }
   	
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		Erro erros = new Erro();
   		if (request.getParameter("bOK") != null) {
   			String login = request.getParameter("login");
   			String senha = request.getParameter("senha");
   			if (login == null || login.isEmpty()) {
   				erros.add("Login não informado!");
   			}
   			if (senha == null || senha.isEmpty()) {
   				erros.add("Senha não informada!");
   			}
   			if (!erros.isExisteErros()) {
   				UsuarioDAO dao = new UsuarioDAO();
   				Usuario usuario = dao.getbyLogin(login);
   				if (usuario != null) {
   					if (usuario.getSenha().equalsIgnoreCase(senha)) {
   						request.getSession().setAttribute("usuarioLogado", usuario);
   						if (usuario.getPapel().equals("ADMIN")) {
   							response.sendRedirect("admin/");
   						} else {
   							response.sendRedirect("usuario/");
   						}
   						return;
   					} else {
   						erros.add("Senha inválida!");
   					}
   				} else {
   					erros.add("Usuário não encontrado!");
   				}
   			}
   		}
   		request.getSession().invalidate();
   		request.setAttribute("mensagens", erros);
   		String URL = "/login.jsp";
   		RequestDispatcher rd = request.getRequestDispatcher(URL);
   		rd.forward(request, response);
   	}
   }
   ```
   
   7.2 Criar a classe **br.ufscar.dc.dsw.controller.UsuarioController**
   
   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.io.IOException;
   import javax.servlet.RequestDispatcher;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import br.ufscar.dc.dsw.domain.Usuario;
   import br.ufscar.dc.dsw.util.Erro;
   
   @WebServlet(urlPatterns = "/usuario/*")
   public class UsuarioController extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
       
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request, response);
       }
       
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       	
       	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
       	Erro erros = new Erro();    	
       	if (usuario == null) {
       		response.sendRedirect(request.getContextPath());
       	} else if (usuario.getPapel().equals("USER")) {
       		RequestDispatcher dispatcher = request.getRequestDispatcher("/logado/usuario/index.jsp");
               dispatcher.forward(request, response);
       	} else {
       		erros.add("Acesso não autorizado!");
       		erros.add("Apenas Papel [USER] tem acesso a essa página");
       		request.setAttribute("mensagens", erros);
       		RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
       		rd.forward(request, response);
       	}    	
       }
   }
   ```
   
   7.3. Criar a classe **br.ufscar.dc.dsw.controller.AdminController**
   
   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.io.IOException;
   import javax.servlet.RequestDispatcher;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import br.ufscar.dc.dsw.domain.Usuario;
   import br.ufscar.dc.dsw.util.Erro;
   
   @WebServlet(urlPatterns = "/admin/*")
   public class AdminController extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
       
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doGet(request, response);
       }
       
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       	
       	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
       	Erro erros = new Erro();
       	if (usuario == null) {
       		response.sendRedirect(request.getContextPath());
       	} else if (usuario.getPapel().equals("ADMIN")) {
       		RequestDispatcher dispatcher = request.getRequestDispatcher("/logado/admin/index.jsp");
               dispatcher.forward(request, response);
       	} else {
       		erros.add("Acesso não autorizado!");
       		erros.add("Apenas Papel [ADMIN] tem acesso a essa página");
       		request.setAttribute("mensagens", erros);
       		RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
       		rd.forward(request, response);
       	}
       }
   }
   ```
   
   
   
8. Criar as visões (arquivos em **src/main/webapp**) **[V do MVC]**

   8.1. Remova o arquivo **index.jsp** e crie o arquivo **login.jsp**
   
   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Autenticação de Usuário</title>
           <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css"/>
       </head>
       <body>
           <h1>Autenticação de Usuário</h1>
           <c:if test="${mensagens.existeErros}">
               <div id="erro">
                   <ul>
                       <c:forEach var="erro" items="${mensagens.erros}">
                           <li> ${erro} </li>
                       </c:forEach>
                   </ul>
               </div>
           </c:if>
           <form method="post" action="index.jsp">
               <table>
                   <tr>
                       <th>Login: </th>
                       <td><input type="text" name="login"
                                  value="${param.login}"/></td>
                   </tr>
                   <tr>
                       <th>Senha: </th>
                       <td><input type="password" name="senha" /></td>
                   </tr>
                   <tr>
                       <td colspan="2"> 
                           <input type="submit" name="bOK" value="Entrar"/>
                       </td>
                   </tr>
               </table>
           </form>
       </body>
   </html>
   ```
   
   8.2. Crie o arquivo **noAuth.jsp**
   
   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Autorização de Usuário</title>
           <link href="${pageContext.request.contextPath}/layout.css" rel="stylesheet" type="text/css"/>
       </head>
       <body>
           <h1>Autorização de Usuário</h1>
           <c:if test="${mensagens.existeErros}">
               <div id="erro">
                   <ul>
                       <c:forEach var="erro" items="${mensagens.erros}">
                           <li> ${erro} </li>
                           </c:forEach>
                   </ul>
               </div>
           </c:if>
       </body>
   </html>
   ```
   
   8.3 Crie o arquivo **layout.css**
   
   ```css
   #erro {
       width: 80%;
       margin: 0 auto;
       border: 1px solid red;
       background-color: beige;
   }
   ```

   8.4. Crie o arquivo **logado/admin/index.jsp**
   
   ```jsp
   <%@ page contentType="text/html" pageEncoding="UTF-8"%>
   <%@ page isELIgnored="false"%>
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Menu do Sistema</title>
       </head>
       <body>
           <h1>Página do Administrador</h1>
           <p>Olá ${sessionScope.usuarioLogado.nome}</p>
           <ul>
               <li>
                   <a href="${pageContext.request.contextPath}/logout.jsp">Sair</a>
               </li>
           </ul>
       </body>
   </html>
   ```
   
   
   
   
   
   8.5 Crie o arquivo **logado/usuario/index.jsp**
   
   ```jsp
   <%@page contentType="text/html" pageEncoding="UTF-8"%>
   <%@page isELIgnored="false"%>
   
   <!DOCTYPE html>
   <html>
       <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
           <title>Menu do Sistema</title>
       </head>
       <body>
           <h1>Página do Usuário</h1>
           <p>Olá ${sessionScope.usuarioLogado.nome}</p>
           <ul>
               <li>
                   <a href="${pageContext.request.contextPath}/logout.jsp">Sair</a>
               </li>
           </ul>
       </body>
   </html>
   ```
   
9. Abrir um terminal dentro da pasta do projeto e executar os seguintes comandos (note que devido ao uso do Maven Wrapper, não é mais necessário acessar a instalação global do Maven):

   ```sh
   ./mvnw tomcat7:deploy
   ./mvnw tomcat7:redeploy <- automaticamente faz o deploy também
   ./mvnw tomcat7:undeploy
   ```

10. Testar, abrindo o browser no endereço:  http://localhost:8080/Login

    - Tentar entrar sem digitar o login e verificar que uma mensagem de erro é apresentada ( Login não informado! )

    - Tentar entrar apenas digitando o login e verificar que uma mensagem de erro é apresentada ( Senha não informada! )

    - Logar admin/maria e verificar que uma mensagem de erro é apresentada (Senha Inválida !)

    - Logar admin/admin e verificar que abre a página de administrador

      Depois tente acessar a seguinte URL http://localhost:8080/Login/usuario

      Verificar que uma mensagem de erro é apresentada (Acesso não autorizado!)

    - Logar maria/maria e verificar que abre a página de usuário. 

      Depois tente acessar a seguinte URL http://localhost:8080/Login/admin

      Verificar que uma mensagem de erro é apresentada (Acesso não autorizado!)

11. Fim

    



<div style="page-break-after: always"></div>

#### Exercícios de fixação

- - -

- Implemente a autenticação/autorização no CRUD de Livros e Editoras
  - Usuário (papel: ADMIN) acesso ao CRUD editoras
  
  - Usuário (papel: USER) acesso ao CRUD de livros 
  
    

* Baseado no exercício anterior, implemente um sistema de compra de livros com as seguintes funcionalidades:
  * Usuário (papel: ADMIN) acesso ao CRUD editoras
  * Usuário (papel: ADMIN) acesso ao CRUD de livros
  * Usuário (papel: ADMIN) acesso ao CRUD de usuários
  * Usuário (paper: USER) acesso a 2 funcionalidades relacionadas a compra de livros
    * Lista de compras (de livros) do usuário logado
    * Nova compra
