
CREATE DATABASE IF NOT EXISTS db_bank;

USE db_bank;

-- User table
CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Account table
CREATE TABLE IF NOT EXISTS account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    balance DECIMAL(10, 2) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Historic table
CREATE TABLE IF NOT EXISTS historic (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    transaction_type ENUM('withdraw', 'deposit', 'transfer') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- Inserting data into user table
INSERT INTO user (username, password) VALUES
('user1@gmail.com', 'password1'),
('user2@gmail.com', 'password2'),
('user3@gmail.com', 'password3');

-- Inserting data into account table
INSERT INTO account (user_id, balance) VALUES
(1, 1000.00),
(2, 500.00),
(3, 2000.00);

-- Inserting data into historic table
INSERT INTO historic (account_id, transaction_type, amount) VALUES
(1, 'deposit', 1000.00),
(2, 'deposit', 500.00),
(3, 'deposit', 2000.00);

select * from user;
select * from account;
select * from historic;


