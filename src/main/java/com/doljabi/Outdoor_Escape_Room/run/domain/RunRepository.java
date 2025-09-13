package com.doljabi.Outdoor_Escape_Room.run.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunRepository extends JpaRepository<Run, Long> {
    Optional<Run> findByUserIdAndStatus(Long userId, Status status);

    Optional<Run> findByUserIdAndStatusAndScenario(Long userId, Status status, Scenario scenario);

    List<Run> findAllByUserIdAndStatus(Long userId, Status status);
}
