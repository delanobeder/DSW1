connect 'jdbc:derby:Cidades;create=true;user=root;password=root';

create table Estado (id bigint not null generated always as identity, nome varchar(30) not null, sigla varchar(2) not null, 
constraint Estado_PK primary key (id));

create table Cidade (id bigint not null generated always as identity, nome varchar(80) not null, estado_id bigint constraint FK_CIDADE references ESTADO, constraint Cidade_PK primary key (id));

disconnect;

quit;
