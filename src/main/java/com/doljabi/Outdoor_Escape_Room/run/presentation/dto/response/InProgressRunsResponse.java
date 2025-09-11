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
public class InProgressRunsResponse {
    private List<InProgressRunResponse> runs;

    public static InProgressRunsResponse fromEntityList(List<Run> runList) {
        List<InProgressRunResponse> runs = new ArrayList<>();
        for(Run run : runList){
            runs.add(InProgressRunResponse.fromEntity(run));
        }
        return new InProgressRunsResponse(runs);
    }
}
