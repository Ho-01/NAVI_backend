package com.doljabi.Outdoor_Escape_Room.problem.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.problem.application.ProblemService;
import com.doljabi.Outdoor_Escape_Room.problem.presentation.dto.response.ProblemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @GetMapping("/problems")
    public ApiResponse<List<ProblemResponse>> getProblems(){
        return ApiResponse.success(problemService.getAllOrderedResponses());
    }

}

