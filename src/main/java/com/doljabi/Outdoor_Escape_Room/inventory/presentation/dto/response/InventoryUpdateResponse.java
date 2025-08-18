package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryUpdateResponse {
    private Long runId;
    private ItemCountResponse item;
}
