package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.service.BackendResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/form/backend")
public class BackendResumeController {
    private final BackendResumeService backendResumeService;

    public BackendResumeController(BackendResumeService backendResumeService) {
        this.backendResumeService = backendResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void>backendCreateResume(@RequestBody BackendResumeRequestDto backendResumeRequestDto){
        backendResumeService.backendCreateResume(backendResumeRequestDto);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<BackendResumeRequestDto> viewResume(@RequestParam Long id) {
        BackendResumeRequestDto backendResumeRequestDto = backendResumeService.backendGetResumeById(id);
        return ResponseEntity.ok(backendResumeRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void>backendUpdateResume(@PathVariable Long id, @RequestBody BackendResumeRequestDto backendResumeRequestDto) {
        backendResumeService.backendUpdateResume(id, backendResumeRequestDto);
        return new ResponseEntity<> (HttpStatus.OK);

    }
}
