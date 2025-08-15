package com.doljabi.Outdoor_Escape_Room.runs.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.runs.domain.Run;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Status;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ClearedRunResponse {
    private Long id;
    private Status status;
    private String userName;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String totalPlayMsText;
    private int hintCount;

    public static ClearedRunResponse fromEntity(Run run) {
        Long ms = run.getTotalPlayMs();
        return new ClearedRunResponse(run.getId(),
                run.getStatus(),
                run.getUser().getName(),
                run.getStartedAt(),
                run.getEndedAt(),
                String.format("%02d:%02d:%02d.%03d",ms/3_600_000,(ms%3_600_000)/60_000,(ms%60_000)/1000,ms%1000),
                run.getHintCount());
    }
}
