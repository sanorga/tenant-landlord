DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `openid_identifier` varchar(50) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `social_acct_type` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
   `address` varchar(80) DEFAULT NULL,
  `address2` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  `country` char(2) NOT NULL DEFAULT 'US',
  `phone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `account_type` enum('L','F','G','T') NOT NULL DEFAULT 'L',
  `last_login` datetime DEFAULT NULL,
  `status` enum('X','A','I','L') NOT NULL DEFAULT 'X',
  `has_hashed_password` enum('Y','N','I') DEFAULT 'N',
  `require_mfa` bit(1) NOT NULL DEFAULT b'0',
  `enable_mfa` bit(1) NOT NULL DEFAULT b'0',
  `password_policy_id` int(10) unsigned DEFAULT NULL,
  `login_fail_count` int(11) NOT NULL DEFAULT '0',
  `lockout_until` datetime DEFAULT NULL,
  `count` int(10) DEFAULT '0',
  `question1` int(10) DEFAULT NULL,
  `question2` int(10) DEFAULT NULL,
  `question3` int(10) DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id` (`email_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;


--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;

CREATE TABLE `property` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `property_ext_id` int(10) unsigned DEFAULT NULL,
  `property_identifier` varchar(50) DEFAULT NULL,
  `organization_id` int(10) unsigned DEFAULT NULL,
  `organization_name` varchar(100) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `street` varchar(80) DEFAULT NULL,
  `unit_no` varchar(10) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `zipcode` varchar(5) DEFAULT NULL,
  `county` varchar(50) DEFAULT NULL,
  `country` char(2) DEFAULT NULL,
  `rental_amount` decimal(12,2) DEFAULT NULL,
  `rental_deposit` double(12,2) NOT NULL DEFAULT '0.00',
  `rental_period` int(10) unsigned DEFAULT 0,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `classification` enum('C','S') NOT NULL DEFAULT 'C',
  `decline_for_open_bkr` bit(1) NOT NULL DEFAULT b'0',
  `inc_foreclosures` bit(1) NOT NULL DEFAULT b'0',
  `inc_med_collections` bit(1) NOT NULL DEFAULT b'0',
  `ir` decimal(12,2) DEFAULT 0.0,
  `is_fcra_accepted` bit(1) NOT NULL DEFAULT b'0',
  `open_bkr_window` int(10) unsigned DEFAULT 0,
  `phone` varchar(10) DEFAULT NULL,
  `phone_extension` varchar(6) DEFAULT NULL,
  `future_use` bit(1) NOT NULL DEFAULT b'0',
  `extension` varchar(6) DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `property_id` (`property_id`),
  KEY `property_identifier` (`property_identifier`),
  KEY `user_id` (`user_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `property_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `property_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
  CONSTRAINT `property_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=496 DEFAULT CHARSET=latin1;






DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(2) NOT NULL,
  `is_available` enum('Y','N') NOT NULL DEFAULT 'Y',
  `description` varchar(2000) DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `password_policy_id`  int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS `capability`;

CREATE TABLE `capability` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `request_id` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `is_available` enum('Y','N') NOT NULL DEFAULT 'Y',
  `created_by` int(10) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_id` (`request_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `capability_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `capability_ibfk_2` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `role2capability`;

CREATE TABLE `role2capability` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL,
  `capability_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `capability_id` (`capability_id`),
  CONSTRAINT `role2capability_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role2capability_ibfk_2` FOREIGN KEY (`capability_id`) REFERENCES `capability` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=322 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `password_policy`;

CREATE TABLE `password_policy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(20) NOT NULL,
  `min_length` tinyint(3) unsigned NOT NULL DEFAULT '8',
  `max_length` tinyint(3) unsigned NOT NULL DEFAULT '20',
  `max_age` tinyint(3) unsigned NOT NULL DEFAULT '90',
  `min_uppercase` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `min_lowercase` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `min_digit` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `min_special_character` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `min_remembered_password` tinyint(3) unsigned NOT NULL DEFAULT '3',
  `min_matches` tinyint(3) unsigned NOT NULL DEFAULT '3',
  `require_mfa` bit(1) DEFAULT NULL,
  `attempts_allowed` int(11) NOT NULL DEFAULT '0',
  `reset_lockout_minutes` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `password_policy_description_idx` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `password_history`;

CREATE TABLE `password_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_changed` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pw_history_ids` (`user_id`,`date_changed`),
  CONSTRAINT `pw_hist_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `anonymous_user`;
CREATE TABLE `anonymous_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reference` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `coapp_email_id` varchar(50) NOT NULL,
  `property_id` int(10) unsigned DEFAULT NULL,
  `application_id` int(10) unsigned DEFAULT NULL,
  `package_id` int(10) unsigned DEFAULT NULL,
  `application_type` enum('T','E') NOT NULL DEFAULT 'T',
  `status` enum('A','I') NOT NULL DEFAULT 'A',
  `price` decimal(12,2) NOT NULL DEFAULT '0.00',
  `rental_amount` decimal(12,2) DEFAULT '0.00',
  `rental_deposit` decimal(12,2) NOT NULL DEFAULT '0.00' 
  `is_coapplicant` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_rental_location` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_residence` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_employment` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_financial` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_reference` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_contact` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_occupants` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_vehicle` enum('N','Y') NOT NULL DEFAULT 'Y',
  `is_pet` enum('N','Y') NOT NULL DEFAULT 'Y',
  `save_new_address` enum('N','Y') NOT NULL DEFAULT 'N',
  `is_all_pages` char(1) DEFAULT 'N',
  `created_by` int(10) unsigned DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_method` enum('B','C','A') NOT NULL DEFAULT 'B',
  PRIMARY KEY (`id`),
  KEY `property_id` (`property_id`),
  KEY `modified_by` (`modified_by`),
  KEY `anonymous_user_ibfk_3` (`application_id`),
  KEY `anonymous_user_ibfk_4` (`created_by`),
  KEY `anonymous_user_ibfk_6` (`package_id`),
  CONSTRAINT `anonymous_user_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
  -- CONSTRAINT `anonymous_user_ibfk_3` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `anonymous_user_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `anonymous_user_ibfk_5` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=408 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `system_property`;
CREATE TABLE `system_property` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_name` varchar(50) NOT NULL,
  `value` varchar(100) NOT NULL,
  `property_group` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_property_unique_name` (`property_name`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=latin1;

INSERT INTO `landlord`.`capability` (`request_id`, `name`, `is_available`, `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('USER', 'USER', 'Y', '162', '2015-03-19 00:00:00', '162', '2015-03-19 00:00:00');
INSERT INTO `landlord`.`capability` (`request_id`, `name`, `is_available`, `created_by`, `created_date`, `modified_by`, `modified_date`) VALUES ('new.anonymous_user', 'new.anonymous_user', 'Y', '162', '2015-03-19 00:00:00', '162', '2015-03-19 00:00:00');

INSERT INTO `landlord`.`system_property` (`property_name`, `value`, `property_group`) VALUES ('apiurl', 'https://smlegacygateway-integration.mysmartmove.com/LandlordApi/v1/', 'TransUnion');
INSERT INTO `landlord`.`system_property` (`property_name`, `value`, `property_group`) VALUES ('partner.id', '126', 'TransUnion');
INSERT INTO `landlord`.`system_property` (`property_name`, `value`, `property_group`) VALUES ('key', 'SI2o6o78UaXBg1DgQj1ULmGBNJKcUQ1vn+qL0SR9hAX9PiFhQSNgRGJK0M4QZwyK9BULpRbEzkCmx4YSg05kBA==', 'TransUnion');
INSERT INTO `landlord`.`system_property` (`property_name`, `value`, `property_group`) VALUES ('live', '0', 'TransUnion');

DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `application_ext_id` int(10) unsigned NOT NULL DEFAULT 0,
  `rental_amount` decimal(12,2) DEFAULT 0,
  `rental_deposit` decimal(12,2) NOT NULL DEFAULT '0.00',
  `lease_term_months` int(10) unsigned DEFAULT 0,
  `landlord_pays` bit(1) DEFAULT NULL,
  `property_id` int(10) unsigned DEFAULT NULL,
  `property_ext_id` int(10) unsigned DEFAULT NULL,
  `unit_no` varchar(10) DEFAULT NULL,
  `credit_recommendation` enum('1','2','3','4','5','6') DEFAULT '6',
  `credit_policy` text,
  `status` varchar(50) DEFAULT NULL,
  `state` enum('P','C','A','D','T') NOT NULL DEFAULT 'P',
  `selected_bundle` enum('1','2') DEFAULT '1',
  `can_request_report` bit(1) DEFAULT b'0',
  `created_by` int(10) unsigned DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `application_ext_id` (`application_ext_id`),
  KEY `property_id` (`property_id`),
  KEY `property_ext_id` (`property_ext_id`),
  KEY `created_by` (`created_by`),
  KEY `modified_by` (`modified_by`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`property_ext_id`) REFERENCES `property` (`property_ext_id`),
  CONSTRAINT `application_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `application_ibfk_4` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1647 DEFAULT CHARSET=latin1;

  DROP TABLE IF EXISTS `applicant`;
  
  CREATE TABLE `applicant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `application_id` int(10) unsigned NOT NULL,
  `application_ext_id` int(10) unsigned NOT NULL DEFAULT 0,
  `applicant_type` varchar(50) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(256) DEFAULT NULL,
  `middle_initial` varchar(10) DEFAULT NULL,
  `email_address` varchar(50) DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(10) unsigned DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `applicant_ibfk_1` (`application_id`),
  KEY `applicant_ibfk_2` (`application_ext_id`),
  KEY `applicant_ibfk_3` (`created_by`),
  CONSTRAINT `applicant_ibfk_1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `applicant_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `applicant_ibfk_4` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1837 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `zipcode`;

CREATE TABLE `zipcode` (

  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,

  `zipcode` varchar(20) NOT NULL,

  `city` varchar(50) NOT NULL DEFAULT '',

  `county` varchar(50) NOT NULL DEFAULT '',

  `state` char(3) NOT NULL DEFAULT '',

  `status` char(1) NOT NULL DEFAULT 'I',

  `created_by` int(10) unsigned DEFAULT NULL,

  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

  `modified_by` varchar(50) DEFAULT NULL,

  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=41856 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 11264 kB';
