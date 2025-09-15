package com.doljabi.Outdoor_Escape_Room.inventory.presentation;

import com.doljabi.Outdoor_Escape_Room.inventory.application.InventoryService;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request.InventoryUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/runs")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/in_progress/inventory")
    public InventoryResponse getMine(HttpServletRequest req) {
        log.info("GET /runs/in_progress/inventory");
        return inventoryService.getMine(req);
    }

    @GetMapping("/{runId}/inventory")
    public InventoryResponse getMineByRun(HttpServletRequest req,
                                          @PathVariable("runId") Long runId) { // ← 이름 명시
        log.info(">> GET /runs/{}/inventory hit", runId);
        return inventoryService.getMineByRunId(req, runId);
    }

    @PostMapping("/in_progress/inventory/item/{itemId}")
    public InventoryResponse updateItemInProgress(HttpServletRequest req, @PathVariable Long itemId,
                                                  @RequestBody InventoryUpdateRequest body) {
        return inventoryService.updateItem(req, itemId, body);
    }

    @PostMapping("/{runId}/inventory/item/{itemId}")
    public InventoryResponse updateItemForRun(HttpServletRequest req, @PathVariable Long runId, @PathVariable Long itemId,
                                              @RequestBody InventoryUpdateRequest body) {
        return inventoryService.updateItemByRunId(req, runId, itemId, body);
    }
}
