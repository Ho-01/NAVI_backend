package com.doljabi.Outdoor_Escape_Room.runs.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.runs.domain.Run;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Status;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class InProgressRunResponse {
    private Long id;
    private Status status;
    private int hintCount;
    private LocalDateTime startedAt;

    public static InProgressRunResponse fromEntity(Run run) {
        return new InProgressRunResponse(run.getId(), run.getStatus(), run.getHintCount(), run.getStartedAt());
    }
}
