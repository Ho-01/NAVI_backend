package com.doljabi.Outdoor_Escape_Room.solvedproblem.application;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.problem.domain.ProblemRepository;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Run;
import com.doljabi.Outdoor_Escape_Room.runs.domain.RunRepository;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Status;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.domain.SolvedProblem;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.domain.SolvedProblemRepository;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.request.SolveProblemRequest;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response.SolveResultResponse;
import com.doljabi.Outdoor_Escape_Room.solvedproblem.presentation.dto.response.SolvedProblemsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolvedProblemService {
    @Autowired private RunRepository runRepository;
    @Autowired private ProblemRepository problemRepository;
    @Autowired private SolvedProblemRepository solvedProblemRepository;

    @Transactional(readOnly = true)
    public SolvedProblemsResponse getSolvedProblems(Long userId){
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS).orElse(null);
        if (run == null) return null;

        var list = solvedProblemRepository.findAllByRunId(run.getId()).stream()
                .map(sp -> SolvedProblemsResponse.ProblemIdOnly.builder()
                        .problemId(sp.getProblem().getId())
                        .build())
                .toList();

        return SolvedProblemsResponse.builder()
                .runId(run.getId())
                .problems(list)
                .build();
    }

    @Transactional
    public SolveResultResponse submitAnswer(Long userId, SolveProblemRequest req){
        Run run = runRepository.findByUserIdAndStatus(userId, Status.IN_PROGRESS)
                .orElseThrow(() -> new AppException(GlobalErrorCode.RUN_NOT_FOUND));

        Problem problem = problemRepository.findById(req.getProblemId())
                .orElseThrow(() -> new AppException(GlobalErrorCode.PROBLEM_NOT_FOUND));

        boolean correct = normalize(req.getAnswer()).equals(normalize(problem.getAnswer()));
        if (correct) {
            solvedProblemRepository.findByRunIdAndProblemId(run.getId(), problem.getId())
                    .orElseGet(() -> solvedProblemRepository.save(
                            SolvedProblem.builder()
                                    .run(run)
                                    .problem(problem)
                                    .build()
                    ));
        }

        return SolveResultResponse.builder()
                .runId(run.getId())
                .problemId(problem.getId())
                .result(correct ? "correct" : "wrong")
                .build();
    }

    private String normalize(String s){
        if (s == null) return "";
        return s.trim().toUpperCase();
    }
}
