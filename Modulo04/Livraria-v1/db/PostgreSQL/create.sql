drop database Livraria;

create database Livraria;

\c Livraria;

drop sequence Editora_id_seq cascade;

CREATE SEQUENCE Editora_id_seq;

drop sequence Livro_id_seq cascade;

CREATE SEQUENCE Livro_id_seq;

drop table Livro cascade;

drop table Editora cascade;

create table Editora(id integer not null primary key default nextval('Editora_id_seq'), cnpj varchar(18) not null, nome varchar(256) not null);

create table Livro(id integer not null primary key default nextval('Livro_id_seq'), titulo varchar(256) not null, autor varchar(256) not null, ano integer not null, preco float not null, editora_id bigint not null, foreign key (editora_id) references Editora(id));

insert into Editora(cnpj, nome) values  ('55.789.390/0008-99', 'Companhia das Letras');

insert into Editora(cnpj, nome) values ('71.150.470/0001-40', 'Record');

insert into Editora(cnpj, nome) values ('32.106.536/0001-82', 'Objetiva');

insert into Livro(titulo, autor, ano, preco, editora_id) values ('Ensaio sobre a Cegueira', 'José Saramago', 1995, 54.9, 1);

insert into Livro(titulo, autor, ano, preco, editora_id) values  ('Cem anos de Solidão', 'Gabriel Garcia Márquez', 1977, 59.9, 2);

insert into Livro(titulo, autor, ano, preco, editora_id) values ('Diálogos Impossíveis', 'Luis Fernando Verissimo', 2012, 22.9, 3);

\q
