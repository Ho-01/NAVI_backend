package com.doljabi.Outdoor_Escape_Room.openedhint.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.openedhint.application.OpenedHintService;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.request.UseHintRequest;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.OpenedHintsResponse;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.UseHintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class OpenedHintController {
    @Autowired
    private OpenedHintService openedHintService;

    @GetMapping("/runs/in_progress/opened_hints")
    public ApiResponse<OpenedHintsResponse> getOpenedHints(@AuthenticationPrincipal CustomUserDetails user) {
        return ApiResponse.success(openedHintService.getOpenedHints(user.getUserId()));
    }

    @PostMapping("/runs/in_progress/opened_hints")
    public ApiResponse<UseHintResponse> useHint(@AuthenticationPrincipal CustomUserDetails user,@RequestBody UseHintRequest request) {
        return ApiResponse.success(openedHintService.useHint(user.getUserId(), request));
    }
}
