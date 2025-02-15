package com.springboot.jwt.resume.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackendResumeRequestDto {
    private Long id;
    private String studentId;
    private String name;
    private String backendcontent1;
    private String backendcontent2;
    private String backendcontent3;
    private String backendcontent4;
    private String backendcontent5;
    private String backendcontent6;
    private String backendcontent7;
    private String backendcontent8;
    private String backendcontent9;
    private String backendcontent10;

    @Override
    public String toString() {
        return "BackendResumeRequestDto(" +
                "id=" + id +
                ", studentId=" + studentId +
                ", name=" + name +
                ", backendcontent1=" + backendcontent1 +
                ", backendcontent2=" + backendcontent2 +
                ", backendcontent3=" + backendcontent3 +
                ", backendcontent4=" + backendcontent4 +
                ", backendcontent5=" + backendcontent5 +
                ", backendcontent6=" + backendcontent6 +
                ", backendcontent7=" + backendcontent7 +
                ", backendcontent8=" + backendcontent8 +
                ", backendcontent9=" + backendcontent9 +
                ", backendcontent10=" + backendcontent10 +
                ")";
    }
}