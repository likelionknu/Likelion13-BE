package com.springboot.jwt.resume.service;


import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.dto.ResumeResponseDto;
import com.springboot.jwt.resume.entity.Resume;

import java.util.List;

public interface ResumeService {
    void createResume(ResumeRequestDto resumeRequestDto);
    void updateResume(Long id, ResumeRequestDto resumeRequestDto);
    ResumeRequestDto getResumeById(Long id);

    //ResumeRequestDto getResumeByStatus(boolean apply);
    ResumeRequestDto updateResumeStatus(Long id, boolean apply);

    List<ResumeRequestDto> findByApply(boolean apply);
}
