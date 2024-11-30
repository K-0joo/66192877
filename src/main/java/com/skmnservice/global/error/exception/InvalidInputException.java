package com.skmnservice.global.error.exception;

import com.skmnservice.global.error.ErrorCode;

public class InvalidInputException extends BusinessException{
    public InvalidInputException(){
        super(ErrorCode.INPUT_VALUE_INVALID);
    }
}
