CREATE DATABASE dualsys;
USE dualsys;

CREATE TABLE invoice_item (
id BIGINT,
price INT,
product_name VARCHAR(50),
quantity INT,
total_price INT,
invoice_id BIGINT
);

CREATE TABLE invoice (
id BIGINT,
comment VARCHAR(250)
customer_name VARCHAR(50),
due_date DATE,
issue_date DATE,
total_price INT
);

INSERT INTO `dualsys`.`invoice` (`id`, `comment`, `customer_name`, `due_date`, `issue_date`, `total_price`) VALUES ('1', 'payed', 'John Smith', '2022-03-05', '2022-03-05', '10000');
INSERT INTO `dualsys`.`invoice` (`id`, `comment`, `customer_name`, `due_date`, `issue_date`, `total_price`) VALUES ('2', 'pending', 'Jane Smith', '2022-03-10', '2022-03-05', '5000');
INSERT INTO `dualsys`.`invoice` (`id`, `comment`, `customer_name`, `due_date`, `issue_date`, `total_price`) VALUES ('3', 'partially payed', 'John Doe', '2022-03-07', '2022-03-01', '65000');
INSERT INTO `dualsys`.`invoice` (`id`, `comment`, `customer_name`, `due_date`, `issue_date`, `total_price`) VALUES ('4', 'payed', 'Jane Doe', '2022-03-02', '2022-02-28', '5000');
INSERT INTO `dualsys`.`invoice` (`id`, `comment`, `customer_name`, `due_date`, `issue_date`, `total_price`) VALUES ('5', 'pending', 'James Bond', '2022-03-04', '2022-03-05', '150000');


INSERT INTO `dualsys`.`invoice_item` (`id`, `price`, `product_name`, `quantity`, `total_price`, `invoice_id`) VALUES ('1', '10000', 'T-shirt', '1', '10000', '1');
INSERT INTO `dualsys`.`invoice_item` (`id`, `price`, `product_name`, `quantity`, `total_price`, `invoice_id`) VALUES ('2', '2500', 'Shoe', '2', '5000', '2');
INSERT INTO `dualsys`.`invoice_item` (`id`, `price`, `product_name`, `quantity`, `total_price`, `invoice_id`) VALUES ('3', '65000', 'Laptop', '1', '65000', '3');
INSERT INTO `dualsys`.`invoice_item` (`id`, `price`, `product_name`, `quantity`, `total_price`, `invoice_id`) VALUES ('4', '5000', 'Dress', '1', '5000', '4');
INSERT INTO `dualsys`.`invoice_item` (`id`, `price`, `product_name`, `quantity`, `total_price`, `invoice_id`) VALUES ('5', '75000', 'Cell phone', '2', '150000', '5');
