package com.skmnservice.member.controller;

import com.skmnservice.global.response.ResponseCode;
import com.skmnservice.global.response.ResponseDto;
import com.skmnservice.member.dto.LoginRequest;
import com.skmnservice.member.dto.LoginResponse;
import com.skmnservice.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// URL 연결 요청(@GetMapping())과 동시에 자동으로 임포트
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/member")
public class LoginApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest requestDto) {
        LoginResponse responseDto = memberService.login(requestDto);

        // SecurityContext에 인증 정보 설정
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(responseDto.id(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(ResponseDto.of(ResponseCode.LOGIN_SUCCESS, responseDto));
    }


    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "html/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/api/member/login";
    }
}
