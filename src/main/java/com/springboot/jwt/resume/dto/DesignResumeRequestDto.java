package com.springboot.jwt.resume.dto;

import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignResumeRequestDto {
    private Long id;
    private String studentId;
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
    private boolean apply;

    @Override
    public String toString() {
        return "DesignResumeRequestDto(" +
                "id=" + id +
                ", studentId=" + studentId +
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

    public static DesignResumeRequestDto fromEntity(DesignResume designResume){
        if (designResume == null){
            return null;
        }
        DesignResumeRequestDto designResumeRequestDto = DesignResumeRequestDto.builder()
                .id(designResume.getId())
                .studentId(designResume.getUser() != null ? String.valueOf(designResume.getUser().getId()) : null)
                .name(designResume.getName())
                .designcontent1(designResume.getDesigncontent1())
                .designcontent2(designResume.getDesigncontent2())
                .designcontent3(designResume.getDesigncontent3())
                .designcontent4(designResume.getDesigncontent4())
                .designcontent5(designResume.getDesigncontent5())
                .designcontent6(designResume.getDesigncontent6())
                .designcontent7(designResume.getDesigncontent7())
                .designcontent8(designResume.getDesigncontent8())
                .designcontent9(designResume.getDesigncontent9())
                .apply(designResume.isApply())
                .build();
        return designResumeRequestDto;
    }
}