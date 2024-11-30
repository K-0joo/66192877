package com.skmnservice.global.error.exception;

import com.skmnservice.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
}
