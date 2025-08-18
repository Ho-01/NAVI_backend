package com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearInfoResponse {
    private Long rank;
    private Long runId;
    private String userName;
    private String totalPlayMsText;
    private int hintCount;
    private String clearedDate;
}
