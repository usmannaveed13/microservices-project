Drop table if exists loans;

Create table loans (
loan_number int not null AUTO_INCREMENT,
customer_id int not null,
start_dt date not null,
loan_type varchar(100) not null,
total_loan int not null,
amount_paid int not null,
outstanding_amount int not null,
create_dt date default null,
primary key (loan_number)
);
