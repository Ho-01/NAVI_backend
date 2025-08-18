package com.doljabi.Outdoor_Escape_Room.common.error;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalErrorCode {
    INVALID_ID_TOKEN("INVALID_ID_TOKEN", "구글 ID 토큰 검증에 실패했습니다", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_ACCESS_TOKEN("INVALID_ACCESS_TOKEN", "카카오 액세스 토큰 검증에 실패했습니다", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", "리프레시 토큰이 유효하지 않거나 만료되었습니다. 다시 로그인하세요", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN_FORMAT("INVALID_REFRESH_TOKEN_FORMAT", "리프레시 토큰 형식이 올바르지 않습니다", HttpStatus.UNPROCESSABLE_ENTITY),
    UNAUTHORIZED("UNAUTHORIZED", "유효한 액세스 토큰이 필요합니다", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    IN_PROGRESS_RUN_EXISTS("IN_PROGRESS_RUN_EXISTS", "이미 진행 중인 게임이 존재합니다", HttpStatus.CONFLICT),
    RUN_NOT_FOUND("RUN_NOT_FOUND", "요청한 게임을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    INVALID_STATE_TRANSITION("INVALID_STATE_TRANSITION", "이미 CLEARED 상태입니다", HttpStatus.CONFLICT),
    INVALID_STATE("INVALID_STATE", "진행중인 게임이 없거나, 존재하지 않는 아이템이거나, 결과 수량이 음수가 됩니다", HttpStatus.CONFLICT),
    PROBLEM_NOT_FOUND("PROBLEM_NOT_FOUND","문제를 찾을 수 없습니다",HttpStatus.NOT_FOUND);

    public final String code;
    public final String message;
    public final HttpStatus httpStatus;
}
