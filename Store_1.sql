-- Create the Online_store database
CREATE DATABASE IF NOT EXISTS Online_store;

-- Use the Online_store database
USE Online_store;

-- Create the Roles table

-- Create the Customer_Auth table
CREATE TABLE IF NOT EXISTS Auth (
                                    auth_id INT AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(50) NOT NULL unique ,
                                    password VARCHAR(100) NOT NULL,
                                    role_id enum('USER','ADMIN') NOT NULL
);

-- Create the Customers table
CREATE TABLE IF NOT EXISTS Customer (
                                        customer_id INT AUTO_INCREMENT PRIMARY KEY,
                                        first_name VARCHAR(50) NOT NULL,
                                        second_name VARCHAR(50) NOT NULL,
                                        email VARCHAR(50) NOT NULL unique,
                                        shipping_address VARCHAR(100) ,
                                        billing_address VARCHAR(100),
                                        FOREIGN KEY (email) REFERENCES Auth (username) ON DELETE CASCADE
);

-- Create the Categories table
CREATE TABLE IF NOT EXISTS Category (
                                        category_id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(50) NOT NULL unique ,
                                        description text
);

-- Create the Products table
CREATE TABLE IF NOT EXISTS Product (
                                       product_id INT AUTO_INCREMENT PRIMARY KEY,
                                       product_name VARCHAR(50) NOT NULL,
                                       price DECIMAL(10, 2) NOT NULL,
                                       description TEXT,
                                       picture varchar(255),
                                       quantity INT NOT NULL,
                                       category_id INT NOT NULL,
                                       CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES Category (category_id),
                                       CONSTRAINT chk_price CHECK (price >= 0),
                                       CONSTRAINT chk_quantity CHECK (quantity >= 0),
                                       INDEX idx_category_id (category_id),
                                       INDEX idx_quantity (quantity)
);


-- Create the Reviews table
CREATE TABLE IF NOT EXISTS Review (
                                      review_id INT AUTO_INCREMENT PRIMARY KEY,
                                      customer_id INT NOT NULL,
                                      product_id INT NOT NULL,
                                      grade enum('One','Two','Three','Four','Five') not null,
                                      review_text VARCHAR(255),
                                      review_date DATE NOT NULL,
                                      FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) ON DELETE CASCADE,
                                      FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE
);



-- Create the Staff table
CREATE TABLE IF NOT EXISTS Staff (
                                     staff_id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL,
                                     email VARCHAR(50) NOT NULL unique ,
                                     job_title VARCHAR(50) NOT NULL
);



-- Create the Payment_Methods table
CREATE TABLE IF NOT EXISTS Payment_Methods (
                                               payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
                                               name VARCHAR(50) NOT NULL unique,
                                               description text
);

-- Create the Orders table
CREATE TABLE IF NOT EXISTS `Order` (
                                       order_id INT AUTO_INCREMENT PRIMARY KEY,
                                       customer_id INT NOT NULL,
                                       order_date DATE NOT NULL,
                                       order_status VARCHAR(50) NOT NULL,
                                       CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);

CREATE TABLE IF NOT EXISTS Order_Product (
                                             order_id INT NOT NULL,
                                             product_id INT NOT NULL,
                                             quantity INT NOT NULL,
                                             PRIMARY KEY (order_id, product_id),
                                             CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES `Order` (order_id),
                                             CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Product (product_id),
                                             CONSTRAINT chk_pr_quantity CHECK (quantity > 0),
                                             INDEX idx_order_id (order_id),
                                             INDEX idx_product_id (product_id)
);

-- Create the Orders_Payments table
CREATE TABLE IF NOT EXISTS Order_Payment (
                                             order_id INT NOT NULL,
                                             payment_method_id INT NOT NULL,
                                             payment_link VARCHAR(255) NOT NULL,
                                             PRIMARY KEY (order_id, payment_method_id),
                                             FOREIGN KEY (order_id) REFERENCES `Order`(order_id) ON UPDATE CASCADE ON DELETE CASCADE,
                                             FOREIGN KEY (payment_method_id) REFERENCES Payment_Methods(payment_method_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Cart (
                                    customer_id INT NOT NULL,
                                    product_id INT NOT NULL,
                                    quantity int NOT NULL,
                                    PRIMARY KEY (customer_id,product_id),
                                    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE,
                                    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON UPDATE CASCADE ON DELETE CASCADE
);