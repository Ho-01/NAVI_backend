package com.doljabi.Outdoor_Escape_Room.openedhint.domain;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.runs.domain.Run;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OpenedHint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) private Run run;
    @ManyToOne(fetch = FetchType.LAZY) private Problem problem;
}
