package com.doljabi.Outdoor_Escape_Room.common.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalErrorCode {
    INVALID_ID_TOKEN("INVALID_ID_TOKEN", "구글 ID 토큰 검증에 실패했습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_ACCESS_TOKEN("INVALID_ACCESS_TOKEN", "카카오 액세스 토큰 검증에 실패했습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", "리프레시 토큰이 유효하지 않거나 만료되었습니다. 다시 로그인하세요.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN_FORMAT("INVALID_REFRESH_TOKEN_FORMAT", "리프레시 토큰 형식이 올바르지 않습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    UNAUTHORIZED("UNAUTHORIZED", "유효한 액세스 토큰이 필요합니다.", HttpStatus.UNAUTHORIZED);

    public final String code;
    public final String message;
    public final HttpStatus httpStatus;
}
