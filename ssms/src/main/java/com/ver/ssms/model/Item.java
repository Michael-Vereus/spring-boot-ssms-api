package com.ver.ssms.model;

import com.ver.ssms.dto.item.incoming.CreateItem;
import com.ver.ssms.dto.item.incoming.UpdateItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.HexFormat;

// used Lombok dependencies
@Data // getter and setters
@NoArgsConstructor // def constructor
@AllArgsConstructor // def all constructor

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "itemId")
    private String itemID;
    @Column(name = "itemName")
    private String itemName;
    @Column(name = "itemPrice")
    private int itemPrice;

    public Item(CreateItem createItem){
        this.itemID = generateHexId();
        this.itemName = createItem.getItemName();
        this.itemPrice = createItem.getItemPrice();
    }
    public Item(UpdateItem updateItem){
        this.itemID = updateItem.getItemId();
        this.itemName = updateItem.getItemName();
        this.itemPrice = updateItem.getItemPrice();
    }

    public String generateHexId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[4]; // 4 bytes = 8 hex chars -> change this for prefered length
        random.nextBytes(bytes);

        // HexFormat is available in Java 17+
        return HexFormat.of().formatHex(bytes);
    }

}
