package com.ver.ssms.controller;

import com.ver.ssms.dto.BodyResponse;
import com.ver.ssms.dto.item.incoming.CreateItem;
import com.ver.ssms.dto.item.incoming.DeleteItem;
import com.ver.ssms.dto.item.incoming.UpdateItem;
import com.ver.ssms.model.Item;
import com.ver.ssms.service.ItemService;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ssms/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService){ this.itemService = itemService;}

    @GetMapping("/index")
    public ResponseEntity<BodyResponse<List<Item>>> index(){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of("result" , itemService.getAll()))
        );
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<BodyResponse<Optional<Item>>> searchByName(@PathVariable String name){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of("result", itemService.getByName(name))
                )
        );
    }

    @PostMapping("/new")
    public ResponseEntity<BodyResponse<Item>> addItem(@Valid @RequestBody CreateItem createItem){
        Item newItem = new Item(createItem);
        //return ResponseEntity.ok(BodyResponse.debug(newItem.getItemID() + newItem.getItemPrice()) ); --> for debugging
        return ResponseEntity.ok(BodyResponse.success(Map.of("result", itemService.upsert(newItem))));
    }
    // test this route later --> note from 06.18
    @DeleteMapping("/del")
    public ResponseEntity<BodyResponse<Boolean>> addItem(@Valid @RequestBody DeleteItem deleteItem){
        return ResponseEntity.ok(BodyResponse.debug(itemService.deleteById(deleteItem.getItemId())));
    }
    // test this route also
    @PutMapping("/update")
    public ResponseEntity<BodyResponse<Item>> updateItem(@Valid @RequestBody UpdateItem updateItem){
        return ResponseEntity.ok(BodyResponse.success(Map.of("result", itemService.upsert(new Item(updateItem)))));
    }

}
