package com.ver.ssms.model;

import com.ver.ssms.dto.storage.incoming.CreateStorage;
import com.ver.ssms.dto.storage.incoming.UpdateStorage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.HexFormat;

@Entity
@Table(name = "storage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @Column(name = "storage_id")
    private String storageId;
    @Column(name = "storage_name")
    private String storageName;
    @Column(name = "storage_capacity")
    private int storageCapacity;

    public Storage(CreateStorage createStorage){
        this.storageId = generateHexId();
        this.storageName = createStorage.getStorageName();
        this.storageCapacity = createStorage.getStorageCapacity();
    }

    public Storage(UpdateStorage updateStorage){
        this.storageId = updateStorage.getStorageId();
        this.storageName = updateStorage.getStorageName();
        this.storageCapacity = updateStorage.getStorageCapacity();
    }

    public String generateHexId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[3]; // 4 bytes = 8 hex chars -> change this for prefered length
        random.nextBytes(bytes);

        // HexFormat is available in Java 17+
        return HexFormat.of().formatHex(bytes);
    }
}
