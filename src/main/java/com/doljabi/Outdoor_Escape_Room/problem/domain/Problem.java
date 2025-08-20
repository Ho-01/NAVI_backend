package com.doljabi.Outdoor_Escape_Room.problem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private int number;       // 문제 번호

    @Column(name = "answer", nullable = false)
    private String answer;    // 정답

    @Builder
    private Problem(int number, String answer){
        this.number = number;
        this.answer = answer;
    }
}
