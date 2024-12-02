package com.skmnservice.member.controller;

import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.LoginRequest;
import com.skmnservice.member.dto.LoginResponse;
import com.skmnservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// URL 연결 요청(@GetMapping())과 동시에 자동으로 임포트
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/member")
public class LoginApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest requestDto){
        // 사용자 검증 로직 추가
        LoginResponse responseDto = memberService.login(requestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.LOGIN_SUCCESS, responseDto));
    }

    @GetMapping("/login")
    public String login(Model model){
        return "html/login";
    }
}
