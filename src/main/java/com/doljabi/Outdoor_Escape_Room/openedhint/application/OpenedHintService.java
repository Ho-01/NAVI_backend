package com.doljabi.Outdoor_Escape_Room.openedhint.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.Hint;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.HintRepository;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.OpenedHint;
import com.doljabi.Outdoor_Escape_Room.openedhint.domain.OpenedHintRepository;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.HintDetail;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.OpenedHintsResponse;
import com.doljabi.Outdoor_Escape_Room.openedhint.presentation.dto.response.UseHintResponse;
import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.problem.domain.ProblemRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import com.doljabi.Outdoor_Escape_Room.run.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.run.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class OpenedHintService {
    @Autowired
    private RunRepository runRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private OpenedHintRepository openedHintRepository;
    @Autowired
    private HintRepository hintRepository;

    @Transactional(readOnly = true)
    public OpenedHintsResponse getOpenedHints(Long userId){
        // 진행 중 run 없으면 data:null
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS).orElse(null);
        if (run == null) return null;

        // N+1 문제 방지를 위해 Hint와 Problem까지 fetch-join해서 가져옴
        List<OpenedHint> openedHints = openedHintRepository.findAllWithHintAndProblemByRunId(run.getId());

        var hints = openedHints.stream()
                .map(openedHint -> HintDetail.builder()
                        .problemId(openedHint.getHint().getProblem().getId())
                        .seq(openedHint.getHint().getSeq())
                        .hint(openedHint.getHint().getHint())
                        .build())
                .toList();

        return OpenedHintsResponse.builder()
                .runId(run.getId())
                .hints(hints)
                .build();
    }

    @Transactional
    public UseHintResponse useHint(Long userId, Long problemId){
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.PROBLEM_NOT_FOUND));

        List<Hint> unopenedHints = hintRepository.findUnopenedByRunAndProblemOrderBySeqAsc(run.getId(), problem.getId());
        Hint nextHint = unopenedHints.isEmpty()? null:unopenedHints.get(0);

        if(nextHint == null){ // 모든 힌트 사용한 상태, 해당 문제의 모든 힌트 재전송 (200 OK)
            List<Hint> all = hintRepository.findAllByProblemIdOrderBySeqAsc(problem.getId());

            List<HintDetail> hintDetails = all.stream()
                    .map(hint -> HintDetail.builder()
                            .problemId(problemId)
                            .seq(hint.getSeq())
                            .hint(hint.getHint())
                            .build())
                    .toList();

            return UseHintResponse.builder()
                    .runId(run.getId())
                    .exhausted(true)
                    .hints(hintDetails)
                    .build();
        }

        // 새 힌트 1개 오픈처리 후 반환
        openedHintRepository.save(
                OpenedHint.builder()
                        .run(run)
                        .hint(nextHint)
                        .build()
        );
        HintDetail hintDetail = HintDetail.builder()
                .problemId(problemId)
                .seq(nextHint.getSeq())
                .hint(nextHint.getHint())
                .build();

        return UseHintResponse.builder()
                .runId(run.getId())
                .exhausted(false)
                .hints(List.of(hintDetail))
                .build();
    }
}
