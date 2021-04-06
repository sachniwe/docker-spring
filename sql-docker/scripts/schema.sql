CREATE DATABASE moviedb;
CREATE TABLE `moviedb`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `original_title` VARCHAR(200) NULL,
  `description` VARCHAR(2000) NULL,
  `released` VARCHAR(50) NULL,
  `studio` VARCHAR(300) NULL,
  `tag` VARCHAR(100) NULL,
  `genre` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));