package com.doljabi.Outdoor_Escape_Room.inventory.application;

import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.Inventory;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.InventoryRepository;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request.InventoryUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryResponse;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.ItemCountResponse;
import com.doljabi.Outdoor_Escape_Room.item.domain.Item;
import com.doljabi.Outdoor_Escape_Room.item.domain.ItemRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final RunRepository runRepository;
    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;

    /** JWT(SecurityContext) → 실패 시 X-User-Id 헤더로 대체 */
    private Long resolveUserId(HttpServletRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof CustomUserDetails cud) {
            return cud.getUserId();
        }
        String h = req.getHeader("X-User-Id");
        if (h != null && !h.isBlank()) return Long.valueOf(h);
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user (X-User-Id)");
    }

    /** 사용자 기준 가장 최근 IN_PROGRESS run */
    private Run getLatestInProgressRun(Long userId) {
        return runRepository
                .findTopByUser_IdAndStatusOrderByIdDesc(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No in-progress run"));
    }

    @Transactional (readOnly = false)// readOnly=true 금지(SQLite 드라이버가 setReadOnly 변경을 막음)
    public InventoryResponse getMine(HttpServletRequest req) {
        Long uid = resolveUserId(req);
        Long runId = getLatestInProgressRun(uid).getId();

        List<Inventory> list = inventoryRepository.findByRun_Id(runId);
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }

    @Transactional
    public InventoryResponse updateItem(HttpServletRequest req, Long pathItemId, InventoryUpdateRequest body) {
        Long uid = resolveUserId(req);
        Run run = getLatestInProgressRun(uid);

        Item item = itemRepository.findById(pathItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found: " + pathItemId));

        Inventory inv = inventoryRepository.findByRun_IdAndItem_Id(run.getId(), item.getId())
                .orElseGet(() -> Inventory.builder().run(run).item(item).itemCount(0).build());

        Integer reqCount = body.getCount(); // nullable
        int n = Math.max(0, (reqCount == null ? 0 : reqCount));
        String op = String.valueOf(body.getOperation()).toUpperCase();

        switch (op) {
            case "ADD" -> inv.addItemCount(Math.max(1, n));
            case "SET" -> inv.setItemCount(n);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operation must be ADD or SET");
        }

        inventoryRepository.save(inv);

        List<Inventory> list = inventoryRepository.findByRun_Id(run.getId());
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }
    @Transactional
    public InventoryResponse getMineByRunId(HttpServletRequest req, Long runId) {
        Long uid = resolveUserId(req);

        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found: " + runId));

        if (!run.getUser().getId().equals(uid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your run");
        }

        List<Inventory> list = inventoryRepository.findByRun_Id(runId);
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }
    @Transactional
    public InventoryResponse updateItemByRunId(HttpServletRequest req, Long runId, Long itemId, InventoryUpdateRequest body) {
        Long uid = resolveUserId(req);

        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found: " + runId));

        // 소유자 & 진행중 검증 (진행중만 허용하지 않을 거면 status 체크 제거)
        if (!run.getUser().getId().equals(uid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your run");
        }
        if (run.getStatus() != Status.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Run is not IN_PROGRESS");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found: " + itemId));

        Inventory inv = inventoryRepository.findByRun_IdAndItem_Id(run.getId(), item.getId())
                .orElseGet(() -> Inventory.builder().run(run).item(item).itemCount(0).build());

        Integer reqCount = body.getCount(); // nullable
        int n = Math.max(0, (reqCount == null ? 0 : reqCount));
        String op = String.valueOf(body.getOperation()).toUpperCase();

        switch (op) {
            case "ADD" -> inv.addItemCount(Math.max(1, n));
            case "SET" -> inv.setItemCount(n);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operation must be ADD or SET");
        }

        inventoryRepository.save(inv);

        List<Inventory> list = inventoryRepository.findByRun_Id(run.getId());
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }
}
