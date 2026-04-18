package com.ver.ssms.dto.stock.incoming;

import com.ver.ssms.utility.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdateStock {
    @NotEmpty(message = "Stock ID CAN'T BE EMPTY !")
    private String stockId;
    @NotEmpty(message = "Item Id CAN'T BE EMPTY !")
    private String itemId;
    @NotEmpty(message = "Storage Id CAN'T BE EMPTY !")
    private String storageId;
    @NotNull(message = "Quantity CAN'T BE EMPTY")
    @Min(value = 1, message = "Quantity CAN'T BE LOWER THAN 1")
    private int quantity;
    @NotNull(message = "Type CAN'T BE EMPTY")
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
