package com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolveResultResponse {
    private Long runId;
    private Long problemId;
    private String result; // "correct" | "wrong"
}
