## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 05 - MVC utilizando as tecnologias Servlet, JSP, JSTL & JDBC**

- - -

#### 03 - AJAX
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo05/AJAX)
- - -


1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 

  - **artifactId**: AJAX

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**AJAX**) e executar o seguinte comando: 

      ```sh
      mvn -N io.takari:maven:wrapper
      ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

      

2. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   2.1. Criar novo banco de dados **Cidades** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/Derby/create.sql**

   ```sql
   connect 'jdbc:derby:Cidades;create=true;user=root;password=root';
   
   create table Estado (id bigint not null generated always as identity, nome varchar(30) not null, sigla varchar(2) not null, 
   constraint Estado_PK primary key (id));
   
   create table Cidade (id bigint not null generated always as identity, nome varchar(80) not null, estado_id bigint constraint FK_CIDADE references ESTADO, constraint Cidade_PK primary key (id));
   
   disconnect;
   
   quit;
   ```
   <div style="page-break-after: always"></div>
   
   2.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:Cidades;create=true;user=root;password=root';
   ij> create table Estado (id bigint not null generated always as identity, nome varchar(30) not null, sigla varchar(2) not null, 
   constraint Estado_PK primary key (id));
   0 linhas inseridas/atualizadas/excluídas
   ij> create table Cidade (id bigint not null generated always as identity, nome varchar(80) not null, estado_id bigint constraint FK_CIDADE references ESTADO, constraint Cidade_PK primary key (id));
   0 linhas inseridas/atualizadas/excluídas
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

   3.1. Criar novo banco de dados **Cidades** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/MySQL/create.sql**

   ```sql
   create database Cidades;
   
   use Cidades;
   
   create table Estado (id bigint not null auto_increment, nome varchar(30) not null, sigla varchar(2) not null, primary key (id));
   
   create table Cidade (id bigint not null auto_increment, nome varchar(80) not null, estado_id bigint not null, primary key (id), foreign key (estado_id) references Estado(id));
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
   
   mysql> source db/MySQL/create.sql
   Query OK, 1 row affected (0.01 sec)
   Database changed
   Query OK, 0 rows affected (0.08 sec)
   Query OK, 0 rows affected (0.06 sec)
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
   
4. Adicionar biblioteca **GSON** via Maven, adicionando as seguintes linhas ao arquivo pom.xml:

   ```xml
   <dependency>
   	<groupId>com.google.code.gson</groupId>
   	<artifactId>gson</artifactId>
   	<version>2.8.0</version>
   </dependency>
   ```

   

   

   

   

   

   

   

   

   

   

5. Classes de entidade/domínio **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.domain**

   5.1. Criar a classe **br.ufscar.dc.dsw.domain.Cidade**

   ```java
   package br.ufscar.dc.dsw.domain;
   public class Cidade {
       private int id;
       private String nome;
       private Estado estado;
       
       public Cidade() {
       }
       
       public Cidade(int id, String nome, Estado estado) {
           this.id = id;
           this.nome = nome;
           this.estado = estado;
       }
       
       public int getId() {
           return id;
       }
       public void setId(int id) {
           this.id = id;
       }
       
       public String getNome() {
           return nome;
       }
       public void setNome(String nome) {
           this.nome = nome;
       }
       
       public Estado getEstado() {
           return estado;
       }
       public void setEstado(Estado estado) {
           this.estado = estado;
       }
       
       @Override
       public String toString() {
           return nome + "/" + estado.getSigla();
       }
   }
   ```
   <div style="page-break-after: always"></div>
   5.2. Criar a classe **br.ufscar.dc.dsw.domain.Estado**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   public class Estado {
       private int id;
       private String sigla;
       private String nome;
       
       public Estado() {
       }
       
       public Estado(int id, String sigla, String nome) {
           this.id = id;
           this.sigla = sigla;
           this.nome = nome;
       }
       
       public int getId() {
           return id;
       }
       public void setId(int id) {
           this.id = id;
       }
       
       public String getSigla() {
           return sigla;
       }
       public void setSigla(String sigla) {
           this.sigla = sigla;
       }
       
       public String getNome() {
           return nome;
       }
       public void setNome(String nome) {
           this.nome = nome;
       }
       
       @Override
       public String toString() {
           return nome + " (" + sigla + ")";
       }
    }
   ```
<div style="page-break-after: always"></div>

6. Classes DAO (*Data Access Object*) **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.dao**

   6.1. Criar a classe **br.ufscar.dc.dsw.dao.GenericDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.util.List;
   
   public abstract class GenericDAO<T> {
   
       protected Connection connection;
   
       public GenericDAO() {
           try {
           	
               /* Setup para uso do banco de dados MySQL */
               
           	Class.forName("com.mysql.cj.jdbc.Driver");
               String url = "jdbc:mysql://localhost:3306/Cidades";
               connection = DriverManager.getConnection(url, "root", "root");
               
               /* Setup para uso do banco de dados Derby */
               
               /*
   			 * Class.forName("org.apache.derby.jdbc.ClientDriver");
                * String url = "jdbc:derby://localhost:1527/Cidades"
   			 * connection = DriverManager.getConnection(url, "root", "root");
   			 */            
           } catch (Exception e) {
               e.printStackTrace();
           }
       }    
       
       abstract public void save(T t);
       abstract List<T> getAll();
   }
   ```

   6.2. Criar a classe **br.ufscar.dc.dsw.dao.CidadeDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import br.ufscar.dc.dsw.domain.Cidade;
   import br.ufscar.dc.dsw.domain.Estado;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.Statement;
   import java.util.ArrayList;
   import java.util.List;
   
   public class CidadeDAO extends GenericDAO<Cidade> {
   
       private final static String SQL_SAVE = "insert into Cidade"
               + " (nome, estado_id) values (?,?)";
       
       private final static String SQL_SELECT_ESTADO = "select id, nome"
               + " from Cidade as c where c.estado_id = ? order by id";
       
       private final static String SQL_SELECT_NOME = "select c.id, c.nome,"
               + " e.id, e.sigla, e.nome from Cidade as c, Estado as e"
               + " where UPPER(c.nome) like ? and c.estado_id = e.id"
               + " order by c.nome";
       
       private final static String SQL_SELECT_ALL = "select c.id, c.nome,"
               + " e.id, e.sigla, e.nome from Cidade as c, Estado as e"
               + " where c.estado_id = e.id order by c.id";
       
       @Override
       public void save(Cidade cidade) {
           try {
               PreparedStatement ps = this.connection.prepareStatement(SQL_SAVE,
                       Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, cidade.getNome());
               ps.setInt(2, cidade.getEstado().getId());
               ps.execute();
       
               ResultSet rs = ps.getGeneratedKeys();
               rs.next();
               cidade.setId(rs.getInt(1));
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       
       @Override
       public List<Cidade> getAll() {   
           List<Cidade> lista = new ArrayList<>();
           try {
               Statement stmt = this.connection.createStatement();
               ResultSet res = stmt.executeQuery(SQL_SELECT_ALL);
       
               while (res.next()) {
                   int id = res.getInt(3);
                   String sigla = res.getString(4);
                   String nome = res.getString(5);
                   Estado estado = new Estado(id, sigla, nome);
                   id = res.getInt(1);
                   nome = res.getString(2);
                   Cidade cidade = new Cidade(id, nome, estado);
                   lista.add(cidade);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return lista;
       }
       
       public List<Cidade> getByEstado(Estado estado) {
           List<Cidade> lista = new ArrayList<>();
       
           try {
               PreparedStatement stmt;
               stmt = this.connection.prepareStatement(SQL_SELECT_ESTADO);
               stmt.setInt(1, estado.getId());
               ResultSet res = stmt.executeQuery();
       
               while (res.next()) {
                   int id = res.getInt(1);
                   String nome = res.getString(2);
                   lista.add(new Cidade(id, nome, estado));
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return lista;
       }
       
       public List<Cidade> getByNome(String s) {
           List<Cidade> lista = new ArrayList<>();
       
           try {
               PreparedStatement stmt;
               stmt = this.connection.prepareStatement(SQL_SELECT_NOME);
               stmt.setString(1, "%"+ s.toUpperCase() + "%");
               ResultSet res = stmt.executeQuery();
       
               while (res.next()) {
                   int id = res.getInt(3);
                   String sigla = res.getString(4);
                   String nome = res.getString(5);
                   Estado estado = new Estado(id, sigla, nome);
                   id = res.getInt(1);
                   nome = res.getString(2);
                   Cidade cidade = new Cidade(id, nome, estado);
       
                   lista.add(cidade);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       
           return lista;
       }
   }
   ```
   6.3. Criar a classe **br.ufscar.dc.dsw.dao.EstadoDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import br.ufscar.dc.dsw.domain.Estado;
   import java.sql.PreparedStatement;
   import java.sql.ResultSet;
   import java.sql.Statement;
   import java.util.ArrayList;
   import java.util.List;
   
   public class EstadoDAO extends GenericDAO<Estado> {
   
       private final static String CRIAR_SQL = "insert into Estado"
               + " (nome, sigla) values (?,?)";
       
       private final static String SELECT_SQL = "select id, nome, sigla "
               + " from Estado order by id";
               
       private final static String SELECT_SIGLA_SQL = "select id, nome "
               + " from Estado e where e.sigla = ?";
               
       @Override
       public void save(Estado estado) {
       
           try {
               PreparedStatement ps = this.connection.prepareStatement(CRIAR_SQL,
                       Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, estado.getNome());
               ps.setString(2, estado.getSigla());
               ps.execute();
       
               ResultSet rs = ps.getGeneratedKeys();
               rs.next();
               estado.setId(rs.getInt(1));
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       
       @Override
       public List<Estado> getAll() {
       
           List<Estado> lista = new ArrayList<>();
       
           try {
               Statement stmt = this.connection.createStatement();
               ResultSet res = stmt.executeQuery(SELECT_SQL);
       
               while (res.next()) {
                   int id = res.getInt(1);
                   String nome = res.getString(2);
                   String sigla = res.getString(3);
                   lista.add(new Estado(id, sigla, nome));
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return lista;
       }
           
       public Estado getBySigla(String sigla) {
       
           Estado estado = null;
       
           try {
               PreparedStatement stmt;
               stmt = this.connection.prepareStatement(SELECT_SIGLA_SQL);
               stmt.setString(1, sigla);
               ResultSet res = stmt.executeQuery();
       
               if (res.next()) {
                   int id = res.getInt(1);
                   String nome = res.getString(2);
                   estado = new Estado(id, sigla, nome);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return estado;
       }
   }
   ```

   6.4. Criar a classe **br.ufscar.dc.dsw.dao.PopulaBD**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import br.ufscar.dc.dsw.domain.Cidade;
   import br.ufscar.dc.dsw.domain.Estado;
   import java.io.BufferedReader;
   import java.io.FileInputStream;
   import java.io.InputStream;
   import java.io.InputStreamReader;
   import java.util.HashMap;
   import java.util.Map;
   import java.util.StringTokenizer;
   
   public class PopulaBD {
   
   	static Map<String, Estado> map = new HashMap<>();
   	
   	private static void populaEstados() {
   		String line;
   		try {
   			InputStream stream = new FileInputStream("./estados.txt");
   			InputStreamReader isr = new InputStreamReader(stream);
   			BufferedReader reader = new BufferedReader(isr);
   			line = reader.readLine();
   	
   			EstadoDAO dao = new EstadoDAO();
   			while (line != null) {
   				StringTokenizer tokenizer = new StringTokenizer(line, ",");
   	
   				Estado estado = new Estado();
   				String sigla = tokenizer.nextToken();
   				estado.setSigla(sigla);
   				estado.setNome(tokenizer.nextToken());
   				dao.save(estado);
   	
   				map.put(sigla, estado);
   	
   				System.out.println("Salvo: " + estado);
   	
   				line = reader.readLine();
   			}
   			stream.close();
   			isr.close();
   			reader.close();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   	}
   	
   	private static void populaCidades() {
   	
   		String line;
   		String nome;
   		Estado estado;
   		String sigla;
   	
   		try {
   			InputStream stream = new FileInputStream("./cidades.txt");
   			InputStreamReader isr = new InputStreamReader(stream);
   			BufferedReader reader = new BufferedReader(isr);
   	
   			line = reader.readLine();
   			CidadeDAO dao = new CidadeDAO();
   			while (line != null) {
   				StringTokenizer tokenizer = new StringTokenizer(line, ",");
   				sigla = tokenizer.nextToken();
   				sigla = sigla.substring(1, 3);
   				nome = tokenizer.nextToken();
   				nome = nome.substring(1, nome.length() - 1);
   				estado = map.get(sigla);
   				Cidade cidade = new Cidade();
   				cidade.setNome(nome);
   				cidade.setEstado(estado);
   				dao.save(cidade);
   				System.out.println("Salvo: " + cidade);
   				line = reader.readLine();
   			}
   			stream.close();
   			isr.close();
   			reader.close();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   	}
   	
   	public static void main(String[] args) {
   		populaEstados();
   		populaCidades();
   	}
   }
   ```

7. Popular o banco de dados

     7.1. Baixar os arquivos **cidades.txt** e **estado.txt**

     7.2. Abrir um terminal dentro da pasta do projeto (**AJAX**) e executar o seguinte comando: 

     ```sh
     mvn clean package 
     mvn exec:java -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="br.ufscar.dc.dsw.dao.PopulaBD" 
     ```

8. Criar as classes de *beans* **[M do MVC]**

     Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.bean**

     8.1. Criar a classe br.ufscar.dc.dsw.bean.BuscaPorEstadoBean

     ```java
     package br.ufscar.dc.dsw.bean;
     
     import br.ufscar.dc.dsw.dao.CidadeDAO;
     import br.ufscar.dc.dsw.dao.EstadoDAO;
     import br.ufscar.dc.dsw.domain.Cidade;
     import br.ufscar.dc.dsw.domain.Estado;
     import java.util.List;
     
     public class BuscaPorEstadoBean {
     
         public List<Estado> getEstados() {
             EstadoDAO dao = new EstadoDAO();
             return dao.getAll();
         }
              
         public List<Cidade> getCidades(String sigla) {
             EstadoDAO dao = new EstadoDAO();
             Estado estado = dao.getBySigla(sigla);
             return new CidadeDAO().getByEstado(estado);
         } 
     }
     ```
     8.2 Criar a classe **br.ufscar.dc.dsw.bean.BuscaPorNomeBean**

     ```java
     package br.ufscar.dc.dsw.bean;
     
     import br.ufscar.dc.dsw.dao.CidadeDAO;
     import br.ufscar.dc.dsw.domain.Cidade;
     import java.util.List;
     
     public class BuscaPorNomeBean {
     
     	public List<Cidade> getCidades() {
     		CidadeDAO dao = new CidadeDAO();
     		return dao.getAll();
     	}
     	
     	public List<Cidade> getCidades(String nome) {
     		CidadeDAO dao = new CidadeDAO();
     		List<Cidade> lista;
     		if (nome.length() > 0) {
     			lista = dao.getByNome(nome);
     		} else {
     			lista = dao.getAll();
     		}
     		return lista;
     	}
     }
     ```


<div style="page-break-after: always"></div>

9. Criar os controladores **[C do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.controller**

   9.1. Criar a classe **br.ufscar.dc.dsw.controller.EstadoController**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import br.ufscar.dc.dsw.bean.BuscaPorEstadoBean;
   import br.ufscar.dc.dsw.domain.Cidade;
   import java.io.IOException;
   import java.util.List;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/buscaPorEstado"})
   public class EstadoController extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
   
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
           response.setContentType("text/html;charset=UTF-8");
           String sigla = request.getParameter("estado");
           String buffer = "<tr><td>Cidade</td><td><select id='cidade' name='cidade' "
                   + "onchange='apresentaDS()'>";
           buffer = buffer + "<option value=''>Selecione</option>";
           List<Cidade> cidades = new BuscaPorEstadoBean().getCidades(sigla);
           for (Cidade cidade : cidades) {
               buffer = buffer + "<option value='" + cidade.getNome() + "'>" + cidade.getNome() + "</option>";
           }
           buffer = buffer + "</select></td>";
           
           response.getWriter().println(buffer);
       }
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

   

   

   9.2. Criar a classe **br.ufscar.dc.dsw.controller.NomeController**

   ```java
   package br.ufscar.dc.dsw.controller;
   
   import br.ufscar.dc.dsw.bean.BuscaPorNomeBean;
   import br.ufscar.dc.dsw.domain.Cidade;
   import com.google.gson.Gson;
   import com.google.gson.GsonBuilder;
   import java.io.IOException;
   import java.util.ArrayList;
   import java.util.List;
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   
   @WebServlet(urlPatterns = {"/buscaPorNome"})
   public class NomeController extends HttpServlet {
   
       private static final long serialVersionUID = 1L;
   
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
   
           String nome = request.getParameter("term");
   
           Gson gsonBuilder = new GsonBuilder().create();
           List<String> cidades = new ArrayList<>();
           for (Cidade cidade : new BuscaPorNomeBean().getCidades(nome)) {
               cidades.add(cidade.toString());
           }
   
           response.getWriter().write(gsonBuilder.toJson(cidades));
       }
   
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
           processRequest(request, response);
       }
   }
   ```

   

   

   

10. Criar as visões (arquivos em **src/main/webapp**) **[V do MVC]**

    10.1. Atualize o arquivo **index.jsp**

    ```jsp
    <html>
    <head>
        <title>AJAX</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        Busca por estado: <a href="buscaEstado.jsp">clique aqui</a> (Dynamic select) 
        <br/><br/>
        Busca por nome: <a href="buscaNome.jsp">clique aqui</a> (Autocomplete) 
        <br/><br/>
        Tabela Dinâmica: <a href="tabelaDinamica.jsp">clique aqui</a> (filtro por nome)
        <br/><br/>
    </body>
    </html>
    ```

    

    10.2. Crie o arquivo **buscaEstado.jsp**

    ```jsp
    <%@ page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html>
     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>AJAX (dynamic select)</title>
         <script src="js/ajaxEstado.js"></script>
     </head>
     <body>
         <br/>
         <jsp:useBean id='bean' class='br.ufscar.dc.dsw.bean.BuscaPorEstadoBean'/>
    
         <form name='form'>
             <table>
                 <tr>
                     <td>Estado</td>
                     <td>
                        <select id = 'estado' onchange='estadoSelecionado(this.value)' name='estado'>
                         <option value='--'>--</option>
                          <c:forEach items='${bean.estados}' var='estado'>
                            <option value='${estado.sigla}'>${estado.sigla}</option>
                          </c:forEach>
                         </select>   
                     </td>
                 </tr>
                 <tr id='cidades'>    
                     <td>
                         Cidade
                     </td>
                     <td>
                         <select id='cidade' name='cidade' onchange='apresentaDS()'>
                         </select>
                     </td>   
                 </tr>
             </table>
    </form>
        <br/>
    <a href="index.jsp">Voltar</a>
     </body>
    </html>
    ```

    

    10.3 Crie o arquivo **js/ajaxEstado.js**

    ```javascript
    var xmlHttp;
        
    function apresentaDS() {
        var cidade = document.getElementById("cidade");
        var estado = document.getElementById("estado");
        var selCidade = cidade.options[cidade.selectedIndex].value; 
        var selEstado = estado.options[estado.selectedIndex].value;
        console.log(selCidade + "/" + selEstado);
        alert("Selecionado: " + selCidade + "/" + selEstado); 
    }
        
    function estadoSelecionado(str) {
        if (typeof XMLHttpRequest !== "undefined") {
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        
        if (xmlHttp === null) {
            alert("Browser does not support XMLHTTP Request");
            return;
        }
            
        var url = "buscaPorEstado";
        url += "?estado=" + str;
        xmlHttp.onreadystatechange = atualizaCidades;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
    }
        
    function atualizaCidades() {
        if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
            document.getElementById("cidades").innerHTML = xmlHttp.responseText;
        }
    }
    ```
    10.4. Crie o arquivo **buscaNome.jsp**

    ```jsp
    <!DOCTYPE html>
    <html>
    <head>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script>
            $(function () {
                $("#cidade").autocomplete({
                    select: function (event, ui) {
                        alert("Selecionado: " + ui.item.value);
                    },
                    source: "buscaPorNome",
                    minLength: 2
                });
            });
        </script>
        <title>AJAX (Autocomplete)</title>
    </head>
    <body>
        <div class="ui-widget">
           <label for="cidade">Nome</label>
           <input id="cidade" name="cidade" placeholder="Pelo menos 2 caracteres">
        </div>
        <br/>
        <a href="index.jsp">Voltar</a>
    </body>
    </html>
    ```

    10.4. Crie o arquivo **tabelaDinamica.jsp**

    ```jsp
    <%@ page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
    <html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AJAX (dynamic table)</title>
    <script src="js/ajaxNome.js"></script>
    </head>
    <body>
    	<br />
    	<jsp:useBean id='bean' class='br.ufscar.dc.dsw.bean.BuscaPorNomeBean' />
    
    	<form name='form'>
    		<div align="center">
    			<p>Lista de Cidades</p>
    			<label for="cidade">Nome</label> <input id="cidade" name="cidade"
    				onkeyUp="getCidades()">
    			<div id="cidades">
    
    				<p>Quantidade: ${fn:length(bean.cidades)}</p>
    				<table border="1" style="width: 400px; border: 1px solid black">
    
    					<tr>
    						<th style="width: 10%; text-align: center"></th>
    						<th style="width: 70%;">Nome</th>
    						<th style="width: 20%; text-align: center">Estado</th>
    					</tr>
    					<c:forEach var="cidade" items="${bean.cidades}">
    						<tr>
    							<td style="width: 10%; text-align: center"><input
    								type="radio" id="${cidade.nome}/${cidade.estado.sigla}"
    								name="selCidade" value="${cidade.nome}/${cidade.estado.sigla}"
    								onclick="alert('Selecionado: ${cidade.nome}/${cidade.estado.sigla}')">
    							</td>
    							<td>${cidade.nome}</td>
    							<td style="text-align: center">${cidade.estado.sigla}</td>
    						</tr>
    					</c:forEach>
    				</table>
    			</div>
    		</div>
    	</form>
    	<br />
    	<a href="index.jsp">Voltar</a>
    </body>
    </html>
    ```

    10.5. Crie o arquivo **js/ajaxNome.js**

    ```javascript
    var xmlHttp;
    
    function apresenta(selCidade){
    	alert("Selecionado: " + selCidade.target.value);	
    }
    
    function getCidades() {
    	var cidade = document.getElementById("cidade");
    	var nome = cidade.value;
    
    	if (typeof XMLHttpRequest !== "undefined") {
    		xmlHttp = new XMLHttpRequest();
    	} else if (window.ActiveXObject) {
    		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}
    
    	if (xmlHttp === null) {
    		alert("Browser does not support XMLHTTP Request");
    		return;
    	}
    
    	var url = "buscaPorNome";
    	url += "?term=" + nome;
    	xmlHttp.onreadystatechange = atualizaTabelaCidades;
    	xmlHttp.open("GET", url, true);
    	xmlHttp.send(null);
    
    }
    
    function atualizaTabelaCidades() {
    	if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
    
    		var cidades = JSON.parse(xmlHttp.responseText);
    
    		// CRIA UMA TABELA DINAMICA
    
    		var table = document.createElement("table");
    		table.border = "1";
    		table.style.border = "1px solid black";
    		table.style.width = "400px";
    
    		// CRIA LINHA TABELA (LINHA CABECALHO).
    
    		var tr = table.insertRow(-1);
    
    		// CRIA COLUNA NA LINHA DE CABECALHO
    		var thSel = document.createElement('th');
    		thSel.innerHTML = '';
    		thSel.style.width = "10%";
    		tr.appendChild(thSel);
    
    		// CRIA COLUNA NA LINHA DE CABECALHO
    		var thNome = document.createElement('th');
    		thNome.innerHTML = 'Nome';
    		thNome.style.width = "70%";
    		tr.appendChild(thNome);
    
    		// CRIA COLUNA NA LINHA DE CABECALHO
    		var thEstado = document.createElement('th');
    		thEstado.innerHTML = 'Estado';
    		thEstado.style.width = "20%";
    		tr.appendChild(thEstado);
    
    		// CRIA DEMAIS LINHAS COM OS VALORES
    
    		for (var i = 0; i < cidades.length; i++) {
    
    			// CRIA NOVA LINHA
    			tr = table.insertRow(-1);
    
    			var tmp = cidades[i];
    			var indice = tmp.indexOf("/");
    			var cidade = tmp.slice(0, indice);
    			var estado = tmp.slice(indice + 1);
    
    			// CRIA COLUNA 1 NA LINHA
    
    			var col1 = tr.insertCell(-1);
    			var radio = document.createElement('input');
    			radio.type = 'radio';
    			radio.id = cidade + "/" + estado;
    			radio.name = 'selCidade';
    			radio.value = cidade + "/" + estado;
    			radio.onclick = apresenta.bind(radio.value);
    			
    			col1.appendChild(radio);
    			// col1.style = "text-align:center"; analogo ao comando abaixo
    			col1.style.textAlign = "center";
    
    			// CRIA COLUNA 2 NA LINHA
    
    			var col2 = tr.insertCell(-1);
    			col2.innerHTML = cidade;
    
    			// CRIA COLUNA 3 NA LINHA
    
    			var col3 = tr.insertCell(-1);
    			// col3.style = "text-align:center"; analogo ao comando abaixo
    			col3.style.textAlign = "center";
    			col3.innerHTML = estado;
    		}
    
    		var divContainer = document.getElementById("cidades");
    		divContainer.innerHTML = "";
    
    		// CRIA UM PARAGRAFO (TAG P) COM A QUANTIDADE DE CIDADES
    
    		var p = document.createElement('p');
    		p.innerHTML = 'Quantidade: ' + cidades.length;
    
    		// ADICIONA O PARAGRAFO AO CONTAINER.
    		divContainer.appendChild(p);
    
    		// ADICIONA A NOVA TABELA AO CONTAINER.
    		divContainer.appendChild(table);
    	}
    }
    ```



11. Abrir um terminal dentro da pasta do projeto e executar os seguintes comandos (note que devido ao uso do Maven Wrapper, não é mais necessário acessar a instalação global do Maven):

    ```sh
    ./mvnw tomcat7:deploy
    ./mvnw tomcat7:redeploy <- automaticamente faz o deploy também
    ./mvnw tomcat7:undeploy
    ```

12. Testar, abrindo o browser no endereço: 
    http://localhost:8080/AJAX

13. Fim



#### Leituras adicionais

- - -

- AJAX Tutorial

  https://www.w3schools.com/xml/ajax_intro.asp

  

- jQuery - AJAX Introduction
  
  https://www.w3schools.com/jquery/jquery_ajax_intro.asp
