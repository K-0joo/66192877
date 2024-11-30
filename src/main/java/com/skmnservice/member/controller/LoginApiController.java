package com.skmnservice.member.controller;

import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.LoginRequest;
import com.skmnservice.member.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class LoginApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest requestDto){
        // 사용자 검증 로직 추가
        LoginResponse responseDto = userService.login(requestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.LOGIN_SUCCESS, responseDto));
    }
}
