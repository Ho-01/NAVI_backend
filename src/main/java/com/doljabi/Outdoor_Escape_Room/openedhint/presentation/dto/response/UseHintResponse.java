package com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UseHintResponse {
    private Long runId;
    private Boolean exhausted; // 해당 문제의 힌트를 모두 소모했는지 여부
    private List<HintDetail> hints;
}
