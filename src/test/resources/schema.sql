-- Create user_account table
CREATE TABLE IF NOT EXISTS `user_account` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `uuid` varchar(36) NOT NULL,
    `user_id` varchar(255) DEFAULT NULL,
    `password_hash` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uuid` (`uuid`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;