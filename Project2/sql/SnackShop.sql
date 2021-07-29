CREATE DATABASE snack_shop;

USE snack_shop;




CREATE TABLE category(
	CategoryID INT AUTO_INCREMENT PRIMARY KEY,
	`NameC` CHAR(5)
);
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

INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Kẹo alpenliebe vị caramen','6000',"Kẹo ngậm vị caramen",'/img/caramen.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Kẹo alpenliebe vị dâu','6000',"Kẹo ngậm vị dâu",'/img/dâu.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Kẹo alpenliebe vị muối ớt','6000',"Kẹo ngậm vị muối ớt",'/img/keo muoi.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Kẹo alpenliebe vị trà sữa','6000',"Kẹo ngậm vị trà sữa",'/img/keo.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Kẹo mút chuppachup','1000',"kẹo mút",'/img/keo mut.jpg','2');

INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Snack khoai tây Slide','38000',"Snack khoai tây ống",'/img/khoai tay.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Snack khoai tây Lays','6000',"Snack khoai tây gói",'/img/lays.png','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Bánh quy Oreo','12000',"Bánh quy Oreo với đầy đủ vị",'/img/oreo.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Đậu phộng da cá Poca','8000',"Đậu phộng da cá",'/img/dauphong.jpg','2');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Dark chocolate','25000',"Socola đen",'/img/socola.jpg','2');

INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước ép trái cây','20000',"Nước ép từ các loại trái cây bổ dưỡng",'/img/Drinkgod.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước uống có ga Cocacola','10000',"Nước giải khát",'/img/coca.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước ép rau má','20000',"Nước ép rau má tươi mát",'/img/drinkrauma.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Matcha kem cheese','30000',"Matcha kem cheese thơm ngon mát lạnh",'/img/matcha.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước uống Mirinda vị cam','10000',"Mirinda vị cam",'/img/mirida cam.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước uống Mirinda vị soda kem','10000',"Mirinda vị soda kem",'/img/mirida soda.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Nước uống có ga Pepsi','10000',"Nước uống pepsi",'/img/pepsi.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Sinh tố bơ','25000',"sinh tố bơ thơm ngon bổ dưỡng, đẹp da",'/img/shirobo.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('Trà sữa dingtea','30000',"Trà sữa thơm ngon",'/img/tatua.jpg','1');
INSERT INTO products(`Name`,Price , Properties,ImgLink,CategoryID)
VALUES ('trà sữa C2','6000',"Trà sữa đóng chai, thơm ngon tiện lợi",'/img/tra sua c2.jpg','1');
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

INSERT INTO account_client(accounts , passwords,nick_name , phone, address)
VALUES ('a@gmail.com',MD5('1234567890'),'Hoang','0978654321','Ha Noi');
INSERT INTO account_client(accounts , passwords,nick_name , phone, address)
VALUES ('b@gmail.com',MD5('1234567890'),'Hoangg','0978654329','Ha Noi');

SELECT * FROM account_client;
SELECT * FROM products WHERE `Name` LIKE '%a%';



CREATE TABLE ShoppingCart(
	productID INT ,
	accounts VARCHAR(50) ,
	quantity INT ,
	Total_price VARCHAR(50)
);

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
