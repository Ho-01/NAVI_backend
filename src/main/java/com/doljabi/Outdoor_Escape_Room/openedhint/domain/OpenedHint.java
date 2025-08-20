package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "opened_hint",
        uniqueConstraints = @UniqueConstraint(columnNames = {"run_id", "hint_id"})
)
public class OpenedHint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hint_id", nullable = false)
    private Hint hint;

    @Builder
    private OpenedHint(Run run, Hint hint){
        this.run = run;
        this.hint = hint;
    }
}
