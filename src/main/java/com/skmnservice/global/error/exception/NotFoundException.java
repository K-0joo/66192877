package com.skmnservice.global.error.exception;

import com.skmnservice.global.error.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
}
