ALTER TABLE order_header
ADD COLUMN shipping_address varchar(30),
ADD COLUMN shipping_city varchar(30),
ADD COLUMN shipping_state varchar(30),
ADD COLUMN shipping_zip_code varchar(30),
ADD COLUMN bill_to_address varchar(30),
ADD COLUMN bill_to_city varchar(30),
ADD COLUMN bill_to_state varchar(30),
ADD COLUMN bill_to_zip_code varchar(30);