drop table if exists order_approval;

create table order_approval
(
    id                 bigint not null auto_increment primary key,
    approved_by       varchar(50),
    created_date       timestamp,
    last_modified_date timestamp
) engine = InnoDB;

ALTER TABLE order_header
ADD COLUMN order_approval_id bigint;

ALTER TABLE order_header
ADD CONSTRAINT order_header_order_approval_id_fk foreign key (order_approval_id) references order_approval (id);

