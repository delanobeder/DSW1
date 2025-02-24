## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 07 - Java Persistence API (JPA)** 

- - -

#### 03 - *Usando JPA para acesso a banco de dados* - LivrariaJPA
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo07/LivrariaJPA)
- - -

1. Criar um novo projeto Spring (https://start.spring.io/)
	
	- **Project:** Maven Project
	
	- **Language:** Java
	
	- **Spring Boot:** 3.4.3
	
	- **Group:** br.ufscar.dc.dsw
	
	- **Artifact:** LivrariaJPA
	
	- **Name:** LivrariaJPA
	
	- **Description:** LivrariaJPA
	
	- **Package name:** br.ufscar.dc.dsw
	
	- **Packaging:** Jar
	
	- **Java:** 17
	
	  **Dependências:** Spring Web, Spring Data JPA e Spring Boot DevTools
	
2. Baixar o arquivo .zip e descompactar em um diretório (**LivrariaJPA**)

3. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 4). Utilizaremos o banco de dados criado no **Roteiro 04-01 (JDBC)**.

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
   ```

   4.2. No arquivo **pom.xml**, adicionaremos a biblioteca do ***MySQL JDBC Driver*** como dependência do projeto

   ```xml
   <dependency>
   	<groupId>mysql</groupId>
   	<artifactId>mysql-connector-java</artifactId>
   	<scope>runtime</scope>
   </dependency>
   ```

   

5. Criando as classes de Domínio (pacote br.ufscar.dc.dsw.domain)

   5.1. Classe **br.ufscar.dc.dsw.domain.Livro**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.math.BigDecimal;
   import jakarta.persistence.Column;
   import jakarta.persistence.Entity;
   import jakarta.persistence.GeneratedValue;
   import jakarta.persistence.GenerationType;
   import jakarta.persistence.Id;
   import jakarta.persistence.JoinColumn;
   import jakarta.persistence.ManyToOne;
   import jakarta.persistence.Table;
   import org.springframework.format.annotation.NumberFormat;
   import org.springframework.format.annotation.NumberFormat.Style;
   
   @Entity
   @Table(name = "Livro")
   public class Livro {
   
   	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   	private Long id;
   	
   	@Column(nullable = false, unique = true, length = 60)
   	private String titulo;
   	
   	@Column(nullable = false, unique = true, length = 60)
   	private String autor;
   	
   	@Column(nullable = false, length = 5)
   	private Integer ano;
   	
   	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
   	@Column(nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.0")
   	private BigDecimal preco;
   	
   	@ManyToOne
   	@JoinColumn(name = "editora_id")
   	private Editora editora;
   	
   	public Long getId() {
   		return id;
   	}
   	public void setId(Long id) {
   		this.id = id;
   	}
   	
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
   		Livro other = (Livro) obj;
   		if (id == null) {
   			if (other.id != null) return false;
   		} else if (!id.equals(other.id)) return false;
   		return true;
   	}
   	
   	@Override
   	public String toString() {
   		StringBuffer sb = new StringBuffer();
   		sb.append("[");
   		sb.append("Título: " + titulo + ", ");
   		sb.append("Autor: " + autor + ", ");
   		sb.append("Ano: " + ano + ", ");
   		sb.append("Preço: " + preco + ", ");
   		sb.append("Editora: " + editora);
   		sb.append("]");
   		return sb.toString(); 
   	}
   }
   ```

   5.2. Classe **br.ufscar.dc.dsw.domain.Editora**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.List;
   import jakarta.persistence.Column;
   import jakarta.persistence.Entity;
   import jakarta.persistence.GeneratedValue;
   import jakarta.persistence.GenerationType;
   import jakarta.persistence.Id;
   import jakarta.persistence.OneToMany;
   import jakarta.persistence.Table;
   
   @Entity
   @Table(name = "Editora")
   public class Editora {
   
   	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   	private Long id;
   	
   	@Column(nullable = false, unique = true, length = 60)
   	private String CNPJ;
   	
   	@Column(nullable = false, unique = true, length = 60)
   	private String nome;
   	
   	@OneToMany(mappedBy = "editora")
   	private List<Livro> livros;
   	
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
   	
   	public List<Livro> getLivros() {
   		return livros;
   	}	
   	public void setLivros(List<Livro> livros) {
   		this.livros = livros;
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
   		Editora other = (Editora) obj;
   		if (id == null) {
   			if (other.id != null) return false;
   		} else if (!id.equals(other.id)) return false;
   		return true;
   	}
   	
   	@Override
   	public String toString() {
   		StringBuffer sb = new StringBuffer();
   		sb.append("[");
   		sb.append("CNPJ: " + CNPJ + ", ");
   		sb.append("Nome: " + nome);
   		sb.append("]");
   		return sb.toString(); 
   	}
   }
   ```



6. Criar as interfaces de *Data Access Object* (pacote **br.ufscar.dc.dsw.dao**)

   6.1. Interface **br.ufscar.dc.dsw.dao.ILivroDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Livro;
   
   public interface ILivroDAO extends CrudRepository<Livro, Long>{
   	List<Livro> findAll();
   	Livro findById(long id);
   }
   ```

7. Classe principal (Spring Boot) - Classe **br.ufscar.dc.dsw.LivrariaJpaApplication**

   ```java
   package br.ufscar.dc.dsw;
   
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.boot.CommandLineRunner;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.context.annotation.Bean;
   
   import br.ufscar.dc.dsw.dao.LivroDAO;
   import br.ufscar.dc.dsw.domain.Livro;
   
   @SpringBootApplication
   public class LivrariaJpaApplication {
   
   	private static final Logger log = LoggerFactory.getLogger(LivrariaJpaApplication.class);
   	
   	public static void main(String[] args) {
   		SpringApplication.run(LivrariaJpaApplication.class, args);
   	}
   	
   	@Bean
   	public CommandLineRunner demo(ILivroDAO dao) {
   		return (args) -> {
   	
   			// Recupere todos livros
   			
   			log.info("Livros recuperados -- findAll():");
   			log.info("--------------------------------");
   			for (Livro livro : dao.findAll()) {
   				log.info(livro.toString());
   			}
   			log.info("");
   	
   			// Recupere um livro por seu ID
   			
   			Livro livro = dao.findById(1L);
   			log.info("Livro recuperado -- findById(1L):");
   			log.info("---------------------------------");
   			log.info(livro.toString());
   			log.info("");
   		};
   	}
   }
   ```

8. Compilar e executar.

   ```sh
   % mvn clean package
   % java -jar target/LivrariaJPA-0.0.1-SNAPSHOT.jar
   ```

   Verificar que:
   (1) um servidor web foi iniciado na porta 8080
   (2) os dados presentes na tabela Livro (classes Livro e Editora) são apresentados no console.

   ```
   Livros recuperados -- findAll():
   --------------------------------
   [Título: Ensaio sobre a Cegueira, Autor: José Saramago, Ano: 1995, Preço: 54.9, Editora: [CNPJ: 55.789.390/0008-99, Nome: Companhia das Letras]
   [Título: Cem anos de Solidão, Autor: Gabriel Garcia Márquez, Ano: 1977, Preço: 59.9, Editora: [CNPJ: 71.150.470/0001-40, Nome: Record]]
   [Título: Diálogos Impossíveis, Autor: Luis Fernando Verissimo, Ano: 2012, Preço: 22.9, Editora: [CNPJ: 32.106.536/0001-82, Nome: Objetiva]]
   
   Livro recuperado -- findById(1L):
   ---------------------------------
   [Título: Ensaio sobre a Cegueira, Autor: José Saramago, Ano: 1995, Preço: 54.9, Editora: [CNPJ: 55.789.390/0008-99, Nome: Companhia das Letras]]
   ```

9. Fim
















#### Leituras adicionais

- - -

- Accessing Data with JPA
  
  https://spring.io/guides/gs/accessing-data-jpa/
  
  
  
- O que é Spring Data JPA?

  https://blog.algaworks.com/spring-data-jpa/
  
  
  
- JPA Tutorial

  https://www.tutorialspoint.com/pg/jpa/index.htm
