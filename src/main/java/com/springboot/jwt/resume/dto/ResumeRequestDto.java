package com.springboot.jwt.resume.dto;

import com.springboot.jwt.resume.entity.Resume;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // 🔴 추가: 모든 필드를 포함하는 생성자 자동 생성
@Builder
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

    // 지원서 최종 제출했는지 확인하는 상태, 기본값은 false
    private boolean apply;

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

    public static ResumeRequestDto fromEntity(Resume resume){
        if (resume == null){
            return null;
        }
        ResumeRequestDto resumeRequestDto = ResumeRequestDto.builder()
                .id(resume.getId())
                .userId(resume.getUser() != null ? resume.getUser().getId() : null)
                .name(resume.getName())
                .content1(resume.getContent1())
                .content2(resume.getContent2())
                .content3(resume.getContent3())
                .content4(resume.getContent4())
                .content5(resume.getContent5())
                .content6(resume.getContent6())
                .content7(resume.getContent7())
                .content8(resume.getContent8())
                .content9(resume.getContent9())
                .content10(resume.getContent10())
                .apply(resume.isApply())
                .build();
        return resumeRequestDto;
    }
}