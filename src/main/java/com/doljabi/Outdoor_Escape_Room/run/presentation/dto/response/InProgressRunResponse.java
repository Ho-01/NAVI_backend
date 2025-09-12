package com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InProgressRunResponse {
    private Long id;
    private Scenario scenario;
    private Status status;
    private String checkpoint;
    private int hintCount;
    private LocalDateTime startedAt;

    public static InProgressRunResponse fromEntity(Run run) {
        return new InProgressRunResponse(
                run.getId(),
                run.getScenario(),
                run.getStatus(),
                run.getCheckpoint(),
                run.getHintCount(),
                run.getStartedAt()
        );
    }
}
