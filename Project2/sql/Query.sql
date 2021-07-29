CREATE DATABASE snack_shop;

USE snack_shop;



CREATE TABLE category(
	CategoryID INT AUTO_INCREMENT PRIMARY KEY,
	`NameC` CHAR(5)
)
 INSERT INTO category(`NameC`)
 VALUES('Drink');
  INSERT INTO category(`NameC`)
 VALUES('Food');
 SELECT * FROM category;
CREATE TABLE products(
	ProductID INT AUTO_INCREMENT PRIMARY KEY,
	`Name` VARCHAR(255) NOT NULL,
	Price int NOT NULL ,
	Properties VARCHAR(255) NOT NULL,
	ImgLink VARCHAR(255),
	CategoryID INT
);

SELECT * FROM products;
alter table products
add foreign key (CategoryID) references category(CategoryID);


CREATE TABLE feedback(
	FbID INT AUTO_INCREMENT PRIMARY KEY ,
	Content TEXT NOT NULL ,
	ProductID INT 
);
alter table feedback
add foreign key (ProductID) references products(ProductID);
SELECT * FROM feedback;
CREATE TABLE account_admin(
	accountadmin VARCHAR(40) PRIMARY KEY ,
	passwordadmin VARCHAR(32) NOT NULL ,
	DoB date NOT NULL ,
	nameadmin VARCHAR(55) NOT null

);

INSERT INTO account_admin(accountadmin , passwordadmin,DoB , nameadmin)
VALUES ('Conzin@gmail.com','cb44f839a193bc118e6314e6dacb0da8',"1970-08-06",'Nguyen Van B');

SELECT * FROM account_admin;
CREATE TABLE account_client(
	accounts VARCHAR(40) PRIMARY KEY ,
	passwords VARCHAR(33) NOT NULL ,
	nick_name VARCHAR(55) NOT NULL ,
	phone VARCHAR(10) NOT NULL ,
	address VARCHAR(55) NOT null
);

SELECT * FROM account_client;
SELECT * FROM products WHERE `Name` LIKE '%a%';



CREATE TABLE ShoppingCart(
	productID INT ,
	accounts VARCHAR(50) ,
	quantity INT ,
	Total_price VARCHAR(50)
)

ALTER TABLE ShoppingCart
ADD FOREIGN KEY (productID) REFERENCES products(ProductID);

ALTER TABLE ShoppingCart
ADD FOREIGN KEY (accounts) REFERENCES account_client(accounts);


CREATE TABLE Orders(
	OrderID INT AUTO_INCREMENT Primary key ,
	Client_ID VARCHAR(50) ,
	total_price VARCHAR(50) ,
	dates DATETIME 
);

Alter table Orders
Add foreign key (Client_ID) REFERENCES account_client(accounts);
SELECT * FROM Orders;

Create table order_detail(
	OrderID INT  ,
	ProductID INT ,
	Quantity INT ,
	PRIMARY KEY ( OrderID , ProductID)
);

Alter table order_detail
Add foreign key (ProductID) references products(ProductID);
ALTER TABLE order_detail
ADD FOREIGN KEY (OrderID) REFERENCES Orders(OrderID);


SELECT * FROM order_detail;


SELECT o.*,p.* FROM  order_detail as o join products as p on o.ProductID = p.ProductID WHERE Client_ID = 'namoizoioi@gmail.com';
SELECT p.*,c.* FROM products as p join category as c on p.CategoryID = c.CategoryID

