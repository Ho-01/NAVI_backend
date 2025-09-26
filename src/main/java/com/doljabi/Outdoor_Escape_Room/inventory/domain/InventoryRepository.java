package com.doljabi.Outdoor_Escape_Room.inventory.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByRun_Id(Long runId);

    Optional<Inventory> findByRun_IdAndItem_Id(Long runId, Long itemId);

    // SQLite UPSERT (user_id 제거)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = """
        INSERT INTO inventory (run_id, item_id, item_count)
        VALUES (:runId, :itemId, :val)
        ON CONFLICT(run_id, item_id) DO UPDATE SET
          item_count = CASE WHEN :isAdd = 1
                            THEN MAX(0, inventory.item_count + :val)
                            ELSE MAX(0, :val)
                       END
        """, nativeQuery = true)
    int upsert(@Param("runId") Long runId,
               @Param("itemId") Long itemId,
               @Param("val") int val,
               @Param("isAdd") int isAdd);
}