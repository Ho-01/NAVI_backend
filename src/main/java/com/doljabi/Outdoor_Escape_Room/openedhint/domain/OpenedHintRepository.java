package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OpenedHintRepository extends JpaRepository<OpenedHint, Long> {
    @Query("""
      select oh from OpenedHint oh
      join fetch oh.hint h
      join fetch h.problem p
      where oh.run.id = :runId
      order by p.id asc, h.seq asc
    """)
    List<OpenedHint> findAllWithHintAndProblemByRunId(Long id);
}
