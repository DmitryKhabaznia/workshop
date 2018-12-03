DROP TABLE IF EXISTS order_products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS payment_types;
DROP TABLE IF EXISTS shipping_types;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS manufacturers;
DROP TABLE IF EXISTS categories;

CREATE TABLE roles(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(15) NOT NULL UNIQUE
);
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'user');

CREATE TABLE users(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	login VARCHAR(15) NOT NULL UNIQUE,
	email VARCHAR(50) NOT NULL,
    password VARCHAR(128) NOT NULL,
    gender BOOL NOT NULL,
    role_id INTEGER NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON UPDATE CASCADE
);

INSERT INTO users VALUES(DEFAULT, 'Petya', 'Petrov', 'notadmin', 'ivanov@gmail.com', '1234asdfF.', true, 1);
INSERT INTO users VALUES(DEFAULT, 'Ivan', 'Ivanov', 'admin', 'petrov@gmail.com', '1234asdfF.', true, 1);

CREATE TABLE categories(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO categories VALUES(DEFAULT, 'bed');
INSERT INTO categories VALUES(DEFAULT, 'sofa');
INSERT INTO categories VALUES(DEFAULT, 'table');
INSERT INTO categories VALUES(DEFAULT, 'chair');

CREATE TABLE manufacturers (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE
);
INSERT INTO manufacturers VALUES(DEFAULT, 'IKEA');
INSERT INTO manufacturers VALUES(DEFAULT, 'GSF');
INSERT INTO manufacturers VALUES(DEFAULT, 'FADO');

CREATE TABLE products(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	price DECIMAL(5,2) NOT NULL,
    description VARCHAR(200),
    image VARCHAR(30) NOT NULL UNIQUE,
	category_id INTEGER NOT NULL,
    manufacturer_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id) ON DELETE RESTRICT ON UPDATE CASCADE
);
INSERT INTO products VALUES(DEFAULT, 'bed one', 200.23, 'This is the best bed.', 'bed_1', 1, 1);
INSERT INTO products VALUES(DEFAULT, 'bed two', 85.45, 'This is the best bed.', 'bed_2', 1, 3);
INSERT INTO products VALUES(DEFAULT, 'bed tree', 150.03, 'This is the best bed.', 'bed_3', 1, 2);
INSERT INTO products VALUES(DEFAULT, 'bed four', 120.30, 'This is the best bed.', 'bed_4', 1, 1);
INSERT INTO products VALUES(DEFAULT, 'sofa one', 190.60, 'This is the best sofa.', 'sofa_1', 2, 1);
INSERT INTO products VALUES(DEFAULT, 'sofa two', 74.56, 'This is the best sofa.', 'sofa_2', 2, 2);
INSERT INTO products VALUES(DEFAULT, 'amazing sofa', 154.99, 'This is the best sofa.', 'sofa_3', 2, 3);
INSERT INTO products VALUES(DEFAULT, 'some sofa', 111.56, 'This is the best sofa.', 'sofa_4', 2, 3);
INSERT INTO products VALUES(DEFAULT, 'not bad sofa', 135.34, 'This is the best sofa.', 'sofa_5', 2, 2);
INSERT INTO products VALUES(DEFAULT, 'sofa', 200.19, 'This is the best sofa.', 'sofa_6', 2, 1);
INSERT INTO products VALUES(DEFAULT, 'sofa tree', 65.73, 'This is the best sofa.', 'sofa_7', 2, 1);
INSERT INTO products VALUES(DEFAULT, 'just sofa for your', 184.79, 'This is the best sofa.', 'sofa_8', 2, 2);
INSERT INTO products VALUES(DEFAULT, 'sofa four', 100.99, 'This is the best table.', 'sofa_9', 2, 3);
INSERT INTO products VALUES(DEFAULT, 'table one', 46.99, 'This is the best table.', 'table_1', 3, 2);
INSERT INTO products VALUES(DEFAULT, 'table two', 57.99, 'This is the best table.', 'table_2', 3, 1);
INSERT INTO products VALUES(DEFAULT, 'table tree', 120.11, 'This is the best table.', 'table_3', 3, 1);
INSERT INTO products VALUES(DEFAULT, 'table four', 140.12, 'This is the best table.', 'table_4', 3, 2);
INSERT INTO products VALUES(DEFAULT, 'table five', 138.14, 'This is the best table.', 'table_5', 3, 3);
INSERT INTO products VALUES(DEFAULT, 'table six', 23.54, 'This is the best table.', 'table_6', 3, 1);
INSERT INTO products VALUES(DEFAULT, 'table seven', 15.35, 'This is the best table.', 'table_7', 3, 2);
INSERT INTO products VALUES(DEFAULT, 'table eight', 74.50, 'This is the best table.', 'table_8', 3, 3);
INSERT INTO products VALUES(DEFAULT, 'chair one', 30.20, 'This is the best chair.', 'chair_1', 4, 2);
INSERT INTO products VALUES(DEFAULT, 'chair two', 59.12, 'This is the best chair.', 'chair_2', 4, 1);
INSERT INTO products VALUES(DEFAULT, 'chair tree', 140.13, 'This is the best chair.', 'chair_3', 4, 3);
INSERT INTO products VALUES(DEFAULT, 'chair four', 170.56, 'This is the best chair.', 'chair_4', 4, 1);
INSERT INTO products VALUES(DEFAULT, 'chair five', 50.99, 'This is the best chair.', 'chair_5', 4, 2);
INSERT INTO products VALUES(DEFAULT, 'six chair', 49.99, 'This is the best chair.', 'chair_6', 4, 3);
INSERT INTO products VALUES(DEFAULT, 'seven chair', 84.99, 'This is the best chair.', 'chair_7', 4, 3);

CREATE TABLE statuses (
	id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO statuses VALUES(0, 'registered');
INSERT INTO statuses VALUES(1, 'confirmed');
INSERT INTO statuses VALUES(2, 'onprocess');
INSERT INTO statuses VALUES(3, 'delivered');
INSERT INTO statuses VALUES(4, 'done');
INSERT INTO statuses VALUES(5, 'canceled');

CREATE TABLE payment_types (
	id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);
INSERT INTO payment_types VALUES(0, 'cash');
INSERT INTO payment_types VALUES(1, 'card');

CREATE TABLE shipping_types (
	id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);
INSERT INTO shipping_types VALUES(0, 'pick up');
INSERT INTO shipping_types VALUES(1, 'delivery');

CREATE TABLE orders (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    date TIMESTAMP NOT NULL,
    status_id INTEGER NOT NULL,
    status_description VARCHAR(100) NOT NULL,
    user_id INTEGER NOT NULL,
    shipping_type_id INTEGER NOT NULL,
    delivery_address VARCHAR(300) DEFAULT NULL,
    payment_type_id INTEGER NOT NULL,
    FOREIGN KEY (status_id) REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE,
    FOREIGN KEY (shipping_type_id) REFERENCES shipping_types(id) ON UPDATE CASCADE,
    FOREIGN KEY (payment_type_id) REFERENCES payment_types(id) ON UPDATE CASCADE
);

CREATE TABLE order_products (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    count INTEGER NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE
);