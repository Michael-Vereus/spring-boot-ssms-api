CREATE TABLE item (
      item_id    VARCHAR(255) NOT NULL,
      item_name  VARCHAR(30)  NOT NULL,
      item_price INT          NOT NULL,
      PRIMARY KEY (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;