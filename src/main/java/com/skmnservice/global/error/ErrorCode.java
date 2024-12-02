package com.skmnservice.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode Convention
 * - 도메인 별로 나누어 관리
 * - [주체_이유] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Global
    INTERNAL_SERVER_ERROR(400, "G001", "내부 서버 오류입니다."),
    INPUT_VALUE_INVALID(400, "G002", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G003", "입력 타입이 유효하지 않습니다."),
    TIME_FORMAT_INVALID(400, "G004", "날짜, 시간 타입 형식이 유효하지 않습니다."),

    // User
    ACCOUNT_NOT_FOUND(400, "U001", "존재하지 않는 아이디입니다."),
    ACCOUNT_ALEREADY_EXIST(400, "U002", "이미 존재하는 사용자 계정입니다."),
    AUTHENTICATION_FAIL(401, "U003", "로그인이 필요한 화면입니다"),
    AUTHORITY_INVALID(403, "U004", "권한이 없습니다."),
    ACCOUNT_MISMATCH(401, "U005", "계정 정보가 일치하지 않습니다."),
    USER_ALREADY_EXIST(400, "U007", "이미 가입된 유저입니다."),

    // Board
    BOARD_NOT_FOUND(400, "B001", "존재하지 않는 게시물입니다."),

    ;

    private final int status;
    private final String code;
    private final String message;
}
