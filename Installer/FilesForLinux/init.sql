USE oat_db;

CREATE TABLE `PCR_manifest` (
  `index` int(11) NOT NULL AUTO_INCREMENT,
  `PCR_number` int(11) DEFAULT NULL,
  `PCR_value` varchar(100) DEFAULT NULL,
  `PCR_desc` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_request_host` varchar(50) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_request_host` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index`),
  UNIQUE KEY `PCR_UNIQUE` (`PCR_number`,`PCR_value`)
);

CREATE TABLE `attest_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` varchar(50) DEFAULT NULL,
  `host_name` varchar(50) DEFAULT NULL,
  `request_time` datetime DEFAULT NULL,
  `next_action` int(11) DEFAULT NULL,
  `is_consumed_by_pollingWS` tinyint(1) DEFAULT NULL,
  `audit_log_id` int(11) DEFAULT NULL,
  `host_id` int(11) DEFAULT NULL,
  `request_host` varchar(50) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `PCRMask` varchar(50) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `is_sync` tinyint(1) DEFAULT NULL,
  `validate_time` datetime DEFAULT NULL,  
  PRIMARY KEY (`id`),
  KEY `FK_audit_log_id` (`audit_log_id`),
  KEY `UNIQUE` (`request_id`,`host_id`)
);

