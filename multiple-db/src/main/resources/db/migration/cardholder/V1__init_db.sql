drop table if exists credit_card_holder;

create table credit_card_holder
(
    id         bigint not null auto_increment,
    first_name varchar(30),
    last_name  varchar(30),
    zip_code   varchar(10),
    PRIMARY KEY (id)
)