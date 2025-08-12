package com.doljabi.Outdoor_Escape_Room.auth.application;

import com.doljabi.Outdoor_Escape_Room.auth.domain.RefreshToken;
import com.doljabi.Outdoor_Escape_Room.auth.domain.RefreshTokenRepository;
import com.doljabi.Outdoor_Escape_Room.auth.infra.GoogleTokenVerifier;
import com.doljabi.Outdoor_Escape_Room.auth.infra.KakaoTokenVerifier;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.LoginResponse;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.TokenResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.util.JwtTokenUtil;
import com.doljabi.Outdoor_Escape_Room.user.domain.Provider;
import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;
    @Autowired
    private KakaoTokenVerifier kakaoTokenVerifier;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public LoginResponse loginWithGoogle(String idToken) {
        String sub = googleTokenVerifier.verify(idToken);
        User user = userRepository.findByProviderAndExternalId(Provider.GOOGLE, sub)
                .orElseGet(()->{
                   return userRepository.save(new User(Provider.GOOGLE, sub));
                });

        String accessToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(new RefreshToken(user, refreshToken));

        return new LoginResponse(
                user.getProvider(),
                user.getName(),
                accessToken,
                refreshToken
        );

    }

    public LoginResponse loginWithKakao(String accessToken) {
        return new LoginResponse(Provider.KAKAO,"","","");
    }

    public TokenResponse reissueTokens(String refreshToken) {
        return new TokenResponse("","");
    }

    public String logout(String refreshToken) {
        return "";
    }
}
