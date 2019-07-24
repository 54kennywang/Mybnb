-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.16

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(2,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(3,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(4,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(5,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(6,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(7,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(8,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(9,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(10,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(17,'Canada','North York','350 Seneca Hill Dr','M2J4S7',-79.3562377,43.7905665,0),(18,'Canada','North York','1 Cliff Fern Way','M2J4L6',-79.3594535,43.7908205,0),(19,'Canada','North York','1 Liszt Gate','M2H1G7',-79.3635088,43.7911536,0),(20,'Canada','North York','2 Cliff Fern Way','M2J4L7',-79.3597329,43.7907703,0),(21,'Canada','North York','2 Greyhound Dr','M2H1K3',-79.3688411,43.7920148,0),(22,'Canada','North York','1 Liszt Gate','M2H1G7',-79.3635088,43.7911536,0),(23,'Canada','Toronto','8 Widmer St','M5V2E7',-79.3918082,43.6467602,0),(24,'Canada','Toronto','8 Charlotte St','M5V2J5',-79.3939515,43.6460936,0),(25,'Canada','Toronto','350 Adelaide St W','M5V1R7',-79.3935259,43.647534,0),(26,'Canada','Toronto','168 Simcoe St','M5H4C9',-79.38737379999999,43.6500917,0);
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
  PRIMARY KEY (`id`,`avilDate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availability`
--

LOCK TABLES `availability` WRITE;
/*!40000 ALTER TABLE `availability` DISABLE KEYS */;
INSERT INTO `availability` VALUES (18,'2019-08-01'),(18,'2019-08-02'),(18,'2019-08-03'),(18,'2019-08-04'),(18,'2019-08-05'),(19,'2019-05-06'),(19,'2019-05-07'),(19,'2019-05-08'),(19,'2019-05-09'),(19,'2019-05-10'),(19,'2019-05-11'),(20,'2019-08-08'),(20,'2019-08-09'),(20,'2019-08-10'),(21,'2019-10-01'),(21,'2019-10-02'),(21,'2019-10-03'),(21,'2019-10-04'),(21,'2019-10-05'),(21,'2019-10-06'),(21,'2019-10-07'),(25,'2019-10-09'),(25,'2019-10-10'),(25,'2019-10-11'),(25,'2019-10-12'),(25,'2019-10-13'),(25,'2019-10-14'),(25,'2019-10-15'),(25,'2019-10-16'),(25,'2019-10-17'),(25,'2019-10-18'),(25,'2019-10-19'),(25,'2019-10-20'),(25,'2019-10-21'),(25,'2019-10-22'),(25,'2019-10-23'),(25,'2019-10-24'),(25,'2019-10-25'),(26,'2019-11-01'),(26,'2019-11-02'),(26,'2019-11-03'),(26,'2019-11-04'),(26,'2019-11-05'),(26,'2019-11-06'),(26,'2019-11-07');
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
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (17,'2019-07-24 15:47:05',50,177.77,1,'Condo','01010111010100'),(18,'2019-07-24 15:47:07',75,177.77,1,'Room','01010111010100'),(19,'2019-07-24 16:23:38',50,177.77,2,'House','01010111010100'),(20,'2019-07-24 16:23:39',75,177.77,2,'House','01010111010100'),(21,'2019-07-24 16:23:40',100,177.77,2,'House','11110001110001'),(22,'2019-07-24 17:03:12',50,177.77,3,'Room','10100101110111'),(23,'2019-07-24 17:03:13',75,177.77,3,'Condo','01010010101110'),(24,'2019-07-24 17:03:15',100,200,3,'Condo','01001101001110'),(25,'2019-07-24 17:03:16',200,210,3,'Condo','10101101011011'),(26,'2019-07-24 17:03:18',100,250,3,'Apt','01001010110110');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing_comment`
--

LOCK TABLES `listing_comment` WRITE;
/*!40000 ALTER TABLE `listing_comment` DISABLE KEYS */;
INSERT INTO `listing_comment` VALUES (3,17,4,1,NULL,5,'Excellent','2019-07-24 18:13:37'),(4,19,5,2,NULL,1,'worst','2019-07-24 18:32:43'),(5,22,1,3,NULL,3,'good','2019-07-24 19:03:30'),(6,23,6,3,NULL,3,'good','2019-07-24 19:03:44'),(7,24,7,3,NULL,3,'good','2019-07-24 19:04:01');
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
INSERT INTO `rented` VALUES (1,22,'2019-05-16','2019-05-21',1,'2019-07-24 18:40:09',177.77),(4,17,'2019-06-27','2019-07-02',1,'2019-07-24 17:51:46',177.77),(5,19,'2019-05-01','2019-05-05',1,'2019-07-24 18:29:12',177.77),(6,23,'2019-06-05','2019-06-20',1,'2019-07-24 18:42:13',177.77),(7,24,'2019-03-03','2019-03-07',1,'2019-07-24 18:56:15',200);
/*!40000 ALTER TABLE `rented` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'A',2,'A','A','graduate','2019-07-24 13:00:47','1996-03-05','A','DMJoY2XsnfHaFqOa3kGGugVByA3l36IyHSWf/bnodas=$Ocd9q7KjC3elVURSQ3/FgwRBTI+JFA7ba7APL1aRB+4='),(2,'B',2,'B','B','graduate','2019-07-24 13:00:47','1996-03-05','B','t9OW15e8FBiQvWTUhPjTcZGA/CYMRNKuE45pVV668g4=$oZc7qVZaC/iYqlQmJRUCam/32euddnWl5jJsqS4cT3Y='),(3,'C',2,'C','C','graduate','2019-07-24 13:00:48','1996-03-05','C','TU2bLpB9w0i1yGBxZt9cOSHUaOzCSCvLyC6HtKLV1DY=$oxKNpJ3r/jIwKJyO9AI5QGhx3FfBBSunircvrsvmyWw='),(4,'D',1,'D','D','graduate','2019-07-24 13:00:48','1996-03-05','D','CeyFXJb8iRHxcuccTfrd1oGwFZFsi5hNwzlYiIqb4rs=$cEcQrnHD0qYrnGigvd3je8F9M88gNoNxflBoUXsV7Bw='),(5,'E',1,'E','E','graduate','2019-07-24 13:00:48','1996-03-05','E','Gtmhd2z59zJZJchxv3TFAFw25JB4DBYw9NKahbXZ35o=$6PDVspfFqvIl/HEEBkH+cIqFi9PvUsSSwo6oyenkhRg='),(6,'F',1,'F','F','graduate','2019-07-24 13:00:48','1996-03-05','F','LXXe+dXa2Qt4m6o/H7+BGLpNwJB9MqQSR9e7nCw2nyU=$0uoexiNCngBdPB8Tb9bxplWg6lGrvsqKFgjXLDWnfI8='),(7,'G',1,'G','G','graduate','2019-07-24 13:00:49','1996-03-05','G','4igHZ3CsgbMTGCOA1CDvtD+6YsYkzTQWz7fcJApcRAQ=$zATV5JBys4BYpLiP4WCav/G3Dp17gMfsRFo/su1rFKg='),(8,'H',1,'H','H','graduate','2019-07-24 13:00:49','1996-03-05','H','765zmrpphZZ6sgia8Rm8A58mokw80jhfRa+CM3oxUoY=$XTE7IBSEPcguYg+oTg62l7lzD9dtwfDEMW9I5D97G1k='),(9,'I',1,'I','I','graduate','2019-07-24 13:00:49','1996-03-05','I','fa6iQVNSs5JacEr9BLNFyFZ9PewHk9PcPO7pNbFDuMs=$S8/YxlpiT6nUGPpGpOhBF4qshmwP6EuuarGj7UeWImo='),(10,'J',1,'J','J','graduate','2019-07-24 13:00:50','1996-03-05','J','cgqjY3mWWHtpC0aOr5KOk73sJk8ejivHy4CmD6y9RHo=$PUSA9M1wb3v90shgpS57pyfqttEq28Pw4jzBUvPPcrw=');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_comment`
--

LOCK TABLES `user_comment` WRITE;
/*!40000 ALTER TABLE `user_comment` DISABLE KEYS */;
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

-- Dump completed on 2019-07-24 19:05:39
