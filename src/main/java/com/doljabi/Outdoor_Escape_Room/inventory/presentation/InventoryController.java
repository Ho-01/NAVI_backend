package com.doljabi.Outdoor_Escape_Room.inventory.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.inventory.application.InventoryService;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request.InventoryUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryResponse;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/runs/in_progress/inventory/items/{itemId}")
    public ApiResponse<InventoryUpdateResponse> updateMyInventory(
            @PathVariable Long itemId,
            @RequestBody InventoryUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ){
        return ApiResponse.success(inventoryService.updateInventory(userDetails.getUserId(), itemId, request));
    }

    @GetMapping("/runs/in_progress/inventory")
    public ApiResponse<InventoryResponse> openMyInventory(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(inventoryService.findMyInventory(userDetails.getUserId()));
    }
}
