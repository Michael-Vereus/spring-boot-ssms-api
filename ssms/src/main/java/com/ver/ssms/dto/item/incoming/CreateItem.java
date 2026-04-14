package com.ver.ssms.dto.item.incoming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class CreateItem {

    @Null(message = "Id field must be empty")
    private String itemId;

    @NotEmpty(message = "Name field can't be empty")
    private String itemName;

    @NotNull(message = "Price field can't be empty")
    @Min(value = 1, message = "Price can't be lower than 1")
    private int itemPrice;

}
