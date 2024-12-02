package com.skmnservice.member.service;

import com.skmnservice.global.error.ErrorCode;
import com.skmnservice.global.error.exception.NotFoundException;
import com.skmnservice.member.entity.Member;
import com.skmnservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 사용자 계정(id)로 사용자의 정보를 가져오는 메서드
    @Override
    public Member loadUserByUsername(String id){ // 해당 부분을 무조건 User로 지어야만 시큐리티가 알아듣는다. Member로 사용시 인식 X
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

}
