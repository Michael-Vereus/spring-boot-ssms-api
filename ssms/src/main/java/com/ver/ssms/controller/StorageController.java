package com.ver.ssms.controller;

import com.ver.ssms.dto.BodyResponse;
import com.ver.ssms.dto.storage.incoming.CreateStorage;
import com.ver.ssms.dto.storage.incoming.DeleteStorage;
import com.ver.ssms.dto.storage.incoming.UpdateStorage;
import com.ver.ssms.model.Storage;
import com.ver.ssms.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ssms/api/storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) { this.storageService = storageService;}

    @GetMapping("/index")
    public ResponseEntity<BodyResponse<List<Storage>>> index(){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of("result" , storageService.getAll()))
        );
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<BodyResponse<Optional<Storage>>> searchByName(@PathVariable String name){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of("result", storageService.getByName(name))
                )
        );
    }
    @PostMapping("/new")
    public ResponseEntity<BodyResponse<Storage>> addItem(@Valid @RequestBody CreateStorage createStorage){
        Storage newStorage = new Storage(createStorage);
        //return ResponseEntity.ok(BodyResponse.debug(newItem.getItemID() + newItem.getItemPrice()) ); --> for debugging
        return ResponseEntity.ok(BodyResponse.success(Map.of("result", storageService.upsert(newStorage))));
    }

    @DeleteMapping("/del")
    public ResponseEntity<BodyResponse<Boolean>> addItem(@Valid @RequestBody DeleteStorage deleteStorage){
        return ResponseEntity.ok(BodyResponse.debug(storageService.deleteById(deleteStorage.getStorageId())));
    }

    @PutMapping("/update")
    public ResponseEntity<BodyResponse<Storage>> updateItem(@Valid @RequestBody UpdateStorage updateStorage){
        return ResponseEntity.ok(BodyResponse.success(Map.of("result", storageService.upsert(new Storage(updateStorage)))));
    }

}
