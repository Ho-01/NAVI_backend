package com.doljabi.Outdoor_Escape_Room.problem.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemResponse {
    private Long id;
    private int number;

    public static ProblemResponse from(Problem p){
        return ProblemResponse.builder()
                .id(p.getId())
                .number(p.getNumber())
                .build();
    }
}
