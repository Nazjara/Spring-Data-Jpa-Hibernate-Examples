drop table if exists customer;

create table customer
(
    id                 bigint not null auto_increment primary key,
    contact_info       varchar(255),
    created_date       timestamp,
    last_modified_date timestamp
) engine = InnoDB;

ALTER TABLE order_header
DROP COLUMN customer_name;

ALTER TABLE order_header
ADD COLUMN customer_id bigint;

ALTER TABLE order_header
ADD CONSTRAINT order_header_customer_id_fk foreign key (customer_id) references customer (id);

