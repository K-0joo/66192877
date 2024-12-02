package com.skmnservice.member.controller;

import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.RegisterRequest;
import com.skmnservice.member.dto.RegisterResponse;
import com.skmnservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/member")
public class RegisterApiController {
    private final MemberService memberService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterRequest requestDto){
        RegisterResponse responseDto = memberService.register(requestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.REGISTER_SUCCESS, responseDto));
    }
}
