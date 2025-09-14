package com.doljabi.Outdoor_Escape_Room.run.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.inventory.application.InventoryService;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import com.doljabi.Outdoor_Escape_Room.run.presentation.dto.response.RunResponse;
import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RunService {
    @Autowired
    private RunRepository runRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryService inventoryService;
    @Transactional
    public RunResponse saveNewGame(Long userId, Scenario scenario) {
        if(runRepository.findByUserIdAndStatusAndScenario(userId, Status.IN_PROGRESS, scenario).isPresent()){
            throw new AppException(GlobalErrorCode.IN_PROGRESS_RUN_EXISTS);
        }
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.UNAUTHORIZED));
        return RunResponse.fromEntity(runRepository.save(new Run(user, scenario)));
    }

    @Transactional(readOnly = true)
    public List<RunResponse> findMyGames(Long userId) {
        List<Run> foundRuns = runRepository.findAllByUserIdAndStatus(userId, Status.IN_PROGRESS);
        return RunResponse.fromEntityList(foundRuns);
    }

    @Transactional(readOnly = true)
    public List<RunResponse> findMyClearedGames(Long userId) {
        List<Run> clearedRuns = runRepository.findAllByUserIdAndStatus(userId, Status.CLEARED);

        if (clearedRuns.isEmpty()) return RunResponse.fromEntityList(Collections.emptyList());

        // "최초 클리어" 비교 기준 (필드명 상황에 맞게 교체)
        Comparator<Run> byFirstClearTime = Comparator.comparing(
                Run::getEndedAt,
                Comparator.nullsLast(Comparator.naturalOrder())
        );
        // 시나리오별로 가장 이른 1개만 남김
        Map<Scenario, Run> earliestByScenario = clearedRuns.stream()
                .collect(Collectors.toMap(
                        Run::getScenario,
                        Function.identity(),
                        (a, b) -> byFirstClearTime.compare(a, b) <= 0 ? a : b
                ));
        // 시나리오 정의 순서를 유지하며 리스트화(없는 시나리오는 제외)
        List<Run> result = Arrays.stream(Scenario.values())
                .map(earliestByScenario::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return RunResponse.fromEntityList(result);
    }

    @Transactional
    public RunResponse updateCheckpoint(Long userId, Long runId, String checkpoint) {
        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));
        if(!userId.equals(run.getUser().getId())){
            throw new AppException(GlobalErrorCode.UNAUTHORIZED);
        }
        run.updateCheckpoint(checkpoint);
        return RunResponse.fromEntity(run);
    }

    @Transactional
    public RunResponse updateClearedRun(Long userId, Long runId) {
        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));
        if(run.getStatus()==Status.CLEARED){
            throw new AppException(GlobalErrorCode.INVALID_STATE_TRANSITION);
        }
        if(!userId.equals(run.getUser().getId())){
            throw new AppException(GlobalErrorCode.UNAUTHORIZED);
        }
        run.cleared();
        return RunResponse.fromEntity(run);
    }
}
