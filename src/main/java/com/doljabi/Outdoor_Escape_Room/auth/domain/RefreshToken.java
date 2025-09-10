package com.doljabi.Outdoor_Escape_Room.auth.domain;

import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Builder
    public RefreshToken(User user, String refreshToken){
        this.user = user;
        this.refreshToken = refreshToken;
    }

    public void update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
    }
}
