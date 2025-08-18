package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HintRepository extends JpaRepository<Hint, Long> {
    @Query(
            "select h from Hint h " +
                    "where h.problem.id = :problemId " +
                    "  and not exists (" +
                    "       select 1 from OpenedHint oh " +
                    "       where oh.run.id = :runId " +
                    "         and oh.hint.id = h.id" +
                    "  ) " +
                    "order by h.seq asc"
    ) // 해당 run에서 아직 안 연 힌트만 seq 오름차순
    List<Hint> findUnopenedByRunAndProblemOrderBySeqAsc(@Param("runId")Long runId, @Param("problemId")Long problemId);

    @Query(""" 
      select h from Hint h
      where h.problem.id = :problemId
      order by h.seq asc
    """) // 문제의 모든 힌트 seq 오름차순
    List<Hint> findAllByProblemIdOrderBySeqAsc(Long problemId);
}
