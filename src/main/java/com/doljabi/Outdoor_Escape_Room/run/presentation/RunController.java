package com.doljabi.Outdoor_Escape_Room.run.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.run.application.RunService;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.request.RunRequest;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.request.UpdateCheckpointRequest;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.RunResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunController {
    @Autowired
    private RunService runService;

    @PostMapping("/runs")
    public ApiResponse<RunResponse> startNewGame(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody RunRequest request
    ){
        return ApiResponse.success(runService.saveNewGame(userDetails.getUserId(), request.getScenario()));
    }

    @GetMapping("/runs/in_progress")
    public ApiResponse<List<RunResponse>> getMyGames(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(runService.findMyGames(userDetails.getUserId()));
    }
    @GetMapping("/runs/cleared")
    public ApiResponse<List<RunResponse>> getMyClearedGames(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(runService.findMyClearedGames(userDetails.getUserId()));
    }

    @PutMapping("/runs/{runId}/checkpoint")
    public ApiResponse<RunResponse> updateCheckpoint(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long runId,
            @RequestBody UpdateCheckpointRequest request
            ){
        return ApiResponse.success(runService.updateCheckpoint(userDetails.getUserId(), runId, request.getCheckpoint()));
    }

    @PutMapping("/runs/{runId}")
    public ApiResponse<RunResponse> gameClear(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long runId
    ){
        return ApiResponse.success(runService.updateClearedRun(userDetails.getUserId(), runId));
    }
}
