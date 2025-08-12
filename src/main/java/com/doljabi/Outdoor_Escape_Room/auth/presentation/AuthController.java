package com.doljabi.Outdoor_Escape_Room.auth.presentation;

import com.doljabi.Outdoor_Escape_Room.auth.application.AuthService;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.request.GoogleLoginRequest;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.request.KakaoLoginRequest;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.request.LogoutRequest;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.request.TokenReissueRequest;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.LoginResponse;
import com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response.TokenResponse;
import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/google/login")
    public ApiResponse<LoginResponse> loginWithGoogle(@RequestBody GoogleLoginRequest request){
        return ApiResponse.success(authService.loginWithGoogle(request.getIdToken()));
    }

    @PostMapping("/auth/kakao/login")
    public ApiResponse<LoginResponse> loginWithKakao(@RequestBody KakaoLoginRequest request){
        return ApiResponse.success(authService.loginWithKakao(request.getAccessToken()));
    }

    @PostMapping("/auth/refresh")
    public ApiResponse<TokenResponse> refreshTokens(@RequestBody TokenReissueRequest request){
        return ApiResponse.success(authService.reissueTokens(request.getRefreshToken()));
    }

    @PostMapping("/auth/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest request){
        return ApiResponse.success(authService.logout(request.getRefreshToken()));
    }
}
