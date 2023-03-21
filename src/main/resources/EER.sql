-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cupcake` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cupcake` ;

-- -----------------------------------------------------
-- Table `cupcake`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cupcake`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `balance` FLOAT NOT NULL,
  `role` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cupcake`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cupcake`.`order` (
  `orderId` INT NOT NULL AUTO_INCREMENT,
  `price` FLOAT NOT NULL,
  `userId` INT NOT NULL,
  `isPaid` TINYINT NOT NULL DEFAULT 0,
  `isOrdered` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`orderId`),
  INDEX `fk_order_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `cupcake`.`user` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cupcake`.`cupcakebase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cupcake`.`cupcakebase` (
  `cupcakebaseId` INT NOT NULL AUTO_INCREMENT,
  `flavor` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`cupcakebaseId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cupcake`.`cupcaketopping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cupcake`.`cupcaketopping` (
  `cupcaketoppingId` INT NOT NULL AUTO_INCREMENT,
  `flavor` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`cupcaketoppingId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cupcake`.`cupcake`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cupcake`.`cupcake` (
  `cupcakeId` INT NOT NULL AUTO_INCREMENT,
  `orderId` INT NOT NULL,
  `cupcakebaseId` INT NOT NULL,
  `cupcaketoppingId` INT NOT NULL,
  PRIMARY KEY (`cupcakeId`),
  INDEX `fk_cupcake_order1_idx` (`orderId` ASC) VISIBLE,
  INDEX `fk_cupcake_cupcakebase1_idx` (`cupcakebaseId` ASC) VISIBLE,
  INDEX `fk_cupcake_cupcaketopping1_idx` (`cupcaketoppingId` ASC) VISIBLE,
  CONSTRAINT `fk_cupcake_order1`
    FOREIGN KEY (`orderId`)
    REFERENCES `cupcake`.`order` (`orderId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cupcake_cupcakebase1`
    FOREIGN KEY (`cupcakebaseId`)
    REFERENCES `cupcake`.`cupcakebase` (`cupcakebaseId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cupcake_cupcaketopping1`
    FOREIGN KEY (`cupcaketoppingId`)
    REFERENCES `cupcake`.`cupcaketopping` (`cupcaketoppingId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `user` VALUES (null,'admin','admin@admin.dk','1234',1000,'admin'),(null,'user','user@user.dk','1234',1000,'user');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
