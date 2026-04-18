package com.ver.ssms.model;

import com.ver.ssms.dto.stock.incoming.CreateStock;
import com.ver.ssms.dto.stock.incoming.UpdateStock;
import com.ver.ssms.utility.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.HexFormat;

@Data
@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @Column(name = "stock_id")
    private String stockId;
    @Column(name = "item_id")
    private String itemId;
    @Column(name = "storage_id")
    private String storageId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(name = "quantity")
    private int quantity;

    public Stock(CreateStock createStock){
        this.stockId = generateHexId();
        this.itemId = createStock.getItemId();
        this.storageId = createStock.getStorageId();
        this.type = createStock.getType();
        this.quantity = createStock.getQuantity();
    }

    public Stock(UpdateStock updateStock){
        this.stockId = updateStock.getStockId();
        this.itemId = updateStock.getItemId();
        this.storageId = updateStock.getStorageId();
        this.type = updateStock.getType();
        this.quantity = updateStock.getQuantity();
    }

    public static Stock outStockObj(Stock latestTransaction){
        Stock outStock = new Stock();
        outStock.setStockId(outStock.generateHexId());
        outStock.setItemId(latestTransaction.getItemId());
        outStock.setStorageId(latestTransaction.getStorageId());
        outStock.setType(TransactionType.TRANSFERRED);
        outStock.setQuantity(latestTransaction.getQuantity());
        return outStock;
    }

    public static Stock deleteStock(Stock stockFound, Long quantityOnHand){

        Stock outStock = new Stock();
        outStock.setStockId(outStock.generateHexId());
        outStock.setItemId(stockFound.getItemId());
        outStock.setStorageId(stockFound.getStorageId());
        outStock.setType(TransactionType.OUT);
        outStock.setQuantity(Math.toIntExact(quantityOnHand));
        return outStock;
    }

    public String generateHexId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[6]; // 6 bytes = 12 hex chars -> change this for prefered length
        random.nextBytes(bytes);

        // HexFormat is available in Java 17+
        return HexFormat.of().formatHex(bytes);
    }
}
