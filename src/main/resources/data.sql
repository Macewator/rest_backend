insert into user(id, first_name, last_name, pesel) values
(1, 'Katarzyna', 'Nowak', '89061403682'),
(2, 'Krzysztof', 'Kowalski', '88052385831'),
(3, 'Marek', 'Rostowski', '86091178568'),
(4, 'Jan', 'Kowalski', '90101222457'),
(5, 'Maciej', 'Zalewski', '87112242456'),
(6, 'Aneta', 'Korczyńska', '76061536749'),
(7, 'Wojciech', 'Sokolik', '82010877245');

insert into category(id,name,description) values
(1,'Laptopy','używane laptopy');

insert into asset(id,name,description,serial_number,category_id) values
(1,'Asus MateBook D','15 calowy laptop, i5, 8GB DDR3, kolor czarny','ASMBD198723',1),
(2,'Apple MacBook Pro 2015','13 calowy laptop, i5, 16GB DDR3, SSD256GB, kolor srebrny','MBP15X0925336',1),
(3,'Dell Inspirion 3576','13 calowy laptop, i7, 8GB DDR4, SSD 512GB, kolor czarny','DI3576XO526716',1);

insert into assignments(id, start, end, asset_id, user_id) values
  (1, '2017-10-08 15:00:00', '2018-10-08 15:00:00', 1, 1),
  (2, '2018-10-09 12:00:00', null, 2, 1),
  (3, '2018-10-10 16:00:00', null, 3, 1);

