CREATE TABLE storage (
     storage_id       VARCHAR(6)  NOT NULL,
     storage_name     VARCHAR(20) NOT NULL,
     storage_capacity INT         NOT NULL,
     PRIMARY KEY (storage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;