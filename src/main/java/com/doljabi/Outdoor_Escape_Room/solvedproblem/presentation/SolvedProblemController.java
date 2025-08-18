package com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.application.SolvedProblemService;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.request.SolveProblemRequest;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response.SolveResultResponse;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response.SolvedProblemsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class SolvedProblemController {
    @Autowired
    private SolvedProblemService solvedProblemService;

    @GetMapping("/runs/in_progress/solved_problems")
    public ApiResponse<SolvedProblemsResponse> getSolvedProblems(@AuthenticationPrincipal CustomUserDetails user){
        return ApiResponse.success(solvedProblemService.getSolvedProblems(user.getUserId()));
    }

    @PostMapping("/runs/in_progress/solved_problems")
    public ApiResponse<SolveResultResponse> solveProblem(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody SolveProblemRequest request){
        return ApiResponse.success(solvedProblemService.submitAnswer(user.getUserId(), request.getProblemId(), request.getAnswer()));
    }
}
