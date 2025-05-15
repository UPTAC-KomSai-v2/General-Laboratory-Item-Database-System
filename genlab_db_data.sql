-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: caboose.proxy.rlwy.net    Database: genlab_db
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Dumping data for table `borrow`
--

LOCK TABLES `borrow` WRITE;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
INSERT INTO `borrower` VALUES ('1020-39934','Test test','ttest@up.edu.ph','09205939000','BS in Biology'),('1111-09878','master','master@up.edu.ph','09121212121','BS in Biology'),('1201-32442','Bio J. Sic','bioJ6@up.edu.ph','09121212121','BS in Computer Science'),('1212-12121','ASDAS','asdas@up.edu.ph','09121212121','BS in Computer Science'),('1231-03992','albert','maec@up.edu.ph','09123231231','BS in Computer Science'),('1231-09231','aswang','s@up.edu.ph','09121212121','BS in Computer Science'),('1231-21313','nakakatatut','mac12@up.edu.ph','09121212121','BS in Computer Science'),('1232-12332','Jali B.','bjsi22c@up.edu.ph','09121212121','BS in Biology'),('1232-32133','as','sebra@up.edu.ph','09121212121','BS in Computer Science'),('1232-32134','mac','a2@up.edu.ph','09121212121','BS in Computer Science'),('1233-12312','Ma2e','aSAda@up.edu.ph','09121212121','BS in Computer Science'),('1500-21044','Earthum G. Dash','egdash@up.edu.ph','09332841940','BS in Computer Science'),('1500-35949','Rolf Eric I. Eulin','rieulin@up.edu.ph','09128439226','BS in Computer Science'),('1999-19249','Ad H. Hl','ahhl@up.edu.ph','09139424255','BS in Computer Science'),('2013-12332','Bayo J. Sic','bjsic@up.edu.ph','09121212122','BS in Biology'),('2020-56789','Isabel Torres','itorres@up.edu.ph','09678901234','BS in Applied Mathematics'),('2021-10843','Juan Dela Cruz','jdelacruz@up.edu.ph','09123456789','BS in Computer Science'),('2021-67890','Ricardo Mendoza','rmendoza@up.edu.ph','09789012345','BS in Computer Science'),('2021-78326','Ange Yu','ayu@up.edu.ph','09765437862','BS in Biology'),('2022-12345','Maria Santos','msantos@up.edu.ph','09234567890','BS in Applied Mathematics'),('2022-23456','Lourdes Navarro','lnavarro@up.edu.ph','09890123456','BS in Biology'),('2022-67442','Ro Ui','rui@up.edu.ph','09765432876','BS in Computer Science'),('2022-81234','Pedro Guzman','pguzman@up.edu.ph','09567890123','BS in Biology'),('2022-89012','Gabriela Cruz','gcruz@up.edu.ph','09012345678','BS in Computer Science'),('2023-00001','Sean Harvey B. Bantanos','sbbantanod@up.edu.ph','09281939595','BS in Computer Science'),('2023-01324','Foo','foo@up.edu.ph','09123532768','BS in Biology'),('2023-01438','Rolf Genree Garces','rlgarces@up.edu.ph','09751857056','BS in Computer Science'),('2023-02132','Para C. Tamol','para@up.edu.ph','09121212121','BS in Biology'),('2023-04187','Jhun Kenneth R. Iniego','jriniego@up.edu.ph','09281646696','BS in Computer Science'),('2023-04190','Jhun Iniega','jriniega@up.edu.ph','09238445867','BS in Biology'),('2023-09052','Mac Darren Test','macaclimba@up.edu.ph','09173970572','BS in Computer Science'),('2023-09055','Mac TEEST','maccalimba@up.edu.ph','09173970574','BS in Computer Science'),('2023-09058','Mac Darren Test Test Test','macalimba@up.edu.ph','09173970578','BS in Computer Science'),('2023-09133','LATEST','m@up.edu.ph','09121212121','BS in Applied Mathematics'),('2023-09313','tini door','asdaspo@up.edu.ph','09129090901','BS in Computer Science'),('2023-09321','s1 person 1','person1@up.edu.ph','09123234053','BS in Computer Science'),('2023-09434','Mac Darren TEST','calimba@up.edu.ph','09172098741','BS in Computer Science'),('2023-09444','Mac Darren TES3','calimbas@up.edu.ph','09172098211','BS in Biology'),('2023-09832','Rolf','rolf@up.edu.ph','09237465768','BS in Computer Science'),('2023-09854','Mac','mac@up.edu.ph','09263748623','BS in Applied Mathematics'),('2023-09878','test','test@up.edu.ph','09456543456','BS in Biology'),('2023-12323','Bio J. Sic','bioj2@up.edu.ph','09121212121','BS in Applied Mathematics'),('2023-12332','Para C. Tamol','pctamol@up.edu.ph','09121212121','BS in Applied Mathematics'),('2023-12343','Bar','bar@up.edu.ph','09123532768','BS in Biology'),('2023-12432','Jhun Iniega','jkmontes@up.edu.ph','09123321235','BS in Computer Science'),('2023-15472','Norman Shet','nceulinshet@up.edu.ph','09973411094','BS in Biology'),('2023-15674','Ana Lopez','alopez@up.edu.ph','09456789012','BS in Computer Science'),('2023-19321','s1 person 2','person2@up.edu.ph','09123234032','BS in Biology'),('2023-78901','Samuel Reyes','sreyes@up.edu.ph','09901234567','BS in Applied Mathematics'),('2024-98765','Carlos Rivera','crivera@up.edu.ph','09345678901','BS in Biology'),('2025-00289','GUgu','guas@up.edu.ph','09786753928','BS in Biology'),('2025-00893','James Reid','jreid@up.edu.ph','09567856676','BS in Computer Science'),('2027-24042','Fu C. K','fck@up.edu.ph','09876543210','BS in Computer Science'),('2032-12312','Para C. Tamol','pesa@up.edu.ph','09121212121','BS in Applied Mathematics'),('2032-32132','getoRaid','jg@up.edu.ph','09121212121','BS in Applied Mathematics'),('2033-35394','Erratum G. Dash','egdash1@up.edu.ph','09235390100','BS in Computer Science'),('2111-03420','reaosn to be gone','rgone@up.edu.ph','09345829957','BS in Computer Science'),('2131-39423','Rizz D. Gallo','rdgallo@up.edu.ph','09348935344','BS in Computer Science'),('2132-21332','massss','12@up.edu.ph','09123456789','BS in Biology'),('2222-22123','as','a@up.edu.ph','09167566543','BS in Computer Science'),('2302-32131','mtest','tessee@up.edu.ph','09123213213','BS in Computer Science'),('2303-09013','mac','mac1@up.edu.ph','09121212121','BS in Biology'),('2310-21331','easda','asdasda@up.edu.ph','09121212121','BS in Applied Mathematics'),('2321-32132','albert','mas@up.edu.ph','09123232132','BS in Biology'),('2904-54966','Real Ni','rni@up.edu.ph','09388990000','BS in Biology'),('2932-12332','Ma','asd2@up.edu.ph','09121212121','BS in Biology'),('3211-33233','hello','hello@up.edu.ph','09888888888','BS in Biology'),('3232-32303','mac','mac2@up.edu.ph','09121212121','BS in Biology');
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (5470006,'Biological Materials'),(5470004,'Consumables and Miscellaneous'),(5470007,'Electrical Equipment'),(5470001,'Glassware/Plasticware'),(5470003,'Lab Tools and Accessories'),(5470002,'Measuring and Analytical Instruments'),(5470008,'Safety Equipment'),(5470005,'Storage Containers');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('Zoo 102.1','Comparative Anatomy of Invertebrates Laboratory'),('Bio 160.1','Ecology Laboratory'),('Chem 40.1','Elementary Biochemistry Laboratory'),('Bio 140.1','Elementary Genetics Laboratory'),('Physics 71.1','Elementary Physics I Laboratory'),('Bio 120.1','General Microbiology Laboratory'),('Chem 23.1','Inorganic Analytical Chemistry Laboratory'),('Zoo 131.1','Introduction to Developmental Biology of Animals Laboratory'),('Physics 21.1','Introductory Physics Laboratory'),('Zoo 111.1','Invertebrate Zoology Laboratory'),('Chem 31.1','Organic Chemistry Laboratory'),('Bot 111.1','Plant Morphoanatomy and Biodiversity Laboratory'),('Bio 200','Undergraduate Thesis');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `course_section`
--

LOCK TABLES `course_section` WRITE;
/*!40000 ALTER TABLE `course_section` DISABLE KEYS */;
INSERT INTO `course_section` VALUES ('Chem 31.1',11,10001),('Zoo 102.1',1,10001),('Bio 160.1',2,10002),('Bot 111.1',12,10002),('Bio 200',13,10003),('Chem 40.1',3,10003),('Bio 140.1',4,10004),('Physics 71.1',5,10005),('Bio 120.1',6,10006),('Chem 23.1',7,10007),('Zoo 131.1',8,10008),('Physics 21.1',9,10009),('Zoo 111.1',10,10010);
/*!40000 ALTER TABLE `course_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES ('Beaker',5470001),('Burette',5470001),('Cuvette',5470001),('Erlenmeyer Flask',5470001),('Funnel',5470001),('Graduated Cylinder',5470001),('Test Tube',5470001),('Analytical Balance',5470002),('Centrifuge',5470002),('Conductivity Meter',5470002),('Bunsen Burner',5470003),('Burette Clamp',5470003),('Cryovial',5470005),('Desiccator',5470005),('Centrifuge Tube',5470006),('Culture Flask',5470006),('Petri Dish',5470006),('Electrophoresis Apparatus',5470007);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (10001,'Elena','Fernandez','efernandez@up.edu.ph'),(10002,'Victor','Santiago','vsantiago@up.edu.ph'),(10003,'Carla','Mendoza','cmendoza@up.edu.ph'),(10004,'Felipe','Castro','fcastro@up.edu.ph'),(10005,'Rosario','Gomez','rgomez@up.edu.ph'),(10006,'Miguel','Dominguez','mdominguez@up.edu.ph'),(10007,'Lucia','Villanueva','lvillanueva@up.edu.ph'),(10008,'Roberto','Ortega','rortega@up.edu.ph'),(10009,'Teresa','Morales','tmorales@up.edu.ph'),(10010,'Andres','Fuentes','afuentes@up.edu.ph');
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `return_log`
--

LOCK TABLES `return_log` WRITE;
/*!40000 ALTER TABLE `return_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `return_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,'A'),(2,'B'),(3,'C'),(4,'D'),(5,'E'),(6,'F'),(7,'G'),(8,'H'),(9,'I'),(10,'J'),(11,'K'),(12,'L'),(13,'M'),(14,'N'),(15,'O'),(16,'P');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `staff_user`
--

LOCK TABLES `staff_user` WRITE;
/*!40000 ALTER TABLE `staff_user` DISABLE KEYS */;
INSERT INTO `staff_user` VALUES ('2025-05-13 05:15:37',1,NULL),('2025-05-13 06:00:14',2,NULL),('2025-05-13 06:09:13',3,'rolf'),('2025-05-13 06:18:11',4,'rolf'),('2025-05-13 06:26:42',5,'rolf'),('2025-05-13 06:28:19',6,'rolf'),('2025-05-13 06:31:55',7,'rolf'),('2025-05-13 06:34:47',8,'rolf'),('2025-05-13 06:35:43',9,'rolf'),('2025-05-13 06:37:14',10,'rolf'),('2025-05-13 06:38:10',11,'rolf'),('2025-05-13 06:38:51',12,'rolf'),('2025-05-13 06:40:21',13,'rolf'),('2025-05-13 06:41:01',14,'rolf'),('2025-05-13 06:42:07',15,'rolf'),('2025-05-13 06:43:28',16,'rolf'),('2025-05-13 06:45:07',17,'rolf'),('2025-05-13 06:45:53',18,'rolf'),('2025-05-13 06:46:39',19,'rolf'),('2025-05-13 06:47:31',20,'rolf'),('2025-05-13 06:48:16',21,'rolf'),('2025-05-13 06:49:11',22,'rolf'),('2025-05-13 06:49:14',23,'rolf'),('2025-05-13 06:50:47',24,'rolf'),('2025-05-13 06:50:51',25,'rolf'),('2025-05-13 06:51:47',26,'rolf'),('2025-05-13 06:54:08',27,'rolf'),('2025-05-13 06:54:50',28,'rolf'),('2025-05-13 06:56:21',29,'rolf'),('2025-05-13 06:57:10',30,'rolf'),('2025-05-13 06:58:02',31,'rolf'),('2025-05-13 06:59:01',32,'rolf'),('2025-05-13 07:07:03',33,'rolf'),('2025-05-13 07:11:23',34,'rolf'),('2025-05-13 07:12:25',35,'rolf'),('2025-05-13 07:23:26',36,'rolf'),('2025-05-13 07:27:07',37,'rolf'),('2025-05-13 07:29:20',38,'rolf'),('2025-05-13 07:58:08',39,'rolf'),('2025-05-13 08:45:15',40,'rolf'),('2025-05-13 08:53:23',41,'rolf'),('2025-05-13 09:41:43',42,'rolf'),('2025-05-13 09:45:19',43,'rolf'),('2025-05-13 09:48:18',44,'rolf'),('2025-05-13 09:54:20',45,'rolf'),('2025-05-13 10:20:44',46,'rolf'),('2025-05-13 10:24:30',47,'rolf'),('2025-05-13 10:34:40',48,'rolf'),('2025-05-13 10:41:40',49,'rolf'),('2025-05-13 10:52:55',50,'rolf'),('2025-05-13 10:56:13',51,'rolf'),('2025-05-13 10:57:34',52,'rolf'),('2025-05-13 11:00:32',53,'rolf'),('2025-05-13 11:04:44',54,'rolf'),('2025-05-14 23:36:58',55,'rolf');
/*!40000 ALTER TABLE `staff_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 22:30:37
