DROP DATABASE  IF EXISTS `bank-application`;

CREATE DATABASE  IF NOT EXISTS `bank-application`;
USE `bank-application`;



DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `branch` varchar(45) DEFAULT NULL,
  `balance` double(16,3) DEFAULT 0.0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` VALUES 
	(1,'Harshit','Kumar','harshit@gmail.com','Giridih',0.0),
	(2,'Aditya','Singh','adi@gmail.com','Bokaro',0.0),
	(3,'Ajay','Gupta','ajay@gmail.com','Bangalore',0.0),
	(4,'Vineeta','Kumari','vineeta@gmail.com','Bokaro',0.0),
	(5,'Aastha','Lohani','aastha@gmail.com','Giridih',0.0);

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction` double(12,3) DEFAULT 0,
  `customer_id` int(11) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_CUSTOMER_ID_idx` (`customer_id`),
  
  CONSTRAINT `FK_CUSTOMER` 
  FOREIGN KEY (`customer_id`) 
  REFERENCES `customer` (`id`) 
  
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `transactions` VALUES 
	(1,1000,1),
	(2,2000,2),
	(3,100,1),
	(4,5000,5),
	(5,1600,2),
    (6,-200,1);
    
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--
-- The passwords are encrypted using BCrypt
--
-- Default passwords here are: bank123
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES 
('john','$2a$10$dLaDnINr1LYbTqEDSwaEVOwcb4vJ5eZuJPoTzNL02MEUbRuYxy7Yi','John','Doe','john@gmail.com'),
('mary','$2a$10$gsdPrAGGqxzG0xXM7fgkxel.MqnoH22kBp7GXZqGMwxOgBsdHP3nm','Mary','Public','mary@gmail.com'),
('susan','$2a$10$gsdPrAGGqxzG0xXM7fgkxel.MqnoH22kBp7GXZqGMwxOgBsdHP3nm','Susan','Adams','susan@gmail.com');


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3)