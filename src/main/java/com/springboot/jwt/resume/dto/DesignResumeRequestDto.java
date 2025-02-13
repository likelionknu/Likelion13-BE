package com.springboot.jwt.resume.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignResumeRequestDto {
    private Long id;
    private Long userId;
    private String name;
    private String designcontent1;
    private String designcontent2;
    private String designcontent3;
    private String designcontent4;
    private String designcontent5;
    private String designcontent6;
    private String designcontent7;
    private String designcontent8;
    private String designcontent9;

    @Override
    public String toString() {
        return "DesignResumeRequestDto(" +
                "id=" + id +
                ", userId=" + userId +
                ", name=" + name +
                ", designcontent1=" + designcontent1 +
                ", designcontent2=" + designcontent2 +
                ", designcontent3=" + designcontent3 +
                ", designcontent4=" + designcontent4 +
                ", designcontent5=" + designcontent5 +
                ", designcontent6=" + designcontent6 +
                ", designcontent7=" + designcontent7 +
                ", designcontent8=" + designcontent8 +
                ", designcontent9=" + designcontent9 +
                ")";
    }
}