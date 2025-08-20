package com.doljabi.Outdoor_Escape_Room.solvedproblem.domain;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "solved_problem",
        uniqueConstraints = @UniqueConstraint(columnNames = {"run_id", "problem_id"})
)
public class SolvedProblem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Builder
    private SolvedProblem(Run run, Problem problem){
        this.run = run;
        this.problem = problem;
    }
}
