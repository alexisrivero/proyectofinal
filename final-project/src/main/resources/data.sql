insert into Product (`name`, price, stock) values ('manzana',5.00,5);
insert into Product (`name`, price, stock) values ('banana',2.00,20);
insert into Product (`name`, price, stock) values ('pera',3.00,25);
insert into Product (`name`, price, stock) values ('sandia',8.00,12);

insert into Users (email, user_Name, first_Name, last_Name, phone_Number) values ('lala@test.com','alexisr','Alexis','Rivero','+503 1234 5678');
insert into Users (email, user_Name, first_Name, last_Name, phone_Number) values ('test@gmail.com','carlosr','Carlos','Rivero','+503 5678 1234');

insert into Address (house_Number, street, city, `state`, user_id) values ('1639','De las Pleyades','Pinamar','Buenos Aires',1);
insert into Address (house_Number, street, city, `state`, user_id) values ('360','Constitucion','Pinamar','Buenos Aires',1);


insert into Payment_Method (`name`,founds, payment_Type, user_id) values ('my debit card',5.00,'Debit Card',1);
insert into Payment_Method (`name`,founds, payment_Type, user_id) values ('my credit card',100.00,'Credit Card',1);


