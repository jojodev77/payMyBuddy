# payMyBuddy

# DataBase => MySql ------ Name: pmb

#-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306

-- Structure de la table `history_transaction`
--

DROP TABLE IF EXISTS `history_transaction`;
CREATE TABLE IF NOT EXISTS `history_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_reference_transaction` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `display_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sold_account` int NOT NULL,
  `user_account_informations_user_account_informations_id` bigint DEFAULT NULL,
  `fee` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl5h49chyn7g4xj93n1xvbddno` (`user_account_informations_user_account_informations_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `provider_user_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Structure de la table `user_account_informations`
--

DROP TABLE IF EXISTS `user_account_informations`;
CREATE TABLE IF NOT EXISTS `user_account_informations` (
  `user_account_informations_id` bigint NOT NULL AUTO_INCREMENT,
  `account_reference_transaction` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `number_account` int NOT NULL,
  `sold_account` int NOT NULL,
  `state` bit(1) NOT NULL,
  `user_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_account_informations_id`),
  KEY `FK7o116xl9hbtvv8bywchgqs5th` (`user_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Structure de la table `user_partner_account`
--

DROP TABLE IF EXISTS `user_partner_account`;
CREATE TABLE IF NOT EXISTS `user_partner_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_ref_transaction` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_account_informations_user_account_informations_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn9jmlv8vhl3eym88s4om0qn1o` (`user_account_informations_user_account_informations_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Structure de la table `user_partner_account_user_account_informations`
--

DROP TABLE IF EXISTS `user_partner_account_user_account_informations`;
CREATE TABLE IF NOT EXISTS `user_partner_account_user_account_informations` (
  `user_account_informations_id` bigint NOT NULL,
  `user_partner_account_id` bigint NOT NULL,
  PRIMARY KEY (`user_account_informations_id`,`user_partner_account_id`),
  UNIQUE KEY `UK_sgft0m2ff58wll9b6yc869bpq` (`user_partner_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Structure de la table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Structure de la table `user_user_account_informations`
--

DROP TABLE IF EXISTS `user_user_account_informations`;
CREATE TABLE IF NOT EXISTS `user_user_account_informations` (
  `user_account_informations_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKjhvsytcyevdculef3j8dgqf32` (`user_account_informations_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

