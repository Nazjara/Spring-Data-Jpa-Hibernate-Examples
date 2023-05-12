drop table if exists product;

create table product
(
    id               bigint not null auto_increment primary key,
    created_date      timestamp,
    last_modified_date timestamp,
    description      varchar(255),
    product_status           varchar(30)
) engine = InnoDB;