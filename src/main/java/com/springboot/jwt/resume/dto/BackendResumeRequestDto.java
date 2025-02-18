package com.springboot.jwt.resume.dto;

import com.springboot.jwt.resume.entity.BackendResume;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private boolean apply;

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

    public static BackendResumeRequestDto fromEntity(BackendResume backendResume){
        if (backendResume == null){
            return null;
        }
        BackendResumeRequestDto backendResumeRequestDto = BackendResumeRequestDto.builder()
                .id(backendResume.getId())
                .studentId(backendResume.getUser() != null ? String.valueOf(backendResume.getUser().getStudentId()) : null)
                .name(backendResume.getName())
                .backendcontent1(backendResume.getBackendcontent1())
                .backendcontent2(backendResume.getBackendcontent2())
                .backendcontent3(backendResume.getBackendcontent3())
                .backendcontent4(backendResume.getBackendcontent4())
                .backendcontent5(backendResume.getBackendcontent5())
                .backendcontent6(backendResume.getBackendcontent6())
                .backendcontent7(backendResume.getBackendcontent7())
                .backendcontent8(backendResume.getBackendcontent8())
                .backendcontent9(backendResume.getBackendcontent9())
                .backendcontent10(backendResume.getBackendcontent10())
                .apply(backendResume.isApply())
                .build();
        return backendResumeRequestDto;
    }
}