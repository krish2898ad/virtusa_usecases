-- FreshMart - Stock Health Report

-- CREATE THE TABLES

-- Categories table: store data relating to product categories and departments
CREATE TABLE Categories (
CategoryID   INT PRIMARY KEY,
CategoryName VARCHAR2(100) NOT NULL,
Department   VARCHAR2(100)
);

-- Products table: store data related to products
CREATE TABLE Products (
ProductID    INT PRIMARY KEY,
ProductName  VARCHAR2(150) NOT NULL,
CategoryID   INT,
StockCount   INT NOT NULL,
CostPrice    NUMBER(10,2),
SellingPrice NUMBER(10,2),
ExpiryDate   DATE,
FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- SalesTransactions table: store data related to sales
CREATE TABLE SalesTransactions (
TransactionID INT PRIMARY KEY,
ProductID     INT,
QuantitySold  INT NOT NULL,
SaleDate      DATE NOT NULL,
TotalAmount   NUMBER(10,2) NOT NULL,
FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- INSERT DATA INTO THE TABLES

-- INsert data into Categories table
INSERT INTO Categories VALUES (1,'Dairy','Chilled');
INSERT INTO Categories VALUES (2,'Bakery','Ambient');
INSERT INTO Categories VALUES (3,'Vegetables','Fresh Produce');
INSERT INTO Categories VALUES (4,'Beverages','Chilled');
INSERT INTO Categories VALUES (5,'Snacks','Ambient');

-- Insert data into Products table
INSERT INTO Products VALUES (101, 'Milk', 1, 120, 42.00,  55.00,  SYSDATE + 3);
INSERT INTO Products VALUES (102, 'Butter', 1,  80, 90.00, 115.00,  SYSDATE + 5);
INSERT INTO Products VALUES (103, 'Yogurt', 1,  60, 65.00,  85.00,  SYSDATE + 2);
INSERT INTO Products VALUES (104, 'Cheese', 1,  30, 75.00, 100.00,  SYSDATE + 20);

INSERT INTO Products VALUES (201, 'Bread', 2, 90, 25.00, 40.00,  SYSDATE + 4);
INSERT INTO Products VALUES (202, 'Croissant Pack of 6', 2, 55, 60.00, 85.00,  SYSDATE + 6);
INSERT INTO Products VALUES (203, 'Buns', 2, 15, 30.00, 45.00, SYSDATE + 1);

INSERT INTO Products VALUES (301, 'Bananas 1kg', 3, 200, 18.00, 30.00,  SYSDATE + 7);
INSERT INTO Products VALUES (302, 'Baby Spinach 200g', 3, 70, 22.00, 38.00,  SYSDATE + 3);
INSERT INTO Products VALUES (303, 'Cherry Tomatoes 500g', 3, 10,  40.00, 65.00,  SYSDATE + 10);

INSERT INTO Products VALUES (401, 'Coca Cola',       4, 110, 35.00,  55.00,  SYSDATE + 180);
INSERT INTO Products VALUES (402, 'Badam Milk',   4,  95, 20.00,  32.00,  SYSDATE + 365);

INSERT INTO Products VALUES (501, 'Popcorn', 5, 75, 28.00,  45.00,  SYSDATE + 90);
INSERT INTO Products VALUES (502, 'Dark Chocolate', 5, 40, 55.00,  80.00,  SYSDATE + 120);
INSERT INTO Products VALUES (503, 'Peas', 5, 88, 30.00,  50.00,  SYSDATE + 60);

-- Insert into SalesTransactions
INSERT INTO SalesTransactions VALUES (1001, 101, 30, SYSDATE - 5,  1650.00);
INSERT INTO SalesTransactions VALUES (1002, 102, 12, SYSDATE - 8,  1380.00);
INSERT INTO SalesTransactions VALUES (1003, 103, 20, SYSDATE - 10, 1700.00);
INSERT INTO SalesTransactions VALUES (1004, 201, 25, SYSDATE - 7,  1000.00);
INSERT INTO SalesTransactions VALUES (1005, 202, 18, SYSDATE - 12, 1530.00);
INSERT INTO SalesTransactions VALUES (1006, 301, 50, SYSDATE - 3,  1500.00);
INSERT INTO SalesTransactions VALUES (1007, 302, 30, SYSDATE - 6,  1140.00);
INSERT INTO SalesTransactions VALUES (1008, 501, 40, SYSDATE - 9,  1800.00);
INSERT INTO SalesTransactions VALUES (1009, 502, 15, SYSDATE - 14, 1200.00);
INSERT INTO SalesTransactions VALUES (1010, 104, 10, SYSDATE - 20, 1000.00);
INSERT INTO SalesTransactions VALUES (1011, 203, 8,  SYSDATE - 25,  360.00);
INSERT INTO SalesTransactions VALUES (1012, 303, 5,  SYSDATE - 18,  325.00);
INSERT INTO SalesTransactions VALUES (1013, 401, 10, SYSDATE - 75,  550.00);
INSERT INTO SalesTransactions VALUES (1014, 402, 8,  SYSDATE - 90,  256.00);

-- Query to get the products that are going to expire in the next 7 days and have more than 50 units in stock

SELECT
p.ProductID,
p.ProductName,
c.CategoryName,
p.StockCount,
p.ExpiryDate,
(p.ExpiryDate - SYSDATE) AS DaysUntilExpiry
FROM Products p
JOIN Categories c ON p.CategoryID = c.CategoryID
WHERE p.ExpiryDate BETWEEN SYSDATE AND SYSDATE + 7
AND p.StockCount > 50
ORDER BY DaysUntilExpiry ASC;

-- Query to get the dead stock - products that have not been sold in the last 60 days and have more than 20 units in stock

SELECT
p.ProductID,
p.ProductName,
c.CategoryName,
p.StockCount,
p.SellingPrice
FROM Products p
JOIN Categories c ON p.CategoryID = c.CategoryID
WHERE p.ProductID NOT IN (
SELECT DISTINCT ProductID
FROM SalesTransactions
WHERE SaleDate >= SYSDATE - 60
)
ORDER BY p.StockCount DESC;

-- Query to get the revenue contribution by category

SELECT
c.CategoryName,
COUNT(st.TransactionID)      AS TotalSales,
SUM(st.QuantitySold)         AS UnitsSold,
SUM(st.TotalAmount)          AS TotalRevenue
FROM SalesTransactions st
JOIN Products p  ON st.ProductID  = p.ProductID
JOIN Categories c ON p.CategoryID = c.CategoryID
WHERE st.SaleDate >= TRUNC(ADD_MONTHS(SYSDATE, -1), 'MM')
AND st.SaleDate <  TRUNC(SYSDATE, 'MM')
GROUP BY c.CategoryName
ORDER BY TotalRevenue DESC;
