## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 03 - Java Server Pages (JSPs)**

- - -

#### 02 - Diferentes tipos de erros com JSP

[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo03/ErrosJSP)

- - - -



1. Criar um novo projeto Maven Java Web com as seguintes características:

  - **groupId**: br.ufscar.dc.dsw 
  - **artifactId**: ErrosJSP

    Vamos criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento 

    - Abrir um terminal dentro da pasta do projeto (**ErrosJSP**) e executar o seguinte comando: 

	  ```sh
	  mvn -N io.takari:maven:wrapper
	  ```

      
    
    - Incluir as dependências (**javax.servlet**, **javax.servlet.jsp**) e plugin (**tomcat7-maven-plugin**), conforme discutido nos roteiros anteriores

2. Modificar o arquivo **index.jsp**, para introduzir um erro na diretiva `<%@page ...` (Ex: `<%#paje ...`)

3. Executar e verificar que produz um erro, e nem gera o servlet

4. Modificar o arquivo **index.jsp**, corrigindo o erro introduzido no passo 2

5. Modificar o arquivo **index.jsp**, introduzindo um erro de compilação

   ```jsp
   <% int a = 2 %>
   ```

6. Executar e verificar que novamente produz um erro, mas gera o servlet
   
   6.1. Mostrar o servlet e achar o erro
   
7. Corrigir o erro introduzido em 5, e adicionar código com erro de execução

   ```jsp
   <% 
     int a = 2;
     String str = null;
     a = str.length();
   %>
   ```

8. Executar e verificar que novamente produz erro, gerando o servlet
   8.1. Rastrear o erro até o JSP. Primeiro, através da saída do Tomcat, e verificar que ele aponta a linha do servlet

9. Fim



#### Leituras adicionais

- - -

- Java Server Pages (Caelum: Curso Java para Desenvolvimento Web)

  https://www.caelum.com.br/apostila-java-web/javaserver-pages

- JSP Tutorial

  https://www.javatpoint.com/jsp-tutorial

- Guide to JavaServer Pages (JSP)

  https://www.baeldung.com/jsp

  
