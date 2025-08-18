package com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HintDetail {
    private Long problemId;
    private int seq;
    private String hint;
}
