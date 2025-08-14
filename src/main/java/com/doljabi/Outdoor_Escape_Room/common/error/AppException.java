package com.doljabi.Outdoor_Escape_Room.common.error;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private final GlobalErrorCode errorCode;
    public AppException(GlobalErrorCode errorCode){
        super(errorCode.code);
        this.errorCode = errorCode;
    }
}
