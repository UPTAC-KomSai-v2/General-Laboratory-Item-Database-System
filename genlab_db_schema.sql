CREATE DATABASE  IF NOT EXISTS `genlab_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `genlab_db`;
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
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow` (
  `borrow_id` int NOT NULL AUTO_INCREMENT,
  `item_id` int NOT NULL,
  `borrower_id` varchar(10) NOT NULL,
  `course_id` varchar(30) DEFAULT NULL,
  `section_id` int NOT NULL,
  `qty_borrowed` int NOT NULL,
  `date_borrowed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expected_return_date` timestamp NOT NULL DEFAULT ((now() + interval 4 day)),
  `actual_return_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`borrow_id`,`borrower_id`,`item_id`),
  KEY `idx_borrow_item` (`item_id`),
  KEY `idx_borrow_borrower` (`borrower_id`),
  KEY `idx_borrow_course_section` (`course_id`,`section_id`),
  KEY `idx_borrow_dates` (`date_borrowed`,`expected_return_date`,`actual_return_date`),
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE,
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`borrower_id`) REFERENCES `borrower` (`borrower_id`) ON DELETE CASCADE,
  CONSTRAINT `borrow_ibfk_3` FOREIGN KEY (`course_id`, `section_id`) REFERENCES `course_section` (`course_id`, `section_id`) ON DELETE CASCADE,
  CONSTRAINT `borrow_chk_1` CHECK ((`qty_borrowed` >= 0)),
  CONSTRAINT `borrow_chk_2` CHECK ((`expected_return_date` >= `date_borrowed`)),
  CONSTRAINT `borrow_chk_3` CHECK (((`actual_return_date` is null) or (`actual_return_date` >= `date_borrowed`)))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_borrow` AFTER INSERT ON `borrow` FOR EACH ROW BEGIN
    UPDATE item 
    SET qty = qty - NEW.qty_borrowed,
        status = CASE
            WHEN (qty - NEW.qty_borrowed) <= 0 THEN 'Borrowed'
            ELSE status
        END 
    WHERE item_id = NEW.item_id AND qty >= NEW.qty_borrowed;
    
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot borrow more items than available';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrower` (
  `borrower_id` varchar(10) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contact_number` varchar(13) NOT NULL,
  `degree_prog` enum('BS in Applied Mathematics','BS in Biology','BS in Computer Science') DEFAULT NULL,
  PRIMARY KEY (`borrower_id`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `chk_contact_number` CHECK (regexp_like(`contact_number`,_utf8mb4'^(09|\\+639)[0-9]{9}$'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5470009 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` varchar(30) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_name` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course_section`
--

DROP TABLE IF EXISTS `course_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_section` (
  `course_id` varchar(30) NOT NULL,
  `section_id` int NOT NULL,
  `instructor_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`,`section_id`),
  KEY `section_id` (`section_id`),
  KEY `instructor_id` (`instructor_id`),
  CONSTRAINT `course_section_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE,
  CONSTRAINT `course_section_ibfk_2` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE CASCADE,
  CONSTRAINT `course_section_ibfk_3` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_name` varchar(50) NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`image_name`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `instructor_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`instructor_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `qty` int NOT NULL,
  `category_id` int NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `idx_item_category` (`category_id`),
  KEY `idx_item_status` (`status`),
  FULLTEXT KEY `idx_item_name_search` (`item_name`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT,
  CONSTRAINT `item_chk_1` CHECK ((`qty` >= 0)),
  CONSTRAINT `item_chk_2` CHECK ((`status` in (_utf8mb4'Available',_utf8mb4'Borrowed',_utf8mb4'Reserved',_utf8mb4'Maintenance')))
) ENGINE=InnoDB AUTO_INCREMENT=10010121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `item_availability`
--

DROP TABLE IF EXISTS `item_availability`;
/*!50001 DROP VIEW IF EXISTS `item_availability`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `item_availability` AS SELECT 
 1 AS `item_id`,
 1 AS `item_name`,
 1 AS `qty`,
 1 AS `status`,
 1 AS `category_name`,
 1 AS `total_reserved`,
 1 AS `total_borrowed`,
 1 AS `available_qty`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `overdue_items`
--

DROP TABLE IF EXISTS `overdue_items`;
/*!50001 DROP VIEW IF EXISTS `overdue_items`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `overdue_items` AS SELECT 
 1 AS `borrow_id`,
 1 AS `item_name`,
 1 AS `borrower_name`,
 1 AS `date_borrowed`,
 1 AS `expected_return_date`,
 1 AS `days_overdue`,
 1 AS `late_fee`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `return_log`
--

DROP TABLE IF EXISTS `return_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_log` (
  `return_id` int NOT NULL AUTO_INCREMENT,
  `borrow_id` int NOT NULL,
  `return_date` timestamp NOT NULL,
  `item_condition` varchar(100) NOT NULL,
  `late_fee` decimal(10,2) NOT NULL DEFAULT '0.00',
  `borrower_id` varchar(10) NOT NULL,
  `item_id` int NOT NULL,
  `qty_returned` int NOT NULL,
  PRIMARY KEY (`return_id`),
  KEY `idx_return_borrow` (`borrow_id`),
  KEY `return_log_ibfk_2` (`borrow_id`,`borrower_id`,`item_id`),
  CONSTRAINT `return_log_ibfk_2` FOREIGN KEY (`borrow_id`, `borrower_id`, `item_id`) REFERENCES `borrow` (`borrow_id`, `borrower_id`, `item_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_return` BEFORE INSERT ON `return_log` FOR EACH ROW BEGIN
    DECLARE expected_date DATE;
    DECLARE days_late INT;
    
    SELECT expected_return_date INTO expected_date 
    FROM borrow 
    WHERE borrow_id = NEW.borrow_id AND borrower_id = NEW.borrower_id AND item_id = NEW.item_id;
    
    SET days_late = DATEDIFF(NEW.return_date, expected_date);
    
    IF days_late > 0 THEN
        SET NEW.late_fee = days_late * 5.00;
        SET NEW.item_condition = CONCAT(IFNULL(NEW.item_condition, ''), ' (Late return)');
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_return` AFTER INSERT ON `return_log` FOR EACH ROW BEGIN
    UPDATE item 
    JOIN borrow ON item.item_id = borrow.item_id 
    SET item.qty = item.qty + borrow.qty_borrowed,
        item.status = CASE 
            WHEN item.qty + borrow.qty_borrowed > 0 THEN 'Available' 
            ELSE item.status 
        END 
    WHERE borrow.borrow_id = NEW.borrow_id AND borrow.borrower_id = NEW.borrower_id AND borrow.item_id = NEW.item_id;
    
    UPDATE borrow 
    SET actual_return_date = NEW.return_date 
    WHERE borrow_id = NEW.borrow_id AND borrower_id = NEW.borrower_id AND item_id = NEW.item_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `update_actual_return` AFTER INSERT ON `return_log` FOR EACH ROW BEGIN
    DECLARE updated_qty INT;

    -- Subtract the qty_returned from the current qty_borrowed
    UPDATE borrow
    SET qty_borrowed = qty_borrowed - NEW.qty_returned
    WHERE borrow_id = NEW.borrow_id
      AND borrower_id = NEW.borrower_id
      AND item_id = NEW.item_id;

    -- Get the updated qty_borrowed to check if it's now zero
    SELECT qty_borrowed INTO updated_qty
    FROM borrow
    WHERE borrow_id = NEW.borrow_id
      AND borrower_id = NEW.borrower_id
      AND item_id = NEW.item_id;

    -- If all items have been returned, set the actual_return_date
    IF updated_qty = 0 THEN
        UPDATE borrow
        SET actual_return_date = NEW.return_date
        WHERE borrow_id = NEW.borrow_id
          AND borrower_id = NEW.borrower_id
          AND item_id = NEW.item_id;
    ELSE
        -- If not all returned, ensure actual_return_date remains NULL
        UPDATE borrow
        SET actual_return_date = NULL
        WHERE borrow_id = NEW.borrow_id
          AND borrower_id = NEW.borrower_id
          AND item_id = NEW.item_id;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `section_id` int NOT NULL AUTO_INCREMENT,
  `section_name` varchar(50) NOT NULL,
  PRIMARY KEY (`section_id`),
  UNIQUE KEY `section_name` (`section_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `staff_user`
--

DROP TABLE IF EXISTS `staff_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff_user` (
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `logID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `user_activity_summary`
--

DROP TABLE IF EXISTS `user_activity_summary`;
/*!50001 DROP VIEW IF EXISTS `user_activity_summary`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_activity_summary` AS SELECT 
 1 AS `borrower_id`,
 1 AS `full_name`,
 1 AS `email`,
 1 AS `total_borrowings`,
 1 AS `total_reservations`,
 1 AS `active_borrowings`,
 1 AS `total_late_fees`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'genlab_db'
--

--
-- Dumping routines for database 'genlab_db'
--

--
-- Final view structure for view `item_availability`
--

/*!50001 DROP VIEW IF EXISTS `item_availability`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jade`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `item_availability` AS select `i`.`item_id` AS `item_id`,`i`.`item_name` AS `item_name`,`i`.`qty` AS `qty`,`i`.`status` AS `status`,`c`.`category_name` AS `category_name`,0 AS `total_reserved`,coalesce(sum((case when (`b`.`actual_return_date` is null) then `b`.`qty_borrowed` else 0 end)),0) AS `total_borrowed`,(`i`.`qty` - coalesce(sum((case when (`b`.`actual_return_date` is null) then `b`.`qty_borrowed` else 0 end)),0)) AS `available_qty` from ((`item` `i` join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) left join `borrow` `b` on((`i`.`item_id` = `b`.`item_id`))) group by `i`.`item_id`,`i`.`item_name`,`i`.`qty`,`i`.`status`,`c`.`category_name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `overdue_items`
--

/*!50001 DROP VIEW IF EXISTS `overdue_items`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jade`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `overdue_items` AS select `b`.`borrow_id` AS `borrow_id`,`i`.`item_name` AS `item_name`,`br`.`full_name` AS `borrower_name`,`b`.`date_borrowed` AS `date_borrowed`,`b`.`expected_return_date` AS `expected_return_date`,(to_days(curdate()) - to_days(`b`.`expected_return_date`)) AS `days_overdue`,`rl`.`late_fee` AS `late_fee` from (((`borrow` `b` join `item` `i` on((`b`.`item_id` = `i`.`item_id`))) join `borrower` `br` on((`b`.`borrower_id` = `br`.`borrower_id`))) left join `return_log` `rl` on((`b`.`borrow_id` = `rl`.`borrow_id`))) where ((`b`.`actual_return_date` is null) and (curdate() > `b`.`expected_return_date`)) order by (to_days(curdate()) - to_days(`b`.`expected_return_date`)) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_activity_summary`
--

/*!50001 DROP VIEW IF EXISTS `user_activity_summary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jade`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `user_activity_summary` AS select `br`.`borrower_id` AS `borrower_id`,`br`.`full_name` AS `full_name`,`br`.`email` AS `email`,count(distinct `b`.`borrow_id`) AS `total_borrowings`,0 AS `total_reservations`,sum((case when (`b`.`actual_return_date` is null) then 1 else 0 end)) AS `active_borrowings`,sum(`rl`.`late_fee`) AS `total_late_fees` from ((`borrower` `br` left join `borrow` `b` on((`br`.`borrower_id` = `b`.`borrower_id`))) left join `return_log` `rl` on((`b`.`borrow_id` = `rl`.`borrow_id`))) group by `br`.`borrower_id`,`br`.`full_name`,`br`.`email` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 22:26:25
