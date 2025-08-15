package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OpenedHintRepository extends JpaRepository<OpenedHint, Long> {
    Optional<OpenedHint> findByRunIdAndProblemId(Long runId, Long problemId);
    List<OpenedHint> findAllByRunId(Long runId);
    long countByRunId(Long runId);
}
