package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import com.springboot.jwt.resume.entity.FrontendResume;
import com.springboot.jwt.resume.service.FrontendResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/form/frontend")
public class FrontendResumeController {
    private final FrontendResumeService frontendResumeService;

    public FrontendResumeController(FrontendResumeService frontendResumeService) {
        this.frontendResumeService = frontendResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> frontendCreateResume(@RequestBody FrontendResumeRequestDto frontendResumeRequestDto) {
        frontendResumeService.frontendCreateResume(frontendResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<FrontendResumeRequestDto> viewResume(@RequestParam Long id) {
        FrontendResumeRequestDto frontendResumeRequestDto = frontendResumeService.frontendGetResumeById(id);
        return ResponseEntity.ok(frontendResumeRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> frontendUpdateResume(
            @PathVariable Long id,
            @RequestBody FrontendResumeRequestDto frontendResumeRequestDto) {
        frontendResumeService.frontendUpdateResume(id, frontendResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
