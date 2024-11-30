package com.skmnservice.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "id", nullable = false, unique = true, length = 50)
    private String id;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "name", length = 50)
    private String name;

    @Builder
    public Member(UUID memberId, String id, String password, String name){
        this.memberId = memberId;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return null;
    }
}
