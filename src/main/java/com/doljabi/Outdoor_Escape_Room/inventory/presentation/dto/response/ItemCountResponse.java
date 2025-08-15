package com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.inventory.domain.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemCountResponse {
    private Long itemId;
    private String itemName;
    private int count;

    public static List<ItemCountResponse> fromEntityList(List<Inventory> inventoryList) {
        List<ItemCountResponse> itemCountResponseList = new ArrayList<>();
        for(Inventory inventory : inventoryList){
            itemCountResponseList.add(new ItemCountResponse(inventory.getItem().getId(), inventory.getItem().getItemName(), inventory.getItemCount()));
        }
        return itemCountResponseList;
    }
}
