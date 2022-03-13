-- CREATE SCHEMA IF NOT EXISTS schema;

DROP TABLE IF EXISTS person cascade;
DROP TABLE IF EXISTS company cascade;
-- DROP TABLE IF EXISTS schema.location_product;
-- DROP TABLE IF EXISTS schema.product_catalog;
-- DROP TABLE IF EXISTS schema.orderposition_order;
DROP TABLE IF EXISTS location cascade;
DROP TABLE IF EXISTS orderposition cascade;
DROP TABLE IF EXISTS warehouse cascade;
DROP TABLE IF EXISTS catalog cascade;
DROP TABLE IF EXISTS product cascade;
DROP TABLE IF EXISTS orders cascade;

CREATE TABLE IF NOT EXISTS person
(
    id         bigserial,
    first_name varchar(30) not null,
    sur_name   varchar(30) not null,
    balance    integer,
    address    varchar(255),
    phone      varchar(30),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS company
(
    id      bigserial,
    name    varchar(30) not null,
    balance integer,
    address varchar(255),
    phone   varchar(30),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS location
(
    id             bigserial,
    warehouse_name varchar(30)
    PRIMARY KEY (id)
);

CREATE TABLE catalog
(
    id   bigserial,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          bigserial,
    code        varchar(255),
    name        varchar(255),
    description varchar(255),
    quantity    int,
    price       int,
    catalog_id  int,
    location_id int,
    PRIMARY KEY (id)
);

ALTER TABLE product ADD CONSTRAINT product_fk1 FOREIGN KEY (catalog_id) REFERENCES catalog (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE product ADD CONSTRAINT product_fk2 FOREIGN KEY (location_id) REFERENCES location (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE IF NOT EXISTS orders
(
    id     bigserial,
    status varchar(255),
    date   varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orderposition
(
    id         bigserial,
    amount     int,
    product_id bigint,
    FOREIGN KEY (product_id) REFERENCES product (id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orderposition_order
(
    orderposition_id bigint,
    order_id         bigint,
    FOREIGN KEY (orderposition_id) REFERENCES orderposition (id) on delete cascade,
    FOREIGN KEY (order_id) REFERENCES orders (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS product_catalog
(
    product_id bigint,
    catalog_id bigint,
    FOREIGN KEY (product_id) REFERENCES product (id) on delete cascade,
    FOREIGN KEY (catalog_id) REFERENCES catalog (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS location_product
(
    location_id bigint,
    product_id  bigint,
    FOREIGN KEY (location_id) REFERENCES location (id) on delete cascade,
    FOREIGN KEY (product_id) REFERENCES product (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS warehouse
(
    id      bigserial,
    name    varchar(255),
    root_id bigint,
    FOREIGN KEY (root_id) REFERENCES catalog (id) on delete cascade,
    PRIMARY KEY (id)
);