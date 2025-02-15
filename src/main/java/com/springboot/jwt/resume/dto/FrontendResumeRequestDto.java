package com.springboot.jwt.resume.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrontendResumeRequestDto {
    private Long id;
    private String studentId;
    private String name;
    private String frontendcontent1;
    private String frontendcontent2;
    private String frontendcontent3;
    private String frontendcontent4;
    private String frontendcontent5;
    private String frontendcontent6;
    private String frontendcontent7;
    private String frontendcontent8;
    private String frontendcontent9;

    @Override
    public String toString() {
        return "FrontendResumeRequestDto(" +
                "id=" + id +
                ", studentId=" + studentId +
                ", name=" + name +
                ", frontendcontent1=" + frontendcontent1 +
                ", frontendcontent2=" + frontendcontent2 +
                ", frontendcontent3=" + frontendcontent3 +
                ", frontendcontent4=" + frontendcontent4 +
                ", frontendcontent5=" + frontendcontent5 +
                ", frontendcontent6=" + frontendcontent6 +
                ", frontendcontent7=" + frontendcontent7 +
                ", frontendcontent8=" + frontendcontent8 +
                ", frontendcontent9=" + frontendcontent9 +
                ")";
    }
}