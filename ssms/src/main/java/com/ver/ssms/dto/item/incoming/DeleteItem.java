package com.ver.ssms.dto.item.incoming;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeleteItem {
    @NotEmpty(message = "Id field can't be empty")
    private List<String> itemId;
}
