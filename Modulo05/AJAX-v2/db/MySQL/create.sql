create database Cidades;

use Cidades;

create table Estado (id bigint not null auto_increment, nome varchar(30) not null, sigla varchar(2) not null, primary key (id));

create table Cidade (id bigint not null auto_increment, nome varchar(80) not null, estado_id bigint not null, primary key (id), foreign key (estado_id) references Estado(id));
