-- MySQL dump 10.13  Distrib 8.0.37, for Linux (x86_64)
--
-- Host: localhost    Database: stylish_db
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `picture` varchar(255) NOT NULL,
  `story` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `campaign_product_id_foreign` (`product_id`),
  CONSTRAINT `campaign_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` VALUES (1,'def','def'),(2,'yellow','ffff00'),(3,'pink','ffaa00'),(4,'red','ff0000'),(5,'green','00ff00'),(6,'blue','0000ff'),(7,'white','ffffff'),(8,'black','000000'),(9,'blue','000055'),(10,'brown','f55f0f'),(11,'azure','0080FF'),(12,'azure','007FFF'),(13,'azure','006FFF');
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hots`
--

DROP TABLE IF EXISTS `hots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hots`
--

LOCK TABLES `hots` WRITE;
/*!40000 ALTER TABLE `hots` DISABLE KEYS */;
/*!40000 ALTER TABLE `hots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hots_product`
--

DROP TABLE IF EXISTS `hots_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hots_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hots_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `hots_product_product_id_foreign` (`product_id`),
  KEY `hots_product_hots_id_foreign` (`hots_id`),
  CONSTRAINT `hots_product_hots_id_foreign` FOREIGN KEY (`hots_id`) REFERENCES `hots` (`id`),
  CONSTRAINT `hots_product_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hots_product`
--

LOCK TABLES `hots_product` WRITE;
/*!40000 ALTER TABLE `hots_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `hots_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` int NOT NULL,
  `texture` varchar(255) NOT NULL,
  `wash` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `story` text NOT NULL,
  `main_image` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'w','def','def',2,'def','def','def','def','def','ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.comimg/f27fe4cc-6c13-44d1-b932-d50f9c508a84.jpg'),(2,'woman','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/45db5e92-dec2-4fd0-be7a-041c0dd15310.jpg'),(3,'defasdfasdf','defqwerqwer','defqwer',8,'defqwer','defqwer','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/e91af162-b8a7-4d08-b629-22de80bb1b78.jpg'),(4,'def','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/d8317c48-661e-4bfb-94f3-921628d4a6d8.jpg'),(5,'def','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/53a3972a-5497-4f76-95a9-1105655ef9a9.jpg'),(6,'def','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/6fed01b0-1d4f-4a8b-8fc6-1b0dcefd53f5.jpg'),(7,'def','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/f65ddef1-1d52-4479-a619-92e03d098d3c.jpg'),(8,'def','def','def',2,'def','def','def','def','def','http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/6a47d6cc-d2d2-4f64-9580-98b32797e5cc.webp'),(9,'def','def','def',2,'def','def','def','def','def','http://52.196.104.13/img/e1f2db7a-6f76-4b90-a437-05f1dea562cb.webp'),(10,'def','def','def',2,'def','def','def','def','def','http://52.196.104.13/img/808bff7e-d3bf-4635-9a64-597e61a03039.jpg'),(11,'women','可愛洋裝','def',180,'def','可水洗','TW','def','def','http://52.196.104.13/img/4d49c371-a987-408d-b6f8-0f07f8d578a3.webp'),(12,'women','大可愛洋裝','def',230,'def','可水洗','TW','def','def','http://52.196.104.13/img/dfd1b993-744f-4a3a-bf17-97c0a6f04cfa.jpg'),(13,'women','純白素色洋裝','def',1000,'def','可水洗','TW','def','def','http://52.196.104.13/img/cbc0e90c-7895-4656-8987-209af5326f8e.jpg'),(14,'women','紅色洋裝','def',1111,'def','可水洗','TW','def','def','http://52.196.104.13/img/f08eab77-89a8-444d-baac-2bf11e38e574.jpg'),(15,'women','黑色神秘洋裝','def',2222,'def','可水洗','TW','def','def','http://52.196.104.13/img/6e0c3120-25ce-4715-a383-da4db7df7c5b.jpg'),(16,'women','藍色清新洋裝','def',3333,'def','可水洗','TW','def','def','http://52.196.104.13/img/a69bafa4-0eb4-445b-890c-d94100df94ad.jpg'),(17,'women','藏青復古洋裝','def',4444,'def','可水洗','TW','def','def','http://52.196.104.13/img/79df1191-4f6d-4ec8-a7d9-34beff32a5dd.jpg'),(18,'women','棕色古風洋裝','def',222,'def','def','def','def','def','http://52.196.104.13/img/15ffe37a-c272-47e6-8140-4ba86adad1dc.jpg'),(19,'women','水色洋裝','def',2223,'def','def','def','def','def','http://52.196.104.13/img/3d396569-34c2-4d8f-bc5b-a39a2ef51e11.jpg'),(20,'women','深黑色洋裝','def',2223,'def','def','def','def','def','http://52.196.104.13/img/753e33d2-f587-4971-b376-204bcb70fe54.jpg'),(21,'women','深藍色洋裝','def',1112,'def','def','def','def','def','http://52.196.104.13/img/ca803797-7401-436f-a1c7-345398a2328c.jpg'),(22,'women','淺藍色少女洋裝','def',1212,'def','def','def','def','def','http://52.196.104.13/img/6aaee3ad-3620-40ce-9c79-5659921ef8c0.jpg'),(23,'women','淺藍色少女洋裝','def',123,'def','def','def','def','def','http://52.196.104.13/img/9df5140c-efac-4f69-a840-b384bf3b04b7.jpg'),(24,'women','淺淺藍色少女洋裝','def',124,'def','def','def','def','def','http://52.196.104.13/img/7162f2e5-d712-4dfa-9e2b-82eb8b349d15.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_image_product_id_foreign` (`product_id`),
  CONSTRAINT `product_image_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,'ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.comimg/8db66a86-57bf-4358-a6ce-5f136cebe9a4.jpg',1),(2,'ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.comimg/92852275-79fd-427a-8410-3eb425a082aa.png',1),(3,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/965fd3db-f69b-47a2-8d4d-e5639df87c57.jpg',2),(4,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/4a5a19bf-c5dd-4752-a7e5-a88796976466.png',2),(5,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/736ece36-42ad-4cb4-9923-7166ab1394fa.png',3),(6,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/53643888-3467-40b4-a37e-ddcd05da4c24.jpg',4),(7,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/f50cac7c-9707-4fc7-be54-e0b1fb61a893.jpg',5),(8,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/d5830fef-1090-4fde-963a-fe7a9a17437a.jpg',6),(9,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/d07567cb-7506-48a0-b55e-ecea3b74e401.jpg',7),(10,'http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com/img/d1eba052-eef9-467d-bd29-7d82d4144727.webp',8),(11,'http://52.196.104.13/img/090097c7-8c41-49a6-ac27-c73899440e42.jpg',9),(12,'http://52.196.104.13/img/4c3cd81f-3f59-46e5-9a02-0664b2fa790d.png',10),(13,'http://52.196.104.13/img/138e029d-a7c3-4967-8f51-1d95594c8ea0.webp',11),(14,'http://52.196.104.13/img/31f2e231-493c-4279-b22e-a2f8e28cd66f.jpg',11),(15,'http://52.196.104.13/img/ed6a8bdf-c340-4821-bc9f-a6dd21fbaac2.jpg',12),(16,'http://52.196.104.13/img/78db7f11-0064-4e0a-87b7-b781add64060.jpg',12),(17,'http://52.196.104.13/img/b52a162e-4414-4a83-aa88-48d048bcacb3.jpg',13),(18,'http://52.196.104.13/img/761d8622-65d5-49f5-a3ef-10a5d57a1cde.jpg',13),(19,'http://52.196.104.13/img/e85119fb-c9a6-427f-b749-3e6e89cadc1c.jpg',14),(20,'http://52.196.104.13/img/6f3d2132-9bce-4eda-9633-b5d31a41c74b.jpg',14),(21,'http://52.196.104.13/img/ded13aaf-83b0-491d-a7ac-6b7ff6929d25.jpg',15),(22,'http://52.196.104.13/img/139a3305-d420-4172-9650-79cac223f554.jpg',15),(23,'http://52.196.104.13/img/29169430-f308-4cc7-9154-0ed4847b02ed.jpg',16),(24,'http://52.196.104.13/img/58b2ea18-5ac8-4c07-954c-18d0131ceb9b.jpg',16),(25,'http://52.196.104.13/img/d9f30865-e616-4f96-850f-2c501a9ab7fa.jpg',16),(26,'http://52.196.104.13/img/b5d06304-79cb-4f60-be28-22086efba64b.jpg',17),(27,'http://52.196.104.13/img/ec1b19bb-2a89-4ea8-8629-1803d0b9757c.jpg',17),(28,'http://52.196.104.13/img/4643d945-5149-41ff-89b4-5b149b2c24d9.jpg',17),(29,'http://52.196.104.13/img/c44cc2e6-3c02-49ad-b9a2-6d473efab205.jpg',18),(30,'http://52.196.104.13/img/679da733-0d44-47c1-9bb0-e793e95b6cba.jpg',19),(31,'http://52.196.104.13/img/b604edab-62a2-49a0-8ad7-d468cfcbcbba.jpg',19),(32,'http://52.196.104.13/img/a7b4ff2c-1237-4cf4-8fca-a62fbda674c4.jpg',20),(33,'http://52.196.104.13/img/330b4853-545c-4e1a-8fd9-8248fd2b5cb2.jpg',20),(34,'http://52.196.104.13/img/4b6e74ca-c2bf-4a3b-8b86-f7da0247dd59.jpg',21),(35,'http://52.196.104.13/img/08cc3044-9cbb-47ce-8db8-72b87851e523.jpg',21),(36,'http://52.196.104.13/img/209b3f1b-f6a6-4733-9bf9-273647e3d245.jpg',22),(37,'http://52.196.104.13/img/53ba81f7-f86e-435e-87cb-a632f7fd87ad.jpg',22),(38,'http://52.196.104.13/img/ffbb6285-479f-4c7f-8c9c-a84c77998d22.jpg',23),(39,'http://52.196.104.13/img/518de6c4-19ec-4086-904d-06682bba0e79.jpg',23),(40,'http://52.196.104.13/img/df70989d-7429-49c2-bfcb-dd29f82a77b4.jpg',24),(41,'http://52.196.104.13/img/3650938e-27e8-4371-a477-922432152bd5.jpg',24);
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variant`
--

DROP TABLE IF EXISTS `product_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `colors_id` int NOT NULL,
  `sizes_id` int NOT NULL,
  `stock` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_variant` (`sizes_id`,`colors_id`,`product_id`),
  KEY `product_variant_colors_id_foreign` (`colors_id`),
  KEY `product_variant_product_id_foreign` (`product_id`),
  CONSTRAINT `product_variant_colors_id_foreign` FOREIGN KEY (`colors_id`) REFERENCES `colors` (`id`),
  CONSTRAINT `product_variant_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_variant_sizes_id_foreign` FOREIGN KEY (`sizes_id`) REFERENCES `sizes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variant`
--

LOCK TABLES `product_variant` WRITE;
/*!40000 ALTER TABLE `product_variant` DISABLE KEYS */;
INSERT INTO `product_variant` VALUES (1,1,1,1,1),(2,1,1,1,2),(3,1,1,1,3),(4,1,1,1,4),(5,1,1,1,5),(6,1,1,1,6),(7,1,1,1,7),(8,1,1,1,8),(9,1,1,1,9),(10,1,1,1,10),(11,2,2,120,11),(12,3,2,100,11),(13,4,2,12,12),(14,5,3,13,12),(15,6,4,14,12),(16,7,2,120,13),(17,4,4,123,14),(18,8,4,123,15),(19,6,2,1,16),(20,6,3,1,16),(21,6,4,1,16),(22,9,4,1,17),(23,10,2,1,18),(24,11,2,13,19),(25,11,3,12,19),(26,8,2,13,20),(27,6,2,13,21),(28,12,2,133,22),(29,12,2,13,23),(30,12,2,12,24),(31,13,2,13,24);
/*!40000 ALTER TABLE `product_variant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sizes`
--

DROP TABLE IF EXISTS `sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sizes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sizes`
--

LOCK TABLES `sizes` WRITE;
/*!40000 ALTER TABLE `sizes` DISABLE KEYS */;
INSERT INTO `sizes` VALUES (1,'def'),(2,'S'),(3,'M'),(4,'L');
/*!40000 ALTER TABLE `sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-23  8:48:06
