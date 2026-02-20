-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: quizapp
-- ------------------------------------------------------
-- Server version	9.6.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'd4a6986a-04ee-11f1-bfb7-50ebf6436908:1-180';

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK35t4wyxqrevf09uwx9e9p6o75` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Preguntas sobre eventos históricos','Historia'),(2,'Preguntas sobre ciencias naturales y exactas','Ciencia'),(3,'Preguntas sobre lugares y territorios del mundo','Geografía'),(4,'Preguntas sobre deportes y atletas','Deportes'),(5,'Preguntas variadas de conocimiento general','Cultura General'),(6,'Preguntas sobre informática y tecnología','Tecnología'),(7,'Preguntas sobre arte, música y cultura','Arte'),(8,'Preguntas sobre cálculos y razonamiento matemático','Matemáticas');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta`
--

DROP TABLE IF EXISTS `pregunta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pregunta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enunciado` varchar(255) DEFAULT NULL,
  `tematica` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `categoria_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa62e8tsgbdtx4n3lqov0uc1uh` (`categoria_id`),
  CONSTRAINT `FKa62e8tsgbdtx4n3lqov0uc1uh` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta`
--

LOCK TABLES `pregunta` WRITE;
/*!40000 ALTER TABLE `pregunta` DISABLE KEYS */;
INSERT INTO `pregunta` VALUES (2,'España ganó el Mundial de Fútbol en 2010',NULL,NULL,4),(3,'París es la capital de Italia',NULL,NULL,3),(4,'Java es un lenguaje de programación orientado a objetos',NULL,NULL,6),(5,'La Gran Muralla China es visible desde el espacio',NULL,NULL,1),(6,'¿Cuál es la capital de Francia?',NULL,NULL,3),(7,'¿En qué año llegó el hombre a la Luna?',NULL,NULL,1),(8,'¿Cuántos jugadores tiene un equipo de fútbol en el campo?',NULL,NULL,4),(9,'¿Cuál es el lenguaje de marcado estándar para páginas web?',NULL,NULL,6),(10,'¿Quién pintó la Mona Lisa?',NULL,NULL,7),(11,'¿Cuál es el río más largo del mundo?',NULL,NULL,4),(12,'¿En qué continente está Egipto?',NULL,NULL,4),(13,'¿Cuál es el océano más grande?',NULL,NULL,4),(15,'El agua hierve a 100 grados centigrados',NULL,NULL,2),(16,'La Tierra es plana',NULL,NULL,5),(17,'El sol es una estrella',NULL,NULL,2),(18,'España tiene 50 provincias',NULL,NULL,1),(19,'El oxigeno es un metal',NULL,NULL,2),(20,'La Segunda Guerra Mundial termino en 1945',NULL,NULL,1),(21,'El corazon humano tiene 4 camaras',NULL,NULL,2),(22,'Madrid es la capital de Portugal',NULL,NULL,3),(23,'Los delfines son mamiferos',NULL,NULL,2),(24,'El ADN tiene forma de doble helice',NULL,NULL,2),(25,'Cual es la capital de Francia',NULL,NULL,3),(26,'Cuantos planetas tiene el sistema solar',NULL,NULL,2),(27,'En que año llego el hombre a la Luna',NULL,NULL,1),(28,'Quien pinto la Mona Lisa',NULL,NULL,7),(29,'Cuantos huesos tiene el cuerpo humano adulto',NULL,NULL,2),(30,'Cual es el oceano mas grande',NULL,NULL,3),(31,'Cuantos lados tiene un hexagono',NULL,NULL,8),(32,'Cual es el animal mas rapido del mundo',NULL,NULL,5),(33,'En que continente esta Egipto',NULL,NULL,3),(34,'Cual es el lenguaje de programacion mas usado',NULL,NULL,6),(36,'Cuales de estos son planetas del sistema solar',NULL,NULL,2),(37,'Cuales son paises de America del Sur',NULL,NULL,3),(38,'Cuales de estos son gases nobles',NULL,NULL,2),(39,'Cuales de estos son rios de España',NULL,NULL,3),(40,'Cuales de estos son lenguajes de programacion',NULL,NULL,6),(41,'¿Cuáles de estos son lenguajes de programación?',NULL,NULL,6),(42,'¿Cuáles de estos países pertenecen a América del Sur?',NULL,NULL,3),(43,'¿Cuáles de estos son planetas del sistema solar?',NULL,NULL,2),(44,'¿Cuáles de estos son mamíferos?',NULL,NULL,2),(45,'¿Cuáles de estos deportes se juegan con pelota?',NULL,NULL,4),(46,'¿Cuáles de estas son capitales europeas?',NULL,NULL,3),(47,'¿Cuáles de estos elementos son metales?',NULL,NULL,2),(48,'¿Cuáles de estas son operaciones matemáticas básicas?',NULL,NULL,8),(49,'¿Cuáles de estos son lenguajes de programación?',NULL,NULL,6),(50,'¿Cuáles de estos países pertenecen a América del Sur?',NULL,NULL,3),(51,'¿Cuáles de estos son planetas del sistema solar?',NULL,NULL,2),(52,'¿Cuáles de estos son mamíferos?',NULL,NULL,2),(53,'¿Cuáles de estos deportes se juegan con pelota?',NULL,NULL,4),(54,'¿Cuáles de estas son capitales europeas?',NULL,NULL,3),(55,'¿Cuáles de estos elementos son metales?',NULL,NULL,2),(56,'¿Cuáles de estas son operaciones matemáticas básicas?',NULL,NULL,8);
/*!40000 ALTER TABLE `pregunta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta_seleccion_multiple`
--

DROP TABLE IF EXISTS `pregunta_seleccion_multiple`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pregunta_seleccion_multiple` (
  `opciona` varchar(255) DEFAULT NULL,
  `opcionb` varchar(255) DEFAULT NULL,
  `opcionc` varchar(255) DEFAULT NULL,
  `opciond` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK34srj4y5cen12ver2ss9dd9am` FOREIGN KEY (`id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta_seleccion_multiple`
--

LOCK TABLES `pregunta_seleccion_multiple` WRITE;
/*!40000 ALTER TABLE `pregunta_seleccion_multiple` DISABLE KEYS */;
INSERT INTO `pregunta_seleccion_multiple` VALUES ('Marte','Pluton','Jupiter','Luna',36),('Brasil','Mexico','Argentina','España',37),('Helio','Oxigeno','Argon','Nitrogeno',38),('Ebro','Amazonas','Tajo','Nilo',39),('Java','HTML','Python','Word',40),('Java','Python','HTML','JavaScript',49),('Brasil','Argentina','México','Colombia',50),('Marte','Plutón','Júpiter','Luna',51),('Delfín','Tiburón','Ballena','Serpiente',52),('Fútbol','Natación','Baloncesto','Tenis',53),('París','Roma','Berlín','Sydney',54),('Oro','Oxígeno','Hierro','Helio',55),('Suma','Resta','Logaritmo','Multiplicación',56);
/*!40000 ALTER TABLE `pregunta_seleccion_multiple` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta_seleccion_multiple_respuestas_correctas`
--

DROP TABLE IF EXISTS `pregunta_seleccion_multiple_respuestas_correctas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pregunta_seleccion_multiple_respuestas_correctas` (
  `pregunta_seleccion_multiple_id` bigint NOT NULL,
  `respuestas_correctas` varchar(255) DEFAULT NULL,
  KEY `FKcmcfjug9plkuyhb8lm8ghcd9t` (`pregunta_seleccion_multiple_id`),
  CONSTRAINT `FKcmcfjug9plkuyhb8lm8ghcd9t` FOREIGN KEY (`pregunta_seleccion_multiple_id`) REFERENCES `pregunta_seleccion_multiple` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta_seleccion_multiple_respuestas_correctas`
--

LOCK TABLES `pregunta_seleccion_multiple_respuestas_correctas` WRITE;
/*!40000 ALTER TABLE `pregunta_seleccion_multiple_respuestas_correctas` DISABLE KEYS */;
INSERT INTO `pregunta_seleccion_multiple_respuestas_correctas` VALUES (36,'A'),(36,'C'),(37,'A'),(37,'C'),(38,'A'),(38,'C'),(39,'A'),(39,'C'),(40,'A'),(40,'C'),(49,'A'),(49,'B'),(49,'D'),(50,'A'),(50,'B'),(50,'D'),(51,'A'),(51,'C'),(52,'A'),(52,'C'),(53,'A'),(53,'C'),(53,'D'),(54,'A'),(54,'B'),(54,'C'),(55,'A'),(55,'C'),(56,'A'),(56,'B'),(56,'D');
/*!40000 ALTER TABLE `pregunta_seleccion_multiple_respuestas_correctas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta_seleccion_unica`
--

DROP TABLE IF EXISTS `pregunta_seleccion_unica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pregunta_seleccion_unica` (
  `opciona` varchar(255) DEFAULT NULL,
  `opcionb` varchar(255) DEFAULT NULL,
  `opcionc` varchar(255) DEFAULT NULL,
  `opcion_correcta` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKtrnw0kphegrbjixa59iktwl23` FOREIGN KEY (`id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta_seleccion_unica`
--

LOCK TABLES `pregunta_seleccion_unica` WRITE;
/*!40000 ALTER TABLE `pregunta_seleccion_unica` DISABLE KEYS */;
INSERT INTO `pregunta_seleccion_unica` VALUES ('Madrid','París','Londres','B',6),('1965','1969','1972','B',7),('9','10','11','C',8),('Python','HTML','Java','B',9),('Van Gogh','Da Vinci','Picasso','B',10),('Madrid','Paris','Roma','B',25),('7','8','9','B',26),('1965','1969','1972','B',27),('Miguel Angel','Rafael','Leonardo da Vinci','C',28),('206','212','198','A',29),('Atlantico','Indico','Pacifico','C',30),('5','6','7','B',31),('Leon','Guepardo','Halcon peregrino','C',32),('Asia','Africa','Europa','B',33),('Python','Java','JavaScript','C',34);
/*!40000 ALTER TABLE `pregunta_seleccion_unica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta_verdadero_falso`
--

DROP TABLE IF EXISTS `pregunta_verdadero_falso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pregunta_verdadero_falso` (
  `respuesta_correcta` bit(1) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKq5ntcqcmcf32ulw9fc9ws1bqj` FOREIGN KEY (`id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta_verdadero_falso`
--

LOCK TABLES `pregunta_verdadero_falso` WRITE;
/*!40000 ALTER TABLE `pregunta_verdadero_falso` DISABLE KEYS */;
INSERT INTO `pregunta_verdadero_falso` VALUES (_binary '',2),(_binary '\0',3),(_binary '',4),(_binary '\0',5),(_binary '',15),(_binary '\0',16),(_binary '',17),(_binary '',18),(_binary '\0',19),(_binary '',20),(_binary '',21),(_binary '\0',22),(_binary '',23),(_binary '',24);
/*!40000 ALTER TABLE `pregunta_verdadero_falso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `puntuacion` double NOT NULL,
  `respuestas_correctas` int NOT NULL,
  `tipo_preguntas` varchar(255) DEFAULT NULL,
  `total_preguntas` int NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmh3qsg6pnd3r0jddwm5qainmm` (`categoria_id`),
  CONSTRAINT `FKmh3qsg6pnd3r0jddwm5qainmm` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_preguntas_ids`
--

DROP TABLE IF EXISTS `test_preguntas_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_preguntas_ids` (
  `test_id` bigint NOT NULL,
  `preguntas_ids` bigint DEFAULT NULL,
  KEY `FKm9o4kulv07b6pssni50lg5bvw` (`test_id`),
  CONSTRAINT `FKm9o4kulv07b6pssni50lg5bvw` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_preguntas_ids`
--

LOCK TABLES `test_preguntas_ids` WRITE;
/*!40000 ALTER TABLE `test_preguntas_ids` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_preguntas_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_respuestas`
--

DROP TABLE IF EXISTS `test_respuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_respuestas` (
  `test_id` bigint NOT NULL,
  `respuestas` varchar(255) DEFAULT NULL,
  KEY `FKffq1duydleu2ud1rx54fwjtiw` (`test_id`),
  CONSTRAINT `FKffq1duydleu2ud1rx54fwjtiw` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_respuestas`
--

LOCK TABLES `test_respuestas` WRITE;
/*!40000 ALTER TABLE `test_respuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_respuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_table`
--

DROP TABLE IF EXISTS `test_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_table` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_table`
--

LOCK TABLES `test_table` WRITE;
/*!40000 ALTER TABLE `test_table` DISABLE KEYS */;
INSERT INTO `test_table` VALUES (1,'Hola');
/*!40000 ALTER TABLE `test_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_roles`
--

DROP TABLE IF EXISTS `usuario_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_roles` (
  `usuario_id` bigint NOT NULL,
  `rol` varchar(255) DEFAULT NULL,
  KEY `FKuu9tea04xb29m2km5lwe46ua` (`usuario_id`),
  CONSTRAINT `FKuu9tea04xb29m2km5lwe46ua` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_roles`
--

LOCK TABLES `usuario_roles` WRITE;
/*!40000 ALTER TABLE `usuario_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ultimo_login` datetime(6) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `nombre_completo` varchar(100) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (10,_binary '','admin@quizapp.com','$2a$10$PHrXTeYs0zc7oRE7ImIeN.EDNWmcTKXPwXPt0rWQDWn1GRrQhCxIi',NULL,'admin',NULL,'Administrador','ROLE_ADMIN'),(11,_binary '','user@quizapp.com','$2a$10$ntETy4u7e95xGRSa4irYtevgPWBAfRLznCbAqKTzgb9Oeigs3oEJe',NULL,'user',NULL,'Usuario Normal','ROLE_USER');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-20 19:01:10
