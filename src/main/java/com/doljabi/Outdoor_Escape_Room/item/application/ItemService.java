package com.doljabi.Outdoor_Escape_Room.item.application;

import com.doljabi.Outdoor_Escape_Room.item.domain.ItemRepository;
import com.doljabi.Outdoor_Escape_Room.item.presentation.dto.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public List<ItemResponse> findItems() {
        return ItemResponse.fromEntityList(itemRepository.findAll());
    }
}
