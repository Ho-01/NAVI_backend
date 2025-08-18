package com.doljabi.Outdoor_Escape_Room.openedhint.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.OpenedHint;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.OpenedHintRepository;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.request.UseHintRequest;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.OpenedHintsResponse;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.OpenedHintsResponse.HintDetail;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.UseHintResponse;
import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.problem.domain.ProblemRepository;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Run;
import com.doljabi.Outdoor_Escape_Room.runs.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpenedHintService {
    @Autowired private RunRepository runRepository;
    @Autowired private ProblemRepository problemRepository;
    @Autowired private OpenedHintRepository openedHintRepository;

    @Transactional(readOnly = true)
    public OpenedHintsResponse getOpenedHints(Long userId){
        // 진행 중 run 없으면 data:null
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS).orElse(null);
        if (run == null) return null;

        var hints = openedHintRepository.findAllByRunId(run.getId()).stream()
                .map(h -> HintDetail.builder()
                        .problemId(h.getProblem().getId())
                        .hint(h.getProblem().getHintText())
                        .build())
                .toList();

        return OpenedHintsResponse.builder()
                .runId(run.getId())
                .hints(hints)
                .build();
    }

    @Transactional
    public UseHintResponse useHint(Long userId, UseHintRequest req){
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));

        Problem problem = problemRepository.findById(req.getProblemId())
                .orElseThrow(() -> new AppException(GlobalErrorCode.PROBLEM_NOT_FOUND));

        OpenedHint opened = openedHintRepository.findByRunIdAndProblemId(run.getId(), problem.getId())
                .orElseGet(() -> openedHintRepository.save(
                        OpenedHint.builder()
                                .run(run)
                                .problem(problem)
                                .build()
                ));

        return UseHintResponse.builder()
                .runId(opened.getRun().getId())
                .problemId(opened.getProblem().getId())
                .hint(opened.getProblem().getHintText())
                .build();
    }
}
