package com.springboot.jwt.resume.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 🔴 추가: 모든 필드를 포함하는 생성자 자동 생성
public class ResumeRequestDto {
    private Long id;
    private Long userId;  // 🔴 수정: userId 추가
    private String name;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
    private String content5;
    private String content6;
    private String content7;
    private String content8;
    private String content9;
    private String content10;

    @Override
    public String toString() {
        return "ResumeRequestDto(" +
                "id=" + id +
                ", userId=" + userId +
                ", name=" + name +
                ", content1=" + content1 +
                ", content2=" + content2 +
                ", content3=" + content3 +
                ", content4=" + content4 +
                ", content5=" + content5 +
                ", content6=" + content6 +
                ", content7=" + content7 +
                ", content8=" + content8 +
                ", content9=" + content9 +
                ", content10=" + content10 +
                ")";
    }
}