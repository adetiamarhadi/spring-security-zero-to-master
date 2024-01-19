create database if not exists defaultdb;

use defaultdb;

CREATE table users(
    id int not null auto_increment primary key,
    username varchar(45) not null,
    password varchar(45) not null,
    enabled int not null
);

CREATE table authorities(
    id int not null auto_increment primary key,
    username varchar(45) not null,
    authority varchar(45) not null
);

CREATE table customers(
    id int not null auto_increment primary key,
    email varchar(45) not null,
    pwd varchar(200) not null,
    `role` varchar(45) not null
);

INSERT INTO users (username, password, enabled) values ('user1', 'password', 1);

INSERT INTO authorities (username, authority) values ('user1', 'write');

INSERT INTO customers (email, pwd, `role`) values ('mail@adet.com', 'password', 'admin');