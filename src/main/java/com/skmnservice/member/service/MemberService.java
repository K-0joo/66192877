package com.skmnservice.member.service;

import com.skmnservice.global.error.ErrorCode;
import com.skmnservice.global.error.exception.InvalidInputException;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.member.dto.LoginRequest;
import com.skmnservice.member.dto.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public LoginResponse login(LoginRequest requestDto){
        // 사용자 조회(String 타입의 ID로 조회)
        Member member = memberRepository.findById(requestDto.id())
                .orElseThrow(()-> new NotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 비밀번호 검증
        if(!bCryptPasswordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new InvalidInputException(ErrorCode.ACCOUNT_MISMATCH);
        }

    }
}
