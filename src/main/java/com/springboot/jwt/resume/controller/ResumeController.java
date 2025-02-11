package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.ResumeRequestDto;
import com.springboot.jwt.resume.entity.Resume;
import com.springboot.jwt.resume.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/form")
public class ResumeController {
    private final ResumeService resumeService;
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void>createResume(@RequestBody ResumeRequestDto resumeRequestDto){
        resumeService.createResume(resumeRequestDto);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }

    @PostMapping("/view")
    public ResponseEntity<ResumeRequestDto> viewResume(@RequestParam Long id) {
        ResumeRequestDto resumeRequestDto = resumeService.getResumeById(id);
        return ResponseEntity.ok(resumeRequestDto);
    }

    @PostMapping("/update/{id}")

    public ResponseEntity<Void>updateResume(@PathVariable Long id, @RequestBody ResumeRequestDto resumeRequestDto) {
        resumeService.updateResume(id, resumeRequestDto);

        return new ResponseEntity<> (HttpStatus.OK);

    }
}
