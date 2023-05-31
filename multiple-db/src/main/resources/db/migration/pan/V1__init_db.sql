drop table if exists credit_card_pan;

create table credit_card_pan
(
    id                 bigint not null auto_increment,
    credit_card_number varchar(30),
    PRIMARY KEY (id)
)