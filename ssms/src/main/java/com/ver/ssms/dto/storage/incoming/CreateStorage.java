package com.ver.ssms.dto.storage.incoming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class CreateStorage {

    @Null(message = "Storage Id Field MUST BE EMPTY")
    private String storageId;
    @NotEmpty(message = "Storage Name CAN'T BE EMPTY")
    private String storageName;
    @NotNull(message = "Storage Capacity CAN'T BE EMPTY")
    @Min(value = 1, message = "Capacity Can't be LOWER THAN 1")
    private int storageCapacity;
}
