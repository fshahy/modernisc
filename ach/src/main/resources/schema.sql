CREATE TABLE `data_file` (
  `id` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `line` (
  `id` bigint NOT NULL,
  `core_id` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `data` varchar(255) NOT NULL,
  `file_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2bjhkx05qatn2yaa4flkkqfs9` (`file_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;