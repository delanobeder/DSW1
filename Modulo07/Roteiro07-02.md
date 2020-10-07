## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 07 - Java Persistence API (JPA)**

- - -

#### 02 - Exemplo de aplicação JPA (Spring Data JPA)
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo07/SpringDataJPA)

- - -




##### (1) Configuração
- - -

1. Criar um novo projeto Spring (https://start.spring.io/)
	
	- **Project:** Maven Project
	
	- **Language:** Java
	
	- **Spring Boot:** 2.3.3
	
	- **Group:** br.ufscar.dc.dsw
	
	- **Artifact:** SpringDataJPA
	
	- **Name:** SpringDataJPA
	
	- **Description:** SpringDataJPA
	
	- **Package name:** br.ufscar.dc.dsw
	
	- **Packaging:** Jar
	
	- **Java:** 8
	
	  **Dependências:** Spring Web, Spring Data JPA e Spring Boot DevTools
	
2. Baixar o arquivo .zip e descompactar em um diretório (**SpringDataJPA**)

3. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 4). Utilizaremos o banco de dados criado no **Roteiro 07-01 (JDBC)**.

   3.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **JPA** criado anteriormente (Roteiro 07-01)

   ```properties
   # DERBY
   spring.datasource.url=jdbc:derby://localhost:1527/JPA
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver
   
   # JPA
   spring.jpa.hibernate.ddl-auto = create
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

   4.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **JPA** criado anteriormente (Roteiro 07-01)

   ```properties
   # MySQL
   spring.datasource.url = jdbc:mysql://localhost:3306/JPA
   spring.datasource.username = root
   spring.datasource.password = root
   
   # JPA
   spring.jpa.hibernate.ddl-auto = create
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


<div style="page-break-after: always"></div>
##### (2) Modelo
- - -

5. Classes de entidade/domínio **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.domain**

   5.1. Criar a classe **br.ufscar.dc.dsw.domain.Pessoa**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.GeneratedValue;
   import javax.persistence.GenerationType;
   import javax.persistence.Id;
   import javax.persistence.Inheritance;
   import javax.persistence.InheritanceType;
   import javax.persistence.Table;
   
   @Entity
   @Table(name = "Pessoa")
   @Inheritance(strategy = InheritanceType.JOINED)
   public abstract class Pessoa {
   	
   	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   	private Long id;
   	
   	@Column(nullable = false, unique = true, length = 60)
   	private String nome;
   
   	public Pessoa() {	
   	}
   	public Pessoa(String nome) {
   		this.nome = nome;
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
   
   	@Override
   	public String toString() {
   		return "Pessoa [Nome=" + nome + "]";
   	}
   }
   ```
   5.2. Criar a classe **br.ufscar.dc.dsw.domain.Aluno**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.Set;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.ManyToMany;
   import javax.persistence.Table;
   
   @Entity
   @Table(name = "Aluno")
   public class Aluno extends Pessoa {
   
   	@Column(nullable = false, unique = true, length = 6)
   	private String RA;
   
   	@ManyToMany(targetEntity = Disciplina.class)
   	private Set<Disciplina> disciplinas;
   
   	public Aluno() {
   	}	
   	public Aluno(String nome, String rA) {
   		super(nome);
   		RA = rA;
   	}
   
   	public String getRA() {
   		return RA;
   	}
   	public void setRA(String rA) {
   		RA = rA;
   	}
   
   	public Set<Disciplina> getDisciplinas() {
   		return disciplinas;
   	}
   	public void setDisciplinas(Set<Disciplina> disciplinas) {
   		this.disciplinas = disciplinas;
   	}
   
   	@Override
   	public String toString() {
   		return "[Nome = " + this.getNome() + ", RA = " + RA + "]";
   	}
   }
   ```
   <div style="page-break-after: always"></div>
   5.3. Criar a classe **br.ufscar.dc.dsw.domain.Professor**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.JoinColumn;
   import javax.persistence.ManyToOne;
   import javax.persistence.OneToOne;
   import javax.persistence.Table;
   
   @Entity
   @Table(name = "Professor")
   public class Professor extends Pessoa {
   	
   	@Column(nullable = false)
   	private float salario;
   	
   	@ManyToOne
   	@JoinColumn(name = "departamento_id")
   	private Departamento departamento;
   
   	@OneToOne(mappedBy = "professor")
   	private Disciplina disciplina;
   	
   	public Professor() {		
   	}
   	public Professor(String nome, float salario, Departamento departamento) {
   		super(nome);
   		this.salario = salario;
   		this.departamento = departamento;
   	}
   
   	public float getSalario() {
   		return salario;
   	}
   	public void setSalario(float salario) {
   		this.salario = salario;
   	}
   
   	public Departamento getDepartamento() {
   		return departamento;
   	}
   	public void setDepartamento(Departamento departamento) {
   		this.departamento = departamento;
   	}
   
   	public Disciplina getDisciplina() {
   		return disciplina;
   	}
   	public void setDisciplina(Disciplina disciplina) {
   		this.disciplina = disciplina;
   	}
   	
   	@Override
   	public String toString() {
   		return "[Nome = " + this.getNome() + ", Salário = " + salario + ", Departamento = " + departamento + "]";
   	}
   }
   ```
   5.4. Criar a classe **br.ufscar.dc.dsw.domain.Departamento**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.List;
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.GeneratedValue;
   import javax.persistence.GenerationType;
   import javax.persistence.Id;
   import javax.persistence.OneToMany;
   import javax.persistence.Table;
   
   @Entity
   @Table(name = "Departamento")
   public class Departamento {
   
   	@Id
   	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	private Long id;
   
   	@Column(nullable = false, unique = true, length = 30)
   	private String nome;
   
   	@Column(nullable = false, unique = true, length = 5)
   	private String sigla;
   
   	@OneToMany(mappedBy = "departamento")
   	private List<Professor> professores;
   	
   	public Departamento() {	
   	}
   	public Departamento(String nome, String sigla) {
   		this.nome = nome;
   		this.sigla = sigla;
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
   
   	public String getSigla() {
   		return sigla;
   	}
   	public void setSigla(String sigla) {
   		this.sigla = sigla;
   	}
   
   	public List<Professor> getProfessores() {
   		return professores;
   	}
   	public void setProfessores(List<Professor> professores) {
   		this.professores = professores;
   	}
   
   	@Override
   	public String toString() {
   		return "[Nome = " + nome + ", Sigla=" + sigla + "]";
   	}
   }
   ```
   5.5. Criar a classe **br.ufscar.dc.dsw.domain.Disciplina**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import java.util.Set;
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.FetchType;
   import javax.persistence.GeneratedValue;
   import javax.persistence.GenerationType;
   import javax.persistence.Id;
   import javax.persistence.ManyToMany;
   import javax.persistence.OneToOne;
   import javax.persistence.Table;
   
   @Entity
   @Table(name = "Disciplina")
   public class Disciplina {
   
   	@Id
   	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	private Long id;
   
   	@Column(nullable = false, unique = true, length = 30)
   	private String nome;
   
   	@Column(nullable = false, unique = true, length = 5)
   	private String sigla;
   
   	@ManyToMany(targetEntity = Aluno.class, mappedBy = "disciplinas", fetch = FetchType.EAGER)
   	private Set<Aluno> alunos;
   
   	@OneToOne
   	private Professor professor;
   
   	public Disciplina() {	
   	}
   	public Disciplina(String nome, String sigla, Professor professor) {
   		this.nome = nome;
   		this.sigla = sigla;
   		this.professor = professor;
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
   
   	public String getSigla() {
   		return sigla;
   	}
   	public void setSigla(String sigla) {
   		this.sigla = sigla;
   	}
   
   	public Set<Aluno> getAlunos() {
   		return alunos;
   	}
   	public void setAlunos(Set<Aluno> alunos) {
   		this.alunos = alunos;
   	}
   	
   	@Override
   	public String toString() {
   		return "[Nome = " + nome + ", Sigla = " + sigla + "]";
   	}
   }
   ```
<div style="page-break-after: always"></div>
6. Interfaces DAO (*Data Access Object*) **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.dao**

   6.1. Criar a interface **br.ufscar.dc.dsw.dao.IPessoaDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Pessoa;
   
   @SuppressWarnings("unchecked")
   public interface IPessoaDAO extends CrudRepository<Pessoa, Long>{
   	Pessoa findById(long id);
   	List<Pessoa> findAll();
   	Pessoa save(Pessoa pessoa);
   	void deleteById(Long id);
   }
   ```
   
   6.2. Criar a interface **br.ufscar.dc.dsw.dao.IProfessorDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Departamento;
   import br.ufscar.dc.dsw.domain.Professor;
   
   public interface IProfessorDAO extends CrudRepository<Professor, Long> {
   	List<Professor> findByDepartamento(Departamento departamento);
   }
   ```
   
   6.3. Criar a interface **br.ufscar.dc.dsw.dao.IDepartamentoDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Departamento;
   
   @SuppressWarnings("unchecked")
   public interface IDepartamentoDAO extends CrudRepository<Departamento, Long>{
   	Departamento findById(long id);
   	List<Departamento> findAll();
   	Departamento save(Departamento departamento);
   	void deleteById(Long id);
   }
   ```
   <div style="page-break-after: always"></div>
   6.4. Criar a interface **br.ufscar.dc.dsw.dao.IDisciplinaDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Disciplina;
   
   @SuppressWarnings("unchecked")
   public interface IDisciplinaDAO extends CrudRepository<Disciplina, Long>{
   	Disciplina findById(long id);
   	List<Disciplina> findAll();
   	Disciplina save(Disciplina disciplina);
   	void deleteById(Long id);
   }
   ```
   



##### (3) Testar
- - -

7. Criar a classe **br.ufscar.dc.dsw.SpringDataJpaApplication**

   ```java
   package br.ufscar.dc.dsw;
   
   import java.util.HashSet;
   import java.util.List;
   import java.util.Set;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.boot.CommandLineRunner;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.context.annotation.Bean;
   import br.ufscar.dc.dsw.dao.IDepartamentoDAO;
   import br.ufscar.dc.dsw.dao.IDisciplinaDAO;
   import br.ufscar.dc.dsw.dao.IPessoaDAO;
   import br.ufscar.dc.dsw.dao.IProfessorDAO;
   import br.ufscar.dc.dsw.domain.Aluno;
   import br.ufscar.dc.dsw.domain.Departamento;
   import br.ufscar.dc.dsw.domain.Disciplina;
   import br.ufscar.dc.dsw.domain.Pessoa;
   import br.ufscar.dc.dsw.domain.Professor;
   
   @SpringBootApplication
   public class SpringDataJpaApplication {
   
   	private static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);
   	
   	public static void main(String[] args) {
   		SpringApplication.run(SpringDataJpaApplication.class, args);
   	}
   
       
       
   	@Bean
   	public CommandLineRunner demo(IPessoaDAO pessoaDAO, IDepartamentoDAO departamentoDAO, IDisciplinaDAO disciplinaDAO, IProfessorDAO professorDAO) {
   		return (args) -> {
   			
   			Departamento dc = new Departamento("Computação", "DC");
   			log.info("Salvando Departamento - DC");
   			departamentoDAO.save(dc);
   
   			Professor professor1 = new Professor("Professor 1", 1000, dc);
   			log.info("Salvando Professor 1");
   			pessoaDAO.save(professor1);
   
   			Professor professor2 = new Professor("Professor 2", 3000, dc);
   			log.info("Salvando Professor 2");
   			pessoaDAO.save(professor2);
   			
   			Disciplina dsw = new Disciplina("Desenvolvimento Web 1", "DSW", professor1);
   			log.info("Salvando Disciplina - DSW");
   			disciplinaDAO.save(dsw);
   
   			Aluno aluno1 = new Aluno("Aluno 1", "123456");
   			log.info("Salvando Aluno 1");
   			pessoaDAO.save(aluno1);
   
   			Aluno aluno2 = new Aluno("Aluno 2", "654321");
   			log.info("Salvando Aluno 2");
   			pessoaDAO.save(aluno2);
   
   			List<Pessoa> pessoas = pessoaDAO.findAll();
   			log.info("Imprimindo pessoas - findAll()");
   			for (Pessoa p : pessoas) {
   				log.info(p.toString());
   			}
   
   			// Matricula aluno1 na disciplina
   
   			Set<Disciplina> disciplinas = new HashSet<>();
   			disciplinas.add(dsw);
   			aluno1.setDisciplinas(disciplinas);
   			pessoaDAO.save(aluno1);
   			
   			// Matricula aluno2 na disciplina
   			
   			disciplinas = new HashSet<>();
   			disciplinas.add(dsw);
   			aluno2.setDisciplinas(disciplinas);
   			pessoaDAO.save(aluno2);
   
   			log.info("Imprimindo alunos da disciplina dsw");
   			dsw = disciplinaDAO.findById(1L);
   			for (Aluno a : dsw.getAlunos()) {
   				log.info(a.toString());
   			}
   			
   			log.info("Imprimindo professores do departamento dc");
   			for (Professor p : professorDAO.findByDepartamento(dc)) {
   				log.info(p.toString());
   			}
   		};
   	}
   }
   ```
   
8. Compilar e executar

      ```sh
   % mvn compile
   % mvn spring-boot:run
   ```

   Verificar que os dados apresentados no console são armazenados no banco de dados.


#### Leituras adicionais

- - -

* Spring Data JPA - Overview

  https://spring.io/projects/spring-data-jpa#overview

* Spring Data JPA - Reference Documentation

  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference

* JPA and Spring Data JPA

  https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data

* Accessing Data Accessing Data with JPA

  https://spring.io/guides/gs/accessing-data-jpa/

* Introduction to the Java Persistence API

  https://docs.oracle.com/javaee/7/tutorial/persistence-intro.htm

* JPA Tutorial

  https://www.tutorialspoint.com/jpa/index.htm

* Many-To-Many Relationship in JPA

  https://www.baeldung.com/jpa-many-to-many

* Hibernate Inheritance Mapping

  https://www.baeldung.com/hibernate-inheritance
