package com.ver.ssms.service;

import com.ver.ssms.model.Item;
import com.ver.ssms.repo.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){this.itemRepository = itemRepository;}

    public List<Item> getAll(){
        return  itemRepository.findAll();
    }
    public Optional<Item> getById(String id){
        return itemRepository.findById(id);
    }
    public Optional<Item> getByName(String name){
        return itemRepository.findByItemNameContainingIgnoreCase(name);
    }
    @Transactional
    public Item upsert(Item item){
        return itemRepository.save(item);
    }

    @Transactional
    public boolean deleteById(List<String> deleteIdList){
        return itemRepository.deleteByIdString(deleteIdList) > 0;
    }
}
