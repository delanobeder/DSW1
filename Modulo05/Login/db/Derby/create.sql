connect 'jdbc:derby:Login;create=true;user=root;password=root';

create table Usuario(id bigint not null generated always as identity, nome varchar(256) not null, login varchar(20) not null unique, senha varchar(64) not null, papel varchar(10), CONSTRAINT Usuario_PK PRIMARY KEY (id));

insert into Usuario(nome, login, senha, papel) values ('Administrador', 'admin', 'admin', 'ADMIN');

insert into Usuario(nome, login, senha, papel) values ('Usuario', 'user', 'user', 'USER');

disconnect;

quit;
