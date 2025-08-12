package com.doljabi.Outdoor_Escape_Room.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private ErrorDetail error;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(data, null);
    }
    public static <T> ApiResponse<T> error(String code, String message){
        return new ApiResponse<>(null, new ErrorDetail(code, message));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorDetail{
        private final String code;
        private final String message;
    }
}
