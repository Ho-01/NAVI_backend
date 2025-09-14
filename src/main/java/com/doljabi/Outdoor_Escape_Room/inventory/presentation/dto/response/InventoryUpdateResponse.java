package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryUpdateResponse {
    private Long itemId;
    private int count;
    private String status;   // ★ 추가 (예: "UPDATED", "DELETED")
}
