Drop table if exists customer;
Drop table if exists accounts;

Create table customer (
customer_id int AUTO_INCREMENT PRIMARY KEY,
name  varchar(100) not null,
email varchar(100) not null,
mobile_number varchar(20) not null,
create_dt date default null
);

Create table accounts (
customer_id int not null,
account_number  int AUTO_INCREMENT PRIMARY KEY,
account_type varchar(100) not null,
branch_address varchar(200) not null,
create_dt date default null
);
