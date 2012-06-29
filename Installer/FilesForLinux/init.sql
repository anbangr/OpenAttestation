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

CREATE TABLE measure_log (
  id int NOT NULL AUTO_INCREMENT,
   audit_id             int not null,
   txt_status           int,
   PRIMARY KEY (id)
);

CREATE TABLE txt_log (
   id                   int NOT NULL  AUTO_INCREMENT,
   measure_id           int NOT NULL,
   os_sinit_data_capabilities varchar(100),
   version              int,
   sinit_hash           varchar(100),
   edx_senter_flags     varchar(100),
   bios_acm_id          varchar(100),
   mseg_valid           varchar(100),
   stm_hash             varchar(100),
   policy_control       varchar(100),
   lcp_policy_hash      varchar(100),
   processor_scr_tm_status varchar(100),
   mle_hash         varchar(100),
   PRIMARY KEY (id)
);

CREATE TABLE module (
   id int NOT NULL AUTO_INCREMENT,
   measure_id           int NOT NULL,
   module_name          varchar(50),
   module_value         varchar(100),
   pcr_number           int,
   PRIMARY KEY (id)
);

CREATE TABLE component_manifest (
   `index` int NOT NULL AUTO_INCREMENT,
   comp_name            varchar(50),
   comp_value           varchar(100),
   comp_desc            varchar(100),
   create_time          datetime,
   create_request_host  varchar(50),
   last_update_time     datetime,
   last_update_request_host varchar(50),
   PRIMARY KEY (`index`)
);
