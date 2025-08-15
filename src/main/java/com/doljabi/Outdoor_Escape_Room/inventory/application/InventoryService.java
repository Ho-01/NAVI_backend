package com.doljabi.Outdoor_Escape_Room.inventory.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.Inventory;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.InventoryRepository;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request.InventoryUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryResponse;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryUpdateResponse;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.ItemCountResponse;
import com.doljabi.Outdoor_Escape_Room.item.domain.Item;
import com.doljabi.Outdoor_Escape_Room.item.domain.ItemRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RunRepository runRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public InventoryUpdateResponse updateInventory(Long userId, Long itemId, InventoryUpdateRequest request) {
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new AppException(GlobalErrorCode.INVALID_STATE));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.INVALID_STATE));
        Inventory inventory = inventoryRepository.findByRunIdAndItemId(run.getId(), itemId)
                .orElseGet(() -> inventoryRepository.save(new Inventory(run, item, 0)));
        inventory.update(request.getOperation(), request.getCount());
        return new InventoryUpdateResponse(run.getId(), new ItemCountResponse(itemId, item.getItemName(), inventory.getItemCount()));
    }

    @Transactional(readOnly = true)
    public InventoryResponse findMyInventory(Long userId) {
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new AppException(GlobalErrorCode.INVALID_STATE));
        List<Inventory> inventory = inventoryRepository.findAllByRunWithItem(run.getId());
        return new InventoryResponse(run.getId(), ItemCountResponse.fromEntityList(inventory));
    }
}
