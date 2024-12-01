-- MySQL dump 10.13  Distrib 9.0.1, for macos15.1 (arm64)
--
-- Host: localhost    Database: DBLetter
-- ------------------------------------------------------
-- Server version	9.0.1
--
-- Table structure for table `Alarm`
--

CREATE TABLE `Alarm` (
  `notificationId` int NOT NULL AUTO_INCREMENT,
  `userId` varchar(50) NOT NULL,
  `alarmContents` varchar(255) NOT NULL,
  `alarmDate` date NOT NULL,
  `letterId` int NOT NULL,
  `isBaked` tinyint DEFAULT '0',
  PRIMARY KEY (`notificationId`),
  KEY `FK_Letter_User2` (`userId`),
  KEY `letterid_idx` (`letterId`),
  KEY `fk_isbaked_alarm_idx` (`isBaked`),
  CONSTRAINT `fk_letter_alram` FOREIGN KEY (`letterId`) REFERENCES `Letter` (`letterId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Letter_User2` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) 

--
-- Table structure for table `Letter`
--

CREATE TABLE `Letter` (
  `letterId` int NOT NULL AUTO_INCREMENT,
  `writerName` varchar(50) NOT NULL,
  `contents` varchar(255) NOT NULL,
  `createDate` datetime NOT NULL,
  `unLockTimer` datetime DEFAULT NULL,
  `userId` varchar(50) NOT NULL,
  `createYear` int NOT NULL,
  `createMonth` int NOT NULL,
  `isBaked` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`letterId`),
  KEY `idx_createDate` (`createYear`,`createMonth`),
  KEY `idx_userid_createDate` (`userId`,`createYear`,`createMonth`),
  CONSTRAINT `FK_Letter_User` FOREIGN KEY (`userId`) REFERENCES `USER` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
--Trigger
--
DELIMITER $$

CREATE TRIGGER `after_letter_insert` 
AFTER INSERT ON `letter` 
FOR EACH ROW 
BEGIN
    INSERT INTO `Alarm` (userId, alarmContents, alarmDate, letterId, isBaked)
    VALUES (
        NEW.userId,
        CONCAT(NEW.writerName, '(으)로부터 새로운 편지가 작성되었습니다'),
        CURDATE(), -- DATE 타입에 맞는 현재 날짜
        NEW.letterId,
        NEW.isBaked
    );
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER `after_unlockTimer_update`
AFTER UPDATE ON `letter`
FOR EACH ROW
BEGIN
    -- unLockTimer가 NULL이 아니고, 현재 시간보다 과거인지 확인
    IF NEW.unLockTimer IS NOT NULL AND NEW.unLockTimer <= NOW() THEN
        INSERT INTO `Alarm` (userId, alarmContents, alarmDate, letterId, isBaked)
        VALUES (NEW.userId, CONCAT(NEW.writerName, '(이)가 작성한 붕어빵이 다 구워졌어요!'), NOW(), NEW.letterId, TRUE);
    END IF;
END $$

DELIMITER ;

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `userId` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- EventScheduler
--
DELIMITER $$

CREATE EVENT UpdateIsBaked
ON SCHEDULE EVERY 1 MINUTE
DO
  UPDATE Letter
  SET isBaked = TRUE
  WHERE isBaked = FALSE
    AND unLockTimer <= NOW();

DELIMITER ;

-- Dump completed on 2024-12-01 19:50:32
