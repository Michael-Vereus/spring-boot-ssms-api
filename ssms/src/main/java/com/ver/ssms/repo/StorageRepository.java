package com.ver.ssms.repo;

import com.ver.ssms.model.Item;
import com.ver.ssms.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, String> {
    // Spring handles the Lowercase and LIKE logic automatically
    Optional<Storage> findByStorageNameContainingIgnoreCase(String name);

    @Modifying // To basically modify a query
    @Query("DELETE FROM Storage i where i.id IN :ids") // the query u want
    int deleteByIdString(@Param("ids") List<String> ids); // told the JPA to return the rows affecteed in this case how many rows deleted
}
