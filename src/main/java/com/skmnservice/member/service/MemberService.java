package com.skmnservice.member.service;

import com.skmnservice.global.error.ErrorCode;
import com.skmnservice.global.error.exception.EntityAlreadyExistException;
import com.skmnservice.global.error.exception.InvalidInputException;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.member.dto.LoginRequest;
import com.skmnservice.member.dto.LoginResponse;
import com.skmnservice.member.dto.RegisterRequest;
import com.skmnservice.member.dto.RegisterResponse;
import com.skmnservice.member.entity.Member;
import com.skmnservice.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public RegisterResponse register(RegisterRequest requestDto){
        // ID 중복 체크
        if(memberRepository.findById(requestDto.id()).isPresent()){
            throw new EntityAlreadyExistException(ErrorCode.USER_ALREADY_EXIST);
        }

        // 회원 생성
        Member member = Member.builder()
                .id(requestDto.id())
                .password(bCryptPasswordEncoder.encode(requestDto.password()))
                .name(requestDto.name())
                .regTime(LocalDateTime.now())
                .build();

        // 저장
        Member savedMember = memberRepository.save(member);

        // 응답 DTO 생성
        return new RegisterResponse(savedMember.getMemberId(), savedMember.getId(), savedMember.getName());
    }

    @Transactional
    public LoginResponse login(LoginRequest requestDto){
        // 사용자 조회(String 타입의 ID로 조회)
        Member member = memberRepository.findById(requestDto.id())
                .orElseThrow(()-> new NotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 비밀번호 검증
        if(!bCryptPasswordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new InvalidInputException(ErrorCode.ACCOUNT_MISMATCH);
        }

        return new LoginResponse(member.getMemberId(), member.getId());
    }
}
