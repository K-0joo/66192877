package com.skmnservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResoibseCode
 * - 도메인 별로 나누어 관리
 * - [동사_목적어_SUCCESS] 형태로 생성
 * */

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // User
    LOGIN_SUCCESS(200, "U001", "로그인에 성공하였습니다."),
    LOGIN_FAIL(200, "U004", "로그인에 실패하였습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
