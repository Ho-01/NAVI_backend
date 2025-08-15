package com.doljabi.Outdoor_Escape_Room.item.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.item.application.ItemService;
import com.doljabi.Outdoor_Escape_Room.item.presentation.dto.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public ApiResponse<List<ItemResponse>> getItems(){
        return ApiResponse.success(itemService.findItems());
    }
}
