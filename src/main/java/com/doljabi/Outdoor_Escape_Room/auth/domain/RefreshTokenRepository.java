package com.doljabi.Outdoor_Escape_Room.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUserId(Long userId);

    Optional<RefreshToken> findByUserId(Long userId);

    long deleteByUserIdAndRefreshToken(Long userId, String refreshToken);
}
