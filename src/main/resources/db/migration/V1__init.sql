-- MySQL Workbench Synchronization
-- Generated: 2020-09-16 21:30
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Fernando Queiroz Fonseca

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `auth` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `auth`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(80) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `status` SMALLINT(1) NULL DEFAULT NULL,
  `platform_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_user_platform1_idx` (`platform_id` ASC) ,
  CONSTRAINT `fk_user_platform1`
    FOREIGN KEY (`platform_id`)
    REFERENCES `auth`.`platform` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `platform_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_role_platform1_idx` (`platform_id` ASC) ,
  CONSTRAINT `fk_role_platform1`
    FOREIGN KEY (`platform_id`)
    REFERENCES `auth`.`platform` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`user_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_role_role1_idx` (`role_id` ASC) ,
  CONSTRAINT `fk_user_group_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `auth`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `auth`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`platform` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `domain` VARCHAR(80) NOT NULL,
  `subdomains` VARCHAR(255) NULL DEFAULT NULL,
  `status` SMALLINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`parameter` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(45) NULL DEFAULT NULL,
  `value` VARCHAR(45) NULL DEFAULT NULL,
  `platform_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_parameter_platform1_idx` (`platform_id` ASC) ,
  CONSTRAINT `fk_parameter_platform1`
    FOREIGN KEY (`platform_id`)
    REFERENCES `auth`.`platform` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`user_token` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `jti` VARCHAR(100) NOT NULL,
  `type` SMALLINT(1) NULL DEFAULT NULL,
  `expiration` DATETIME NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NOW(),
  `client_id` VARCHAR(80) NULL DEFAULT NULL,
  `client_application_id` INT(11) NULL,
  `user_id` INT(11) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `uuid_UNIQUE` (`jti` ASC) ,
  INDEX `fk_user_token_user1_idx` (`user_id` ASC) ,
  INDEX `fk_user_token_client_application1_idx` (`client_application_id` ASC) ,
  CONSTRAINT `fk_user_token_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `auth`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_token_client_application1`
    FOREIGN KEY (`client_application_id`)
    REFERENCES `auth`.`client_application` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`client_application` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `client_id` VARCHAR(80) NOT NULL,
  `client_secret` VARCHAR(150) NULL DEFAULT NULL,
  `grant_types` VARCHAR(200) NOT NULL,
  `scope` VARCHAR(150) NULL DEFAULT NULL,
  `resource_ids` VARCHAR(200) NULL DEFAULT NULL,
  `refresh_token_validity` INT(11) NULL DEFAULT NULL,
  `access_token_validity` INT(11) NULL DEFAULT NULL,
  `redirect_uri` VARCHAR(200) NULL DEFAULT NULL,
  `additional_information` VARCHAR(200) NULL DEFAULT NULL,
  `platform_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_client_application_platform1_idx` (`platform_id` ASC) ,
  CONSTRAINT `fk_client_application_platform1`
    FOREIGN KEY (`platform_id`)
    REFERENCES `auth`.`platform` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `auth`.`user_activity` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(100) NULL DEFAULT NULL,
  `user_agent` VARCHAR(100) NULL DEFAULT NULL,
  `ip_address` VARCHAR(60) NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `user_id` INT(11) NOT NULL,
  `client_application_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_user_activity_user1_idx` (`user_id` ASC) ,
  INDEX `fk_user_activity_client_application1_idx` (`client_application_id` ASC) ,
  CONSTRAINT `fk_user_activity_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `auth`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_activity_client_application1`
    FOREIGN KEY (`client_application_id`)
    REFERENCES `auth`.`client_application` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
