delete from quote;
delete from stock;


insert into stock (id, stock_id) values ('159', 'petr4');
insert into stock (id, stock_id) values ('987', 'vale5');

insert into quote (date, price, stock_id) values ('2015-10-25', 10, '159');
insert into quote (date, price, stock_id) values ('2015-10-26', 15, '987');