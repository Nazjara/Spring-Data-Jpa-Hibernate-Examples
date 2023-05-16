drop table if exists category;
drop table if exists product_category;

create table category
(
    id                 bigint not null auto_increment primary key,
    description        varchar(50),
    created_date       timestamp,
    last_modified_date timestamp
) engine = InnoDB;

create table product_category
(
    product_id  bigint not null,
    category_id bigint not null,
    primary key (product_id, category_id),
    constraint product_category_product_id_fk foreign key (product_id) references product (id),
    constraint product_category_category_id_fk foreign key (category_id) references category (id)
) engine = InnoDB;

INSERT INTO product (created_date, last_modified_date, description, product_status) values (NOW(), NOW(), 'PRODUCT1', 'NEW');
INSERT INTO product (created_date, last_modified_date, description, product_status) values (NOW(), NOW(), 'PRODUCT2', 'NEW');
INSERT INTO product (created_date, last_modified_date, description, product_status) values (NOW(), NOW(), 'PRODUCT3', 'NEW');
INSERT INTO product (created_date, last_modified_date, description, product_status) values (NOW(), NOW(), 'PRODUCT4', 'NEW');

insert into category (description, created_date, last_modified_date) VALUES ('CAT1', NOW(), NOW());
insert into category (description, created_date, last_modified_date) VALUES ('CAT2', NOW(), NOW());
insert into category (description, created_date, last_modified_date) VALUES ('CAT3', NOW(), NOW());

INSERT INTO product_category (product_id, category_id) SELECT p.id, c.id from product p, category c WHERE p.description = 'PRODUCT1' and c.description = 'CAT1';
INSERT INTO product_category (product_id, category_id) SELECT p.id, c.id from product p, category c WHERE p.description = 'PRODUCT2' and c.description = 'CAT1';
INSERT INTO product_category (product_id, category_id) SELECT p.id, c.id from product p, category c WHERE p.description = 'PRODUCT1' and c.description = 'CAT3';
INSERT INTO product_category (product_id, category_id) SELECT p.id, c.id from product p, category c WHERE p.description = 'PRODUCT4' and c.description = 'CAT3';
