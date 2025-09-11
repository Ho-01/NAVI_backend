package com.doljabi.Outdoor_Escape_Room.run.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.ClearedRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunsResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.LeaderboardResponse;
import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RunService {
    @Autowired
    private RunRepository runRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public LeaderboardResponse findLeaderBoard(Scenario scenario) {
        List<Run> topRuns = runRepository.findTop20ByStatusAndScenarioOrderByTotalPlayMsAsc(Status.CLEARED, scenario);
        return LeaderboardResponse.fromEntityList(topRuns, scenario);
    }

    @Transactional(readOnly = true)
    public InProgressRunsResponse findMyGames(Long userId) {
        List<Run> foundRuns = runRepository.findAllByUserIdAndStatus(userId, Status.IN_PROGRESS);
        return InProgressRunsResponse.fromEntityList(foundRuns);
    }

    @Transactional
    public InProgressRunResponse saveNewGame(Long userId, Scenario scenario) {
        if(runRepository.findByUserIdAndStatusAndScenario(userId, Status.IN_PROGRESS, scenario).isPresent()){
            throw new AppException(GlobalErrorCode.IN_PROGRESS_RUN_EXISTS);
        }
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.UNAUTHORIZED));
        return InProgressRunResponse.fromEntity(runRepository.save(new Run(user, scenario)));
    }

    @Transactional
    public ClearedRunResponse updateClearedRun(Long userId, Long runId) {
        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));
        if(run.getStatus()==Status.CLEARED){
            throw new AppException(GlobalErrorCode.INVALID_STATE_TRANSITION);
        }
        if(!userId.equals(run.getUser().getId())){
            throw new AppException(GlobalErrorCode.UNAUTHORIZED);
        }
        run.cleared();
        return ClearedRunResponse.fromEntity(run);
    }
}
