package com.doljabi.Outdoor_Escape_Room.problem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // JPA 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE)       // 빌더 전용
@Builder
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private int number;       // 문제 번호

    @Column(name = "answer", nullable = false)
    private String answer;    // 정답
}
