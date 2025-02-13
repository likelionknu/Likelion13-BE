package com.springboot.jwt.resume.service;

import com.springboot.jwt.resume.dto.DesignResumeRequestDto;

public interface DesignResumeService {
    void designCreateResume(DesignResumeRequestDto designResumeRequestDto);
    void designUpdateResume(Long id, DesignResumeRequestDto designResumeRequestDto);
    DesignResumeRequestDto designGetResumeById(Long id);
}