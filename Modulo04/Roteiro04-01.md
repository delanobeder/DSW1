## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 04 - Java Database Connectivity (JDBC)** 

- - -

#### 01 - *Usando JDBC para acesso a banco de dados* 
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo04/Livraria-v1)
- - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: Livraria

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**Livraria**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores


2. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   2.1. Criar novo banco de dados **Livraria** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/Derby/create.sql**

     ```sql
     connect 'jdbc:derby:Livraria;create=true;user=root;password=root';
     
     create table Editora(id bigint not null generated always as identity, cnpj varchar(18) not null, nome varchar(256) not null, constraint Editora_PK primary key (id));
     
     create table Livro(id bigint not null generated always as identity, titulo varchar(256) not null, autor varchar(256) not null, ano integer not null, preco float not null, editora_id bigint not null, constraint Livro_PK primary key (id), constraint Editora_FK foreign key (editora_id) references Editora(id));
     
     insert into Editora(cnpj, nome) values  ('55.789.390/0008-99', 'Companhia das Letras');
     
     insert into Editora(cnpj, nome) values ('71.150.470/0001-40', 'Record');
     
     insert into Editora(cnpj, nome) values ('32.106.536/0001-82', 'Objetiva');
     
     insert into Livro(titulo, autor, ano, preco, editora_id) values ('Ensaio sobre a Cegueira', 'José Saramago', 1995, 54.9, 1);
     
     insert into Livro(titulo, autor, ano, preco, editora_id) values  ('Cem anos de Solidão', 'Gabriel Garcia Márquez', 1977, 59.9, 2);
     
     insert into Livro(titulo, autor, ano, preco, editora_id) values ('Diálogos Impossíveis', 'Luis Fernando Verissimo', 2012, 22.9, 3);
     
     disconnect;
     
     quit;
     ```

   
   
   2.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)
   
   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:Livraria;create=true;user=root;password=root';
   ij> create table Editora(id bigint not null generated always as identity, cnpj varchar(18) not null, nome varchar(256) not null, constraint Editora_PK primary key (id));
   0 linhas inseridas/atualizadas/excluídas
   ij> create table Livro(id bigint not null generated always as identity, titulo varchar(256) not null, autor varchar(256) not null, ano integer not null, preco float not null, editora_id bigint not null, constraint Livro_PK primary key (id), constraint Editora_FK foreign key (editora_id) references Editora(id));
   0 linhas inseridas/atualizadas/excluídas
   ij> insert into Editora(cnpj, nome) values  ('55.789.390/0008-99', 'Companhia das Letras');
   1 linha inserida/atualizada/excluída
   ij> insert into Editora(cnpj, nome) values ('71.150.470/0001-40', 'Record');
   1 linha inserida/atualizada/excluída
   ij> insert into Editora(cnpj, nome) values ('32.106.536/0001-82', 'Objetiva');
   1 linha inserida/atualizada/excluída
   ij> insert into Livro(titulo, autor, ano, preco, editora_id) values ('Ensaio sobre a Cegueira', 'José Saramago', 1995, 54.9, 1);
   1 linha inserida/atualizada/excluída
   ij> insert into Livro(titulo, autor, ano, preco, editora_id) values  ('Cem anos de Solidão', 'Gabriel Garcia Márquez', 1977, 59.9, 2);
   1 linha inserida/atualizada/excluída
   ij> insert into Livro(titulo, autor, ano, preco, editora_id) values ('Diálogos Impossíveis', 'Luis Fernando Verissimo', 2012, 22.9, 3);
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
   
2. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

   3.1. Criar novo banco de dados **Livraria** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/MySQL/create.sql**
   
   ```sql
   create database Livraria;
   
   use Livraria;
   
   create table Editora(id bigint not null auto_increment, cnpj varchar(18) not null, nome varchar(256) not null, primary key (id));
   
   create table Livro(id bigint not null auto_increment, titulo varchar(256) not null, autor varchar(256) not null, ano integer not null, preco float not null, editora_id bigint not null, primary key (id), foreign key (editora_id) references Editora(id));
   
   insert into Editora(cnpj, nome) values  ('55.789.390/0008-99', 'Companhia das Letras');
   
   insert into Editora(cnpj, nome) values ('71.150.470/0001-40', 'Record');
   
   insert into Editora(cnpj, nome) values ('32.106.536/0001-82', 'Objetiva');
   
   insert into Livro(titulo, autor, ano, preco, editora_id) values ('Ensaio sobre a Cegueira', 'José Saramago', 1995, 54.9, 1);
   
   insert into Livro(titulo, autor, ano, preco, editora_id) values  ('Cem anos de Solidão', 'Gabriel Garcia Márquez', 1977, 59.9, 2);
   
   insert into Livro(titulo, autor, ano, preco, editora_id) values ('Diálogos Impossíveis', 'Luis Fernando Verissimo', 2012, 22.9, 3);
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
   Query OK, 1 row affected (0.02 sec)
   Database changed
   Query OK, 0 rows affected (0.07 sec)
   Query OK, 0 rows affected (0.11 sec)
   Query OK, 1 row affected (0.01 sec)
   Query OK, 1 row affected (0.01 sec)
   Query OK, 1 row affected (0.02 sec)
   Query OK, 1 row affected (0.01 sec)
   Query OK, 1 row affected (0.01 sec)
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
   
4. Criar a classe **br.ufscar.dc.dsw.AcessaBD**

   ```java
   package br.ufscar.dc.dsw;
   
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;
   
   public class AcessaBD {
   
   	public static void main(String[] args) {
   		try {
   
   			/* Setup para uso do banco de dados MySQL */
   			
   			Class.forName("com.mysql.cj.jdbc.Driver");
               String url = "jdbc:mysql://localhost:3306/Livraria";
   			Connection con = (Connection) DriverManager.getConnection(url,
   					"root", "root");
   
   			/* Setup para uso do banco de dados Apache Derby */
   
   			/*
   			 * Class.forName("org.apache.derby.jdbc.ClientDriver");
                * String url = "jdbc:derby://localhost:1527/Livraria";
   			 * Connection con = (Connection) DriverManager.getConnection(url, 
   			 *         "root", "root");
   			 */
   
   			Statement stmt = con.createStatement();
   			ResultSet rs = stmt.executeQuery("select * from Livro");
   			while (rs.next()) {
   				System.out.print(rs.getString("Titulo"));
   				System.out.print(", " + rs.getString("Autor"));
   				System.out.print(", " + rs.getInt("Ano"));
   				System.out.println(" (R$ " + rs.getFloat("Preco") + ")");
   			}
   			stmt.close();
   			con.close();
   		} catch (ClassNotFoundException e) {
   			System.out.println("A classe do driver de conexão não foi encontrada!");
   		} catch (SQLException e) {
   			System.out.println("O comando SQL não pode ser executado!");
   		}
   	}
   }
   ```

5. Compilar e executar

   ```sh
   % mvn compile
   % mvn exec:java -Dexec.mainClass="br.ufscar.dc.dsw.AcessaBD" -Dexec.cleanupDaemonThreads=false
   ```

   Verificar que os dados presentes na tabela Livro são apresentados no console.

   ```
   Ensaio sobre a Cegueira, José Saramago, 1995 (R$ 54.9)
   Cem anos de Solidão, Gabriel Garcia Márquez, 1977 (R$ 59.9)
   Diálogos Impossíveis, Luis Fernando Verissimo, 2012 (R$ 22.9)
   ```

6. Fim

<div style="page-break-after: always"></div>


#### Leituras adicionais
- - -

- JDBC tutorial

  https://www.devmedia.com.br/jdbc-tutorial/6638

  

- Lesson: JDBC Basics

  https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html

  

- Java JDBC Tutorial

  https://www.javatpoint.com/java-jdbc
