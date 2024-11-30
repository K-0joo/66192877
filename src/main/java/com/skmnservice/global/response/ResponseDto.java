package com.skmnservice.global.response;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ResponseDto {
    private int status;

    private String code;

    private String message;

    private Object data;

    public ResponseDto(ResponseCode responseCode, Object data) {
        this.status = responseCode.getStatus();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    // 전송할 데이터가 있는 경우
    public static ResponseDto of(ResponseCode responseCode, Object data) {
        return new ResponseDto(responseCode, data);
    }

    // 전송할 데이터가 없는 경우
    public static ResponseDto of(ResponseCode responseCode) {
        return new ResponseDto(responseCode, "");
    }
}
