## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 09 - REST API**

- - -

#### 01 - Exemplo de servidor REST

**Cidades REST Server**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo09/CidadesRS-v1)

- - -



##### (1) Configuração

- - -

1. Criar um novo projeto Spring (https://start.spring.io/)
	
	- **Project:** Maven Project
	
	- **Language:** Java
	
	- **Spring Boot:** 2.4.0
	
	- **Group:** br.ufscar.dc.dsw
	
	- **Artifact:** CidadesRS
	
	- **Name:** CidadesRS
	
	- **Description:** CidadesRS
	
	- **Package name:** br.ufscar.dc.dsw
	
	- **Packaging:** Jar
	
	- **Java:** 8
	
	  **Dependências:** Spring Web, Spring Data JPA e Spring Boot DevTools
	
2. Baixar o arquivo .zip e descompactar em um diretório (**CidadesRS**)

3. Configurar o projeto --- no arquivo **pom.xml** (incluir novas maven dependências)

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
   ```

   <div style="page-break-after: always"></div>

4. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 4). Utilizaremos o banco de dados **Cidades** criado no **Roteiro 05-03 (AJAX)**.

   4.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Cidades** criado anteriormente (Roteiro 05-03)

   ```properties
   # DERBY
   spring.datasource.url=jdbc:derby://localhost:1527/Cidades
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver
   
   # JPA
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   
   # Server Port
   server.port = 8081
   ```

   4.2. No arquivo **pom.xml**, adicionaremos a biblioteca do ***Derby JDBC Driver*** como dependência do projeto

   ```xml
   <dependency>
   	<groupId>org.apache.derby</groupId>
   	<artifactId>derbyclient</artifactId>
   	<scope>runtime</scope>
   </dependency>
   ```

5. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado). Utilizaremos o banco de dados **Cidades** criado no **Roteiro 05-03 (AJAX)**.

   5.1 No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados **Cidades** criado anteriormente (Roteiro 05-03)

   ```properties
   # MySQL
   spring.datasource.url = jdbc:mysql://localhost:3306/Cidades
   spring.datasource.username = root
   spring.datasource.password = root
   
   # JPA
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   
   # Server Port
   server.port = 8081
   ```

   <div style="page-break-after: always"></div>

   5.2. No arquivo **pom.xml**, adicionaremos a biblioteca do ***MySQL JDBC Driver*** como dependência do projeto
   
   ```xml
   <dependency>
   	<groupId>mysql</groupId>
   	<artifactId>mysql-connector-java</artifactId>
   	<scope>runtime</scope>
   </dependency>
   ```



##### (2) Modelo
- - -

6. Criar as classes de domínio (pacote **br.ufscar.dc.dsw.domain**)

   6.1. Classe **br.ufscar.dc.dsw.domain.AbstractEntity**

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

   

   6.2. Classe **br.ufscar.dc.dsw.domain.Estado**

   ```java
   
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.Table;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Estado")
   public class Estado extends AbstractEntity<Long>{
       
      @Column(nullable = false, length = 2)
      private String sigla;
   	
      @Column(nullable = false, length = 30)
       private String nome;
   
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

   6.3. Classe **br.ufscar.dc.dsw.domain.Cidade**

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.JoinColumn;
   import javax.persistence.ManyToOne;
   import javax.persistence.Table;
   
   @SuppressWarnings("serial")
   @Entity
   @Table(name = "Cidade")
   public class Cidade extends AbstractEntity<Long> {
   
   	@Column(nullable = false, length = 80)
   	private String nome;
   
   	@ManyToOne
   	@JoinColumn(name = "estado_id")
   	private Estado estado;
   
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

6. Criar as interfaces DAO (**pacote br.ufscar.dc.dsw.dao**)

   7.1. Interface **br.ufscar.dc.dsw.dao.IEstadoDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.repository.CrudRepository;
   import br.ufscar.dc.dsw.domain.Estado;
   
   @SuppressWarnings("unchecked")
   public interface IEstadoDAO extends CrudRepository<Estado, Long> {
   	
       Estado findById(long id);
   	List<Estado> findAll();
   	Estado save(Estado estado);
   	void deleteById(Long id);
   }
   ```

   

   7.2. Interface **br.ufscar.dc.dsw.dao.spec.ICidadeDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import java.util.List;
   import org.springframework.data.jpa.repository.Query;
   import org.springframework.data.repository.CrudRepository;
   import org.springframework.data.repository.query.Param;
   import br.ufscar.dc.dsw.domain.Cidade;
   
   @SuppressWarnings("unchecked")
   public interface ICidadeDAO extends CrudRepository<Cidade, Long> {
   	
       Cidade findById(long id);
   	List<Cidade> findAll();
   	Cidade save(Cidade cidade);
   	void deleteById(Long id);	
   	
       public List<Cidade> findByNomeLikeIgnoreCase(String nome);
   	
       @Query("select c from Cidade c where estado.id = :id")
   	public List<Cidade> findByEstadoId(@Param("id") Long id);
   }
   ```

   <div style="page-break-after: always"></div>

8. Criar as classes e interfaces de serviço (pacote **br.ufscar.dc.dsw.service**)

   8.1. Interface **br.ufscar.dc.dsw.service.spec.IEstadoService**

   ```java
   package br.ufscar.dc.dsw.service.spec;
   
   import java.util.List;
   import br.ufscar.dc.dsw.domain.Estado;
   
   public interface IEstadoService {
   	Estado findById(long id);
   	List<Estado> findAll();
   	void save(Estado estado);
   	void delete(Long id);
   }
   ```

   

   8.2. Classe **br.ufscar.dc.dsw.service.impl.EstadoService**

   ```java
   package br.ufscar.dc.dsw.service.impl;
   
   import java.util.List;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;
   import br.ufscar.dc.dsw.dao.IEstadoDAO;
   import br.ufscar.dc.dsw.domain.Estado;
   import br.ufscar.dc.dsw.service.spec.IEstadoService;
   
   @Service
   @Transactional(readOnly = false)
   public class EstadoService implements IEstadoService {
   	@Autowired
   	IEstadoDAO dao;
   
   	@Override
   	@Transactional(readOnly = true)
   	public Estado findById(long id) {
   		return dao.findById(id);
   	}
   
   	@Override
   	@Transactional(readOnly = true)
   	public List<Estado> findAll() {
   		return dao.findAll();
   	}
   
   	@Override
   	public void save(Estado estado) {
   		dao.save(estado);
   	}
   	
   	@Override
   	public void delete(Long id) {
   		dao.deleteById(id);
   	}
   }
   ```
   8.3. Interface **br.ufscar.dc.dsw.service.spec.ICidadeService**
   
   ```java
package br.ufscar.dc.dsw.service.spec;
   
   import java.util.List;
   import br.ufscar.dc.dsw.domain.Cidade;
   
   public interface ICidadeService {
   	
   	Cidade findById(Long id);
   	List<Cidade> findAll();
   	void save(Cidade estado);
   	void delete(Long id);
   	
   	List<Cidade> findByEstado(Long id);
   	List<Cidade> findByNome(String nome);
   }
   ```
   
   8.4. Classe **br.ufscar.dc.dsw.service.impl.CidadeService**

   ```java
package br.ufscar.dc.dsw.service.impl;
   
   import java.util.List;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;
   
   import br.ufscar.dc.dsw.dao.ICidadeDAO;
   import br.ufscar.dc.dsw.domain.Cidade;
   import br.ufscar.dc.dsw.service.spec.ICidadeService;
   
   @Service
   @Transactional(readOnly = false)
   public class CidadeService implements ICidadeService {
   
   	@Autowired
   	ICidadeDAO dao;
   
   	@Override
   	@Transactional(readOnly = true)
   	public Cidade findById(Long id) {
   		return dao.findById(id.longValue());
   	}
   
   	@Override
   	@Transactional(readOnly = true)
   	public List<Cidade> findAll() {
   		return dao.findAll();
   	}
   	
   	@Override
   	@Transactional(readOnly = true)
   	public List<Cidade> findByEstado(Long id) {
   		return dao.findByEstadoId(id);
   	}
   	
   	@Override
   	@Transactional(readOnly = true)
   	public List<Cidade> findByNome(String nome) {
   		return dao.findByNomeLikeIgnoreCase(nome);
   	}
   
   	@Override
   	public void save(Cidade cidade) {
   		dao.save(cidade);
   	}
   	
   	@Override
   	public void delete(Long id) {
   		dao.deleteById(id);
   	}
   }
   ```


##### (3) Controlador
- - -
8. Criar as classes  controladores (pacote **br.ufscar.dc.dsw.controller**)

   8.1. Classe **br.ufscar.dc.dsw.controller.EstadoRestController**
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
   import org.springframework.web.bind.annotation.CrossOrigin;
   import com.fasterxml.jackson.databind.ObjectMapper;
   import br.ufscar.dc.dsw.domain.Estado;
   import br.ufscar.dc.dsw.service.spec.IEstadoService;
   
   @CrossOrigin
   @RestController
   public class EstadoRestController {
   
   	@Autowired
   	private IEstadoService service;
   
   	private boolean isJSONValid(String jsonInString) {
   		try {
   			return new ObjectMapper().readTree(jsonInString) != null;
   		} catch (IOException e) {
   			return false;
   		}
   	}
   
   	private void parse(Estado estado, JSONObject json) {
   		
   		Object id = json.get("id");
   		if (id != null) {
   			if (id instanceof Integer) {
   				estado.setId(((Integer) id).longValue());
   			} else {
   				estado.setId((Long) id);
   			}
   		}
   
   		estado.setNome((String) json.get("nome"));
   		estado.setSigla((String) json.get("sigla"));
   	}
   
   	@GetMapping(path = "/estados")
   	public ResponseEntity<List<Estado>> lista() {
   		List<Estado> lista = service.findAll();
   		if (lista.isEmpty()) {
   			return ResponseEntity.notFound().build();
   		}
   		return ResponseEntity.ok(lista);
   	}
   
   	@GetMapping(path = "/estados/{id}")
   	public ResponseEntity<Estado> lista(@PathVariable("id") long id) {
   		Estado estado = service.findById(id);
   		if (estado == null) {
   			return ResponseEntity.notFound().build();
   		}
   		return ResponseEntity.ok(estado);
   	}
   
   	@PostMapping(path = "/estados")
   	@ResponseBody
   	public ResponseEntity<Estado> cria(@RequestBody JSONObject json) {
   		try {
   			if (isJSONValid(json.toString())) {
   				Estado estado = new Estado();
   				parse(estado, json);
   				service.save(estado);
   				return ResponseEntity.ok(estado);
   			} else {
   				return ResponseEntity.badRequest().body(null);
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
   		}
   	}
   
   	@PutMapping(path = "/estados/{id}")
   	public ResponseEntity<Estado> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
   		try {
   			if (isJSONValid(json.toString())) {
   				Estado estado = service.findById(id);
   				if (estado == null) {
   					return ResponseEntity.notFound().build();
   				} else {
   					parse(estado, json);
   					service.save(estado);
   					return ResponseEntity.ok(estado);
   				}
   			} else {
   				return ResponseEntity.badRequest().body(null);
   			}
   		} catch (Exception e) {
   			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
   		}
   	}
   
   	@DeleteMapping(path = "/estados/{id}")
   	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
   
   		Estado estado = service.findById(id);
   		if (estado == null) {
   			return ResponseEntity.notFound().build();
   		} else {
   			service.delete(id);
   			return ResponseEntity.noContent().build();
   		}
   	}
   }
   ```
   
   
   
   8.2. Classe **br.ufscar.dc.dsw.controller.CidadeRestController**
   ```java
   package br.ufscar.dc.dsw.controller;
   
   import java.io.IOException;
   import java.util.ArrayList;
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
   import org.springframework.web.bind.annotation.RequestParam;
   import org.springframework.web.bind.annotation.ResponseBody;
   import org.springframework.web.bind.annotation.RestController;
   import org.springframework.web.bind.annotation.CrossOrigin;
   import com.fasterxml.jackson.databind.ObjectMapper;
   import br.ufscar.dc.dsw.domain.Cidade;
   import br.ufscar.dc.dsw.domain.Estado;
   import br.ufscar.dc.dsw.service.spec.ICidadeService;
   
   @CrossOrigin
   @RestController
   public class CidadeRestController {
   
   	@Autowired
   	private ICidadeService service;
   
   	private boolean isJSONValid(String jsonInString) {
   		try {
   			return new ObjectMapper().readTree(jsonInString) != null;
   		} catch (IOException e) {
   			return false;
   		}
   	}
   
   	@SuppressWarnings("unchecked")
   	private void parse(Estado estado, JSONObject json) {
   		Map<String, Object> map = (Map<String, Object>) json.get("estado");
   		
   		Object id = map.get("id");
   		if (id instanceof Integer) {
   			estado.setId(((Integer) id).longValue());
   		} else {
   			estado.setId((Long) id);
   		}
   		 		
   		estado.setSigla((String) map.get("sigla"));
   		estado.setNome((String) map.get("nome"));
   	}
   
   	private void parse(Cidade cidade, JSONObject json) {
   
   		Object id = json.get("id");
   		if (id != null) {
   			if (id instanceof Integer) {
   				cidade.setId(((Integer) id).longValue());
   			} else {
   				cidade.setId((Long) id);
   			}
   		}
   
   		cidade.setNome((String) json.get("nome"));
   
   		Estado estado = new Estado();
   		parse(estado, json);
   		cidade.setEstado(estado);
   	}
   
   	@GetMapping(path = "/cidades")
   	public ResponseEntity<List<Cidade>> lista() {
   		List<Cidade> lista = service.findAll();
   		if (lista.isEmpty()) {
   			return ResponseEntity.notFound().build();
   		}
   		return ResponseEntity.ok(lista);
   	}
   
   	@GetMapping(path = "/cidades/{id}")
   	public ResponseEntity<Cidade> lista(@PathVariable("id") long id) {
   		Cidade cidade = service.findById(id);
   		if (cidade == null) {
   			return ResponseEntity.notFound().build();
   		}
   		return ResponseEntity.ok(cidade);
   	}
   
   	@GetMapping(path = "/cidades/estados/{id}")
   	public ResponseEntity<List<Cidade>> listaPorEstado(@PathVariable("id") long id) {
   		List<Cidade> lista = service.findByEstado(id);
   		if (lista.isEmpty()) {
   			return ResponseEntity.notFound().build();
   		}
   		return ResponseEntity.ok(lista);
   	}
   
   	@GetMapping(path = "/cidades/filtros")
   	public ResponseEntity<List<String>> listaPorNome(@RequestParam(name = "term") String nome) {
   		List<Cidade> cidades = service.findByNome("%" + nome + "%");
   		List<String> lista = new ArrayList<>();
   		for (Cidade c : cidades) {
   			lista.add(c.getNome() + "/" + c.getEstado().getSigla());
   		}
   		return ResponseEntity.ok(lista);
   	}
   
   	@PostMapping(path = "/cidades")
   	@ResponseBody
   	public ResponseEntity<Cidade> cria(@RequestBody JSONObject json) {
   		try {
   			if (isJSONValid(json.toString())) {
   				Cidade cidade = new Cidade();
   				parse(cidade, json);
   				service.save(cidade);
   				return ResponseEntity.ok(cidade);
   			} else {
   				return ResponseEntity.badRequest().body(null);
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
   		}
   	}
   
   	@PutMapping(path = "/cidades/{id}")
   	public ResponseEntity<Cidade> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
   		try {
   			if (isJSONValid(json.toString())) {
   				Cidade cidade = service.findById(id);
   				if (cidade == null) {
   					return ResponseEntity.notFound().build();
   				} else {
   					parse(cidade, json);
   					service.save(cidade);
   					return ResponseEntity.ok(cidade);
   				}
   			} else {
   				return ResponseEntity.badRequest().body(null);
   			}
   		} catch (Exception e) {
   			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
   		}
   	}
   
   	@DeleteMapping(path = "/cidades/{id}")
   	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
   
   		Cidade cidade = service.findById(id);
   		if (cidade == null) {
   			return ResponseEntity.notFound().build();
   		} else {
   			service.delete(id);
   			return ResponseEntity.noContent().build();
   		}
   	}
   }
   ```


##### (4) Execução e testes

9. Compilar e executar (**mvn spring-boot:run**)

    Verificar que as operações REST estão funcionais

10. Fim

    

#### Leituras adicionais

- - -

- Building REST services with Spring

  https://spring.io/guides/tutorials/rest/

  

- Building a RESTful Web Service
  
  https://spring.io/guides/gs/rest-service/
