package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryResponse {
    private Long runId;
    private List<ItemCountResponse> items;
}
