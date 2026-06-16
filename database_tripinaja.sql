/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.1.2-MariaDB, for osx10.21 (arm64)
--
-- Host: 127.0.0.1    Database: db_tripinaja
-- ------------------------------------------------------
-- Server version	12.1.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `accommodation`
--

DROP TABLE IF EXISTS `accommodation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `accommodation` (
  `accommodation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `provinsi_id` int(10) unsigned NOT NULL,
  `nama` varchar(255) NOT NULL,
  `lokasi` varchar(255) DEFAULT NULL,
  `harga_per_malam` double DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`accommodation_id`),
  KEY `idx_accommodation_provinsi_id` (`provinsi_id`),
  CONSTRAINT `fk_accommodation_provinsi` FOREIGN KEY (`provinsi_id`) REFERENCES `provinsi` (`provinsi_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accommodation`
--

LOCK TABLES `accommodation` WRITE;
/*!40000 ALTER TABLE `accommodation` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `accommodation` VALUES
(1,1,'Hotel Kuta Indah','Bali',750000,4.5,'2026-06-14 13:59:09'),
(2,2,'Villa Bandung Asri','Bandung',500000,4.4,'2026-06-14 13:59:09');
/*!40000 ALTER TABLE `accommodation` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `admin_level` int(10) unsigned NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_admin_user_id` (`user_id`),
  CONSTRAINT `fk_admin_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `admin` VALUES
(1,1,1,'2026-06-14 13:59:09');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `destinasi`
--

DROP TABLE IF EXISTS `destinasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `destinasi` (
  `destinasi_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `provinsi_id` int(10) unsigned NOT NULL,
  `nama` varchar(255) NOT NULL,
  `lokasi` varchar(255) DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`destinasi_id`),
  KEY `idx_destinasi_provinsi_id` (`provinsi_id`),
  CONSTRAINT `fk_destinasi_provinsi` FOREIGN KEY (`provinsi_id`) REFERENCES `provinsi` (`provinsi_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinasi`
--

LOCK TABLES `destinasi` WRITE;
/*!40000 ALTER TABLE `destinasi` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `destinasi` VALUES
(1,1,'Pantai Kuta','Bali','Pantai terkenal di Bali',50000,4.8,'2026-06-14 13:59:09'),
(2,1,'Tanah Lot','Bali','Destinasi wisata religi dan sunset',60000,4.9,'2026-06-14 13:59:09'),
(3,2,'Kawah Putih','Bandung','Wisata alam kawah',30000,4.7,'2026-06-14 13:59:09'),
(4,1,'Bali','Indonesia','asdfsafds',150000,4.5,'2026-06-16 17:42:44');
/*!40000 ALTER TABLE `destinasi` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `itinerary_day`
--

DROP TABLE IF EXISTS `itinerary_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `itinerary_day` (
  `day_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itinerary_id` int(10) unsigned NOT NULL,
  `hari_ke` int(10) unsigned NOT NULL,
  `catatan` text DEFAULT NULL,
  `biaya_hari` double NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`day_id`),
  UNIQUE KEY `ux_itinerary_day_unique` (`itinerary_id`,`hari_ke`),
  CONSTRAINT `fk_itinerary_day` FOREIGN KEY (`itinerary_id`) REFERENCES `itinerary_total` (`itinerary_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary_day`
--

LOCK TABLES `itinerary_day` WRITE;
/*!40000 ALTER TABLE `itinerary_day` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `itinerary_day` VALUES
(13,6,1,'Day 1 - Explore and Adventure',2300000,'2026-06-16 20:53:54'),
(14,7,1,'Day 1 - Explore and Adventure',2300000,'2026-06-16 21:11:20'),
(15,7,2,'Day 2 - Explore and Adventure',1110000,'2026-06-16 21:11:20'),
(16,8,1,'Day 1 - Explore and Adventure',780000,'2026-06-16 21:13:59'),
(17,8,2,'Day 2 - Explore and Adventure',750000,'2026-06-16 21:13:59'),
(18,8,3,'Day 3 - Explore and Adventure',750000,'2026-06-16 21:13:59'),
(19,9,1,'Day 1 - Explore and Adventure',780000,'2026-06-16 21:14:32'),
(20,9,2,'Day 2 - Explore and Adventure',750000,'2026-06-16 21:14:32'),
(21,9,3,'Day 3 - Explore and Adventure',750000,'2026-06-16 21:14:32'),
(22,10,1,'Day 1 - Explore and Adventure',2300000,'2026-06-16 21:18:44'),
(23,11,1,'Day 1 - Explore and Adventure',780000,'2026-06-16 21:19:59');
/*!40000 ALTER TABLE `itinerary_day` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `itinerary_day_accommodation`
--

DROP TABLE IF EXISTS `itinerary_day_accommodation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `itinerary_day_accommodation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `day_id` int(10) unsigned NOT NULL,
  `accommodation_id` int(10) unsigned NOT NULL,
  `checkin_time` datetime DEFAULT NULL,
  `checkout_time` datetime DEFAULT NULL,
  `malam` int(10) unsigned NOT NULL DEFAULT 1,
  `biaya` double NOT NULL,
  `catatan` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_day_accommodation` (`day_id`,`accommodation_id`),
  KEY `fk_itineraryday_accommodation_accommodation` (`accommodation_id`),
  CONSTRAINT `fk_itineraryday_accommodation_accommodation` FOREIGN KEY (`accommodation_id`) REFERENCES `accommodation` (`accommodation_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_itineraryday_accommodation_day` FOREIGN KEY (`day_id`) REFERENCES `itinerary_day` (`day_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary_day_accommodation`
--

LOCK TABLES `itinerary_day_accommodation` WRITE;
/*!40000 ALTER TABLE `itinerary_day_accommodation` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `itinerary_day_accommodation` VALUES
(12,13,1,'2026-06-16 07:00:54','2026-06-16 05:00:54',1,750000,NULL,'2026-06-16 20:53:54'),
(13,14,1,NULL,NULL,1,750000,NULL,'2026-06-16 21:11:20'),
(14,15,1,NULL,NULL,1,750000,NULL,'2026-06-16 21:11:20'),
(15,16,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:13:59'),
(16,17,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:13:59'),
(17,18,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:13:59'),
(18,19,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:14:32'),
(19,20,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:14:32'),
(20,21,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:14:32'),
(21,22,1,NULL,NULL,1,750000,NULL,'2026-06-16 21:18:44'),
(22,23,2,NULL,NULL,1,500000,NULL,'2026-06-16 21:19:59');
/*!40000 ALTER TABLE `itinerary_day_accommodation` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `itinerary_day_destinasi`
--

DROP TABLE IF EXISTS `itinerary_day_destinasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `itinerary_day_destinasi` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `day_id` int(10) unsigned NOT NULL,
  `destinasi_id` int(10) unsigned NOT NULL,
  `urutan` int(10) unsigned NOT NULL DEFAULT 1,
  `durasi_menit` int(10) unsigned DEFAULT NULL,
  `biaya` double NOT NULL,
  `catatan` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_day_destinasi` (`day_id`,`destinasi_id`),
  UNIQUE KEY `ux_day_destinasi_urutan` (`day_id`,`urutan`),
  KEY `fk_itineraryday_destinasi_destinasi` (`destinasi_id`),
  CONSTRAINT `fk_itineraryday_destinasi_day` FOREIGN KEY (`day_id`) REFERENCES `itinerary_day` (`day_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_itineraryday_destinasi_destinasi` FOREIGN KEY (`destinasi_id`) REFERENCES `destinasi` (`destinasi_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary_day_destinasi`
--

LOCK TABLES `itinerary_day_destinasi` WRITE;
/*!40000 ALTER TABLE `itinerary_day_destinasi` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `itinerary_day_destinasi` VALUES
(12,13,1,1,120,50000,NULL,'2026-06-16 20:53:54'),
(13,14,1,1,120,50000,NULL,'2026-06-16 21:11:20'),
(14,15,2,1,120,60000,NULL,'2026-06-16 21:11:20'),
(15,16,3,1,120,30000,NULL,'2026-06-16 21:13:59'),
(16,19,3,1,120,30000,NULL,'2026-06-16 21:14:32'),
(17,22,1,1,120,50000,NULL,'2026-06-16 21:18:44'),
(18,23,3,1,120,30000,NULL,'2026-06-16 21:19:59');
/*!40000 ALTER TABLE `itinerary_day_destinasi` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `itinerary_day_transport`
--

DROP TABLE IF EXISTS `itinerary_day_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `itinerary_day_transport` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `day_id` int(10) unsigned NOT NULL,
  `transport_id` int(10) unsigned NOT NULL,
  `urutan` int(10) unsigned NOT NULL DEFAULT 1,
  `waktu_berangkat` datetime DEFAULT NULL,
  `waktu_tiba` datetime DEFAULT NULL,
  `biaya` double NOT NULL,
  `catatan` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_day_transport` (`day_id`,`transport_id`),
  UNIQUE KEY `ux_day_transport_urutan` (`day_id`,`urutan`),
  KEY `fk_itineraryday_transport_transport` (`transport_id`),
  CONSTRAINT `fk_itineraryday_transport_day` FOREIGN KEY (`day_id`) REFERENCES `itinerary_day` (`day_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_itineraryday_transport_transport` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`transport_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary_day_transport`
--

LOCK TABLES `itinerary_day_transport` WRITE;
/*!40000 ALTER TABLE `itinerary_day_transport` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `itinerary_day_transport` VALUES
(13,13,1,1,'2026-06-16 01:00:54','2026-06-16 10:00:54',1500000,NULL,'2026-06-16 20:53:54'),
(14,14,4,1,NULL,NULL,1500000,NULL,'2026-06-16 21:11:20'),
(15,15,2,1,NULL,NULL,300000,NULL,'2026-06-16 21:11:20'),
(16,16,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:13:59'),
(17,17,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:13:59'),
(18,18,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:13:59'),
(19,19,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:14:32'),
(20,20,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:14:32'),
(21,21,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:14:32'),
(22,22,1,1,NULL,NULL,1500000,NULL,'2026-06-16 21:18:44'),
(23,23,3,1,NULL,NULL,250000,NULL,'2026-06-16 21:19:59');
/*!40000 ALTER TABLE `itinerary_day_transport` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `itinerary_total`
--

DROP TABLE IF EXISTS `itinerary_total`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `itinerary_total` (
  `itinerary_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `traveler_id` int(10) unsigned NOT NULL,
  `title` varchar(255) NOT NULL,
  `total_hari` int(10) unsigned NOT NULL DEFAULT 1,
  `total_biaya` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`itinerary_id`),
  KEY `idx_itinerary_traveler_id` (`traveler_id`),
  CONSTRAINT `fk_itinerary_traveler` FOREIGN KEY (`traveler_id`) REFERENCES `traveler` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary_total`
--

LOCK TABLES `itinerary_total` WRITE;
/*!40000 ALTER TABLE `itinerary_total` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `itinerary_total` VALUES
(6,1,'Trip to Bali',1,2300000,'DRAFT','2026-06-16 20:53:54','2026-06-16 20:53:54'),
(7,1,'Trip to Bali',2,3410000,'DRAFT','2026-06-16 21:11:20','2026-06-16 21:11:20'),
(8,1,'Trip to Jawa Barat',3,2280000,'DRAFT','2026-06-16 21:13:59','2026-06-16 21:13:59'),
(9,1,'Trip to Jawa Barat',3,2280000,'DRAFT','2026-06-16 21:14:32','2026-06-16 21:14:32'),
(10,1,'Trip to Bali',1,2300000,'DRAFT','2026-06-16 21:18:44','2026-06-16 21:18:44'),
(11,1,'Trip to Jawa Barat',1,780000,'DRAFT','2026-06-16 21:19:59','2026-06-16 21:19:59');
/*!40000 ALTER TABLE `itinerary_total` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `provinsi`
--

DROP TABLE IF EXISTS `provinsi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `provinsi` (
  `provinsi_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `kota` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`provinsi_id`),
  UNIQUE KEY `ux_provinsi_nama` (`nama`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provinsi`
--

LOCK TABLES `provinsi` WRITE;
/*!40000 ALTER TABLE `provinsi` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `provinsi` VALUES
(1,'Bali','Provinsi wisata populer di Indonesia','2026-06-14 13:59:09',NULL),
(2,'Jawa Barat','Provinsi di Pulau Jawa','2026-06-14 13:59:09',NULL);
/*!40000 ALTER TABLE `provinsi` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transport` (
  `transport_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `provinsi_id` int(10) unsigned NOT NULL,
  `jenis` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `jadwal` varchar(255) DEFAULT NULL,
  `pajak` double DEFAULT NULL,
  PRIMARY KEY (`transport_id`),
  KEY `idx_transport_provinsi_id` (`provinsi_id`),
  CONSTRAINT `fk_transport_provinsi` FOREIGN KEY (`provinsi_id`) REFERENCES `provinsi` (`provinsi_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `transport` VALUES
(1,1,'Pesawat','Garuda Indonesia',1500000,'2026-06-14 13:59:09',NULL,NULL),
(2,1,'Mobil Sewa','Bali Rent Car',300000,'2026-06-14 13:59:09',NULL,NULL),
(3,2,'Kereta','KAI',250000,'2026-06-14 13:59:09',NULL,NULL),
(4,1,'Flight','Lion Air',1500000,'2026-06-16 10:09:33','08:00 - 11:00',NULL);
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `traveler`
--

DROP TABLE IF EXISTS `traveler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `traveler` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `preference` varchar(255) DEFAULT NULL,
  `budget` double NOT NULL,
  `account_type` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_traveler_user_id` (`user_id`),
  CONSTRAINT `fk_traveler_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traveler`
--

LOCK TABLES `traveler` WRITE;
/*!40000 ALTER TABLE `traveler` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `traveler` VALUES
(1,2,'Pantai dan wisata alam',5000000,'PREMIUM','2026-06-14 13:59:09'),
(2,3,NULL,0,'FREE','2026-06-16 09:54:15');
/*!40000 ALTER TABLE `traveler` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `user` VALUES
(1,'Admin Utama','admin@gmail.com','admin123','2026-06-14 13:59:09','2026-06-14 13:59:09'),
(2,'Akbar Rizqullah','akbar@gmail.com','akbar123','2026-06-14 13:59:09','2026-06-14 13:59:09'),
(3,'User testing','usertest@gmail.com','$2a$10$cZ.2wxs8ljDNQzUy/OrvkeL9IYqAogxiFTxLKnDURREKHZCEAfoyW','2026-06-16 09:54:15','2026-06-16 09:54:15');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
commit;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2026-06-16 23:35:03
