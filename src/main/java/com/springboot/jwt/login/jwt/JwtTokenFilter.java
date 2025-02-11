package com.springboot.jwt.login.jwt;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Authorization header: " + authorizationHeader);

        // Authorization 헤더가 없거나 Bearer가 아니면 필터 진행
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰 추출
        String token = authorizationHeader.substring(7);

        // JWT 파싱 및 검증
        String email;
        try {
            email = jwtTokenUtil.getLoginId(token);
            System.out.println("Extracted email from token: " + email);
        } catch (Exception e) {
            System.out.println("JWT 파싱 오류 발생: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인
        if (jwtTokenUtil.isExpired(token)) {
            System.out.println("JWT 토큰이 만료됨");
            filterChain.doFilter(request, response);
            return;
        }

        // 사용자 조회
        User loginUser = userService.getLoginUserByLoginId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("Authenticated user: " + loginUser.getEmail());

        // 인증 객체 생성 및 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null,
                        List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContextHolder에 설정
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}