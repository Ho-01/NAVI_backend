package com.doljabi.Outdoor_Escape_Room.run.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunRepository extends JpaRepository<Run, Long> {
    List<Run> findTop20ByStatusOrderByTotalPlayMsAsc(Status status);

    Optional<Run> findByUserIdAndStatus(Long userId, Status status);
}
