insert into person (first_name, sur_name, balance, address, phone)
values ('Ben6test', 'Elliot', 10, 'str.Toporkov,12', '3801112233');

insert into company (name, balance, address, phone)
values ('Some Company', 100000, '10, Some Street', '3801234567');

insert into catalog (name)
values ('spots');

insert into catalog (name)
values ('timers');

insert into product (code, name, description, quantity, price, catalog_id)
values ('392005', 'Feron JCD9 220v/35w', 'spot light', 1000, 120, 1);

insert into location (warehouse_name)
values ('90 warehouse', 5);

insert into orders (status, date)
values ('not_processed', '08-01-2022 18:00:00');

insert into orderposition (amount, product_id) values (100, 1);

insert into warehouse (name)
values ('main');