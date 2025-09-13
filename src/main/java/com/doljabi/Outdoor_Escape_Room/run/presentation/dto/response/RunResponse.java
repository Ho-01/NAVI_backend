package com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RunResponse {
    private Long id;
    private Scenario scenario;
    private Status status;
    private String checkpoint;
    private int hintCount;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public static RunResponse fromEntity(Run run) {
        return new RunResponse(
                run.getId(),
                run.getScenario(),
                run.getStatus(),
                run.getCheckpoint(),
                run.getHintCount(),
                run.getStartedAt(),
                run.getEndedAt()
        );
    }

    public static List<RunResponse> fromEntityList(List<Run> runList) {
        List<RunResponse> runResponseList = new ArrayList<>();
        for(Run run : runList){
            runResponseList.add(RunResponse.fromEntity(run));
        }
        return runResponseList;
    }
}
