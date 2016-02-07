--
-- Spring Boot runs this automatically on start
-- "poor-man's migrations" - fail-fast disabled
-- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-initialize-a-database-using-spring-jdbc
--


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
CREATE SCHEMA `%SCHEMA%` DEFAULT CHARACTER SET utf8
;

USE `%SCHEMA%`
;

-- -----------------------------------------------------
-- Table `presidents`.`users`
-- -----------------------------------------------------
CREATE TABLE `%SCHEMA%`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `creationTimestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(255) not null,

  `birthDate` DATE NULL,
  `gender` varchar(32) NULL,
  `politicsSocial` varchar(64) NULL,
  `politicsFiscal` varchar(64) NULL,
  `education` varchar(64) NULL,
  `occupation` varchar(64) NULL,
  `stateOrTerritory` varchar(32) NULL,
  `countryAlpha2Code` varchar(2) NULL,
  `religion` varchar(64) NULL,
  `annualIncome` varchar(32) NULL,
  `maritalStatus` varchar(32) NULL,
  `sexuality` varchar(32) NULL,
  PRIMARY KEY (`username`))
;


-- -----------------------------------------------------
-- Table `presidents`.`authorities`
-- -----------------------------------------------------
CREATE TABLE `%SCHEMA%`.`authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `ix_auth_username` (`username` ASC, `authority` ASC),
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `%SCHEMA%`.`users` (`username`))
;


-- -----------------------------------------------------
-- Table `presidents`.`p_presidents`
-- -----------------------------------------------------
CREATE TABLE `%SCHEMA%`.`presidents` (
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
CREATE TABLE `%SCHEMA%`.`events` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `description` TEXT NOT NULL,
  `president_id` INT(11) NOT NULL,
  `weight` TINYINT(4) NOT NULL DEFAULT 0,
  `importance` varchar(16) NOT NULL DEFAULT 'minor',
  `category` varchar(32) NULL,
  `summary` TEXT NULL,
  `start` DATE NULL,
  `end` DATE NULL,
  `wiki_link` TEXT NULL,
  primary key (`id`),
  CONSTRAINT `events_ibfk_1`
    FOREIGN KEY (`president_id`)
    REFERENCES `%SCHEMA%`.`presidents` (`id`))
;


-- -----------------------------------------------------
-- Table `presidents`.`p_votes`
-- -----------------------------------------------------
CREATE TABLE `%SCHEMA%`.`votes` (
  `user_username` VARCHAR(50) NOT NULL,
  `event_id` BIGINT(20) UNSIGNED NOT NULL,
  `response` varchar(32) NOT NULL,
  `voteWeight` TINYINT(4) NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `vote_TO_event`
    FOREIGN KEY (`event_id`)
    REFERENCES `%SCHEMA%`.`events` (`id`)
    ON DELETE CASCADE -- todo - test
    ON UPDATE CASCADE, -- todo - test
  CONSTRAINT `vote_TO_user`
    FOREIGN KEY (`user_username`)
    REFERENCES `%SCHEMA%`.`users` (`username`)
    ON DELETE CASCADE -- todo - test
    ON UPDATE CASCADE, -- todo - test
  primary key (user_username, event_id))
;


SET SQL_MODE=@OLD_SQL_MODE
;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS
;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS
;
