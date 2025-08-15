package com.doljabi.Outdoor_Escape_Room.run.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.run.application.RunService;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.ClearedRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.LeaderboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class RunController {
    @Autowired
    private RunService runService;

    @GetMapping("/runs/leaderboard")
    public ApiResponse<LeaderboardResponse> getLeaderBoard(){
        return ApiResponse.success(runService.findLeaderBoard());
    }

    @GetMapping("/runs/in_progress")
    public ApiResponse<InProgressRunResponse> getMyGame(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(runService.findMyGame(userDetails.getUserId()));
    }

    @PostMapping("/runs")
    public ApiResponse<InProgressRunResponse> startNewGame(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(runService.saveNewGame(userDetails.getUserId()));
    }

    @PutMapping("/runs/{runId}")
    public ApiResponse<ClearedRunResponse> gameClear(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long runId
    ){
        return ApiResponse.success(runService.updateClearedRun(userDetails.getUserId(), runId));
    }
}
