package com.springboot.jwt.resume.service;


import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.dto.ResumeResponseDto;

public interface ResumeService {
    void createResume(ResumeRequestDto resumeRequestDto);
    void updateResume(Long id, ResumeRequestDto resumeRequestDto);
    ResumeRequestDto getResumeById(Long id);
}