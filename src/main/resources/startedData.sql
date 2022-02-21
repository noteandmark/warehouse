-- !!! unscramble on first startup to create:
--CREATE DATABASE warehouse;
--CREATE SCHEMA IF NOT EXISTS schema;


-- !!! unscramble, if want to drop tables before each application startup:
--DROP TABLE IF EXISTS schema.person cascade;
--DROP TABLE IF EXISTS schema.company cascade;
--DROP TABLE IF EXISTS schema.location cascade;
--DROP TABLE IF EXISTS schema.orderposition cascade;
--DROP TABLE IF EXISTS schema.warehouse cascade;
--DROP TABLE IF EXISTS schema.catalog cascade;
--DROP TABLE IF EXISTS schema.product cascade;
--DROP TABLE IF EXISTS schema.orders cascade;

CREATE TABLE IF NOT EXISTS schema.person
(
    id         bigserial,
    first_name varchar(30) not null,
    sur_name   varchar(30) not null,
    balance    integer,
    address    varchar(255),
    phone      varchar(30),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.company
(
    id      bigserial,
    name    varchar(30) not null,
    balance integer,
    address varchar(255),
    phone   varchar(30),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.location
(
    id             bigserial,
    warehouse_name varchar(30),
    shelf_number   int,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.product
(
    id          bigserial,
    code        varchar(255),
    name        varchar(255),
    description varchar(255),
    quantity    int,
    price       int,
    catalog_id  bigint,
    location_id bigint,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.orders
(
    id     bigserial,
    status varchar(255),
    date   varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.orderposition
(
    id         bigserial,
    amount     int,
    product_id bigint,
    FOREIGN KEY (product_id) REFERENCES schema.product (id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.orderposition_order
(
    orderposition_id bigint,
    order_id         bigint,
    FOREIGN KEY (orderposition_id) REFERENCES schema.orderposition (id) on delete cascade,
    FOREIGN KEY (order_id) REFERENCES schema.orders (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS schema.catalog
(
    id         bigserial,
    name       varchar(255),
    product_id bigint,
    FOREIGN KEY (product_id) REFERENCES schema.product (id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS schema.product_catalog
(
    product_id bigint,
    catalog_id bigint,
    FOREIGN KEY (product_id) REFERENCES schema.product (id) on delete cascade,
    FOREIGN KEY (catalog_id) REFERENCES schema.catalog (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS schema.location_product
(
    location_id bigint,
    product_id  bigint,
    FOREIGN KEY (location_id) REFERENCES schema.location (id) on delete cascade,
    FOREIGN KEY (product_id) REFERENCES schema.product (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS schema.warehouse
(
    id      bigserial,
    name    varchar(255),
    root_id bigint,
    FOREIGN KEY (root_id) REFERENCES schema.catalog (id) on delete cascade,
    PRIMARY KEY (id)
);

insert into schema.person (first_name, sur_name, balance, address, phone)
values ('Ben', 'Elliot', 10, 'str.Toporkov,12', '3801512633'),
       ('Adrian', 'Anatols', 200, 'str.Luzhnij,88', '3801312233'),
       ('Joe', 'Davi', 30, 'str.Valley,6', '3802122231'),
       ('Benedict', 'Ismailov', 10, 'str.Mersi,16', '3805166233'),
       ('Lui', 'Funes', 40, 'str.Kaunas,43', '3801512277'),
       ('Fedor', 'Suvorov', 70, 'str.Dorgi,34', '3801312733'),
       ('Boris', 'Poterskiy', 20, 'str.Strange,65', '3808119033'),
       ('Pavel', 'Gromov', 15, 'str.Diksi,33', '3801110033'),
       ('Igor', 'Pavlov', 80, 'str.Fedeski,1', '3801144234'),
       ('Silvio', 'Enriko', 150, 'str.Arnautsk,84', '3801332273');
