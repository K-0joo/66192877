package com.skmnservice.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    private ErrorResponse(final ErrorCode code){
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(final ErrorCode code){
        return new ErrorResponse(code);
    }
}
