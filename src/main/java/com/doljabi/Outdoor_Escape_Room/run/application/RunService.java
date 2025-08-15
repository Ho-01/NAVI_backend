package com.doljabi.Outdoor_Escape_Room.run.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.ClearedRunResponse;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.InProgressRunResponse;
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
    public LeaderboardResponse findLeaderBoard() {
        List<Run> topRuns = runRepository.findTop20ByStatusOrderByTotalPlayMsAsc(Status.CLEARED);
        return LeaderboardResponse.fromEntityList(topRuns);
    }

    @Transactional(readOnly = true)
    public InProgressRunResponse findMyGame(Long userId) {
        return runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .map(InProgressRunResponse::fromEntity)
                .orElse(null);
    }

    @Transactional
    public InProgressRunResponse saveNewGame(Long userId) {
        if(runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS).isPresent()){
            throw new AppException(GlobalErrorCode.IN_PROGRESS_RUN_EXISTS);
        }
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.UNAUTHORIZED));
        return InProgressRunResponse.fromEntity(runRepository.save(new Run(user)));
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
