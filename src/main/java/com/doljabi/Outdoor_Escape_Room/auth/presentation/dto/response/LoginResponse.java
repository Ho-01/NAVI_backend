package com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.user.domain.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Provider provider;
    private String userName;
    private String accessToken;
    private String refreshToken;
}
