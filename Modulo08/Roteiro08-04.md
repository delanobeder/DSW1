## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA** 

- - -

#### 04 - Autenticação/Autorização 

**Spring Security (Autenticação através acesso a banco de dados)**

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo08/LoginMVC-v2)

- - -



1. Fazer uma cópia do diretório da aplicação **LoginMVC** (Roteiro 08-03).

   ```sh
   % cp -r LoginMVC LoginMVC-v2
   ```

   

2. Abrir a aplicação cópia

3. Adicionar a dependência para o **Spring Data JPA** (arquivo pom.xml)

   ```xml
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   ```

   

4. Utilizando o **Apache Derby** (se preferir utilizar o **MySQL** pule para o passo 3)

   4.1. Criar novo banco de dados **Login** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/Derby/create.sql**

   ```sql
   connect 'jdbc:derby:Login;create=true;user=root;password=root';
   
   disconnect;
   
   quit;
   ```

   <div style="page-break-after: always"></div>

   4.2. Em um terminal no diretório do projeto (<DB_HOME> é o local em que serão armazenados os bancos de dados do DERBY e $DERBY_HOME é a instalação do Derby -- onde foi descompactado seu conteúdo)

   ```sh
   % java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij
   versão ij 10.15
   ij> run 'db/Derby/create.sql';
   ij> connect 'jdbc:derby:Login;create=true;user=root;password=root';
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

   4.5. No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados criado

   ```properties
   # DERBY
   spring.datasource.url=jdbc:derby://localhost:1527/Login
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver
   
   # JPA
   spring.jpa.hibernate.ddl-auto = create
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   
   # THYMELEAF
   spring.thymeleaf.cache = false
   
   # I18n
   spring.messages.basename = messages
   ```

<div style="page-break-after: always"></div>

5. Utilizando o  **MySQL** (pule esse passo, se o **Apache Derby** já foi configurado)

   5.1. Criar novo banco de dados **Login** (usuário: **root**, senha: **root**) e popular com alguns dados

   - Crie o arquivo **db/MySQL/create.sql**

   ```sql
   create database Login;
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
   
   mysql> source db/MySQL/create.sql;
   Query OK, 1 row affected (0.02 sec)
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
   5.4. No arquivo **src/main/resources/application.properties**, iremos configurar o projeto para acessar o banco de dados criado

   ```properties
   # MYSQL
   spring.datasource.url = jdbc:mysql://localhost:3306/Login
   spring.datasource.username = root
   spring.datasource.password = root
   
   # JPA
   spring.jpa.hibernate.ddl-auto = create
   spring.jpa.show-sql = true
   spring.jpa.open-in-view = true
   spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
   
   # THYMELEAF
   spring.thymeleaf.cache = false
   
   # I18n
   spring.messages.basename = messages
   ```

6. Atualizar a classe **br.ufscar.dc.dsw.config.WebSecurityConfig**

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
   				.antMatchers("/", "/index", "/error").permitAll()
   				.antMatchers("/login/**", "/js/**", "/css/**").permitAll()
               	.antMatchers("/image/**", "/webjars/**").permitAll()
   				.antMatchers("/admin/**").hasRole("ADMIN")
   				.antMatchers("/user/**").hasRole("USER")
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

  

7. Adicionar a classe **br.ufscar.dc.dsw.domain.Usuario** (entidade JPA)

   ```java
   package br.ufscar.dc.dsw.domain;
   
   import javax.persistence.Column;
   import javax.persistence.Entity;
   import javax.persistence.GeneratedValue;
   import javax.persistence.GenerationType;
   import javax.persistence.Id;
   import javax.persistence.Table;
    
   @Entity
   @Table(name = "Usuario")
   public class Usuario {
    
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       @Column(nullable = false, length = 45)
       private String username;
       @Column(nullable = false, length = 64)
       private String password;
       @Column(nullable = false, length = 45)
       private String role;
       @Column(nullable = false)
       private boolean enabled;
   	
       public Long getId() {
   		return id;
   	}
   	
       public void setId(Long id) {
   		this.id = id;
   	}
   	
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
   
7. Adicionar a classe **br.ufscar.dc.dsw.dao.IUsuarioDAO**

   ```java
   package br.ufscar.dc.dsw.dao;
   
   import org.springframework.data.jpa.repository.Query;
   import org.springframework.data.repository.CrudRepository;
   import org.springframework.data.repository.query.Param;
   
   import br.ufscar.dc.dsw.domain.Usuario;
   
   public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
       @Query("SELECT u FROM Usuario u WHERE u.username = :username")
       public Usuario getUserByUsername(@Param("username") String username);
   }
   ```

8. Adicionar a classe **br.ufscar.dc.dsw.security.UsuarioDetails**

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
    
       private Usuario usuario;
        
       public UsuarioDetails(Usuario usuario) {
           this.user = usuario;
       }
    
       @Override
       public Collection<? extends GrantedAuthority> getAuthorities() {
           SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole());
           return Arrays.asList(authority);
       }
    
       @Override
       public String getPassword() {
           return usuario.getPassword();
       }
    
       @Override
       public String getUsername() {
           return usuario.getUsername();
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
   }
   ```



10. Adicionar a classe **br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl**

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
           Usuario usuario = dao.getUserByUsername(username);
            
           if (usuario == null) {
               throw new UsernameNotFoundException("Could not find user");
           }
            
           return new UsuarioDetails(usuario);
       }
    
   }
   ```


11. Atualizar a classe **LoginMVCApplication** 

    ```java
    package br.ufscar.dc.dsw;
    
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    
    import br.ufscar.dc.dsw.dao.IUsuarioDAO;
    import br.ufscar.dc.dsw.domain.Usuario;
    
    @SpringBootApplication
    public class LoginMVCApplication {
    
    	public static void main(String[] args) throws Throwable {
    		SpringApplication.run(LoginMVCApplication.class, args);
    	}
    
    	@Bean
    	public CommandLineRunner demo(IUsuarioDAO dao, BCryptPasswordEncoder encoder) {
    		return (args) -> {
    			
    			Usuario u1 = new Usuario();
    			u1.setUsername("user");
    			u1.setPassword(encoder.encode("user"));
    			u1.setRole("ROLE_USER");
    			u1.setEnabled(true);
    			dao.save(u1);
    			
    			Usuario u2 = new Usuario();
    			u2.setUsername("admin");
    			u2.setPassword(encoder.encode("admin"));
    			u2.setRole("ROLE_ADMIN");
    			u2.setEnabled(true);
    			dao.save(u2);			 
    		};
    	}
    }
    ```



12. Executar (**mvn spring-boot:run**) e testar

* Testar, abrindo o browser no endereço: http://localhost:8080
* Acessar a página principal (não precisa autenticação)
* Acessar a página segura (precisa autenticação)
  * Logar admin/user e verificar que uma mensagem de erro é apresentada (senha inválida)
  * Logar admin/admin (Login com sucesso)
    * Tentar acessar a área de administrador (papel ROLE_ADMIN): Sucesso
    * Tentar acessar a área de usuário comum (papel ROLE_USER): mensagem de erro é apresentada (acesso não autorizado)
    * Logout
  * Logar user/admin e verificar que uma mensagem de erro é apresentada (senha inválida)
    * Logar user/user (Login com sucesso)
    * Tentar acessar a área de administrador (papel ROLE_ADMIN): mensagem de erro é apresentada (acesso não autorizado)
    * Tentar acessar a área de usuário comum (papel ROLE_USER): Sucesso 
    * Logout

13. Fim

  #### Leituras adicionais

- - -

  - Spring Security

    https://spring.io/projects/spring-security

    

  - Securing a Web Application

    https://spring.io/guides/gs/securing-web/

    

  - Thymeleaf + Spring Security integration basics
    
    https://www.thymeleaf.org/doc/articles/springsecurity.html
    

    
  - Spring Boot Security Authentication with JPA, Hibernate and MySQL

    https://www.codejava.net/frameworks/spring-boot/spring-boot-security-authentication-with-jpa-hibernate-and-mysql
