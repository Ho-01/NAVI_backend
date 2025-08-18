package com.doljabi.Outdoor_Escape_Room.inventory.domain;

import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByRunIdAndItemId(Long id, Long itemId);

    @Query("""
        select i from Inventory i
        join fetch i.item
        where i.run.id = :runId
        order by i.item.itemName asc
    """)
    List<Inventory> findAllByRunWithItem(Long id);
}
