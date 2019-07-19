-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `country` text NOT NULL,
  `city` text NOT NULL,
  `street` text NOT NULL,
  `pcode` text NOT NULL,
  `lng` double NOT NULL,
  `lat` double NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (6,'United Kingdom','Wapping Ln','Tobacco Quay','E1W2SF',-0.0595714,51.5081761,1),(7,'Canada','Scarborough','1265 Military Trail','M1C1A5',-79.1868269,43.7845948,0),(9,'Germany','Frankfurt am Main','22-24 Hamburger Allee','60486',8.6479,50.11544,0),(10,'United Kingdom','London','30 Saint Mary Axe','C3A8BF',-0.0803065,51.51449179999999,0),(11,'Canada','Scarborough','3351 Ellesmere Rd','M1C1G9',-79.18392039999999,43.7873813,0),(12,'Canada','Scarborough','3561 Lawrence Ave E','M1H1B2',-79.2272481,43.7590773,0),(13,'Canada','Toronto','384 Kingston Rd','M4L1T9',-79.3061216,43.6760107,0),(14,'Canada','Scarborough','2908 Ellesmere Rd','M1E4B8',-79.2043877,43.7832732,0),(15,'Canada','Scarborough','701 Military Trail','M1E4P6',-79.1974851,43.7895915,0);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `availability` (
  `id` int(11) NOT NULL,
  `avilDate` date NOT NULL,
  PRIMARY KEY (`id`,`avilDate`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `listing` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availability`
--

LOCK TABLES `availability` WRITE;
/*!40000 ALTER TABLE `availability` DISABLE KEYS */;
INSERT INTO `availability` VALUES (2,'2019-05-03'),(4,'2019-07-02'),(4,'2019-07-03'),(5,'2019-06-28'),(5,'2019-06-29'),(5,'2019-06-30'),(5,'2019-07-01'),(5,'2019-07-02'),(7,'2009-06-29'),(7,'2009-06-30'),(7,'2009-07-01'),(7,'2009-07-02'),(9,'1999-06-29'),(9,'1999-06-30'),(9,'1999-07-01'),(9,'1999-07-02'),(10,'2020-06-02'),(10,'2020-06-03'),(10,'2020-06-04'),(10,'2020-06-05'),(10,'2020-07-29'),(10,'2020-07-30'),(10,'2020-07-31'),(10,'2020-08-01'),(10,'2020-08-02'),(11,'2020-06-29'),(11,'2020-06-30'),(11,'2020-07-01'),(11,'2020-07-02'),(12,'2019-07-31'),(12,'2019-08-01'),(12,'2019-08-02'),(13,'2019-07-29'),(13,'2019-07-30'),(13,'2019-07-31'),(13,'2019-08-01'),(13,'2019-08-02'),(14,'2019-07-29'),(14,'2019-07-30'),(14,'2019-07-31'),(14,'2019-08-01'),(14,'2019-08-02'),(15,'2019-07-29'),(15,'2019-07-30'),(15,'2019-07-31'),(15,'2019-08-01'),(15,'2019-08-02');
/*!40000 ALTER TABLE `availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `listing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `area` int(11) NOT NULL,
  `dayPrice` double NOT NULL,
  `owner` int(11) NOT NULL,
  `type` text NOT NULL,
  `amenity` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `owner_idx` (`owner`),
  CONSTRAINT `owner` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (1,'2019-07-02 21:31:42',10,50,3,'Condo','01111111111111'),(2,'2019-07-02 23:05:36',20,50,3,'condo','10110100100111'),(3,'2019-07-03 10:28:34',20,50,3,'condo','10110100100111'),(4,'2019-07-03 10:30:01',20,50,3,'condo','10110100100111'),(5,'2019-07-03 10:37:26',20,50,3,'condo','10110100100111'),(7,'2019-07-03 20:34:57',99,50,3,'House','11010111010100'),(9,'2019-07-03 20:47:55',9,50,3,'Room','01010111010100'),(10,'2019-07-03 21:01:46',19,19.99,3,'Apt','01010111010100'),(11,'2019-07-09 17:54:00',77,67,3,'Room','01010111010100'),(12,'2019-07-13 12:01:05',177,99,3,'Room','01010111010100'),(13,'2019-07-13 12:09:15',177,99,3,'Room','01010111010100'),(14,'2019-07-13 12:11:56',177,99,3,'Room','01010111010100'),(15,'2019-07-13 12:12:38',177,77,3,'Room','01010111010100');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing_comment`
--

DROP TABLE IF EXISTS `listing_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `listing_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `l_id` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `parent_comment` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `content` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `l_id_idx` (`l_id`),
  CONSTRAINT `l_id` FOREIGN KEY (`l_id`) REFERENCES `listing` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing_comment`
--

LOCK TABLES `listing_comment` WRITE;
/*!40000 ALTER TABLE `listing_comment` DISABLE KEYS */;
INSERT INTO `listing_comment` VALUES (1,10,6,3,NULL,5,'this Apt is good.','2019-07-05 17:42:38'),(2,10,3,6,1,NULL,'thx for the comment: this Apt is good.','2019-07-05 17:47:05');
/*!40000 ALTER TABLE `listing_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rented`
--

DROP TABLE IF EXISTS `rented`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rented` (
  `u_id` int(11) NOT NULL,
  `l_id` int(11) NOT NULL,
  `fromDate` date NOT NULL,
  `toDate` date NOT NULL,
  `status` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `dayPrice` double NOT NULL,
  PRIMARY KEY (`u_id`,`l_id`,`toDate`,`fromDate`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rented`
--

LOCK TABLES `rented` WRITE;
/*!40000 ALTER TABLE `rented` DISABLE KEYS */;
INSERT INTO `rented` VALUES (5,2,'2019-05-03','2019-05-03',-1,'2019-07-03 00:00:00',50),(5,4,'2019-07-02','2019-07-02',-2,'2019-07-03 17:19:30',50),(5,5,'2019-06-28','2019-06-29',-1,'2019-07-03 11:23:52',50),(5,5,'2019-07-01','2019-07-02',-2,'2019-07-03 17:16:59',50),(5,5,'2019-07-01','2019-07-02',-1,'2019-07-03 17:06:20',50),(6,10,'2020-07-01','2020-07-02',0,'2019-07-03 22:00:39',50),(6,12,'2019-07-29','2019-07-30',0,'2019-07-16 23:21:26',99);
/*!40000 ALTER TABLE `rented` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL,
  `type` int(11) NOT NULL,
  `cardNum` char(16) NOT NULL,
  `name` text NOT NULL,
  `occ` text NOT NULL,
  `date` datetime NOT NULL,
  `DOB` date NOT NULL,
  `SIN` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'qibowang7@outlook.com',2,'1111222233334444','Kenny Wang','Student','2019-07-02 00:00:00','1996-06-22','123456789','OA9S8GXLqCXWm6o60dO2szOOqYHPoJb/Me1ogGU2KmY=$Pm62xiPhDr6bfE/b6//TSNIysIVLb5TviNMXm/tJqWE='),(5,'qibowang7@gmail.com',1,'1111111111111111','Wang QB','Student','2019-07-03 11:08:15','1996-06-22','123456789','Z83rH14q1SMp+N+L3cpVINzXv8H0NxJay05FBYZCBJY=$CZn0VTeyfisYv+IO7XJSit7HvMIpU1rStO/XxFe7c0w='),(6,'michael@outlook.com',1,'2222222222222222','Michael','graduate','2019-07-03 21:08:54','1996-03-05','12csa442','KXz4EaWuVXlK+3UGsCq3VMJ6KT8ggb+MMU3DVYGozHU=$z3qIIDTXhoMgLQSQBJQmjIR0Sj6PWzFQLjLP3w0i3AE=');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_comment`
--

DROP TABLE IF EXISTS `user_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `parent_comment` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `content` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_comment`
--

LOCK TABLES `user_comment` WRITE;
/*!40000 ALTER TABLE `user_comment` DISABLE KEYS */;
INSERT INTO `user_comment` VALUES (1,6,3,NULL,5,'this user is good','2019-07-03 00:00:00'),(2,3,6,1,NULL,'thanks for the comment: this user is good','2019-07-04 00:00:00'),(3,6,3,2,5,'reply to comment id = 2','2019-07-05 00:00:00'),(4,5,6,NULL,4,'comment on user id = 6','2019-07-05 00:00:00'),(5,6,5,4,4,'reply to comment id = 4','2019-07-05 00:00:00'),(6,5,3,NULL,5,'comment on user id = 3','2019-07-05 00:00:00'),(7,3,5,6,5,'reply to comment id = 6','2019-07-05 00:00:00'),(8,5,3,1,5,'xxxxxxxxxx','2019-08-05 00:00:00');
/*!40000 ALTER TABLE `user_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-19 12:15:25
