package com.skmnservice.member.entity;

import com.skmnservice.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "member")
@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails { // UserDetails를 상속 받아 인증 객체로 사용
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "id", nullable = false, unique = true, length = 50)
    private String id;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "name", length = 50)
    private String name;

//    @OneToMany(mappedBy = "boards", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Board> boards; // 회원이 작성한 게시글 목록

    @Builder
    public Member(UUID memberId, String id, String password, String name){
        this.memberId = memberId;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("member"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return id;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
