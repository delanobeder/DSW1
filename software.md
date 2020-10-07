## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1
**Prof. Delano M. Beder (UFSCar)**

**Dicas sobre a instalação de softwares**



- - -
#### JDK
- - -

1. Instalar o Java Development Kit
2. Há duas opções atualmente, da Oracle e OpenJDK

    ```
    https://www.oracle.com/technetwork/java/javase/downloads/index.html

    https://openjdk.java.net/
    ```

3. Para testar se funcionou, executar em um terminal

    ```sh
    java -version
    javac -version
    ```




- - -
#### Apache Maven
- - -

Instalação Windows: https://dicasdejava.com.br/como-instalar-o-maven-no-windows/

Instalação Ubuntu: https://www.hostinger.com.br/tutoriais/install-maven-ubuntu/



1. Baixar o Maven de ```https://maven.apache.org```
2. O Maven precisa estar no ```PATH```, e precisa conhecer a variável ```JAVA_HOME```
- Rodar os seguintes comandos no terminal (substituir pelos caminhos corretos):

    ```sh
    export JAVA_HOME=$HOME/Programs/jdk1.8.0_212/
    export PATH=$HOME/Programs/apache-maven-3.6.1/bin:$PATH
    ```

- Esses comandos precisam ser executados sempre que um novo terminal for iniciado. É também possível configurar permanentemente (consulte a documentação do Sistema Operacional para como fazer isso).
- No Linux/Mac, uma boa prática é criar um script temporário para evitar de mexer nas configurações globais
- Criar um arquivo useMaven.sh, com permissão de execução, com o conteúdo acima
- Antes de iniciar o uso do Maven, executar

    ```sh
    source useMaven.sh
    ```

3. Para testar se funcionou, executar:

    ```sh
    mvn -version
    ```

4. Para compilar e gerar um arquivo .war, utilizar o seguinte comando

    ```sh
    mvn clean package
    ```

5. Opcionalmente, é possível criar um Maven Wrapper para que o projeto tenha sua própria instalação do Maven, fixando assim a versão e evitando problemas de versionamento. Também evita a necessidade de executar o comando ```source``` a cada novo terminal.

- Abrir um terminal dentro da pasta do projeto e executar o seguinte comando

    ```sh
    mvn -N io.takari:maven:wrapper
    ```

- Depois disso, será criado um arquivo executável ```mvnw```. Ao invés de executar ```mvn```, basta executar esse ```mvnw```.
- Esse arquivo ```mvnw``` e demais criados devem ser compartilhados com a equipe no repositório de controle de versões



- - -
#### Apache Tomcat
- - -

1. Baixar o arquivo zip de ```http://tomcat.apache.org/```

2. Descompactar em uma pasta sem espaços ou acentos (diretório `<instalação tomcat>`)

3. Explorar a estrutura de diretórios (diretório `<instalação tomcat>`)

4. Abrir o arquivo `conf/server.xml` (configuração do servidor tomcat)

5. Abrir o arquivo `conf/tomcat-users.xml` (configuração dos usuários)

6. Rodar o tomcat
   
   6.1. No windows
  
      - Abrir o Windows PowerShell
      - Rodar `$env:JAVA_HOME="C:\<caminho_para_java>\jreXXX"`
      - Executar `startup.bat` (ou `catalina.bat run`) no diretório `<instalação tomcat>\bin`
   
   6.2. No Linux/Mac
   
      - Abrir um terminal
      
      - Rodar `export JAVA_HOME="/<caminho_para_java>/jreXXX"`
      
      - Executar `startup.sh` (ou `catalina.sh run`) no diretório `<instalação tomcat>\bin` 
         [pode ser necessário dar permissão – executar comando **chmod 755**]

7. Abrir em um navegador a URL: http://localhost:8080
   
   7.1. Observar que não tem acesso ao **manager**
   
   7.2. Modificar `conf/tomcat-users.xml`, adicionando a seguinte linha (obs: será importante na execução de nossos exemplos)

    ```xml
    <user username="admin" password="admin" roles="manager-gui, manager-script" />
    ```
   7.3. Parar tomcat e reiniciar, e tentar novamente

8. Acessar aplicações **manager** e **status**

9. Fim



- - -
#### Apache Derby
- - -

1. Baixar o servidor: `http://db.apache.org/derby/`

2. Descompactar o conteúdo em alguma pasta (`<instalação Derby>`)

3. Para iniciar/interromper o servidor, abrir o terminal e executar os seguintes comandos (substituir o caminho do `derby.system.home` por uma pasta onde serão criados os bancos de dados e a instalação do Derby onde foi descompactado seu conteúdo):

    ```sh
    java -Dderby.system.home=<pasta com bancos de dados> -jar <instalação Derby>/lib/derbyrun.jar server start

    java -Dderby.system.home=<pasta com bancos de dados> -jar <instalação Derby>/lib/derbyrun.jar server shutdown
    ```

- No exemplo a seguir, o servidor está instalado na pasta ```/usr/lib/jvm/db-derby-10.15.2.0-bin``` e irá buscar os bancos de dados na pasta ```/home/delano/DerbyDatabases```:

    ```sh
    java -Dderby.system.home=/home/delano/DerbyDatabases -jar /usr/lib/jvm/db-derby-10.15.2.0-bin/lib/derbyrun.jar server start
    ```

4. Para criar um banco de dados, executar em um terminal (não é necessário que o servidor esteja rodando):

    ```sh
    java -Dderby.system.home=<caminho bancos de dados> -Dij.protocol=jdbc:derby: -jar <instalação Derby>/lib/derbyrun.jar ij
    ```

5. Uma vez executando o comando `ij`, executar o seguinte comando (obs: `meubanco` é o nome do banco de dados. `create=true` indica que o banco será criado, caso não exista):

    ```
    CONNECT 'meubanco;create=true';
    ```

- Caso queira criar o banco com um usuário e senha:

    ```
    CONNECT 'meubanco;create=true;user=usuario;password=senha';
    ```

- Após esse comando, uma pasta chamada "meubanco" será criada no diretório especificado em `derby.system.home`

- Caso não queira criar um novo banco, apenas conectar, executar o comando:

    ```sql
    CONNECT 'meubanco';
    ```

6. Uma vez conectado, é possível executar comandos SQL:

    ```sql
    CREATE TABLE Teste (codigo int, nome varchar(100));
    ````

7. Para sair do `ij`, executar:

    ```sql
    exit;
    ```



- - -
#### Eclipse
- - -

1. Baixar a versão *Eclipse IDE for Enterprise Java Developers* em  https://www.eclipse.org/downloads/packages/release/2020-06/r/eclipse-ide-enterprise-java-developers
2. Descompactar o conteúdo em alguma pasta (`<instalação eclipse>`)
3. Para iniciar o eclipse, clique no arquivo executável (**eclipse.exe**, **eclipse**, etc) presente no diretório `<instalação eclipse>` 



- - -
#### Spring Tools Suite
- - -

1. Baixar a versão *Spring Tools 4 for Eclipse* em https://spring.io/tools/
2. Descompactar o conteúdo em alguma pasta (`<instalação sts>`)
3. Para iniciar o sts, clique no arquivo executável (**SpringToolSuite4.exe**, **SpringToolSuite4**, etc) presente no diretório `<instalação sts>` 
