package com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UseHintResponse {
    private Long runId;
    private Long problemId;
    private String hint;
}
