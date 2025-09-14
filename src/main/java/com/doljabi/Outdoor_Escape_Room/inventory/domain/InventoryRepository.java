package com.doljabi.Outdoor_Escape_Room.inventory.domain;

import com.doljabi.Outdoor_Escape_Room.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByRun_Id(Long runId);
    Optional<Inventory> findByRun_IdAndItem_Id(Long runId, Long itemId);


}
