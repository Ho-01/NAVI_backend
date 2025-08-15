package com.doljabi.Outdoor_Escape_Room.problem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByOrderByNumberAsc();
}
