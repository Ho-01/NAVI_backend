package com.doljabi.Outdoor_Escape_Room.common.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {
    private final SecretKey secretKey;
    public JwtTokenUtil(@Value("${secretKey}") String secret){
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public final long ACCESS_TOKEN_EXPIRATION = 1000*60*20; // 20분
    public final long REFRESH_TOKEN_EXPIRATION = 1000*60*60*24*7; // 1주일

    public String generateAccessToken(Long userId){
        return Jwts.builder()
                .claim("type", "access")
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Long userId){
        return Jwts.builder()
                .claim("type", "refresh")
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+REFRESH_TOKEN_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String jwtToken){
        return Long.parseLong(getClaims(jwtToken).getSubject());
    }

    public void validateAccessToken(String jwtToken){
        String type = getClaims(jwtToken).get("type", String.class);
        if(!type.equals("access")){
            throw new IllegalArgumentException("accessToken이 아님");
        }
        if(getClaims(jwtToken).getExpiration().before(new Date())){
            throw new IllegalArgumentException("만료된 토큰입니다");
        }
    }

    public void validateRefreshToken(String jwtToken){
        String type = getClaims(jwtToken).get("type", String.class);
        if(!type.equals("refresh")){
            throw new IllegalArgumentException("refreshToken이 아님");
        }
        if(getClaims(jwtToken).getExpiration().before(new Date())){
            throw new IllegalArgumentException("만료된 토큰입니다");
        }
    }

    private Claims getClaims(String jwtToken){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken).getPayload();
    }
}
