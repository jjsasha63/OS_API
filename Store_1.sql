-- Create the Online_store database
CREATE DATABASE IF NOT EXISTS Online_store;

-- Use the Online_store database
USE Online_store;

-- Create the Customers table
CREATE TABLE IF NOT EXISTS Customers (
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  second_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  shipping_address VARCHAR(100) NOT NULL,
  billing_address VARCHAR(100) NOT NULL
);

-- Create the Customer_Auth table
CREATE TABLE IF NOT EXISTS Customer_Auth (
  customer_auth_id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  username VARCHAR(50) NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);

-- Create the Categories table
CREATE TABLE IF NOT EXISTS Categories (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL
);

-- Create the Products table
CREATE TABLE IF NOT EXISTS Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES Categories (category_id),
    CONSTRAINT chk_price CHECK (price >= 0),
    CONSTRAINT chk_quantity CHECK (quantity >= 0),
    INDEX idx_category_id (category_id),
    INDEX idx_quantity (quantity)
);


-- Create the Reviews table
CREATE TABLE IF NOT EXISTS Reviews (
  review_id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  product_id INT NOT NULL,
  review_text VARCHAR(255) NOT NULL,
  review_date DATE NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

-- Create the Staff table
CREATE TABLE IF NOT EXISTS Staff (
  staff_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  job_title VARCHAR(50) NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

-- Create the Staff_Auth table
CREATE TABLE IF NOT EXISTS Staff_Auth (
  staff_auth_id INT AUTO_INCREMENT PRIMARY KEY,
  staff_id INT NOT NULL,
  username VARCHAR(50) NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  FOREIGN KEY (staff_id) REFERENCES Staff(staff_id) ON DELETE CASCADE
);

-- Create the Roles table
CREATE TABLE IF NOT EXISTS Roles (
  role_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL
);

-- Create the Payment_Methods table
CREATE TABLE IF NOT EXISTS Payment_Methods (
  payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL
);

-- Create the Orders table
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date DATE NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES Customers (customer_id)
);

CREATE TABLE Order_Products (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES Products (product_id),
    CONSTRAINT chk_quantity CHECK (quantity > 0),
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);

-- Create the Orders_Payments table
CREATE TABLE Orders_Payments (
  order_id INT NOT NULL,
  payment_method_id INT NOT NULL,
  payment_link VARCHAR(255) NOT NULL,
  PRIMARY KEY (order_id, payment_method_id),
  FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (payment_method_id) REFERENCES Payment_Methods(payment_method_id) ON UPDATE CASCADE ON DELETE CASCADE
);