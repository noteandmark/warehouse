TRUNCATE TABLE schema.person RESTART IDENTITY;
insert into schema.warehouse (name)
values ('main warehouse');
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
