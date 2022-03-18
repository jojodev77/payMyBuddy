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
