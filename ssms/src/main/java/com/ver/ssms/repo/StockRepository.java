package com.ver.ssms.repo;

import com.ver.ssms.dto.stock.outrepo.StockSummaryMapping;
import com.ver.ssms.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {

    @Query(value = """
        SELECT
            (
                SELECT s2.stock_id
                FROM stock s2
                WHERE s2.item_id  = s.item_id
                  AND s2.storage_id = s.storage_id
                ORDER BY s2.created_at DESC
                LIMIT 1
            )                                                         AS stockId,
            s.item_id                                                 AS itemId,
            s.storage_id                                              AS storageId,
            SUM(CASE WHEN s.type = 'IN'  THEN s.quantity ELSE 0 END) -
            SUM(CASE WHEN s.type = 'OUT' THEN s.quantity ELSE 0 END) -
            SUM(CASE WHEN s.type = 'TRANSFERRED' THEN s.quantity ELSE 0 END) AS quantity_on_hand
        FROM stock s
        GROUP BY s.item_id, s.storage_id
        """, nativeQuery = true)
    List<StockSummaryMapping> getCurrentStock();

    @Query(value = """
        SELECT
            SUM(CASE WHEN type = 'IN'  THEN quantity ELSE 0 END) -
            SUM(CASE WHEN type = 'OUT' THEN quantity ELSE 0 END) -
            SUM(CASE WHEN type = 'TRANSFERRED' THEN quantity ELSE 0 END)
        FROM stock
        WHERE item_id = :itemId AND storage_id = :storageId
    """, nativeQuery = true)
    Long getQuantityOnHand(@Param("itemId") String itemId,
                           @Param("storageId") String storageId);
}
