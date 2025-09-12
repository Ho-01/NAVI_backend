package com.doljabi.Outdoor_Escape_Room.run.domain;

import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Run {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "scenario", nullable = false)
    private Scenario scenario;

    @Column(name = "started_at", nullable = false, columnDefinition = "DATETIME(3)")
    private LocalDateTime startedAt;

    @Column(name = "ended_at", columnDefinition = "DATETIME(3)")
    private LocalDateTime endedAt;

    @Column(name = "total_play_ms", nullable = false)
    private long totalPlayMs;

    @Column(name = "hint_count", nullable = false)
    private int hintCount;

    //opening, problem1~13, ending
    @Column(name = "checkpoint")
    private String checkpoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Builder
    public Run(User user, Scenario scenario){
        this.user = user;
        this.scenario = scenario;
        this.checkpoint = "opening";
        this.startedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.totalPlayMs = 0L;
        this.hintCount = 0;
        this.status = Status.IN_PROGRESS;
    }

    public void cleared() {
        this.endedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.totalPlayMs = Duration.between(this.startedAt, this.endedAt).toMillis();
        this.status = Status.CLEARED;
    }

    public void updateCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }
}
