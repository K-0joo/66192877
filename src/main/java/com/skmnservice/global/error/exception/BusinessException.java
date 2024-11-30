package com.skmnservice.global.error.exception;

import com.skmnservice.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public BusinessException(ErrorCode errorCode){
        this(errorCode, errorCode.getMessage());
    }
}
