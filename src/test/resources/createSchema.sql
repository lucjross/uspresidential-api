-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0
;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0
;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES'
;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema presidents
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `${schema.name}`
;

-- -----------------------------------------------------
-- Schema presidents
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `${schema.name}` DEFAULT CHARACTER SET utf8
;
USE `${schema.name}`
;

-- -----------------------------------------------------
-- Table `presidents`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`username`))
;


-- -----------------------------------------------------
-- Table `presidents`.`authorities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `ix_auth_username` (`username` ASC, `authority` ASC),
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `${schema.name}`.`users` (`username`))
;


-- -----------------------------------------------------
-- Table `presidents`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`country` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `iso` CHAR(2) CHARACTER SET 'latin1' NOT NULL,
  `name` VARCHAR(80) CHARACTER SET 'latin1' NOT NULL,
  `nicename` VARCHAR(80) CHARACTER SET 'latin1' NOT NULL,
  `iso3` CHAR(3) CHARACTER SET 'latin1' NULL DEFAULT NULL,
  `numcode` SMALLINT(6) NULL DEFAULT NULL,
  `phonecode` INT(5) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `presidents`.`p_presidents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`presidents` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `lastname` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `order` INT(11) NOT NULL,
  `wiki_link` TEXT NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `presidents`.`p_events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`events` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `description` TEXT NOT NULL,
  `president_id` INT(11) NOT NULL,
  `weight` TINYINT(4) NOT NULL DEFAULT '0',
  `importance` ENUM('major','minor') NOT NULL DEFAULT 'minor',
  `category` ENUM('foreign','domestic','appointment','misc','unassigned') NOT NULL DEFAULT 'unassigned',
  `summary` TEXT NOT NULL,
  `start` DATE NULL DEFAULT NULL,
  `end` DATE NULL DEFAULT NULL,
  `wiki_link` TEXT NOT NULL,
  primary key (`id`),
  CONSTRAINT `events_ibfk_1`
    FOREIGN KEY (`president_id`)
    REFERENCES `${schema.name}`.`presidents` (`id`))
;


-- -----------------------------------------------------
-- Table `presidents`.`p_users`
-- -----------------------------------------------------
--CREATE TABLE IF NOT EXISTS `${schema.name}`.`p_users` (
--  `ID` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
--  `user_login` VARCHAR(60) NOT NULL,
--  `user_pass` VARCHAR(64) NOT NULL,
--  `user_email` VARCHAR(100) NOT NULL,
--  `user_registered` DATETIME NOT NULL,
--  `display_name` VARCHAR(250) NOT NULL,
--  `privilege` TINYINT(4) NOT NULL,
--  PRIMARY KEY (`ID`))
--;


-- -----------------------------------------------------
-- Table `presidents`.`p_userdetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`userdetail` (
  `username` VARCHAR(50) NOT NULL,
  `creation_timestamp` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `birth_date` DATE NOT NULL,
  `gender` ENUM('M','F') NOT NULL,
  `politics_social` ENUM('Conservative','Moderate','Liberal') NOT NULL,
  `politics_fiscal` ENUM('Conservative','Moderate','Liberal') NOT NULL,
  `education` ENUM('Less than HS','HS Graduate or equivalent','Technical school','Some college','Two-year college/Assoc degree','Four-year college Bachelor degree','Master degree','Doctorate') NOT NULL,
  `occupation` ENUM('Architecture and Engineering','Arts, Design, Entertainment, Sports, and Media','Building and Grounds Cleaning and Maintenance','Business and Financial Operations','Community and Social Service','Computer and Mathematical','Construction and Extraction','Education, Training, and Library','Farming, Fishing, and Forestry','Food Preparation and Serving','Healthcare Practitioners and Technical','Healthcare Support','Installation, Maintenance, and Repair','Legal','Life, Physical, and Social Science','Management','Military Specific','Office and Administrative Support','Personal Care and Service','Production/Manufacturing','Protective Service','Sales and Related','Transportation and Material Moving') NOT NULL,
  `state_residence` TEXT NOT NULL,
  `country_residence` INT(11) NOT NULL,
  `religion` TEXT NOT NULL,
  `annual_income` ENUM('< 40k','40-59k','60-79k','80-119k','> 120k') NOT NULL,
  `marital_status` ENUM('Never married','Married','Divorced','Widow[er]','Domestic partnership') NOT NULL,
  `sexuality` ENUM('Straight or heterosexual','Lesbian, gay, or homosexual','Bisexual','Something else','Don''t know') NOT NULL,
  INDEX `username` (`username` ASC),
  CONSTRAINT `p_userdetail_ibfk_1`
    FOREIGN KEY (`username`)
    REFERENCES `${schema.name}`.`users` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `p_userdetail_ibfk_2`
    FOREIGN KEY (`country_residence`)
    REFERENCES `${schema.name}`.`country` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
;


-- -----------------------------------------------------
-- Table `presidents`.`p_votes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `${schema.name}`.`votes` (
  `user_username` VARCHAR(50) NOT NULL,
  `event_id` BIGINT(20) UNSIGNED NOT NULL,
  `vote` ENUM('Yes', 'No', 'Unknown', 'Not important') NOT NULL,
  `weight` TINYINT(4) NULL,
  `timestamp` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  INDEX `user_username` (`user_username` ASC),
  INDEX `event_id` (`event_id` ASC),
  CONSTRAINT `vote_TO_event`
    FOREIGN KEY (`event_id`)
    REFERENCES `${schema.name}`.`events` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `vote_TO_user`
    FOREIGN KEY (`user_username`)
    REFERENCES `${schema.name}`.`users` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
;


SET SQL_MODE=@OLD_SQL_MODE
;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS
;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS
;
