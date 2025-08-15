package com.doljabi.Outdoor_Escape_Room.problem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private int number;       // 문제 번호
    private String hintText;  // 힌트(현 정책상 255자 이내)
    private String answer;    // 정답
}
