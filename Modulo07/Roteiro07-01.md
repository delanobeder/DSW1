## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 07 - Java Persistence API (JPA)**

- - -

#### 01 - Exemplo de aplicação JPA
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo07/JPA)
- - -




##### (1) Configuração
- - -



1. Criar um projeto Maven Java -- abrir um terminal e executar o seguinte comando (não esquecer de configurar o Maven)

     ```sh
   mvn archetype:generate -DartifactId=JPA -DgroupId=br.ufscar.dc.dsw -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```

2. Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

   - Abrir um terminal dentro da pasta do projeto (**JPA**) e executar o seguinte comando: 

   ```sh
   mvn -N io.takari:maven:wrapper
   ```

3. Abra o diretório AloMundoMVN em um editor de texto. Adicionar a seguinte dependência e plugin ao arquivo **pom.xml** 

     ```xml
   <project ... >
     <dependencies>
     	...
      	<dependency>
   		<groupId>org.hibernate</groupId>
   		<artifactId>hibernate-core</artifactId>
   		<version>4.3.8.Final</version>
   		<scope>compile</scope>
   	</dependency>
   
   	<!-- Implementação de EntityManager da JPA -->
   		
   	<dependency>
   		<groupId>org.hibernate</groupId>
   		<artifactId>hibernate-entitymanager</artifactId>
   		<version>4.3.8.Final</version>
   		<scope>compile</scope>
   	</dependency>
     </dependencies>
     ...   
   </project>
   ```
   
2. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   4.1. Criar novo banco de dados **JPA** (usuário: **root**, senha: **root**)

   - Crie o arquivo **db/Derby/create.sql**

     ```sql
     connect 'jdbc:derby:JPA;create=true;user=root;password=root';
     
     disconnect;
     
     quit;
     ```
   
   
   
   4.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)
   
   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:JPA;create=true;user=root;password=root';
   ij> disconnect;
   ij> quit;
   ```
   
   4.3. Iniciar o servidor **Apache Derby**. Em um terminal executar: 
   
   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar $DERBY_HOME/lib/derbyrun.jar server start
   ```
   
   4.4. Adicionar biblioteca do ***Derby JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
   <dependency>
        <groupId>org.apache.derby</groupId>
        <artifactId>derbyclient</artifactId>
        <version>10.14.2.0</version>
        <scope>runtime</scope>
    </dependency>
   ```
   <div style="page-break-after: always"></div>
   
   4.4 Criar o arquivo **src/resources/META-INF/persistence.xml**
   
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <persistence xmlns="http://java.sun.com/xml/ns/persistence"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
            http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
   version="2.0">  
    <persistence-unit name="JPAPU">
       <provider>org.hibernate.ejb.HibernatePersistence</provider>
       <properties>
           <property name="javax.persistence.jdbc.url" value="jdbc:derby://localhost:1527/JPA" />
           <property name="javax.persistence.jdbc.user" value="root" />
           <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.ClientDriver" />
           <property name="javax.persistence.jdbc.password" value="root" />
           <property name="javax.persistence.schema-generation.database.action" value="drop-and-create" />
           <property name="hibernate.show_sql" value="true" />
           <property name="hibernate.format_sql" value="true"/>
       </properties>
   </persistence-unit>
   </persistence>
   ```
   
2. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

   5.1. Criar novo banco de dados **JPA** (usuário: **root**, senha: **root**)

   - Crie o arquivo **db/MySQL/create.sql**

   ```sql
   create database JPA;
   
   use JPA;
   ```

   5.2. Em um terminal no diretório do projeto, executar 

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
   mysql> quit
   Bye
   ```

   5.3. Adicionar biblioteca do  ***MySQL JDBC Driver*** como dependência do projeto (no arquivo **pom.xml**)

   ```xml
   <dependency>
   	<groupId>mysql</groupId>
   	<artifactId>mysql-connector-java</artifactId>
        <version>8.0.21</version>
   	<scope>runtime</scope>
   </dependency>
   ```
   5.4 Criar o arquivo **src/resources/META-INF/persistence.xml**
   
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <persistence xmlns="http://java.sun.com/xml/ns/persistence"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
            http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
   version="2.0">
   
   <persistence-unit name="JPAPU">
       <provider>org.hibernate.ejb.HibernatePersistence</provider>
       <properties>
           <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/JPA" />
           <property name="javax.persistence.jdbc.user" value="root" />
           <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
           <property name="javax.persistence.jdbc.password" value="root" />
           <property name="javax.persistence.schema-generation.database.action" value="drop-and-create" />
           <property name="hibernate.show_sql" value="true" />
           <property name="hibernate.format_sql" value="true"/>
       </properties>
   </persistence-unit>
   </persistence>
   ```

<div style="page-break-after: always"></div>
##### (2) Modelo
- - -

6. Classes de entidade/domínio **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.domain**

   6.1. Criar a classe **br.ufscar.dc.dsw.domain.Pessoa**
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
   6.2. Criar a classe **br.ufscar.dc.dsw.domain.Aluno**
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
   6.3. Criar a classe **br.ufscar.dc.dsw.domain.Professor**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.JoinColumn;
   import javax.persistence.ManyToOne;
   import javax.persistence.OneToOne;
   import javax.persistence.Table;
   
   @Entity @Table(name = "Professor")
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
   	public Professor(String nome, float salario, Departamento depto) {
   		super(nome);
   		this.salario = salario;
   		this.departamento = depto;
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
   6.4. Criar a classe **br.ufscar.dc.dsw.domain.Departamento**
   
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
   6.5. Criar a classe **br.ufscar.dc.dsw.domain.Disciplina**
   
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
7. Classes DAO (*Data Access Object*) **[M do MVC]**

   Criar uma nova pasta (pacote) dentro de src/main/java: **br.ufscar.dc.dsw.dao**

   7.1. Criar a classe **br.ufscar.dc.dsw.dao.GenericDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import javax.persistence.EntityManager;
   import javax.persistence.EntityManagerFactory;
   import javax.persistence.Persistence;
   
   public abstract class GenericDAO<T> {
   
   	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAPU");
   
   	protected EntityManager getEntityManager() {
   		return emf.createEntityManager();
   	}
   
   	public abstract T find(Long id);
   	public abstract List<T> findAll();
   	public abstract void save(T t);
   	public abstract void update(T t);
   	public abstract void delete(Long id);
   	
   	public static void close() {
   		emf.close();
   	}
   }
   ```

   7.2. Criar a classe **br.ufscar.dc.dsw.dao.PessoaDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import javax.persistence.EntityManager;
   import javax.persistence.EntityTransaction;
   import javax.persistence.Query;
   import br.ufscar.dc.dsw.domain.Pessoa;
   
   public class PessoaDAO extends GenericDAO<Pessoa>{
   
   	@Override
   	public Pessoa find(Long id) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		Pessoa pessoa = em.find(Pessoa.class, id);
   		tx.commit();
   		em.close();
   		return pessoa;
   	}
   	
   	@SuppressWarnings("unchecked")
   	@Override
       public List<Pessoa> findAll() {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
   		tx.begin();
           Query q = em.createQuery("SELECT p FROM Pessoa p");
           List<Pessoa> lista = q.getResultList();
           tx.commit();
           em.close();
           return lista;
       }
   
       @Override
       public void save(Pessoa pessoa) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           tx.begin();
           em.persist(pessoa);
           tx.commit();
           em.close();
       }
   
       @Override
       public void update(Pessoa pessoa) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           tx.begin();
           em.merge(pessoa);
           tx.commit();
           em.close();
       }
   
       @Override
       public void delete(Long id) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           Pessoa pessoa = em.getReference(Pessoa.class, id);
           tx.begin();
           em.remove(pessoa);
           tx.commit();
           em.close();
       }
   }
   ```
   <div style="page-break-after: always"></div>
   7.3. Criar a classe **br.ufscar.dc.dsw.dao.ProfessorDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import javax.persistence.EntityManager;
   import javax.persistence.EntityTransaction;
   import javax.persistence.TypedQuery;
   
   import br.ufscar.dc.dsw.domain.Departamento;
   import br.ufscar.dc.dsw.domain.Professor;
   
   public class ProfessorDAO extends PessoaDAO {
   
   	public List<Professor> findbyDepartamento(Departamento departamento) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		String jpql = "SELECT p FROM Professor p where p.departamento = :departamento";
   		TypedQuery<Professor> q = em.createQuery(jpql, Professor.class);
   		q.setParameter("departamento", departamento);
   		List<Professor> lista = q.getResultList();
   		tx.commit();
   		em.close();
   		return lista;
   	}
   }
   ```
   
   7.4. Criar a classe **br.ufscar.dc.dsw.dao.DepartamentoDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import javax.persistence.EntityManager;
   import javax.persistence.EntityTransaction;
   import javax.persistence.Query;
   
   import br.ufscar.dc.dsw.domain.Departamento;
   
   public class DepartamentoDAO extends GenericDAO<Departamento>{
   
   	@Override
   	public Departamento find(Long id) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		Departamento departamento = em.find(Departamento.class, id);
   		tx.commit();
   		em.close();
   		return departamento;
   	}
   	
   	@SuppressWarnings("unchecked")
   	@Override
       public List<Departamento> findAll() {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
   		tx.begin();
           Query q = em.createQuery("SELECT d FROM Departamento d");
           List<Departamento> lista = q.getResultList();
           tx.commit();
           em.close();
           return lista;
       }
   	
       @Override
       public void save(Departamento departamento) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           tx.begin();
           em.persist(departamento);
           tx.commit();
           em.close();
       }
   
       @Override
       public void update(Departamento departamento) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           tx.begin();
           em.merge(departamento);
           tx.commit();
           em.close();
       }
   
       @Override
       public void delete(Long id) {
           EntityManager em = this.getEntityManager();
           EntityTransaction tx = em.getTransaction();
           Departamento departamento = em.getReference(Departamento.class, id);
           tx.begin();
           em.remove(departamento);
           tx.commit();
           em.close();
       }
   }
   ```
   <div style="page-break-after: always"></div>
   7.5. Criar a classe **br.ufscar.dc.dsw.dao.DisciplinaDAO**
   
   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   
   import javax.persistence.EntityManager;
   import javax.persistence.EntityTransaction;
   import javax.persistence.Query;
   
   import br.ufscar.dc.dsw.domain.Disciplina;
   
   public class DisciplinaDAO extends GenericDAO<Disciplina> {
   
   	@Override
   	public Disciplina find(Long id) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		Disciplina disciplina = em.find(Disciplina.class, id);
   		tx.commit();
   		em.close();
   		return disciplina;
   	}
   
   	@SuppressWarnings("unchecked")
   	@Override
   	public List<Disciplina> findAll() {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		Query q = em.createQuery("SELECT d FROM Disciplina d");
   		List<Disciplina> lista = q.getResultList();
   		tx.commit();
   		em.close();
   		return lista;
   	}
   
   	@Override
   	public void save(Disciplina disciplina) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		em.persist(disciplina);
   		tx.commit();
   		em.close();
   	}
   
   	@Override
   	public void update(Disciplina disciplina) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		tx.begin();
   		em.merge(disciplina);
   		tx.commit();
   		em.close();
   	}
   
   	@Override
   	public void delete(Long id) {
   		EntityManager em = this.getEntityManager();
   		EntityTransaction tx = em.getTransaction();
   		Disciplina disciplina = em.getReference(Disciplina.class, id);
   		tx.begin();
   		em.remove(disciplina);
   		tx.commit();
   		em.close();
   	}
   }
   ```



##### (3) Testar
- - -

12. Criar a classe **br.ufscar.dc.dsw.App**
```java
package br.ufscar.dc.dsw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufscar.dc.dsw.dao.DepartamentoDAO;
import br.ufscar.dc.dsw.dao.DisciplinaDAO;
import br.ufscar.dc.dsw.dao.PessoaDAO;
import br.ufscar.dc.dsw.dao.ProfessorDAO;
import br.ufscar.dc.dsw.domain.Aluno;
import br.ufscar.dc.dsw.domain.Departamento;
import br.ufscar.dc.dsw.domain.Disciplina;
import br.ufscar.dc.dsw.domain.Pessoa;
import br.ufscar.dc.dsw.domain.Professor;

public class App {

	public static void main(String[] args) {

		PessoaDAO pessoaDAO = new PessoaDAO();

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();

		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

		ProfessorDAO professorDAO = new ProfessorDAO();
		
		Departamento dc = new Departamento("Computação", "DC");

		System.out.println("Salvando Departamento - DC");

		departamentoDAO.save(dc);

		Professor professor1 = new Professor("Professor 1", 1000, dc);

		System.out.println("Salvando Professor 1");

		pessoaDAO.save(professor1);

		Professor professor2 = new Professor("Professor 2", 3000, dc);

		System.out.println("Salvando Professor 2");

		pessoaDAO.save(professor2);
		
		Disciplina dsw = new Disciplina("Desenvolvimento Web 1", "DSW", professor1);

		System.out.println("Salvando Disciplina - DSW");

		disciplinaDAO.save(dsw);

		Aluno aluno1 = new Aluno("Aluno 1", "123456");

		System.out.println("Salvando Aluno 1");

		pessoaDAO.save(aluno1);

		Aluno aluno2 = new Aluno("Aluno 2", "654321");

		System.out.println("Salvando Aluno 2");

		pessoaDAO.save(aluno2);

		List<Pessoa> pessoas = pessoaDAO.findAll();

		System.out.println("Imprimindo pessoas - findAll()");

		for (Pessoa p : pessoas) {
			System.out.println(p);
		}

		// Matricula aluno1 na disciplina

		Set<Disciplina> disciplinas = new HashSet<>();
		disciplinas.add(dsw);
		aluno1.setDisciplinas(disciplinas);

		pessoaDAO.update(aluno1);
		
		// Matricula aluno2 na disciplina
		
		disciplinas = new HashSet<>();
		disciplinas.add(dsw);
		aluno2.setDisciplinas(disciplinas);

		pessoaDAO.update(aluno2);

		System.out.println("Imprimindo alunos da disciplina dsw");

		dsw = disciplinaDAO.find(1L);
		
		for (Aluno a : dsw.getAlunos()) {
			System.out.println(a);
		}
		
		System.out.println("Imprimindo professores do departamento dc");
		
		for (Professor p : professorDAO.findbyDepartamento(dc)) {
			System.out.println(p);
		}
		
		PessoaDAO.close();
	}
}
```

13. Compilar e executar

   ```sh
% mvn compile
% mvn exec:java -Dexec.mainClass="br.ufscar.dc.dsw.App" -Dexec.cleanupDaemonThreads=false
   ```

   Verificar que os dados apresentados no console são armazenados no banco de dados.





#### Leituras adicionais

- - -


* Introduction to the Java Persistence API

  https://docs.oracle.com/javaee/7/tutorial/persistence-intro.htm

  

* JPA Tutorial

  https://www.tutorialspoint.com/jpa/index.htm



* Many-To-Many Relationship in JPA

  https://www.baeldung.com/jpa-many-to-many

  

* Hibernate Inheritance Mapping

  https://www.baeldung.com/hibernate-inheritance

