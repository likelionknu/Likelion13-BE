package com.springboot.jwt.adminPage.controller;

import com.springboot.jwt.adminPage.dto.UserListDto;
import com.springboot.jwt.adminPage.service.UserListService;
import com.springboot.jwt.resume.dto.FrontendResumeRequestDto;
import com.springboot.jwt.resume.service.FrontendResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3000", "https://www.likelionknu.com"})
public class AdminFrontendResumeController {

    private final FrontendResumeService frontendResumeService;
    private final UserListService userListService;

    public AdminFrontendResumeController(FrontendResumeService frontendResumeService, UserListService userListService) {
        this.frontendResumeService = frontendResumeService;
        this.userListService = userListService;
    }

    @GetMapping("/admin/userform/frontend/{id}")
    public ResponseEntity<FrontendResumeRequestDto> showFrontendResume(@PathVariable Long id){
        FrontendResumeRequestDto frontendResumeRequestDto = frontendResumeService.frontendGetResumeById(id);
        return ResponseEntity.ok(frontendResumeRequestDto);

    }

    @GetMapping("/admin/frontend/submit")
    public ResponseEntity<List<UserListDto>> getFrontendUserList() {
        List<UserListDto> userList = userListService.getFrontendUserList();
        return ResponseEntity.ok(userList);
    }


}
