Drop table if exists cards;

Create table cards (
card_id int not null AUTO_INCREMENT,
card_number varchar(100) not null,
customer_id int not null,
card_type varchar(100) not null,
total_limit int not null,
amount_used int not null,
available_amount int not null,
create_dt date default null,
primary key (card_id)
);
