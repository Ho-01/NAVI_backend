package com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class OpenedHintsResponse {
    private Long runId;
    private List<HintDetail> hints;

    @Getter
    @Builder
    public static class HintDetail {
        private Long problemId;
        private String hint;
    }
}
