## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. Delano M. Beder (UFSCar)**

**Módulo 02 - Java Servlets**
- - -

#### 01 - Instalando o Tomcat


* * *



1. Baixar o arquivo zip - http://tomcat.apache.org (preferencialmente a versão 9.0.37)

2. Descompactar em uma pasta (`<diretório Tomcat>`) sem espaços ou acentos

3. Explorar a estrutura de diretórios (`<diretório Tomcat>`)

4. Abrir o arquivo `conf/server.xml` (configuração do servidor tomcat)

5. Abrir o arquivo `conf/tomcat-users.xml` (configuração dos usuários)

6. Rodar o tomcat
    - No windows
        - Abrir o Windows PowerShell
        - Rodar `$env:JAVA_HOME="C:\<caminho_para_java>\jreXXX"`
        - Entrar no diretório `<diretório Tomcat>\bin`
        - Executar `startup.bat` (ou `catalina.bat run`)
        
    - No Linux/Mac
        - Abrir um terminal
        - Rodar `echo $JAVA_HOME ou export JAVA_HOME="/<caminho_para_java>/jreXXX"`
        - 6.2.3. Entrar no diretório `<diretório Tomcat>/bin`
        - 6.2.4. Executar `startup.sh` (ou `catalina.sh run`) 
        [pode ser necessário dar permissão – executar comando **chmod 755**]
7. Abrir em um navegador http://localhost:8080 
   7.1. Observar que não tem acesso ao manager
   7.2. Modificar `conf/tomcat-users.xml`, adicionando a seguinte linha (obs: será importante na execução dos exemplos de aula)
    ```xml
    <user username="admin" password="admin" roles="manager-gui, manager-script" />
    ```
    7.3. Parar tomcat e reiniciar, e tentar novamente

8. Acessar aplicações **manager** e **status**

9. Fim



#### Leituras adicionais

- - -

- Tomcat Setup

  https://tomcat.apache.org/tomcat-9.0-doc/setup.html
