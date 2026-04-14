package com.ver.ssms.service;

import com.ver.ssms.model.Storage;
import com.ver.ssms.repo.StorageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository){this.storageRepository = storageRepository;}

    public List<Storage> getAll(){ return storageRepository.findAll();}

    public Optional<Storage> getByName(String name){ return storageRepository.findByStorageNameContainingIgnoreCase(name);}

    public Optional<Storage> getById(String id){ return storageRepository.findById(id);}

    @Transactional
    public Storage upsert(Storage storage){
        return storageRepository.save(storage);
    }

    @Transactional
    public boolean deleteById(List<String> deleteIdList){
        return storageRepository.deleteByIdString(deleteIdList) > 0;
    }
}
