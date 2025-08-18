package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request;

import com.doljabi.Outdoor_Escape_Room.inventory.domain.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryUpdateRequest {
    private Operation operation;
    private int count;
}
