CREATE TABLE product(
	id	    number(10) NOT NULL PRIMARY KEY,
	name	varchar(50) NOT NULL,
    price   NUMBER(19,4) NOT NULL,
    brand  varchar(50) NOT NULL,
    weight  varchar(10) NOT NULL,
    discount NUMBER(3,2) NOT NULL
);

INSERT INTO product VALUE (1, 'Whey Protein Concentrado', 76.50, 'Growth', '1kg', 10);
INSERT INTO product VALUE (2, 'Whey Protein Isolado', 139.50, 'Growth', '1kg', 10);
INSERT INTO product VALUE (3, 'Basic Whey Protein', 34.20, 'Growth', '1kg', 10);
INSERT INTO product VALUE (4, '100% Whey Protein', 161.10, 'Optimun', '909g', 10);
INSERT INTO product VALUE (5, '100% Whey Protein', 81.00, 'Max Titanium', '900g', 10);