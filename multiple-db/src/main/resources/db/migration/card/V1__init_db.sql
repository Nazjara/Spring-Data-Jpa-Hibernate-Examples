drop table if exists credit_card;

create table credit_card
(
    id              bigint not null auto_increment,
    cvv             varchar(20),
    expiration_date varchar(20),
    PRIMARY KEY (id)
)