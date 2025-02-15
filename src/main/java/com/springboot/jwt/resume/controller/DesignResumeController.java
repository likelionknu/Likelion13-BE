package com.springboot.jwt.resume.controller;

import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.service.DesignResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/form/design")
public class DesignResumeController {
    private final DesignResumeService designResumeService;

    public DesignResumeController(DesignResumeService designResumeService) {
        this.designResumeService = designResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> designCreateResume(@RequestBody DesignResumeRequestDto designResumeRequestDto) {
        designResumeService.designCreateResume(designResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<DesignResumeRequestDto> viewResume(@RequestParam Long id) {
        DesignResumeRequestDto designResumeRequestDto = designResumeService.designGetResumeById(id);
        return ResponseEntity.ok(designResumeRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> designUpdateResume(
            @PathVariable Long id,
            @RequestBody DesignResumeRequestDto designResumeRequestDto) {
        designResumeService.designUpdateResume(id, designResumeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
