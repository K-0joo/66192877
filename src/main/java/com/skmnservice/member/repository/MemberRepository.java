package com.skmnservice.member.repository;

import com.skmnservice.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository <Member, UUID> {
    // id로 사용자 정보 가져오기
    Optional<Member> findById(String id);
}
