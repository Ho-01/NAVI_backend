package com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class LeaderboardResponse {
    private List<ClearInfoResponse> leaderboard;
    private Scenario scenario;
    private int total;

    public static LeaderboardResponse fromEntityList(List<Run> topRuns, Scenario scenario) {
        List<ClearInfoResponse> clearInfoResponseList = new ArrayList<>();
        Long rank = 1L;
        for(Run run : topRuns){
            long ms = run.getTotalPlayMs();
            clearInfoResponseList.add(
                    new ClearInfoResponse(rank,
                            run.getId(),
                            run.getUser().getName(),
                            String.format("%02d:%02d:%02d.%03d",ms/3_600_000,(ms%3_600_000)/60_000,(ms%60_000)/1000,ms%1000),
                            run.getHintCount(),
                            run.getEndedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    ));
            rank++;
        }
        return new LeaderboardResponse(clearInfoResponseList, scenario, clearInfoResponseList.size());
    }
}
