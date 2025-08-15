package com.doljabi.Outdoor_Escape_Room.item.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemResponse {
    private Long itemId;
    private String itemName;

    private static ItemResponse fromEntity(Item item) {
        return new ItemResponse(item.getId(), item.getItemName());
    }
    public static List<ItemResponse> fromEntityList(List<Item> itemList) {
        List<ItemResponse> itemResponseList = new ArrayList<>();
        for(Item item : itemList){
            itemResponseList.add(ItemResponse.fromEntity(item));
        }
        return itemResponseList;
    }
}
