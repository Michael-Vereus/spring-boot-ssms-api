package com.ver.ssms.dto.storage.incoming;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteStorage {
    @NotEmpty(message = "Storage Id CAN'T BE EMTPY")
    private List<String> storageId;
}
