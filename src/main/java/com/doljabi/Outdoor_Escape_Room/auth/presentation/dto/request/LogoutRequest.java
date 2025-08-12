package com.doljabi.Outdoor_Escape_Room.auth.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutRequest {
    private String refreshToken;
}
