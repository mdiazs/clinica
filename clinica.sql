-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: clinica
-- ------------------------------------------------------
-- Server version	5.5.24-log

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
-- Table structure for table `auditorias`
--

DROP TABLE IF EXISTS `auditorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditorias` (
  `id_auditoria` int(9) NOT NULL AUTO_INCREMENT,
  `id_user` int(2) unsigned NOT NULL,
  `accion` varchar(30) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `fecha` datetime NOT NULL,
  PRIMARY KEY (`id_auditoria`),
  UNIQUE KEY `id_auditoria_UNIQUE` (`id_auditoria`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditorias`
--

LOCK TABLES `auditorias` WRITE;
/*!40000 ALTER TABLE `auditorias` DISABLE KEYS */;
INSERT INTO `auditorias` VALUES (1,1,'Inicio de Sesión','Admin ha accedido a la aplicación','2018-05-31 00:00:00'),(3,1,'Crear cliente','Creación del cliente con DNI 63784736L','2018-05-31 02:00:00'),(4,1,'Crear cliente','Creación del cliente con DNI 15698672C','2018-05-31 20:06:00'),(5,1,'Crear cliente','Creación del cliente con DNI 12345432S','2018-05-31 20:08:00'),(6,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 16:16:48'),(7,1,'Crear cliente','Creación del cliente con DNI 12345675D','2018-06-01 16:18:02'),(8,1,'Eliminar cliente','Eliminación del cliente con DNI 12345432S','2018-06-01 16:18:02'),(9,1,'Eliminar cliente','Eliminación del cliente con DNI 12345675D','2018-06-01 16:18:02'),(10,1,'Editar cliente','Edición del cliente con DNI 63784736L','2018-06-01 16:18:02'),(11,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 16:25:10'),(12,1,'Crear cliente','Creación del cliente con DNI 34543454X','2018-06-01 16:26:09'),(13,1,'Editar cliente','Edición del cliente con DNI 34543454X','2018-06-01 16:26:55'),(14,1,'Eliminar cliente','Eliminación del cliente con DNI 34543454X','2018-06-01 16:27:06'),(15,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 16:40:32'),(16,1,'Crear complemento','Creación del complemento a','2018-06-01 16:40:58'),(17,1,'Editar complemento','Edición del complemento ae','2018-06-01 16:41:05'),(18,1,'Eliminar complemento','Eliminación del complemento ae','2018-06-01 16:41:10'),(19,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 16:55:16'),(20,1,'Crear tratamiento','Creación del tratamiento aaa','2018-06-01 16:55:46'),(21,1,'Editar tratamiento','Edición del tratamiento aaa','2018-06-01 16:55:53'),(22,1,'Eliminar tratamiento','Eliminación del tratamiento aaa','2018-06-01 16:55:58'),(23,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 17:07:37'),(24,1,'Crear lesión','Creación de la lesión a','2018-06-01 17:09:02'),(25,1,'Editar lesión','Edición de la lesión att','2018-06-01 17:09:19'),(26,1,'Eliminar lesión','Eliminación de la lesión att','2018-06-01 17:09:29'),(27,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 17:41:50'),(28,1,'Atender cita','Atención de la cita con id 103','2018-06-01 17:43:07'),(29,1,'Generar PDF','Generación del PDF de la cita con id 103','2018-06-01 17:43:13'),(30,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 17:48:17'),(31,1,'Crear cita','Creación de la cita con id 0','2018-06-01 17:48:40'),(32,1,'Editar cita','Edición de la cita con id 104','2018-06-01 17:48:54'),(33,1,'Eliminar cita','Eliminación de la cita con id 104','2018-06-01 17:49:17'),(34,1,'Inicio de Sesión','admin ha iniciado sesión','2018-06-01 17:51:28'),(35,1,'Crear cita','Creación de la cita con id 105','2018-06-01 17:51:52'),(36,1,'Editar cita','Edición de la cita con id 105','2018-06-01 17:52:47'),(37,1,'Eliminar cita','Eliminación de la cita con id 105','2018-06-01 17:52:55'),(38,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 18:44:28'),(39,1,'Crear cliente','Creación del cliente con DNI: 12345678C','2018-06-01 18:44:55'),(40,1,'Editar Cliente','Edición del cliente con DNI: 12345678C','2018-06-01 18:45:13'),(41,1,'Eliminar Cliente','Eliminación del cliente con DNI: 12345678C','2018-06-01 18:45:24'),(42,1,'Crear cita','Creación de la cita con id: 106','2018-06-01 18:45:54'),(43,1,'Editar Cita','Edición de la cita con id: 106','2018-06-01 18:46:09'),(44,1,'Editar Cita','Edición de la cita con id: 106','2018-06-01 18:46:44'),(45,1,'Editar Cita','Edición de la cita con id: 106','2018-06-01 18:50:20'),(46,1,'Eliminar Cita','Eliminación de la cita con id: 106','2018-06-01 18:51:10'),(47,1,'Atender Cita','Atención de la cita con id: 101','2018-06-01 18:51:32'),(48,1,'Atender Cita','Atención de la cita con id: 101','2018-06-01 18:52:19'),(49,1,'Generar PDF','Generación del PDF de la cita con id: 101','2018-06-01 18:52:32'),(50,1,'Atender Cita','Atención de la cita con id: 100','2018-06-01 18:53:47'),(51,1,'Crear cita','Creación de la cita con id: 107','2018-06-01 18:54:35'),(52,1,'Atender Cita','Atención de la cita con id: 107','2018-06-01 18:54:47'),(53,1,'Crear lesión','Creación de la lesión: a','2018-06-01 18:55:25'),(54,1,'Editar Lesión','Edición de la lesión: a','2018-06-01 18:55:35'),(55,1,'Eliminar Lesión','Eliminación de la lesión: a','2018-06-01 18:55:41'),(56,1,'Crear tratamiento','Creación del tratamiento: a','2018-06-01 18:56:06'),(57,1,'Editar Tratamiento','Edición del tratamiento: a','2018-06-01 18:56:13'),(58,1,'Eliminar Tratamiento','Eliminación del tratamiento:a','2018-06-01 18:56:18'),(59,1,'Crear complemento','Creación del complemento: a','2018-06-01 18:56:41'),(60,1,'Editar Complemento','Edición del complemento: a','2018-06-01 18:56:50'),(61,1,'Eliminar Complemento','Eliminación del complemento: a','2018-06-01 18:56:54'),(62,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 19:17:43'),(63,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 19:39:19'),(64,1,'Editar Cita','Edición de la cita con id: 102','2018-06-01 19:42:22'),(65,1,'Editar Cita','Edición de la cita con id: 102','2018-06-01 19:43:07'),(66,1,'Crear cita','Creación de la cita con id: 108','2018-06-01 19:43:59'),(67,1,'Atender Cita','Atención de la cita con id: 108','2018-06-01 19:44:13'),(68,1,'Generar PDF','Generación del PDF de la cita con id: 108','2018-06-01 19:44:19'),(69,1,'Crear cita','Creación de la cita con id: 109','2018-06-01 19:46:35'),(70,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 19:55:54'),(71,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 20:03:42'),(72,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 20:11:48'),(73,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 20:44:10'),(74,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 20:48:50'),(75,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 21:13:11'),(76,1,'Crear cliente','Creación del cliente con DNI: 12345678X','2018-06-01 21:13:48'),(77,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 21:17:37'),(78,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-01 21:18:51'),(79,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 08:47:59'),(80,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 08:56:27'),(81,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 08:59:08'),(82,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 09:11:18'),(83,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 09:23:45'),(84,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 09:26:52'),(85,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 09:58:11'),(86,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 11:05:25'),(87,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-02 11:17:24'),(88,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-03 11:45:42'),(89,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-03 13:01:53'),(90,1,'Crear lesión','Creación de la lesión: a','2018-06-03 13:02:49'),(91,1,'Eliminar Lesión','Eliminación de la lesión: a','2018-06-03 13:02:57'),(92,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-03 13:05:37'),(93,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-03 13:51:03'),(94,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-03 13:51:18'),(95,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-09 11:08:15'),(96,1,'Crear cliente','Creación del cliente con DNI: 40987460J','2018-06-09 11:41:06'),(97,1,'Eliminar Cliente','Eliminación del cliente con DNI: 48987460J','2018-06-09 11:47:14'),(98,1,'Eliminar Cliente','Eliminación del cliente con DNI: 12345678X','2018-06-09 12:15:53'),(99,1,'Crear cita','Creación de la cita con id: 110','2018-06-09 12:20:29'),(100,1,'Crear cita','Creación de la cita con id: 111','2018-06-09 12:20:49'),(101,1,'Crear cita','Creación de la cita con id: 112','2018-06-09 12:21:29'),(102,1,'Eliminar Cita','Eliminación de la cita con id: 112','2018-06-09 12:21:36'),(103,1,'Crear cita','Creación de la cita con id: 113','2018-06-09 12:22:14'),(104,1,'Atender Cita','Atención de la cita con id: 110','2018-06-09 12:22:49'),(105,1,'Atender Cita','Atención de la cita con id: 111','2018-06-09 12:23:18'),(106,1,'Atender Cita','Atención de la cita con id: 113','2018-06-09 12:23:58'),(107,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-09 16:13:49'),(108,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-09 16:48:54'),(109,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 11:38:58'),(110,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 12:06:59'),(111,1,'Crear cita','Creación de la cita con id: 114','2018-06-10 12:42:53'),(112,1,'Crear cita','Creación de la cita con id: 115','2018-06-10 12:43:18'),(113,1,'Crear cita','Creación de la cita con id: 116','2018-06-10 12:43:49'),(114,1,'Crear cita','Creación de la cita con id: 117','2018-06-10 12:44:11'),(115,1,'Editar Cita','Edición de la cita con id: 116','2018-06-10 12:46:10'),(116,1,'Atender Cita','Atención de la cita con id: 115','2018-06-10 14:00:32'),(117,1,'Atender Cita','Atención de la cita con id: 114','2018-06-10 14:04:20'),(118,1,'Generar PDF','Generación del PDF de la cita con id: 114','2018-06-10 14:04:28'),(119,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 15:51:57'),(120,1,'Crear cita','Creación de la cita con id: 118','2018-06-10 15:52:36'),(121,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 16:18:06'),(122,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 16:21:59'),(123,1,'Atender Cita','Atención de la cita con id: 118','2018-06-10 16:28:20'),(124,1,'Crear cita','Creación de la cita con id: 119','2018-06-10 16:28:52'),(125,1,'Editar Cita','Edición de la cita con id: 116','2018-06-10 16:29:22'),(126,1,'Atender Cita','Atención de la cita con id: 119','2018-06-10 16:30:24'),(127,1,'Crear cita','Creación de la cita con id: 120','2018-06-10 16:34:30'),(128,1,'Atender Cita','Atención de la cita con id: 120','2018-06-10 16:34:59'),(129,1,'Generar PDF','Generación del PDF de la cita con id: 120','2018-06-10 16:35:20'),(130,1,'Crear cita','Creación de la cita con id: 121','2018-06-10 16:36:06'),(131,1,'Editar Cita','Edición de la cita con id: 117','2018-06-10 16:36:20'),(132,1,'Atender Cita','Atención de la cita con id: 121','2018-06-10 16:36:58'),(133,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 17:01:53'),(134,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 17:02:03'),(135,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 17:05:00'),(136,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-10 17:39:33'),(137,1,'Crear cita','Creación de la cita con id: 122','2018-06-10 17:45:16'),(138,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-15 16:32:48'),(139,1,'Crear cita','Creación de la cita con id: 123','2018-06-15 16:36:54'),(140,1,'Crear cita','Creación de la cita con id: 124','2018-06-15 16:37:24'),(141,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 19:18:51'),(142,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 20:50:30'),(143,1,'Editar Cliente','Edición del cliente con DNI: 98765432G','2018-06-20 20:52:05'),(144,1,'Eliminar Cliente','Eliminación del cliente con DNI: 63784736L','2018-06-20 20:52:46'),(145,1,'Crear complemento','Creación del complemento: a','2018-06-20 20:55:40'),(146,1,'Editar Complemento','Edición del complemento: a','2018-06-20 20:55:51'),(147,1,'Eliminar Complemento','Eliminación del complemento: a','2018-06-20 20:56:00'),(148,1,'Editar Tratamiento','Edición del tratamiento: Acupuntura','2018-06-20 20:57:05'),(149,1,'Crear tratamiento','Creación del tratamiento: a','2018-06-20 20:57:32'),(150,1,'Eliminar Tratamiento','Eliminación del tratamiento: a','2018-06-20 20:57:46'),(151,1,'Crear lesión','Creación de la lesión: a','2018-06-20 20:58:26'),(152,1,'Editar Lesión','Edición de la lesión: a','2018-06-20 20:58:39'),(153,1,'Eliminar Lesión','Eliminación de la lesión: a','2018-06-20 20:58:55'),(154,1,'Crear cita','Creación de la cita con id: 125','2018-06-20 21:00:03'),(155,1,'Crear cita','Creación de la cita con id: 126','2018-06-20 21:00:39'),(156,1,'Crear cita','Creación de la cita con id: 127','2018-06-20 21:01:30'),(157,1,'Crear cita','Creación de la cita con id: 128','2018-06-20 21:02:14'),(158,1,'Crear cita','Creación de la cita con id: 129','2018-06-20 21:03:21'),(159,1,'Editar Cita','Edición de la cita con id: 125','2018-06-20 21:06:22'),(160,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 21:59:39'),(161,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 22:10:39'),(162,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 22:15:18'),(163,1,'Atender Cita','Atención de la cita con id: 130','2018-06-20 22:20:48'),(164,1,'Generar PDF','Generación del PDF de la cita con id: 130','2018-06-20 22:20:55'),(165,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 22:30:46'),(166,1,'Editar Tratamiento','Edición del tratamiento: Acupuntura','2018-06-20 22:35:19'),(167,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 22:50:28'),(168,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 22:59:13'),(169,1,'Editar Cliente','Edición del cliente con DNI: 12345678D','2018-06-20 23:02:34'),(170,1,'Editar Cliente','Edición del cliente con DNI: 12345678D','2018-06-20 23:03:10'),(171,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-20 23:11:37'),(172,1,'Editar Cliente','Edición del cliente con DNI: 12345678D','2018-06-20 23:12:17'),(173,1,'Editar Cliente','Edición del cliente con DNI: 12345678D','2018-06-20 23:12:36'),(174,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-21 09:00:11'),(175,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-21 09:24:01'),(176,1,'Crear cliente','Creación del cliente con DNI: 12345654G','2018-06-21 09:25:50'),(177,1,'Editar Cliente','Edición del cliente con DNI: 12345654G','2018-06-21 09:26:17'),(178,1,'Eliminar Cliente','Eliminación del cliente con DNI: 12345654G','2018-06-21 09:26:38'),(179,1,'Crear cita','Creación de la cita con id: 131','2018-06-21 09:32:40'),(180,1,'Atender Cita','Atención de la cita con id: 127','2018-06-21 09:37:42'),(181,1,'Generar PDF','Generación del PDF de la cita con id: 127','2018-06-21 09:38:55'),(182,1,'Atender Cita','Atención de la cita con id: 128','2018-06-21 09:41:22'),(183,1,'Editar Cita','Edición de la cita con id: 131','2018-06-21 09:43:01'),(184,1,'Crear lesión','Creación de la lesión: Dolor muscular','2018-06-21 09:44:31'),(185,1,'Editar Lesión','Edición de la lesión: Dolor muscular','2018-06-21 09:44:54'),(186,1,'Eliminar Lesión','Eliminación de la lesión: Dolor muscular','2018-06-21 09:45:46'),(187,1,'Crear tratamiento','Creación del tratamiento: Fisioterapia','2018-06-21 09:53:52'),(188,1,'Editar Tratamiento','Edición del tratamiento: Fisioterapia','2018-06-21 09:54:25'),(189,1,'Crear cita','Creación de la cita con id: 132','2018-06-21 09:56:29'),(190,1,'Editar Cita','Edición de la cita con id: 131','2018-06-21 10:14:29'),(191,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-25 19:17:51'),(192,1,'Editar Cita','Edición de la cita con id: 131','2018-06-25 19:18:48'),(193,1,'Editar Cita','Edición de la cita con id: 131','2018-06-25 19:19:09'),(194,1,'Editar Cita','Edición de la cita con id: 131','2018-06-25 19:19:21'),(195,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-25 22:02:24'),(196,1,'Iniciar sesión','admin ha iniciado sesión','2018-06-25 22:13:03'),(197,1,'Crear cliente','Creación del cliente con DNI: 34543452Z','2018-06-25 22:15:09'),(198,1,'Editar Cliente','Edición del cliente con DNI: 34543452Z','2018-06-25 22:17:10'),(199,1,'Editar Cliente','Edición del cliente con DNI: 34543452Z','2018-06-25 22:17:23'),(200,1,'Eliminar Cliente','Eliminación del cliente con DNI: 34543452Z','2018-06-25 22:19:03'),(201,1,'Crear tratamiento','Creación del tratamiento: pRUEBA','2018-06-25 22:20:25'),(202,1,'Editar Tratamiento','Edición del tratamiento: pRUEBA','2018-06-25 22:21:07'),(203,1,'Eliminar Tratamiento','Eliminación del tratamiento: pRUEBA','2018-06-25 22:21:17'),(204,1,'Crear complemento','Creación del complemento: a','2018-06-25 22:21:57'),(205,1,'Editar Complemento','Edición del complemento: s','2018-06-25 22:22:41'),(206,1,'Eliminar Complemento','Eliminación del complemento: s','2018-06-25 22:22:49'),(207,1,'Crear lesión','Creación de la lesión: w','2018-06-25 22:25:21'),(208,1,'Editar Lesión','Edición de la lesión: w','2018-06-25 22:25:42'),(209,1,'Eliminar Lesión','Eliminación de la lesión: w','2018-06-25 22:27:27'),(210,1,'Atender Cita','Atención de la cita con id: 125','2018-06-25 22:32:36'),(211,1,'Generar PDF','Generación del PDF de la cita con id: 125','2018-06-25 22:32:42'),(212,1,'Editar Cita','Edición de la cita con id: 131','2018-06-25 22:35:47'),(213,1,'Crear cita','Creación de la cita con id: 133','2018-06-25 22:36:53'),(214,1,'Crear cita','Creación de la cita con id: 134','2018-06-25 22:37:20');
/*!40000 ALTER TABLE `auditorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cita_complementos`
--

DROP TABLE IF EXISTS `cita_complementos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cita_complementos` (
  `id_cita_complemento` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_cita` int(10) unsigned NOT NULL,
  `id_complemento` int(4) unsigned NOT NULL,
  PRIMARY KEY (`id_cita_complemento`),
  KEY `cita_complementos_id_cita_fk_idx` (`id_cita`),
  KEY `cita_complementos_id_complemento_fk_idx` (`id_complemento`),
  CONSTRAINT `cita_complementos_id_cita_fk` FOREIGN KEY (`id_cita`) REFERENCES `citas` (`id_cita`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cita_complementos_id_complemento_fk` FOREIGN KEY (`id_complemento`) REFERENCES `complementos` (`id_complemento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita_complementos`
--

LOCK TABLES `cita_complementos` WRITE;
/*!40000 ALTER TABLE `cita_complementos` DISABLE KEYS */;
INSERT INTO `cita_complementos` VALUES (41,110,1),(42,111,2),(43,111,1),(44,113,1),(45,115,1),(46,115,2),(47,114,1),(48,119,1),(49,120,1),(50,121,2),(51,130,2),(52,127,2),(53,127,1),(54,128,1),(55,125,1);
/*!40000 ALTER TABLE `cita_complementos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cita_modulo`
--

DROP TABLE IF EXISTS `cita_modulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cita_modulo` (
  `id_modulo` int(2) unsigned NOT NULL,
  `fecha` date NOT NULL,
  `id_cita` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_modulo`,`fecha`),
  KEY `cita_modulo_id_cita_fk_idx` (`id_cita`),
  KEY `cita_modulo_id_modulo_fk_idx` (`id_modulo`),
  CONSTRAINT `cita_modulo_id_cita_fk` FOREIGN KEY (`id_cita`) REFERENCES `citas` (`id_cita`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cita_modulo_id_modulo_fk` FOREIGN KEY (`id_modulo`) REFERENCES `modulos` (`id_modulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita_modulo`
--

LOCK TABLES `cita_modulo` WRITE;
/*!40000 ALTER TABLE `cita_modulo` DISABLE KEYS */;
INSERT INTO `cita_modulo` VALUES (11,'2018-06-09',110),(12,'2018-06-09',110),(13,'2018-06-09',110),(51,'2018-06-09',111),(52,'2018-06-09',111),(53,'2018-06-09',111),(54,'2018-06-09',111),(63,'2018-06-09',113),(51,'2018-06-10',114),(52,'2018-06-10',114),(53,'2018-06-10',114),(12,'2018-06-10',115),(13,'2018-06-10',115),(14,'2018-06-10',115),(15,'2018-06-10',115),(9,'2018-06-14',116),(10,'2018-06-14',116),(11,'2018-06-14',116),(63,'2018-06-11',117),(64,'2018-06-11',117),(65,'2018-06-11',117),(59,'2018-06-10',118),(60,'2018-06-10',118),(61,'2018-06-10',118),(64,'2018-06-10',119),(65,'2018-06-10',119),(66,'2018-06-10',119),(54,'2018-06-10',120),(55,'2018-06-10',120),(56,'2018-06-10',120),(58,'2018-06-10',121),(63,'2018-06-10',122),(51,'2018-06-15',123),(52,'2018-06-15',123),(53,'2018-06-15',123),(57,'2018-06-15',124),(51,'2018-06-25',125),(52,'2018-06-25',125),(53,'2018-06-25',125),(9,'2018-06-21',126),(10,'2018-06-21',126),(1,'2018-06-21',127),(2,'2018-06-21',127),(3,'2018-06-21',127),(4,'2018-06-21',127),(63,'2018-06-21',128),(58,'2018-06-21',129),(59,'2018-06-21',129),(58,'2018-06-20',130),(59,'2018-06-20',130),(9,'2018-07-03',131),(10,'2018-07-03',131),(11,'2018-07-03',131),(14,'2018-06-21',132),(15,'2018-06-21',132),(16,'2018-06-21',132),(63,'2018-07-03',133),(64,'2018-07-03',133),(65,'2018-07-03',133),(51,'2018-07-03',134),(52,'2018-07-03',134),(53,'2018-07-03',134),(54,'2018-07-03',134);
/*!40000 ALTER TABLE `cita_modulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citas`
--

DROP TABLE IF EXISTS `citas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `citas` (
  `id_cita` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int(10) unsigned NOT NULL,
  `id_lesion` int(3) unsigned NOT NULL,
  `id_zona` int(2) unsigned DEFAULT NULL,
  `id_musculo` int(3) unsigned DEFAULT NULL,
  `grado` char(1) DEFAULT NULL,
  `estado` char(1) DEFAULT NULL,
  `observaciones` varchar(200) DEFAULT NULL,
  `id_tratamiento` int(4) unsigned NOT NULL,
  `atendida` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id_cita`),
  UNIQUE KEY `id_cita_UNIQUE` (`id_cita`),
  KEY `fk_citas_id_cliente_idx` (`id_cliente`),
  KEY `fk_citas_id_lesion_idx` (`id_lesion`),
  KEY `fk_citas_id_tratamiento_idx` (`id_tratamiento`),
  KEY `fk_citas_id_zona_idx` (`id_zona`),
  KEY `fk_citas_id_musculo_idx` (`id_musculo`),
  CONSTRAINT `fk_citas_id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_citas_id_lesion` FOREIGN KEY (`id_lesion`) REFERENCES `lesiones` (`id_lesion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_citas_id_musculo` FOREIGN KEY (`id_musculo`) REFERENCES `musculos` (`id_musculo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_citas_id_tratamiento` FOREIGN KEY (`id_tratamiento`) REFERENCES `tratamientos` (`id_tratamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_citas_id_zona` FOREIGN KEY (`id_zona`) REFERENCES `zonas` (`id_zona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citas`
--

LOCK TABLES `citas` WRITE;
/*!40000 ALTER TABLE `citas` DISABLE KEYS */;
INSERT INTO `citas` VALUES (110,1,2,4,16,'1','1','Zona inflamada',1,1),(111,1,4,2,4,'2','1','Zona inflamada',2,1),(113,1,3,5,3,'2','1','',4,1),(114,1,2,5,2,'2','0','Inflamación de la zona',1,1),(115,1,4,4,16,'1','0','aaaaaaaaaaaaaaaaaa',2,1),(116,7,3,NULL,NULL,NULL,NULL,NULL,1,0),(117,1,2,NULL,NULL,NULL,NULL,NULL,1,0),(118,1,2,4,16,'1','0','',1,1),(119,1,3,3,15,'3','0','ddddddddddddddd',1,1),(120,1,2,5,8,'1','0','xxxxxxxxxxx',1,1),(121,1,5,5,3,'2','0','sssssssssss',4,1),(122,1,1,NULL,NULL,NULL,NULL,NULL,4,0),(123,1,2,NULL,NULL,NULL,NULL,NULL,1,0),(124,1,1,NULL,NULL,NULL,NULL,NULL,4,0),(125,1,3,3,1,'2','0','aaaaaaaaaaaaaaaaaaaaaaaa',1,1),(126,7,6,NULL,NULL,NULL,NULL,NULL,3,0),(127,17,4,5,9,'1','0','Zona inflamada',2,1),(128,3,3,1,6,'1','0','eeeee',4,1),(129,2,6,NULL,NULL,NULL,NULL,NULL,3,0),(130,1,6,2,4,'1','0','aaaaaaaaa',3,1),(131,7,2,NULL,NULL,NULL,NULL,NULL,1,0),(132,1,3,NULL,NULL,NULL,NULL,NULL,1,0),(133,1,5,NULL,NULL,NULL,NULL,NULL,1,0),(134,1,4,NULL,NULL,NULL,NULL,NULL,2,0);
/*!40000 ALTER TABLE `citas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `id_cliente` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `localidad` varchar(30) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `id_cliente_UNIQUE` (`id_cliente`),
  UNIQUE KEY `dni_UNIQUE` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'12345678H','Antonio','Postigo Martínez','Arahal','Jorge Guillén, 8','673456789'),(2,'98765432G','Pastora','Arraz López','Mairena del Alcor','Jorge Guillén, 8','612345659'),(3,'87463297U','Clara','Postigo Arraz','Mairena del Alcor','Jorge Guillén, 8','638292817'),(5,'44444444D','Nuria','Postigo Arraz','Arahal','Jorge Guillén, 8','615658421'),(7,'12345678D','Pilar','Sánchez Portillo','Marchena','Cid, 19','623456706'),(9,'98989898M','Cristina','Postigo Arraz','Mairena del Alcor','Avenida de Lepanto, 30','647859237'),(17,'40987460J','Marco','Díaz Sánchez','Marchena','Cid, 19','615658438');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complementos`
--

DROP TABLE IF EXISTS `complementos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complementos` (
  `id_complemento` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_complemento`),
  UNIQUE KEY `id_remedio_UNIQUE` (`id_complemento`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complementos`
--

LOCK TABLES `complementos` WRITE;
/*!40000 ALTER TABLE `complementos` DISABLE KEYS */;
INSERT INTO `complementos` VALUES (1,'Estiramientos','Ejercicio Físico','Alivio zona afectada'),(2,'Reflex','Aplicación','Reducción inflamación'),(3,'Romero','Ingesta','Reducción inflamación');
/*!40000 ALTER TABLE `complementos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleados` (
  `id_user` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(20) NOT NULL,
  `pass` varchar(8) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `user_UNIQUE` (`user`),
  UNIQUE KEY `id_user_UNIQUE` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesiones`
--

DROP TABLE IF EXISTS `lesiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesiones` (
  `id_lesion` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `tipo` tinyint(1) NOT NULL,
  `causas` varchar(100) DEFAULT NULL,
  `prevencion` varchar(100) DEFAULT NULL,
  `sintomas` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_lesion`),
  UNIQUE KEY `id_lesion_UNIQUE` (`id_lesion`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesiones`
--

LOCK TABLES `lesiones` WRITE;
/*!40000 ALTER TABLE `lesiones` DISABLE KEYS */;
INSERT INTO `lesiones` VALUES (1,'Indefinida','Indefinida',0,'Indefinidas','Indefinidas','Indefinidos'),(2,'Agujetas','Micro - roturas de fibras musculares',0,'Actividades físicas exigentes o ejercicios diferentes a las habituales ','Calentamiento adecuado que incluya estiramientos progresivos previos y posteriores a la actividad','Reacción inflamatoria de la zona muscular afectada, dolor muscular, pesadez y pinchazos'),(3,'Calambres','Contracción muscular involuntaria',0,'Calentamiento inadecuado o deficiente, fatiga o deshidratación','Hidratación adecuada,  ingesta de bebidas isotónicas o  dieta con un buen aporte vitamínico y minera','Dolor muscular intenso'),(4,'Contractura','Contracción mantenida de un grupo de fibras musculares',0,'Sensación de \"bola o nudo\" en la zona y, al tacto de que se \"monta\"  el músculo afectado','Calentamiento adecuado al inicio del ejercicio.','Fatiga mecánica o  reiteración de gestos o posturas'),(5,'Distensión','Estiramiento superlativo del músculo sin llegar a la rotura',1,'Tensión excesiva de las fibras musculares','Estiramientos suaves antes y después del ejercicio y evitar realizar esfuerzos físicos excesivos en ','Cuadro inflamatorio que desemboca en una sensación de dolor'),(6,'Contusión','Aplastamiento físico de los tejidos',1,'Choque o compresión de la zona con un agente externo','Precaución a la hora de realizar actividades que puedan implicar golpes o atrapamientos','Dolor en la zona afectada con manifestación de derrame y/o hematoma');
/*!40000 ALTER TABLE `lesiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesiones_complementos`
--

DROP TABLE IF EXISTS `lesiones_complementos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesiones_complementos` (
  `id_lesion` int(3) unsigned NOT NULL,
  `id_complemento` int(4) unsigned NOT NULL,
  KEY `fk_complementos_idx` (`id_complemento`),
  KEY `fk_lesiones-lesiones_complementos_idx` (`id_lesion`),
  CONSTRAINT `fk_complementos-lesiones_complementos` FOREIGN KEY (`id_complemento`) REFERENCES `complementos` (`id_complemento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lesiones-lesiones_complementos` FOREIGN KEY (`id_lesion`) REFERENCES `lesiones` (`id_lesion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesiones_complementos`
--

LOCK TABLES `lesiones_complementos` WRITE;
/*!40000 ALTER TABLE `lesiones_complementos` DISABLE KEYS */;
INSERT INTO `lesiones_complementos` VALUES (2,1),(3,1),(4,1),(4,2),(4,3),(5,2),(6,2),(6,3);
/*!40000 ALTER TABLE `lesiones_complementos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesiones_tratamientos`
--

DROP TABLE IF EXISTS `lesiones_tratamientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesiones_tratamientos` (
  `id_lesion` int(3) unsigned NOT NULL,
  `id_tratamiento` int(2) unsigned NOT NULL,
  PRIMARY KEY (`id_lesion`,`id_tratamiento`),
  KEY `lesiones_tratamientos_tratamientos_idx` (`id_tratamiento`),
  CONSTRAINT `lesiones_tratamientos_lesiones` FOREIGN KEY (`id_lesion`) REFERENCES `lesiones` (`id_lesion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lesiones_tratamientos_tratamientos` FOREIGN KEY (`id_tratamiento`) REFERENCES `tratamientos` (`id_tratamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesiones_tratamientos`
--

LOCK TABLES `lesiones_tratamientos` WRITE;
/*!40000 ALTER TABLE `lesiones_tratamientos` DISABLE KEYS */;
INSERT INTO `lesiones_tratamientos` VALUES (2,1),(3,1),(4,1),(5,1),(4,2),(5,2),(6,3),(1,4);
/*!40000 ALTER TABLE `lesiones_tratamientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulos`
--

DROP TABLE IF EXISTS `modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modulos` (
  `id_modulo` int(2) unsigned NOT NULL,
  `tipo` tinyint(1) NOT NULL COMMENT 'Tipo 0: Horario Mañana\nTipo 1: Horario Tarde',
  `modulo` varchar(5) NOT NULL,
  PRIMARY KEY (`id_modulo`),
  UNIQUE KEY `modulo_UNIQUE` (`modulo`),
  UNIQUE KEY `id_modulo_UNIQUE` (`id_modulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulos`
--

LOCK TABLES `modulos` WRITE;
/*!40000 ALTER TABLE `modulos` DISABLE KEYS */;
INSERT INTO `modulos` VALUES (1,0,'10:00'),(2,0,'10:15'),(3,0,'10:30'),(4,0,'10:45'),(5,0,'11:00'),(6,0,'11:15'),(7,0,'11:30'),(8,0,'11:45'),(9,0,'12:00'),(10,0,'12:15'),(11,0,'12:30'),(12,0,'12:45'),(13,0,'13:00'),(14,0,'13:15'),(15,0,'13:30'),(16,0,'13:45'),(51,1,'17:00'),(52,1,'17:15'),(53,1,'17:30'),(54,1,'17:45'),(55,1,'18:00'),(56,1,'18:15'),(57,1,'18:30'),(58,1,'18:45'),(59,1,'19:00'),(60,1,'19:15'),(61,1,'19:30'),(62,1,'19:45'),(63,1,'20:00'),(64,1,'20:15'),(65,1,'20:30'),(66,1,'20:45');
/*!40000 ALTER TABLE `modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `musculos`
--

DROP TABLE IF EXISTS `musculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `musculos` (
  `id_musculo` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_zona` int(2) unsigned NOT NULL,
  `url_imagen` varchar(100) NOT NULL,
  PRIMARY KEY (`id_musculo`),
  UNIQUE KEY `id_musculo_UNIQUE` (`id_musculo`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `url_imagen_UNIQUE` (`url_imagen`),
  KEY `fk_musculos_id_zona_idx` (`id_zona`),
  CONSTRAINT `fk_musculos_id_zona` FOREIGN KEY (`id_zona`) REFERENCES `zonas` (`id_zona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `musculos`
--

LOCK TABLES `musculos` WRITE;
/*!40000 ALTER TABLE `musculos` DISABLE KEYS */;
INSERT INTO `musculos` VALUES (1,'Abdominales',3,'images/musculos/abdominal.jpg'),(2,'Tendón de Aquiles',5,'images/musculos/aquiles.jpg'),(3,'Cuadriceps',5,'images/musculos/cuadriceps.jpg'),(4,'Deltoides',2,'images/musculos/deltoide.jpg'),(5,'Dorsales',3,'images/musculos/dorsal.jpg'),(6,'Esternocleidomastoideo',1,'images/musculos/esternocleidomastoideo.jpg'),(7,'Biceps Femoral',5,'images/musculos/femoral.jpg'),(8,'Gemelos',5,'images/musculos/gemelos.jpg'),(9,'Glúteos',5,'images/musculos/gluteo.jpg'),(10,'Infraespinoso',2,'images/musculos/infraespinoso.jpg'),(11,'Oblicuo Abdominal',3,'images/musculos/oblicuo.jpg'),(12,'Pectoral',3,'images/musculos/pectoral.jpg'),(13,'Sartorio',5,'images/musculos/sartorio.jpg'),(14,'Sóleo',5,'images/musculos/soleo.jpg'),(15,'Trapecio',3,'images/musculos/trapecio.jpg'),(16,'Triceps',4,'images/musculos/triceps.jpg'),(17,'Biceps',4,'images/musculos/biceps.jpg');
/*!40000 ALTER TABLE `musculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratamientos`
--

DROP TABLE IF EXISTS `tratamientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tratamientos` (
  `id_tratamiento` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `duracion` int(3) NOT NULL,
  `precio` decimal(5,2) unsigned NOT NULL,
  `color` varchar(7) NOT NULL,
  PRIMARY KEY (`id_tratamiento`),
  UNIQUE KEY `id_tratamiento_UNIQUE` (`id_tratamiento`),
  UNIQUE KEY `tipo_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratamientos`
--

LOCK TABLES `tratamientos` WRITE;
/*!40000 ALTER TABLE `tratamientos` DISABLE KEYS */;
INSERT INTO `tratamientos` VALUES (1,'Masaje',45,25.00,'FF0000'),(2,'Acupuntura',60,30.00,'c751e8'),(3,'Osteopatía',30,15.00,'0000FF'),(4,'Exploración',15,10.00,'f2ff00'),(6,'Fisioterapia',30,0.03,'267b8a');
/*!40000 ALTER TABLE `tratamientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zonas`
--

DROP TABLE IF EXISTS `zonas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zonas` (
  `id_zona` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_zona`),
  UNIQUE KEY `id_zona_UNIQUE` (`id_zona`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zonas`
--

LOCK TABLES `zonas` WRITE;
/*!40000 ALTER TABLE `zonas` DISABLE KEYS */;
INSERT INTO `zonas` VALUES (4,'Brazos'),(1,'Cuello'),(2,'Hombros'),(5,'Piernas'),(3,'Tronco');
/*!40000 ALTER TABLE `zonas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-25 22:46:12
