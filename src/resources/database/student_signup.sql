CREATE DATABASE  IF NOT EXISTS `student_signup` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `student_signup`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: student_signup
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_serial` int NOT NULL AUTO_INCREMENT,
  `course_code` varchar(45) NOT NULL,
  `course_name` varchar(45) DEFAULT NULL,
  `course_section` varchar(45) NOT NULL,
  `course_section_faculty_ID` varchar(45) NOT NULL,
  PRIMARY KEY (`course_serial`,`course_code`,`course_section`,`course_section_faculty_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (5,'101','ENG','A','9'),(6,'102','ICS','D','10'),(8,'103','DSA','E','12');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` varchar(3) NOT NULL,
  `department_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_table`
--

DROP TABLE IF EXISTS `faculty_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty_table` (
  `faculty_serial_id` int NOT NULL AUTO_INCREMENT,
  `faculty_email` varchar(100) NOT NULL,
  `faculty_name` varchar(100) DEFAULT NULL,
  `faculty_password` varchar(100) DEFAULT NULL,
  `google_meet_link` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`faculty_serial_id`,`faculty_email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_table`
--

LOCK TABLES `faculty_table` WRITE;
/*!40000 ALTER TABLE `faculty_table` DISABLE KEYS */;
INSERT INTO `faculty_table` VALUES (6,'fahim@gmail.com','Fahim','4595',NULL),(7,'ag3ewqt','asd','asd43',NULL),(9,'iq@bscse.faculty.uiu.ac.bd','Ishtiaq','1111',NULL),(10,'iq@bscse.faculty.uiu.ac.bd','Ishtiaq','1111',NULL),(12,'iq@bscse,faculty.uiu.ac.bd','Ishtiaq','1111',NULL);
/*!40000 ALTER TABLE `faculty_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lms_data`
--

DROP TABLE IF EXISTS `lms_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lms_data` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(100) DEFAULT NULL,
  `course_section` varchar(3) NOT NULL,
  `course_content_date` datetime DEFAULT NULL,
  `course_content_data` varchar(3000) DEFAULT NULL,
  `course_send_notification` enum('yes','no') DEFAULT NULL,
  `course_notes` varchar(2000) DEFAULT NULL,
  `course_faculty` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`course_id`,`course_section`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lms_data`
--

LOCK TABLES `lms_data` WRITE;
/*!40000 ALTER TABLE `lms_data` DISABLE KEYS */;
INSERT INTO `lms_data` VALUES (109,'BN','B','2023-05-03 09:14:53','dfsdf','yes',NULL,'Ishtiaq'),(110,'ICS','A','2023-05-17 00:00:00','asdasd','yes','asdasd',NULL),(112,'ICS','A','2023-05-25 00:00:00','asdasd','yes','asdasd',NULL),(113,'BN','B','2023-05-17 00:00:00','asdasd','yes','h45tgewe',NULL),(114,'ICS','A','2023-05-12 00:00:00','asdasd','yes','asdads',NULL),(116,'BN','B','2023-05-17 00:00:00','asdasd','yes','asdasd',NULL),(117,'ICS','A','2023-05-24 00:00:00','asdasd','yes','asdafwaqdasw',NULL),(118,'BN','B','2023-05-16 00:00:00','asdasd','yes','asdasd',NULL),(119,'ICS','A','2023-05-25 00:00:00','asdasd','yes','asdasd',NULL),(120,'BN','B','2024-05-16 00:00:00','asdasd','yes','asdasd',NULL),(121,'BN','B','2023-05-17 00:00:00','dfhgdfgd656561','yes','',NULL),(122,'ICS','A','2023-05-22 00:00:00','more','yes','asdasd',NULL),(123,'BN','B','2023-05-24 00:00:00','more and more','yes','asdasd',NULL);
/*!40000 ALTER TABLE `lms_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_signup_table`
--

DROP TABLE IF EXISTS `student_signup_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_signup_table` (
  `student_email` varchar(45) DEFAULT NULL,
  `student_name` varchar(45) DEFAULT NULL,
  `student_id` varchar(45) NOT NULL,
  `student_password` varchar(45) DEFAULT NULL,
  `student_status` tinyint DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_signup_table`
--

LOCK TABLES `student_signup_table` WRITE;
/*!40000 ALTER TABLE `student_signup_table` DISABLE KEYS */;
INSERT INTO `student_signup_table` VALUES ('asdas','fahim','011201142','12213',0),('fahimbin@gmail.com','Fahim','011201143','asdasd2wr',0),('fa@gmail.com','watgaga','011201145','asdasfas',1),('qawfa2423','asdas','011201148','t346',0),('fahim23@gmail.com','fahim','011201158','a234',1),('235423','asdetg','011201160','sadasd',1),('asfaa','asdas','011201162','asdasd',0),('atasefa','asdasd','011201190','asdfasd',1),('new@gmail.com','New Student2','011201194','12as',0);
/*!40000 ALTER TABLE `student_signup_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-03 12:46:02
