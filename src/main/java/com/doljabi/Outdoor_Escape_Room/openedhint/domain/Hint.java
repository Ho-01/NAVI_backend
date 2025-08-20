package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hint",
        uniqueConstraints = @UniqueConstraint(columnNames = {"problem_id", "seq"})
)
public class Hint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "hint", nullable = false)
    private String hint;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Builder
    private Hint(Problem problem, String hint, int seq){
        this.problem = problem;
        this.hint = hint;
        this.seq = seq;
    }
}
