package com.doljabi.Outdoor_Escape_Room.run.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.run.application.RunService;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.request.RunRequest;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.request.UpdateCheckpointRequest;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.ClearedRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunsResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.LeaderboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class RunController {
    @Autowired
    private RunService runService;

    @PostMapping("/runs")
    public ApiResponse<InProgressRunResponse> startNewGame(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody RunRequest request
    ){
        return ApiResponse.success(runService.saveNewGame(userDetails.getUserId(), request.getScenario()));
    }

    @GetMapping("/runs/{scenario}/leaderboard")
    public ApiResponse<LeaderboardResponse> getLeaderBoard(@PathVariable Scenario scenario){
        return ApiResponse.success(runService.findLeaderBoard(scenario));
    }

    @GetMapping("/runs/in_progress")
    public ApiResponse<InProgressRunsResponse> getMyGames(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(runService.findMyGames(userDetails.getUserId()));
    }

    @PutMapping("/runs/{runId}/checkpoint")
    public ApiResponse<InProgressRunResponse> updateCheckpoint(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long runId,
            @RequestBody UpdateCheckpointRequest request
            ){
        return ApiResponse.success(runService.updateCheckpoint(userDetails.getUserId(), runId, request.getCheckpoint()));
    }

    @PutMapping("/runs/{runId}")
    public ApiResponse<ClearedRunResponse> gameClear(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long runId
    ){
        return ApiResponse.success(runService.updateClearedRun(userDetails.getUserId(), runId));
    }
}
