package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.ItemCountResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryResponse {
    private List<ItemCountResponse> items;

    public static InventoryResponse from(List<ItemCountResponse> items) {
        return new InventoryResponse(items);
    }
}
