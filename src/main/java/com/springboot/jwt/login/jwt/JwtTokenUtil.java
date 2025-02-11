package com.springboot.jwt.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // ✅ JWT 생성
    public String createToken(String email, long expireTimeMs) {
        return Jwts.builder()
                .setSubject(email) // ✅ setSubject() 사용
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ JWT에서 Email 추출
    public String getLoginId(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ JWT 만료 여부 확인
    public boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // ✅ JWT Claims 추출
    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 파싱 오류: " + e.getMessage());
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다.");
        }
    }
}