CREATE TABLE stock (
   stock_id   VARCHAR(12) NOT NULL,
   item_id    VARCHAR(8)  NOT NULL,
   storage_id VARCHAR(6)  NOT NULL,
   type       ENUM('IN', 'OUT', 'TRANSFERRED') NOT NULL,
   quantity   INT         NOT NULL,
   created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (stock_id),
   KEY fk_stock_item (item_id),
   KEY fk_stock_storage (storage_id),
   CONSTRAINT fk_stock_item    FOREIGN KEY (item_id)    REFERENCES item (item_id),
   CONSTRAINT fk_stock_storage FOREIGN KEY (storage_id) REFERENCES storage (storage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;