package com.springboot.jwt.resume.dto;

import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.entity.FrontendResume;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private boolean apply;

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

    public static FrontendResumeRequestDto fromEntity(FrontendResume frontendResume){
        if (frontendResume == null){
            return null;
        }
        FrontendResumeRequestDto frontendResumeRequestDto = FrontendResumeRequestDto.builder()
                .id(frontendResume.getId())
                .studentId(frontendResume.getUser() != null ? String.valueOf(frontendResume.getUser().getStudentId()) : null)
                .name(frontendResume.getName())
                .frontendcontent1(frontendResume.getFrontendcontent1())
                .frontendcontent2(frontendResume.getFrontendcontent2())
                .frontendcontent3(frontendResume.getFrontendcontent3())
                .frontendcontent4(frontendResume.getFrontendcontent4())
                .frontendcontent5(frontendResume.getFrontendcontent5())
                .frontendcontent6(frontendResume.getFrontendcontent6())
                .frontendcontent7(frontendResume.getFrontendcontent7())
                .frontendcontent8(frontendResume.getFrontendcontent8())
                .frontendcontent9(frontendResume.getFrontendcontent9())
                .apply(frontendResume.isApply())
                .build();
        return frontendResumeRequestDto;
    }
}