package com.doljabi.Outdoor_Escape_Room.auth.application;

import com.doljabi.Outdoor_Escape_Room.auth.domain.RefreshToken;
import com.doljabi.Outdoor_Escape_Room.auth.domain.RefreshTokenRepository;
import com.doljabi.Outdoor_Escape_Room.auth.infra.GoogleTokenVerifier;
import com.doljabi.Outdoor_Escape_Room.auth.infra.KakaoTokenVerifier;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.LoginResponse;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.TokenResponse;
import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.common.security.util.JwtTokenUtil;
import com.doljabi.Outdoor_Escape_Room.user.domain.Provider;
import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public LoginResponse createGuest() {
        User user = userRepository.save(new User(null, null));
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());
        refreshTokenRepository.save(new RefreshToken(user, refreshToken));
        return new LoginResponse(null, "guest", accessToken, refreshToken);
    }

    @Transactional
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

        return new LoginResponse(user.getProvider(), user.getName(), accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse loginWithKakao(String accessToken) {
        return new LoginResponse(Provider.KAKAO,"","","");
    }

    @Transactional
    public LoginResponse linkWithGoogle(Long userId, String idToken) {
        String sub = googleTokenVerifier.verify(idToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.UNAUTHORIZED));
        if(user.getProvider()!=null){
            throw new AppException(GlobalErrorCode.MULTIPLE_PROVIDER_NOT_ALLOWED);
        }

        userRepository.findByProviderAndExternalId(Provider.GOOGLE, sub)
                        .filter(foundUser -> !foundUser.getId().equals(userId))
                        .ifPresent(foundUser -> {
                            throw new AppException(GlobalErrorCode.ACCOUNT_LINK_CONFLICT);
                        });

        user.link(Provider.GOOGLE, sub);

        String accessToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(new RefreshToken(user, refreshToken));

        return new LoginResponse(user.getProvider(), user.getName(), accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse linkWithKakao(Long userId, String kakaoAccessToken) {
        return new LoginResponse(Provider.KAKAO, "", "", "");
    }

    @Transactional
    public TokenResponse reissueTokens(String refreshToken) {
        jwtTokenUtil.validateRefreshToken(refreshToken);
        Long userId = jwtTokenUtil.extractUserId(refreshToken);

        RefreshToken stored = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(GlobalErrorCode.INVALID_REFRESH_TOKEN));

        if(!refreshToken.equals(stored.getRefreshToken())){
            refreshTokenRepository.deleteByUserId(userId);
            throw new AppException(GlobalErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtTokenUtil.generateAccessToken(userId);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(userId);

        stored.update(newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public String logout(String refreshToken) {
        try{
            jwtTokenUtil.validateRefreshToken(refreshToken);
            Long userId = jwtTokenUtil.extractUserId(refreshToken);

            long deleted = refreshTokenRepository.deleteByUserIdAndRefreshToken(userId, refreshToken);
            if(deleted == 0){
                refreshTokenRepository.deleteByUserId(userId);
            }
        }catch(Exception ignore){
            // 항상 성공으로 응답하여 상태 은닉
        }
        return "로그아웃 성공";
    }
}
