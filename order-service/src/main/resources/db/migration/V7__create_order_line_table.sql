drop table if exists order_line;

create table order_line
(
    id                 bigint not null auto_increment primary key,
    quantity_ordered   int,
    created_date       timestamp,
    last_modified_date timestamp,
    order_header_id    bigint,
    constraint order_header_pk foreign key (order_header_id) references order_header (id)
) engine = InnoDB;