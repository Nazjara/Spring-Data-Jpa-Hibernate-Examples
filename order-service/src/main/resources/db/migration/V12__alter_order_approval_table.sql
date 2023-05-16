ALTER TABLE order_approval
ADD COLUMN order_header_id bigint;

ALTER TABLE order_approval
ADD CONSTRAINT order_approval_order_header_id_fk foreign key (order_header_id) references order_header (id);

