package com.skmnservice.member.config;

import com.skmnservice.member.service.MemberDetailService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final MemberDetailService memberDetailService;

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/", "/api/member/**", "/api/board", "/api/board/{boardId}", "/h2-console/**", "/api/board/search").permitAll()
                        .requestMatchers("/css/**", "/javascript/**", "/images/**", "/favicon.ico" ).permitAll()
                        .requestMatchers("/api/board/edit/**", "/api/board/delete/**", "/api/board/write").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .usernameParameter("id") // 기본 username을 id로 변경
                        .passwordParameter("password")
                        .loginPage("/api/member/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/api/member/login") // Spring Security가 처리하는 경로
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"message\": \"로그인 실패\"}");
                        })
                        .defaultSuccessUrl("/api/board", true) // 로그인 성공 시 이동할 페이지
                        .permitAll())
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 비활성화
                .logout(logout -> logout
                        .logoutUrl("/api/member/logout")
                        .logoutSuccessUrl("/api/member/login")
                        .invalidateHttpSession(true)) // 로그아웃 이후 세션 전체 삭제 여부
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션이 필요한 경우 생성
            .headers(headers -> headers.frameOptions().disable());
        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, MemberDetailService memberDetailService) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(memberDetailService).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
