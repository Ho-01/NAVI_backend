package com.doljabi.Outdoor_Escape_Room.inventory.application;

import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.Inventory;
import com.doljabi.Outdoor_Escape_Room.inventory.domain.InventoryRepository;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.request.InventoryUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.InventoryResponse;
import com.doljabi.Outdoor_Escape_Room.inventory.presentation.dto.response.ItemCountResponse;
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

    @Transactional(readOnly = true)
    public InventoryResponse getMine(HttpServletRequest req) {
        Long uid = resolveUserId(req);
        Long runId = getLatestInProgressRun(uid).getId();
        List<Inventory> list = inventoryRepository.findByRunIdAndUserId(runId, uid);
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }

    @Transactional
    public InventoryResponse updateItem(HttpServletRequest req, Long pathItemId, InventoryUpdateRequest body) {
        Long uid = resolveUserId(req);
        Run run  = getLatestInProgressRun(uid);

        itemRepository.findById(pathItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found: " + pathItemId));

        String op = (body.getOperation() == null) ? "" : body.getOperation().name();
        op = op.toUpperCase();
        if (!op.equals("ADD") && !op.equals("SET")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operation must be ADD or SET");
        }

        int n      = (body.getCount()==null)? 0 : body.getCount();

        int val    = op.equals("ADD") ? Math.max(1, n) : n;
        int isAdd  = op.equals("ADD") ? 1 : 0;

        inventoryRepository.upsert(run.getId(), pathItemId, uid, val, isAdd);

        List<Inventory> list = inventoryRepository.findByRun_Id(run.getId());
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }


    @Transactional(readOnly = true)
    public InventoryResponse getMineByRunId(HttpServletRequest req, Long runId) {
        Long uid = resolveUserId(req);
        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found: " + runId));
        if (!run.getUser().getId().equals(uid))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your run");

        List<Inventory> list = inventoryRepository.findByRunIdAndUserId(runId, uid);
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }
    @Transactional
    public InventoryResponse updateItemByRunId(HttpServletRequest req, Long runId, Long itemId, InventoryUpdateRequest body) {
        Long uid = resolveUserId(req);

        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found: " + runId));

        if (!run.getUser().getId().equals(uid)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your run");
        if (run.getStatus() != Status.IN_PROGRESS) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Run is not IN_PROGRESS");

        itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found: " + itemId));

        String op = (body.getOperation() == null) ? "" : body.getOperation().name();
        op = op.toUpperCase();
        if (!op.equals("ADD") && !op.equals("SET")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operation must be ADD or SET");
        }

        int n      = (body.getCount()==null)? 0 : body.getCount();
        int val    = op.equals("ADD") ? Math.max(1, n) : n;
        int isAdd  = op.equals("ADD") ? 1 : 0;

        inventoryRepository.upsert(runId, itemId, uid, val, isAdd);

        List<Inventory> list = inventoryRepository.findByRun_Id(runId);
        return InventoryResponse.from(ItemCountResponse.fromEntityList(list));
    }
    }
