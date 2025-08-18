package com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class SolvedProblemsResponse {
    private Long runId;
    private List<ProblemIdOnly> problems;

    @Getter
    @Builder
    public static class ProblemIdOnly {
        private Long problemId;
    }
}
