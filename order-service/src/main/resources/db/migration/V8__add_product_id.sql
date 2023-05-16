alter table order_line add column product_id bigint;
alter table order_line add constraint product_pk foreign key (product_id) references product(id);