CREATE TABLE users (
   user_id  VARCHAR(8)   NOT NULL,
   username VARCHAR(100) NOT NULL,
   password VARCHAR(80)  NOT NULL,
   role     ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
   PRIMARY KEY (user_id),
   UNIQUE KEY username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;