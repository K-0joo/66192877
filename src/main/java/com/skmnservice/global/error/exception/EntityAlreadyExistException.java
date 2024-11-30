package com.skmnservice.global.error.exception;

import com.skmnservice.global.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException{
    public EntityAlreadyExistException(ErrorCode errorCode){
        super(errorCode);
    }
}
