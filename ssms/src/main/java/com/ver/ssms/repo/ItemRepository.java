package com.ver.ssms.repo;

import com.ver.ssms.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    // Spring handles the Lowercase and LIKE logic automatically
    Optional<Item> findByItemNameContainingIgnoreCase(String name);

    @Modifying // To basically modify a query
    @Query("DELETE FROM Item i where i.id IN :ids") // the query u want
    int deleteByIdString(@Param("ids") List<String> ids); // told the JPA to return the rows affecteed in this case how many rows deleted
}
