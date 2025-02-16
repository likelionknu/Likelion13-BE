package com.springboot.jwt.resume.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDto {
    private String name;
    private String email;
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
    private boolean apply;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getContent1() {
        return content1;
    }

    public String getContent2() {
        return content2;
    }

    public String getContent3() {
        return content3;
    }

    public String getContent4() {
        return content4;
    }

    public String getContent5() {
        return content5;
    }

    public String getContent6() {
        return content6;
    }

    public String getContent7() {
        return content7;
    }

    public String getContent8() {
        return content8;
    }

    public String getContent9() {
        return content9;
    }

    public String getContent10() {
        return content10;
    }

    public boolean getApply(){
        return apply;
    }
    public String toString() {
        return "ResumeResponseDto(email=" + this.getEmail() +
                ", name=" + this.getName() +
                ", content1=" + this.getContent1() +
                ", content2=" + this.getContent2() +
                ", content3=" + this.getContent3() +
                ", content4=" + this.getContent4() +
                ", content5=" + this.getContent5() +
                ", content6=" + this.getContent6() +
                ", content7=" + this.getContent7() +
                ", content8=" + this.getContent8() +
                ", content9=" + this.getContent9() +
                ", content10=" + this.getContent10() + ")";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public void setContent6(String content6) {
        this.content6 = content6;
    }

    public void setContent7(String content7) {
        this.content7 = content7;
    }

    public void setContent8(String content8) {
        this.content8 = content8;
    }

    public void setContent9(String content9) {
        this.content9 = content9;
    }

    public void setContent10(String content10) {
        this.content10 = content10;
    }
}