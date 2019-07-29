-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: newestDB
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `address` VALUES (1,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(2,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(3,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(4,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(5,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(6,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(7,'Canada','North York','1 Hayes Ln','M2N0E7',-79.3991238,43.7828764,1),(8,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(9,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(10,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(11,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(12,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(13,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(14,'Canada','Ottawa','216 Hudson Ave','K2C0G4',-75.7109696,45.3702919,1),(15,'Canada','Vancouver','5476 Angus Dr','V6M3N4',-123.1470688,49.2363239,1),(16,'Canada','Vancouver','5476 Angus Dr','V6M3N4',-123.1470688,49.2363239,1),(17,'Canada','North York','350 Seneca Hill Dr','M2J4S7',-79.3562377,43.7905665,0),(18,'Canada','North York','1 Cliff Fern Way','M2J4L6',-79.3594535,43.7908205,0),(18,'USA','New York','300 E 55th St','Y10022',-73.96600459999999,40.7577216,1),(19,'Canada','North York','1 Liszt Gate','M2H1G7',-79.3635088,43.7911536,0),(19,'USA','New York','300 E 55th St','Y10022',-73.96600459999999,40.7577216,1),(20,'Canada','North York','2 Cliff Fern Way','M2J4L7',-79.3597329,43.7907703,0),(20,'USA','New York','300 E 55th St','Y10022',-73.96600459999999,40.7577216,1),(21,'Canada','North York','2 Greyhound Dr','M2H1K3',-79.3688411,43.7920148,0),(21,'USA','Los Angeles','135 N Grand Ave','A90012',-118.248733,34.0562974,1),(22,'Canada','North York','1 Liszt Gate','M2H1G7',-79.3635088,43.7911536,0),(22,'USA','Los Angeles','135 N Grand Ave','A90012',-118.248733,34.0562974,1),(23,'Canada','Toronto','8 Widmer St','M5V2E7',-79.3918082,43.6467602,0),(23,'USA','Los Angeles','135 N Grand Ave','A90012',-118.248733,34.0562974,1),(24,'Canada','Toronto','8 Charlotte St','M5V2J5',-79.3939515,43.6460936,0),(24,'USA','Boston','564 Tremont St','A02118',-71.0720039,42.34356349999999,1),(25,'Canada','Toronto','350 Adelaide St W','M5V1R7',-79.3935259,43.647534,0),(25,'USA','Boston','564 Tremont St','A02118',-71.0720039,42.34356349999999,1),(26,'Canada','Toronto','168 Simcoe St','M5H4C9',-79.38737379999999,43.6500917,0),(26,'USA','Boston','564 Tremont St','A02118',-71.0720039,42.34356349999999,1),(27,'Canada','Ottawa','391 Holland Ave','K1Y 0Y9',-75.7283082,45.3929904,0),(27,'Canada','Scarborough','38 Gladys Rd','M1C1C6',-79.18093139999999,43.7856563,1),(28,'Canada','Ottawa','217 Hudson Ave','K2C0G5',-75.7111386,45.370688,0),(29,'Canada','Ottawa','218 Hudson Ave','K2C0G4',-75.7110891,45.3702759,0),(30,'Canada','Ottawa','219 Hudson Ave','K2C0G5',-75.71118609999999,45.3706678,0),(31,'Canada','Ottawa','220 Hudson Ave','K2C0G4',-75.71075239999999,45.3706211,0),(32,'Canada','Ottawa','221 Hudson Ave','K2C0G5',-75.71075239999999,45.3706211,0),(33,'Canada','Ottawa','1025 Stormont St','K2C0N1',-75.7045258,45.3586743,0),(34,'Canada','Ottawa','1026 Stormont St','K2C0M9',-75.70410009999999,45.358381,0),(35,'Canada','Ottawa','1027 Stormont St','K2C0N1',-75.7046008,45.3586971,0),(36,'Canada','Ottawa','1 Dorothea Dr','K1V7C6',-75.6977236,45.3532641,0),(37,'Canada','Ottawa','2 Dorothea Dr','K1V7C7',-75.69714929999999,45.3535286,0),(38,'Canada','Vancouver','2701 W 36th Ave','V6N2P7',-123.165737,49.2394611,0),(39,'Canada','Vancouver','2702 W 35th Ave','V6N2M1',-123.1657767,49.2402333,0),(40,'Canada','Vancouver','915 E 14th Ave','V5T2N7',-123.0848814,49.2581295,0),(41,'Canada','Vancouver','916 E 14th Ave','V5T2N7',-123.084717,49.2576656,0),(42,'Canada','Vancouver','917 E 14th Ave','V5T2N7',-123.084876,49.25806619999999,0),(43,'Canada','Vancouver','2130 William St','V5L2S3',-123.0610347,49.2736724,0),(44,'Canada','Vancouver','2140 William St','V5L2S3',-123.060875,49.27366749999999,0),(45,'Canada','Vancouver','2150 William St','V5L2S3',-123.0606583,49.27361399999999,0),(46,'Canada','Vancouver','2160 William St','V5L2S3',-123.0604436,49.2736625,0),(47,'Canada','Vancouver','2170 William St','V5L2S3',-123.0603804,49.2736618,0),(64,'USA','New York','223 E 60th St','Y10022',-73.96498770000001,40.761862,0),(65,'USA','New York','224 E 60th St','Y10022',-73.9654147,40.761799,0),(66,'USA','New York','800 5th Ave','Y10065',-73.9715764,40.7657125,0),(67,'USA','New York','1 E 62nd St','Y10065',-73.9709555,40.7661082,0),(68,'USA','New York','47-02 48th Ave','Y11377',-73.9184678,40.7389175,0),(69,'USA','New York','47-3 48th Ave','Y11377',-73.9182496,40.739379,0),(70,'USA','New York','47-4 48th Ave','Y11377',-73.9183455,40.7390174,0),(71,'USA','New York','47-5 48th Ave','Y11377',-73.91821019999999,40.7393746,0),(72,'USA','New York','47-06 48th Ave','Y11377',-73.91828410000001,40.7390491,0),(73,'USA','New York','47-07 48th Ave','Y11377',-73.918166,40.73939499999999,0),(74,'USA','New York','47-8 48th Ave','Y11377',-73.9182381,40.7390054,0),(75,'USA','New York','47-09 48th Ave','Y11377',-73.91809239999999,40.73936,0),(76,'USA','Los Angeles','251 S Olive St','A90012',-118.2505609,34.0525457,0),(77,'USA','Los Angeles','416 W 8th St','A90014',-118.2565653,34.0448071,0),(78,'USA','Los Angeles','520 N Orchard Dr','A91506',-118.3268577,34.1734648,0),(79,'USA','Los Angeles','521 N Orchard Dr','A91506',-118.32736,34.17333900000001,0),(80,'USA','Los Angeles','522 N Orchard Dr','A91506',-118.3268505,34.1735523,0),(81,'USA','Los Angeles','523 N Orchard Dr','A91506',-118.3274651,34.1733897,0),(82,'USA','Los Angeles','524 N Orchard Dr','A91506',-118.3269491,34.1736018,0),(83,'USA','Los Angeles','525 N Orchard Dr','A91506',-118.3274513,34.173476,0),(84,'USA','Los Angeles','526 N Orchard Dr','A91506',-118.3269083,34.1736754,0),(85,'USA','Los Angeles','527 N Orchard Dr','A91506',-118.3275157,34.1734974,0),(86,'USA','Boston','100 Stuart St','A02116',-71.0656551,42.3508419,0),(87,'USA','Boston','19 Stuart St','A02116',-71.0634137,42.3511117,0),(88,'USA','Boston','161 Devonshire St','A02110',-71.0575303,42.35617269999999,0),(89,'USA','Boston','185 State St','A02109',-71.05292380000002,42.3592418,0),(90,'USA','Boston','161 Devonshire St','A02110',-71.0575303,42.35617269999999,0),(91,'USA','Boston','520 E 6th St','A02127',-71.0395092,42.333229,0),(92,'USA','Boston','521 E 6th St','A02127',-71.0395456,42.33289569999999,0),(93,'USA','Boston','522 E 6th St','A02127',-71.039388,42.3331838,0),(94,'USA','Boston','523 E 6th St','A02127',-71.0394944,42.33289080000001,0),(95,'USA','Boston','524 E 6th St','A02127',-71.0393474,42.33315,0);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `availability` VALUES (18,'2019-08-01'),(18,'2019-08-02'),(18,'2019-08-03'),(18,'2019-08-04'),(18,'2019-08-05'),(19,'2019-05-06'),(19,'2019-05-07'),(19,'2019-05-08'),(19,'2019-05-09'),(19,'2019-05-10'),(19,'2019-05-11'),(20,'2019-08-08'),(20,'2019-08-09'),(20,'2019-08-10'),(21,'2019-10-01'),(21,'2019-10-02'),(21,'2019-10-03'),(21,'2019-10-04'),(21,'2019-10-05'),(21,'2019-10-06'),(21,'2019-10-07'),(25,'2019-10-09'),(25,'2019-10-10'),(25,'2019-10-11'),(25,'2019-10-12'),(25,'2019-10-13'),(25,'2019-10-14'),(25,'2019-10-15'),(25,'2019-10-16'),(25,'2019-10-17'),(25,'2019-10-18'),(25,'2019-10-19'),(25,'2019-10-20'),(25,'2019-10-21'),(25,'2019-10-22'),(25,'2019-10-23'),(25,'2019-10-24'),(25,'2019-10-25'),(26,'2019-11-01'),(26,'2019-11-02'),(26,'2019-11-03'),(26,'2019-11-04'),(26,'2019-11-05'),(26,'2019-11-06'),(26,'2019-11-07'),(29,'2019-06-06'),(29,'2019-06-07'),(29,'2019-06-08'),(29,'2019-06-09'),(29,'2019-06-10'),(31,'2019-08-01'),(31,'2019-08-02'),(31,'2019-08-03'),(31,'2019-08-04'),(31,'2019-08-05'),(31,'2019-08-06'),(31,'2019-08-07'),(31,'2019-08-08'),(31,'2019-08-09'),(31,'2019-08-10'),(32,'2019-09-01'),(32,'2019-09-02'),(32,'2019-09-03'),(32,'2019-09-04'),(32,'2019-09-05'),(32,'2019-09-06'),(32,'2019-09-07'),(32,'2019-09-08'),(32,'2019-09-09'),(32,'2019-09-10'),(33,'2019-09-03'),(33,'2019-09-04'),(33,'2019-09-05'),(34,'2019-01-06'),(34,'2019-01-07'),(34,'2019-01-08'),(34,'2019-01-09'),(34,'2019-01-10'),(34,'2019-01-11'),(34,'2019-01-12'),(34,'2019-01-13'),(34,'2019-01-14'),(34,'2019-01-15'),(34,'2019-01-16'),(34,'2019-01-17'),(34,'2019-01-18'),(34,'2019-01-19'),(34,'2019-01-20'),(34,'2019-01-21'),(34,'2019-01-22'),(36,'2019-02-06'),(36,'2019-02-07'),(36,'2019-02-08'),(36,'2019-02-09'),(36,'2019-02-10'),(36,'2019-02-11'),(36,'2019-02-12'),(37,'2019-12-08'),(37,'2019-12-09'),(37,'2019-12-10'),(37,'2019-12-11'),(37,'2019-12-12'),(37,'2019-12-13'),(37,'2019-12-14'),(37,'2019-12-15'),(37,'2019-12-16'),(37,'2019-12-17'),(37,'2019-12-18'),(37,'2019-12-19'),(37,'2019-12-20'),(39,'2019-08-20'),(39,'2019-08-21'),(39,'2019-08-22'),(39,'2019-08-23'),(39,'2019-08-24'),(39,'2019-08-25'),(41,'2019-11-15'),(41,'2019-11-16'),(41,'2019-11-17'),(41,'2019-11-18'),(41,'2019-11-19'),(41,'2019-11-20'),(44,'2019-09-21'),(44,'2019-09-22'),(44,'2019-09-23'),(44,'2019-09-24'),(44,'2019-09-25'),(44,'2019-09-26'),(44,'2019-09-27'),(44,'2019-09-28'),(44,'2019-09-29'),(44,'2019-09-30'),(44,'2019-10-01'),(46,'2019-09-01'),(46,'2019-09-02'),(46,'2019-09-03'),(46,'2019-09-04'),(46,'2019-09-05'),(46,'2019-09-06'),(46,'2019-09-07'),(46,'2019-09-08'),(46,'2019-09-09'),(46,'2019-09-10'),(46,'2019-09-11'),(46,'2019-09-12'),(46,'2019-09-13'),(46,'2019-09-14'),(46,'2019-09-15'),(46,'2019-09-16'),(46,'2019-09-17'),(46,'2019-09-18'),(46,'2019-09-19'),(46,'2019-09-20'),(46,'2019-09-21'),(46,'2019-09-22'),(46,'2019-09-23'),(46,'2019-09-24'),(46,'2019-09-25'),(46,'2019-09-26'),(46,'2019-09-27'),(46,'2019-09-28'),(46,'2019-09-29'),(46,'2019-09-30'),(46,'2019-10-01'),(47,'2019-09-01'),(47,'2019-09-02'),(47,'2019-09-03'),(47,'2019-09-04'),(47,'2019-09-05'),(47,'2019-09-06'),(47,'2019-09-07'),(47,'2019-09-08'),(47,'2019-09-09'),(47,'2019-09-10'),(47,'2019-09-11'),(47,'2019-09-12'),(47,'2019-09-13'),(47,'2019-09-14'),(47,'2019-09-15'),(47,'2019-09-16'),(47,'2019-09-17'),(47,'2019-09-18'),(47,'2019-09-19'),(47,'2019-09-20'),(47,'2019-09-21'),(47,'2019-09-22'),(47,'2019-09-23'),(47,'2019-09-24'),(47,'2019-09-25'),(47,'2019-09-26'),(47,'2019-09-27'),(47,'2019-09-28'),(47,'2019-09-29'),(47,'2019-09-30'),(47,'2019-10-01'),(65,'2019-08-20'),(65,'2019-08-21'),(65,'2019-08-22'),(72,'2019-09-21'),(72,'2019-09-22'),(72,'2019-09-23'),(72,'2019-09-24'),(74,'2019-09-21'),(74,'2019-09-22'),(74,'2019-09-23'),(74,'2019-09-24'),(75,'2019-10-12'),(75,'2019-10-13'),(75,'2019-10-14'),(75,'2019-10-15'),(81,'2019-10-22'),(81,'2019-10-23'),(82,'2019-10-01'),(82,'2019-10-04'),(83,'2019-11-20'),(83,'2019-11-21'),(84,'2019-12-01'),(84,'2019-12-02'),(84,'2019-12-03'),(84,'2019-12-04'),(85,'2019-12-01'),(85,'2019-12-02'),(85,'2019-12-03'),(85,'2019-12-04'),(87,'2020-06-02'),(87,'2020-06-03'),(87,'2020-06-04'),(87,'2020-06-05'),(89,'2020-12-02'),(89,'2020-12-03'),(89,'2020-12-04'),(89,'2020-12-05'),(93,'2019-07-20'),(93,'2019-07-21'),(93,'2019-07-22'),(93,'2019-07-23'),(94,'2019-07-20'),(94,'2019-07-21'),(94,'2019-07-22'),(94,'2019-07-23'),(95,'2019-07-20'),(95,'2019-07-21'),(95,'2019-07-22'),(95,'2019-07-23');
/*!40000 ALTER TABLE `availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (17,'2019-07-24 15:47:05',50,177.77,1,'Condo','11010111010100'),(18,'2019-07-24 15:47:07',75,177.77,1,'Room','01010111010100'),(19,'2019-07-24 16:23:38',50,177.77,2,'House','01010111010100'),(20,'2019-07-24 16:23:39',75,177.77,2,'House','01010111010100'),(21,'2019-07-24 16:23:40',100,177.77,2,'House','11110001110001'),(22,'2019-07-24 17:03:12',50,177.77,3,'Room','10100101110111'),(23,'2019-07-24 17:03:13',75,90,3,'Condo','01010010101110'),(24,'2019-07-24 17:03:15',100,150,3,'Condo','01001101001110'),(25,'2019-07-24 17:03:16',200,210,3,'Condo','10101101011011'),(26,'2019-07-24 17:03:18',100,250,3,'Apt','01001010110110'),(27,'2019-07-24 20:49:54',50,100,9,'House','11111111111111'),(28,'2019-07-24 20:50:34',50,100,9,'House','10100101110110'),(29,'2019-07-24 20:50:35',50,100,9,'House','10100101110110'),(30,'2019-07-24 20:50:37',50,100,9,'House','10100101110110'),(31,'2019-07-24 20:50:38',50,100,9,'House','10100101110110'),(32,'2019-07-24 20:50:40',50,100,9,'House','10100101110110'),(33,'2019-07-24 21:17:58',220,150,3,'House','10110100101001'),(34,'2019-07-24 21:17:59',220,150,3,'House','10110100101001'),(35,'2019-07-24 21:18:00',50,150,3,'Room','10110100101001'),(36,'2019-07-24 21:18:02',100,300,3,'House','11111110101110'),(37,'2019-07-24 21:18:03',50,156,3,'Room','10110100101001'),(38,'2019-07-25 11:04:43',30,150,13,'Room','11010010111010'),(39,'2019-07-25 11:04:45',100,200,14,'House','11010010111010'),(40,'2019-07-25 11:43:21',50,125,15,'Room','11010010111010'),(41,'2019-07-25 11:43:22',50,125,15,'Room','11010010111010'),(42,'2019-07-25 11:43:23',50,125,15,'Room','11010010111010'),(43,'2019-07-25 11:43:23',50,125,15,'Room','11010010111010'),(44,'2019-07-25 11:43:26',100,200,15,'House','11111111111111'),(45,'2019-07-25 11:43:28',100,200,15,'House','11111111111111'),(46,'2019-07-25 11:43:31',100,200,15,'House','11111111111111'),(47,'2019-07-25 11:43:33',100,200,15,'House','11111111111101'),(64,'2019-07-26 14:43:45',30,175,18,'Apt','11010010111011'),(65,'2019-07-26 14:43:46',50,200,18,'Apt','11010010111011'),(66,'2019-07-26 14:50:17',40,150,19,'Apt','11010010111011'),(67,'2019-07-26 14:50:18',50,200,19,'Apt','11010010111011'),(68,'2019-07-26 15:11:56',40,150,9,'Apt','11010010111011'),(69,'2019-07-26 15:11:57',40,150,9,'Apt','11010010111011'),(70,'2019-07-26 15:11:57',40,150,9,'Apt','11010010111011'),(71,'2019-07-26 15:11:58',40,150,9,'Apt','11010010111011'),(72,'2019-07-26 15:11:59',40,150,9,'Apt','11010010111011'),(73,'2019-07-26 15:12:00',40,150,9,'Apt','11010010111011'),(74,'2019-07-26 15:12:01',40,150,9,'Apt','11010010111011'),(75,'2019-07-26 15:12:02',40,150,9,'Apt','11010010111011'),(76,'2019-07-26 17:32:32',30,300,21,'Room','11010010111011'),(77,'2019-07-26 17:32:33',40,130,22,'Room','11010010111011'),(78,'2019-07-26 17:32:34',100,150,10,'House','11010010111011'),(79,'2019-07-26 17:32:35',100,150,10,'House','11010010111011'),(80,'2019-07-26 17:32:36',100,150,10,'House','11010010111011'),(81,'2019-07-26 17:32:37',100,150,10,'House','11010010111011'),(82,'2019-07-26 17:32:37',100,150,10,'House','11010010111011'),(83,'2019-07-26 17:32:38',100,150,10,'House','11010010111011'),(84,'2019-07-26 17:32:39',100,150,10,'House','11010010111011'),(85,'2019-07-26 17:32:40',100,150,10,'House','11010010111011'),(86,'2019-07-27 01:48:03',70,350,24,'Room','11010010111011'),(87,'2019-07-27 01:48:06',70,350,24,'Room','11010010111011'),(88,'2019-07-27 01:48:07',70,200,25,'Room','11010010111011'),(89,'2019-07-27 01:48:08',70,225,25,'Room','11010010111011'),(90,'2019-07-27 01:48:08',70,200,8,'Room','11010010111011'),(91,'2019-07-27 02:09:57',100,150,26,'House','11010010111011'),(92,'2019-07-27 02:09:58',100,150,26,'House','11010010111011'),(93,'2019-07-27 02:09:59',100,150,26,'House','11010010111011'),(94,'2019-07-27 02:10:00',100,150,26,'House','11010010111011'),(95,'2019-07-27 02:10:01',100,150,26,'House','11010010111011');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing_comment`
--

DROP TABLE IF EXISTS `listing_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing_comment`
--

LOCK TABLES `listing_comment` WRITE;
/*!40000 ALTER TABLE `listing_comment` DISABLE KEYS */;
INSERT INTO `listing_comment` VALUES (3,17,4,1,NULL,5,'The condo is comfortable and wifi is fast ','2019-07-24 18:13:37'),(4,19,5,2,NULL,1,'How is the price so high without WiFi? The place is bad too','2019-07-24 18:32:43'),(5,22,1,3,NULL,4,'The place has all the amenities I need and it feels like home','2019-07-24 19:03:30'),(6,23,6,3,NULL,3,'The price is very cheap. It\'s cost effective','2019-07-24 19:03:44'),(7,24,7,3,NULL,3,'The price is reasonable and the host is friendly','2019-07-24 19:04:01'),(8,28,11,9,NULL,5,'The house is so beautiful and comfortable. Can\'t wait to live here next time','2019-07-24 22:06:07'),(9,29,12,9,NULL,1,'The sound insulation of the house is not good. You can hear people walking around. Also it smells weird in my room','2019-07-24 22:06:36'),(10,30,13,9,NULL,3,'The house is not good with sound insulation but it is bearable considering the price is cheap','2019-07-24 22:06:53'),(11,38,1,13,NULL,3,'I had a good time living in this room but the price is little high considering the size of the room is small','2019-07-25 13:41:13'),(12,40,2,15,NULL,4,'The wifi is fast and stable. I had a good experience','2019-07-25 13:41:14'),(13,42,3,15,NULL,2,'It does not have AC. It feels very cold to live in ','2019-07-25 13:41:14'),(14,43,4,15,NULL,2,'The price is not reasonable when it does not have AC. It feels hot inside the room','2019-07-25 13:41:14'),(15,45,5,15,NULL,5,'I love the house! It offers me so many amenities and I feel like I am home!','2019-07-25 13:41:14'),(16,64,13,18,NULL,4,'We had a wonderful experience. Great location, clean place, quiet and safe building! ','2019-07-26 16:43:36'),(20,66,14,19,NULL,4,' Everything was very clean and the bed was comfortable. The location was great. Definitely a 10/10 and we would stay here again','2019-07-26 16:47:25'),(21,68,15,9,NULL,5,'We stayed 3 nights in this apartment and had a great time. It is nicely decorated and had everything we needed in it. A very comfortable bed!','2019-07-26 16:47:25'),(22,69,16,9,NULL,5,'Would recommend this to anyone. Lots of great restaurants and sights nearby and a short walk away.','2019-07-26 16:47:25'),(23,70,20,9,NULL,4,'The apartment was small and compact but very clean!','2019-07-26 16:47:25'),(24,76,5,21,NULL,5,'Enjoyed a wonderful stay at this gem of a house. Quiet neighborhood, wonderful space, convenient to the freeway and lots of stores and restaurants.','2019-07-26 18:06:06'),(25,77,6,22,NULL,4,' The house was modern, comfortable, clean, and fully stocked with anything that you could possibly need for a comfortable stay.','2019-07-26 18:06:06'),(26,78,7,10,NULL,2,'If you like privacy, find somewhere else. The bed was comfy until I found a long hair in it. The hosts were needlessly confrontational about me bringing food and drink into the room.','2019-07-26 18:06:06'),(27,79,8,10,NULL,5,'Was a good experience, got to interact with both of them. Breakfast was very good and homely. Would definitely recommend staying here.','2019-07-26 18:06:07'),(28,80,9,10,NULL,4,'A lovely room, comfortable bed, friendly hosts and a great collection of books and LA tourism information.','2019-07-26 18:06:07'),(29,86,1,24,NULL,5,'Good accommodation, location is ok close to metro station close to airport.','2019-07-27 10:49:29'),(30,92,2,26,NULL,4,'All in all, it was a very comfortable stay, the only down side is the walls being very thin (we could hear our neighbour snore), but they do provide ear plugs! It\'s also very close to the airport','2019-07-27 10:50:44'),(31,88,8,25,NULL,4,'The apartment was clean, convenient and very well managed by the host!','2019-07-27 10:50:44'),(32,90,9,8,NULL,5,'Not a single thing that I would want to change about this place. A nice quiet location just outside of Boston. The interior of the home is phenomenal. Made us feel extremely at home!','2019-07-27 10:50:44'),(33,91,10,26,NULL,4,'Incredible place in an perfect location. Great value! No need to look anywhere else in Boston!','2019-07-27 10:50:45'),(34,30,9,13,10,NULL,'sorry','2019-07-28 14:14:33');
/*!40000 ALTER TABLE `listing_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rented`
--

DROP TABLE IF EXISTS `rented`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `rented` VALUES (1,22,'2019-05-16','2019-05-21',1,'2019-07-24 18:40:09',177.77),(1,38,'2019-06-26','2019-07-01',1,'2019-07-25 13:26:24',150),(1,86,'2019-04-02','2019-04-05',1,'2019-07-27 10:32:26',350),(2,35,'2019-08-05','2019-08-22',0,'2019-07-24 18:56:15',150),(2,40,'2019-01-10','2019-01-15',1,'2019-07-25 13:26:25',125),(2,92,'2019-07-20','2019-07-23',1,'2019-07-27 10:33:59',150),(3,42,'2019-02-10','2019-02-15',1,'2019-07-25 13:26:25',125),(3,67,'2019-08-25','2019-08-29',0,'2019-07-26 16:53:05',200),(4,17,'2019-06-27','2019-07-02',1,'2019-07-24 17:51:46',177.77),(4,43,'2019-07-13','2019-07-19',1,'2019-07-25 13:26:26',125),(4,71,'2019-09-20','2019-09-23',0,'2019-07-26 16:53:05',150),(5,19,'2019-05-01','2019-05-05',1,'2019-07-24 18:29:12',177.77),(5,45,'2019-04-01','2019-04-06',1,'2019-07-25 13:26:27',200),(5,73,'2019-09-25','2019-09-28',0,'2019-07-26 16:53:06',150),(5,76,'2019-04-02','2019-04-05',1,'2019-07-26 17:50:37',300),(6,23,'2019-06-05','2019-06-20',1,'2019-07-24 18:42:13',177.77),(6,77,'2019-07-05','2019-07-09',1,'2019-07-26 17:50:37',130),(7,24,'2019-03-03','2019-03-07',1,'2019-07-24 18:56:15',200),(7,78,'2019-07-20','2019-07-23',1,'2019-07-26 17:50:38',150),(8,37,'2019-12-05','2019-12-07',0,'2019-07-24 18:56:15',156),(8,79,'2019-07-20','2019-07-23',1,'2019-07-26 17:50:38',150),(8,81,'2019-10-20','2019-10-21',0,'2019-07-26 18:12:17',150),(8,88,'2019-05-02','2019-05-05',1,'2019-07-27 10:32:26',200),(9,80,'2019-07-20','2019-07-23',1,'2019-07-26 17:50:39',150),(9,90,'2019-05-02','2019-05-05',1,'2019-07-27 10:32:26',200),(10,82,'2019-10-02','2019-10-03',0,'2019-07-26 18:12:46',150),(10,91,'2019-07-25','2019-07-28',1,'2019-07-27 10:33:59',150),(11,28,'2019-05-01','2019-05-10',1,'2019-07-24 18:56:15',100),(11,83,'2019-11-22','2019-11-23',0,'2019-07-26 18:12:18',150),(12,29,'2019-06-01','2019-06-05',1,'2019-07-24 18:56:15',100),(12,33,'2019-07-01','2019-07-02',1,'2019-07-29 00:59:32',150),(13,30,'2019-07-01','2019-07-10',1,'2019-07-24 18:56:15',100),(13,64,'2019-07-20','2019-07-22',1,'2019-07-26 16:22:03',175),(14,66,'2019-07-20','2019-07-23',1,'2019-07-26 16:22:03',150),(15,68,'2019-07-20','2019-07-23',1,'2019-07-26 16:22:04',150),(16,69,'2019-07-20','2019-07-23',1,'2019-07-26 16:22:05',150),(20,70,'2019-07-20','2019-07-23',1,'2019-07-26 16:22:05',150);
/*!40000 ALTER TABLE `rented` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'A',2,'A','A','graduate','2019-07-24 13:00:47','1996-03-05','A','DMJoY2XsnfHaFqOa3kGGugVByA3l36IyHSWf/bnodas=$Ocd9q7KjC3elVURSQ3/FgwRBTI+JFA7ba7APL1aRB+4='),(2,'B',2,'B','B','graduate','2019-07-24 13:00:47','1996-03-05','B','t9OW15e8FBiQvWTUhPjTcZGA/CYMRNKuE45pVV668g4=$oZc7qVZaC/iYqlQmJRUCam/32euddnWl5jJsqS4cT3Y='),(3,'C',2,'C','C','graduate','2019-07-24 13:00:48','1996-03-05','C','TU2bLpB9w0i1yGBxZt9cOSHUaOzCSCvLyC6HtKLV1DY=$oxKNpJ3r/jIwKJyO9AI5QGhx3FfBBSunircvrsvmyWw='),(4,'D',1,'D','D','graduate','2019-07-24 13:00:48','1996-03-05','D','CeyFXJb8iRHxcuccTfrd1oGwFZFsi5hNwzlYiIqb4rs=$cEcQrnHD0qYrnGigvd3je8F9M88gNoNxflBoUXsV7Bw='),(5,'E',1,'E','E','graduate','2019-07-24 13:00:48','1996-03-05','E','Gtmhd2z59zJZJchxv3TFAFw25JB4DBYw9NKahbXZ35o=$6PDVspfFqvIl/HEEBkH+cIqFi9PvUsSSwo6oyenkhRg='),(6,'F',1,'F','F','graduate','2019-07-24 13:00:48','1996-03-05','F','LXXe+dXa2Qt4m6o/H7+BGLpNwJB9MqQSR9e7nCw2nyU=$0uoexiNCngBdPB8Tb9bxplWg6lGrvsqKFgjXLDWnfI8='),(7,'G',1,'G','G','graduate','2019-07-24 13:00:49','1996-03-05','G','4igHZ3CsgbMTGCOA1CDvtD+6YsYkzTQWz7fcJApcRAQ=$zATV5JBys4BYpLiP4WCav/G3Dp17gMfsRFo/su1rFKg='),(8,'H',2,'H','H','graduate','2019-07-24 13:00:49','1996-03-05','H','765zmrpphZZ6sgia8Rm8A58mokw80jhfRa+CM3oxUoY=$XTE7IBSEPcguYg+oTg62l7lzD9dtwfDEMW9I5D97G1k='),(9,'I',2,'I','I','graduate','2019-07-24 13:00:49','1996-03-05','I','fa6iQVNSs5JacEr9BLNFyFZ9PewHk9PcPO7pNbFDuMs=$S8/YxlpiT6nUGPpGpOhBF4qshmwP6EuuarGj7UeWImo='),(10,'J',2,'J','J','graduate','2019-07-24 13:00:50','1996-03-05','J','cgqjY3mWWHtpC0aOr5KOk73sJk8ejivHy4CmD6y9RHo=$PUSA9M1wb3v90shgpS57pyfqttEq28Pw4jzBUvPPcrw='),(11,'K',1,'K','K','graduate','2019-07-24 19:15:42','1996-03-05','K','xYlmA0kyoi1SIki++zxTtUJbSSeJEIpivfUddY0v7Eg=$MzdOVVGHoFf8U9l20owh+N2Xycl/Tq2clxBdxW4a1VU='),(12,'L',1,'L','L','graduate','2019-07-24 19:15:43','1996-03-05','L','QvKnHnJFwUOTNZPwSlY3gEkf592tA+weEFISZjX21GY=$jLoCQioNmeXFlMFuNsiuUTfkbE2Uo8cMGk/lHhQZFf0='),(13,'M',2,'M','M','graduate','2019-07-24 19:15:43','1996-03-05','M','3spm2BwI4tcg3PQQAHbzJaZ/ASMzvLKj/KXGc/epN9E=$3C2WeOJs+dQ8wSUo/arnqzPfcqE9PeAbEsGONOWf8BQ='),(14,'N',2,'N','N','graduate','2019-07-24 19:15:43','1996-03-05','N','pOYrAQ/jQwnEavzNlmk4g0dkIYJUJ1KXZUVFud3JUv4=$l7jHtHlSvsDjh3u7/A2/HGXFKNi9EdersV4E5hZGoj0='),(15,'O',2,'O','O','graduate','2019-07-25 10:22:20','1996-03-05','O','Cwfmi2e73UWUDS9irTtD5D9b+c9pNX83EZftRUtxTCg=$M1aWVAhpLsx/OThsThryo0CR5Z1ScoLvaa6c1HKjOnc='),(16,'P',1,'P','P','graduate','2019-07-25 10:22:21','1996-03-05','P','GxwrtMK1jY+2h404yx1FfT2AqT/MiwgJhDl+OvuTf5s=$355jSLjZ4FHTfSsrHII/zFKm+OP72O6Zwk7/bR4WAZQ='),(18,'R',2,'R','R','graduate','2019-07-26 13:52:53','1996-03-05','R','qak3nAR6IZi9o2ZQCfa1ALBetpqRg4UZG79CNviUplw=$HNi0QgCBcIot2nZWI/eKDM9xHWhG9RJJhq6xC07ke38='),(19,'S',2,'S','S','graduate','2019-07-26 13:52:54','1996-03-05','S','eGXqAko/ltXRj4vymoHKkWFcJCLXedsIzj2nGyLXOA0=$+RLYl6Cy6gPB9nu4kKs/1MLYZr2EhZY8kkNm4BBYTNg='),(20,'T',1,'T','T','graduate','2019-07-26 13:52:54','1996-03-05','T','3NBQYTLZc5qQHHWCPE6SznFO3SE9uoW+BKTY6lv9n24=$DS8Bqz9Qrd6d8njkDQ4B8ruSVTQ4wRWZ7LoYGa63QKc='),(21,'Q',2,'Q','Q','graduate','2019-07-26 17:17:46','1996-03-05','Q','fIEfovr0qn2h5XUTeTgDPMEXGpxNByJLmgc19N5lt5o=$PY21YeQmpbhBYgM6LCwGm4+5zilLYJA4ywcE5thvLDM='),(22,'U',2,'U','U','graduate','2019-07-26 17:17:47','1996-03-05','U','ZqU6bRkeObm84LBRf/Pc22u7Hy9GBNBLkgdLIhy8yZ4=$POxGXjV1i0ZQoDJlWEoQedQE+zY0QZl9BS6mF9QJkPY='),(23,'V',1,'V','V','graduate','2019-07-26 17:17:48','1996-03-05','V','bKfbJ2PFLUTxPEvwahgKAitFIrvbGqSZobLTxzvet0s=$WzC5ngMlmq+nKrRm6Jo7CYBQ5Rfp00YvD97b2l11xLA='),(24,'W',2,'W','W','graduate','2019-07-26 18:41:16','1996-03-05','W','Mx1ug40KgBKJQezDP3DOjhxT6k5JWT/7iOcCTetFi8w=$7ehE/rjOhJgVhfjYToLjeLwCQ8QWpqBiXptvO0iujCQ='),(25,'X',2,'X','X','graduate','2019-07-26 18:41:17','1996-03-05','X','D4VPoT8HmipYcSeIXJvhi7mUf0aCXyNXKnDhB7UFnj8=$n+jHFfC2pxBVVAZZmS3SGyu6Qg/KlUkhVy0/lXEvaek='),(26,'Y',2,'Y','Y','graduate','2019-07-26 18:41:18','1996-03-05','Y','5beWeTAJXxLR9/uF8q5IHs6AgdVJoRFdhjAGOjVlerg=$ltN4EiViBjgsFtkJ7vHcDJQ3j7FDAqrOxW12LvL8kcU='),(27,'admin',2,'1','admin','admin','2019-07-29 14:14:43','1990-01-01','1','8HQdQ1kt+qGBA/3SKX1gXQh7JKJdQg35G59VrcRa5dA=$hh1TTngWvmtj78WcZCTW0NsbVAl6ISdrOHXCwOktx5s=');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_comment`
--

DROP TABLE IF EXISTS `user_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `parent_comment` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `content` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_comment`
--

LOCK TABLES `user_comment` WRITE;
/*!40000 ALTER TABLE `user_comment` DISABLE KEYS */;
INSERT INTO `user_comment` VALUES (3,5,2,NULL,3,'The host is okay but he should renovate his house','2019-07-26 12:13:18'),(4,1,3,NULL,5,'The host was a wonderful host, super helpful and super easy to communicate with. He kindly let us check in early so we didn’t have to carry our bags around Toronto. The place itself was beautiful, and really relaxed setting.','2019-07-26 12:13:19'),(5,6,3,NULL,4,'Breathtaking view of the city, very modern yet homey feel. Will stay again!! Had an amazing time','2019-07-26 12:13:19'),(6,7,3,NULL,4,'It is good overall','2019-07-26 12:13:19'),(7,4,1,NULL,5,'As always, 5 star ... will book again for sure!','2019-07-26 12:18:27'),(8,11,9,NULL,5,'The place is great. The place is clean, stylish and has a parking spot included which was quite convenient for us. Would definitely go back!','2019-07-26 12:41:31'),(9,12,9,NULL,2,'The host is friendly but the house is not good','2019-07-26 12:41:31'),(10,13,9,NULL,3,'Mediocre','2019-07-26 12:41:31'),(11,2,15,NULL,4,'The room is so clean and super organized. The location is GREAT as it’s close to everything we needed during our stay. I would come back sometime soon.','2019-07-26 12:41:32'),(12,1,13,NULL,4,'Super clean modern space in a great quiet location.','2019-07-26 13:15:38'),(13,4,15,NULL,3,'I hope the host can install an AC sooner','2019-07-26 13:17:04'),(14,3,15,NULL,3,'I will not recommend this to others unless AC is installed','2019-07-26 13:17:24'),(15,13,18,NULL,5,'She is an excellent host and even let us check in a bit early.','2019-07-26 16:43:36'),(17,14,19,NULL,5,'Her place was a wonderful place conveniently located in Manhattan with easy access to train stations.','2019-07-26 16:47:25'),(18,15,9,NULL,5,'Would recommend this to anyone. She is one of the best hosts I have met.','2019-07-26 16:47:25'),(19,16,9,NULL,5,'This was my 3rd stay at this host place and it was even better than the last. The location is perfect and the apartment is as described. I cannot recommend it enough.','2019-07-26 16:47:25'),(20,20,9,NULL,5,'I recommend his place.','2019-07-26 16:47:25'),(21,5,21,NULL,5,'This place is truly a gem! We had an absolutely great time and Vince was the perfect host. He made us feel very welcome and catered to all our needs.','2019-07-26 18:06:06'),(22,6,22,NULL,5,'The host even put out some snacks and had a good supply of bottled water for us. He is very considerate.','2019-07-26 18:06:06'),(23,7,10,NULL,5,'Not recommend this','2019-07-26 18:06:06'),(24,8,10,NULL,5,'This host was great','2019-07-26 18:06:07'),(25,9,10,NULL,5,'Great please. Very recommended. Near to everything in LA and nice neighborhood.','2019-07-26 18:06:07'),(26,1,24,NULL,5,'We had an awesome time staying at it! Their place was clean, homey and they had so many thoughtful touches that improved our stay even more. Definitely recommend to anyone visiting Boston!','2019-07-27 10:49:29'),(27,2,26,NULL,5,'Great place to stay! Super clean and cosy.','2019-07-27 10:50:44'),(28,8,25,NULL,5,'I Highly recommend it','2019-07-27 10:50:44'),(29,9,8,NULL,5,' This is a great place! It\' s clear that the host really care about their guests\' experience. Highly recommend!','2019-07-27 10:50:44'),(30,10,26,NULL,5,'This place could not be in a better location. The home was clean and as advertised. All responses to questions we had were given quickly. I would recommend this place to any visitors in Boston.','2019-07-27 10:50:45'),(31,9,13,NULL,5,'Honest renter','2019-07-28 14:17:20');
/*!40000 ALTER TABLE `user_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

CREATE TRIGGER delete_addr_after_delete_listing AFTER DELETE ON listing

FOR EACH ROW

                DELETE FROM address

WHERE address.id = OLD.id and address.type = 0;

 

CREATE TRIGGER delete_addr_after_delete_user AFTER DELETE ON user

FOR EACH ROW

            DELETE FROM address

    WHERE address.id = OLD.id and address.type = 1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-29 14:19:26
