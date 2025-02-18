package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.DesignResumeRequestDto;

public interface DesignResumeService {
    void designCreateResume(DesignResumeRequestDto designResumeRequestDto);
    void designUpdateResume(String studentId, DesignResumeRequestDto designResumeRequestDto);
    DesignResumeRequestDto designGetResumeById(String studentId);

    DesignResumeRequestDto updateResumeStatus(String studentId, boolean apply);
}