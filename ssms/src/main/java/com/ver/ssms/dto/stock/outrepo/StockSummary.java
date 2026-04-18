package com.ver.ssms.dto.stock.outrepo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSummary {
    @Column(name = "stock_id")
    private String stockId;
    @Column(name = "item_id")
    private String itemId;
    @Column(name = "storage_id")
    private String storageId;
    @Column(name = "quantity_on_hand")
    private Long quantityOnHand;
}
