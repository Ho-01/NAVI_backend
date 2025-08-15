package com.doljabi.Outdoor_Escape_Room.solvedproblem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, Long> {
    Optional<SolvedProblem> findByRunIdAndProblemId(Long runId, Long problemId);
    List<SolvedProblem> findAllByRunId(Long runId);
}
